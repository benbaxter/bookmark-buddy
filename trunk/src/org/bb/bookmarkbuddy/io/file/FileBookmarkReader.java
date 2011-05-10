package org.bb.bookmarkbuddy.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.bb.bookmarkbuddy.io.BookmarkReader;
import org.bb.bookmarkbuddy.model.Bookmark;
import org.bb.bookmarkbuddy.model.BookmarkList;
import org.bb.bookmarkbuddy.ui.swing.bookmarkmanager.BookmarkManager;

public class FileBookmarkReader implements BookmarkReader {
	private File file;
	
	public FileBookmarkReader( File file )
	{
		this.file = file;
	}
	
	@Override
	public BookmarkList readBookmarks() 
	{
		BookmarkList bookmarks = new BookmarkList();
		try
		{
			System.out.println(file.getAbsolutePath());
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if( line == null )
			{
				System.out.println("File is empty");
			}
			else 
			{
				do 
				{
					String label = line.split("\t")[0];
					String urlText = line.split("\t")[1];
					URL url;
					if( urlText.matches( "^([A-Za-z]\\:|/).*" ) )
					{
						url = new File( urlText ).toURI().toURL();
					}
					else
					{
						url = new URL(urlText);
					}
					bookmarks.addBookmark(new Bookmark(label, url));
				}while((line = br.readLine()) != null);
			}
		}
        catch( IOException ex )
		{
			JOptionPane.showMessageDialog(null, "There is no data base file to load from");
			ex.printStackTrace();
			return null;
		}
        return bookmarks;
	}
	
	private File getDatabaseFile() throws URISyntaxException 
	{
		String userHomeDir = System.getProperty("user.home");
		File file = new File( userHomeDir, "links.txt" );
		if( !file.exists() )
		{
			String databaseName = "/database/links.txt";
			URL url = BookmarkManager.class.getResource(databaseName);
			System.out.println(file.toURI().toString());
			file = new File(url.toURI());			
		}
		return file;
	}
}
