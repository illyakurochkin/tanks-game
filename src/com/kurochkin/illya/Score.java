package com.kurochkin.illya;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import acm.graphics.GLabel;
import acm.graphics.GRoundRect;

@SuppressWarnings("serial")
public class Score extends GLabel {

	public static final int X_POSITION = 50;
	public static final int Y_POSITION = 50;

	private static final int MARGIN = 20;

	public static final String SCORE_TEXT = "score: ";
	private static final String FONT = "Gadugi-";
	private static final int FONT_SIZE = 30;

	private static final Color TEXT_COLOR = new Color(40, 50, 55);
	private static final Color BACKGROUND_COLOR = new Color(75, 115, 115);

	private GRoundRect border;

	public Score(int points) {
		super(SCORE_TEXT + points, X_POSITION + MARGIN, Y_POSITION + MARGIN);
		setFont(FONT + FONT_SIZE);

		border = new GRoundRect(X_POSITION, Y_POSITION - getHeight() + 10, getWidth() + 2 * MARGIN,
				getHeight() + 2 * MARGIN, getHeight() / 2, getHeight() / 2);
		border.setFilled(true);
		border.setFillColor(BACKGROUND_COLOR);

		border.setColor(BACKGROUND_COLOR);
		setColor(TEXT_COLOR);
	}
	
	@Override
	public void setLabel(String text) {
		super.setLabel(SCORE_TEXT + text);
		border.setSize(getWidth() + 2 * MARGIN, getHeight() + 2 * MARGIN);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		border.paint(g2d);
		g.setColor(TEXT_COLOR);
		super.paint(g2d);
	}
}
