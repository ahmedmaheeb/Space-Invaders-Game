package main.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    @Test
    void bulletMovementDown() {
        Bullet bullet = new Bullet(new Point2D(10.0, 25.0), Bullet.BulletDirection.DOWN);
        Point2D startPos = new Point2D(10.0, 25.0 + bullet.getSPEED());
        bullet.update();
        assertEquals(startPos, bullet.getPosition());
    }

    @Test
    void bulletMovementUp() {
        Bullet bullet = new Bullet(new Point2D(7.0, 200.0), Bullet.BulletDirection.UP);
        Point2D expected = new Point2D(7.0, 200 - bullet.getSPEED());
        bullet.update();
        assertEquals(expected, bullet.getPosition());
    }
}