package com.orangereading.stardict.io;

import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

/**
 * 
 * Read StarDict .ifo file.
 * 
 * @author sean
 *
 */
public interface DictionaryInfoReader {

	/**
	 * 
	 * Read StarDict .ifo file.
	 * 
	 * @return meta data
	 * 
	 */
	public ImmutableDictionaryInfo read();

}