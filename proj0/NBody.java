public class NBody {

	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int numOfplanets = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readBodies(String fileName) {
		In in = new In(fileName);
		int numOfplanets = in.readInt();
		double radius = in.readDouble();
		Body[] bodies = new Body[numOfplanets];
		for (int i = 0; i < numOfplanets; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			bodies[i] = new Body(xP, yP, xV, yV, m, img);
 		}
 		return bodies;
	}



	public static void main(String[] args) {
		/**Collect all needed input information*/
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Body[] bodies = readBodies(filename);

		/** Enables double buffering.
		  * A animation technique where all drawing takes place on the offscreen canvas.
		  * Only when you call show() does your drawing get copied from the
		  * offscreen canvas to the onscreen canvas, where it is displayed
		  * in the standard drawing window. */
		StdDraw.enableDoubleBuffering();

		int numOfplanets = bodies.length;

		for (double time = 0; time < T; time += dt) {
			double[] xForces = new double[numOfplanets];
			double[] yForces = new double[numOfplanets];
			for (int i = 0; i < numOfplanets; i++) {
				double xForceOnBody = bodies[i].calcNetForceExertedByX(bodies);
				xForces[i] = xForceOnBody;
				double yForceOnBody = bodies[i].calcNetForceExertedByY(bodies);
				yForces[i] = yForceOnBody;
 			}
 			for (int i = 0; i < numOfplanets; i++) {
 				bodies[i].update(dt, xForces[i], yForces[i]);
 			}
	
			/** Sets up the universe so it goes from
			  * (-radius, -radius) up to (radius, radius) */
			StdDraw.setScale(-radius, radius);
			StdDraw.clear();	
			StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);
			for (Body body : bodies) {
				body.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
		                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}

}