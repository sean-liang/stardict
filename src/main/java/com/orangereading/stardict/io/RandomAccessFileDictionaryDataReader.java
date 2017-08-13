package com.orangereading.stardict.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.parser.DictionaryParser;

/**
 * 
 * Read normal(not compressed) StarDict .dict file.
 * 
 * @author sean
 *
 */
public class RandomAccessFileDictionaryDataReader implements DictionaryDataReader {

	private final RandomAccessFile file;

	private final DictionaryParser parser;

	public RandomAccessFileDictionaryDataReader(final DictionaryParser parser, final File file) {
		this.parser = parser;
		try {
			this.file = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DictionaryItem read(final DictionaryIndexItem indexItem) throws IOException {
		final byte[] data = new byte[indexItem.getSize()];
		this.file.read(data, indexItem.getOffset().intValue(), indexItem.getSize());
		return parser.parse(indexItem, data);
	}

	@Override
	public void close() throws IOException {
		this.file.close();
	}

}
