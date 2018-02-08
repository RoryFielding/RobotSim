package robotsimulator;

public class LineBoxCollider {

	/**
	 * Author R J Fielding
	 */

	private double boxx;
	private double boxy;
	private double boxwidth;
	private double boxheight;
	private double linestartx;
	private double lineendx;
	private double linestarty;
	private double lineendy;

	/**
	 * Constructor for the LineBoxCollider
	 * 
	 * @param boxx
	 *            box x position start
	 * @param boxy
	 *            box y position start
	 * @param boxwidth
	 *            width of the box
	 * @param boxheight
	 *            height of the box
	 * @param linestartx
	 *            start x position of the line
	 * @param lineendx
	 *            end x position of the line
	 * @param linestarty
	 *            start y position of the line
	 * @param lineendy
	 *            end y position of the line
	 */

	public LineBoxCollider(double boxx, double boxy, double boxwidth,
			double boxheight, double linestartx, double lineendx,
			double linestarty, double lineendy) {
		this.boxx = boxx;
		this.boxy = boxy;
		this.boxwidth = boxwidth;
		this.boxheight = boxheight;
		this.linestartx = linestartx;
		this.lineendx = lineendx;
		this.linestarty = linestarty;
		this.lineendy = lineendy;
	}

	/**
	 * Function for checking if there has been a collision between a line and a
	 * box. Create an array of doubles for the box positions, if a line
	 * collision occurs on this box
	 * 
	 * @return true if a collision has occurred, else return false.
	 */

	public boolean CheckCollision() {
		// NESW
		double[] boxstartxs = new double[] { boxx - boxwidth, boxx + boxwidth,
				boxx + boxwidth, boxx - boxwidth };
		double[] boxendxs = new double[] { boxx + boxwidth, boxx + boxwidth,
				boxx - boxwidth, boxx - boxwidth };
		double[] boxstartys = new double[] { boxy - boxheight,
				boxy - boxheight, boxy + boxheight, boxy + boxheight };
		double[] boxendys = new double[] { boxy - boxheight, boxy + boxheight,
				boxy + boxheight, boxy - boxheight };

		for (int i = 0; i < 4; i++) {
			double boxlinestartx = boxstartxs[i];
			double boxlinestarty = boxstartys[i];
			double boxlineendx = boxendxs[i];
			double boxlineendy = boxendys[i];

			if ((new LineCollider(linestartx, lineendx, linestarty, lineendy,
					boxlinestartx, boxlineendx, boxlinestarty, boxlineendy))
					.CheckCollision()) {
				return true;
			}
		}

		return false;
	}
}