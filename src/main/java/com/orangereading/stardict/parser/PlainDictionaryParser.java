package com.orangereading.stardict.parser;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.DictionaryItemEntry;
import com.orangereading.stardict.domain.TypeIdentifier;

/**
 * 
 * Parse plain dictionary item.
 * 
 * @author sean
 *
 */
public class PlainDictionaryParser implements DictionaryParser {

	@Override
	public DictionaryItem parse(final DictionaryIndexItem indexItem, final byte[] bytes) {
		final DictionaryItem item = new DictionaryItem(indexItem);

		final int len = bytes.length;
		int offset = 0;
		while (true) {
			final TypeIdentifier type = TypeIdentifier.valueOf((char) bytes[offset]);
			offset++;
			DataSlice slice = null;
			if (type.isUpperCase()) {
				slice = DictionaryParser.parseEntryWithSize(bytes, offset);
			} else {
				slice = DictionaryParser.parseEntryNullTail(bytes, offset);
			}
			item.getEntries().add(new DictionaryItemEntry(type, slice.getData()));
			offset = slice.getNext();
			if (offset >= len) {
				break;
			}
		}

		return item;
	}

}
