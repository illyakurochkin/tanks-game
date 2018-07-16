package com.kurochkin.illya;

import java.awt.Color;
import java.awt.Graphics;

import acm.graphics.GLabel;
import acm.graphics.GRect;

@SuppressWarnings("serial")
public class GameOverMessage extends GRect {

	private GLabel gameOverLabel;
	private GLabel pointsLabel;

	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 200);
	private static final Color TEXT_COLOR = new Color(200, 200, 200, 200);
	private static final String GAME_OVER_TEXT = "G A M E    O V E R";
	private static final int GAME_OVER_FONT_SIZE = 80;
	private static final int POINTS_FONT_SIZE = 50;
	private static final String POINTS_TEXT = "points : ";
	private static final String BEST_TEXT = " (best: ";
	private static final String FONT = "Gadugi-";

	public GameOverMessage(int points) {
		super(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);

		setColor(TEXT_COLOR);
		gameOverLabel = new GLabel(GAME_OVER_TEXT);
		gameOverLabel.setColor(TEXT_COLOR);
		gameOverLabel.setFont(FONT + GAME_OVER_FONT_SIZE);
		gameOverLabel.setLocation(Game.WINDOW_WIDTH / 2 - gameOverLabel.getWidth() / 2, Game.WINDOW_HEIGHT / 3);

		pointsLabel = new GLabel(POINTS_TEXT + points + BEST_TEXT + Game.bestScore + ")");
		pointsLabel.setColor(TEXT_COLOR);
		pointsLabel.setFont(FONT + POINTS_FONT_SIZE);
		pointsLabel.setLocation(Game.WINDOW_WIDTH / 2 - pointsLabel.getWidth() / 2,
				gameOverLabel.getY() + 1.5 * gameOverLabel.getHeight());

		setFilled(true);
		setFillColor(BACKGROUND_COLOR);
	}

	@Override
	public void paint(Graphics g2d) {
		// Graphics2D g2d = (Graphics2D) g;
		// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2d);
		gameOverLabel.paint(g2d);
		pointsLabel.paint(g2d);
	}

}
