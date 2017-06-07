package com.orangereading.stardict.io;

import com.orangereading.stardict.domain.DictionaryIndex;
import com.orangereading.stardict.domain.DictionaryInfo;

/**
 * 
 * Read StarDict .idx file.
 * 
 * @author sean
 *
 */
public interface DictionaryIndexReader {

	/**
	 * 
	 * Read StarDict .idx file.
	 * 
	 * @param info
	 *            dictionary meta data
	 * @param in
	 *            index file content in a ByteBuffer
	 * 
	 * @return index data
	 */
	DictionaryIndex read(DictionaryInfo info);

}