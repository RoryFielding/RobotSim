package robotsimulator;

import java.io.Serializable;

public abstract class Robot implements Serializable {

	/**
	 * Author R J FIelding
	 */

	private static final long serialVersionUID = 7050240088115437806L;
	protected int x, y;
	protected double rad; // position and size of bot
	protected char col; // used to set color
	protected static int robotCtr; // each ball must have unique id to increment
	protected int robotID; // unique id for robot

	/**
	 * Constructor for Robot. Colour is automatically set to red. Each time a
	 * robot is created the robot counter is increased.
	 * 
	 * @param ix
	 *            x position of the robot
	 * @param iy
	 *            y position of the robot
	 * @param ir
	 *            radius of the robot
	 */

	Robot(int ix, int iy, double ir) {
		x = ix;
		y = iy;
		rad = ir;
		robotID = robotCtr++;
		col = 'r';
	}

	/**
	 * Abstract method for drawing a robot
	 * 
	 * @param ri
	 *            Robot Interface
	 */
	public abstract void drawRobot(RobotInterface ri);

	/**
	 * Function for creating a string giving the robot's details
	 * 
	 * @return Information in a string
	 */
	protected String getStrType() {
		return "RobotID: " + robotID + "x: " + x + "y: " + y;
	}

	/**
	 * This converts the information to a string using the toString function.
	 */
	public String toString() {
		return getStrType();
	}

	/**
	 * abstract method for adjusting a robot in the arena
	 * @param r RobotArena
	 */
	protected abstract void adjustRobot(RobotArena r);

	/**
	 * return x position
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * return y position
	 * 
	 * @return y position of robot
	 */
	public int getY() {
		return y;
	}

	/**
	 * return radius of robot
	 * 
	 * @return radius of robot
	 */
	public double getRad() {
		return rad;
	}

	/**
	 * set the robot at position nx,ny
	 * 
	 * @param xx x position
	 * @param yy y position
	 */
	public void setXY(int xx, int yy) {
		x = xx;
		y = yy;
	}

	/**
	 * return the id of robot
	 * 
	 * @return robotID
	 */
	public int getID() {
		return robotID;
	}
}
