package org.bb.bookmarkbuddy;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.bb.bookmarkbuddy.io.file.FileBookmarkReader;
import org.bb.bookmarkbuddy.model.BookmarkList;
import org.bb.bookmarkbuddy.ui.swing.bookmarkmanager.BookmarkManager;

public class Main {

	private static BookmarkList bookmarks;
	
	public static void main(String[] args) {

		String userHomeDir = System.getProperty("user.home");
		File file = new File(userHomeDir, "links.txt");
		if (!file.exists()) {
			String databaseName = "/database/links.txt";
			URL url = BookmarkManager.class.getResource(databaseName);
			try {
				file = new File(url.toURI());
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println(file.getAbsolutePath());
		bookmarks = new FileBookmarkReader(file).readBookmarks();
	
		BookmarkManager application = new BookmarkManager(bookmarks);
		

	}

}
