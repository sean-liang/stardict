package com.orangereading.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * StarDict Dictionary Index.
 * 
 * @author sean
 *
 */
public class StarDictDictionaryIndex implements Serializable {

	private static final long serialVersionUID = -860867409851270304L;

	// Index items
	private StarDictDictionaryIndexItem[] items;

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
	public StarDictDictionaryIndex(final int wordCount) {
		if (wordCount <= 0) {
			throw new IllegalArgumentException("wordCount must be geater than 0");
		}
		this.items = new StarDictDictionaryIndexItem[wordCount];
	}

	/**
	 * 
	 * Get item at that position. Start from 0.
	 * 
	 * @param pos
	 *            position
	 * 
	 * @return item or null if items is null or position < 0 or position > items
	 *         length
	 */
	public StarDictDictionaryIndexItem getItem(final int pos) {
		return this.items != null && pos >= 0 && pos < items.length ? items[pos] : null;
	}

	/**
	 * 
	 * Set item at position.
	 * 
	 * @param pos
	 *            position
	 * @param item
	 *            item
	 * 
	 * @throws RuntimeException
	 *             when items is null
	 * @throws IllegalArgumentException
	 *             when item is null or position < 0 or position > items length
	 */
	public void setItem(final int pos, final StarDictDictionaryIndexItem item) {
		if (this.items == null) {
			throw new RuntimeException("items is null");
		}
		if (null == item) {
			throw new IllegalArgumentException("item can't be null");
		}
		if (pos < 0 || pos > this.items.length) {
			throw new IllegalArgumentException("position must between 0 and " + this.items.length);
		}
		this.items[pos] = item;
	}

	/**
	 * 
	 * Get size of elements.
	 * 
	 * @return size or 0 if items is null
	 * 
	 */
	public int size() {
		return this.items != null ? this.items.length : 0;
	}

	/**
	 * 
	 * Remove tailing null elements.
	 * 
	 * @return number of items removed
	 * 
	 */
	public int pack() {
		final int len = this.items.length;
		int removed = 0;
		for (int i = len - 1; i >= 0; i--) {
			if (null == items[i]) {
				removed++;
			} else {
				break;
			}
		}
		if (removed > 0) {
			this.items = Arrays.copyOf(this.items, len - removed);
		}
		return removed;
	}

	@Override
	public String toString() {
		return "StarDictDictionaryIndex [items=" + Arrays.toString(items) + "]";
	}

}
