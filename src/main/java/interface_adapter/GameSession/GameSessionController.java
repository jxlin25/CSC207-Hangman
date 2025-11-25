package interface_adapter.GameSession;
import entity.Player;
import entity.game_session.GameState;
import entity.game_session.GamePhase;
import java.util.List;

import manager.GameSessionManager;
import use_case.game_session.GameInteractor;

public class GameSessionController {
    private final GameState gameState;
    private final GameInteractor gameInteractor;

    public GameSession

    public void handleGuess(String playerId, char letter) {
        if (!gameState.getCurrentGuesserId().equals(playerId)) {
            return;
        }

        String newRevealedWord = gameInteractor.processGuess(
                gameState.getSecretWord(),
                gameState.getRevealedWord(),
                letter
        );

        gameState.setRevealedWord(newRevealedWord);

        if (gameInteractor.checkWinCondition(newRevealedWord)) {
            // Handle round win
        }
        broadcastGameState();
    }


}
