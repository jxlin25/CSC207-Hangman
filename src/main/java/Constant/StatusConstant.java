package Constant;

public class StatusConstant {


    /* These status are used by Round to track the state of the round
    * WAITING means the round has not started yet and is waiting for the round(s) before it to finish
    * GUESSING means the round is not over yet and the player is currently playing the round
    * WON means the round is over and the player won (guessed the word)
    * LOST means the round is over and the player lost (used up all the attempts without guessing the word correctly)

     */
    public static final String WAITING = "Waiting";
    public static final String GUESSING = "Guessing";
    public static final String WON = "Won";
    public static final String LOST = "Lost";

}
