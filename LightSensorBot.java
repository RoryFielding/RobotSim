package robotsimulator;

public class LightSensorBot extends TravelBot {

	/**
	 * Author R J Fielding
	 */
	private static final long serialVersionUID = -2080308645508674361L;
	private double lastLight;

	/**
	 * Create LightSensorBot
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
	public LightSensorBot(int ix, int iy, double ir, int ia, int is) {
		super(ix, iy, ir, ia, is);
		col = 'b';
		lastLight = 0;
	}

	/**
	 * This function will adjust the robot in relation to the robot arena. If
	 * the robot collides with another robot, or the boundaries of the arena it
	 * will make a 90 degree turn. Else, this will move according to whether the
	 * robot has moved to a lighter or darker location. The current light value,
	 * and the previous light value are stored, which are calculated by
	 * considering the sum of the squared difference in distance from each
	 * light. If the robot moves to a location that is brighter (closer to the
	 * light) it will turn 5 degrees, causing the robot to circle round a light
	 * source.
	 */
	@Override
	public void adjustRobot(RobotArena arena) {
		int arenax = arena.getXSize();
		int arenay = arena.getYSize();

		// robot collisions
		int numCollisionsCtr = 0; // counter for number of collisions
		for (Robot r : arena.getAllRobots()) { // for each robot in the arena
			if (new BoxCollider(r.getX(), this.x, r.getY(), this.y, r.getRad(),
					this.rad, r.getRad(), this.rad).checkCollision()) { // check
																		// collisions
				numCollisionsCtr++; // increment counter
			}
		}

		// arena boundary collision
		if (y <= 0 + rad + rSpeed || y >= arenay - rad - rSpeed
				|| x <= 0 + rad + rSpeed || x >= arenax - rad - rSpeed
				|| numCollisionsCtr > 1) {
			// turn 90 deg clockwise if hitting a wall
			x -= rSpeed * Math.cos(getAngleRad()); // back up when hiting wall
			y -= rSpeed * Math.sin(getAngleRad()); // back up when hitting wall

			rAngle += 90;
		} else {
			double lightValue = 0;
			for (Robot r : arena.getAllRobots()) { // calculate sum of squared
													// differences between
													// LightBots
				if (r instanceof LightBot) {
					lightValue += Math.pow(Math.pow(this.x - r.getX(), 2)
							+ Math.pow(this.y - r.getY(), 2), -0.5); // 1 over
																		// the
																		// distance
																		// to
																		// each
																		// light
				}
			}
			if (lightValue < lastLight) { // if current light value is less than
											// last light value
				rAngle += 5; // turn right 5 degrees
			}
			lastLight = lightValue; // update light value
		}
		x += rSpeed * Math.cos(getAngleRad()); // change in x position
		y += rSpeed * Math.sin(getAngleRad()); // change in y position
	}

	/**
	 * This function will create a sentence with information regarding the robot
	 */
	protected String getStrType() {
		return "LightSensorBot ID: " + robotID + " x: " + x + " y: " + y
				+ " Speed: " + rSpeed;
	}

}
