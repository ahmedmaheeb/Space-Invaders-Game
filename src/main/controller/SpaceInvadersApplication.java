package main.controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.view.GameToolBar;
import main.view.GameView;

/**
 * This class is an adapted version of the BumpersApplication from the bumpers game from EIST.
 */

public class SpaceInvadersApplication extends Application {
    private static final int GRID_LAYOUT_PADDING = 5;
    private static final int GRID_LAYOUT_PREF_HEIGHT = 350;
    private static final int GRID_LAYOUT_PREF_WIDTH = 505;

    /**
     * Starts the Bumpers Window by setting up a new tool bar, a new user interface
     * and adding them to the stage.
     *
     * @param primaryStage the primary stage for this application, onto which the
     *                     application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // the tool bar object with start and stop buttons
        GameToolBar toolBar = new GameToolBar();
        GameView gameView = new GameView(toolBar);
        toolBar.initializeActions(gameView);

        Pane gridLayout = createLayout(gameView, toolBar);

        // scene and stages
        Scene scene = new Scene(gridLayout);
        primaryStage.setTitle("Space invaders");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeEvent -> gameView.stopGame());
        primaryStage.show();

        // Key Steering
        new KeyControl(gameView, scene);
    }

    /**
     * Creates a new {@link Pane} that arranges the game's UI elements.
     */
    private static Pane createLayout(GameView gameView, GameToolBar toolBar) {
        // GridPanes are divided into columns and rows, like a table
        GridPane gridLayout = new GridPane();
        gridLayout.setPrefSize(GRID_LAYOUT_PREF_WIDTH, GRID_LAYOUT_PREF_HEIGHT);
        gridLayout.setVgap(GRID_LAYOUT_PADDING);
        gridLayout.setPadding(new Insets(GRID_LAYOUT_PADDING));

        // add all components to the gridLayout
        // second parameter is column index, second parameter is row index of grid
        gridLayout.add(gameView, 0, 1);
        gridLayout.add(toolBar, 0, 0);
        return gridLayout;
    }

    /**
     * The whole game will be executed through the launch() method.
     * <p>
     */
    public static void startApp(String[] args) {
        launch(args);
    }
}
