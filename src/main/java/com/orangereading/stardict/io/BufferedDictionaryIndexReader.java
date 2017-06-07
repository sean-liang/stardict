package com.orangereading.stardict.io;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.orangereading.stardict.domain.DictionaryIndex;
import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryInfo;

/**
 * 
 * Read StarDict .idx file from ByteBuffer.
 * 
 * @author sean
 *
 */
public class BufferedDictionaryIndexReader implements DictionaryIndexReader {

	private final ByteBuffer buffer;

	public BufferedDictionaryIndexReader(final ByteBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public DictionaryIndex read(final DictionaryInfo info) {
		// The length of "word_str" should be less than 256.
		final ByteBuffer buf = ByteBuffer.allocate(256);

		int counter = 0;
		final DictionaryIndex index = new DictionaryIndex(info.getWordCount());
		// index item format: word_str(256bytes) \0 word_data_offset(32/64bit)
		// word_data_size(32bit)
		while (this.buffer.remaining() > 0 && (null != info.getWordCount() && counter < info.getWordCount())) {
			final byte b = this.buffer.get();
			if (0 == b) {
				// get word_str
				buf.flip();
				final byte[] bytes = new byte[buf.limit()];
				buf.get(bytes);
				final String word = new String(bytes, Charset.forName("UTF-8"));
				buf.clear();

				// get offset
				final Long offset = Integer.valueOf(64).equals(info.getIdxOffsetBits()) ? this.buffer.getLong()
						: (long) this.buffer.getInt();

				// get size
				final Integer size = this.buffer.getInt();

				index.addItem(new DictionaryIndexItem(word, offset, size));
				counter++;
			} else {
				buf.put(b);
			}
		}

		return index;
	}

}
