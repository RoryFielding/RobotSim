package robotsimulator;

public class BoxCollider {

	/**
	 * Author R J Fielding
	 */

	private double botonex; // declare first robot to compare x
	private double bottwox; // declare second robot to compare x
	private double botoney; // declare first robot to compare y
	private double bottwoy; // declare second robot to compare y
	private double botonewidth; // declare first robot width
	private double bottwowidth; // declare second robot width
	private double botoneheight; // declare first robot height
	private double bottwoheight; // declare second robot height

	/**
	 * Constructor for the BoxCollider class
	 * 
	 * @param x
	 *            first box x position
	 * @param x2
	 *            second box x position
	 * @param y
	 *            first box y position
	 * @param y2
	 *            second box y position
	 * @param width
	 *            first box width
	 * @param width2
	 *            second box width
	 * @param height
	 *            first box height
	 * @param height2
	 *            second box height
	 */
	public BoxCollider(double x, double x2, double y, double y2, double width,
			double width2, double height, double height2) {
		botonex = x;
		bottwox = x2;
		botoney = y;
		bottwoy = y2;
		botonewidth = width;
		bottwowidth = width2;
		botoneheight = height;
		bottwoheight = height2;

	}

	/**
	 * Function to check the collision between two boxes.
	 * 
	 * @return True if there is a collision on both the x and y planes.
	 */

	public boolean checkCollision() {

		double leftx;
		double leftwidth;
		double rightx;
		double rightwidth;
		double topy;
		double topheight;
		double lowery;
		double lowerheight;

		if (botonex < bottwox) { // if first robot has a smaller x coord than
									// second
			leftx = botonex; // set left robot to botone
			rightx = bottwox; // set right robot to bottwo
			leftwidth = botonewidth;
			rightwidth = bottwowidth;
		} else { // invert
			leftx = bottwox;
			rightx = botonex;
			leftwidth = bottwowidth;
			rightwidth = botonewidth;
		}

		boolean xColl = leftx + leftwidth > rightx - rightwidth; // condition
																	// for
																	// collision

		if (botoney < bottwoy) { // if first robot has smaller y coord than
									// second
			topy = botoney; // set top y to first robot
			lowery = bottwoy; // set lower y to second robot
			topheight = botoneheight;
			lowerheight = bottwoheight;
		} else { // invert
			topy = bottwoy;
			lowery = botoney;
			topheight = bottwoheight;
			lowerheight = botoneheight;
		}

		boolean yColl = topy + topheight > lowery - lowerheight; // condition
																	// for
																	// collision

		return xColl && yColl; // if both cases are colliding return true
	}

}
