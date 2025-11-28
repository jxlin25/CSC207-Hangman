package app;

import data_access.InMemoryHangmanDataAccessObject;
import data_access.DBHintDataAccessObject;
import data_access.DBGenerateWordDataAccessObject;

import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.InitializeRound.InitializeRoundPresenter;
import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.InitializeRound.InitializeRoundPresenter;
import interface_adapter.ViewManagerModel;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.Hint.HintPresenter;
import interface_adapter.Hint.HintController;
import use_case.Hint.HintInteractor;

import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import use_case.Hint.HintDataAccessInterface;
import use_case.Hint.HintInputBoundary;
import use_case.Hint.HintOutputBoundary;
import use_case.InitializeRound.InitializeRoundInputBoundary;
import use_case.InitializeRound.InitializeRoundInteractor;
import use_case.InitializeRound.InitializeRoundOutputBoundary;
import use_case.MakeGuess.*;

import view.*;

import javax.swing.*;
import java.awt.*;

import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;


import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.MakeGuess.MakeGuessPresenter;

import interface_adapter.EndGameResults.EndGameResultsViewModel;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    //DAO
    final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();
    final InMemoryHangmanDataAccessObject hangmanGameDAO = new InMemoryHangmanDataAccessObject();
    final HintDataAccessInterface hintDAO = new DBHintDataAccessObject();

    //View Model
    private GenerateWordViewModel generateWordViewModel;
    private MakeGuessViewModel makeGuessViewModel;
    private EndGameResultsViewModel endGameResultsViewModel;

    //View
    private GenerateWordView generateWordView;
    private MakeGuessView makeGuessView;
    private RoomJoinView roomJoinView;
    private EndGameResultsView endGameResultsView;

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
        makeGuessView.setViewManagerModel(viewManagerModel);
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

    public AppBuilder addHintUseCase() {
        HintOutputBoundary hintPresenter = new HintPresenter(makeGuessViewModel);

        HintInputBoundary hintInteractor = new HintInteractor(hintDAO, hintPresenter);

        HintController hintController = new HintController(hintInteractor);

        makeGuessView.setHintController(hintController);

        return this;
    }

    public AppBuilder addEndGameResultsViewModel() {
        this.endGameResultsViewModel = new EndGameResultsViewModel();
        return this;
    }

    public AppBuilder addEndGameResultsView() {
        endGameResultsView = new EndGameResultsView(endGameResultsViewModel);
        cardPanel.add(endGameResultsView, endGameResultsView.getViewName());
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