package com.orangereading.stardict.io;

import java.io.Closeable;
import java.io.IOException;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;

/**
 * 
 * Read StarDict .dict file.
 * 
 * @author sean
 *
 */
public interface DictionaryDataReader extends Closeable {

	/**
	 * 
	 * Read StarDict .dict file.
	 * 
	 * @param indexItem
	 *            index info
	 * 
	 * @return dictionary item
	 * 
	 * @throws IOException
	 *             IO Exception
	 */
	public DictionaryItem read(final DictionaryIndexItem indexItem) throws IOException;

}
