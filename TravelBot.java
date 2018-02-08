package robotsimulator;

import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class TravelBot extends Robot implements Serializable {

	/**
	 * Author R J Fielding
	 */

	private static final long serialVersionUID = 269630113092468100L;
	protected double rAngle, rSpeed; // angle and speed of travel

	/**
	 * Create TravelBot
	 * 
	 * @param ix
	 *            x position
	 * @param iy
	 *            y position
	 * @param ir
	 *            radius
	 * @param ia
	 *            angle
	 * @param is
	 *            speed
	 */
	public TravelBot(int ix, int iy, double ir, int ia, int is) {
		super(ix, iy, ir);
		rAngle = ia;
		rSpeed = is;
	}

	/**
	 * Create a line of information about the robot detailing ID, position and
	 * speed.
	 */
	protected String getStrType() {
		return "TravelBot ID: " + robotID + " x: " + x + " y: " + y
				+ " Speed: " + rSpeed;
	}

	/**
	 * Return string of converted information regarding robot ID, position and
	 * speed.
	 */
	public String toString() {
		return getStrType();
	}

	/**
	 * This function details how the robot will move. If a robot is either
	 * hitting another robot, or if a robot has reached the boundary of the
	 * arena it will alter the angle it is travelling at by 90 degrees.
	 */
	@Override
	public void adjustRobot(RobotArena arena) {
		int arenax = arena.getXSize();
		int arenay = arena.getYSize();

		// robot collisions
		int numCollisionsCtr = 0; // counter for number of collisions
		for (Robot r : arena.getAllRobots()) { // for each robot in the arena
			if (new BoxCollider(r.x, this.x, r.y, this.y, r.rad, this.rad,
					r.rad, this.rad).checkCollision()) { // check collisions
				numCollisionsCtr++; // increment counter
			}
		}

		// arena boundary collision
		if (y <= 0 + rad + rSpeed || y >= arenay - rad - rSpeed
				|| x <= 0 + rad + rSpeed || x >= arenax - rad - rSpeed
				|| numCollisionsCtr > 1) {
			// turn 90 deg clockwise if hitting a wall
		
			x -= rSpeed * Math.cos(getAngleRad()); 
			y -= rSpeed * Math.sin(getAngleRad()); // back up when hitting wall
			
			rAngle += 90;
		}

		x += rSpeed * Math.cos(getAngleRad()); // change in x position
		y += rSpeed * Math.sin(getAngleRad()); // change in y position
	}

	/**
	 * Function to return angle as degrees
	 * 
	 * @return rAngle
	 */

	public double getAngleDeg() {
		return rAngle;
	}

	/**
	 * Function to return angle as radians
	 * 
	 * @return rAngle as radian
	 */

	public double getAngleRad() {
		return rAngle * (Math.PI / 180);
	}

	/**
	 * Function to draw the robot in the arena. Initially this colours in a
	 * circle set to the size of the radius of the robot *2. The wheels of the
	 * robot are then drawn using trigonometry to calculate the position of the
	 * wheels at all times in relation to the x/y position of the robot and the
	 * angle it is travelling.
	 */
	@Override
	public void drawRobot(RobotInterface ri) {

		GraphicsContext gc = ri.getgc();
		double angle = getAngleRad();
		gc.setFill(ri.colFromChar(col)); // set the fill colour
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND); // fill
																				// circle
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(4);

		// draw the right wheel on the robot
		gc.strokeLine(
				x + (rad * Math.cos(angle + Math.PI / 2))
						+ (rad * Math.cos(angle)),
				y + (rad * Math.sin(angle + Math.PI / 2))
						+ (rad * Math.sin(angle)),
				x + (rad * Math.cos(angle + Math.PI / 2))
						+ (rad * Math.cos(angle + Math.PI)),
				y + (rad * Math.sin(angle + Math.PI / 2))
						+ (rad * Math.sin(angle + Math.PI)));

		// draw the left wheel on the robot
		gc.strokeLine(
				x + (rad * Math.cos(angle - Math.PI / 2))
						+ (rad * Math.cos(angle)),
				y + (rad * Math.sin(angle - Math.PI / 2))
						+ (rad * Math.sin(angle)),
				x + (rad * Math.cos(angle - Math.PI / 2))
						+ (rad * Math.cos(angle + Math.PI)),
				y + (rad * Math.sin(angle - Math.PI / 2))
						+ (rad * Math.sin(angle + Math.PI)));

	}

}