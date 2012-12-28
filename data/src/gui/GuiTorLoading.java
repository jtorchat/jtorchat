package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import core.language;

@SuppressWarnings("serial")
public class GuiTorLoading extends JFrame {
	public JProgressBar getProgressBar1() {
		return progressBar1;
	}
	public JTextArea gettextArea1() {
		return textArea1;
	}
	public GuiTorLoading() {
		initComponents();
		language();
		addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent e) {
        		System.exit(0);
            }
        });
	}
	
	private void language() {
				textArea1.setText(language.langtext[51]);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - jhgfdf jhgfc
		vSpacer3 = new JPanel(null);
		hSpacer1 = new JPanel(null);
		progressBar1 = new JProgressBar();
		hSpacer2 = new JPanel(null);
		hSpacer3 = new JPanel(null);
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		hSpacer4 = new JPanel(null);
		vSpacer2 = new JPanel(null);

		//======== this ========
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 75, 21, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
		contentPane.add(vSpacer3, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 9), 0, 0));
		contentPane.add(hSpacer1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 9), 0, 0));
		contentPane.add(progressBar1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 9), 0, 0));
		contentPane.add(hSpacer2, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));
		contentPane.add(hSpacer3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 9), 0, 0));

		//======== scrollPane1 ========
		{

			//---- textArea1 ----
			textArea1.setText("Everything works until now.");
			textArea1.setWrapStyleWord(true);
			textArea1.setRows(2);
			textArea1.setLineWrap(true);
			textArea1.setEditable(false);
			scrollPane1.setViewportView(textArea1);
		}
		contentPane.add(scrollPane1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 9), 0, 0));
		contentPane.add(hSpacer4, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 0), 0, 0));
		contentPane.add(vSpacer2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 5, 9), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - jhgfdf jhgfc
	private JPanel vSpacer3;
	private JPanel hSpacer1;
	private JProgressBar progressBar1;
	private JPanel hSpacer2;
	private JPanel hSpacer3;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JPanel hSpacer4;
	private JPanel vSpacer2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
