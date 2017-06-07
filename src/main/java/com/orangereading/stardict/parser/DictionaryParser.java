package com.orangereading.stardict.parser;

import java.nio.ByteBuffer;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;

/**
 * 
 * Parse StarDict .dict item from bytes.
 * 
 * @author sean
 *
 */
public interface DictionaryParser {

	final static int INT_BYTES = 4;

	/**
	 * 
	 * Parse dictionary item.
	 * 
	 * @param info
	 *            meta data
	 * @param indexItem
	 *            index
	 * @param bytes
	 *            item content in bytes
	 * 
	 * @return dictionary item
	 */
	public DictionaryItem parse(final DictionaryIndexItem indexItem, final byte[] bytes);

	/**
	 * 
	 * Extract '\0' tail dictionary item entry from bytes.
	 * 
	 * @param data
	 *            bytes
	 * @param offset
	 *            offset
	 * 
	 * @return data slice of the entry
	 */
	static DataSlice parseEntryNullTail(final byte[] data, final int offset) {
		final int len = data.length;
		int pos = offset;
		for (int i = offset; i < len; i++) {
			final byte b = data[i];
			if ('\0' == b) {
				break;
			}
			pos++;
		}
		return DataSlice.from(data, offset, pos, pos + 1);
	}

	/**
	 * 
	 * Extract item entry with size info.
	 * 
	 * @param data
	 *            bytes
	 * @param offset
	 *            offset
	 * 
	 * @return data slice of the entry
	 */
	static DataSlice parseEntryWithSize(final byte[] data, final int offset) {
		final ByteBuffer buffer = ByteBuffer.wrap(data);
		final int len = data.length;
		buffer.position(offset);
		final int size = buffer.getInt();
		if (offset + size > len) {
			throw new RuntimeException("illegal size");
		}
		final int next = offset + INT_BYTES + size;
		return DataSlice.from(data, offset + INT_BYTES, next, next);
	}

}
