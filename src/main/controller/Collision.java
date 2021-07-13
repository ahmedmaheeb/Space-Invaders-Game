package main.controller;

import main.model.Dimension2D;
import main.model.Entity;
import main.model.Point2D;

public class Collision {

	/**
	 * This method is adapted from the Bumpers game
	 */
	public static boolean isCollision(Entity entity1, Entity entity2) {
		Point2D p1 = entity1.getPosition();
		Dimension2D d1 = entity1.getSize();

		Point2D p2 = entity2.getPosition();
		Dimension2D d2 = entity2.getSize();

		boolean above = p1.getY() + d1.getHeight() < p2.getY();
		boolean below = p1.getY() > p2.getY() + d2.getHeight();
		boolean right = p1.getX() + d1.getWidth() < p2.getX();
		boolean left = p1.getX() > p2.getX() + d2.getWidth();

		return !above && !below && !right && !left;
	}
}
