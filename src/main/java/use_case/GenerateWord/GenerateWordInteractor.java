package use_case.GenerateWord;

import entity.HangmanGame;

import use_case.MakeGuess.MakeGuessHangmanGameDataAccessInterface;

import java.util.ArrayList;

/**
 * The Generate Word Interactor.
 */
public class GenerateWordInteractor implements GenerateWordInputBoundary {

    private final GenerateWordDataAccessInterface generateWordDataAccessInterface;
    private final GenerateWordOutputBoundary generateWordOutputBoundary;
    private final MakeGuessHangmanGameDataAccessInterface hangmanGameDAO;

    public GenerateWordInteractor(GenerateWordDataAccessInterface generateWordDataAccessInterface,
                                  GenerateWordOutputBoundary generateWordOutputBoundary,
                                  MakeGuessHangmanGameDataAccessInterface hangmanGameDAO) {
        this.generateWordDataAccessInterface = generateWordDataAccessInterface;
        this.generateWordOutputBoundary = generateWordOutputBoundary;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute(GenerateWordInputData inputData) {
        int n = inputData.getNumbers();
        int attempts = inputData.getAttempts(); // attempts added here

        if (n < 0) {
            //This situation won't occur, but to prevent any accidents
            generateWordOutputBoundary.prepareFailureView("Number of words must be langer than 0!!");
            return;
        }
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int tries = 0;
            String word = null;
            while (tries < 10) {
                word = generateWordDataAccessInterface.getRandomWord();
                generateWordDataAccessInterface.saveRandomWord(word);
                if (generateWordDataAccessInterface.isValidWord(word)) {
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

        //TODO this part maybe can change, now, we don't use OutPutData
        hangmanGameDAO.setHangmanGame(new HangmanGame(words));
        GenerateWordOutputData outputData = new GenerateWordOutputData(words, attempts);
        generateWordOutputBoundary.prepareSuccessView(outputData);
    }

}