package broadcast;

<<<<<<< HEAD
=======



>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.*;

import alpha.Config;

<<<<<<< HEAD
=======


>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
@SuppressWarnings("serial")
public class BGui extends JFrame {
	public static BGui instance;

	public BGui() {
		instance = this;
		initComponents();
		String[] array = Broadcast.tagMap.keySet().toArray(new String[0]);
		Arrays.sort(array);
		ArrayList<String> x = new ArrayList<String>();
		x.addAll(Arrays.asList(array));
		x.add(0, "All");
		comboBox1.setModel(new DefaultComboBoxModel(x.toArray(new String[0])));
<<<<<<< HEAD
		// textField1.setEditable(false);
		textField1.setEnabled(false);
		button1.setEnabled(false);

=======
//		textField1.setEditable(false);
		textField1.setEnabled(false);
		button1.setEnabled(false);
		
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	}

	private void send(ActionEvent e) {
		String text = textField1.getText();
		if (text.length() > 0) {
			Broadcast.broadcast((String) comboBox1.getSelectedItem(), Config.us, text, Broadcast.forwardAll, true);
			textField1.setText("");
		}
	}

<<<<<<< HEAD
=======
	
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	public JTextPane getTextPane2() {
		return textPane2;
	}

	private void itemChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			if (e.getItem().equals("All")) {
				button1.setEnabled(false);
				textField1.setEnabled(false);
			} else {
				textField1.setEnabled(true);
				button1.setEnabled(true);
			}

			DefaultStyledDocument d = Broadcast.minilog.get(e.getItem());
			if (d == null) {
				d = new DefaultStyledDocument();
				Broadcast.minilog.put((String) e.getItem(), d);
				Style timestampStyle = d.addStyle("Time Stamp", null);
<<<<<<< HEAD
				StyleConstants.setForeground(timestampStyle, Color.gray.darker());
				Style myNameStyle = d.addStyle("Sender", null);
				StyleConstants.setForeground(myNameStyle, Color.blue.darker());
				Style theirNameStyle = d.addStyle("Tag", null);
				StyleConstants.setForeground(theirNameStyle, Color.red.darker());
=======
			    StyleConstants.setForeground(timestampStyle, Color.gray.darker());
			    Style myNameStyle = d.addStyle("Sender", null);
			    StyleConstants.setForeground(myNameStyle, Color.blue.darker());
			    Style theirNameStyle = d.addStyle("Tag", null);
			    StyleConstants.setForeground(theirNameStyle, Color.red.darker());
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			}
			textPane2.setDocument(d);
			BGui.instance.getTextPane2().setCaretPosition(BGui.instance.getTextPane2().getDocument().getLength());
		}
	}

	private void initComponents() {
<<<<<<< HEAD
		// JFormDesigner - Component initialization - DO NOT MODIFY //GEN-BEGIN:initComponents
=======
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		// Generated using JFormDesigner Evaluation license - TIm daaa
		label1 = new JLabel();
		comboBox1 = new JComboBox();
		scrollPane2 = new JScrollPane();
		textPane2 = new JTextPane();
		textField1 = new JTextField();
		button1 = new JButton();

<<<<<<< HEAD
		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

		// ---- label1 ----
		label1.setText("Tag: ");
		contentPane.add(label1, new GridBagConstraints(2, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// ---- comboBox1 ----
=======
		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//---- label1 ----
		label1.setText("Tag: ");
		contentPane.add(label1, new GridBagConstraints(2, 1, 3, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- comboBox1 ----
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		comboBox1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				itemChanged(e);
			}
		});
<<<<<<< HEAD
		contentPane.add(comboBox1, new GridBagConstraints(12, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// ======== scrollPane2 ========
		{

			// ---- textPane2 ----
=======
		contentPane.add(comboBox1, new GridBagConstraints(12, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//======== scrollPane2 ========
		{

			//---- textPane2 ----
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
			textPane2.setPreferredSize(new Dimension(300, 200));
			textPane2.setEditable(false);
			scrollPane2.setViewportView(textPane2);
		}
<<<<<<< HEAD
		contentPane.add(scrollPane2, new GridBagConstraints(2, 2, 12, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// ---- textField1 ----
=======
		contentPane.add(scrollPane2, new GridBagConstraints(2, 2, 12, 5, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- textField1 ----
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		textField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send(e);
			}
		});
<<<<<<< HEAD
		contentPane.add(textField1, new GridBagConstraints(2, 7, 12, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

		// ---- button1 ----
=======
		contentPane.add(textField1, new GridBagConstraints(2, 7, 12, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 5), 0, 0));

		//---- button1 ----
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
		button1.setText("Send");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send(e);
			}
		});
<<<<<<< HEAD
		contentPane.add(button1, new GridBagConstraints(2, 8, 12, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
=======
		contentPane.add(button1, new GridBagConstraints(2, 8, 12, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 5), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
	// Generated using JFormDesigner Evaluation license - TIm daaa
	private JLabel label1;
	private JComboBox comboBox1;
	private JScrollPane scrollPane2;
	private JTextPane textPane2;
	private JTextField textField1;
	private JButton button1;
<<<<<<< HEAD
	// JFormDesigner - End of variables declaration //GEN-END:variables
=======
	// JFormDesigner - End of variables declaration  //GEN-END:variables
>>>>>>> 375e43e7e30d42801ac6c8a22f823368e5cb2d95
}
