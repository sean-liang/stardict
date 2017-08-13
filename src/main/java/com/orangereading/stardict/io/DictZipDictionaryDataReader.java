package com.orangereading.stardict.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.dict.zip.DictZipInputStream;
import org.dict.zip.RandomAccessInputStream;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.parser.DictionaryParser;

/**
 * 
 * Read compressed StarDict .dict.dz file.
 * 
 * @author sean
 *
 */
public class DictZipDictionaryDataReader implements DictionaryDataReader {

	private final DictionaryParser parser;

	private final DictZipInputStream steam;

	public DictZipDictionaryDataReader(final DictionaryParser parser, final File file) {
		this.parser = parser;
		try {
			this.steam = new DictZipInputStream(new RandomAccessInputStream(new RandomAccessFile(file, "r")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DictionaryItem read(final DictionaryIndexItem indexItem) throws IOException {
		final byte[] data = new byte[indexItem.getSize()];
		this.steam.read(data, indexItem.getOffset().intValue(), indexItem.getSize());
		return parser.parse(indexItem, data);
	}

	@Override
	public void close() throws IOException {
		this.steam.close();
	}

}
