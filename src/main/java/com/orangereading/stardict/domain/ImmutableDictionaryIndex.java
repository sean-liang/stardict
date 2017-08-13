package com.orangereading.stardict.domain;

import java.util.List;

public interface ImmutableDictionaryIndex {

	List<DictionaryIndexItem> getItems();

	/**
	 * 
	 * Get item at that position. Start from 0.
	 * 
	 * @param pos
	 *            position
	 * 
	 * @return item or null if position < 0 or position > items length
	 */
	DictionaryIndexItem getItem(int pos);

	/**
	 * 
	 * Get size of elements.
	 * 
	 * @return size or 0 if items is null
	 * 
	 */
	int size();

}