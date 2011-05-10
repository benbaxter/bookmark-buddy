package org.bb.bookmarkbuddy.ui.swing.bookmarkmanager;

import java.net.URL;

import javax.swing.table.AbstractTableModel;

import org.bb.bookmarkbuddy.model.Bookmark;
import org.bb.bookmarkbuddy.model.BookmarkList;


@SuppressWarnings("serial")
class BookmarkTableModel extends AbstractTableModel {
	
	private BookmarkList bookmarks;
	private String[] columns = { "Label", "URL", "Select" };
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public BookmarkTableModel(BookmarkList bookmarks)
	{
		this.bookmarks = bookmarks;
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return bookmarks.size();
	}
	
	public String getColumnName(int index)
	{
		return columns[index];
	}
	
	public void setValueAt(Object value, int row, int col)
	{
		Bookmark bookmark = bookmarks.getBookmark(row);
		switch ( col )
		{
			case 0:
				bookmark.setLabel((String)value);
				break;
			case 1:
				bookmark.setUrl((URL) value);
				break;
			case 2:
				break;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		Bookmark bookmark = bookmarks.getBookmark(row);
		switch ( col )
		{
			case 0:
				return bookmark.getLabel();
			case 1:
				return bookmark.getUrl();
			case 2:
				return false;
		}
		return null;
	}
}