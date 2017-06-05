package com.orangereading.model;

import java.io.Serializable;

/**
 * 
 * StarDict dictionary synonyms index item.
 * 
 * @author sean
 *
 */
public class StarDictDictionarySynonymsIndexItem implements Serializable {

	private static final long serialVersionUID = 6672567268969497023L;

	private String word;

	private Integer index;

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
