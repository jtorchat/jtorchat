package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import alpha.Config;


public class Alert extends Thread {

	private JFrame f;
	private JPanel p;
	private double prmax = 50.0d;
	private double pr = prmax;
	private boolean running = true;
	private int c;
	private static int dispWidth = 144 + 48, dispHeight = 115 / 2; // w = 250
	private static AtomicInteger numAlerts = new AtomicInteger();


	@SuppressWarnings("serial")
	public Alert(final String s) {
		if (Config.alert == 0){ // returns immediately if alerts is disabled
			return;
		}
		
		
		
		f = new JFrame();
		f.setUndecorated(true);
		f.setAlwaysOnTop(true);
		doAWTUtilities();

		f.getRootPane().setOpaque(false);

		p = new JPanel() {

			@Override
			public void paintComponent(Graphics g1) {
				Graphics2D g = (Graphics2D) g1;
				g.setBackground(new Color(0, 0, 0, 0));
				g.clearRect(0, 0, f.getWidth(), f.getHeight());
				g.translate(0, dispHeight / prmax * pr);
				if (c % 2 == 0)
					g.setColor(new Color(0, 0, 0, 150 + 50));
				else if (c % 2 == 1)
					g.setColor(new Color(255, 255, 255, 150 + 50));
				g.fillRoundRect(0, 0, dispWidth, dispHeight, 25, 25);
				if (c % 2 == 0)
					g.setColor(Color.white);
				else if (c % 2 == 1)
					g.setColor(Color.black);
				g.drawString(s, 12, 12 + g.getFontMetrics().getAscent());
			}
		};

		f.getContentPane().add(p);

		f.setSize(dispWidth, dispHeight);
		f.setFocusableWindowState(false);
		// max width 180
		// norm width 144
	}

	private void doAWTUtilities() {
//		try {
//			Class.forName("com.sun.awt.AWTUtilities");
//			if ((AWTUtilities.isTranslucencySupported(AWTUtilities.Translucency.PERPIXEL_TRANSLUCENT)) && (AWTUtilities.isTranslucencyCapable(f.getGraphicsConfiguration())))
//				AWTUtilities.setWindowOpaque(f, false);
//		} catch (Exception e) {
//			// ignore
//		}
		try {
			Class<?> awtuc = Class.forName("com.sun.awt.AWTUtilities");
			Class<?> awtutc = Class.forName("com.sun.awt.AWTUtilities$Translucency");
			if ((Boolean) awtuc.getMethod("isTranslucencySupported", awtutc).invoke(null, awtutc.getDeclaredField("PERPIXEL_TRANSLUCENT").get(null))
					&& (Boolean) awtuc.getMethod("isTranslucencyCapable", GraphicsConfiguration.class).invoke(null, f.getGraphicsConfiguration())) {
				awtuc.getMethod("setWindowOpaque", Window.class, boolean.class).invoke(null, f, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		numAlerts.getAndIncrement();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle mwb = ge.getMaximumWindowBounds();

		f.setLocation(mwb.x + mwb.width - dispWidth - 1, mwb.y + mwb.height - dispHeight - 1 - dispHeight * (numAlerts.get() - 1) - (numAlerts.get() - 1));
		f.setVisible(true);
		while (running && pr-- > 1)
			try {
				p.repaint();
				Thread.sleep(15);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		pr = 0;
		p.repaint();
		for (c = 1 ; c <= 25 ; c++)
			try {
				p.repaint();
				Thread.sleep(625);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}

		while (running && pr++ < prmax - 1)
			try {
				p.repaint();
				Thread.sleep(15);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		// pr = prmax;
		// p.repaint();
		numAlerts.getAndDecrement();
		f.dispose();
	}

	public void kill() {
		running = false;
		f.dispose();
	}

	public boolean isFinished() {
		return !running;
	}

}
