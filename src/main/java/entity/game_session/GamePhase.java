package entity.game_session;

// Defines the finite set of steps a round can be in.
public enum GamePhase {

    // The round has started, waiting for the Setter to submit the custom word.
    WORD_SELECTION,

    // The Guesser is currently making guesses.
    GUESSING,

    // The word has been solved or failed; scores are being calculated/displayed.
    ROUND_END,

    // The entire 1v1 match (e.g., best-of-X rounds) is finished.
    GAME_OVER
}