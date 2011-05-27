package org.bb.bookmarkbuddy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BookmarkList implements Iterable<Bookmark>{

	List<Bookmark> bookmarks;
	List<BookmarkListListener> listeners;
	
	public BookmarkList()
	{
		bookmarks = new ArrayList<Bookmark>();
		listeners = new ArrayList<BookmarkListListener>(3);
	}
	
	public void addListener(BookmarkListListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(BookmarkListListener listener)
	{
		listeners.remove(listener);
	}
	
	public void addBookmark(Bookmark bookmark)
	{
		int index = Collections.binarySearch(bookmarks, bookmark);
		if ( index < 0 )
			index *= -1;
		if( index > bookmarks.size() )
			index = bookmarks.size();
		bookmarks.add(index, bookmark);
		for ( BookmarkListListener listener : listeners )
		{
			listener.bookmarkAdded(this, bookmark, index);
		}
	}
	
	public void removeBookmark(Bookmark bookmark)
	{
		int index = bookmarks.indexOf(bookmark);
		if ( index >= 0 )
		{
			bookmarks.remove(bookmark);
			for ( BookmarkListListener listener : listeners )
			{
				listener.bookmarkRemoved(this, bookmark, index);
			}
		}
	}
	
	public int size()
	{
		return bookmarks.size();
	}
	
	@Override
	public Iterator<Bookmark> iterator() {
		return bookmarks.iterator();
	}

	public Bookmark getBookmark(int row) {
		return bookmarks.get(row);
	}
}
