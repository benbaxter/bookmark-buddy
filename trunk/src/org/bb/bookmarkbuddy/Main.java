package org.bb.bookmarkbuddy;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.bb.bookmarkbuddy.io.file.FileBookmarkReader;
import org.bb.bookmarkbuddy.model.BookmarkList;
import org.bb.bookmarkbuddy.ui.swing.bookmarkmanager.BookmarkManager;
import org.bb.bookmarkbuddy.ui.systemtray.BookmarkSystemTray;

public class Main {

	private static BookmarkList bookmarks;
	static boolean systemtray;
	
	public static void main(String[] args) {

		if( args.length > 0)
		{
			for( String arg : args)
				System.out.println(arg);
			systemtray = Boolean.valueOf(args[0].split("=")[1]);
		}
		System.out.println(systemtray);
		String userHomeDir = System.getProperty("user.home");
		File homedir = new File(userHomeDir + "/bookmark-buddy");
		if(!homedir.exists())
			homedir.mkdir();
		System.out.println(homedir.getAbsolutePath());
		File file = new File(homedir, "links.txt");
		
		//default file to use when the generated file does not exist.
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
		if( systemtray )
		{
			BookmarkSystemTray sysTray = new BookmarkSystemTray(bookmarks);
		}
		

	}

}
