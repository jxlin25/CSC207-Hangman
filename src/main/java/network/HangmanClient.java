package network;

import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.Room.RoomJoinPresenter;
import interface_adapter.Room.RoomJoinViewModel;
import interface_adapter.ViewManagerModel;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import entity.Player;
import use_case.Room.RoomJoinOutputData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HangmanClient extends WebSocketClient {
    private int roomId;
    private final ViewManagerModel viewManagerModel;
    private final RoomJoinViewModel roomJoinViewModel;
    private final RoomJoinPresenter roomJoinPresenter;
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("CLIENT: Connection opened successfully to " + this.getURI());
    }

    public HangmanClient(
            ViewManagerModel viewManagerModel,
            RoomJoinViewModel roomJoinViewModel,
            RoomJoinPresenter roomJoinPresenter
    ) throws URISyntaxException {

        super(new URI("ws://localhost:8080"));

        this.viewManagerModel = viewManagerModel;
        this.roomJoinViewModel = roomJoinViewModel;
        this.roomJoinPresenter = roomJoinPresenter;
    }


    @Override
    public void onMessage(String message) {
        System.out.println("Received: " + message);
        // Simple parsing for JSON messages from the server
        Map<String, String> parsedMessage = parseMessage(message);
        String type = parsedMessage.get("type");
        String room = parsedMessage.get("room");
        String errorMessage = parsedMessage.get("message");


        if ("error".equals(type)) {
            System.err.println("Server Error: " + errorMessage);
            roomJoinViewModel.getState().setError(errorMessage);
            roomJoinViewModel.firePropertyChange();
                } else if ("created".equals(type) || "joined".equals(type)) {
                    try {
                        int receivedRoomId = Integer.parseInt(room);
                        String isHostStr = parsedMessage.get("isHost");
                        boolean isHost = Boolean.parseBoolean(isHostStr);

                        Player currentPlayer = new Player("You"); // Placeholder player
                        currentPlayer.setHost(isHost); // Set host status
                        List<Player> players = Collections.singletonList(currentPlayer);
                        // Update the current client's local room ID (might be used for subsequent messages)
                        setRoomId(receivedRoomId);
        
                        RoomJoinOutputData successData = new RoomJoinOutputData(receivedRoomId, currentPlayer, players);
                        roomJoinPresenter.prepareSuccess(successData);
                        
                    } catch (NumberFormatException e) {
                        System.err.println("CLIENT: Error parsing room ID from server response: " + room);
                        roomJoinViewModel.getState().setError("Error joining/creating room: Invalid room ID from server.");
                        roomJoinViewModel.firePropertyChange();
                    }
                } else if ("START_GAME".equals(type)) { // Assuming START_GAME is a top-level type in the JSON
            viewManagerModel.setState(MakeGuessViewModel.VIEW_NAME);
            viewManagerModel.firePropertyChange();
        }
    }

    private Map<String, String> parseMessage(String json) {
        Map<String, String> result = new HashMap<>();

        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1); // Remove braces
            String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by comma outside quotes
            for (String pair : pairs) {
                String[] keyValue = pair.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by colon outside quotes
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replaceAll("\"", "");
                    String value = keyValue[1].trim().replaceAll("\"", "");
                    result.put(key, value);
                }
            }
        }
        return result;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("CLIENT: Connection closed by " + (remote ? "remote peer" : "us") + ". Code: " + code + ", Reason: " + reason);
    }


    @Override
    public void onError(Exception ex) {
        System.err.println("CLIENT: An error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }
}
