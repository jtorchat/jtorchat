

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import alpha.Config;
import alpha.Logger;


@SuppressWarnings("serial")
public class KillTor extends JFrame {


public static int newpb = 0;


	public KillTor() {
		initComponents();
	}
	
	public static void listenerport() {
		KillTor kgui = new KillTor();
		kgui.setVisible(true);
		
		while (newpb == 0)
		{
			

				  try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//sleep for 1000 ms
					Logger.log(Logger.NOTICE, "ConfigWriter", "Lodddddtings.ini");

		}	
		
	}

	private void button1ActionPerformed(ActionEvent e) {
		
		String command = null;
		String command2 = null;
		if (Config.os.indexOf("win") >= 0) {
			command="taskkill /F /IM jtor.exe";
			command2="taskkill /F /IM jobf.exe";
		} else if (Config.os.indexOf("nix") >= 0 || Config.os.indexOf("nux") >= 0) {
			command="killall tor.lin";
			command2="killall jobf";
		}
		
		Runtime run = Runtime.getRuntime();
		Process pr,pr2;
		try {
			pr = run.exec(command); // NOTE could potentially be null
			pr2 = run.exec(command2); // NOTE could potentially be null
			try {
				pr.waitFor();
				pr2.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		newpb = 2;
		this.dispose();
	}

	private void button4ActionPerformed(ActionEvent e) {
		System.exit(0);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
		panel1 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		button1 = new JButton();
		button4 = new JButton();
		label5 = new JLabel();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== panel1 ========
		{

			//---- label1 ----
			label1.setText("The Listener Port is in use.");

			//---- label2 ----
			label2.setText("That means another Instance of Tor is already running.");

			//---- label3 ----
			label3.setText("Normally it is an old tior instance from jtorchat.");

			//---- label4 ----
			label4.setText("Can i try to kill them and start a new one?");

			//---- button1 ----
			button1.setText("Yes (kill jtor.exe or tor.lin)");
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			//---- button4 ----
			button4.setText("No (end of programm)");
			button4.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button4ActionPerformed(e);
				}
			});

			//---- label5 ----
			label5.setText("(try to kill obfsproxy to)");

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(label2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(panel1Layout.createSequentialGroup()
								.addComponent(button1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
								.addComponent(button4, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
							.addComponent(label5, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
						.addContainerGap())
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
						.addGap(18, 18, 18)
						.addComponent(label1)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label3)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label4)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label5)
						.addGap(35, 35, 35)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(button4)
							.addComponent(button1))
						.addContainerGap(69, Short.MAX_VALUE))
			);
		}
		contentPane.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
	private JPanel panel1;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JButton button1;
	private JButton button4;
	private JLabel label5;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
