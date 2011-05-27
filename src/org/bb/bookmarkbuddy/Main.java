package org.bb.bookmarkbuddy;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.bb.bookmarkbuddy.io.file.FileBookmarkReader;
import org.bb.bookmarkbuddy.io.file.FileBookmarkWriter;
import org.bb.bookmarkbuddy.model.BookmarkList;
import org.bb.bookmarkbuddy.ui.swing.bookmarkmanager.BookmarkManager;
import org.bb.bookmarkbuddy.ui.systemtray.BookmarkSystemTray;

public class Main {

	private static BookmarkList bookmarks;
	static boolean systemtray;
	
	public static void main(String[] args) {

		systemtray = Boolean.getBoolean("org.bb.bookmarkbuddy.systemtray");

//		if( args.length > 0)
//		{
//			for( String arg : args)
//				System.out.println(arg);
//			systemtray = Boolean.valueOf(args[0].split("=")[1]);
//		}
		
		System.out.println(systemtray);
		
		String userHomeDir = System.getProperty("user.home");
		File homedir = new File(userHomeDir + "/bookmark-buddy");
		if(!homedir.exists())
			homedir.mkdir();

		File file = new File(homedir, "links.txt");
		if( file.exists() ) 
		{
			bookmarks = new FileBookmarkReader(file).readBookmarks();
		}
		else
		{
			bookmarks = new BookmarkList();
		}
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
		BookmarkManager bookmarkManager = new BookmarkManager(bookmarks);
		bookmarkManager.addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
 				new FileBookmarkWriter().writeBookmarks(bookmarks);
				if( !systemtray )
				{
					System.exit(0);
				}
			}
		} );
		
		if( systemtray )
		{
			BookmarkSystemTray sysTray = new BookmarkSystemTray(bookmarks, bookmarkManager);
		}
		

	}

}
