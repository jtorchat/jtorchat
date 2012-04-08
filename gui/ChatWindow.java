package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;

import alpha.Buddy;
import alpha.BuddyList;
import alpha.Config;

import fileTransfer.FileDrop;
import fileTransfer.FileSender;

/**
 * @author Tsu
 */
@SuppressWarnings("serial")
public class ChatWindow extends JFrame {

	private Buddy b;
	private Style timestampStyle;
	private Style myNameStyle;
	private Style theirNameStyle;
	private String command;
	private Boolean shiftpress;

	// private Style normalStyle;

	// Clickable links start

	public void addUrlText(String text) {

		if (Config.ClickableLinks == 0) {
			append("Plain", text);
		} else {

			String[] splittall = text.split(" ");

			int x = 0;
			while (x < splittall.length) {

				if (splittall[x].startsWith("http://")) {
					try {
						addHyperlink(new URL(splittall[x]), splittall[x].substring(7), Color.blue);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // if the original doesnt have a protocol specified, insert http:// at the beggining
				} else if (splittall[x].startsWith("https://")) {
					try {
						addHyperlink(new URL(splittall[x]), splittall[x].substring(8), Color.blue);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // if the original doesnt have a protocol specified, insert http:// at the beggining
				} else {
					append("Plain", splittall[x]);
				}
				append("Plain", " ");
				x++;
			}
		}
	}

	public void addHyperlink(URL url, String text, Color color) {
		try {
			Document doc = textPane1.getDocument();
			SimpleAttributeSet attrs = new SimpleAttributeSet();
			StyleConstants.setUnderline(attrs, true);
			StyleConstants.setForeground(attrs, color);
			attrs.addAttribute(HTML.Attribute.HREF, url.toString());
			doc.insertString(doc.getLength(), text, attrs);
		} catch (BadLocationException e) {
			e.printStackTrace(System.err);
		}
	}

	// Clickable Links End

	public ChatWindow(Buddy b) {
		this.b = b;
		this.shiftpress = false;
		initComponents();
		LinkController lc = new LinkController();
		textPane1.addMouseListener(lc);
		textPane1.addMouseMotionListener(lc);

		new FileDrop(textPane1, new FileDrop.Listener() {

			public void filesDropped(java.io.File[] files) {
				Buddy b = ((ChatWindow) (textPane1).getRootPane().getParent()).getBuddy();
				for (int i = 0; i < files.length; i++) {
					new FileSender(b, files[i].getAbsolutePath());
				}

			}

		});

		System.out.println(textPane1.getDocument().getClass().getCanonicalName());
		textPane1.setEditable(false);
		// textPane1.setWrapStyleWord(true); // not needed anymore
		// textPane1.setLineWrap(true); // not needed anymore
		textArea4.setWrapStyleWord(true);
		textArea4.setLineWrap(true);
		addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				textArea4.requestFocusInWindow();
			}
		});
		timestampStyle = ((StyledDocument) textPane1.getDocument()).addStyle("Time Stamp", null);
		StyleConstants.setForeground(timestampStyle, Color.gray.darker());
		myNameStyle = ((StyledDocument) textPane1.getDocument()).addStyle("Me", null);
		StyleConstants.setForeground(myNameStyle, Color.blue.darker());
		theirNameStyle = ((StyledDocument) textPane1.getDocument()).addStyle("Them", null);
		StyleConstants.setForeground(theirNameStyle, Color.red.darker());
		textPane1.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				textArea4.dispatchEvent(e);
				textArea4.requestFocusInWindow();

			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});
	}

	private void textArea4KeyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16) {
			shiftpress = false;
		}

		if (e.getKeyCode() == 10 & shiftpress) {
			textArea4.setText(textArea4.getText() + "\n");
		}

		if (e.getKeyCode() == 10 & !shiftpress) { // enter
			if (!textArea4.getText().trim().equals("")) {
				boolean flag = b.isFullyConnected();
				try {
					String msg = textArea4.getText().trim().replaceAll("\n", "\\\\n").replaceAll("\n", "\\\\n").replaceAll("\r", "");
					if (msg.startsWith("/")) {
						command = Commands.run(b, msg, textPane1.getText());
						if (command.startsWith("0")) {
							append("Me", "Private: ");
							append("Them", (flag ? "" : "[Delayed] ") + command.substring(1) + "\n");
							textPane1.setCaretPosition(textPane1.getDocument().getLength());
							textArea4.requestFocusInWindow();

							textArea4.setText("");
						} else if (command.startsWith("1")) {
							textArea4.setText(command.substring(1));
						} else if (command.startsWith("2")) {
							append("Time Stamp", "(" + ChatWindow.getTime() + ") ");
							append("Me", "* " + BuddyList.buds.get(Config.us).toStringforme() + " " + (flag ? "" : "[Delayed] ") + command.substring(1) + "\n");

							textPane1.setCaretPosition(textPane1.getDocument().getLength());
							textArea4.requestFocusInWindow();
							if (command.trim().endsWith("\\\\n")) {
								command.substring(0, command.length() - 6);
							}

							textArea4.setText("");
						} else if (command.startsWith("3")) {
							append("Time Stamp", "(" + ChatWindow.getTime() + ") ");
							append("Me", " --> " + (flag ? "" : "[Delayed] ") + command.substring(1) + "\n");

							textPane1.setCaretPosition(textPane1.getDocument().getLength());
							textArea4.requestFocusInWindow();
							if (command.trim().endsWith("\\\\n")) {
								command.substring(0, command.length() - 6);
							}

							textArea4.setText("");
						}

					} else {

						// textPane1
						append("Time Stamp", "(" + getTime() + ") ");
						append("Me", "Me: ");
						addUrlText((flag ? "" : "[Delayed] ") + textArea4.getText().trim() + "\n");

						textPane1.setCaretPosition(textPane1.getDocument().getLength());
						textArea4.requestFocusInWindow();
						if (msg.trim().endsWith("\\\\n")) {
							msg.substring(0, msg.length() - 6);
						}

						if (flag)
							b.sendMessage(msg);
						else {
							new File(Config.MESSAGE_DIR).mkdirs();
							FileOutputStream fos = new FileOutputStream(Config.MESSAGE_DIR + b.getAddress() + ".txt", true);
							fos.write((msg + "\n").getBytes());
							fos.close();
						}
						textArea4.setText("");
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				textArea4.setText("");
				// clear the text area anyways
			}
		}
	}

	private void textArea4KeyPressed(KeyEvent e) {

		if (e.getKeyCode() == 16) {
			shiftpress = true;
		}
		if (e.getKeyCode() == 10) {
			e.consume();
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - TIm daaa
		scrollPane3 = new JScrollPane();
		textPane1 = new JTextPane();
		scrollPane4 = new JScrollPane();
		textArea4 = new JTextArea();

		// ======== this ========
		Container contentPane = getContentPane();

		// ======== scrollPane3 ========
		{
			scrollPane3.setViewportView(textPane1);
		}

		// ======== scrollPane4 ========
		{

			// ---- textArea4 ----
			textArea4.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					textArea4KeyPressed(e);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					textArea4KeyReleased(e);
				}
			});
			scrollPane4.setViewportView(textArea4);
		}

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createParallelGroup().addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE).addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE));
		contentPaneLayout.setVerticalGroup(contentPaneLayout.createParallelGroup().addGroup(GroupLayout.Alignment.TRAILING,
				contentPaneLayout.createSequentialGroup().addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	public void append(String style, String text) {
		try {
			StyledDocument doc = (StyledDocument) textPane1.getDocument(); // Create a style object and then set the style attributes
			doc.insertString(doc.getLength(), text, doc.getStyle(style));
		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - TIm daaa
	private JScrollPane scrollPane3;
	private JTextPane textPane1;
	private JScrollPane scrollPane4;
	private JTextArea textArea4;

	// JFormDesigner - End of variables declaration //GEN-END:variables

	public JEditorPane getTextPane1() {
		return textPane1;
	}

	public JTextArea getTextArea4() {
		return textArea4;
	}

	public static String getTime() {
		return new SimpleDateFormat("h:mm:ss").format(new Date());
		// return Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
	}

	public Buddy getBuddy() {
		return b;
	}
}
