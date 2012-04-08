package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import alpha.Config;

import util.LogWriter;

@SuppressWarnings("serial")
public class Log extends JFrame {
	public static final Log instance;

	public Log() {
		initComponents();
		setSize(755, 402);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		textPane1.setEditable(false);
		DefaultStyledDocument d = (DefaultStyledDocument) textPane1.getDocument();
		Style timestampStyle = d.addStyle("Time Stamp", null);
		StyleConstants.setForeground(timestampStyle, Color.gray.darker());
		Style theirNameStyle = d.addStyle("Err", null);
		StyleConstants.setForeground(theirNameStyle, Color.red.darker());
		Style classc = d.addStyle("Class-c", null);
		StyleConstants.setForeground(classc, Color.green.darker());
		Style classt = d.addStyle("Class-t", null);
		StyleConstants.setForeground(classt, Color.green.darker().darker().darker());
	}

	static {
		Gui.setLAF("Nimbus");
		instance = new Log();
		// instance.setVisible(true);
	}

	private static Object LOCK = new Object();

	public static void append(String text, String style) {
		synchronized (LOCK) {
			DefaultStyledDocument d = (DefaultStyledDocument) instance.textPane1.getDocument();
			try {

				d.insertString(d.getLength(), text, style == null ? null : d.getStyle(style));
				trimText();
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	}

	public static void updateErr(String s) {
		synchronized (LOCK) {
			DefaultStyledDocument d = (DefaultStyledDocument) instance.textPane1.getDocument();
			try {
				d.insertString(d.getLength(), s, d.getStyle("Err"));
				trimText();
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	}

	public static void updateOut(String s) {
		synchronized (LOCK) {
			DefaultStyledDocument d = (DefaultStyledDocument) instance.textPane1.getDocument();
			try {
				d.insertString(d.getLength(), s, null);
				trimText();
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	}

	private void button1ActionPerformed(ActionEvent e) {
		LogWriter.LogWrite(instance.textPane1.getText(), 0, "");
	}

	private static void trimText() {
		if (Config.fulllog == 0) {
			if (instance.textPane1.getDocument().getLength() > 10000) {
				int i = instance.textPane1.getText().indexOf("\n");
				try {
					instance.textPane1.getDocument().remove(0, i + 1);
				} catch (BadLocationException e) {
//					e.printStackTrace(); // shouldnt have to worry much about this, if it were to print it would flood the log most likely triggering another trimText() call
				}
				trimText();
			}
			// instance.textPane1.setCaretPosition(((DefaultStyledDocument) instance.textPane1.getDocument()).getLength());
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - test test2
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();
		button1 = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));

		// ======== scrollPane1 ========
		{
			scrollPane1.setViewportView(textPane1);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		// ---- button1 ----
		button1.setText("save");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1ActionPerformed(e);
			}
		});
		contentPane.add(button1, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - test test2
	private JScrollPane scrollPane1;
	private JTextPane textPane1;
	private JButton button1;
	// JFormDesigner - End of variables declaration //GEN-END:variables

}
