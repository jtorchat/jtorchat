package util;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import core.Buddy;
import core.Config;
import core.Logger;



@SuppressWarnings("serial")
public class TCIconRenderer extends DefaultTreeCellRenderer {
	public static Image newimg;
	public static Image img;
	public static ImageIcon awayIcon;
	public static ImageIcon handshakeIcon;
	public static ImageIcon offlineIcon;
	public static ImageIcon onlineIcon;
	public static ImageIcon xaIcon;
	public static Image awayImage;
	public static Image handshakeImage;
	public static Image offlineImage;
	public static Image onlineImage;
	public static Image xaImage;
	public static Image awayImagebase;
	public static Image handshakeImagebase;
	public static Image offlineImagebase;
	public static Image onlineImagebase;
	public static Image xaImagebase;
	@SuppressWarnings("unused")
	private JTree t;
	
	static {
		Logger.log(Logger.INFO, "Gui", "Loading status icons.");

		awayIcon = new ImageIcon(Config.ICON_DIR+"/aw.png");
		handshakeIcon = new ImageIcon(Config.ICON_DIR+"/co.png");
		offlineIcon = new ImageIcon(Config.ICON_DIR+"/of.png");
		onlineIcon = new ImageIcon(Config.ICON_DIR+"/on.png");
		xaIcon = new ImageIcon(Config.ICON_DIR+"/xa.png");
		
		
		awayImagebase = getImg(Config.ICON_DIR+"/aw.png");
		handshakeImagebase = getImg(Config.ICON_DIR+"/co.png");
		offlineImagebase = getImg(Config.ICON_DIR+"/of.png");
		onlineImagebase = getImg(Config.ICON_DIR+"/on.png");
		xaImagebase = getImg(Config.ICON_DIR+"/xa.png");

		
	awayImage = awayImagebase.getScaledInstance(Config.image_size, Config.image_size,  java.awt.Image.SCALE_SMOOTH); 
	handshakeImage = handshakeImagebase.getScaledInstance(Config.image_size, Config.image_size,  java.awt.Image.SCALE_SMOOTH);
	offlineImage = offlineImagebase.getScaledInstance(Config.image_size, Config.image_size,  java.awt.Image.SCALE_SMOOTH);
	onlineImage = onlineImagebase.getScaledInstance(Config.image_size, Config.image_size,  java.awt.Image.SCALE_SMOOTH);
	xaImage = xaImagebase.getScaledInstance(Config.image_size, Config.image_size,  java.awt.Image.SCALE_SMOOTH);
		
	    img = awayIcon.getImage();  
	    newimg = img.getScaledInstance(Config.icon_size, Config.icon_size,  java.awt.Image.SCALE_SMOOTH);  
	    awayIcon = new ImageIcon(newimg);  
	    
	    img = handshakeIcon.getImage();  
	    newimg = img.getScaledInstance(Config.icon_size, Config.icon_size,  java.awt.Image.SCALE_SMOOTH);  
	    handshakeIcon = new ImageIcon(newimg);  
	    
	    img = offlineIcon.getImage();  
	    newimg = img.getScaledInstance(Config.icon_size, Config.icon_size,  java.awt.Image.SCALE_SMOOTH);  
	    offlineIcon = new ImageIcon(newimg);  
	
	    img = onlineIcon.getImage();  
	    newimg = img.getScaledInstance(Config.icon_size, Config.icon_size,  java.awt.Image.SCALE_SMOOTH);  
	    onlineIcon = new ImageIcon(newimg);  
	    
	    img = xaIcon.getImage();  
	    newimg = img.getScaledInstance(Config.icon_size, Config.icon_size,  java.awt.Image.SCALE_SMOOTH);  
	    xaIcon = new ImageIcon(newimg);  
		
	}
	
	public static Image getImg(URL u) {
		try {
			Image x = ImageIO.read(u);
//			System.out.println("Loaded: " + x);
			return x;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Image getImg(String s) {
		try {
			Image x = ImageIO.read(new File(s));
//			System.out.println("Loaded: " + x);
			return x;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TCIconRenderer(JTree t) {
		this.t = t;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		
		Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
		if (nodeObj instanceof Buddy) {
			Buddy b = ((Buddy) nodeObj);
			int status = b.getStatus();
			String s = "<html>" + b.getAddress();
			if (b.getProfile_name() != null && !b.getProfile_name().equals(""))
				s += "<BR>" + b.getProfile_name();
			if (b.getProfile_text() != null && !b.getProfile_text().equals(""))
				s += "<BR>" + b.getProfile_text();
			if (b.getClient() != null && !b.getClient().equals(""))
				s += "<BR>" + b.getClient() + " " + b.getVersion(); 
					
			s += "</html>";
			this.setToolTipText(s);
			setIcon(getStatusIcon(status));
		}
		return this;
	}



	public static ImageIcon getStatusIcon(int status) {
		// TODO Auto-generated method stub
		if (status == Buddy.AWAY) {
			return awayIcon;
		} else if (status == Buddy.HANDSHAKE) {
			return handshakeIcon;
		} else if (status == Buddy.OFFLINE) {
			return offlineIcon;
		} else if (status == Buddy.ONLINE) {
			// System.out .println("onlie");
			return onlineIcon;
		} else if (status == Buddy.XA) {
			return xaIcon;
		}
		return null;
	}
}