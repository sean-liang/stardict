package com.orangereading.stardict.exporter;

import java.io.IOException;

import com.orangereading.stardict.cli.CommandExport;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

public interface DictionaryExporter {

	/**
	 * 
	 * Initialize the exporter. Will be called by the container right after new
	 * instance created.
	 * 
	 * @param info
	 *            dictionary info
	 * @param name
	 *            dictionary file name(without extension)
	 * @param command
	 *            cli command
	 * 
	 */
	public void init(final ImmutableDictionaryInfo info, final String name, final CommandExport command);

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
	 *             IO Exception
	 * 
	 */
	public void done() throws IOException;

}
