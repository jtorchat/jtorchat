package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import core.Config;
import util.ConfigWriter;




@SuppressWarnings("serial")
public class GuiIcon extends JFrame {
//	private boolean hasBroadcast = false;
	
	public GuiIcon() {
//		try {
//			Class.forName("broadcast.Broadcast");
//			hasBroadcast = true;
//		} catch (ClassNotFoundException e) {
//			// ignored
//		}
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		list1 = util.list.addelements(list1,Config.icon_folder.substring(0,Config.icon_folder.length()-5),Config.ICON_DIR_MAIN,".icon");
		language();
		textField1.setText(Integer.toString(Config.image_size));
		textField2.setText(Integer.toString(Config.icon_size));
		textField3.setText(Integer.toString(Config.icon_space));
		
		
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
		
		if (textField1.getText().length() != 0) {
			try {
				tray_size = Integer.parseInt(textField1.getText());
				if (tray_size < 1) {
					JOptionPane.showMessageDialog(null, textField1.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				Config.image_size = tray_size;

				
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField1.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if (textField2.getText().length() != 0) {
			try {
				buddy_size = Integer.parseInt(textField2.getText());
				if (buddy_size < 1) {
					JOptionPane.showMessageDialog(null, textField2.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Config.icon_size = buddy_size;
				
				
				
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField2.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if (textField3.getText().length() != 0) {
			try {
				buddy_space = Integer.parseInt(textField3.getText());
				if (buddy_space < 1) {
					JOptionPane.showMessageDialog(null, textField3.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Config.icon_space = buddy_space;
				

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField3.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	    ConfigWriter.saveall(1);
		dispose();
		synchronized(this) {
			this.notifyAll(); // tell anyone waiting on us that we're done
		}
	}

	public JTabbedPane getTabbedPane1() {
		return tabbedPane1;
	}
	

	private void language()
	{

		label1.setText("Tray icon size: ");
		label2.setText("Buddy icon size: ");
		label3.setText("Space between Buddys: ");
		label4.setText("You can see the change when you have restart!");
	tabbedPane1.removeAll();
	tabbedPane1.addTab("Icons", panel1);



	}

	private void list1ValueChanged(ListSelectionEvent e) {
	    Config.icon_folder = list1.getSelectedValue().toString() + ".icon";
	}


	
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		label3 = new JLabel();
		textField3 = new JTextField();
		label4 = new JLabel();
		scrollPane1 = new JScrollPane();
		list1 = new JList();
		button1 = new JButton();

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

				//======== scrollPane1 ========
				{

					//---- list1 ----
					list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list1.addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							list1ValueChanged(e);
						}
					});
					scrollPane1.setViewportView(list1);
				}

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label4, GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(panel1Layout.createSequentialGroup()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addComponent(label1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
								.addComponent(label2, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
								.addComponent(label3, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addComponent(textField1, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
								.addComponent(textField2, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
								.addComponent(textField3, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addGroup(panel1Layout.createParallelGroup()
								.addGroup(panel1Layout.createSequentialGroup()
									.addGap(10, 10, 10)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
								.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addComponent(label4, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(21, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Images", panel1);

		}
		contentPane.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- button1 ----
		button1.setText("Ok");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ok(e);
			}
		});
		contentPane.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 10, 10), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField textField2;
	private JLabel label3;
	private JTextField textField3;
	private JLabel label4;
	private JScrollPane scrollPane1;
	private JList list1;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
