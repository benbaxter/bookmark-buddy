package org.bb.bookmarkbuddy.ui.swing.bookmarkmanager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Iterator;

import javax.swing.*;

import org.bb.bookmarkbuddy.io.file.FileBookmarkReader;
import org.bb.bookmarkbuddy.io.file.FileBookmarkWriter;
import org.bb.bookmarkbuddy.model.Bookmark;
import org.bb.bookmarkbuddy.model.BookmarkList;


public class BookmarkManager extends JFrame
{
	static private JTextField labelTxt, urlTxt;
	private JButton addButton, saveButton, openButton, deleteButton;
	private JTable linksTable;
	private JPanel pnlTable;
	private BookmarkList bookmarks;
	
	
	
	public BookmarkManager(BookmarkList bookmarks)
	{
		this.bookmarks = bookmarks;
		
		TFFocusHandler tfFocusHandler = new TFFocusHandler();
		ButtonHandler bh = new ButtonHandler();
		
		JPanel pnlCenter = new JPanel(new GridLayout(4,1));
		JPanel[] pnlGrid = new JPanel[4];
		for(int i = 0; i< pnlGrid.length; ++i)
		{
			pnlGrid[i] = new JPanel( new FlowLayout(FlowLayout.LEFT));
			pnlCenter.add(pnlGrid[i]);
		}
		
		
		JLabel titleLbl = new JLabel("Enter in label for link and copy in URL, its that easy!");
		pnlGrid[0].add(titleLbl);
		titleLbl.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel labelLbl = new JLabel("Label:");
		pnlGrid[1].add(labelLbl);
		labelLbl.setHorizontalAlignment(JLabel.RIGHT);
		
		labelTxt = new JTextField(20);
		labelTxt.addFocusListener(tfFocusHandler);
		pnlGrid[1].add(labelTxt);
		
		JLabel urlLbl = new JLabel("URL:");
		pnlGrid[2].add(urlLbl);
		urlLbl.setHorizontalAlignment(JLabel.RIGHT);
		
		urlTxt = new JTextField(40);
		urlTxt.addFocusListener(tfFocusHandler);
		pnlGrid[2].add(urlTxt);
		
		BookmarkTableModel tableModel = new BookmarkTableModel(bookmarks);
		linksTable = new JTable(tableModel);
		
		linksTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		linksTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		linksTable.getColumnModel().getColumn(2).setPreferredWidth(50);

        linksTable.setPreferredScrollableViewportSize(new Dimension(350, 100));
//		linksTable.setSize(350, 10);
		
				
		pnlTable = new JPanel();
		pnlTable.add(new JScrollPane(linksTable));
		
		JPanel pnlSouth = new JPanel();
		
		addButton = new JButton("Add");
		addButton.setMnemonic('A');
		addButton.addActionListener(bh);
		pnlSouth.add(addButton);
		
		saveButton = new JButton("Save");
		saveButton.setMnemonic('S');
		saveButton.addActionListener(bh);
		pnlSouth.add(saveButton);
		
		openButton = new JButton("Open");
		openButton.setMnemonic('O');
		openButton.addActionListener(bh);
		pnlSouth.add(openButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setMnemonic('D');
		deleteButton.addActionListener(bh);
		pnlSouth.add(deleteButton);
		
		add(pnlCenter, BorderLayout.NORTH);
		add(pnlTable, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		
		//Set some attributes to the frame
		setTitle("Bookmark Manager");
		setSize(400, 150);
		setLocationRelativeTo(null);
		pack();
		setResizable(false);
		setVisible(true);
		
	}
		
	public void addToDatabase()
	{
		String label = labelTxt.getText();
		String urlText = urlTxt.getText();
		URL url;
		try 
		{
			if( urlText.matches( "^[A-Za-z]\\:.*" ) )
			{
				url = new File( urlText ).toURI().toURL();
			}
			else
			{
				url = new URL(urlText);
			}
			
			System.out.println(url);
			bookmarks.addBookmark( new Bookmark(label, url));
		}
		catch ( MalformedURLException mue )
		{
			JOptionPane.showMessageDialog(this, "The URI is invalid", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			urlTxt.requestFocus();
		}
	}

	public void saveTable()
	{
//		mapOfBookmarks.clear();
//		linksTable.getCellEditor().stopCellEditing();
//		BookmarkTableModel tableModel = (BookmarkTableModel) linksTable.getModel();
//		for(int i = 0; i < tableModel.getRowCount(); ++i)
//		{
//			String label = (String)tableModel.getValueAt(i, 0);
//			String url = (String)tableModel.getValueAt(i, 1);
//			mapOfBookmarks.put(label, url);
//		}
	}
	
	public void openLink()
	{
		Desktop desktop = Desktop.getDesktop();
		BookmarkTableModel tableModel = (BookmarkTableModel) linksTable.getModel();
		linksTable.clearSelection();
//		linksTable.getSelectedRow() != -1
		for(int i = 0; i < tableModel.getRowCount(); ++i)
		{
			if( (Boolean) tableModel.getValueAt(i, 2) == true)
			{
//				String urlText = (String) tableModel.getValueAt(i, 1);
				URI uri;
				try {
					uri = ((URL) tableModel.getValueAt(i, 1)).toURI();
					System.out.println(uri);
					desktop.browse(uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void removeFromTable()
	{
//		BookmarkTableModel tableModel = (BookmarkTableModel) linksTable.getModel();
//		for(int i = 0; i < tableModel.getRowCount(); ++i)
//		{
//			if( (Boolean) tableModel.getValueAt(i, 2) == true)
//			{
//				mapOfBookmarks.remove((String) tableModel.getValueAt(i, 0));
//				tableModel.removeRow(i);
//				--i;
//			}
//		}
	}
	
	public void updateTable()
	{
//		BookmarkTableModel tableModel = (BookmarkTableModel) linksTable.getModel();
//		for(int i = 0; i < tableModel.getRowCount(); ++i)
//		{
//			tableModel.removeRow(i);
//		}
//		for( String label : mapOfBookmarks.keySet() )
//		{
//			tableModel.addRow(new Object[] {label, mapOfBookmarks.get(label), new Boolean(false)} );
//		}
	}

	//the following classes are modeled after professor Weiner's examples
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			if(command.equals( "Add" ))
			{
				addToDatabase();
			}
			else if(command.equals( "Save" ))
			{
				saveTable();
				labelTxt.requestFocus();
			}
			else if(command.equals( "Open" ))
			{
				openLink();
			}
			else if(command.equals("Delete"))
			{
				removeFromTable();
				labelTxt.requestFocus();
			}
		}
	}

	class TFFocusHandler extends FocusAdapter
	{
		public void focusGained( FocusEvent e)
		{
			JTextField tf = (JTextField)e.getSource();
			tf.selectAll();
		}
	}	
}
