package gui;

import java.awt.BorderLayout;
import java.awt.SystemTray;
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
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import alpha.APIManager;
import alpha.Buddy;
import alpha.BuddyList;
import alpha.Config;
import alpha.Logger;
import alpha.TCPort;
import alpha.language;
import broadcast.BGui;

import listeners.APIListener;

public class Gui {

	private Listener listener;
	private JFrame f;
	private static DefaultMutableTreeNode root;
	private static DefaultMutableTreeNode buddyNodeholy;
	private static DefaultMutableTreeNode buddyNodeon;
	private static DefaultMutableTreeNode buddyNode;
	private static DefaultMutableTreeNode buddyNodeblack;
	private static HashMap<String, MutableTreeNode> nodeMap = new HashMap<String, MutableTreeNode>();
	private static HashMap<String, ChatWindow> windowMap = new HashMap<String, ChatWindow>();
	private static JTree jt;
	private Alert alert;
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
			Config.prop.put("firststart", "0");

			GUISettings guis = new GUISettings();
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
			GUISettings guis = new GUISettings();
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
		f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		if (SystemTray.isSupported())
			Tray.init();
		else
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar jmb = new JMenuBar();
		JMenu jmStatus = new JMenu(language.langtext[1]);
		JMenu jmBroad = new JMenu("Broadcast");
		JMenu jmHelp = new JMenu(language.langtext[2]);
		final JMenuItem jmiHelpLink = new JMenuItem(language.langtext[11]);
		jmiHelpLink.addActionListener(new ActionListener() { // note - the link is copiable so as to not open the link in the users normal browser automatically which could tip off anyone sniffing the network that they are using jtorcat

					@Override
					public void actionPerformed(ActionEvent e) {
						JTextField jtf = new JTextField();
						jtf.setEditable(false);
						jtf.setText("http://code.google.com/p/jtorchat/w/list");
						JOptionPane.showMessageDialog(null, jtf, "Wiki link", JOptionPane.PLAIN_MESSAGE);

					}
				});
		jmHelp.add(jmiHelpLink);

		JMenuItem jmiLog = new JMenuItem(language.langtext[12]);
		jmiLog.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Log.instance.setVisible(!Log.instance.isVisible());
			}
		});
		jmHelp.add(jmiLog);

		JMenuItem jmibroad = new JMenuItem("Chat");
		jmibroad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BGui bgui = new BGui();
				bgui.setVisible(true);
			}
		});
		jmBroad.add(jmibroad);

		final JMenuItem jmion = new JMenuItem(language.langtext[7]);
		jmion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.updateStatus = 1;
			}
		});
		jmStatus.add(jmion);

		final JMenuItem jmiaway = new JMenuItem(language.langtext[9]);
		jmiaway.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.updateStatus = 2;
			}
		});
		jmStatus.add(jmiaway);

		final JMenuItem jmixa = new JMenuItem(language.langtext[10]);
		jmixa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.updateStatus = 3;
			}
		});
		jmStatus.add(jmixa);

		JMenu jmFile = new JMenu(language.langtext[0]);

		JMenuItem jmiAddContact = new JMenuItem(language.langtext[3]);
		JMenuItem jmiSettings = new JMenuItem(language.langtext[4]);
		JMenuItem jmiGUISettings = new JMenuItem("GUI Settings");
		JMenuItem jmiExit = new JMenuItem(language.langtext[5]);

		jmiAddContact.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GUIContactAdd guica = new GUIContactAdd();
				// guica.setBuddy(null); // for when editing a buddy
				guica.setVisible(true);
			}
		});
		jmiSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JOptionPane.showMessageDialog(null, "Not Implemented", "Not Implemented", JOptionPane.ERROR_MESSAGE);
				GUISettings guis = new GUISettings();
				guis.setVisible(true);
			}
		});
		jmiGUISettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// JOptionPane.showMessageDialog(null, "Not Implemented", "Not Implemented", JOptionPane.ERROR_MESSAGE);
				SettingsGUI guis = new SettingsGUI();
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
		jmFile.add(new JSeparator());
		jmFile.add(jmiExit);
		jmb.add(jmFile);
		jmb.add(jmStatus);
		jmb.add(jmBroad);
		jmb.add(jmHelp);

		f.setJMenuBar(jmb);

		JScrollPane jsp = new JScrollPane();
		jsp.setHorizontalScrollBar(null); // no horizontal scrollbar
		jt = new JTree();
		jsp.getViewport().add(jt);
		f.getContentPane().add(jsp, BorderLayout.CENTER);

		root = new DefaultMutableTreeNode("[root]");
		buddyNodeholy = new DefaultMutableTreeNode("Holy");
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

	// private void setLAF() {
	// try {
	// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	// } catch (ClassNotFoundException e1) {
	// e1.printStackTrace();
	// } catch (InstantiationException e1) {
	// e1.printStackTrace();
	// } catch (IllegalAccessException e1) {
	// e1.printStackTrace();
	// } catch (UnsupportedLookAndFeelException e1) {
	// e1.printStackTrace();
	// }
	// }

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

			popup.add(new JPopupMenu.Separator());
			popup.add(getMenuItem(language.langtext[74], new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					GUIContactAdd guica = new GUIContactAdd();
					guica.setBuddy((Buddy) o); // for when editing a buddy
					guica.setVisible(true);
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
			// popup.add(getMenuItem("Edit groups", al));
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

	public static ChatWindow getChatWindow(Buddy b, boolean createIfNotExist, boolean setVis) {
		ChatWindow w = windowMap.get(b.getAddress());
		if (w == null && !createIfNotExist)
			return null;
		if (w == null) {
			w = new ChatWindow(b);
			windowMap.put(b.getAddress(), w);
		}
		w.setIconImage(b.getStatus() == Buddy.OFFLINE ? TCIconRenderer.offlineImagebase : b.getStatus() == Buddy.HANDSHAKE ? TCIconRenderer.handshakeImagebase : b.getStatus() == Buddy.ONLINE ? TCIconRenderer.onlineImagebase : b.getStatus() == Buddy.AWAY ? TCIconRenderer.awayImagebase : b
				.getStatus() == Buddy.XA ? TCIconRenderer.xaImagebase : null);
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
			for (int i = 0; i < 3; i++) // repaint 3 times since sometimes it fails? FIXME
				jt.repaint();
			try {
				if (getChatWindow(buddy, false, false) != null) { // why was this commented out?
					getChatWindow(buddy, false, false).setIconImage(newStatus == Buddy.OFFLINE ? TCIconRenderer.offlineImage : newStatus == Buddy.HANDSHAKE ? TCIconRenderer.handshakeImage : newStatus == Buddy.ONLINE ? TCIconRenderer.onlineImage : newStatus == Buddy.AWAY ? TCIconRenderer.awayImage : newStatus == Buddy.XA ? TCIconRenderer.xaImage : null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Logger.oldOut.println(buddy + " changed from " + Buddy.getStatusName(oldStatus) + " to " + Buddy.getStatusName(newStatus));
			if (newStatus >= Buddy.ONLINE && oldStatus <= Buddy.HANDSHAKE) {

				if (!BuddyList.black.containsKey(buddy.getAddress())) {
					MutableTreeNode node = nodeMap.remove(buddy.getAddress());
					if (node != null) // remove entry in the gui
						((DefaultTreeModel) jt.getModel()).removeNodeFromParent(node);

					node = nodeMap.get(buddy.getAddress());
					if (node != null)
						node.removeFromParent();
					nodeMap.put(buddy.getAddress(), node = new DefaultMutableTreeNode(buddy));

					Alert alert;
					alert = new Alert(buddy.toString() + " is online");
					alert.start();

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

			ChatWindow w = getChatWindow(buddy, true, true);

			String msg = s.trim().replaceAll("\n", "\\\\n").replaceAll("\n", "\\\\n").replaceAll("\r", "");

			if (msg.startsWith("/")) {
				if (msg.trim().endsWith("\\\\n")) {
					msg.substring(0, msg.length() - 6);
				}
				String command = Commands.runin(buddy, msg);
				if (command.startsWith("0")) {
					w.append("Me", "Private: ");
					w.append("Them", command.substring(1).replaceAll("\\\\n", "\n").trim() + "\n");
					w.getTextPane1().setCaretPosition(w.getTextPane1().getDocument().getLength());
					w.getTextArea4().requestFocusInWindow();

					if (!w.isFocused()) {
						if (alert != null && !alert.isFinished())
							alert.kill();
						alert = new Alert("* " + buddy.toStringforme() + " " + command.substring(1).replaceAll("\\\\n", "\n").trim() + "\n");
						alert.start();
					}

				} else if (command.startsWith("1")) {
					w.append("Time Stamp", "(" + ChatWindow.getTime() + ") ");
					w.append("Them", "* " + buddy.toStringforme() + " " + command.substring(1).replaceAll("\\\\n", "\n").trim() + "\n");
					w.getTextPane1().setCaretPosition(w.getTextPane1().getDocument().getLength());
					w.getTextArea4().requestFocusInWindow();

					if (!w.isFocused()) {
						if (alert != null && !alert.isFinished())
							alert.kill();
						alert = new Alert("* " + buddy.toStringforme() + " " + command.substring(1).replaceAll("\\\\n", "\n").trim() + "\n");
						alert.start();
					}
				} else if (command.startsWith("2")) {
					w.append("Time Stamp", "(" + ChatWindow.getTime() + ") ");
					w.append("Them", " --> " + command.substring(1).replaceAll("\\\\n", "\n").trim() + "\n");
					w.getTextPane1().setCaretPosition(w.getTextPane1().getDocument().getLength());
					w.getTextArea4().requestFocusInWindow();

					if (!w.isFocused()) {
						if (alert != null && !alert.isFinished())
							alert.kill();
						alert = new Alert(buddy.toStringforme() + " --> " + command.substring(1).replaceAll("\\\\n", "\n").trim() + "\n");
						alert.start();
					}
				}

			} else {

				if (!w.isFocused() && !buddy.getBlack()) {
					if (alert != null && !alert.isFinished())
						alert.kill();
					alert = new Alert(buddy.toString() + ": " + s);
					alert.start();
				}

				w.append("Time Stamp", "(" + ChatWindow.getTime() + ") ");
				w.append("Them", buddy.toString() + ": ");
				w.addUrlText(s.replaceAll("\\\\n", "\n").trim() + "\n");
				// w.getTextArea3().insert("(" + ChatWindow.getTime() + ") " + buddy.toString() + ": " + s + "\n", w.getTextArea3().getText().length());
				w.getTextPane1().setCaretPosition(w.getTextPane1().getDocument().getLength());
				w.getTextArea4().requestFocusInWindow();
			}
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
