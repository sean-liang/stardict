package com.orangereading.stardict.domain;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 
 * StarDict Dictionary Item.
 * 
 * @author sean
 *
 */
public class DictionaryItem implements Serializable {

	private static final long serialVersionUID = -1599690250183321142L;

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	// a single char identifying the data type
	private final TypeIdentifier type;

	// the data
	private final byte[] data;

	public DictionaryItem(final TypeIdentifier type, final byte[] data) {
		this.type = type;
		this.data = data;
	}

	public TypeIdentifier getType() {
		return type;
	}

	public byte[] getData() {
		return data;
	}

	public String getDataAsString(final Charset charset) {
		return new String(this.data, charset);
	}

	public String getDataAsString() {
		return new String(this.data, DEFAULT_CHARSET);
	}

	public String getDataAsBase64String() {
		return Base64.getEncoder().encodeToString(this.data);
	}

	@Override
	public String toString() {
		return "DictionaryItem [type=" + type + ", data length=" + data.length + "]";
	}

}
