package com.orangereading.stardict.io;

import java.io.IOException;
import java.util.function.Consumer;

import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

public interface DictionaryReader {

	/**
	 * 
	 * Get dictionary info.
	 * 
	 * @return dictionary info
	 * 
	 */
	public ImmutableDictionaryInfo getInfo();

	/**
	 * 
	 * Iterate each item in the dictionary.
	 * 
	 * @param consumer
	 *            consumer to accept the item
	 * @throws IOException
	 *             IO Exception
	 *             
	 */
	public void eachWord(Consumer<DictionaryItem> consumer) throws IOException;

}
