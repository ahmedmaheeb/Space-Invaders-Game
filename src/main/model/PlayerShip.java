package main.model;

import main.controller.GameBoard;

public class PlayerShip extends Entity {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static final String iconLocation = "Spaceship.png";
    private static final int SPEED = 5;

    private int lives = 3;

    private final GameBoard gameBoard;

    public PlayerShip(GameBoard gameBoard, Point2D position) {
        super(position, new Dimension2D(WIDTH, HEIGHT), iconLocation);
        this.gameBoard = gameBoard;
    }

    public void moveRight() {
        double x = Math.min(getPosition().getX() + SPEED, gameBoard.getSize().getWidth() - getSize().getWidth());
        setPosition(new Point2D(x, getPosition().getY()));
    }

    public void moveLeft() {
        double x = Math.max(getPosition().getX() - SPEED, 0);
        setPosition(new Point2D(x, getPosition().getY()));
    }

    public void incrementLives() {
        lives++;
    }

    public void decrementLives() {
        //game over if all lives are lost
        if(--lives <= 0){
            gameBoard.gameOver();
        }
    }

    public void shootBullet() {
        gameBoard.addBullet(new Bullet(new Point2D(getPosition().getX() + 25, getPosition().getY()), Bullet.BulletDirection.UP));
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
