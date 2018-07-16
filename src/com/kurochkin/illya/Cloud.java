package com.kurochkin.illya;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import acm.graphics.GImage;

@SuppressWarnings("serial")
public class Cloud extends GImage implements Updatable {

	private static int LOWEST_LOCATION = 250;
	private static int HIGHEST_LOCATION = 5;

	private static int SMALLEST_WIDTH = 120;
	private static int BIGGEST_WIDTH = 200;

	private static double MAX_SPEED = 2;
	private static double MIN_SPEED = 0.5;

	private static int MIN_CLOUD_INTERVAL = 800;
	private static int MAX_CLOUD_INTERVAL = 5_000;

	private static Random random = new Random();
	private static Image CLOUD_IMAGE;
	static {
		try {
			CLOUD_IMAGE = ImageIO.read(new File("images/cloud-image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int getRandomYLocation() {
		return random.nextInt(-HIGHEST_LOCATION + LOWEST_LOCATION) + HIGHEST_LOCATION;
	}

	private static int getRandomWidth() {
		return random.nextInt(BIGGEST_WIDTH - SMALLEST_WIDTH) + SMALLEST_WIDTH;
	}

	private static double getRandomSpeed() {
		return random.nextDouble() * (MAX_SPEED - MIN_SPEED) + MIN_SPEED;
	}

	public static int getCloudInterval() {
		return random.nextInt(MAX_CLOUD_INTERVAL - MIN_CLOUD_INTERVAL) + MIN_CLOUD_INTERVAL;
	}

	private final double speed = getRandomSpeed();

	public Cloud() {
		super(CLOUD_IMAGE, Game.WINDOW_WIDTH + 1, getRandomYLocation());
		double k = getRandomWidth() / getWidth();
		setSize(k * getWidth(), k * getHeight());
	}

	@Override
	public boolean isVisible() {
		return getX() + getWidth() >= 0;
	}

	@Override
	public void update() {
		move(-speed, 0);
		//sendToFront();
	}

}
