package com.kurochkin.illya;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import acm.graphics.GImage;

@SuppressWarnings("serial")
public class Bomb extends GImage implements Updatable {

	// bomb's width
	public static final int BOMB_WIDTH = 15;

	// bomb's speed
	public static final double BOMB_X_SPEED = 1;
	public static final double BOMB_Y_SPEED = 5;

	// image from 'images/bomb-image.png'
	public static Image BOMB_IMAGE;

	// initialize BOMB_IMAGE
	static {
		try {
			BOMB_IMAGE = ImageIO.read(new File("images/bomb-image.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	private final double xSpeed;
	private final double ySpeed;

	public final int damage;

	// constructor [input double x - start x position of bomb]
	public Bomb(Fighter fighter) {
		this(fighter, 1, BOMB_X_SPEED, BOMB_Y_SPEED);
	}

	public Bomb(Fighter fighter, int damage, double xSpeed, double ySpeed) {
		super(BOMB_IMAGE, fighter.getX() + fighter.getWidth() / 2, fighter.getY() + fighter.getHeight() / 2);
		this.damage = damage;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		double k = BOMB_WIDTH / getWidth();
		setSize(k * getWidth(), k * getHeight());
	}

	// updatable interface method moves bomb down
	@Override
	public void update() {
		move(-xSpeed, ySpeed);
		sendToBack();
		sendForward();
	}

	// returns true is bomb is on screen
	@Override
	public boolean isVisible() {
		return getY() + getHeight() <= Game.WINDOW_HEIGHT;
	}

	// input Set<Aim> aims - set of all aims
	// returns Set<Aim> - set of blowed up aims (because of this bomb)
	public Aim isSucceed(Set<Aim> aims) {
		for (Aim aim : aims) {
			if (getY() + getHeight() >= aim.getY() && ((getX() <= aim.getX() && getX() + getWidth() >= aim.getX())
					|| (getX() <= aim.getX() + aim.getWidth() && getX() + getWidth() >= aim.getX() + aim.getWidth())
					|| (getX() >= aim.getX() && getX() + getWidth() <= aim.getX() + aim.getWidth()))) {
				return aim;
			}
		}
		return null;
	}

}
