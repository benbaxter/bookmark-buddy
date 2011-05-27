package org.bb.bookmarkbuddy.ui.systemtray;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.SwingUtilities;

import org.bb.bookmarkbuddy.model.Bookmark;
import org.bb.bookmarkbuddy.model.BookmarkList;
import org.bb.bookmarkbuddy.model.BookmarkListListener;
import org.bb.bookmarkbuddy.ui.swing.bookmarkmanager.BookmarkManager;

public class BookmarkSystemTray {
	
	private class BookmarkMenuActionListener implements ActionListener
	{
		private Bookmark bookmark;
		
		public BookmarkMenuActionListener( Bookmark bookmark )
		{
			this.bookmark = bookmark;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
        	Desktop browserDesktop = Desktop.getDesktop();
        	URI location;
			try {
				location = bookmark.getUrl().toURI();
				browserDesktop.browse( location );
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
				setErrorStatus("Could not open " + bookmark.getLabel());
			}
		}
	}

	private BookmarkManager bookmarkManager;
	private String displayMessage;
	private TrayIcon trayIcon;
	private BookmarkList bookmarks;
    private ActionListener exitListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    private ActionListener openManagerListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	bookmarkManager.setVisible(true);
        }
    };
    
    
	public BookmarkSystemTray(BookmarkList bookmarks, BookmarkManager bookmarkManager)
	{
		this.bookmarks = bookmarks;
		this.bookmarkManager = bookmarkManager;
        
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	setUpSystemTray();
            }
        });
	}
	
	public void setUpSystemTray()
	{
		if (SystemTray.isSupported()) {

		    SystemTray tray = SystemTray.getSystemTray();

		    PopupMenu popup = new PopupMenu();
		    loadPopupMenu( bookmarks, popup );
		    
		    Image trayImage = loadImage("/images/bookmark.png");
		    trayIcon = new TrayIcon(trayImage, "Bookmark Buddy", popup);

		    trayIcon.setImageAutoSize(true);
		    trayIcon.addActionListener(openManagerListener);
		    
		    try {
		        tray.add(trayIcon);
		    } catch (AWTException e) {
		        System.err.println("TrayIcon could not be added.");
		    }
		} else {
		    //  System Tray is not supported
			System.out.println("System Tray not supported");
		}
	}
	
	private void setErrorStatus(String message) {
		trayIcon.displayMessage("Error", message, MessageType.ERROR);
	}
	
	private Image loadImage(String imagePath) {
		URL imgURL = BookmarkSystemTray.class.getResource(imagePath);
		return Toolkit.getDefaultToolkit().getImage(imgURL);
	}
	
	private void loadPopupMenu(BookmarkList bookmarks, final PopupMenu popup)
	{
		MenuItem item;
		for( Bookmark bookmark : bookmarks )
		{
			addBookmarkMenuItem(popup, bookmark);
		}
		
		bookmarks.addListener(new BookmarkListListener() {
			
			@Override
			public void bookmarkRemoved(BookmarkList bookmarks,
					Bookmark removedBookmark, int oldIndex) {
				popup.remove(oldIndex);
			}
			
			@Override
			public void bookmarkAdded(BookmarkList bookmarks, Bookmark newBookmark,
					int index) {
				addBookmarkMenuItem(popup, newBookmark, index);
			}
		});

	    popup.addSeparator();
	    
	    MenuItem managerItem = new MenuItem("Manage...");
	    managerItem.addActionListener(openManagerListener);
	    popup.add(managerItem);
		
	    MenuItem exitItem = new MenuItem("Exit");
	    exitItem.addActionListener(exitListener);
	    popup.add(exitItem);
	    
	}

	private void addBookmarkMenuItem(final PopupMenu popup, Bookmark bookmark) {
		addBookmarkMenuItem(popup, bookmark, popup.getItemCount());
	}
	
	private void addBookmarkMenuItem(final PopupMenu popup, Bookmark bookmark, int index) {
		MenuItem item;
		item = new MenuItem(bookmark.getLabel());
		item.addActionListener(new BookmarkMenuActionListener(bookmark));
		popup.insert(item, index);
	}
}