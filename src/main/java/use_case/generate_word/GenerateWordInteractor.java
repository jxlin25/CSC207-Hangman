package use_case.generate_word;

import java.util.ArrayList;

import entity.HangmanGame;
import use_case.make_guess.MakeGuessDataAccessInterface;

/**
 * The Generate Word Interactor.
 */
public class GenerateWordInteractor implements GenerateWordInputBoundary {

    private final GenerateWordDataAccessInterface generateWordDataAccessInterface;
    private final GenerateWordOutputBoundary generateWordOutputBoundary;
    private final MakeGuessDataAccessInterface hangmanGameDataAccessObject;

    public GenerateWordInteractor(GenerateWordDataAccessInterface generateWordDataAccessInterface,
                                  GenerateWordOutputBoundary generateWordOutputBoundary,
                                  MakeGuessDataAccessInterface hangmanGameDataAccessObject) {
        this.generateWordDataAccessInterface = generateWordDataAccessInterface;
        this.generateWordOutputBoundary = generateWordOutputBoundary;
        this.hangmanGameDataAccessObject = hangmanGameDataAccessObject;
    }

    @Override
    public void execute(GenerateWordInputData inputData) {
        int n = inputData.getNumbers();
        if (n < 0) {
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

        hangmanGameDataAccessObject.setHangmanGame(new HangmanGame(words));
        final GenerateWordOutputData outputData = new GenerateWordOutputData(words);
        generateWordOutputBoundary.prepareSuccessView(outputData);
    }
}