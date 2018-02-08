package robotsimulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class RobotArena implements Serializable {

	/**
	 * Author R J Fielding
	 */
	private static final long serialVersionUID = 5550090494392646761L;
	private int xSize;
	private int ySize;
	private ArrayList<Robot> allRobots;
	private int maxRobots;
	private double actualRobots;

	/**
	 * Constructor for the robot arena, initialises a new arraylist of robots,
	 * initialises the counter (actualRobots) and sets the maximum number of
	 * robots to 30
	 * 
	 * @param xS
	 *            X-Size of the robot arena
	 * @param yS
	 *            Y-Size of the robot arena
	 */
	RobotArena(int xS, int yS) {
		xSize = xS;
		ySize = yS;
		allRobots = new ArrayList<Robot>();
		actualRobots = 0;
		maxRobots = 20;
	}

	/**
	 * This function creates an arraylist of strings, then for each robot in the
	 * array of robots, their toString function will be added to this array
	 * which details their robot ID, x, y position and speed
	 * 
	 * @return string of robot names, co-ordinates, id and speed
	 */
	public ArrayList<String> describeAll() {
		ArrayList<String> ans = new ArrayList<String>(); // sets up empty
															// arraylist
		for (Robot r : allRobots)
			ans.add(r.toString()); // add each robot to string in list
		return ans; // return list
	}

	/**
	 * Function to draw the robot arena in reference to the robot interface
	 * 
	 * @param ri
	 *            Robot Interface
	 */
	public void drawArena(RobotInterface ri) { // draw robots in arena
		for (Robot r : allRobots) {
			r.drawRobot(ri);
		}
	}

	/**
	 * This function attempts to add a robot to the robot arena, as well as
	 * there is not another robot currently in this position and as long as this
	 * does not exceed the maxRobots value. If there is no robot colliding here,
	 * the robot will be added and the number of actual robots in the arena is
	 * incremented.
	 * 
	 * @param r
	 *            Robot
	 * @return True when a robot has been added successfully
	 */
	public boolean tryAddBot(Robot r) {
		for (Robot other : allRobots) {
			if (new BoxCollider(r.x, other.x, r.y, other.y, r.rad, other.rad,
					r.rad, other.rad).checkCollision()) {
				return false;
			}
			if (actualRobots >= maxRobots) {
				System.out.println("Too many robots!");
			}
		}
		allRobots.add(r);
		actualRobots++;
		return true;
	}

	/**
	 * Function to add a TravelBot to the robot arena. Its x and y position are
	 * firstly randomised, within the arena bounds, the speed is randomised
	 * between 2-7 and the angle it is travelling at is randomised. A new bot
	 * with random characteristsics will be created until one which is not
	 * colliding with another robot can be found.
	 */
	public void addTravelBot() {
		Robot newBot;
		Random rng = new Random();
		do {
			int randomxInt = rng.nextInt(xSize - 60) + 30;
			int randomyInt = rng.nextInt(ySize - 60) + 30;
			int randomSpeed = rng.nextInt(1) + 2;
			int randomAngle = rng.nextInt();
			newBot = new TravelBot(randomxInt, randomyInt, 10, randomAngle,
					randomSpeed + 1); // add botone
		} while (!tryAddBot(newBot));
	}

	/**
	 * Function to add a WhiskerBot to the robot arena. Its x and y position are
	 * firstly randomised, within the arena bounds, the speed is randomised
	 * between 2-7 and the angle it is travelling at is randomised. The size of
	 * the whiskers are also then randomised. A new bot with random
	 * characteristsics will be created until one which is not colliding with
	 * another robot can be found.
	 */
	public void addWhiskerBot() {
		Robot newBot;
		Random rng = new Random();
		do {
			int randomxInt = rng.nextInt(xSize - 60) + 30;
			int randomyInt = rng.nextInt(ySize - 60) + 30;
			int randomSpeed = rng.nextInt(1) + 2;
			int randomAngle = rng.nextInt();
			int randomWhisker = rng.nextInt(15) + 20;
			newBot = new WhiskerBot(randomxInt, randomyInt, 10, randomAngle,
					randomSpeed + 1, randomWhisker);
		} while (!tryAddBot(newBot));
	}

	/**
	 * Function to add a WallBot to the robot arena. Its x and y position are
	 * firstly randomised, within the arena bounds. A new bot with random
	 * characteristsics will be created until one which is not colliding with
	 * another robot can be found.
	 */
	public void addWallBot() {
		Robot newBot;
		Random rng = new Random();
		do {
			int randomxInt = rng.nextInt(xSize - 60) + 30;
			int randomyInt = rng.nextInt(ySize - 60) + 30;
			newBot = new WallBot(randomxInt, randomyInt, 10); // add botone
		} while (!tryAddBot(newBot));
	}

	/**
	 * Function to add a LightBot to the robot arena. Its x and y position are
	 * firstly randomised, within the arena bounds. A new bot with random
	 * characteristsics will be created until one which is not colliding with
	 * another robot can be found.
	 */
	public void addLightBot() {
		Robot newBot;
		Random rng = new Random();
		do {
			int randomxInt = rng.nextInt(xSize - 60) + 30;
			int randomyInt = rng.nextInt(ySize - 60) + 30;
			newBot = new LightBot(randomxInt, randomyInt, 10); // add botone
		} while (!tryAddBot(newBot));
	}

	/**
	 * Function to add a LightSensorBot to the robot arena. Its x and y position
	 * are firstly randomised, within the arena bounds, the speed is randomised
	 * between 2-7 and the angle it is travelling at is randomised. A new bot
	 * with random characteristsics will be created until one which is not
	 * colliding with another robot can be found.
	 */
	public void addLightSensorBot() {
		Robot newBot;
		Random rng = new Random();
		do {
			int randomxInt = rng.nextInt(xSize - 60) + 30;
			int randomyInt = rng.nextInt(ySize - 60) + 30;
			int randomSpeed = rng.nextInt(1) + 2;
			int randomAngle = rng.nextInt();
			newBot = new LightSensorBot(randomxInt, randomyInt, 10,
					randomAngle, randomSpeed + 1); // add botone
		} while (!tryAddBot(newBot));
	}

	/**
	 * This function will adjust all robots. For each robot in the array, adjust
	 * them in context to this arena.
	 */
	public void adjustRobots() {
		for (Robot r : allRobots)
			r.adjustRobot(this);
	}

	/**
	 * Function to return ArrayList of robots
	 * 
	 * @return allRobots ArrayList
	 */

	public ArrayList<Robot> getAllRobots() {
		return allRobots;
	}

	/**
	 * Function to get the x size of the arena
	 * 
	 * @return xSize
	 */

	public int getXSize() {
		return xSize;
	}

	/**
	 * Function to set the x size of the arena
	 * 
	 * @param x
	 *            integer to set size
	 * @return xSize
	 */
	public int setXSize(int x) {
		xSize = x;
		return x;
	}

	/**
	 * Function to get the y size of the arena
	 * 
	 * @return ySize
	 */

	public int getYSize() {
		return ySize;
	}

	/**
	 * Function to set the y size of the arena
	 * 
	 * @param y
	 *            integer to set the size of arena
	 * @return ySize
	 */

	public int setYSize(int y) {
		ySize = y;
		return y;
	}

	/**
	 * Function to get the maximum number of robots in the arena
	 * 
	 * @return maxRobots maximum number of robots in the arena
	 */
	public int getMaxRobots() {
		return maxRobots;
	}

	/**
	 * Function to set the maximum number of robots in the arena
	 * 
	 * @param maxRobots
	 *            maximum number of robots in the arena
	 */
	public void setMaxRobots(int maxRobots) {
		this.maxRobots = maxRobots;
	}

}