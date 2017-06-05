package com.orangereading.stardict.reader;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.orangereading.stardict.model.DictionaryIndex;
import com.orangereading.stardict.model.DictionaryIndexItem;
import com.orangereading.stardict.model.DictionaryInfo;

/**
 * 
 * Read StarDict .idx file.
 * 
 * @author sean
 *
 */
public class DictionaryIndexReader {

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
	public DictionaryIndex read(final DictionaryInfo info, final ByteBuffer in) {
		// The length of "word_str" should be less than 256.
		final ByteBuffer buffer = ByteBuffer.allocate(256);

		int counter = 0;
		final DictionaryIndex index = new DictionaryIndex(info.getWordCount());
		// index item format: word_str(256bytes) \0 word_data_offset(32/64bit)
		// word_data_size(32bit)
		while (in.remaining() > 0 && (null != info.getWordCount() && counter < info.getWordCount())) {
			final byte b = in.get();
			if (0 == b) {
				// get word_str
				buffer.flip();
				final byte[] bytes = new byte[buffer.limit()];
				buffer.get(bytes);
				final String word = new String(bytes, Charset.forName("UTF-8"));
				buffer.clear();

				// get offset
				final Long offset = Integer.valueOf(64).equals(info.getIdxOffsetBits()) ? in.getLong()
						: (long) in.getInt();

				// get size
				final Integer size = in.getInt();

				index.addItem(new DictionaryIndexItem(word, offset, size));
				counter++;
			} else {
				buffer.put(b);
			}
		}

		return index;
	}

}
