package com.orangereading.stardict.io;

import java.io.IOException;

import com.orangereading.stardict.domain.ImmutableDictionaryIndex;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

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
	ImmutableDictionaryIndex read(ImmutableDictionaryInfo info) throws IOException;

}