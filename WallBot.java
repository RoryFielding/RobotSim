package robotsimulator;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WallBot extends Robot implements Serializable {

	/**
	 * Author R J Fielding
	 */
	private static final long serialVersionUID = 7585866811824711679L;

	/**
	 * Create TravelBot
	 * 
	 * @param ix
	 *            x position
	 * @param iy
	 *            y position
	 * @param ir
	 *            radius
	 */
	public WallBot(int ix, int iy, double ir) {
		super(ix, iy, ir);
	}

	/**
	 * This function will draw the robot in relation to the robot interface. A
	 * vertical line will be drawn from the x and y position on the y axis at a
	 * length of 4 times the radius of the robot.
	 */
	@Override
	public void drawRobot(RobotInterface ri) {
		GraphicsContext gc = ri.getgc();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(4);

		// draw the line for the wall
		gc.strokeLine(x, (y - rad * 4), x, (y + rad * 4));

	}

	/**
	 * This function will create a sentence with information regarding the robot
	 */
	protected String getStrType() {
		return "WallBot ID: " + robotID + " x: " + x + " y: " + y;
	}

	/**
	 * Abstract method carried but unimplemented
	 */
	@Override
	protected void adjustRobot(RobotArena r) {
	}
}
