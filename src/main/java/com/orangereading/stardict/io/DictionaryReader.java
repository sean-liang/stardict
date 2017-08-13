package com.orangereading.stardict.io;

import java.io.IOException;
import java.util.function.Consumer;

import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

public interface DictionaryReader {
	
	public ImmutableDictionaryInfo getInfo();
	
	public void eachWord(Consumer<DictionaryItem> consumer) throws IOException;

}
