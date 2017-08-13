package com.orangereading.stardict.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Consumer;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

/**
 * 
 * Read StarDict .idx file from Input Stream.
 * 
 * @author sean
 *
 */
public class InputStreamDictionaryIndexReader implements DictionaryIndexReader {

	private final DataInputStream in;

	public InputStreamDictionaryIndexReader(final InputStream in) {
		this.in = new DataInputStream(new BufferedInputStream(in));
	}

	@Override
	public void eachItem(final ImmutableDictionaryInfo info, final Consumer<DictionaryIndexItem> consumer) throws IOException {
		// The length of "word_str" should be less than 256.
		final ByteBuffer buf = ByteBuffer.allocate(256);

		int counter = 0;
		// index item format: word_str(256bytes) \0 word_data_offset(32/64bit)
		// word_data_size(32bit)
		while (null != info.getWordCount() && counter < info.getWordCount()) {
			byte b;
			try {
				b = this.in.readByte();
			} catch (EOFException ex) {
				break;
			}
			if (0 == b) {
				// get word_str
				buf.flip();
				final byte[] bytes = new byte[buf.limit()];
				buf.get(bytes);
				final String word = new String(bytes, Charset.forName("UTF-8"));
				buf.clear();

				// get offset
				final Long offset = Integer.valueOf(64).equals(info.getIdxOffsetBits()) ? this.in.readLong()
						: (long) this.in.readInt();

				// get size
				final Integer size = this.in.readInt();

				consumer.accept(new DictionaryIndexItem(word, offset, size));
				counter++;
			} else {
				buf.put(b);
			}
		}
	}

	@Override
	public void close() throws IOException {
		this.in.close();
	}

}
