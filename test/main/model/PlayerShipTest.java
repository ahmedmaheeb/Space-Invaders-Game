package main.model;

import main.controller.GameBoard;
import main.controller.GameOutcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class PlayerShipTest {

    private GameBoard gameBoard;
    private PlayerShip playerShip;

    @BeforeEach
    public void setup() {
        gameBoard = new GameBoard(new Dimension2D(500.0, 500.0));
        playerShip = new PlayerShip(gameBoard, new Point2D(100, 100));
    }

    @Test
    public void moveRight() {
        Point2D initialPosition = playerShip.getPosition();
        playerShip.moveRight();
        Assertions.assertTrue(initialPosition.getX() < playerShip.getPosition().getX());
    }

    @Test
    public void moveLeft() {
        Point2D initialPosition = playerShip.getPosition();
        playerShip.moveLeft();
        Assertions.assertTrue(initialPosition.getX() > playerShip.getPosition().getX());
    }

    @Test
    public void decrementLives() {
        playerShip.setLives(2);
        playerShip.decrementLives();
        Assertions.assertEquals(playerShip.getLives(), 1);
    }

    @Test
    public void decrementLivesToZero() {
        playerShip.setLives(1);
        playerShip.decrementLives();
        Assertions.assertEquals(gameBoard.getGameOutcome(), GameOutcome.LOST);
    }
}