package com.kurochkin.illya;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Ambulance extends Aim {

	private static Image AMBULANCE_IMAGE;
	static {
		try {
			AMBULANCE_IMAGE = ImageIO.read(new File("images/ambulance-image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// constructor
	public Ambulance(double speed, int health) {
		super(AMBULANCE_IMAGE, speed, health);
	}

	@Override
	public Ambulance getCopy() {
		return new Ambulance(getSpeed(), getHealth());
	}
}
