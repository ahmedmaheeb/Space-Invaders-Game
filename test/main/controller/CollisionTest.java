package main.controller;

import main.model.Dimension2D;
import main.model.PlayerShip;
import main.model.Point2D;
import main.model.devil.BigDevil;
import main.model.devil.SmallDevil;
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
    @Test
    public void testIsCollisionUsingMock() {
        GameBoard gameboard = new GameBoard(new Dimension2D(500.0, 500.0));
        // initialize the mock objects
        BigDevil bigDevilMock = mock(BigDevil.class);
        PlayerShip playerShipMock = mock(PlayerShip.class);
        // setup mock objects
        expect(bigDevilMock.getPosition()).andReturn(new Point2D(100, 100));
        expect(bigDevilMock.getSize()).andReturn(new Dimension2D(100, 100));
        expect(playerShipMock.getPosition()).andReturn(new Point2D(100, 100));
        expect(playerShipMock.getSize()).andReturn(new Dimension2D(100, 100));
        // activate the mock objects
        replay(bigDevilMock);
        PlayerShip playerShip2 = new PlayerShip(gameboard, new Point2D(100, 100));
        assertTrue(isCollision(bigDevilMock, playerShip2));
        verify(bigDevilMock);
    }
}