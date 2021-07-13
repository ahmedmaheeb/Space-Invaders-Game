package main.controller;

import main.model.Bullet;
import main.model.Dimension2D;
import main.model.Point2D;
import main.model.devil.Devil;
import main.model.devil.SmallDevil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameBoardTest {
    private final GameBoard gameBoard = new GameBoard(new Dimension2D(100.0, 100.0));
    private final Bullet bullet = new Bullet(new Point2D(20.0, 0.0), Bullet.BulletDirection.UP);
    private final Devil devil = new SmallDevil(new Point2D(21.0, 21.0));

    @Test
    void testDevilShot() {
        gameBoard.addBullet(bullet);
        gameBoard.getDevils().add(devil);
        gameBoard.update();
        assertEquals(0, gameBoard.getDevils().size());
    }

}