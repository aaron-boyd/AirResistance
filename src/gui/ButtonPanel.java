package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ButtonPanel extends JPanel {

	private JTextField txtC = new JTextField("0.139");
	private JTextField txtV = new JTextField("382.524");
	private JTextField txtH = new JTextField("0.3048");
	private JTextField txtT = new JTextField("0.2");
	private JLabel lblC = new JLabel("Coefficient:");
	private JLabel lblV = new JLabel("Velocity(m/s):");
	private JLabel lblH = new JLabel("Height(m):");
	private JLabel lblT = new JLabel("Angle(°):");
	private JButton cmdCalc = new JButton("Calculate");
	private GraphPanel XYpanel;
	private GraphPanel XTpanel;
	private GraphPanel YTpanel;
	private Point[] dropChart = new Point[3];
	private Scale scale;
	private Object[][] rowData = { { "", "No Air", "Air" }, { "Max Dis.", "-", "-" } };

	private String[] colNames = { "", "Air", "No Air" };
	private JTable dropTable = new JTable(rowData, colNames);

	public ButtonPanel(GraphPanel xy, GraphPanel xt, Scale s) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(20, 20, 20, 20);
		scale = s;
		XYpanel = xy;
		XTpanel = xt;
		Color colorOne = new Color(205,208,209);
		Color colorTwo = new Color(188,28, 28);
		Color colorThree = new Color(50, 50, 50);

		setTextDimAndFont(new Dimension(scale.multScaleWidth(300), scale.multScaleHeight(50)), new Font("Consolas", Font.PLAIN, scale.multScaleHeight(40)), colorOne);
		setButtonDimAndFont(new Dimension(scale.multScaleWidth(650), scale.multScaleHeight(100)), new Font("Consolas", Font.PLAIN, scale.multScaleHeight(56)), colorThree, colorOne);
		setLabelDimAndFont(new Dimension(scale.multScaleWidth(350), scale.multScaleHeight(50)), new Font("Consolas", Font.PLAIN, scale.multScaleHeight(36)), colorThree);

		setBackground(colorTwo);

		// Velocity
		c.gridx = 0;
		c.gridy = 0;

		add(lblV, c);

		c.gridx = 2;

		add(txtV, c);

		c.gridx = 0;
		c.gridy = 1;

		// angle
		add(lblT, c);

		c.gridx = 2;

		add(txtT, c);

		c.gridx = 0;
		c.gridy = 2;

		// Coefficient
		add(lblC, c);

		c.gridx = 2;

		add(txtC, c);

		c.gridx = 0;
		c.gridy = 3;

		c.gridx = 0;
		c.gridy = 4;

		// Height
		add(lblH, c);

		c.gridx = 2;

		add(txtH, c);

		c.gridx = 0;
		c.gridy = 5;

		// Button
		c.gridwidth = 3;
		add(cmdCalc, c);
		c.gridy = 6;

		dropTable.setPreferredSize(new Dimension(scale.multScaleWidth(650), scale.multScaleHeight(100)));
		dropTable.getColumnModel().getColumn(0).setPreferredWidth(scale.multScaleWidth(200));
		dropTable.getColumnModel().getColumn(1).setPreferredWidth(scale.multScaleWidth(200));
		dropTable.getColumnModel().getColumn(2).setPreferredWidth(scale.multScaleWidth(200));
		dropTable.setRowHeight(scale.multScaleHeight(50));

		add(dropTable, c);

		cmdCalc.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg) {
				DecimalFormat round = new DecimalFormat("##0.000");
				try {
					double Vo = Double.parseDouble(txtV.getText());
					double K = Double.parseDouble(txtC.getText());
					double theta = Double.parseDouble(txtT.getText());
					double h = Double.parseDouble(txtH.getText());
					XYpanel.clearArrays();

					for (double i = 0.000; i < 3.000; i += 0.001) {
						Point XYAir = new Point(AirCalculator.calcAirResistanceX(Vo, K, theta, i), AirCalculator.calcAirResistanceY(Vo, K, theta, i) + h);
						Point XYNoAir = new Point(AirCalculator.calcNoAirResistanceX(Vo, theta, i), AirCalculator.calcNoAirResistanceY(Vo, theta, i) + h);
						Point XTAir = new Point(i, AirCalculator.calcAirResistanceVX(Vo, K, theta, i));
						Point XTNoAir = new Point(i, Vo);

						XYpanel.addAirPoint(XYAir.getX(), XYAir.getY());
						XYpanel.addNoAirPoint(XYNoAir.getX(), XYNoAir.getY());

						XTpanel.addAirPoint(XTAir.getX(), XTAir.getY());
						XTpanel.addNoAirPoint(XTNoAir.getX(), XTNoAir.getY());

						// System.out.println("Time = " +round.format(i) + " " + XTAir.getX() + " " +XTAir.getY());

					}
					ArrayList<Point> arrAir = new ArrayList<Point>(XYpanel.getAirXY());
					ArrayList<Point> arrNoAir =new ArrayList<Point> (XYpanel.getNoAirXY());
					
					dropTable.getModel().setValueAt(Math.round(arrAir.get(arrAir.size() - 1).getX()) +  " m", 1, 1);
					dropTable.getModel().setValueAt(Math.round(arrNoAir.get(arrNoAir.size() - 1).getX())+" m", 1, 2);

					
					XYpanel.updateTrueSize(300, 1);
					XYpanel.scale(30, 0.1);
					XYpanel.repaint();
					double mult = 100 * (Math.round(Vo / 100) + 1);

					XTpanel.updateTrueSize(3, mult);
					XTpanel.scale(0.3, (int) (mult / 10));
					XTpanel.repaint();

				
					/*
					 * double FiftyT = AirCalculator.calcAirResistanceT(Vo, K, theta, 50);
					 * 
					 * double FiftyY = AirCalculator.calcAirResistanceY(Vo, K, theta, FiftyT) + h;
					 * 
					 * for (int i = 0; i < 3; i++) { double disTime = AirCalculator.calcAirResistanceT(Vo, K, theta, 50 + (i * 25)); double disY = AirCalculator.calcAirResistanceY(Vo, K, theta, disTime) + h; // System.out.println((50 + (i * 25)) + ": "+ (disY *39.3701 )); // 39.3701 if (FiftyY > 0 && disY > 0) { String drop = round.format((FiftyY - disY) * 39.3701); dropTable.getModel().setValueAt(new Integer((50 + (i * 25))), i + 1, 0); dropTable.getModel().setValueAt(new String(drop), i + 1, 1); } else { dropTable.getModel().setValueAt(new Integer((50 + (i * 25))), i + 1, 0); dropTable.getModel().setValueAt(new String("N/A"), i + 1, 1); }
					 * 
					 * }
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}

	private void setTextDimAndFont(Dimension d, Font f, Color back) {
		txtC.setPreferredSize(d);
		txtV.setPreferredSize(d);
		txtH.setPreferredSize(d);
		txtT.setPreferredSize(d);

		txtC.setFont(f);
		txtV.setFont(f);
		txtH.setFont(f);
		txtT.setFont(f);

		txtC.setBackground(back);
		txtT.setBackground(back);
		txtV.setBackground(back);
		txtH.setBackground(back);

	}

	private void setButtonDimAndFont(Dimension d, Font f, Color back, Color fore) {
		cmdCalc.setPreferredSize(d);
		// cmdCalc.setForeground(fore);
		// cmdCalc.setBackground(back);
		cmdCalc.setFont(f);
	}

	private void setLabelDimAndFont(Dimension d, Font f, Color back) {
		lblC.setPreferredSize(d);
		lblV.setPreferredSize(d);
		lblH.setPreferredSize(d);
		lblT.setPreferredSize(d);
		lblC.setFont(f);
		lblV.setFont(f);
		lblH.setFont(f);
		lblT.setFont(f);
		lblC.setForeground(back);
		lblV.setForeground(back);
		lblT.setForeground(back);
		lblH.setForeground(back);
		dropTable.setFont(f);

	}
}
