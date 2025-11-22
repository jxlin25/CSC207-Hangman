package app;

import data_access.InMemoryHangmanDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import data_access.DBGenerateWordDataAccessObject;
import use_case.MakeGuess.*;
import view.GenerateWordView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;

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

    //TODO if we need add view
    private GenerateWordViewModel generateWordViewModel;
    private MakeGuessViewModel makeGuessViewModel;

    private GenerateWordView generateWordView;
    private MakeGuessView makeGuessView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
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

    //TODO if we have any new view, put it aboard, and usecase below

    public AppBuilder addGenerateWordUseCase() {

        GenerateWordOutputBoundary GenerateWordOutputBoundary = new GenerateWordPresenter(generateWordViewModel, makeGuessViewModel, viewManagerModel);

        GenerateWordInputBoundary generateWordInteractor = new GenerateWordInteractor(generateWordAccessObject, GenerateWordOutputBoundary,hangmanGameDAO);

        GenerateWordController controller = new GenerateWordController(generateWordInteractor);

        generateWordView.setGenerateWordController(controller);
        return this;
    }

    public AppBuilder addMakeGuessUseCase() {
        MakeGuessOutputBoundary makeGuessPresenter = new MakeGuessPresenter(makeGuessViewModel);

        MakeGuessInputBoundary makeGuessInteractor = new MakeGuessInteractor(makeGuessPresenter, hangmanGameDAO);

        MakeGuessController makeGuessController = new MakeGuessController(makeGuessInteractor);

        makeGuessView.setMakeGuessController(makeGuessController);

        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Hangman");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState(generateWordView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
