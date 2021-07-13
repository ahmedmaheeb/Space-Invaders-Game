package main.model.devil;

import main.controller.GameBoard;
import main.model.Bullet;
import main.model.Dimension2D;
import main.model.Point2D;

public class BigDevil extends Devil {

    private final GameBoard gameBoard;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final String iconLocation = "BigDevil.png";
    private int cycles = 0;

    public BigDevil(Point2D position, GameBoard gameBoard) {
        super(position, new Dimension2D(WIDTH, HEIGHT), iconLocation);
        this.gameBoard = gameBoard;
    }

    @Override
    public void update() {
        cycles++;
        if(cycles % 30 == 0) {
            shootBullet();
        }
        // TODO
    }

    public void shootBullet() {
        gameBoard.addBullet(new Bullet(new Point2D(getPosition().getX() + 20, getPosition().getY() + 40), Bullet.BulletDirection.DOWN));
    }
}
