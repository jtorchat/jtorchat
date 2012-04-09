package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import alpha.Buddy;
import alpha.BuddyList;
import alpha.language;

@SuppressWarnings("serial")
public class GUIContactAdd extends JFrame {

	private Buddy b;

	// public static void main(String[] a) {
	// new GUIContactAdd().setVisible(true);
	// }

	public GUIContactAdd() {
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		language();
	}

	private void ok(ActionEvent e) {
		if (b == null) { // we're adding a buddy
			String addr = textField1.getText();
			if (addr.length() != 16) {
				JOptionPane.showMessageDialog(null, language.langtext[55], language.langtext[56], JOptionPane.ERROR_MESSAGE);
				return;
			}
			String dispName = textField2.getText();
			if (BuddyList.buds.containsKey(addr))
				try {
					BuddyList.buds.get(addr).remove();
				} catch (IOException ioe) {
					System.err.println("Error disconnecting buddy: " + ioe.getLocalizedMessage());
				}
			new Buddy(addr, dispName).connect();
			dispose();
		} else {
			if (textField2.getText().length() > 0)
				b.overrideName = true;
			b.setName(textField2.getText());
			dispose();
		}
	}

	private void language() {

		label1.setText(language.langtext[13]);
		label2.setText(language.langtext[14]);
		button1.setText(language.langtext[15]);

	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY //GEN-BEGIN:initComponents
		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		textField2 = new JTextField();
		button1 = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout) contentPane.getLayout()).columnWidths = new int[] { 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).rowHeights = new int[] { 0, 0, 0, 0, 0 };
		((GridBagLayout) contentPane.getLayout()).columnWeights = new double[] { 0.0, 0.0, 1.0E-4 };
		((GridBagLayout) contentPane.getLayout()).rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0E-4 };

		// ---- label1 ----
		label1.setText("Address: ");
		contentPane.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(10, 10, 5, 5), 0, 0));
		contentPane.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 0, 5, 10), 0, 0));

		// ---- label2 ----
		label2.setText("Display Name: ");
		contentPane.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 10, 5, 5), 0, 0));
		contentPane.add(textField2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 5, 10), 0, 0));

		// ---- button1 ----
		button1.setText("Ok");
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ok(e);
			}
		});
		contentPane.add(button1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 125, 15, 10), 10, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JTextField textField2;
	private JButton button1;

	// JFormDesigner - End of variables declaration //GEN-END:variables

	public void setBuddy(Buddy o) {
		this.b = o;
		textField1.setText(b.getAddress());
		textField1.setEnabled(false);
		if (b.overrideName)
			textField2.setText(b.getName());
	}
}
