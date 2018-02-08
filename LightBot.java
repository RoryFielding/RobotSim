package robotsimulator;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class LightBot extends Robot implements Serializable {

	/**
	 * Author R J Fielding
	 */

	private static final long serialVersionUID = -4650332859682395808L;

	/**
	 * Constructor for the LightBot, setting the colour to yellow.
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param r
	 *            radius
	 */
	LightBot(int x, int y, int r) {
		super(x, y, r);
		col = 'y';

	}

	/**
	 * This function will create a sentence with information regarding the robot
	 */
	protected String getStrType() {
		return "LightBot ID: " + robotID + " x: " + x + " y: " + y;
	}

	/**
	 * Method used to draw the robot in relation to the robot interface. This
	 * fills a circle with the colour yellow, as initiated in the constructor.
	 */
	@Override
	public void drawRobot(RobotInterface ri) {
		GraphicsContext gc = ri.getgc();
		gc.setFill(ri.colFromChar(col)); // set the fill colour
		gc.fillArc(x - rad, y - rad, rad * 2, rad * 2, 0, 360, ArcType.ROUND); // fill
																				// circle
	}

	/**
	 * Abstract method carried but unimplemented
	 */
	@Override
	protected void adjustRobot(RobotArena r) {
	}

}
