package main.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Text;
import main.controller.GameBoard;

import java.util.Optional;

/**
 * This class is an adapted version of the GameToolBar from the bumpers game from EIST.
 */
public class GameToolBar extends ToolBar {
    private final Button start;
    private final Button end;
    private final Text lives;
    private final Text points;
    private final Text timePlayed;
    private final Text killsPerSecond;
    private GameBoard gameBoard;

    public GameToolBar() {
        this.start = new Button("Start");
        this.end = new Button("End");
        end.setFocusTraversable(false);
        this.lives = new Text(" Lives: 3");
        this.points = new Text("| Points: 0");
        this.killsPerSecond = new Text("| 0.0 kills per second");
        this.timePlayed = new Text("| 0 seconds played");

        getItems().addAll(start, end, lives, points, killsPerSecond, timePlayed);
        addKeyControlInfo();
        // the game is stopped initially
        updateToolBarStatus(false);
    }

    /**
     * Initializes the actions of the toolbar buttons.
     */
    public void initializeActions(GameView gameView) {
        gameBoard = gameView.getGameBoard();

        this.start.setOnAction(event -> gameView.startGame());

        this.end.setOnAction(event -> {
            // stop the game while the alert is shown
            gameView.stopGame();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to end the game?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("End Game Confirmation");
            // By default the header additionally shows the Alert Type (Confirmation)
            // but we want to disable this to only show the question
            alert.setHeaderText("");

            Optional<ButtonType> result = alert.showAndWait();
            // reference equality check is OK here because the result will return the same
            // instance of the ButtonType
            if (result.isPresent() && result.get() == ButtonType.YES) {
                // reset the game board to prepare the new game
                gameView.setup();
            } else {
                // continue running
                gameView.startGame();
            }
        });
    }

    /**
     * Updates the status of the toolbar. This will for example enable or disable
     * buttons.
     *
     * @param running true if game is running, false otherwise
     */
    public void updateToolBarStatus(boolean running) {
        this.start.setDisable(running);
        this.end.setDisable(!running);
    }

    public void update() {
        this.lives.setText(" Lives: " + gameBoard.getPlayerShip().getLives());
        this.points.setText("| Points: " + gameBoard.getPoints());
        this.killsPerSecond.setText("| " + gameBoard.getKillsPerSecond() + " kills per second");
        this.timePlayed.setText("| " + gameBoard.getTimePlayed() + " seconds played");
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    private void addKeyControlInfo(){
        Text gap = new Text(" ".repeat(11));
        getItems().add(gap);

        Button rightArrow = new Button("➡");
        Button leftArrow = new Button("⬅");
        Button space = new Button("Space");

        rightArrow.setFocusTraversable(false);
        leftArrow.setFocusTraversable(false);
        space.setFocusTraversable(false);

        getItems().addAll(rightArrow, new Text("move right"));
        getItems().addAll(leftArrow, new Text("move left"));
        getItems().addAll(space, new Text("shoot bullet"));
    }
}