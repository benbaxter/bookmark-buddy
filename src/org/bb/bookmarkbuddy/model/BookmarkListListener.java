package org.bb.bookmarkbuddy.model;

public interface BookmarkListListener
{
	public void bookmarkAdded( BookmarkList bookmarks, Bookmark newBookmark, int index );
	public void bookmarkRemoved( BookmarkList bookmarks, Bookmark removedBookmark, int oldIndex );
}
