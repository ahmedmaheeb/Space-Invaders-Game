package main.view;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.controller.GameBoard;
import main.controller.GameOutcome;
import main.controller.KeyControl;
import main.controller.SoundPlayer;
import main.model.Bullet;
import main.model.Dimension2D;
import main.model.PlayerShip;
import main.model.Point2D;
import main.model.devil.BigDevil;
import main.model.devil.Devil;
import main.model.devil.SmallDevil;

import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is an adapted version of the GameBoardUI from the bumpers game from EIST.
 */
public class GameView extends Canvas {

    private static final Color BACKGROUND_COLOR = Color.BLACK;
    /**
     * The update period of the game in ms, this gives us 25 fps.
     */
    private static final int UPDATE_PERIOD = 1000 / 25;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 500;
    private static final Dimension2D DEFAULT_SIZE = new Dimension2D(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public static Dimension2D getPreferredSize() {
        return DEFAULT_SIZE;
    }

    /**
     * Timer responsible for updating the game every frame that runs in a separate
     * thread.
     */
    private Timer gameTimer;

    private GameBoard gameBoard;

    private KeyControl keyControl;

    private final GameToolBar gameToolBar;

    private HashMap<String, Image> imageCache;

    public GameView(GameToolBar gameToolBar) {
        this.gameToolBar = gameToolBar;
        setup();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Removes all devils from the game board. The player is set back and his lifes are reset
     */
    public void setup() {
        setupGameBoard();
        setupImageCache();
        this.gameToolBar.setGameBoard(gameBoard);
        this.gameToolBar.updateToolBarStatus(false);
        paint();
    }

    private void setupGameBoard() {
        Dimension2D size = getPreferredSize();
        this.gameBoard = new GameBoard(size);
        this.gameBoard.setSoundPlayer(new SoundPlayer());
        widthProperty().set(size.getWidth());
        heightProperty().set(size.getHeight());
    }

    private void setupImageCache() {
        this.imageCache = new HashMap<>();

        addToImageCache(new SmallDevil(new Point2D(0, 0)).getIconLocation());
        addToImageCache(new BigDevil(new Point2D(0, 0), gameBoard).getIconLocation());
        addToImageCache(new Bullet(new Point2D(0, 0), Bullet.BulletDirection.DOWN).getIconLocation());
        addToImageCache(gameBoard.getPlayerShip().getIconLocation());
    }

    private void addToImageCache(String iconLocation) {
        this.imageCache.put(iconLocation, getImage(iconLocation));
    }

    /**
     * Sets the image of an entity.
     *
     * @param ImageFilePath an image file path that needs to be available in the
     *                         resources folder of the project
     */
    private Image getImage(String ImageFilePath) {
        URL ImageUrl = getClass().getClassLoader().getResource(ImageFilePath);
        if (ImageUrl == null) {
            throw new IllegalArgumentException(String.format("The file %s can't be found", ImageFilePath));
        }
        return new Image(ImageUrl.toExternalForm());
    }

    /**
     * Starts the GameView Thread, if it wasn't running. Starts the game board,
     * which causes the devils to change their positions (i.e. move). Renders graphics
     * and updates tool bar status.
     */
    public void startGame() {
        if (!this.gameBoard.isRunning()) {
            this.gameBoard.startGame();
            this.gameToolBar.updateToolBarStatus(true);
            this.gameToolBar.update();
            startTimer();
            paint();
        }
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                updateGame();
            }
        };
        if (this.gameTimer != null) {
            this.gameTimer.cancel();
        }
        this.gameTimer = new Timer();
        this.gameTimer.scheduleAtFixedRate(timerTask, UPDATE_PERIOD, UPDATE_PERIOD);
    }

    private void updateGame() {
        if (gameBoard.isRunning()) {
            // updates car positions and re-renders graphics
            this.gameBoard.update();
            this.gameToolBar.update();
            // when this.gameBoard.getOutcome() is OPEN, do nothing
            if (this.gameBoard.getGameOutcome() == GameOutcome.LOST) {
                showAsyncAlert("Your last ship was destroyed. The world is now being overrun by devils.");
                this.stopGame();
            } else if (this.gameBoard.getGameOutcome() == GameOutcome.WON) {
                showAsyncAlert("Congratulations! You won!!");
                this.stopGame();
            }
            paint();
        }
    }

    /**
     * Stops the game board and set the tool bar to default values.
     */
    public void stopGame() {
        if (this.gameBoard.isRunning()) {
            this.keyControl.reset();
            this.gameBoard.stopGame();
            this.gameToolBar.updateToolBarStatus(false);
            this.gameTimer.cancel();
        }
    }

    /**
     * Render the graphics of the whole game by iterating through the devils of the
     * game board and render each of them individually.
     */
    private void paint() {
        getGraphicsContext2D().setFill(BACKGROUND_COLOR);
        getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());

        for (Devil devil : this.gameBoard.getDevils()) paintDevil(devil);
        for (Bullet bullet : this.gameBoard.getPlayerBullets()) paintBullet(bullet);
        for (Bullet bullet : this.gameBoard.getDevilBullets()) paintBullet(bullet);

        paintPlayerShip(this.gameBoard.getPlayerShip());
    }

    /**
     * Show image of a devil at the current position of the devil.
     *
     * @param devil to be drawn
     */
    private void paintDevil(Devil devil) {
        Point2D devilLocation = devil.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(devil.getIconLocation()), devilLocation.getX(),
                devilLocation.getY(), devil.getSize().getWidth(), devil.getSize().getHeight());
    }

    private void paintPlayerShip(PlayerShip playerShip) {
        Point2D playerShipLocation = playerShip.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(playerShip.getIconLocation()), playerShipLocation.getX(),
                playerShipLocation.getY(), playerShip.getSize().getWidth(), playerShip.getSize().getHeight());
    }

    private void paintBullet(Bullet bullet) {
        Point2D bulletLocation = bullet.getPosition();

        getGraphicsContext2D().drawImage(this.imageCache.get(bullet.getIconLocation()), bulletLocation.getX(),
                bulletLocation.getY(), bullet.getSize().getWidth(), bullet.getSize().getHeight());
    }

    /**
     * Method used to display alerts in moveDevils().
     *
     * @param message you want to display as a String
     */
    private void showAsyncAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(message);
            alert.showAndWait();
            this.setup();
        });
    }

    public void setKeyControl(KeyControl keyControl) {
        this.keyControl = keyControl;
    }
}
