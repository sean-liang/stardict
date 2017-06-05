package com.orangereading.stardict.model;

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
	private String word;

	// a 32-bits unsigned number in network byte order
	private Integer index;

	public DictionarySynonymsIndexItem(String word, Integer index) {
		this.word = word;
		this.index = index;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "StarDictDictionarySynonymsIndexItem [word=" + word + ", index=" + index + "]";
	}

}
