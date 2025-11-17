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

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    final DBGenerateWordDataAccessObject generateWordAccessObject = new DBGenerateWordDataAccessObject();

    private GenerateWordViewModel generateWordViewModel;
    private GenerateWordView generateWordView;


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
}
