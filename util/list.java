package util;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class list {
	public static JList addelements(JList list, String compare, String Dir, String files) {
		int index = 0;
		int now = 0;
		DefaultListModel listModel = new DefaultListModel();
		File dir = new File(Dir);
		File[] fileList = dir.listFiles();
		for (File f : fileList) {
			if (f.getName().endsWith(files)) {
				listModel.addElement(f.getName().subSequence(0, f.getName().length() - files.length()));
				if ((f.getName().subSequence(0, f.getName().length() - files.length())).equals(compare)) {
					now = index + 1;
				}
				index++;
			}
		}
		list.setModel(listModel);
		if (now != 0) {
			list.setSelectedIndex(now - 1);
		}
		return list;
	}
}
