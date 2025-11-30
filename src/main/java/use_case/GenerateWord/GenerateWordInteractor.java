package use_case.GenerateWord;

import entity.HangmanGame;

import use_case.MakeGuess.HangmanGameDataAccessInterface;

import java.util.ArrayList;

/**
 * The Generate Word Interactor.
 */
public class GenerateWordInteractor implements GenerateWordInputBoundary {

    private final GenerateWordDataAccessInterface generateWordDataAccessInterface;
    private final GenerateWordOutputBoundary generateWordOutputBoundary;
    private final HangmanGameDataAccessInterface hangmanGameDataAccessInterface;

    public GenerateWordInteractor(GenerateWordDataAccessInterface generateWordDataAccessInterface,
                                  GenerateWordOutputBoundary generateWordOutputBoundary,
                                  HangmanGameDataAccessInterface hangmanGameDataAccessInterface) {
        this.generateWordDataAccessInterface = generateWordDataAccessInterface;
        this.generateWordOutputBoundary = generateWordOutputBoundary;
        this.hangmanGameDataAccessInterface = hangmanGameDataAccessInterface;
    }

    @Override
    public void execute(GenerateWordInputData inputData) {
        int n = inputData.getNumbers();
        if (n <= 0) {
            // This situation won't occur, but to prevent any accidents
            generateWordOutputBoundary.prepareFailureView("Number of words must be langer than 0!!");
            return;
        }
        final ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int tries = 0;
            String word = null;
            while (tries < 10) {
                word = generateWordDataAccessInterface.getRandomWord();
                if (generateWordDataAccessInterface.isValidWord(word)) {
                    break;
                }
                tries++;
            }
            if (word == null || !generateWordDataAccessInterface.isValidWord(word)) {
                generateWordOutputBoundary.prepareFailureView("Failed to generate a valid word after 10 attempts, please try again!");
                return;
            }
            words.add(word);
        }
        System.out.println("----------------------------");
        System.out.println("The Generate Words is :" + words);
        System.out.println("----------------------------");

        hangmanGameDataAccessInterface.setHangmanGame(new HangmanGame(words));
        final GenerateWordOutputData outputData = new GenerateWordOutputData(words);
        generateWordOutputBoundary.prepareSuccessView(outputData);
    }
}
