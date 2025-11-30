package app;

import data_access.DBGenerateWordDataAccessObject;
import data_access.InMemoryHangmanDataAccessObject;
import data_access.InMemoryLobbyDataAccessObject;
import data_access.InMemoryRoomJoinDataAccessObject;
import data_access.NetworkMakeGuessDataAccessObject; // Added
import data_access.NetworkSubmitWordDataAccessObject;
import data_access.RoomRepository;
import entity.HangmanGame;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.InitializeFirstRound.InitializeFirstRoundController;
import interface_adapter.InitializeFirstRound.InitializeFirstRoundPresenter;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.MakeGuess.MakeGuessPresenter;
import interface_adapter.MakeGuess.MakeGuessState;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.Room.*;
import interface_adapter.SubmitWord.SubmitWordController;
import interface_adapter.SubmitWord.SubmitWordPresenter;
import interface_adapter.SubmitWord.SubmitWordViewModel;
import interface_adapter.ViewManagerModel;
import manager.GameSessionManager;
import network.HangmanClient;
import org.java_websocket.client.WebSocketClient;
import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import use_case.InitializeFirstRound.InitializeFirstRoundInputBoundary;
import use_case.InitializeFirstRound.InitializeFirstRoundInteractor;
import use_case.InitializeFirstRound.InitializeFirstRoundOutputBoundary;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInteractor;
import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.Room.LobbyDataAccessInterface;
import use_case.Room.LobbyInteractor;
import use_case.Room.RoomJoinInteractor;
import use_case.SubmitWord.SubmitWordInputBoundary;
import use_case.SubmitWord.SubmitWordInteractor;
import use_case.SubmitWord.SubmitWordOutputBoundary;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    //DAO
    final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();
    final InMemoryHangmanDataAccessObject hangmanGameDAO = new InMemoryHangmanDataAccessObject();
    final RoomRepository roomRepository = new RoomRepository();
    private final HangmanClient hangmanClient;
    private NetworkMakeGuessDataAccessObject networkMakeGuessDataAccessObject; // Added

    // View Models
    private final LobbyViewModel lobbyViewModel = new LobbyViewModel();
    private final RoomJoinViewModel roomJoinViewModel = new RoomJoinViewModel();
    private final GenerateWordViewModel generateWordViewModel = new GenerateWordViewModel();
    private final MakeGuessViewModel makeGuessViewModel = new MakeGuessViewModel();
    private final SubmitWordViewModel submitWordViewModel = new SubmitWordViewModel();

    // Presenters
    private final RoomJoinPresenter roomJoinPresenter;
    private final MakeGuessPresenter makeGuessPresenter;
    private final LobbyPresenter lobbyPresenter;
    private final SubmitWordPresenter submitWordPresenter;

    // Controllers
    private RoomJoinController roomJoinController;
    private MakeGuessController makeGuessController;
    private GenerateWordController generateWordController;
    private SubmitWordController submitWordController;

    // Views
    private GenerateWordView generateWordView;
    private MakeGuessView makeGuessView;
    private RoomJoinView roomJoinView;
    private LobbyView lobbyView;
    private WordSetView wordSetView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);

        // Initialize Presenters and other dependencies that need ViewModels
        this.roomJoinPresenter = new RoomJoinPresenter(this.roomJoinViewModel, this.lobbyViewModel, this.viewManagerModel);
        this.makeGuessPresenter = new MakeGuessPresenter(this.makeGuessViewModel);
        this.lobbyPresenter = new LobbyPresenter(lobbyViewModel, viewManagerModel, makeGuessViewModel);
        this.submitWordPresenter = new SubmitWordPresenter(submitWordViewModel);

        try {
            this.hangmanClient = new HangmanClient(viewManagerModel, this.roomJoinViewModel, this.roomJoinPresenter, this.makeGuessViewModel);
            this.hangmanClient.connectBlocking(); // Establish connection before UI is used.
            this.networkMakeGuessDataAccessObject = new NetworkMakeGuessDataAccessObject(hangmanClient); // Initialized here
        } catch (Exception e) {
            // This includes the InterruptedException from connectBlocking
            throw new RuntimeException(e);
        }
    }

    /**
     * View
     */

    public AppBuilder addGenerateWordView() {
        generateWordView = new GenerateWordView(generateWordViewModel);
        cardPanel.add(generateWordView, generateWordView.getViewName());
        return this;
    }

    public AppBuilder addMakeGuessView() {
        // Dependencies for MakeGuessController
        MakeGuessInputBoundary makeGuessInteractor = new MakeGuessInteractor(makeGuessPresenter, hangmanGameDAO);
        // Updated MakeGuessController constructor call
        makeGuessController = new MakeGuessController(makeGuessInteractor, networkMakeGuessDataAccessObject, makeGuessViewModel);
        
        makeGuessView = new MakeGuessView(makeGuessViewModel, makeGuessController, viewManagerModel);
        cardPanel.add(makeGuessView, makeGuessView.getViewName());
        return this;
    }

    public AppBuilder addLobbyView() {
        // LobbyPresenter is now initialized in constructor
        LobbyDataAccessInterface dataAccess = new InMemoryLobbyDataAccessObject(roomRepository, hangmanClient);
        GameSessionManager sessionManager = GameSessionManager.getInstance();

        LobbyInteractor lobbyInteractor = new LobbyInteractor(lobbyPresenter, dataAccess, sessionManager);
        LobbyController controller = new LobbyController(lobbyInteractor);

        lobbyView = new LobbyView(lobbyViewModel);
        lobbyView.setLobbyController(controller);
        cardPanel.add(lobbyView, lobbyView.getLobbyViewModel().getViewName());
        return this;
    }

    public AppBuilder addRoomJoinView() {
        try {
            InMemoryRoomJoinDataAccessObject inMemoryRoomDataAccess = new InMemoryRoomJoinDataAccessObject(hangmanClient, roomRepository);
            RoomJoinInteractor interactor = new RoomJoinInteractor(inMemoryRoomDataAccess, roomJoinPresenter);
            roomJoinController = new RoomJoinController();
            roomJoinView = new RoomJoinView(this.roomJoinViewModel);
            roomJoinView.setController(roomJoinController);
            roomJoinView.getController().setInputBoundary(interactor);
            cardPanel.add(roomJoinView, this.roomJoinViewModel.getViewName());

            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }

    public AppBuilder addWordSetView() {
        // Dependencies for SubmitWordController
        NetworkSubmitWordDataAccessObject submitWordDataAccessObject = new NetworkSubmitWordDataAccessObject(hangmanClient);
        SubmitWordInputBoundary submitWordInteractor = new SubmitWordInteractor(submitWordDataAccessObject, submitWordPresenter);
        submitWordController = new SubmitWordController(submitWordInteractor);

        wordSetView = new WordSetView(submitWordController, submitWordViewModel);
        cardPanel.add(wordSetView, wordSetView.viewName);
        return this;
    }


    /**
     * UseCase
     */

    public AppBuilder addGenerateWordUseCase() {

        GenerateWordOutputBoundary generateWordOutputBoundary = new GenerateWordPresenter(generateWordViewModel, makeGuessViewModel, viewManagerModel);
        GenerateWordInputBoundary generateWordInteractor = new GenerateWordInteractor(generateWordAccessObject, generateWordOutputBoundary,hangmanGameDAO);
        generateWordController = new GenerateWordController(generateWordInteractor);
        generateWordView.setGenerateWordController(generateWordController);
        return this;
    }

    // makeGuessController is now initialized earlier
    public AppBuilder addMakeGuessUseCase() {
        // makeGuessPresenter is now initialized in constructor
        // makeGuessInteractor is now initialized within addMakeGuessView
        // makeGuessController is now initialized within addMakeGuessView
        // makeGuessView.setMakeGuessController(makeGuessController); // Controller is now passed in constructor
        return this;
    }


    public JFrame build() {
        JFrame application = new JFrame("Hangman");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

// disable for demo
//        viewManagerModel.setState(generateWordView.getViewName());
//        viewManagerModel.firePropertyChange();
//
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Multiplayer");
        JMenuItem roomMenuItem = new JMenuItem("Join/Create Room");
        roomMenuItem.addActionListener(e -> {
                viewManagerModel.setState(roomJoinView.getRoomJoinViewModel().getViewName());
                viewManagerModel.firePropertyChange();
        });

        gameMenu.add(roomMenuItem);
        menuBar.add(gameMenu);
        application.setJMenuBar(menuBar);

        // Ensure all views are added before setting initial view
        this.addGenerateWordView().addMakeGuessView().addLobbyView().addRoomJoinView().addWordSetView();
        this.addGenerateWordUseCase(); // Make sure controllers are set

        // Initial view will be set by the client based on server communication (e.g., after joining a room or receiving GameStateUpdate)
        // viewManagerModel.setState(generateWordView.getViewName());
        // viewManagerModel.firePropertyChange();
        // Add the main content
        return application;
    }
}
