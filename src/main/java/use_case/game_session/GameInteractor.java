package use_case.game_session;
import entity.WordPuzzle;
import entity.Player;
import entity.game_session.GameState;
import entity.game_session.GamePhase;


public class GameInteractor {
    public void processGuess(String word, String revealedWord, char guess){
        WordPuzzle puzzle = new WordPuzzle(word.toCharArray());
        for (int i = 0; i < revealedWord.length(); i++ ) {
            if(revealedWord.charAt(i) != '_') {
                puzzle.revealLetter(revealedWord.charAt(i));
            }
        }

        puzzle.revealLetter(guess);

    }
    public boolean isGuessCorrect(String word, char guess){
        WordPuzzle puzzle = new WordPuzzle(word.toCharArray());
        return puzzle.isLetterInWord(guess);
    }
    public boolean checkWinCondition(String revealedWord, int incorrectGuesses){

        boolean won = !revealedWord.contains("_");

        boolean lost = incorrectGuesses >= 6;

        return won || lost;
    }
    public void incrementSetterScore(Player player, String revealedWord, int incorrectGuesses) {
        if (!checkWinCondition(revealedWord, incorrectGuesses)) {
            player.addToScore(1);
        }
    }
    public void incrementWordSetterScore(Player player, String revealedWord, int incorrectGuesses) {
        if (checkWinCondition(revealedWord, incorrectGuesses)) {
            player.addToScore(2);
        }
    }
    public void determineNextRole(){}
    public boolean checkMatchOver(GameState gameState){
        if (gameState.getCurrentRound() >= gameState.getMaxRounds()) {
            gameState.setCurrentPhase(GamePhase.GAME_OVER);
        }
        return gameState.getCurrentRound() >= gameState.getMaxRounds();
    }
}
