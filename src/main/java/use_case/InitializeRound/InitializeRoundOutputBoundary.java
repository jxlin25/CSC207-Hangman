package use_case.InitializeRound;

public interface InitializeRoundOutputBoundary {

    /**
     * Initialize the view by passing the necessary data of the first round of the game.
     * @param outputData necessary data for the View to display the initial state of the game
     */
    void initializeView(InitializeRoundOutputData outputData);
}