package robotsimulator;

public class LineCollider {

	/**
	 * Author R J Fielding
	 */

	private double linestartx;
	private double lineendx;
	private double linestarty;
	private double lineendy;
	private double linestartx2;
	private double lineendx2;
	private double linestarty2;
	private double lineendy2;

	/**
	 * Constructor for the LineCollider
	 * 
	 * @param linestartx
	 *            starting x position of the first line
	 * @param lineendx
	 *            ending x position of the first line
	 * @param linestarty
	 *            starting y position of the first line
	 * @param lineendy
	 *            ending y position of the first line
	 * @param linestartx2
	 *            starting x position of the second line
	 * @param lineendx2
	 *            ending x position of the second line
	 * @param linestarty2
	 *            starting y position of the second line
	 * @param lineendy2
	 *            ending y position of the second line
	 */

	LineCollider(double linestartx, double lineendx, double linestarty,
			double lineendy, double linestartx2, double lineendx2,
			double linestarty2, double lineendy2) {
		this.linestartx = linestartx;
		this.lineendx = lineendx;
		this.linestarty = linestarty;
		this.lineendy = lineendy;
		this.linestartx2 = linestartx2;
		this.lineendx2 = lineendx2;
		this.linestarty2 = linestarty2;
		this.lineendy2 = lineendy2;

	}

	/**
	 * This function utilises the counterclockwise function to check whether or
	 * not a collision has occured. Think of two line segments AB, and CD. These
	 * intersect if and only if points A and B are separated by segment CD and
	 * points C and D are separated by segment AB. If points A and B are
	 * separated by segment CD then ACD and BCD should have opposite orientation
	 * meaning either ACD or BCD is counterclockwise but not both. Therefore
	 * calculating if two line segments AB and CD intersect is as follows:
	 * intersect(A,B,C,D): return ccw(A,C,D) != ccw(B,C,D) and ccw(A,B,C) !=
	 * ccw(A,B,D)
	 * 
	 * @return true if a collision has occurred.
	 */
	public boolean CheckCollision() {
		return (ccw(linestartx, linestarty, linestartx2, linestarty2,
				lineendx2, lineendy2) != ccw(lineendx, lineendy, linestartx2,
				linestarty2, lineendx2, lineendy2))
				&& (ccw(linestartx, linestarty, lineendx, lineendy,
						linestartx2, linestarty2) != ccw(linestartx,
						linestarty, lineendx, lineendy, lineendx2, lineendy2));
	}

	/**
	 * This function finds whether or not three points are in counterclockwise
	 * order. A, B and C. If the slope of the line AB is less than the slope of
	 * the line AC then the three points are listed in a counterclockwise order.
	 * 
	 * @param Ax
	 *            A x position
	 * @param Ay
	 *            A y position
	 * @param Bx
	 *            B x position
	 * @param By
	 *            B y position
	 * @param Cx
	 *            C x position
	 * @param Cy
	 *            C y position
	 * @return True if slope of line AB is less than slope of line AC
	 */
	private boolean ccw(double Ax, double Ay, double Bx, double By, double Cx,
			double Cy) {
		return (Cy - Ay) * (Bx - Ax) > (By - Ay) * (Cx - Ax);
	}
}
