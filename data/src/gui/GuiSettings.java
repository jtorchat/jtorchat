package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import core.Config;
import core.TCPort;
import core.language;

import util.ConfigWriter;




@SuppressWarnings("serial")
public class GuiSettings extends JFrame {
	
	public GuiSettings() {

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initComponents();
		list1 = util.list.addelements(list1,Config.lang,Config.LANG_DIR,".ini");
		language();
		textField4.setText(Config.SOCKS_PORT > 0 ? Config.SOCKS_PORT + "" : "");
		textField5.setText(Config.LOCAL_PORT > 0 ? Config.LOCAL_PORT + "" : "");
		textField6.setText((Config.us != null && Config.us.length() > 0) ? Config.us : "");
		textField7.setText(Config.sync);
		textField8.setText(Config.update);
		if (Config.alert_on_status_change == 1) { checkBox12.setSelected(true); } else { checkBox12.setSelected(false); }
		if (Config.alert_on_message == 1) { checkBox2.setSelected(true); } else { checkBox2.setSelected(false); }
		if (Config.loadTor == 1) { checkBox1.setSelected(true); } else { checkBox1.setSelected(false); }
		if (Config.buddyStart == 1) { checkBox3.setSelected(true); } else { checkBox3.setSelected(false); }
		if (Config.transferonstart == 1) { checkBox4.setSelected(true); } else { checkBox4.setSelected(false); }
		if (Config.pageactive == 1) { checkBox5.setSelected(true); } else { checkBox5.setSelected(false); }
		if (Config.updateStart == 1) { checkBox6.setSelected(true); } else { checkBox6.setSelected(false); }
		if (Config.visiblelog == 1) { checkBox7.setSelected(true); } else { checkBox7.setSelected(false); }
		if (Config.fulllog == 1) { checkBox8.setSelected(true); } else { checkBox8.setSelected(false); }
		if (Config.obfsproxy == 1) { checkBox9.setSelected(true); } else { checkBox9.setSelected(false); }
		if (Config.ClickableLinks == 1) { checkBox10.setSelected(true); } else { checkBox10.setSelected(false); }		
		if (Config.offlineMod == 1) { checkBox11.setSelected(true); } else { checkBox11.setSelected(false); }	
		
		this.addWindowListener(new WindowListener() {
            @Override
			public void windowClosed(WindowEvent e) {}
            @Override
			public void windowActivated(WindowEvent e) {}
            @Override
			public void windowClosing(WindowEvent e) {
        		dispose();
        		synchronized(this) {
        			this.notifyAll(); // tell anyone waiting on us that we're done
        		}
            }
            @Override
			public void windowDeactivated(WindowEvent e) {}
            @Override
			public void windowDeiconified(WindowEvent e) {}
            @Override
			public void windowIconified(WindowEvent e) {}
            @Override
			public void windowOpened(WindowEvent e) {}
        });
	}

	private void ok(ActionEvent e) {

		String sync = textField7.getText();
		String update = textField8.getText();
		boolean alert_on_message = checkBox2.isSelected();
		boolean alert_on_status_change = checkBox12.isSelected();
		boolean loadTor = checkBox1.isSelected();
		boolean buddyStart = checkBox3.isSelected();
		boolean updateStart = checkBox6.isSelected();
		boolean showlog = checkBox7.isSelected();
		boolean fulllog = checkBox8.isSelected();
		boolean file = checkBox4.isSelected();
		boolean page = checkBox5.isSelected();
		boolean obfsproxy = checkBox9.isSelected();
		boolean ClickableLinks = checkBox10.isSelected();
		boolean offlineMod = checkBox11.isSelected();
		String lang = list1.getSelectedValue().toString();
		int sp = -1, lp = -1;
		
		if (textField4.getText().length() != 0) {
			try {
				sp = Integer.parseInt(textField4.getText());
				if (sp < 1) {
					JOptionPane.showMessageDialog(null, textField4.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField4.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (textField5.getText().length() != 0) {
			try {
				lp = Integer.parseInt(textField5.getText());
				if (lp < 1) {
					JOptionPane.showMessageDialog(null, textField5.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, textField5.getText() + " is an invalid number", "Invalid Number", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if (textField6.getText().length() == 16) {
			Config.us = textField6.getText(); // FIXME not fully checking the id
		} else if (textField6.getText().length() != 0) {
			JOptionPane.showMessageDialog(null, textField6.getText() + " is an invalid Id", "Invalid Id", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Config.SOCKS_PORT = sp;
		Config.LOCAL_PORT = lp;
		Config.lang = lang;
		Config.sync = sync;
		Config.update = update;
		if (alert_on_message) { Config.alert_on_message = 1; } else {Config.alert_on_message = 0; };
		if (alert_on_status_change) { Config.alert_on_status_change = 1; } else {Config.alert_on_status_change = 0; } ;
		if (loadTor) { Config.loadTor = 1; } else {Config.loadTor = 0; } ;
		if (buddyStart) { Config.buddyStart = 1; } else {Config.buddyStart = 0; } ;
		if (updateStart) { Config.updateStart = 1; } else {Config.updateStart = 0; } ;
		if (file) { Config.transferonstart = 1; } else {Config.transferonstart = 0; } ;
		if (page) { Config.pageactive = 1; } else {Config.pageactive = 0; } ;
		if (showlog) { Config.visiblelog = 1; } else {Config.visiblelog = 0; } ;
		if (fulllog) { Config.fulllog = 1; } else {Config.fulllog = 0; } ;
		if (obfsproxy) { Config.obfsproxy = 1; } else {Config.obfsproxy = 0; } ;		
		if (ClickableLinks) { Config.ClickableLinks = 1; } else {Config.ClickableLinks = 0; } ;
		if (offlineMod) { Config.offlineMod = 1; } else {Config.offlineMod = 0; } ;
		

	    
	    TCPort.sendMyProfil();
	    ConfigWriter.saveall(0);
		dispose();
		synchronized(this) {
			this.notifyAll(); // tell anyone waiting on us that we're done
		}
	}

	public JTabbedPane getTabbedPane1() {
		return tabbedPane1;
	}

	private void update(ActionEvent e) {
		Config.nowstartupdate = textField8.getText();
		Config.allcheckupdate = 1;
	}

	private void sync(ActionEvent e) {
		Config.nowstart = textField7.getText();
	}

	private void language()
	{

		checkBox11.setText("offlineMod (start jtorchat without any internet connection)");
		checkBox12.setText("Alert on status change");
		checkBox2.setText(language.langtext[20]);
		checkBox4.setText(language.langtext[21]);
		checkBox5.setText(language.langtext[22]);
		checkBox7.setText(language.langtext[23]);
		checkBox8.setText(language.langtext[24]);
		checkBox10.setText(language.langtext[41]);
		label4.setText(language.langtext[25]);
		label5.setText(language.langtext[26]);
		label6.setText(language.langtext[27]);
		checkBox1.setText(language.langtext[28]);
		checkBox9.setText(language.langtext[38]);
		label3.setText(language.langtext[29]);
		label7.setText(language.langtext[30]);
		label8.setText(language.langtext[31]);
		button2.setText(language.langtext[33]);
		checkBox3.setText(language.langtext[32]);
		label9.setText(language.langtext[34]);
		button3.setText(language.langtext[35]);
		checkBox6.setText(language.langtext[36]);;


	
	tabbedPane1.removeAll();
	tabbedPane1.addTab(language.langtext[16], panel1);
	tabbedPane1.addTab(language.langtext[17], panel2);
	tabbedPane1.addTab(language.langtext[18], panel3);
	tabbedPane1.addTab(language.langtext[19], panel4);
	tabbedPane1.addTab(language.langtext[45], panel5);


	}

	private void getlanginfo(String file) {
		String[] info = core.language.getinfo(file);
		textField3.setText(info[0]);
		textField11.setText(info[1]);
		textField12.setText(info[2]);
	}

	private void list1ValueChanged(ListSelectionEvent e) {
	 getlanginfo(list1.getSelectedValue().toString());
     Config.lang = list1.getSelectedValue().toString();
	}
	
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		checkBox12 = new JCheckBox();
		checkBox2 = new JCheckBox();
		checkBox4 = new JCheckBox();
		checkBox5 = new JCheckBox();
		checkBox7 = new JCheckBox();
		checkBox8 = new JCheckBox();
		checkBox10 = new JCheckBox();
		panel3 = new JPanel();
		label8 = new JLabel();
		textField7 = new JTextField();
		button2 = new JButton();
		checkBox3 = new JCheckBox();
		panel4 = new JPanel();
		label9 = new JLabel();
		textField8 = new JTextField();
		button3 = new JButton();
		checkBox6 = new JCheckBox();
		panel5 = new JPanel();
		textField3 = new JTextField();
		textField11 = new JTextField();
		textField12 = new JTextField();
		scrollPane1 = new JScrollPane();
		list1 = new JList();
		label10 = new JLabel();
		panel2 = new JPanel();
		label4 = new JLabel();
		textField4 = new JTextField();
		label5 = new JLabel();
		textField5 = new JTextField();
		label6 = new JLabel();
		textField6 = new JTextField();
		checkBox1 = new JCheckBox();
		checkBox9 = new JCheckBox();
		label3 = new JLabel();
		label7 = new JLabel();
		checkBox11 = new JCheckBox();
		button1 = new JButton();

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
		((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
		((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
		((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

		//======== tabbedPane1 ========
		{

			//======== panel1 ========
			{


				//---- checkBox2 ----
				checkBox2.setText("alert on message");
				checkBox2.setPreferredSize(new Dimension(60, 18));
				checkBox2.setMinimumSize(new Dimension(30, 18));
				
				checkBox12.setText("alert on status change");
				checkBox12.setPreferredSize(new Dimension(60, 18));
				checkBox12.setMinimumSize(new Dimension(30, 18));
				//---- checkBox4 ----
				checkBox4.setText("automatic File Transfer start");

				//---- checkBox5 ----
				checkBox5.setText("page function");

				//---- checkBox7 ----
				checkBox7.setText("show log on start");

				//---- checkBox8 ----
				checkBox8.setText("activate full log (fatal over time)");

				//---- checkBox10 ----
				checkBox10.setText("Clickable Links (open with broswer)");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup()
								.addComponent(checkBox12, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox2, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox4, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox5, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox7, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox8, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox10, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(52, Short.MAX_VALUE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addGap(22, 22, 22)
							.addComponent(checkBox12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(checkBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(checkBox4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(checkBox5)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(checkBox7)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(checkBox8)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(checkBox10)
							.addContainerGap())
				);
			}
			tabbedPane1.addTab("General", panel1);


			//======== panel3 ========
			{

				//---- label8 ----
				label8.setText("URL to buddylist");
				label8.setHorizontalAlignment(SwingConstants.RIGHT);

				//---- textField7 ----
				textField7.setPreferredSize(new Dimension(180, 28));

				//---- button2 ----
				button2.setText("sync");
				button2.setMaximumSize(new Dimension(100, 23));
				button2.setMinimumSize(new Dimension(100, 23));
				button2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sync(e);
					}
				});

				//---- checkBox3 ----
				checkBox3.setText("sync at every start");
				checkBox3.setPreferredSize(new Dimension(60, 18));
				checkBox3.setMinimumSize(new Dimension(30, 18));

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(panel3Layout.createSequentialGroup()
							.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addGroup(panel3Layout.createSequentialGroup()
									.addContainerGap()
									.addComponent(checkBox3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(panel3Layout.createSequentialGroup()
									.addGap(12, 12, 12)
									.addComponent(textField7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(label8, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
								.addGroup(GroupLayout.Alignment.LEADING, panel3Layout.createSequentialGroup()
									.addGap(130, 130, 130)
									.addComponent(button2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(panel3Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label8)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(textField7, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addGap(18, 18, 18)
							.addComponent(button2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(44, 44, 44)
							.addComponent(checkBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(52, 52, 52))
				);
			}
			tabbedPane1.addTab("Buddy Sync", panel3);


			//======== panel4 ========
			{

				//---- label9 ----
				label9.setText("URL for check");
				label9.setHorizontalAlignment(SwingConstants.RIGHT);
				label9.setAlignmentY(0.0F);
				label9.setHorizontalTextPosition(SwingConstants.CENTER);

				//---- textField8 ----
				textField8.setPreferredSize(new Dimension(180, 28));

				//---- button3 ----
				button3.setText("check");
				button3.setMaximumSize(new Dimension(100, 23));
				button3.setMinimumSize(new Dimension(100, 23));
				button3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						update(e);
					}
				});

				//---- checkBox6 ----
				checkBox6.setText("check for update at every start");
				checkBox6.setPreferredSize(new Dimension(60, 18));
				checkBox6.setMinimumSize(new Dimension(30, 18));

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
						.addGroup(panel4Layout.createSequentialGroup()
							.addGroup(panel4Layout.createParallelGroup()
								.addGroup(panel4Layout.createSequentialGroup()
									.addGap(122, 122, 122)
									.addComponent(button3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
								.addGroup(panel4Layout.createSequentialGroup()
									.addContainerGap()
									.addGroup(panel4Layout.createParallelGroup()
										.addComponent(label9, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
										.addComponent(textField8, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)))
								.addGroup(panel4Layout.createSequentialGroup()
									.addContainerGap()
									.addComponent(checkBox6, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)))
							.addContainerGap())
				);
				panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
						.addGroup(panel4Layout.createSequentialGroup()
							.addGap(8, 8, 8)
							.addComponent(label9, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textField8, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addGap(18, 18, 18)
							.addComponent(checkBox6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(30, 30, 30)
							.addComponent(button3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(51, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Update Check", panel4);


			//======== panel5 ========
			{

				//---- textField3 ----
				textField3.setEditable(false);

				//---- textField11 ----
				textField11.setEditable(false);

				//---- textField12 ----
				textField12.setEditable(false);

				//======== scrollPane1 ========
				{

					//---- list1 ----
					list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					list1.addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent e) {
							list1ValueChanged(e);
						}
					});
					scrollPane1.setViewportView(list1);
				}

				//---- label10 ----
				label10.setText("Please choose a language.");

				GroupLayout panel5Layout = new GroupLayout(panel5);
				panel5.setLayout(panel5Layout);
				panel5Layout.setHorizontalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createSequentialGroup()
							.addContainerGap(20, Short.MAX_VALUE)
							.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(textField3, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
								.addComponent(textField11, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
								.addComponent(textField12, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
								.addComponent(label10, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
				panel5Layout.setVerticalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createSequentialGroup()
							.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addGroup(panel5Layout.createSequentialGroup()
									.addGap(25, 25, 25)
									.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
								.addGroup(panel5Layout.createSequentialGroup()
									.addGap(35, 35, 35)
									.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18, 18, 18)
									.addComponent(textField11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18, 18, 18)
									.addComponent(textField12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label10)))
							.addContainerGap(16, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Language", panel5);


			//======== panel2 ========
			{

				//---- label4 ----
				label4.setText("* Tor socks port: ");
				label4.setHorizontalAlignment(SwingConstants.RIGHT);

				//---- textField4 ----
				textField4.setPreferredSize(new Dimension(180, 28));

				//---- label5 ----
				label5.setText("* Local port: ");
				label5.setHorizontalAlignment(SwingConstants.RIGHT);

				//---- textField5 ----
				textField5.setPreferredSize(new Dimension(180, 28));

				//---- label6 ----
				label6.setText("* Our ID: ");
				label6.setHorizontalAlignment(SwingConstants.RIGHT);

				//---- textField6 ----
				textField6.setPreferredSize(new Dimension(180, 28));

				//---- checkBox1 ----
				checkBox1.setText("Initial Tor Portable at start");
				checkBox1.setPreferredSize(new Dimension(60, 18));
				checkBox1.setMinimumSize(new Dimension(30, 18));

				//---- checkBox9 ----
				checkBox9.setText("obfsproxy (portable only)");

				//---- label3 ----
				label3.setText("Note: Our ID is your hostname without .onion");

				//---- label7 ----
				label7.setText("* Required");

				//---- checkBox11 ----
				checkBox11.setText("offlineMod (start jtorchat without any internet connection)");

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addGroup(panel2Layout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addContainerGap()
									.addComponent(label4, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(textField4, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(label5, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
										.addComponent(label6, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(panel2Layout.createParallelGroup()
										.addComponent(textField6, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField5, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
								.addGroup(panel2Layout.createSequentialGroup()
									.addGap(84, 84, 84)
									.addComponent(label7, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
								.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
									.addGap(13, 13, 13)
									.addComponent(label3, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
								.addComponent(checkBox11, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
								.addComponent(checkBox9, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
								.addComponent(checkBox1, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
							.addContainerGap())
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addGap(10, 10, 10)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(textField4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(label4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(5, 5, 5)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(5, 5, 5)
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label6, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField6, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addGap(9, 9, 9)
							.addComponent(checkBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(checkBox9)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(checkBox11)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				);
			}
			tabbedPane1.addTab("Advanced", panel2);

		}
		contentPane.add(tabbedPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(0, 0, 0, 0), 0, 0));

		//---- button1 ----
		button1.setText("Ok");
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ok(e);
			}
		});
		contentPane.add(button1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
			GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
			new Insets(0, 0, 10, 10), 0, 0));
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - dfddfd dfdfdf
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JCheckBox checkBox12;
	private JCheckBox checkBox2;
	private JCheckBox checkBox4;
	private JCheckBox checkBox5;
	private JCheckBox checkBox7;
	private JCheckBox checkBox8;
	private JCheckBox checkBox10;
	private JPanel panel3;
	private JLabel label8;
	private JTextField textField7;
	private JButton button2;
	private JCheckBox checkBox3;
	private JPanel panel4;
	private JLabel label9;
	private JTextField textField8;
	private JButton button3;
	private JCheckBox checkBox6;
	private JPanel panel5;
	private JTextField textField3;
	private JTextField textField11;
	private JTextField textField12;
	private JScrollPane scrollPane1;
	private JList list1;
	private JLabel label10;
	private JPanel panel2;
	private JLabel label4;
	private JTextField textField4;
	private JLabel label5;
	private JTextField textField5;
	private JLabel label6;
	private JTextField textField6;
	private JCheckBox checkBox1;
	private JCheckBox checkBox9;
	private JLabel label3;
	private JLabel label7;
	private JCheckBox checkBox11;
	private JButton button1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
