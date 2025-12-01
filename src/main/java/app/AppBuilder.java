package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.DBGenerateWordDataAccessObject;
import data_access.DBHintDataAccessObject;
import data_access.InMemoryHangmanDataAccessObject;
import interface_adapter.Room.RoomJoinController;
import interface_adapter.ViewManagerModel;

import interface_adapter.EndGameResults.EndGameResultsController;
import interface_adapter.EndGameResults.EndGameResultsPresenter;
import interface_adapter.EndGameResults.EndGameResultsViewModel;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import interface_adapter.Hint.HintController;
import interface_adapter.Hint.HintPresenter;
import interface_adapter.InitializeRound.InitializeRoundController;
import interface_adapter.InitializeRound.InitializeRoundPresenter;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.MakeGuess.MakeGuessPresenter;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import use_case.endgameresults.EndGameResultsInputBoundary;
import use_case.endgameresults.EndGameResultsInteractor;
import use_case.endgameresults.EndGameResultsOutputBoundary;
import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import use_case.Hint.HintInputBoundary;
import use_case.Hint.HintInteractor;
import use_case.Hint.HintOutputBoundary;
import use_case.InitializeRound.InitializeRoundInputBoundary;
import use_case.InitializeRound.InitializeRoundInteractor;
import use_case.InitializeRound.InitializeRoundOutputBoundary;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInteractor;
import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.Room.RoomJoinInteractor;
import view.EndGameResultsView;
import view.GenerateWordView;
import view.MakeGuessView;
import view.RoomJoinView;
import view.ViewManager;

/**
 * Builder class for constructing the Hangman application.
 * Handles initialization of all views, view models, controllers, and use cases.
 */
public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // DAO
    private final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();
    private final InMemoryHangmanDataAccessObject hangmanGameDAO = new InMemoryHangmanDataAccessObject();
    private final DBHintDataAccessObject hintDAO = new DBHintDataAccessObject();

    // View Model
    private GenerateWordViewModel generateWordViewModel;
    private MakeGuessViewModel makeGuessViewModel;
    private EndGameResultsViewModel endGameResultsViewModel;

    // View
    private GenerateWordView generateWordView;
    private MakeGuessView makeGuessView;
    private RoomJoinView roomJoinView;
    private EndGameResultsView endGameResultsView;

    // Controller
    private RoomJoinController roomJoinController;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Gets the ViewManagerModel instance.
     * @return the ViewManagerModel
     */
    public ViewManagerModel getViewManagerModel() {
        return this.viewManagerModel;
    }

    /**
     * Adds the Generate Word View to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addGenerateWordView() {
        generateWordViewModel = new GenerateWordViewModel();
        generateWordView = new GenerateWordView(generateWordViewModel);
        cardPanel.add(generateWordView, generateWordView.getViewName());
        return this;
    }

    /**
     * Adds the Make Guess View to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addMakeGuessView() {
        makeGuessViewModel = new MakeGuessViewModel();
        makeGuessView = new MakeGuessView(makeGuessViewModel);
        cardPanel.add(makeGuessView, makeGuessView.getViewName());
        return this;
    }

    /**
     * Adds the End Game Results View to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addEndGameResultsView() {
        endGameResultsView = new EndGameResultsView(endGameResultsViewModel);
        endGameResultsView.setViewManagerModel(viewManagerModel);
        cardPanel.add(endGameResultsView, endGameResultsView.getViewName());
        return this;
    }

    /**
     * Adds the Room Join View to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addRoomJoinView() {
        final RoomJoinInteractor interactor = new RoomJoinInteractor();
        roomJoinController = new RoomJoinController(interactor);
        roomJoinView = new RoomJoinView();
        roomJoinView.setController(roomJoinController);
        return this;
    }

    /**
     * Adds the Generate Word Use Case to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addGenerateWordUseCase() {
        final GenerateWordOutputBoundary generateWordPresenter =
                new GenerateWordPresenter(generateWordViewModel, makeGuessViewModel, viewManagerModel);
        final GenerateWordInputBoundary generateWordInteractor =
                new GenerateWordInteractor(generateWordAccessObject, generateWordPresenter, hangmanGameDAO);
        final GenerateWordController controller = new GenerateWordController(generateWordInteractor);
        generateWordView.setGenerateWordController(controller);

        return this;
    }



    /**
     * Adds the Make Guess Use Case to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addMakeGuessUseCase() {
        final MakeGuessOutputBoundary makeGuessPresenter = new MakeGuessPresenter(makeGuessViewModel);
        final MakeGuessInputBoundary makeGuessInteractor = new MakeGuessInteractor(makeGuessPresenter, hangmanGameDAO);
        final MakeGuessController makeGuessController = new MakeGuessController(makeGuessInteractor);
        makeGuessView.setViewManagerModel(viewManagerModel);
        makeGuessView.setMakeGuessController(makeGuessController);
        return this;
    }

    /**
     * Adds the Initialize Round Use Case to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addInitializeRoundUseCase() {
        final InitializeRoundOutputBoundary initializeFirstRoundPresenter =
                new InitializeRoundPresenter(makeGuessViewModel);
        final InitializeRoundInputBoundary initializeFirstRoundInteractor =
                new InitializeRoundInteractor(initializeFirstRoundPresenter, hangmanGameDAO);
        final InitializeRoundController initializeRoundController =
                new InitializeRoundController(initializeFirstRoundInteractor);
        generateWordView.setInitializeRoundController(initializeRoundController);
        makeGuessView.setInitializeRoundController(initializeRoundController);
        return this;
    }

    /**
     * Adds the Hint Use Case to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addHintUseCase() {
        final HintOutputBoundary hintPresenter = new HintPresenter(makeGuessViewModel);
        final HintInputBoundary hintInteractor = new HintInteractor(hintDAO, hintPresenter, hangmanGameDAO);
        final HintController hintController = new HintController(hintInteractor);
        makeGuessView.setHintController(hintController);

        return this;
    }

    /**
     * Adds the End Game Results View Model to the application.
     * @return this AppBuilder instance for method chaining
     */
    public AppBuilder addEndGameResultsViewModel() {
        this.endGameResultsViewModel = new EndGameResultsViewModel();
        return this;
    }

    /**
     * Adds the End Game Results Use Case to the application.
     * @return this AppBuilder instance for method chaining
     */
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

    /**
     * Builds and returns the main application JFrame.
     * @return the configured JFrame for the Hangman application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Hangman");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        final JMenuBar menuBar = new JMenuBar();
        final JMenu gameMenu = new JMenu("Multiplayer");
        final JMenuItem roomMenuItem = new JMenuItem("Join/Create Room");
        roomMenuItem.addActionListener(event -> {
            if (roomJoinView != null) {
                roomJoinView.setVisible(true);
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

        return application;
    }
}