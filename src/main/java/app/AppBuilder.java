package app;

import data_access.InMemoryHangmanDataAccessObject;
import data_access.DBHintDataAccessObject;
import data_access.DBGenerateWordDataAccessObject;

import interface_adapter.choose_difficulty.ChooseDifficultyController;
import interface_adapter.choose_difficulty.ChooseDifficultyPresenter;
import interface_adapter.endgame_results.EndGameResultsController;
import interface_adapter.endgame_results.EndGameResultsPresenter;
import interface_adapter.initialize_round.InitializeRoundController;
import interface_adapter.initialize_round.InitializeRoundPresenter;
import interface_adapter.ViewManagerModel;
import interface_adapter.generate_word.GenerateWordController;
import interface_adapter.generate_word.GenerateWordPresenter;
import interface_adapter.generate_word.GenerateWordViewModel;
import interface_adapter.generate_hint.HintPresenter;
import interface_adapter.generate_hint.HintController;

import use_case.choose_difficulty.ChooseDifficultyInputBoundary;
import use_case.choose_difficulty.ChooseDifficultyInteractor;
import use_case.choose_difficulty.ChooseDifficultyOutputBoundary;
import use_case.endgame_results.EndGameResultsInputBoundary;
import use_case.endgame_results.EndGameResultsInteractor;
import use_case.endgame_results.EndGameResultsOutputBoundary;
import use_case.generate_hint.HintInteractor;

import use_case.generate_word.GenerateWordInputBoundary;
import use_case.generate_word.GenerateWordInteractor;
import use_case.generate_word.GenerateWordOutputBoundary;
import use_case.generate_hint.HintInputBoundary;
import use_case.generate_hint.HintOutputBoundary;
import use_case.initialize_round.InitializeRoundInputBoundary;
import use_case.initialize_round.InitializeRoundInteractor;
import use_case.initialize_round.InitializeRoundOutputBoundary;
import use_case.make_guess.*;
import view.GenerateWordView;
import view.ViewManager;

import view.*;

import javax.swing.*;
import java.awt.*;

import view.RoomJoinView;
import interface_adapter.Room.RoomJoinController;
import use_case.Room.RoomJoinInteractor;


import view.MakeGuessView;
import interface_adapter.make_guess.MakeGuessViewModel;
import interface_adapter.make_guess.MakeGuessController;
import interface_adapter.make_guess.MakeGuessPresenter;

import interface_adapter.endgame_results.EndGameResultsViewModel;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    //DAO
    final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();
    final InMemoryHangmanDataAccessObject hangmanGameDAO = new InMemoryHangmanDataAccessObject();
    final DBHintDataAccessObject hintDAO = new DBHintDataAccessObject();

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

    public ViewManagerModel getViewManagerModel() {
        return this.viewManagerModel;
    }


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

//    public AppBuilder addDifficultySelectionView() {
//        // Ensure generateWordView is already created before this is called
//        chooseDifficultyView = new ChooseDifficultyView(viewManagerModel, generateWordView);
//        cardPanel.add(chooseDifficultyView, chooseDifficultyView.getViewName());
//        return this;
//        }

    public AppBuilder addEndGameResultsView() {
        endGameResultsView = new EndGameResultsView(endGameResultsViewModel);
        endGameResultsView.setViewManagerModel(viewManagerModel);
        cardPanel.add(endGameResultsView, endGameResultsView.getViewName());
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

        GenerateWordOutputBoundary generateWordPresenter = new GenerateWordPresenter(generateWordViewModel, makeGuessViewModel, viewManagerModel);
        GenerateWordInputBoundary generateWordInteractor = new GenerateWordInteractor(generateWordAccessObject, generateWordPresenter,hangmanGameDAO);
        GenerateWordController controller = new GenerateWordController(generateWordInteractor);
        generateWordView.setGenerateWordController(controller);

        return this;
    }

    public AppBuilder addChooseDifficultyUseCase() {
        final ChooseDifficultyOutputBoundary chooseDifficultyPresenter = new ChooseDifficultyPresenter(makeGuessViewModel);
        final ChooseDifficultyInputBoundary chooseDifficultyInteractor = new ChooseDifficultyInteractor(chooseDifficultyPresenter, hangmanGameDAO);
        final ChooseDifficultyController chooseDifficultyController = new ChooseDifficultyController(chooseDifficultyInteractor);
        generateWordView.setChooseDifficultyController(chooseDifficultyController);
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
        generateWordView.setInitializeRoundController(initializeRoundController);
        makeGuessView.setInitializeRoundController(initializeRoundController);
        return this;
    }

    public AppBuilder addHintUseCase() {
        HintOutputBoundary hintPresenter = new HintPresenter(makeGuessViewModel);

        HintInputBoundary hintInteractor = new HintInteractor(hintDAO, hintPresenter, hangmanGameDAO);

        HintController hintController = new HintController(hintInteractor);

        makeGuessView.setHintController(hintController);

        return this;
    }

    public AppBuilder addEndGameResultsViewModel() {
        this.endGameResultsViewModel = new EndGameResultsViewModel();
        return this;
    }


    public AppBuilder addEndGameResultsUseCase() {
        final EndGameResultsOutputBoundary presenter =
                new EndGameResultsPresenter(endGameResultsViewModel, viewManagerModel);
        final EndGameResultsInputBoundary interactor =
                new EndGameResultsInteractor(presenter, hangmanGameDAO);
        final EndGameResultsController controller =
                new EndGameResultsController(interactor);
        makeGuessView.setEndGameResultsController(controller);
        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Hangman");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

// disable for demo
// viewManagerModel.setState(generateWordView.getViewName());
//  viewManagerModel.firePropertyChange();

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

//        // CHANGED: start on difficulty selection instead of generate word
//        if (chooseDifficultyView != null) {
//            viewManagerModel.setState(chooseDifficultyView.getViewName());
//        } else {
//            // fallback to generateWordView if difficultySelectionView not configured
//            viewManagerModel.setState(generateWordView.getViewName());
//        }
        viewManagerModel.setState(generateWordView.getViewName());
        viewManagerModel.firePropertyChange();
        // Add the main content
        return application;
    }
}
