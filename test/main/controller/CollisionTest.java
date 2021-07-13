package main.controller;

import main.model.Dimension2D;
import main.model.PlayerShip;
import main.model.Point2D;
import main.model.devil.BigDevil;
import main.model.devil.SmallDevil;
import org.easymock.Mock;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static main.controller.Collision.isCollision;

class CollisionTest {

    @Test
    public void testIsCollision() {
        GameBoard gameboard = new GameBoard(new Dimension2D(500.0, 500.0));
        SmallDevil smallDevil = new SmallDevil(new Point2D(100, 100));
        PlayerShip playerShip2 = new PlayerShip(gameboard, new Point2D(100, 100));
        assertTrue(isCollision(smallDevil, playerShip2));
    }

    // Collision Test no.2 with mock
    //sfs
    @Mock
    private BigDevil bigDevil;

    @Test
    public void testIsCollisionUsingMock() {
        GameBoard gameboard = new GameBoard(new Dimension2D(500.0, 500.0));
        replay(bigDevil);
        expect(bigDevil.getPosition()).andReturn(new Point2D(100, 100));
        PlayerShip playerShip2 = new PlayerShip(gameboard, new Point2D(100, 100));
        assertTrue(isCollision(bigDevil, playerShip2));
    }
}