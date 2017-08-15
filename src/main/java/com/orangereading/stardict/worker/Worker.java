package com.orangereading.stardict.worker;

import java.io.IOException;

import com.orangereading.stardict.io.DictionaryReader;

public interface Worker {

	/**
	 * 
	 * Run worker.
	 * 
	 * @param name
	 *            dictionary name(without extension)
	 * @param reader
	 *            dictionary reader
	 *            
	 * @throws IOException
	 *             IO Exception
	 *             
	 */
	public void run(String name, DictionaryReader reader) throws IOException;

}
