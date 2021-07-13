package main.model.devil;

import main.controller.GameBoard;
import main.model.Bullet;
import main.model.Dimension2D;
import main.model.Point2D;

public class BigDevil extends Devil {

    private final GameBoard gameBoard;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final String iconLocation = "BigDevil.png";

    public BigDevil(Point2D position, GameBoard gameBoard) {
        super(position, new Dimension2D(WIDTH, HEIGHT), iconLocation);
        this.gameBoard = gameBoard;
    }

    @Override
    public void update() {
        // TODO
    }

    public void shootBullet() {
        gameBoard.addBullet(new Bullet(getPosition(), Bullet.BulletDirection.DOWN));
    }
}
