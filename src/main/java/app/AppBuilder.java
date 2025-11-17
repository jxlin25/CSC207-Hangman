package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.GenerateWord.GenerateWordController;
import interface_adapter.GenerateWord.GenerateWordPresenter;
import interface_adapter.GenerateWord.GenerateWordViewModel;
import use_case.GenerateWord.GenerateWordInputBoundary;
import use_case.GenerateWord.GenerateWordInteractor;
import use_case.GenerateWord.GenerateWordOutputBoundary;
import data_access.DBGenerateWordDataAccessObject;
import view.GenerateWordView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;


import view.MakeGuessView;
import interface_adapter.MakeGuess.MakeGuessViewModel;
import interface_adapter.MakeGuess.MakeGuessController;
import interface_adapter.MakeGuess.MakeGuessPresenter;
import use_case.MakeGuess.MakeGuessInputBoundary;
import use_case.MakeGuess.MakeGuessInteractor;
import use_case.MakeGuess.MakeGuessOutputBoundary;
import use_case.MakeGuess.MakeGuessWordPuzzleDataAccessInterface;

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

    public JFrame build() {
        JFrame application = new JFrame("Hangman");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState(generateWordView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }

    public AppBuilder addMakeGuessView() {

        makeGuessViewModel = new MakeGuessViewModel(MakeGuessViewModel.VIEW_NAME);

        makeGuessView = new MakeGuessView(makeGuessViewModel);

        return this;
    }

    public AppBuilder addMakeGuessUseCase() {
        MakeGuessOutputBoundary makeGuessOutputBoundary = new MakeGuessPresenter(makeGuessViewModel);

        return this;
    }
}
