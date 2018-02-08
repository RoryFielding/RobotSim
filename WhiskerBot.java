package robotsimulator;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WhiskerBot extends TravelBot implements Serializable {

	/**
	 * Author R J Fielding
	 */
	private static final long serialVersionUID = 3683070643359705933L;
	private int whiskersize;

	/**
	 * Create WhiskerBot
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
	 * @param ws
	 *            whisker length
	 */
	public WhiskerBot(int ix, int iy, double ir, int ia, int is, int ws) {
		super(ix, iy, ir, ia, is);
		whiskersize = ws;
		col = 'g';
	}

	/**
	 * Create a line of information about the robot detailing ID, position and
	 * speed.
	 */
	protected String getStrType() {
		return "WhiskerBot ID: " + robotID + " x: " + x + " y: " + y
				+ " Speed: " + rSpeed;
	}

	/**
	 * Function to draw the robot in relation to the robot interface. This
	 * initially calls the inherited method for the circle and wheels, however
	 * adds to this code drawing the whiskers on top of this robot.
	 */
	@Override
	public void drawRobot(RobotInterface ri) {
		super.drawRobot(ri);
		GraphicsContext gc = ri.getgc();
		double angle = getAngleRad();

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(4);

		// draw the right whisker on the robot
		gc.strokeLine(x + (rad * Math.cos(angle + Math.PI / 4)), y
				+ (rad * Math.sin(angle + Math.PI / 4)), x
				+ ((rad + whiskersize) * Math.cos(angle + Math.PI / 4)), y
				+ ((rad + whiskersize) * Math.sin(angle + Math.PI / 4)));

		// draw the left whisker on the robot
		gc.strokeLine(x + (rad * Math.cos(angle - Math.PI / 4)), y
				+ (rad * Math.sin(angle - Math.PI / 4)), x
				+ ((rad + whiskersize) * Math.cos(angle - Math.PI / 4)), y
				+ ((rad + whiskersize) * Math.sin(angle - Math.PI / 4)));

	}

	/**
	 * Function to adjust the robot in reference to the robot arena. This
	 * function firstly initialises the whiskers, the arena size and the angle
	 * of the robot. If the robot either hits a wall or has a box collision, it
	 * will make a 90 degree turn. If the robot's whiskers collide but the robot
	 * itself does not, the robot will adjust its angle by 3 degrees.
	 */

	@Override
	public void adjustRobot(RobotArena arena) {
		int arenax = arena.getXSize();
		int arenay = arena.getYSize();
		double angle = getAngleRad();

		double rwsx = x + (rad * Math.cos(angle + Math.PI / 4));
		double rwsy = y + (rad * Math.sin(angle + Math.PI / 4));
		double rwex = x + ((rad + whiskersize) * Math.cos(angle + Math.PI / 4));
		double rwey = y + ((rad + whiskersize) * Math.sin(angle + Math.PI / 4));

		double lwsx = x + (rad * Math.cos(angle - Math.PI / 4));
		double lwsy = y + (rad * Math.sin(angle - Math.PI / 4));
		double lwex = x + ((rad + whiskersize) * Math.cos(angle - Math.PI / 4));
		double lwey = y + ((rad + whiskersize) * Math.sin(angle - Math.PI / 4));

		// robot collisions
		int numCollisionsCtr = 0; // counter for number of collisions
		for (Robot r : arena.getAllRobots()) { // for each robot in the arena
			if (new BoxCollider(r.x, this.x, r.y, this.y, r.rad, this.rad,
					r.rad, this.rad).checkCollision()) { // check collisions
				numCollisionsCtr++; // increment counter
			}
			if (new LineBoxCollider(r.x, r.y, r.rad, r.rad, rwsx, rwex, rwsy,
					rwey).CheckCollision()) { // robot collision
				rAngle -= 3;
			}
			if (new LineBoxCollider(r.x, r.y, r.rad, r.rad, lwsx, lwex, lwsy,
					lwey).CheckCollision()) { // robot collision
				rAngle += 3;
			}
			if (new LineBoxCollider(0, 0, arenax, 5, lwsx, lwex, lwsy, lwey)
					.CheckCollision()) { // top border
				rAngle += 3;
			}
			if (new LineBoxCollider(0, 0, arenax, 5, rwsx, rwex, rwsy, rwey)
					.CheckCollision()) { // top border
				rAngle -= 3;
			}
			if (new LineBoxCollider(0, arenay, arenax, 5, lwsx, lwex, lwsy,
					lwey).CheckCollision()) { // bottom border
				rAngle += 3;
			}
			if (new LineBoxCollider(0, arenay, arenax, 5, rwsx, rwex, rwsy,
					rwey).CheckCollision()) { // bottom border
				rAngle -= 3;
			}
			if (new LineBoxCollider(0, arenay, 5, arenay, lwsx, lwex, lwsy,
					lwey).CheckCollision()) { // left border
				rAngle += 3;
			}
			if (new LineBoxCollider(0, arenay, 5, arenay, rwsx, rwex, rwsy,
					rwey).CheckCollision()) { // left border
				rAngle -= 3;
			}
			if (new LineBoxCollider(arenax, arenay, 5, arenay, lwsx, lwex,
					lwsy, lwey).CheckCollision()) { // right border
				rAngle += 3;
			}
			if (new LineBoxCollider(arenax, arenay, 5, arenay, rwsx, rwex,
					rwsy, rwey).CheckCollision()) { // right border
				rAngle -= 3;
			}

		}

		// arena boundary collision or robot collision
		if (y <= 0 + rad + rSpeed || y >= arenay - rad - rSpeed
				|| x <= 0 + rad + rSpeed || x >= arenax - rad - rSpeed
				|| numCollisionsCtr > 1) {
			// turn 90 deg clockwise if hitting a wall
			x -= rSpeed * Math.cos(getAngleRad()); // back up when hitting wall
			y -= rSpeed * Math.sin(getAngleRad()); // back up when hitting wall

			rAngle += 90;
		}

		x += rSpeed * Math.cos(getAngleRad()); // change in x position
		y += rSpeed * Math.sin(getAngleRad()); // change in y position
	}

}
