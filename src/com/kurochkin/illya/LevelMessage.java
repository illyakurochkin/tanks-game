package com.kurochkin.illya;

import java.awt.Color;
import java.awt.Graphics;

import acm.graphics.GLabel;
import acm.graphics.GRect;

@SuppressWarnings("serial")
public class LevelMessage extends GRect {
	private static final String FONT = "Gadugi-";// Condara
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 200);
	private static final Color TEXT_COLOR = new Color(200, 200, 200, 200);
	private static final int LEVEL_FONT_SIZE = 80;
	private static final int INFO_FONT_SIZE = 50;

	private static final String LEVEL_TEXT = "L E V E L  -  ";

	private final GLabel idLabel;
	private final GLabel infoLabel;

	public LevelMessage(Level level) {
		super(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
		setFilled(true);
		setColor(TEXT_COLOR);
		setFillColor(BACKGROUND_COLOR);

		idLabel = new GLabel(LEVEL_TEXT + level.id);
		idLabel.setColor(TEXT_COLOR);
		idLabel.setFont(FONT + LEVEL_FONT_SIZE);
		idLabel.setLocation(Game.WINDOW_WIDTH / 2 - idLabel.getWidth() / 2, Game.WINDOW_HEIGHT / 3);

		infoLabel = new GLabel(level.info());
		infoLabel.setColor(TEXT_COLOR);
		infoLabel.setFont(FONT + INFO_FONT_SIZE);
		infoLabel.setLocation(Game.WINDOW_WIDTH / 2 - infoLabel.getWidth() / 2,
				idLabel.getY() + 1.5 * idLabel.getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		idLabel.paint(g);
		infoLabel.paint(g);
	}

}
