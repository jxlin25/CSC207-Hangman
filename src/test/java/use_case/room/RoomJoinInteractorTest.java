package use_case.room;

import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.Assert.*;

public class RoomJoinInteractorTest {

    private RoomJoinInteractor interactor;

    @Before
    public void setUp() {
        interactor = new RoomJoinInteractor();
    }

    @Test
    public void testCheckRoomExists_ConnectionError() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean onErrorCalled = new AtomicBoolean(false);
        String[] errorMessage = new String[1];

        RoomJoinInteractor.RoomCheckCallback callback = new RoomJoinInteractor.RoomCheckCallback() {
            @Override
            public void onRoomChecked(int roomId, boolean exists) {
                // Should not be called
                fail("onRoomChecked should not be called on connection error");
            }

            @Override
            public void onError(String message) {
                onErrorCalled.set(true);
                errorMessage[0] = message;
                latch.countDown();
            }
        };

        // Assuming no server is running on this port
        interactor.checkRoomExists(9999, callback);

        // Wait for the async operation to complete or timeout
        assertTrue("Timeout waiting for onError callback", latch.await(10, TimeUnit.SECONDS));

        assertTrue("onError should be called when connection fails", onErrorCalled.get());
        assertNotNull("Error message should not be null", errorMessage[0]);
        assertTrue("Error message should indicate connection failure", errorMessage[0].contains("Connection failed"));
    }

}