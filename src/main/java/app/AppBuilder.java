package app;

import data_access.InMemoryHangmanDataAccessObject;
//import data_access.InMemoryWordPuzzleDataAccessObject;
import entity.HangmanGame;
import interface_adapter.InitializeFirstRound.InitializeFirstRoundController;
import interface_adapter.InitializeFirstRound.InitializeFirstRoundPresenter;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.ViewManagerModel;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import data_access.DBGenerateWordDataAccessObject;
import use_case.InitializeFirstRound.InitializeFirstRoundInputBoundary;
import use_case.InitializeFirstRound.InitializeFirstRoundInteractor;
import use_case.InitializeFirstRound.InitializeFirstRoundOutputBoundary;
import use_case.MakeGuess.*;
import view.GenerateWordView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import view.RoomJoinView;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;


import view.MakeGuessView;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.MakeGuess.MakeGuessPresenter;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();

    private GenerateWordViewModel generateWordViewModel;
    private GenerateWordView generateWordView;
    private GenerateWordController generateWordController;

    private MakeGuessViewModel makeGuessViewModel;
    private MakeGuessView makeGuessView;

    private RoomJoinView roomJoinView;
    private RoomJoinController roomJoinController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addGenerateWordView() {
        generateWordViewModel = new GenerateWordViewModel();
        generateWordView = new GenerateWordView(generateWordViewModel);
        cardPanel.add(generateWordView, generateWordView.getViewName());
        return this;
    }

    public AppBuilder addGenerateWordUseCase() {

        GenerateWordOutputBoundary GenerateWordOutputBoundary = new GenerateWordPresenter(generateWordViewModel);

        GenerateWordInputBoundary generateWordInteractor = new GenerateWordInteractor(generateWordAccessObject, GenerateWordOutputBoundary);

        GenerateWordController controller = new GenerateWordController(generateWordInteractor);

        generateWordView.setGenerateWordController(controller);
        return this;
    }

    public AppBuilder addRoomJoinView() {
        RoomJoinInteractor interactor = new RoomJoinInteractor();
        roomJoinController = new RoomJoinController(interactor);
        roomJoinView = new RoomJoinView();
        roomJoinView.setController(roomJoinController);

        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Hangman");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
// disable for demo
//        viewManagerModel.setState(generateWordView.getViewName());
//        viewManagerModel.firePropertyChange();
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Multiplayer"); // Changed from "Game" to be more clear
        JMenuItem roomMenuItem = new JMenuItem("Join/Create Room");
        roomMenuItem.addActionListener(e -> {
            if (roomJoinView != null) {
                roomJoinView.setVisible(true);
                // Center the room join view
                roomJoinView.setLocationRelativeTo(application);
            } else {
                System.out.println("roomJoinView is null!");
            }
        });

        gameMenu.add(roomMenuItem);
        menuBar.add(gameMenu);
        application.setJMenuBar(menuBar);

        // Add the main content
        application.add(cardPanel);

        if (makeGuessView != null) {
            viewManagerModel.setState(makeGuessView.getViewName());
            viewManagerModel.firePropertyChange();
        }

        return application;
    }

    public AppBuilder addMakeGuessView() {

        makeGuessViewModel = new MakeGuessViewModel(MakeGuessViewModel.VIEW_NAME);

        makeGuessView = new MakeGuessView(makeGuessViewModel);



//        // Cheat for demo
//        MakeGuessState initial = makeGuessViewModel.getState();
//        initial.setLetters("apple".toCharArray());
//        initial.setRevealedLettersBooleans(new boolean[]{false, false, false, false, false});
//        initial.setRemainingAttempts(6);
//        initial.setMessage("Game started!");
//        initial.setCurrentRoundNumber(1);
//        makeGuessViewModel.setState(initial);
//        makeGuessViewModel.firePropertyChanged();

        cardPanel.add(makeGuessView, makeGuessView.getViewName());

        return this;
    }

    public AppBuilder addMakeGuessUseCase() {
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList("Charizard", "cat", "umbrella", "university", "hangman"));
        InMemoryHangmanDataAccessObject hangmanGameDAO = new InMemoryHangmanDataAccessObject(new HangmanGame(wordList));

        MakeGuessOutputBoundary makeGuessOutputBoundary =
                new MakeGuessPresenter(makeGuessViewModel);

        MakeGuessInputBoundary makeGuessInteractor =
                new MakeGuessInteractor(makeGuessOutputBoundary, hangmanGameDAO);

        MakeGuessController makeGuessController =
                new MakeGuessController(makeGuessInteractor);

        makeGuessView.setMakeGuessController(makeGuessController);

        // Initialize the first round to the view
        InitializeFirstRoundOutputBoundary initializeFirstRoundOutputBoundary =
                new InitializeFirstRoundPresenter(makeGuessViewModel);

        InitializeFirstRoundInputBoundary initializeFirstRoundInteractor =
                new InitializeFirstRoundInteractor(initializeFirstRoundOutputBoundary, hangmanGameDAO);

        InitializeFirstRoundController initializeFirstRoundController =
                new InitializeFirstRoundController(initializeFirstRoundInteractor);

        initializeFirstRoundController.execute();

        return this;
    }

//    public AppBuilder addInitializeFirstRoundUseCase() {
//
//
//
//        return this;
//    }
}
