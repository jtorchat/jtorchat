package fileTransfer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import core.Buddy;
import core.language;



@SuppressWarnings("serial")
public class GUITransfer extends JFrame {

	private Buddy buddy;
	private boolean sending;
	private String fileName;
	@SuppressWarnings("unused")
	private boolean completed;
	private IFileTransfer ift;
	private int startstop;

	public GUITransfer(IFileTransfer ift, Buddy b, String fileName, boolean sending) {
		this.ift = ift;
		this.buddy = b;
		this.fileName = fileName;
		this.sending = sending;
		this.startstop = 0;
		initComponents();
		language();
		label1.setText("<html><br><br>");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (sending)
		addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent e) {
            	if (GUITransfer.this.ift != null)
            		GUITransfer.this.ift.close();
            }
        });
		setSize(getWidth() + 5, getHeight() + 5);
	}

	public void update(long fileSize, long recieved, String status) {
		if (recieved == -1) {
			button1.setText(language.langtext[62]);
			button2.setVisible(false);
		}
		if (sending)
		{
		button2.setVisible(false);
		button3.setVisible(false);	
		button4.setVisible(false);	
		button5.setVisible(false);	
		}
		double perc = Math.round(((double) recieved / (double) fileSize) * 1000) / 10d;
		label1.setText("<html>" + (sending ? "Sending " : "Recieving ") + fileName + "<br>" + (sending ? "to " : "from ") + buddy.toString(true) + "<br>" + perc + "% (" + recieved + " of " + fileSize + " bytes)    " + status);
		setTitle(perc + "% - " + fileName);
		progressBar1.setValue((int) ((double) recieved / (double) fileSize * 100d));
		//Logger.oldOut.println(getClass().getCanonicalName() + ": gui() " + fileSize + ", " + recieved + ", " + status);
		if (fileSize == recieved) {
			button1.setText(language.langtext[62]);
			completed = true;
			if (!sending && ((FileReceiver) ift).fileNameSave != null && ((FileReceiver) ift).fileNameSave.length() > 0) {
				((FileReceiver) ift).close();
				button1.setText(language.langtext[62]);
			}}
		
		if (fileSize == recieved & !sending) {
			button2.setVisible(false);
			button3.setVisible(true);	
			button4.setVisible(true);	
			button5.setVisible(true);	
        }
		else if (fileSize != recieved & !sending) {
			if (recieved == -1) { button2.setVisible(false); } else { button2.setVisible(true); }
			button3.setVisible(false);	
			button4.setVisible(false);	
			button5.setVisible(false);	
		}
		
		
	}

	public void update(long fileSize, long recieved) {
		update(fileSize, recieved, "");
		// Logger.oldOut.println(getClass().getCanonicalName() + ": gui() " + fileSize + ", " + recieved);
	}

	private void opendir(ActionEvent e) {
		ift.close();
		ift.opendir();
		dispose();
	}

	private void cancel(ActionEvent e) {

		ift.close();
		dispose();
	}

	private void open(ActionEvent e) {
		ift.close();
		ift.open();
		dispose();
	}

	void startfirst() {
	button2.setText(language.langtext[63]);
	ift.startstop();
	this.startstop = 1;
	}
	private void startstop(ActionEvent e) {
		ift.startstop();
        if(this.startstop == 0)
        {
        button2.setText(language.langtext[63]);
        this.startstop = 1;
        }
        else
        {
       button2.setText(language.langtext[64]);
       this.startstop = 0;
        }
	}

	private void delete(ActionEvent e) {
		ift.delete();
	}

private void language(){
	
	button2.setText(language.langtext[64]);
	button5.setText(language.langtext[68]);
	button4.setText(language.langtext[67]);
	button3.setText(language.langtext[66]);
	button1.setText(language.langtext[65]);
}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - test test2
		label1 = new JLabel();
		progressBar1 = new JProgressBar();
		button2 = new JButton();
		button5 = new JButton();
		button4 = new JButton();
		button3 = new JButton();
		button1 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 60, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

		//---- label1 ----
		label1.setText("<html><br><br>");
		contentPane.add(label1, new GridBagConstraints(0, 0, 25, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(10, 10, 5, 0), 0, 0));
		contentPane.add(progressBar1, new GridBagConstraints(0, 1, 25, 1, 1.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 10, 5, 10), 0, 0));

		//---- button2 ----
		button2.setText("Start");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startstop(e);
			}
		});
		contentPane.add(button2, new GridBagConstraints(8, 2, 2, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 15, 15), 0, 0));

		//---- button5 ----
		button5.setText("Folder");
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				opendir(e);
			}
		});
		contentPane.add(button5, new GridBagConstraints(10, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 15, 15), 0, 0));

		//---- button4 ----
		button4.setText("Open");
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(e);
			}
		});
		contentPane.add(button4, new GridBagConstraints(11, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 15, 15), 0, 0));

		//---- button3 ----
		button3.setText("Delete");
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete(e);
			}
		});
		contentPane.add(button3, new GridBagConstraints(12, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 15, 15), 0, 0));

		//---- button1 ----
		button1.setText("Cancel");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel(e);
			}
		});
		contentPane.add(button1, new GridBagConstraints(13, 2, 3, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE,
			new Insets(0, 0, 15, 15), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - test test2
	private JLabel label1;
	private JProgressBar progressBar1;
	private JButton button2;
	private JButton button5;
	private JButton button4;
	private JButton button3;
	private JButton button1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
