package com.orangereading.reader;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.orangereading.model.StarDictDictionaryInfo;
import com.orangereading.model.StarDictDictionarySynonymsIndex;
import com.orangereading.model.StarDictDictionarySynonymsIndexItem;

/**
 * 
 * Read StarDict .syn file.
 * 
 * @author sean
 *
 */
public class StarDictDictionarySynonymsIndexReader {

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
	public StarDictDictionarySynonymsIndex read(final StarDictDictionaryInfo info, final ByteBuffer in) {
		// The length of "synonym_word" should be less than 256.
		final ByteBuffer buffer = ByteBuffer.allocate(256);

		int counter = 0;
		final StarDictDictionarySynonymsIndex index = new StarDictDictionarySynonymsIndex(info.getSynWordCount());
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

				index.addItem(new StarDictDictionarySynonymsIndexItem(word, offset));
				counter++;
			} else {
				buffer.put(b);
			}
		}

		return index;
	}

}
