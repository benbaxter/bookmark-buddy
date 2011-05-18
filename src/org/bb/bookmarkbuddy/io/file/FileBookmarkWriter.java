package org.bb.bookmarkbuddy.io.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.bb.bookmarkbuddy.io.BookmarkWriter;
import org.bb.bookmarkbuddy.model.Bookmark;
import org.bb.bookmarkbuddy.model.BookmarkList;

public class FileBookmarkWriter implements BookmarkWriter{

	@Override
	public void writeBookmarks(BookmarkList bookmarks) 
	{
		try {
			String userHomeDir = System.getProperty("user.home") + "/bookmark-buddy/";
			File file = new File(userHomeDir, "links.txt");
			PrintWriter save = new PrintWriter(new FileWriter(file));
			for (Bookmark bookmark : bookmarks) {
				save.println(bookmark.getLabel() + "\t" + bookmark.getUrl());
			}
			save.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null,
					"Error -- Unable to open or write to backup file", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

}
