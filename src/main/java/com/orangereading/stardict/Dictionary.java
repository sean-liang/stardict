package com.orangereading.stardict;

import java.util.function.Consumer;

import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

public interface Dictionary {
	
	public ImmutableDictionaryInfo getInfo();
	
	public void eachWord(Consumer<DictionaryItem> consumer);

}
