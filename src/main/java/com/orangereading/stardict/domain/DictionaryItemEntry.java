package com.orangereading.stardict.domain;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;

public class DictionaryItemEntry implements Serializable {

	private static final long serialVersionUID = 536664796504320977L;

	// a single char identifying the data type
	private final TypeIdentifier type;

	// the data
	private final byte[] data;

	public DictionaryItemEntry(final TypeIdentifier type, final byte[] data) {
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

	public String getDataAsLocalString() {
		return new String(this.data);
	}
	
	public String getDataAsUTF8String() {
		return new String(this.data, Charset.forName("UTF-8"));
	}

	public String getDataAsBase64String() {
		return Base64.getEncoder().encodeToString(this.data);
	}

	@Override
	public String toString() {
		return "DictionaryItemEntry [type=" + type + ", data=" + Arrays.toString(data) + "]";
	}

}
