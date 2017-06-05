package com.orangereading.stardict.model;

import java.io.Serializable;

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
	private String word;

	// word data's offset in .dict file. If the version is "3.0.0" and
	// "idxoffsetbits=64", word_data_offset will be 64-bits unsigned number in
	// network byte order. Otherwise it will be 32-bits.
	private Long offset;

	// word data's total size in .dict file, 32-bits unsigned number in network
	// byte order.
	private Integer size;

	public DictionaryIndexItem() {
	}

	public DictionaryIndexItem(String word, Long offset, Integer size) {
		this.word = word;
		this.offset = offset;
		this.size = size;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "StarDictDictionaryIndexItem [word=" + word + ", offset=" + offset + ", size=" + size + "]";
	}

}
