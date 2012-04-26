/*
 * Created by JFormDesigner on Thu Apr 26 20:42:46 CEST 2012
 */

package gui;

import java.awt.*;
import javax.swing.*;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import alpha.Buddy;


@SuppressWarnings("serial")
public class Profile extends JFrame {
	


Buddy b;
	
	public Profile(Buddy b) {
		this.b = b;
		initComponents();
		textField1.setText(b.getProfile_name());
		textField4.setText(b.getName());
		textField2.setText(b.getAddress());
		textField3.setText(b.getVersion());
		textArea1.setText(b.getProfile_text());
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

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== panel1 ========
		{

			// JFormDesigner evaluation mark
			panel1.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


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

			//---- textField4 ----
			textField4.setEditable(false);

			//---- label3 ----
			label3.setText("Name:");

			//---- label4 ----
			label4.setText("Disp-Name:");

			//---- label5 ----
			label5.setText("Tor-ID:");

			//---- label6 ----
			label6.setText("Version:");

			GroupLayout panel1Layout = new GroupLayout(panel1);
			panel1.setLayout(panel1Layout);
			panel1Layout.setHorizontalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(panel1Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel1Layout.createParallelGroup()
							.addComponent(scrollPane1)
							.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(label3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(panel1Layout.createParallelGroup()
									.addComponent(textField4, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
									.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addComponent(textField1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
										.addComponent(textField2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
										.addComponent(textField3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)))))
						.addContainerGap())
			);
			panel1Layout.setVerticalGroup(
				panel1Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label3))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label4))
						.addGap(4, 4, 4)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label5))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label6))
						.addGap(12, 12, 12)
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
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
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
