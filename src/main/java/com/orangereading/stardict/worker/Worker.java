package com.orangereading.stardict.worker;

import java.io.IOException;

import com.orangereading.stardict.io.DictionaryReader;

public interface Worker {
	
	public void run(String name, DictionaryReader reader) throws IOException;

}
