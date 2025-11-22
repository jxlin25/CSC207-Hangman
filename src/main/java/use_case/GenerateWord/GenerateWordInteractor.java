package use_case.GenerateWord;

import entity.HangmanGame;
import entity.WordPuzzle;

import use_case.MakeGuess.MakeGuessHangmanGameDataAccessInterface;

import java.util.ArrayList;

public class GenerateWordInteractor implements GenerateWordInputBoundary {

    private final WordPuzzleDataAccessInterface wordPuzzleDataAccessInterface;
    private final GenerateWordOutputBoundary generateWordOutputBoundary;
    private final MakeGuessHangmanGameDataAccessInterface hangmanGameDAO;

    public GenerateWordInteractor(WordPuzzleDataAccessInterface wordPuzzleDataAccessInterface,
                                  GenerateWordOutputBoundary generateWordOutputBoundary,
                                  MakeGuessHangmanGameDataAccessInterface hangmanGameDAO) {
        this.wordPuzzleDataAccessInterface = wordPuzzleDataAccessInterface;
        this.generateWordOutputBoundary = generateWordOutputBoundary;
        this.hangmanGameDAO = hangmanGameDAO;
    }

    @Override
    public void execute() {
        String word = wordPuzzleDataAccessInterface.getRandomWord();
        wordPuzzleDataAccessInterface.saveRandomWord(word);
        if (wordPuzzleDataAccessInterface.isValidWord(word)) {

            //TODO We need to think about, use words? or word
            ArrayList<String> words = new ArrayList<>();
            words.add(word);
            hangmanGameDAO.setHangmanGame(new HangmanGame(words));

            WordPuzzle puzzle = new WordPuzzle(word.toCharArray());
            GenerateWordOutputData outputData = new GenerateWordOutputData(puzzle);
            generateWordOutputBoundary.prepareSuccessView(outputData);
        }
        //TODO here must be pass because isValidWord only return true now.
        return;
    }
}