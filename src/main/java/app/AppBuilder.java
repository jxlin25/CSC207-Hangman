package app;

import data_access.InMemoryHangmanDataAccessObject;
import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.InitializeRound.InitializeRoundPresenter;
import interface_adapter.ViewManagerModel;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import data_access.DBGenerateWordDataAccessObject;
import use_case.InitializeRound.InitializeRoundInputBoundary;
import use_case.InitializeRound.InitializeRoundInteractor;
import use_case.InitializeRound.InitializeRoundOutputBoundary;
import use_case.MakeGuess.*;
import view.GenerateWordView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;

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

    //DAO
    final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();
    final InMemoryHangmanDataAccessObject hangmanGameDAO = new InMemoryHangmanDataAccessObject();

    //View Model
    private GenerateWordViewModel generateWordViewModel;
    private MakeGuessViewModel makeGuessViewModel;

    //View
    private GenerateWordView generateWordView;
    private MakeGuessView makeGuessView;
    private RoomJoinView roomJoinView;

    //Controller
    private RoomJoinController roomJoinController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * View
     */

    public AppBuilder addGenerateWordView() {
        generateWordViewModel = new GenerateWordViewModel();
        generateWordView = new GenerateWordView(generateWordViewModel);
        cardPanel.add(generateWordView, generateWordView.getViewName());
        return this;
    }

    public AppBuilder addMakeGuessView() {
        makeGuessViewModel = new MakeGuessViewModel();
        makeGuessView = new MakeGuessView(makeGuessViewModel);
        cardPanel.add(makeGuessView, makeGuessView.getViewName());
        return this;
    }

    public AppBuilder addRoomJoinView() {
        RoomJoinInteractor interactor = new RoomJoinInteractor();
        roomJoinController = new RoomJoinController(interactor);
        roomJoinView = new RoomJoinView();
        roomJoinView.setController(roomJoinController);
        return this;
    }

    //TODO if we have any new view, put it aboard, and usecase below

    /**
     *UseCase
     */

    public AppBuilder addGenerateWordUseCase() {

        GenerateWordOutputBoundary GenerateWordOutputBoundary = new GenerateWordPresenter(generateWordViewModel, makeGuessViewModel, viewManagerModel);
        GenerateWordInputBoundary generateWordInteractor = new GenerateWordInteractor(generateWordAccessObject, GenerateWordOutputBoundary,hangmanGameDAO);
        GenerateWordController controller = new GenerateWordController(generateWordInteractor);
        generateWordView.setGenerateWordController(controller);
        return this;
    }

    public AppBuilder addMakeGuessUseCase() {
        final MakeGuessOutputBoundary makeGuessPresenter = new MakeGuessPresenter(makeGuessViewModel);
        final MakeGuessInputBoundary makeGuessInteractor = new MakeGuessInteractor(makeGuessPresenter, hangmanGameDAO);
        final MakeGuessController makeGuessController = new MakeGuessController(makeGuessInteractor);
        makeGuessView.setMakeGuessController(makeGuessController);
        return this;
    }

    public AppBuilder addInitializeRoundUseCase() {
        final InitializeRoundOutputBoundary initializeFirstRoundPresenter = new InitializeRoundPresenter(makeGuessViewModel);
        final InitializeRoundInputBoundary initializeFirstRoundInteractor = new InitializeRoundInteractor(initializeFirstRoundPresenter, hangmanGameDAO);
        final InitializeRoundController initializeRoundController = new InitializeRoundController(initializeFirstRoundInteractor);
        generateWordView.setInitializeFirstRoundController(initializeRoundController);
        makeGuessView.setInitializeRoundController(initializeRoundController);
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
            }
            else {
                System.out.println("roomJoinView is null!");
            }
        });

        gameMenu.add(roomMenuItem);
        menuBar.add(gameMenu);
        application.setJMenuBar(menuBar);

        viewManagerModel.setState(generateWordView.getViewName());
        viewManagerModel.firePropertyChange();
        // Add the main content
        return application;
    }
}
