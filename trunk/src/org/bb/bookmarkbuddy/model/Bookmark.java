package org.bb.bookmarkbuddy.model;

import java.net.URL;

public class Bookmark implements Comparable<Bookmark>{
	private String label;
	private URL url;

	public Bookmark(String label, URL url)
	{
		this.label = label;
		this.url = url;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public int compareTo(Bookmark other) {
		return this.label.compareTo(other.label);
	}

}
