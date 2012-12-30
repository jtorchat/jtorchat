package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import util.ChatWindow;

import commands.list_of_commands;

import core.Buddy;
import core.Config;

import listeners.LinkController;


import fileTransfer.FileDrop;
import fileTransfer.FileSender;




/**
 * @author Tsu
 */
@SuppressWarnings("serial")
public class GuiChatWindow extends JFrame {

	public Buddy b;
	private Style timestampStyle;
	private Style myNameStyle;
	private Style theirNameStyle;
    private Boolean shiftpress;
	// private Style normalStyle;
    
// Clickable links start

public void addUrlText(String type, String text) {
		
		
if (Config.ClickableLinks == 0)
{
append(type, text);
}
else
{
String[] splittall = text.split(" ");
	
int x=0;
while (x < splittall.length) { 
	
if(splittall[x].startsWith("http://"))
{
try {
	addHyperlink(new URL(splittall[x]), splittall[x].substring(7), Color.blue);
	append(type, " ");
} catch (MalformedURLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} // if the original doesnt have a protocol specified, insert http:// at the beggining
}
else if (splittall[x].startsWith("https://"))
{
try {
	addHyperlink(new URL(splittall[x]), splittall[x].substring(8), Color.blue);
	append(type, " ");
} catch (MalformedURLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} // if the original doesnt have a protocol specified, insert http:// at the beggining
}
else
{
append(type, splittall[x]);

if (x < splittall.length-1){append(type, " ");}
}


x++;                          
}}}

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

	public GuiChatWindow(Buddy b) {
		this.b = b;
		this.shiftpress = false;
		initComponents();
		LinkController lc = new LinkController();
		textPane1.addMouseListener(lc);
		textPane1.addMouseMotionListener(lc);
		
	    new  FileDrop(textPane1, new FileDrop.Listener()
	    {   

			@Override
			public void  filesDropped( java.io.File[] files)
	        {   
				Buddy b = ((GuiChatWindow)(textPane1).getRootPane().getParent()).getBuddy();
				for(int i=0;i<files.length;i++) {
					new FileSender(b, files[i].getAbsolutePath());
				}
	           
	        }

	    }); 

		System.out.println(textPane1.getDocument().getClass().getCanonicalName());
		textPane1.setEditable(false);

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
		if (e.getKeyCode() == 16)
		{
			shiftpress=false;	
		}

	
		if (e.getKeyCode() == 10 & shiftpress) {
			textArea4.setText(textArea4.getText()+"\n");
		}
		
		if (e.getKeyCode() == 10 & !shiftpress) { // enter
			if (!textArea4.getText().trim().equals("")) {
				String msg = textArea4.getText();

				
					boolean right = true;
					if (msg.startsWith("/")) 
					{
                    right = list_of_commands.in_command(b, msg,this);
					}
					if(right){ChatWindow.update_window(1, this,msg,"",msg,!b.isFullyConnected());}
					
				
			} else {textArea4.setText("");}
		}
	}

	private void textArea4KeyPressed(KeyEvent e) {

		if(e.getKeyCode() == 16)
		{
		shiftpress=true;	
		}
		if (e.getKeyCode() == 10)
		{
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

		//======== this ========
		Container contentPane = getContentPane();

		//======== scrollPane3 ========
		{
			scrollPane3.setViewportView(textPane1);
		}

		//======== scrollPane4 ========
		{

			//---- textArea4 ----
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
		contentPaneLayout.setHorizontalGroup(
			contentPaneLayout.createParallelGroup()
				.addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
				.addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
		);
		contentPaneLayout.setVerticalGroup(
			contentPaneLayout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
					.addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
		);
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
	
   public JTextArea get_textArea4() {
	 return textArea4;
   }

   public JTextPane get_textPane1() {
	 return textPane1;
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
