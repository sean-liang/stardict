package com.orangereading.stardict.io;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.orangereading.stardict.domain.DictionaryInfo;
import com.orangereading.stardict.domain.DictionarySynonymsIndex;
import com.orangereading.stardict.domain.DictionarySynonymsIndexItem;

/**
 * 
 * Read StarDict .syn file.
 * 
 * @author sean
 *
 */
public class DictionarySynonymsIndexReader {

	/**
	 * 
	 * Read StarDict .syn file.
	 * 
	 * @param info
	 *            dictionary meta data
	 * @param in
	 *            index file content in a ByteBuffer
	 * 
	 * @return synonyms index data
	 */
	public DictionarySynonymsIndex read(final DictionaryInfo info, final ByteBuffer in) {
		// The length of "synonym_word" should be less than 256.
		final ByteBuffer buffer = ByteBuffer.allocate(256);

		int counter = 0;
		final DictionarySynonymsIndex index = new DictionarySynonymsIndex(info.getSynWordCount());
		// index item format: synonym_word(256bytes) \0
		// original_word_index(32/64bit)
		while (in.remaining() > 0 && (null != info.getSynWordCount() && counter < info.getSynWordCount())) {
			final byte b = in.get();
			if (0 == b) {
				// get synonym_word
				buffer.flip();
				final byte[] bytes = new byte[buffer.limit()];
				buffer.get(bytes);
				final String word = new String(bytes, Charset.forName("UTF-8"));
				buffer.clear();

				// get index
				final Integer offset = in.getInt();

				index.addItem(new DictionarySynonymsIndexItem(word, offset));
				counter++;
			} else {
				buffer.put(b);
			}
		}

		return index;
	}

}
