package gui;

public class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double xx) {
		this.x = xx;
	}
	
	public void setY(double yy) {
		this.y = yy;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public String toString(){
		return "(" + x + " , " + y + ")";
	}
}