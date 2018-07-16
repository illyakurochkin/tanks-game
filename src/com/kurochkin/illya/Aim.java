package com.kurochkin.illya;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import acm.graphics.GImage;

@SuppressWarnings("serial")
public abstract class Aim extends GImage implements Updatable {

	// aim width and height equals AIM_SIZE
	public static final int AIM_WIDTH = 100;
	public static final double AIM_SPEED = 2;

	// private static int getRandom
	public static double allSpeed = AIM_SPEED;

	public static void addToSpeed(double stap) {
		allSpeed += stap;
	}

	private int health = 1;
	private final double speed;

	public void damageAim(int damage) {
		health -= damage;
	}

	public int getHealth() {
		return health;
	}
	
	public double getSpeed() {
		return speed;
	}

	public Aim(Image image, double speed, int health) {
		super(image, Game.WINDOW_WIDTH + 1, Game.WINDOW_HEIGHT);
		this.speed = speed;
		this.health = health;
		double k = AIM_WIDTH / getWidth();
		setSize(k * getWidth(), k * getHeight());
		move(0, -getHeight());
	}

	// updatable interface implemented method, moves aim left
	@Override
	public void update() {
		move(-speed, 0);
	}

	// returns true when aim is on screen
	@Override
	public boolean isVisible() {
		return getX() + getWidth() >= 0;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2d);
	}

	// return new instance of Explosion with the same position
	public Explosion blowUp() {
		return new Explosion(this);
	}

	public abstract Aim getCopy();
}
