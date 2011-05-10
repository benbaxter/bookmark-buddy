package org.bb.bookmarkbuddy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BookmarkList implements Iterable<Bookmark>{

	List<Bookmark> bookmarks;
	
	public BookmarkList()
	{
		bookmarks = new ArrayList<Bookmark>();
	}
	
	public void addBookmark(Bookmark bookmark)
	{
		int index = Collections.binarySearch(bookmarks, bookmark);
		if ( index < 0 )
			index *= -1;
		if( index > bookmarks.size() )
			index = bookmarks.size();
		bookmarks.add(index, bookmark);
	}
	
	public void removeBookmark(Bookmark bookmark)
	{
		bookmarks.remove(bookmark);
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
