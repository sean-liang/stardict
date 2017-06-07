package com.orangereading.stardict.parser;

import java.util.Arrays;

/**
 * 
 * Represent a slice of data from a byte array.
 * 
 * @author sean
 *
 */
class DataSlice {

	// whole data in bytes
	private final byte[] data;

	// start position of the slice
	private final int start;

	// end position of the slice
	private final int end;

	// start position of next slice(skip the tail '/0')
	private final int next;

	public DataSlice(final byte[] data, final int start, final int end, final int next) {
		this.data = data;
		this.start = start;
		this.end = end;
		this.next = next;
	}

	public byte[] getData() {
		return data;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getNext() {
		return next;
	}

	@Override
	public String toString() {
		return "DataSlice [data length=" + data.length + ", start=" + start + ", end=" + end + ", next=" + next + "]";
	}

	public static DataSlice from(final byte[] data, final int from, final int to, final int next) {
		return new DataSlice(Arrays.copyOfRange(data, from, to), from, to, next);
	}

}
