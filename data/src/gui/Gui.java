package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import commands.list_of_commands;

import core.APIManager;
import core.Buddy;
import core.BuddyList;
import core.Config;
import core.Logger;
import core.TCPort;
import core.language;

import util.ChatWindow;
import util.TCIconRenderer;
import util.Tray;


import listeners.APIListener;

public class Gui {

	private Listener listener;
	public static JFrame f;
	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode buddyNodeholy;
	private static DefaultMutableTreeNode buddyNodeon;
	private static DefaultMutableTreeNode buddyNode;
	private static DefaultMutableTreeNode buddyNodeblack;
	private static HashMap<String, MutableTreeNode> nodeMap = new HashMap<String, MutableTreeNode>();
	private static HashMap<String, GuiChatWindow> windowMap = new HashMap<String, GuiChatWindow>();
	private static JTree jt;
	private GuiAlert alert;
	public static Gui instance;
	public HashMap<String, GuiListener> cmdListeners = new HashMap<String, GuiListener>();
	public int extraSpace;

	public void init() {
		int w = 268, h = -1;
		if (listener != null) {
			Logger.log(Logger.WARNING, this.getClass(), "init(V)V called twice?");
			return;
		}
		instance = this;
		setLAF("Nimbus");

		if ((TCPort.profile_name == null && TCPort.profile_text == null) || Config.firststart == 1) {
			Logger.log(Logger.WARNING, this.getClass(), "Start setting window");
	
			GuiSettings guis = new GuiSettings();
			guis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			guis.setVisible(true);
			try {
				synchronized (guis) {
					guis.wait();
				}
			} catch (InterruptedException e) {
				// ignored
			}
		} else if (Config.SOCKS_PORT < 1 || Config.LOCAL_PORT < 1 || Config.us == null) {
			Logger.log(Logger.WARNING, this.getClass(), "Start setting window on advanced");
			GuiSettings guis = new GuiSettings();
			guis.getTabbedPane1().setSelectedIndex(1);
			guis.setVisible(true);
			try {
				synchronized (guis) {
					guis.wait();
				}
			} catch (InterruptedException e) {
				// ignored
			}
		}

		/**
		 * extraSpace notes. 0 - should be fine for metal LAF 4 - should be fine for Nimbus
		 * 
		 * TODO - really should find a proper fix for this.
		 */
		extraSpace = 4;
		listener = new Listener();
		APIManager.addEventListener(listener);
		f = new JFrame(Config.us + " - Buddy List");
		f.setLayout(new BorderLayout());
		
		// Change HIDE_ON_CLOSE or EXIT_ON_CLOSE when it works
        Tray.init();

		JMenuBar jmb = new JMenuBar();
		JMenu jmStatus = new JMenu(language.langtext[1]);
		JMenu jmHelp = new JMenu(language.langtext[2]);
		final JMenuItem jmiHelpLink = new JMenuItem(language.langtext[11]);
		final JMenuItem jmiVersionName = new JMenuItem("Version");
		jmiHelpLink.addActionListener(new ActionListener() { // note - the link is copiable so as to not open the link in the users normal browser automatically which could tip off anyone sniffing the network that they are using jtorcat

					@Override
					public void actionPerformed(ActionEvent e) {
						JTextField jtf = new JTextField();
						jtf.setEditable(false);
						jtf.setText("https://github.com/jtorchat/jtorchat/wiki");
						JOptionPane.showMessageDialog(null, jtf, "Wiki link", JOptionPane.PLAIN_MESSAGE);

					}
				});
		jmHelp.add(jmiHelpLink);
		
		
		JMenuItem jmiLog = new JMenuItem(language.langtext[12]);
		jmiLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiLog.instance.setVisible(!GuiLog.instance.isVisible());
			}
		});
		jmHelp.add(jmiLog);


		jmiVersionName.addActionListener(new ActionListener() { // note - the link is copiable so as to not open the link in the users normal browser automatically which could tip off anyone sniffing the network that they are using jtorcat

			public void actionPerformed(ActionEvent e) {
				JTextField jtf = new JTextField();
				jtf.setEditable(false);
				jtf.setText(Config.CLIENT+" "+Config.VERSION);
				JOptionPane.showMessageDialog(null, jtf, "Version", JOptionPane.PLAIN_MESSAGE);

			}
		});
	    jmHelp.add(jmiVersionName);
		
		final JMenuItem jmion = new JMenuItem(language.langtext[7]);
		jmion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.updateStatus = 1;
				TCPort.sendMyStatus();
			}
		});
		jmStatus.add(jmion);

		final JMenuItem jmiaway = new JMenuItem(language.langtext[9]);
		jmiaway.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.updateStatus = 2;
				TCPort.sendMyStatus();
			}
		});
		jmStatus.add(jmiaway);

		final JMenuItem jmixa = new JMenuItem(language.langtext[10]);
		jmixa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.updateStatus = 3;
				TCPort.sendMyStatus();
			}
		});
		jmStatus.add(jmixa);

		JMenu jmFile = new JMenu(language.langtext[0]);

		JMenuItem jmiAddContact = new JMenuItem(language.langtext[3]);
		JMenuItem jmiSettings = new JMenuItem(language.langtext[4]);
		JMenuItem jmiGUISettings = new JMenuItem(language.langtext[80]);
		JMenuItem jmiProfileSettings = new JMenuItem(language.langtext[81]);
		JMenuItem jmiExit = new JMenuItem(language.langtext[5]);

		
		
		jmiAddContact.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiContactAdd guica = new GuiContactAdd();
				// guica.setBuddy(null); // for when editing a buddy
				guica.setVisible(true);
			}
		});
		jmiSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JOptionPane.showMessageDialog(null, "Not Implemented", "Not Implemented", JOptionPane.ERROR_MESSAGE);
				GuiSettings guis = new GuiSettings();
				guis.setVisible(true);
			}
		});
		jmiGUISettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GuiIcon guis = new GuiIcon();
				guis.setVisible(true);
			}
		});
		jmiProfileSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				GuiProfile guis = new GuiProfile(null,true);
				guis.setVisible(true);
			}
		});
		jmiExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		jmFile.add(jmiAddContact);
		jmFile.add(jmiSettings);
		jmFile.add(jmiGUISettings);
		jmFile.add(jmiProfileSettings);
		jmFile.add(new JSeparator());
		jmFile.add(jmiExit);
		jmb.add(jmFile);
		jmb.add(jmStatus);
		jmb.add(jmHelp);
		f.setJMenuBar(jmb);

		JScrollPane jsp = new JScrollPane();
		jsp.setHorizontalScrollBar(null); // no horizontal scrollbar
		jt = new JTree();
		jsp.getViewport().add(jt);
		f.getContentPane().add(jsp, BorderLayout.CENTER);

		root = new DefaultMutableTreeNode("[root]");
		buddyNodeholy = new DefaultMutableTreeNode(language.langtext[40]);
		buddyNodeon = new DefaultMutableTreeNode(language.langtext[7]);
		buddyNode = new DefaultMutableTreeNode(language.langtext[8]);
		buddyNodeblack = new DefaultMutableTreeNode(language.langtext[39]);
		root.add(buddyNodeholy);
		root.add(buddyNodeon);
		root.add(buddyNode);
		root.add(buddyNodeblack);
		jt.setModel(new DefaultTreeModel(root));
		jt.setLargeModel(true);
		jt.setRootVisible(false);
		jt.setCellRenderer(new TCIconRenderer(jt));

		jt.setRowHeight(Config.icon_size + Config.icon_space);
		ToolTipManager.sharedInstance().registerComponent(jt);

		jt.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int selRow = jt.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = jt.getPathForLocation(e.getX(), e.getY());
				if (selRow != -1) {
					if (e.getClickCount() == 2 && !e.isPopupTrigger()) {
						doAction(selPath);
						// myDoubleClick(selRow, selPath);
					}
				}
				if (e.isPopupTrigger()) // works only here on Linux
				{
					doPopup(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) // works only here on Windows
				{
					doPopup(e);
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		f.pack();
		if (h == -1)
			h = f.getHeight();
		f.setSize(w, h);
		f.setVisible(true);
	}

	public void setVisible(boolean b) {
		f.setVisible(b);
	}

	public boolean isVisible() {
		return f.isVisible();
	}

	public static void setLAF(String string) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (string.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
	}


	private JMenuItem getMenuItem(String s, ActionListener al) {
		JMenuItem menuItem = new JMenuItem(s);
		menuItem.setActionCommand(s.toUpperCase());
		menuItem.addActionListener(al);
		return menuItem;
	}

	protected void doPopup(MouseEvent e) {
		final int x = e.getX();
		final int y = e.getY();
		JTree tree = (JTree) e.getSource();
		TreePath path = tree.getPathForLocation(x, y);
		if (path == null)
			return;

		tree.setSelectionPath(path);
		JPopupMenu popup = new JPopupMenu();
		DefaultMutableTreeNode d = (DefaultMutableTreeNode) path.getLastPathComponent();
		final Object o = d.getUserObject();
		if (o instanceof Buddy) {

			popup.add(getMenuItem(language.langtext[73], new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// String ac = e.getActionCommand();
					// TreePath path = jt.getPathForLocation(x, y);
					openChatWindow((Buddy) o);
				}
			}));
			
			
			popup.add(getMenuItem("Display Profile", new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					GuiProfile guis = new GuiProfile((Buddy) o,false);
					guis.setVisible(true);
				}
			}));

			if (((Buddy) o).getBlack()) {
				popup.add(getMenuItem(language.langtext[75], new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						BuddyList.black.remove(((Buddy) o).getAddress());
						pardon(((Buddy) o));
					}
				}));
			} else {
				if (((Buddy) o).getHoly()) {

					popup.add(getMenuItem("Not Holy contact", new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							BuddyList.holy.remove(((Buddy) o).getAddress());
							pardon(((Buddy) o));
						}
					}));
				} else {
					popup.add(getMenuItem("Holy contact.", new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							BuddyList.addHoly(((Buddy) o));
						}
					}));
					
					popup.add(getMenuItem(language.langtext[76], new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							BuddyList.addBlack(((Buddy) o));
							if (getChatWindow(((Buddy) o), false, false) != null) {
								windowMap.remove(((Buddy) o).getAddress()).dispose();
							}
						}
					}));
				}
			}

			popup.add(getMenuItem(language.langtext[77], new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						((Buddy) o).remove();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}));

			popup.add(new JPopupMenu.Separator());
		}

		popup.show(tree, x, y);
	}

	public static Gui getInstance() {
		return instance;
	}

	private void doAction(TreePath x) {
		DefaultMutableTreeNode o = (DefaultMutableTreeNode) x.getLastPathComponent();
		if (o.getUserObject() instanceof Buddy) {
			Buddy b = (Buddy) o.getUserObject();
			openChatWindow(b);
		}
	}

	private void openChatWindow(Buddy b) {
		if (!b.getBlack()) {
			getChatWindow(b, true, true).toFront();
		}
	}

	public static GuiChatWindow getChatWindow(Buddy b, boolean createIfNotExist, boolean setVis) {
		GuiChatWindow w = windowMap.get(b.getAddress());
		if (w == null && !createIfNotExist)
			return null;
		if (w == null) {
			w = new GuiChatWindow(b);
			windowMap.put(b.getAddress(), w);
		}
        w.setTitle(b.toString(true));

		w.setFocusableWindowState(false);
		if (setVis)
			w.setVisible(true);

		w.setFocusableWindowState(true);
		return w;
	}

	public static void blacklist(Buddy buddy) {

		MutableTreeNode node = nodeMap.remove(buddy.getAddress());
		if (node != null) // remove entry in the gui
			((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

		node = nodeMap.get(buddy.getAddress());
		if (node != null)
			node.removeFromParent();
		nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

		if (buddy.getAddress().equals(Config.us)) {
			((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeblack, 0);
		} else {
			((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeblack, buddyNodeblack.getChildCount());
		}

		if (buddyNodeblack.getChildCount() == 1) {
			jt.expandRow(0);
		}

	}

	public static void holylist(Buddy buddy) {
		if (buddy.isFullyConnected()) {
			MutableTreeNode node = nodeMap.remove(buddy.getAddress());
			if (node != null) // remove entry in the gui
				((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

			node = nodeMap.get(buddy.getAddress());
			if (node != null)
				node.removeFromParent();
			nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

			if (buddy.getAddress().equals(Config.us)) {
				((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeholy, 0);
			} else {
				((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeholy, buddyNodeholy.getChildCount());
			}

			if (buddyNodeholy.getChildCount() == 1) {
				jt.expandRow(0);
			}
		}

	}

	public static void pardon(Buddy buddy) {

		if (buddy.getStatus() >= Buddy.ONLINE) {

			MutableTreeNode node = nodeMap.remove(buddy.getAddress());
			if (node != null) // remove entry in the gui
				((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

			node = nodeMap.get(buddy.getAddress());
			if (node != null)
				node.removeFromParent();
			nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

			if (buddy.getHoly()) {
				if (buddy.getAddress().equals(Config.us)) {
					((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeholy, 0);
				} else {
					((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeholy, buddyNodeholy.getChildCount());
				}
				if (buddyNodeholy.getChildCount() == 1) {
					jt.expandRow(0);
				}
			} else {
				if (buddy.getAddress().equals(Config.us)) {
					((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeon, 0);
				} else {
					((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeon, buddyNodeon.getChildCount());
				}
				if (buddyNodeon.getChildCount() == 1) {
					jt.expandRow(0);
				}
			}

		} else {

			MutableTreeNode node = nodeMap.remove(buddy.getAddress());
			if (node != null) // remove entry in the gui
				((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

			node = nodeMap.get(buddy.getAddress());
			if (node != null)
				node.removeFromParent();
			nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

			if (buddy.getAddress().equals(Config.us)) {
				((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNode, 0);
			} else {
				((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNode, buddyNode.getChildCount());
			}

			if (buddyNode.getChildCount() == 1) {
				jt.expandRow(0);
			}

		}

	}

	private class Listener implements APIListener {

		@Override
		public void onStatusChange(Buddy buddy, byte newStatus, byte oldStatus) {
			jt.repaint();
			Logger.oldOut.println(buddy + " changed from " + Buddy.getStatusName(oldStatus) + " to " + Buddy.getStatusName(newStatus));
			
			
			if (newStatus == Buddy.ONLINE && oldStatus != newStatus) {
				if(Config.alert_on_status_change==1){
					GuiAlert alert;
					alert = new GuiAlert(buddy.toString() + " is online.");
					alert.start();
					}}

			if (newStatus == Buddy.AWAY && oldStatus != newStatus) {
				if(Config.alert_on_status_change==1){
					GuiAlert alert;
					alert = new GuiAlert(buddy.toString() + " is away.");
					alert.start();
					}}
			
			if (newStatus == Buddy.XA && oldStatus != newStatus) {
				if(Config.alert_on_status_change==1){
					GuiAlert alert;
					alert = new GuiAlert(buddy.toString() + " is far away.");
					alert.start();
					}}
			
			if (newStatus >= Buddy.ONLINE && oldStatus <= Buddy.HANDSHAKE) {
				
				if (!BuddyList.black.containsKey(buddy.getAddress())) {
					MutableTreeNode node = nodeMap.remove(buddy.getAddress());
					if (node != null) // remove entry in the gui
						((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

					node = nodeMap.get(buddy.getAddress());
					if (node != null)
						node.removeFromParent();
					nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));
					
					if (buddy.getHoly()) {
						if (buddy.getAddress().equals(Config.us)) {
							((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeholy, 0);
						} else {
							((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeholy, buddyNodeholy.getChildCount());
						}

						if (buddyNodeholy.getChildCount() == 1) {
							jt.expandRow(0);
						}
					} else {
						if (buddy.getAddress().equals(Config.us)) {
							((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeon, 0);
						} else {
							((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNodeon, buddyNodeon.getChildCount());
						}

						if (buddyNodeon.getChildCount() == 1) {
							jt.expandRow(0);
						}
					}

					if (new File(Config.MESSAGE_DIR + buddy.getAddress() + ".txt").exists()) {
						try {
							Scanner sc = new Scanner(new FileInputStream(Config.MESSAGE_DIR + buddy.getAddress() + ".txt"));
							while (sc.hasNextLine()) {
								try {
									buddy.sendMessage(sc.nextLine());
								} catch (IOException ioe) {
									buddy.disconnect();
									break;
								}
							}
							sc.close();
							new File(Config.MESSAGE_DIR + buddy.getAddress() + ".txt").delete();
							getChatWindow(buddy, true, true).append("Time Stamp", "Delayed messages sent.\n");
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}

				}

			} else if (oldStatus >= Buddy.ONLINE && newStatus <= Buddy.HANDSHAKE) {

				if(Config.alert_on_status_change==1){
				GuiAlert alert;
				alert = new GuiAlert(buddy.toString() + " is offline");
				alert.start();
				}
				
				if (!BuddyList.black.containsKey(buddy.getAddress())) {
					MutableTreeNode node = nodeMap.remove(buddy.getAddress());
					if (node != null) // remove entry in the gui
						((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

					node = nodeMap.get(buddy.getAddress());
					if (node != null)
						node.removeFromParent();
					nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

					if (buddy.getAddress().equals(Config.us)) {
						((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNode, 0);
					} else {
						((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNode, buddyNode.getChildCount());
					}

					if (buddyNode.getChildCount() == 1) {
						jt.expandRow(0);
					}
				}
			}

		}

		@Override
		public void onProfileNameChange(Buddy buddy, String newName, String oldName) {
			jt.repaint();
			jt.setCellRenderer(null); // this is stupid, but it works
			jt.setCellRenderer(new TCIconRenderer(jt));
		}

		@Override
		public void onProfileTextChange(Buddy buddy, String newText, String oldText) {

		}

		@Override
		public void onAddMe(Buddy buddy) {
			jt.repaint();
		}

		@Override
		public void onMessage(Buddy buddy, String s) {
			GuiChatWindow w = getChatWindow(buddy, true, true);
			String msg = s.trim().replaceAll("\\\\n", "\n").replaceAll("\r", "");
					
			boolean right = true;
			if (msg.startsWith("/")) 
			{
			right=list_of_commands.out_command(buddy, msg,w,false);
			} 
			else if(msg.startsWith("[Delayed] /"))
			{
			right=list_of_commands.out_command(buddy, msg.substring(10),w,true);
			}
			
			if(right){ChatWindow.update_window(2, w,msg,w.get_textArea4().getText(),"",!buddy.isFullyConnected());}
			
		if(Config.alert_on_message==1){
			if (!w.isFocused() && !buddy.getBlack()) {
				if (alert != null && !alert.isFinished()){alert.kill();}
				alert = new GuiAlert("New Message: "+buddy.toString());
				alert.start();
			}}
			
		}

		@Override
		public void onBuddyRemoved(Buddy buddy) {
			MutableTreeNode node = nodeMap.remove(buddy.getAddress());
			if (node != null) // remove entry in the gui
				((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);
			if (getChatWindow(buddy, false, false) != null) {
				windowMap.remove(buddy.getAddress()).dispose();
			}
		}

		@Override
		public void onNewBuddy(Buddy buddy) {
			MutableTreeNode node = nodeMap.get(buddy.getAddress());
			if (node != null)
				node.removeFromParent();
			nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

			if (buddy.getAddress().equals(Config.us)) {
				((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNode, 0);
			} else {
				((DefaultTreeModel) jt.getModel()).insertNodeInto(node, buddyNode, buddyNode.getChildCount());
			}

			if (buddyNode.getChildCount() == 1) {
				jt.expandRow(0);
			}
		}
	}

	public JMenu getFileMenu() {
		return f.getJMenuBar().getMenu(0);
	}

}
