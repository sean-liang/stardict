package com.orangereading.stardict.parser;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.orangereading.stardict.parser.DataSlice;
import com.orangereading.stardict.parser.DictionaryParser;

import junit.framework.TestCase;

public class DictionaryParserTest extends TestCase {

	@Test
	public void testParseEntryNullTail() {
		final ByteBuffer data = ByteBuffer.allocate(10);
		data.position(2);
		data.put("abc".getBytes());
		data.put((byte) '\0');
		data.put("xyz".getBytes());
		data.flip();

		final DataSlice slice = DictionaryParser.parseEntryNullTail(data.array(), 2);
		assertEquals("abc", new String(slice.getData()));
		assertEquals(2, slice.getStart());
		assertEquals(5, slice.getEnd());
		assertEquals(6, slice.getNext());
	}

	@Test
	public void testParseEntryWithSize() {
		final ByteBuffer data = ByteBuffer.allocate(20);
		data.position(2);
		data.putInt(3);
		data.put("abc".getBytes());
		data.put("xyz".getBytes());
		data.flip();

		final DataSlice slice = DictionaryParser.parseEntryWithSize(data.array(), 2);
		assertEquals("abc", new String(slice.getData()));
		assertEquals(6, slice.getStart());
		assertEquals(9, slice.getEnd());
		assertEquals(9, slice.getNext());
	}

}
