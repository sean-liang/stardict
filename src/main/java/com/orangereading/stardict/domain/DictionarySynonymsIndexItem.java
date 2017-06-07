package com.orangereading.stardict.domain;

import java.io.Serializable;

/**
 * 
 * StarDict dictionary synonyms index item.
 * 
 * @author sean
 *
 */
public class DictionarySynonymsIndexItem implements Serializable {

	private static final long serialVersionUID = 6672567268969497023L;

	// synonym_word, a utf-8 string, length < 256
	private final String word;

	// a 32-bits unsigned number in network byte order
	private final Integer index;

	public DictionarySynonymsIndexItem(final String word, final Integer index) {
		this.word = word;
		this.index = index;
	}

	public String getWord() {
		return word;
	}

	public Integer getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "StarDictDictionarySynonymsIndexItem [word=" + word + ", index=" + index + "]";
	}

}
