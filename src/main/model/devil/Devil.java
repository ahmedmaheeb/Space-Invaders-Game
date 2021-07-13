package main.model.devil;

import main.model.Dimension2D;
import main.model.Entity;
import main.model.Point2D;

public abstract class Devil extends Entity {

    public Devil(Point2D position, Dimension2D size, String iconLocation) {
        super(position, size, iconLocation);
    }

    public abstract void update();
}
