package com.orangereading.stardict.io;

import com.orangereading.stardict.domain.DictionaryInfo;

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
	DictionaryInfo read();

}