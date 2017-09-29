package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Window extends JFrame {
	private Scale scale = new Scale(Toolkit.getDefaultToolkit().getScreenSize());
	private GraphPanel XYpanel = new GraphPanel(1, 1,scale, "X-Position vs Y-Position", "Meters (m)", "Meters (m)");
	private GraphPanel XTpanel = new GraphPanel(1, 1,scale, "X-Velocity vs Time", "Seconds (s)", "Meters per second (m/s)");
	private ButtonPanel Button = new ButtonPanel(XYpanel, XTpanel,scale);
	private JPanel MainPanel = new JPanel(new GridBagLayout());

	public Window() throws HeadlessException {
		super("Air Resistance Calculator");
		GridBagConstraints c = new GridBagConstraints();
        Image img = Toolkit.getDefaultToolkit().getImage("bullet.png");
        setIconImage(img);

		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(20, 20, 20, 20);
		setMinimumSize(new Dimension(scale.multScaleWidth(2350), scale.multScaleHeight(1700)));
		setLayout(new GridBagLayout());
		Container contentPane = getContentPane();

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 4;
		contentPane.add(Button, c);
		c.gridx = 1;
		c.gridheight = 1;

		contentPane.add(XYpanel, c);
		c.gridy = 2;

		contentPane.add(XTpanel, c);

		contentPane.setBackground(new Color(205,208,209));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void addXYAirPoint(double x, double y) {
		XYpanel.addAirPoint(x, y);
	}

	public void addXYNoAirPoint(double x, double y) {
		XYpanel.addNoAirPoint(x, y);
	}

	public GraphPanel getXYPanel() {
		return XYpanel;
	}

	public void addXTAirPoint(double x, double y) {
		XYpanel.addAirPoint(x, y);
	}

	public void addXTNoAirPoint(double x, double y) {
		XYpanel.addNoAirPoint(x, y);
	}

	public GraphPanel getXTPanel() {
		return XTpanel;
	}

}
