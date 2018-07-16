package com.kurochkin.illya;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class Game extends GraphicsProgram {
	public static final int WINDOW_WIDTH = 1000;
	public static final int WINDOW_HEIGHT = 562;

	public static final File BEST_SCORE_FILE = new File("best-score-file.txt");

	private MouseMotionAdapter motionListener = new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent event) {
			fighter.setLocation(event.getX() - Fighter.FIGHTER_SIZE / 2, (int) fighter.getY()
			/* event.getY() */);
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			fighter.setLocation(event.getX() - Fighter.FIGHTER_SIZE / 2, (int) fighter.getY()
			/* event.getY() */);
		}
	};

	private MouseAdapter mouseListener = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			Bomb bomb = new Bomb(fighter);
			add(bomb);
			bombs.add(bomb);
		}
	};

	@Override
	public void init() {
		setSize(WINDOW_WIDTH + 17, WINDOW_HEIGHT + 63);
		setTitle("FIGHTER GAME @illyakurochkin");

		GImage background = new GImage("images/background.jpg");
		background.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		add(background);

		mouseController = new GRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		mouseController.setColor(new Color(60, 60, 100));
		add(mouseController);

		score = new Score(0);
		add(score);
		score.sendToBack();
		score.sendForward();

		mouseController.addMouseListener(mouseListener);
		mouseController.addMouseMotionListener(motionListener);

		clouds = Collections.newSetFromMap(new ConcurrentHashMap<>());
		aims = Collections.newSetFromMap(new ConcurrentHashMap<>());
		bombs = Collections.newSetFromMap(new ConcurrentHashMap<>());
		fighter = new Fighter(this);

		points = 0;
		isPlaying = false;

		bestScore = getBestScore();
	}

	private GRect mouseController = new GRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	private Score score;

	private Set<Cloud> clouds;
	private Set<Aim> aims;
	private Set<Bomb> bombs;
	private Fighter fighter;

	private int points;
	public boolean isPlaying;

	public static int bestScore = 0;

	public void startPlaying() {
		start();
		play();
	}

	public void play() {
		this.add(fighter);

		isPlaying = true;

		Aim.allSpeed = Aim.AIM_SPEED;

		startMakingAims();
		startMakingClouds();

		new Thread(() -> {
			while (isPlaying) {
				updateElements();
				check();
				try {
					Thread.sleep(8);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void startMakingAims() {
		new Thread(() -> {
			Level.createLevels(this, aims);
			for (Level temp : Level.levels) {
				temp.startLevel();
				if (!isPlaying) {
					break;
				}
			}
		}).start();
	}

	private void startMakingClouds() {
		new Thread(() -> {
			try {
				while (isPlaying) {

					Cloud cloud = new Cloud();
					this.add(cloud);
					clouds.add(cloud);

					cloud.sendBackward();
					Thread.sleep(Cloud.getCloudInterval());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void check() {

		for (Bomb bomb : bombs) {

			Aim succeed = bomb.isSucceed(aims);

			if (succeed != null) {

				succeed.damageAim(bomb.damage);
				remove(bomb);
				bombs.remove(bomb);

				new Thread(() -> {
					try {

						Explosion explosion = succeed.blowUp();
						add(explosion);
						Thread.sleep(Explosion.TIME);
						remove(explosion);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}).start();

				if (succeed.getHealth() <= 0) {

					this.remove(succeed);
					aims.remove(succeed);

					if (succeed instanceof Ambulance) {
						gameOver();
						return;
					}
				}

				if (succeed instanceof Tank) {
					score.setLabel(++points + "");
				}
			}
		}
	}

	public void restart() {

		removeAll();
		init();
		play();

	}

	private void saveBestScore() {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(BEST_SCORE_FILE));

			writer.write(bestScore + "");

			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getBestScore() {

		try {

			Scanner scanner = new Scanner(BEST_SCORE_FILE);
			int result = scanner.hasNextInt() ? scanner.nextInt() : 0;
			scanner.close();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

	}

	private void gameOver() {

		isPlaying = false;

		if (points > bestScore) {
			bestScore = points;
			saveBestScore();
		}

		mouseController.removeMouseListener(mouseListener);
		mouseController.removeMouseMotionListener(motionListener);

		GameOverMessage gameOverRect = new GameOverMessage(points);

		gameOverRect.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent event) {
				gameOverRect.removeMouseListener(this);
				restart();
			}
		});

		add(gameOverRect);

	}

	private void updateElements() {

		if (aims.stream().filter(a -> !a.isVisible() && a instanceof Tank).count() > 0) {
			gameOver();
		} else {

			clouds = clouds.stream().filter(Cloud::isVisible)
					.collect(Collectors.toCollection(() -> Collections.newSetFromMap(new ConcurrentHashMap<>())));
			bombs = bombs.stream().filter(Bomb::isVisible)
					.collect(Collectors.toCollection(() -> Collections.newSetFromMap(new ConcurrentHashMap<>())));

			aims.forEach(Aim::update);
			clouds.forEach(Cloud::update);
			bombs.forEach(Bomb::update);

		}
	}
}