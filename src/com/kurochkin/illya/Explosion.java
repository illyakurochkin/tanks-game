package com.kurochkin.illya;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import acm.graphics.GImage;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

@SuppressWarnings("serial")
public class Explosion extends GImage {

	public static final int TIME = 120;

	// image from 'images/explosion-image.png'
	public static Image EXPLOSION_IMAGE;

	// initialize EXPLOSION_IMAGE
	static {
		try {
			EXPLOSION_IMAGE = ImageIO.read(new File("images/explosion-image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// constructor [input Aim aim - the aim witch is blowing up]
	public Explosion(Aim aim) {
		super(EXPLOSION_IMAGE, aim.getX(), 0);
		double k = aim.getWidth() / getWidth();
		setSize(k * getWidth(), k * getHeight());
		move(0, Game.WINDOW_HEIGHT - getHeight());
		playBackgroundSound();
	}

	// starts playing sound using javazoom library
	// plays sound from 'sounds/explosion-sound.mp3'
	private void playBackgroundSound() {
		new Thread() {
			public void run() {
				try {
					AdvancedPlayer player = new AdvancedPlayer(new FileInputStream("sounds/explosion-sound.mp3"));
					player.play();
				} catch (JavaLayerException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
