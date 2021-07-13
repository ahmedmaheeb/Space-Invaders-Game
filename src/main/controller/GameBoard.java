package main.controller;

import main.model.Bullet;
import main.model.Dimension2D;
import main.model.PlayerShip;
import main.model.Point2D;
import main.model.devil.Devil;
import main.model.devil.SmallDevil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {

	private final static int POINTS_PER_DEVIL = 100;

	private final Dimension2D size;

	private final PlayerShip playerShip;
	private final List<Devil> devils = new ArrayList<>();
	private final List<Bullet> playerBullets = new ArrayList<>();
	private final List<Bullet> devilBullets = new ArrayList<>();

	private SoundPlayer soundPlayer;
	private GameOutcome gameOutcome;
	private boolean running;

	private int points;
	private int pointsToNextLife = 200;

	private long startCurrentTimeMillis = 0;

	private long timePlayed = 0;
	private long sessionTimePlayed = 0;
	private int devilsKilled = 0;
	private double killsPerSecond;

	public GameBoard(Dimension2D size) {
		this.size = size;
		this.playerShip = new PlayerShip(this, new Point2D(size.getWidth() / 2, size.getHeight() - 50));
	}

	public void startGame() {
		soundPlayer.playMusic();
		this.running = true;
		this.startCurrentTimeMillis = System.currentTimeMillis();
	}

	public void stopGame() {
		soundPlayer.stopMusic();
		this.running = false;
		timePlayed += sessionTimePlayed;
	}

	public void gameOver() {
		gameOutcome = GameOutcome.LOST;
	}

	public void update() {
		// Movement
		for (Bullet bullet : playerBullets) bullet.update();
		for (Bullet bullet : devilBullets) bullet.update();
		for (Devil devil : devils) devil.update();

		// Spawning of new devils if needed
		addDevils();

		//remove bullets outside of scope
		devilBullets.removeIf(bullet -> bullet.getPosition().getY() > size.getHeight());
		playerBullets.removeIf(bullet -> bullet.getPosition().getY() < 0);
		// remove devils outside of scope
		devils.removeIf(devil -> devil.getPosition().getY() > size.getHeight());

		List<Devil> toBeRemovedDevil = new ArrayList<>();
		List<Bullet> toBeRemovedPlayerBullet = new ArrayList<>();
		List<Bullet> toBeRemovedDevilBullet = new ArrayList<>();

		// Collision checks
		for (Devil devil : devils) {
			for (Bullet bullet : playerBullets) {
				if (Collision.isCollision(devil, bullet)) {
					toBeRemovedPlayerBullet.add(bullet);
					toBeRemovedDevil.add(devil);
					increaseScore(10);
				}
			}
			if (Collision.isCollision(devil, playerShip)) {
				toBeRemovedDevil.add(devil);
				playerShip.decrementLives();
				soundPlayer.playPlayerShotSound();
			}
		}
		for (Bullet bullet : devilBullets) {
			if (Collision.isCollision(bullet, playerShip)) {
				toBeRemovedDevilBullet.add(bullet);
				playerShip.decrementLives();
				soundPlayer.playPlayerShotSound();
			}
		}

		devils.removeAll(toBeRemovedDevil);
		playerBullets.removeAll(toBeRemovedPlayerBullet);
		devilBullets.removeAll(toBeRemovedDevilBullet);

		increasePlayerLives();

		//Stats
		sessionTimePlayed = System.currentTimeMillis() - startCurrentTimeMillis;
		if (sessionTimePlayed != 0) killsPerSecond = (double) Math.round((double) devilsKilled * 100000 / sessionTimePlayed) / 100;
		else killsPerSecond = 0.00;
	}

	private void increasePlayerLives() {
		if (points >= pointsToNextLife) {
			playerShip.incrementLives();
			pointsToNextLife *= 2;
		}
	}

	private void killDevil(Devil devil) {
		devils.remove(devil);
		increaseScore(10);
		devilsKilled++;
	}

	private void addDevils() {
		Random random = new Random();
		while (devils.size() < points / POINTS_PER_DEVIL + 5) {
			devils.add(new SmallDevil(new Point2D(random.nextDouble() * size.getWidth(), 0)));
		}
	}

	public void addBullet(Bullet bullet) {
		if (bullet.getDirection() == Bullet.BulletDirection.DOWN) devilBullets.add(bullet);
		else playerBullets.add(bullet);
	}

	public void increaseScore(int points) {
		this.points += points;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Dimension2D getSize() {
		return size;
	}

	public PlayerShip getPlayerShip() {
		return playerShip;
	}

	public boolean isRunning() {
		return running;
	}

	public List<Devil> getDevils() {
		return devils;
	}

	public GameOutcome getGameOutcome() {
		return gameOutcome;
	}

	public List<Bullet> getPlayerBullets() {
		return playerBullets;
	}

	public List<Bullet> getDevilBullets() {
		return devilBullets;
	}

	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	public int getPointsToNextLife() {
		return pointsToNextLife;
	}

	public long getTimePlayed() {
		return (timePlayed + sessionTimePlayed) / 1000;
	}

	public double getKillsPerSecond() {
		return killsPerSecond;
	}
}
