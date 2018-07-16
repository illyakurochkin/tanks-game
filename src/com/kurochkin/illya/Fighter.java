package com.kurochkin.illya;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import acm.graphics.GImage;

@SuppressWarnings("serial")
public class Fighter extends GImage {

	private static Image FIGHTER_IMAGE;
	static {
		try {
			FIGHTER_IMAGE = ImageIO.read(new File("images/fighter-image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final int FIGHTER_SIZE = 100;
	public static final int FIGHTER_Y = 40;

	public Fighter(Game game) {
		super(FIGHTER_IMAGE, Game.WINDOW_WIDTH / 2 - FIGHTER_SIZE / 2, FIGHTER_Y);
		setSize(FIGHTER_SIZE, FIGHTER_SIZE);

	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2d);
	}
}
