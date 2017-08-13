package com.orangereading.stardict.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * StarDict Dictionary Index.
 * 
 * @author sean
 *
 */
public class DictionaryIndex implements Serializable, ImmutableDictionaryIndex {

	private static final long serialVersionUID = -860867409851270304L;

	// Index items
	private final List<DictionaryIndexItem> items;

	/**
	 * 
	 * Create index item array.
	 * 
	 */
	public DictionaryIndex() {
		this.items = new ArrayList<>();
	}

	/**
	 * 
	 * Create index item array.
	 * 
	 * @param wordCount
	 *            array size
	 * 
	 * @throws IllegalArgumentException
	 *             when wordCount <= 0
	 */
	public DictionaryIndex(final int wordCount) {
		if (wordCount <= 0) {
			throw new IllegalArgumentException("wordCount must be geater than 0");
		}
		this.items = new ArrayList<>(wordCount);
	}

	@Override
	public List<DictionaryIndexItem> getItems() {
		return items;
	}

	@Override
	public DictionaryIndexItem getItem(final int pos) {
		return pos >= 0 && pos < items.size() ? items.get(pos) : null;
	}

	/**
	 * 
	 * append item to list.
	 * 
	 * @param item
	 *            index item
	 * 
	 */
	public void addItem(final DictionaryIndexItem item) {
		if (this.items == null) {
			throw new RuntimeException("items is null");
		}
		this.items.add(item);
	}

	@Override
	public int size() {
		return this.items != null ? this.items.size() : 0;
	}

}
