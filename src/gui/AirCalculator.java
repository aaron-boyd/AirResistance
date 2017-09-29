package gui;

public abstract class AirCalculator {

	private final static double M_G = 9.80665;

	public static final double calcAirResistanceX(double Vo, double K, double theta, double t) {
		theta = (theta * Math.PI) / 180;
		return (-1 * (Vo * Math.cos(theta) * Math.pow(Math.E, (-1 * t * K))) / K) + ((Vo * Math.cos(theta)) / K);
	}

	public static final double calcAirResistanceY(double Vo, double K, double theta, double t) {
		double sectionOne = 0.0;
		double sectionTwo = 0.0;
		double sectionThree = 0.0;
		theta = (theta * Math.PI) / 180;
		sectionOne = -1 * Math.pow(Math.E, (-1 * K * t));
		sectionTwo = (M_G * K * t * Math.pow(Math.E, K * t)) + M_G + (K * Vo * Math.sin(theta));
		sectionThree = M_G + (K * Vo * Math.sin(theta));

		return ((sectionOne * sectionTwo) + sectionThree) / Math.pow(K, 2);
	}

	public static final double calcNoAirResistanceX(double Vo, double theta, double t) {
		theta = (theta * Math.PI) / 180;
		return Vo * Math.cos(theta) * t;

	}

	public static final double calcNoAirResistanceY(double Vo, double theta, double t) {
		theta = (theta * Math.PI) / 180;
		double Viy = Vo * Math.sin(theta);
		return (Viy * t) + (0.5 * (-1 * M_G) * Math.pow(t, 2));

	}

	public static final double calcAirResistanceVX(double Vo, double K, double theta, double t) {
		theta = (theta * Math.PI) / 180;
		return Vo * Math.cos(theta) * Math.pow(Math.E, (-1 * K * t ));
	}

	public static final double calcAirResistanceVY(double Vo, double K, double theta, double t) {
		theta = (theta * Math.PI) / 180;
		return (Vo * Math.sin(theta) + (9.88 / K)) * Math.pow(Math.E, (-1 * K * t)) - (9.88 / K);
	}

	public static final double calcNoAirResistanceVY(double Vo, double theta, double t) {
		theta = (theta * Math.PI) / 180;
		return (Vo * Math.sin(theta) - (9.88 * t));
	}

	public static final double calcAirResistanceT(double Vo, double k, double theta, int x) {
		theta = (theta * Math.PI) / 180;
		double xx = x * 0.9144;
		//System.out.println(x);
		return (-1 / k) * Math.log(((-1 * k * xx) / (Vo * Math.cos(theta))) + 1);
	}

}
