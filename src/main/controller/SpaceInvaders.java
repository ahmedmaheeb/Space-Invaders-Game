package main.controller;

/**
 * This class is the Bumpers class from the bumpers game from EIST.
 */
public class SpaceInvaders {
    private SpaceInvaders() {
        // Private constructor because a utility class should not be instantiable.
    }

    public static void main(String[] args) {
        // This is a workaround for a known issue when starting JavaFX applications
        SpaceInvadersApplication.startApp(args);
    }
}
