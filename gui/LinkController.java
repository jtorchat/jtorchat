package gui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;

public class LinkController extends MouseAdapter implements MouseMotionListener {

	public void mouseClicked(MouseEvent e) {
		java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		JTextPane editor = (JTextPane) e.getSource();
		Document doc = editor.getDocument();
		Point pt = new Point(e.getX(), e.getY());
		int pos = editor.viewToModel(pt);

		if (pos >= 0) {
			if (doc instanceof DefaultStyledDocument) {
				DefaultStyledDocument hdoc = (DefaultStyledDocument) doc;
				Element el = hdoc.getCharacterElement(pos);
				AttributeSet a = el.getAttributes();
				String href = (String) a.getAttribute(HTML.Attribute.HREF);

				if (href != null) {
					try {
						java.net.URI uri = new java.net.URI(href);
						desktop.browse(uri);
					} catch (Exception ev) {
						System.err.println(ev.getMessage());
					}
				}
			}
		}
	}

	public void mouseMoved(MouseEvent ev) {

		Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
		JTextPane editor = (JTextPane) ev.getSource();
		Point pt = new Point(ev.getX(), ev.getY());
		int pos = editor.viewToModel(pt);

		if (pos >= 0) {
			Document doc = editor.getDocument();

			if (doc instanceof DefaultStyledDocument) {
				DefaultStyledDocument hdoc = (DefaultStyledDocument) doc;
				Element e = hdoc.getCharacterElement(pos);
				AttributeSet a = e.getAttributes();
				String href = (String) a.getAttribute(HTML.Attribute.HREF);

				if (href != null) {
					if (editor.getCursor() != handCursor) {
						editor.setCursor(handCursor);
					}
				} else {
					editor.setCursor(defaultCursor);
				}
			}
		} else {
			editor.setToolTipText(null);
		}
	}
}
