package files;

import java.util.ArrayList;
import java.util.Formatter;

import gui.Point;

public class File {

	private Formatter x;
	private Formatter y;

	public void openFile() {

		try {
			x = new Formatter("AirResistance.txt");
			y = new Formatter("NoAirResistance.txt");
		} catch (Exception e) {
			System.out.println("Open file error!");
		}
	}

	public void addData(ArrayList<Point> pAir, ArrayList<Point> pNoAir) {
		x.format("X-Position w/ Air Resistance(m),Y-Position w/ Air Resistance(m),X-Position w/ No Air Resistance, Y-Position w/ No Air Resistance\r\n");
		
		for(int i = 0; i < pNoAir.size(); i++){
			if(pNoAir.get(i).getY() >=0 || pAir.get(i).getY() >=0)
			x.format(pNoAir.get(i).getX() + "," + pNoAir.get(i).getY() + "," +pAir.get(i).getX() + "," + pAir.get(i).getY() + "," + "\r\n");
		}
	}

	public void closeFile() {
		x.close();
	}
}