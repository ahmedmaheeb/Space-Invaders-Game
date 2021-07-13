package main.model.devil;

import main.model.Dimension2D;
import main.model.Point2D;

public class SmallDevil extends Devil {

    private static final int SPEED = 5;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final String iconLocation = "SmallDevil.png";

    public SmallDevil(Point2D position) {
        super(position, new Dimension2D(WIDTH, HEIGHT), iconLocation);
    }

    @Override
    public void update() {
        Point2D position = this.getPosition();
        this.setPosition(new Point2D(position.getX(), position.getY() + SPEED));
    }
}
