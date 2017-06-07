package com.orangereading.stardict.io;

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
public class RandomAccessFileDictionaryReader implements DictionaryReader {

	private final RandomAccessFile file;

	private final DictionaryParser parser;

	public RandomAccessFileDictionaryReader(final DictionaryParser parser, final RandomAccessFile file) {
		this.parser = parser;
		this.file = file;
	}

	@Override
	public DictionaryItem read(final DictionaryIndexItem indexItem) throws IOException {
		final byte[] data = new byte[indexItem.getSize()];
		this.file.read(data, indexItem.getOffset().intValue(), indexItem.getSize());
		return parser.parse(indexItem, data);
	}

}
