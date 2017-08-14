package com.orangereading.stardict.domain;

import java.io.Serializable;
import java.util.Base64;

/**
 * 
 * StarDict dictionary index item.
 * 
 * @author sean
 *
 */
public class DictionaryIndexItem implements Serializable {

	private static final long serialVersionUID = -1612699881834769937L;

	// a utf-8 string represents word
	private final String word;

	// word data's offset in .dict file. If the version is "3.0.0" and
	// "idxoffsetbits=64", word_data_offset will be 64-bits unsigned number in
	// network byte order. Otherwise it will be 32-bits.
	private final Long offset;

	// word data's total size in .dict file, 32-bits unsigned number in network
	// byte order.
	private final Integer size;

	public DictionaryIndexItem(String word, Long offset, Integer size) {
		this.word = word;
		this.offset = offset;
		this.size = size;
	}

	public String getWord() {
		return word;
	}

	public String getWordAsBase64String() {
		return Base64.getEncoder().encodeToString(this.word.getBytes());
	}

	public Long getOffset() {
		return offset;
	}

	public Integer getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "StarDictDictionaryIndexItem [word=" + word + ", offset=" + offset + ", size=" + size + "]";
	}

}
