package com.orangereading.stardict.io;

import java.nio.channels.SeekableByteChannel;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;

public interface DictionaryReader {

	public DictionaryItem[] read(final DictionaryIndexItem indexItem, final SeekableByteChannel channel);

}
