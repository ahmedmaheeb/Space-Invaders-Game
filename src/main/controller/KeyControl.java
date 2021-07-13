package main.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.view.GameView;

import java.util.Timer;
import java.util.TimerTask;

public class KeyControl {

    private final GameView gameView;
    private boolean right = false;
    private boolean left = false;
    private boolean shoot = false;
    private boolean canShoot = true;

    public KeyControl(GameView gameView, Scene scene) {
        this.gameView = gameView;
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::keyPressed);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::keyReleased);
        actionTimer();
        gameView.setKeyControl(this);
    }

    public void reset() {
        right = false;
        left = false;
        shoot = false;
        canShoot = true;
    }

    private void keyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case RIGHT -> right = true;
            case LEFT -> left = true;
            case SPACE -> shoot = true;
            case ESCAPE -> {
                reset();
                stopGame();
            }
        }
    }

    private void keyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case RIGHT -> right = false;
            case LEFT -> left = false;
            case SPACE -> shoot = false;
        }
    }

    private void actionTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (right) moveRight();
                if (left) moveLeft();
                if (shoot) shootBullet();
            }
        };
        Timer gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(timerTask, 40, 40);
    }

    private void bulletTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                canShoot = true;
            }
        };
        Timer gameTimer = new Timer();
        gameTimer.schedule(timerTask, 300);
    }

    private void shootBullet() {
        if (canShoot) {
            gameView.getGameBoard().getPlayerShip().shootBullet();
            canShoot = false;
            bulletTimer();
        }
    }

    private void moveLeft() {
        gameView.getGameBoard().getPlayerShip().moveLeft();
    }

    private void moveRight() {
        gameView.getGameBoard().getPlayerShip().moveRight();
    }

    private void stopGame() {
        gameView.stopGame();
    }
}
