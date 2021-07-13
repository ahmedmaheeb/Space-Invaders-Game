package main.model;

public class Entity {

    private Point2D position;
    private final Dimension2D size;
    private final String iconLocation;

    public Entity(Point2D position, Dimension2D size, String iconLocation) {
        this.position = position;
        this.size = size;
        this.iconLocation = iconLocation;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Dimension2D getSize() {
        return size;
    }

    public String getIconLocation() {
        return iconLocation;
    }
}
