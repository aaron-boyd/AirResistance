package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;

import files.File;

public class GraphPanel extends JPanel {

	private static final int RADIUS = 4;
	private static final int TICK_SIZE = 16;
	private Scale scale;
	private int trueWidth;
	private int trueHeight;
	private String title;
	private String xLabel;
	private String yLabel;

	private ArrayList<Point> arrAir = new ArrayList<>();
	private ArrayList<Point> arrNoAir = new ArrayList<>();
	private double xUnit;
	private double yUnit;
	private File file = new File();

	public GraphPanel(int width, int height, Scale s, String title, String x, String y) {
		super();
		this.trueWidth = width;
		this.trueHeight = height;
		this.title = title;
		this.scale = s;
		this.xLabel = x;
		this.yLabel = y;
		xUnit = 0;
		yUnit = 0;

		setPreferredSize(new Dimension(scale.multScaleWidth(1500), scale.multScaleHeight(750)));
	}

	private int scaleX(double x) {
		return (int) ((double) (x) / (double) trueWidth * (double) getWidth() + (getWidth() / 10));
	}

	private int scaleY(double y) {
		return (int) ((double) (trueHeight - y) / (double) trueHeight * (double) getHeight()) - (2 * getHeight() / 10);
	}

	public void addAirPoint(double x, double y) {
		if (y < 0) return;
		arrAir.add(new Point(x, y));
	}

	public void addNoAirPoint(double x, double y) {
		if (y < 0) return;
		arrNoAir.add(new Point(x, y));
	}

	private void drawTicks(Graphics g) {
		// set color
		// draw axis
		DecimalFormat dec = new DecimalFormat("##0.00");

		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.black);
		g2D.setFont(new Font("Consolas", Font.BOLD, scale.multScaleHeight(24)));
		g2D.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

		g2D.drawString(xLabel, getWidth() / 2, getHeight() - scale.multScaleWidth(50));

		g2D.setFont(new Font("Consolas", Font.CENTER_BASELINE, scale.multScaleHeight(28)));
		g2D.drawString(title, (getWidth() / 2) - scale.multScaleWidth(20), scale.multScaleHeight(22));
		g2D.setFont(new Font("Consolas", Font.CENTER_BASELINE, scale.multScaleHeight(24)));

		// Create a rotation transformation for the font.
		AffineTransform fontAT = new AffineTransform();

		// get the current font
		Font theFont = g2D.getFont();

		// Derive a new font using a rotatation transform
		fontAT.rotate(-89.555);
		Font theDerivedFont = theFont.deriveFont(fontAT);

		// set the derived font in the Graphics2D context
		g2D.setFont(theDerivedFont);

		// Render a string using the derived font
		g2D.drawString(yLabel, (int) 15, (int) getHeight() / 2 + 50);

		// put the original font back
		g2D.setFont(theFont);

		g2D.drawLine(getWidth() / 10, 0, getWidth() / 10, getHeight() - (getWidth() / 10));
		
		g2D.drawLine(getWidth() / 10, getHeight() - (getWidth() / 10), getWidth(), getHeight() - (getWidth() / 10));
		// draw ticks
		for (int i = 0; i < 10; i++) {
			int y = (int) ((double) (i - 1) / 10.0 * getHeight());
			int x = (int) ((double) (i + 1) / 10.0 * getWidth());

			// Horizontal Ticks
			g2D.drawLine((getWidth() / 10) - scale.multScaleWidth(20), y, (getWidth() / 10) + scale.multScaleHeight(20), y);
			g2D.drawString(dec.format((i) * xUnit), x, getHeight() - scale.multScaleHeight(100));
			// Vertical Ticks
			g2D.drawLine(x, getHeight() - (2 * getHeight() / 10) - scale.multScaleWidth(20), x, getHeight() - (2 * getHeight() / 10) + scale.multScaleHeight(20));
			g2D.drawString(dec.format((9 - i) * yUnit), (x - x) + scale.multScaleWidth(50), y + scale.multScaleHeight(15));

		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Color line1 = new Color(255, 80, 80);
		Color line2 = new Color(0, 107, 179);
		Color backGround = new Color(205,208,209);

		g.setColor(backGround);
		g.fillRect(0, 0, getWidth(), getHeight());

		drawTicks(g);

		g.setColor(line1);

		for (int i = 0; i < arrAir.size(); i++) {
			g.fillOval(((int) arrAir.get(i).getX() - (RADIUS / 2)), (int) (arrAir.get(i).getY() - (RADIUS / 2)), RADIUS, RADIUS);
		}

		g.setColor(line2);
		for (int i = 0; i < arrNoAir.size(); i++) {
			g.fillOval(((int) arrNoAir.get(i).getX() - (RADIUS / 2)), (int) (arrNoAir.get(i).getY() - (RADIUS / 2)), RADIUS, RADIUS);
		}

	}

	public void updateTrueSize(double maxX, double maxY) {
		DecimalFormat dec = new DecimalFormat("###.00###");

		trueHeight = (int) maxY;
		trueWidth = (int) maxX;

		for (int i = 0; i < arrNoAir.size(); i++) {
			arrNoAir.get(i).setX(scaleX(arrNoAir.get(i).getX()));
			arrNoAir.get(i).setY(scaleY(arrNoAir.get(i).getY()));
		}

		for (int i = 0; i < arrAir.size(); i++) {
			arrAir.get(i).setX(scaleX(arrAir.get(i).getX()));
			arrAir.get(i).setY(scaleY(arrAir.get(i).getY()));
		}
		repaint();
	}

	public void clearArrays() {
		arrAir.clear();
		arrNoAir.clear();
	}

	public void scale(double x, double y) {
		xUnit = x;
		yUnit = y;
	}

	public void setUnits(String x, String y) {

	}

	public ArrayList<Point> getAirXY() {
		return this.arrAir;
	}

	public ArrayList<Point> getNoAirXY() {
		return this.arrNoAir;
	}

	public void saveFile() {
		File file = new File();
		file.openFile();
		file.addData(arrAir, arrNoAir);
		file.closeFile();
	}

}
