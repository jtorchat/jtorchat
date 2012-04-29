/*
 * Created by JFormDesigner on Thu Apr 26 20:42:46 CEST 2012
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import util.ConfigWriter;

import alpha.Buddy;
import alpha.Config;
import alpha.TCPort;


@SuppressWarnings("serial")
public class Profile extends JFrame {
	


Buddy b;
	
	public Profile(Buddy b) {
		this.b = b;
		initComponents();
		
		if (!b.getAddress().equals(Config.us))
		{
		textField1.setText(b.getProfile_name());
		textField4.setText(b.getName());
		textField2.setText(b.getAddress());
		textField3.setText(b.getClient() + " " + b.getVersion());
		textArea1.setText(b.getProfile_text());
		}
		else
		{
		button2.setVisible(true);
		button3.setVisible(true);
		textField1.setEditable(true);
		textArea1.setEditable(true);
		textField1.setText(TCPort.profile_name);
		textField4.setText(b.getName());
		textField2.setText(Config.us);
		textField3.setText(Config.CLIENT + " " + Config.VERSION);
		textArea1.setText(TCPort.profile_text);
		}
	}

	private void button1ActionPerformed(ActionEvent e) {
		this.b.setName(textField4.getText());
	}

	private void button2ActionPerformed(ActionEvent e) {
		TCPort.profile_name = textField1.getText();
		ConfigWriter.saveall(3);
		TCPort.sendMyProfil();
	}

	private void button3ActionPerformed(ActionEvent e) {
		TCPort.profile_text = textArea1.getText();
		TCPort.sendMyProfil();
		ConfigWriter.saveall(3);
	}
	


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
		panel1 = new JPanel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		textField1 = new JTextField();
		textField2 = new JTextField();
		textField3 = new JTextField();
		textField4 = new JTextField();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== panel1 ========
		{

			//======== scrollPane1 ========
			{

				//---- textArea1 ----
				textArea1.setEditable(false);
				scrollPane1.setViewportView(textArea1);
			}

			//---- textField1 ----
			textField1.setEditable(false);

			//---- textField2 ----
			textField2.setEditable(false);

			//---- textField3 ----
			textField3.setEditable(false);

			//---- label3 ----
			label3.setText("Name:");

			//---- label4 ----
			label4.setText("Disp-Name:");

			//---- label5 ----
			label5.setText("Tor-ID:");

			//---- label6 ----
			label6.setText("Version:");

			//---- button1 ----
			button1.setText("save");
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button1ActionPerformed(e);
				}
			});

			//---- button2 ----
			button2.setText("save");
			button2.setVisible(false);
			button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button2ActionPerformed(e);
				}
			});

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addGroup(panel1Layout.createSequentialGroup()
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(panel1Layout.createParallelGroup()
									.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createParallelGroup()
										.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
											.addComponent(textField2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
											.addComponent(textField3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
										.addComponent(textField4, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
									.addComponent(textField1, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel1Layout.createParallelGroup()
									.addComponent(button1)
									.addComponent(button2))
								.addGap(19, 19, 19))
							.addGroup(panel1Layout.createSequentialGroup()
								.addComponent(scrollPane1)
								.addContainerGap())))
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label3)
							.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(button2))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label4)
							.addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(button1))
						.addGap(4, 4, 4)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label5)
							.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label6)
							.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
			);
		}
		contentPane.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- button3 ----
		button3.setText("save");
		button3.setVisible(false);
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button3ActionPerformed(e);
			}
		});
		contentPane.add(button3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
	private JPanel panel1;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
