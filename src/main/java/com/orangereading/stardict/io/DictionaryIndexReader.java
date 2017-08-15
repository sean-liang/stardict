package com.orangereading.stardict.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

/**
 * 
 * Read StarDict .idx file.
 * 
 * @author sean
 *
 */
public interface DictionaryIndexReader extends Closeable {

	/**
	 * 
	 * Read all index items.
	 * 
	 * @param info
	 *            Dictionary Info
	 * 
	 * @param consumer
	 *            Index item consumer
	 * 
	 * @throws IOException
	 *             IO Exception
	 * 
	 */
	public void eachItem(ImmutableDictionaryInfo info, Consumer<DictionaryIndexItem> consumer) throws IOException;

}