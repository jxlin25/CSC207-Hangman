package entity.game_session;

// Defines the finite set of steps a round can be in.
public enum GamePhase {

    WORD_SELECTION,

    GUESSING,

    ROUND_END,

    GAME_OVER
}