package com.orangereading.stardict.parser;

import java.util.Arrays;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.DictionaryItemEntry;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.domain.TypeIdentifier;

/**
 * 
 * Parse dictionary item with sametypesequence defined in .ifo file.
 * 
 * @author sean
 *
 */
public class SameTypeSequenceDictionaryParser implements DictionaryParser {

	private final ImmutableDictionaryInfo info;

	public SameTypeSequenceDictionaryParser(final ImmutableDictionaryInfo info) {
		this.info = info;
	}

	@Override
	public DictionaryItem parse(final DictionaryIndexItem indexItem, final byte[] bytes) {
		final DictionaryItem item = new DictionaryItem(indexItem);
		final int len = info.getSameTypeSequence().length;
		int offset = 0;
		for (int i = 0; i < len; i++) {
			final TypeIdentifier type = info.getSameTypeSequence()[i];

			if (i == len - 1) {
				// The omissions of last field's size information are the
				// optimizations required by the "sametypesequence" option
				// described.
				item.getEntries().add(new DictionaryItemEntry(type, Arrays.copyOfRange(bytes, offset, bytes.length)));
			} else {
				if (type.isUpperCase()) {
					// for fields with size(type identifier is upper case)
					final DataSlice slice = DictionaryParser.parseEntryWithSize(bytes, offset);
					offset = slice.getNext();
					item.getEntries().add(new DictionaryItemEntry(type, slice.getData()));
				} else {
					// for '\0' terminated field
					final DataSlice slice = DictionaryParser.parseEntryNullTail(bytes, offset);
					offset = slice.getNext();
					item.getEntries().add(new DictionaryItemEntry(type, slice.getData()));
				}
			}
		}
		return item;
	}

}
