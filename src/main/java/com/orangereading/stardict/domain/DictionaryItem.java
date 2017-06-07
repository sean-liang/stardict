package com.orangereading.stardict.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * StarDict Dictionary Item.
 * 
 * @author sean
 *
 */
public class DictionaryItem implements Serializable {

	private static final long serialVersionUID = -1599690250183321142L;

	private final DictionaryIndexItem index;

	// a single char identifying the data type
	private final List<DictionaryItemEntry> entries;

	public DictionaryItem(final DictionaryIndexItem index) {
		this.index = index;
		this.entries = new ArrayList<>();
	}

	public DictionaryIndexItem getIndex() {
		return index;
	}

	public List<DictionaryItemEntry> getEntries() {
		return entries;
	}

	@Override
	public String toString() {
		return "DictionaryItem [index=" + index + ", entries=" + entries + "]";
	}

}
