package org.bb.bookmarkbuddy.ui.swing.bookmarkmanager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.bb.bookmarkbuddy.model.Bookmark;
import org.bb.bookmarkbuddy.model.BookmarkList;


@SuppressWarnings("serial")
class BookmarkTableModel extends AbstractTableModel {
	
	private BookmarkList bookmarks;
	private List<Boolean> selected;
	private String[] columns = { "Label", "URL", "Select" };
	
	public BookmarkTableModel(BookmarkList bookmarks)
	{
		this.bookmarks = bookmarks;
		this.selected = new ArrayList<Boolean>(bookmarks.size());
		for( Boolean val : selected )
		{
			val = (Boolean)false;
		}
	}

	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
	
	public boolean isCellEditable(int row, int col)
	{
		if( col == 2 ) 
			return true;
		return false;
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
				if( selected.size() < bookmarks.size() )
				{
					selected.add(false);
				}
				else
				{
					selected.set(row, !(Boolean) getValueAt(row, 2));
				}
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
				while( selected.size() < bookmarks.size() )
				{
					selected.add(false);
				}
				return selected.get(row);
		}
		return null;
	}
}