package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

import alpha.Config;
import alpha.TCPort;

<<<<<<< HEAD
@SuppressWarnings("serial")
public class SettingsGUI extends JFrame {
	// private boolean hasBroadcast = false;

	public SettingsGUI() {
		// try {
		// Class.forName("broadcast.Broadcast");
		// hasBroadcast = true;
		// } catch (ClassNotFoundException e) {
		// // ignored
		// }
=======


@SuppressWarnings("serial")
public class SettingsGUI extends JFrame {
//	private boolean hasBroadcast = false;
	
	public SettingsGUI() {
//		try {
//			Class.forName("broadcast.Broadcast");
//			hasBroadcast = true;
//		} catch (ClassNotFoundException e) {
//			// ignored
//		}
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		language();
		textField1.setText(Integer.toString(Config.image_size));
		textField2.setText(Integer.toString(Config.icon_size));
		textField3.setText(Integer.toString(Config.icon_space));
		textField4.setText(Config.icon_folder);
<<<<<<< HEAD

		this.addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				synchronized (this) {
					this.notifyAll(); // tell anyone waiting on us that we're done
				}
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
	}

	private void ok(ActionEvent e) {

		int tray_size;
		int buddy_size;
		int buddy_space;

=======
		
		
		this.addWindowListener(new WindowListener() {
            @Override
			public void windowClosed(WindowEvent e) {}
            @Override
			public void windowActivated(WindowEvent e) {}
            @Override
			public void windowClosing(WindowEvent e) {
        		dispose();
        		synchronized(this) {
        			this.notifyAll(); // tell anyone waiting on us that we're done
        		}
            }
            @Override
			public void windowDeactivated(WindowEvent e) {}
            @Override
			public void windowDeiconified(WindowEvent e) {}
            @Override
			public void windowIconified(WindowEvent e) {}
            @Override
			public void windowOpened(WindowEvent e) {}
        });
	}

	private void ok(ActionEvent e) {
		
		int tray_size;
		int buddy_size;
		int buddy_space;
		
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		if (textField1.getText().length() != 0) {
			try {
				tray_size = Integer.parseInt(textField1.getText());
				if (tray_size < 1) {
					JOptionPane.showMessageDialog(null, textField1.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
<<<<<<< HEAD

				Config.image_size = tray_size;
				Config.prop.put("image_size", tray_size + "");

=======
				
				
				Config.image_size = tray_size;
				Config.prop.put("image_size", tray_size+ "");

				
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField1.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
<<<<<<< HEAD

=======
		
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		if (textField2.getText().length() != 0) {
			try {
				buddy_size = Integer.parseInt(textField2.getText());
				if (buddy_size < 1) {
					JOptionPane.showMessageDialog(null, textField2.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Config.icon_size = buddy_size;
<<<<<<< HEAD
				Config.prop.put("icon_size", buddy_size + "");

=======
				Config.prop.put("icon_size",buddy_size+ "");
				
				
				
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField2.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
<<<<<<< HEAD

=======
		
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		if (textField3.getText().length() != 0) {
			try {
				buddy_space = Integer.parseInt(textField3.getText());
				if (buddy_space < 1) {
					JOptionPane.showMessageDialog(null, textField3.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
<<<<<<< HEAD

				Config.icon_space = buddy_space;
				Config.prop.put("icon_space", buddy_space + "");
=======
				
				Config.icon_space = buddy_space;
				Config.prop.put("icon_space", buddy_space+ "");
				
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField3.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
<<<<<<< HEAD

		Config.icon_folder = textField4.getText();
		Config.prop.put("ICON", Config.icon_folder);

		TCPort.sendMyInfo();
=======
		
		Config.icon_folder = textField4.getText();
		Config.prop.put("ICON", Config.icon_folder);
		
	    
	    TCPort.sendMyInfo();
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		try {
			Config.prop.store(new FileOutputStream(Config.CONFIG_DIR + "settings.ini"), null);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
<<<<<<< HEAD

		dispose();
		synchronized (this) {
=======
		
		dispose();
		synchronized(this) {
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			this.notifyAll(); // tell anyone waiting on us that we're done
		}
	}

	public JTabbedPane getTabbedPane1() {
		return tabbedPane1;
	}

<<<<<<< HEAD
	private void language() {
=======

	private void language()
	{
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95

		label1.setText("Tray icon size: ");
		label2.setText("Buddy icon size: ");
		label3.setText("Space between Buddys: ");
		label4.setText("You can see the change when you have restart!");
		label5.setText("Icon Set");
		label6.setText("\"orginal\" and \"juan\" are pre installed --> Icon Set");
<<<<<<< HEAD
		tabbedPane1.removeAll();
		tabbedPane1.addTab("Icons", panel1);

	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY //GEN-BEGIN:initComponents
=======
	tabbedPane1.removeAll();
	tabbedPane1.addTab("Icons", panel1);



	}


	
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		// Generated using JFormDesigner Evaluation license - jhgfdf jhgfc
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		label3 = new JLabel();
		textField3 = new JTextField();
		label4 = new JLabel();
		label5 = new JLabel();
		textField4 = new JTextField();
		label6 = new JLabel();
		button1 = new JButton();

<<<<<<< HEAD
		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 0, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 1.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 1.0, 0.0, 1.0E-4 };

		// ======== tabbedPane1 ========
		{

			// ======== panel1 ========
			{

				// ---- label1 ----
				label1.setText("Size of Tray Icons");
				label1.setHorizontalAlignment(SwingConstants.RIGHT);

				// ---- textField1 ----
				textField1.setPreferredSize(new Dimension(180, 28));

				// ---- label2 ----
				label2.setText("Size of Buddy Icons");
				label2.setHorizontalAlignment(SwingConstants.RIGHT);

				// ---- textField2 ----
				textField2.setPreferredSize(new Dimension(180, 28));

				// ---- label3 ----
				label3.setText("Space between Buddys");
				label3.setHorizontalAlignment(SwingConstants.TRAILING);

				// ---- label4 ----
				label4.setText("You can see the change when you have restart!");
				label4.setHorizontalTextPosition(SwingConstants.CENTER);

				// ---- label5 ----
				label5.setText("Icon Set");
				label5.setHorizontalAlignment(SwingConstants.TRAILING);

				// ---- label6 ----
=======
		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== tabbedPane1 ========
		{

			//======== panel1 ========
			{


				//---- label1 ----
				label1.setText("Size of Tray Icons");
				label1.setHorizontalAlignment(SwingConstants.RIGHT);

				//---- textField1 ----
				textField1.setPreferredSize(new Dimension(180, 28));

				//---- label2 ----
				label2.setText("Size of Buddy Icons");
				label2.setHorizontalAlignment(SwingConstants.RIGHT);

				//---- textField2 ----
				textField2.setPreferredSize(new Dimension(180, 28));

				//---- label3 ----
				label3.setText("Space between Buddys");
				label3.setHorizontalAlignment(SwingConstants.TRAILING);

				//---- label4 ----
				label4.setText("You can see the change when you have restart!");
				label4.setHorizontalTextPosition(SwingConstants.CENTER);

				//---- label5 ----
				label5.setText("Icon Set");
				label5.setHorizontalAlignment(SwingConstants.TRAILING);

				//---- label6 ----
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
				label6.setText("\"orginal\" and \"juan\" are pre installed --> Icon Set");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
<<<<<<< HEAD
				panel1Layout.setHorizontalGroup(panel1Layout.createParallelGroup().addGroup(
						panel1Layout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										panel1Layout
												.createParallelGroup()
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														panel1Layout
																.createSequentialGroup()
																.addGroup(
																		panel1Layout.createParallelGroup().addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
																				.addComponent(label2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE).addComponent(label3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
																				.addComponent(label5, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(textField4, GroupLayout.Alignment.LEADING)
																				.addComponent(textField1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE).addComponent(textField2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
																				.addComponent(textField3, GroupLayout.Alignment.LEADING)).addContainerGap()).addGroup(panel1Layout.createSequentialGroup().addComponent(label4, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE).addGap(4, 4, 4))
												.addGroup(panel1Layout.createSequentialGroup().addComponent(label6, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE).addContainerGap(23, Short.MAX_VALUE)))));
				panel1Layout.setVerticalGroup(panel1Layout.createParallelGroup().addGroup(
						panel1Layout
								.createSequentialGroup()
								.addGroup(
										panel1Layout.createParallelGroup().addGroup(panel1Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
												.addGroup(panel1Layout.createSequentialGroup().addContainerGap().addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE).addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addComponent(textField3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label5, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE).addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18).addComponent(label4, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE).addGap(2, 2, 2).addComponent(label6).addContainerGap()));
=======
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
									.addGroup(panel1Layout.createParallelGroup()
										.addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
										.addComponent(label2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(label3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(label5, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addComponent(textField4, GroupLayout.Alignment.LEADING)
										.addComponent(textField1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
										.addComponent(textField2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
										.addComponent(textField3, GroupLayout.Alignment.LEADING))
									.addContainerGap())
								.addGroup(panel1Layout.createSequentialGroup()
									.addComponent(label4, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
									.addGap(4, 4, 4))
								.addGroup(panel1Layout.createSequentialGroup()
									.addComponent(label6, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
									.addContainerGap(23, Short.MAX_VALUE))))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addGroup(panel1Layout.createParallelGroup()
								.addGroup(panel1Layout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
								.addGroup(panel1Layout.createSequentialGroup()
									.addContainerGap()
									.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label5, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
								.addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addComponent(label4, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(2, 2, 2)
							.addComponent(label6)
							.addContainerGap())
				);
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			}
			tabbedPane1.addTab("Images", panel1);

		}
<<<<<<< HEAD
		contentPane.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		// ---- button1 ----
=======
		contentPane.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- button1 ----
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		button1.setText("Ok");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ok(e);
			}
		});
<<<<<<< HEAD
		contentPane.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 10, 10), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
=======
		contentPane.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 10, 10), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	// Generated using JFormDesigner Evaluation license - jhgfdf jhgfc
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField textField2;
	private JLabel label3;
	private JTextField textField3;
	private JLabel label4;
	private JLabel label5;
	private JTextField textField4;
	private JLabel label6;
	private JButton button1;
<<<<<<< HEAD
	// JFormDesigner - End of variables declaration //GEN-END:variables
=======
	// JFormDesigner - End of variables declaration  //GEN-END:variables
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
}
