package main.model;

public class Bullet extends Entity {
    private static final int SPEED = 10;
    private static final int WIDTH = 6;
    private static final int HEIGHT = 6;
    private static final String iconLocation = "Bullet.png";

    private final BulletDirection direction;

    public enum BulletDirection {
        UP, DOWN
    }

    public Bullet(Point2D position, BulletDirection direction) {
        super(position, new Dimension2D(WIDTH, HEIGHT), iconLocation);
        this.direction = direction;
    }

    public void update() {
        if (direction == BulletDirection.DOWN){
            setPosition(new Point2D(getPosition().getX(), getPosition().getY() + SPEED));
        } else {
            setPosition(new Point2D(getPosition().getX(), getPosition().getY() - SPEED));
        }
    }

    public BulletDirection getDirection() {
        return direction;
    }

    public int getSPEED() {
        return SPEED;
    }
}
