public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance (Body b) {
		return Math.sqrt((Math.pow((xxPos - b.xxPos), 2) + Math.pow((yyPos - b.yyPos), 2)));
	}

	public double calcForceExertedBy (Body b) {
		double G = 6.67 * Math.pow(10, -11);
		double distance = calcDistance(b);
		return G * mass * b.mass / Math.pow(distance, 2);
	}

	public double calcForceExertedByX (Body b) {
		double totalForce = calcForceExertedBy(b);
		double dx = b.xxPos - xxPos;
		double distance = calcDistance(b);
		double forceExertedByX = totalForce * dx / distance;
		return forceExertedByX;
	}

	public double calcForceExertedByY (Body b) {
		double totalForce = calcForceExertedBy(b);
		double dy = b.yyPos - yyPos;
		double distance = calcDistance(b);
		double forceExertedByY = totalForce * dy / distance;
		return forceExertedByY;
	}

	public double calcNetForceExertedByX (Body[] allBodys) {
		double netForceExertedByX = 0;
		for (Body b : allBodys) {
			if (!this.equals(b)) {
				netForceExertedByX += calcForceExertedByX(b);
			}		
		}
		return netForceExertedByX;
	}

	public double calcNetForceExertedByY (Body[] allBodys) {
		double netForceExertedByY = 0;
		for (Body b : allBodys) {
			if (!this.equals(b)) {
				netForceExertedByY += calcForceExertedByY(b);
			}	
		}
		return netForceExertedByY;
	}

	public void update (double dt, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;
		xxVel += aX * dt;
		yyVel += aY * dt;
		xxPos += xxVel * dt;
		yyPos += yyVel * dt;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}