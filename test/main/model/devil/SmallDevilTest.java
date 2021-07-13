package main.model.devil;

import main.controller.GameBoard;
import main.model.Bullet;
import main.model.Dimension2D;
import main.model.Point2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmallDevilTest {

    private final GameBoard gameBoard = new GameBoard(new Dimension2D(100.0, 100.0));
    private final SmallDevil smallDevil = new SmallDevil(new Point2D(10.0, 20.0));
    private final BigDevil bigDevil = new BigDevil(new Point2D(15.0, 25.0), gameBoard);

    @Test
    void testSmallDevilMovement() {
        Point2D expected = new Point2D(10.0, 25.0);
        smallDevil.update();
        assertEquals(expected, smallDevil.getPosition());
    }

    @Test
    void testBigDevilShooting() {
        bigDevil.shootBullet();
        List<Bullet> devilBullets = gameBoard.getDevilBullets();
        Bullet lastDevilBullet = devilBullets.get(devilBullets.size() - 1);
        Bullet expectedBullet = new Bullet(new Point2D(15.0, 25.0), Bullet.BulletDirection.DOWN);
        assertEquals(expectedBullet.getDirection(), lastDevilBullet.getDirection());
        assertEquals(expectedBullet.getPosition(), lastDevilBullet.getPosition());
    }
}