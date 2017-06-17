package com.orangereading.stardict;

public class DictionaryLoader {

	private String path;

	private String name;

	private DictionaryLoader() {

	}

	public DictionaryLoader path(final String path) {
		this.path = path;
		return this;
	}

	public DictionaryLoader name(final String name) {
		this.name = name;
		return this;
	}

	public Dictionary load() {
		return new DictionaryImpl(this.path, this.name);
	}

	public static DictionaryLoader create() {
		return new DictionaryLoader();
	}

}
