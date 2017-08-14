package com.orangereading.stardict.exporter;

import java.io.IOException;

import com.orangereading.stardict.domain.DictionaryItem;

public interface DictionaryExporter {

	/**
	 * 
	 * Append a dictionary item.
	 * 
	 * @param item
	 *            dictionary item
	 * 
	 */
	public void append(DictionaryItem item);

	/**
	 * 
	 * Dump result and do cleanup works.
	 * 
	 * @throws IOException
	 * 
	 */
	public void done() throws IOException;

}
