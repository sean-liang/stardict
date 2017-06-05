package com.orangereading.reader;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.orangereading.model.StarDictDictionaryIndex;
import com.orangereading.model.StarDictDictionaryIndexItem;
import com.orangereading.model.StarDictDictionaryInfo;

/**
 * 
 * Read StarDict .idx file.
 * 
 * @author sean
 *
 */
public class StarDictDictionaryIndexReader {

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
	public StarDictDictionaryIndex read(final StarDictDictionaryInfo info, final ByteBuffer in) {
		// The length of "word_str" should be less than 256.
		final ByteBuffer buffer = ByteBuffer.allocate(256);

		int counter = 0;
		final StarDictDictionaryIndex index = new StarDictDictionaryIndex(info.getWordCount());
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

				index.addItem(new StarDictDictionaryIndexItem(word, offset, size));
				counter++;
			} else {
				buffer.put(b);
			}
		}

		return index;
	}

}
