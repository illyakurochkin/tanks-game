package com.kurochkin.illya;

import java.awt.Image;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Tank extends Aim {

	// constructor input tank speed and tank health
	public Tank(double speed, int health) {
		super(getRandomAimImage(), speed, health);
	}

	// instance of Random , use when make new aims (random images)
	private static Random random = new Random();

	// tank images
	private static Image TANK_IMAGE_1;
	private static Image TANK_IMAGE_2;
	private static Image TANK_IMAGE_3;
	private static Image TANK_IMAGE_4;

	// initialize images
	static {
		try {
			TANK_IMAGE_1 = ImageIO.read(new File("images/tank-image1.png"));
			TANK_IMAGE_2 = ImageIO.read(new File("images/tank-image2.png"));
			TANK_IMAGE_3 = ImageIO.read(new File("images/tank-image3.png"));
			TANK_IMAGE_4 = ImageIO.read(new File("images/tank-image4.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// returns random image for aim using static Random instance
	private static Image getRandomAimImage() {
		int randomInt = random.nextInt(4) + 1;
		return randomInt == 1 ? TANK_IMAGE_1
				: randomInt == 2 ? TANK_IMAGE_2 : randomInt == 3 ? TANK_IMAGE_3 : TANK_IMAGE_4;
	}
	
	@Override
	public Tank getCopy() {
		return new Tank(getSpeed(), getHealth());
	}
}
