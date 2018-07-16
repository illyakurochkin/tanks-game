package com.kurochkin.illya;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Level {
	public static final List<Level> levels = new ArrayList<>();
	public static final int LEVEL_INTERVAL = 6_000;

	public static void main(String[] args) {
		// Scanner scanner = new Scanner(System.in);
		// while (scanner.hasNextLine()) {
		// scanner.next();
		// int timeInterval = scanner.nextInt();
		// String info = scanner.nextLine().trim();
		// List<Aim> currentAims = new ArrayList<>();
		// while (scanner.hasNext()) {
		// String type = scanner.next();
		// if ("".equals(type)) {
		// break;
		// } else {
		// double speed = scanner.nextDouble();
		// int health = scanner.nextInt();
		// if ("t".equals(type)) {
		// currentAims.add(new Tank(speed, health));
		// } else if ("a".equals(type)) {
		// currentAims.add(new Ambulance(speed, health));
		// }
		// scanner.nextLine();
		// }
		// }
		// }
		// scanner.close();
	}

	public static void createLevelsFromFile(Game program, Set<Aim> aims) {
		System.out.println("createLevelsFromFile");
		try {
			Scanner scanner = new Scanner(new File("levels"));
			scanner.next();
			while (scanner.hasNextLine()) {
				System.out.println("while(scanner.hasNextLine()");
				
				int timeInterval = scanner.nextInt();
				String info = scanner.nextLine().trim();
				List<Aim> currentAims = new ArrayList<>();
				while (scanner.hasNext()) {
					System.out.println("while(scanner.hasNext()");
					String type = scanner.next();
					System.out.println(type + "----------------");
					if ("l".equals(type.trim())) {
						System.out.println("empty");
						break;
					} else {
						double speed = scanner.nextDouble();
						System.out.println("speed = " + speed);
						int health = scanner.nextInt();
						System.out.println(type + speed + health);
						if ("t".equals(type)) {
							System.out.println("t");
							currentAims.add(new Tank(speed, health));
						} else if ("a".equals(type)) {
							System.out.println("a");
							currentAims.add(new Ambulance(speed, health));
						}
						//scanner.nextLine();
					}
				}
				levels.add(new Level(timeInterval, info, currentAims));
			}
			scanner.close();
			System.out.println("level size : " + levels.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void createLevels(Game program, Set<Aim> aims) {
		levels.clear();
		Level.program = program;
		Level.aims = aims;

		createLevelsFromFile(program, aims);

		// levels.add(new Level(5, 1500, 1.5, 1));
		// levels.add(new Level(5, 1500, 1.5, 1));
		// levels.add(new Level(5, 1500, 1.5, 1));
		// levels.add(new Level(3, 1, false));
		// levels.add(new Level(1500, Arrays.asList(new Tank(2, 1), new Tank(2, 1), new
		// Tank(2, 1), new Ambulance(4, 1),
		// new Tank(2, 1), new Tank(2, 1))));
		// levels.add(new Level(new Ambulance(1, 1)));
		// levels.add(new Level(10, 1800, 1, 1));
		// levels.add(new Level(10, 1500, 1, 1));
		// levels.add(new Level(15, 1500, 1, 1));
		// levels.add(new Level(5, 2000, 1.2, 2));
		// levels.add(new Level(10, 2000, 1.2, 2));
		// levels.add(new Level(200, 2000, 1, 1));
	}

	public final int id;
	private final int timeInterval;
	private List<Aim> aimsInWave;
	private final String info;

	public static Game program;
	public static Set<Aim> aims;

	// public Level(int aimsCount, int timeInterval, Supplier<Aim> aimFactory) {
	// id = levels.size() + 1;
	// this.timeInterval = timeInterval;
	// aimsInWave =
	// Stream.generate(aimFactory).limit(aimsCount).collect(Collectors.toList());
	// }
	//
	// public Level(int aimsCount, int timeInterval, Aim aim) {
	// this(aimsCount, timeInterval, aim::getCopy);
	// }
	//
	// public Level(int aimsCount, int timeInterval, double speed, int health) {
	// this(aimsCount, timeInterval, () -> new Tank(speed, health));
	// }
	//
	// public Level(double speed, int health, boolean isTank) {
	// this(1, 0, () -> isTank ? new Tank(speed, health) : new Ambulance(speed,
	// health));
	// }

	public Level(int timeInterval, String info, List<Aim> aimsInWave) {
		id = levels.size() + 1;
		this.timeInterval = timeInterval;
		this.info = info;
		this.aimsInWave = aimsInWave;
	}

	// public Level(Aim aim) {
	// this(1, 0, aim::getCopy);
	// }

	public void startLevel() {
		try {
			LevelMessage levelInfo = new LevelMessage(this);
			program.add(levelInfo);
			levelInfo.sendToFront();
			Thread.sleep(2000);
			program.remove(levelInfo);
			Thread.sleep(LEVEL_INTERVAL);

			for (Aim aim : aimsInWave) {
				program.add(aim);
				aims.add(aim);
				Thread.sleep(timeInterval);
			}

			while (true) {
				aimsInWave = aimsInWave.stream().filter(a -> a instanceof Tank ? a.getHealth() > 0 : a.isVisible())
						.collect(Collectors.toList());
				if (aimsInWave.size() == 0) {
					break;
				}
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	public String info() {
		return info;
	}
}
