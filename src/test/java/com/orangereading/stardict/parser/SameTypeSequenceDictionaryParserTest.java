package com.orangereading.stardict.parser;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.TypeIdentifier;

import junit.framework.TestCase;

public class SameTypeSequenceDictionaryParserTest extends TestCase {

	@Test
	public void testParse() {
		final SameTypeSequenceDictionaryParser parser = new SameTypeSequenceDictionaryParser(new TypeIdentifier[] { TypeIdentifier.WAVE, TypeIdentifier.TEXT,
				TypeIdentifier.PICTURE, TypeIdentifier.ENGLISH_PHONETIC });

		final ByteBuffer buffer = ByteBuffer.allocate(21);
		buffer.putInt(3);
		buffer.put("abc".getBytes());
		buffer.put("123".getBytes());
		buffer.put((byte) '\0');
		buffer.putInt(3);
		buffer.put("efg".getBytes());
		buffer.put("456".getBytes());
		buffer.flip();

		final DictionaryItem item = parser.parse(new DictionaryIndexItem("test word", 0L, 21), buffer.array());
		assertEquals(4, item.getEntries().size());

		assertEquals(TypeIdentifier.WAVE, item.getEntries().get(0).getType());
		assertEquals("abc", item.getEntries().get(0).getDataAsUTF8String());

		assertEquals(TypeIdentifier.TEXT, item.getEntries().get(1).getType());
		assertEquals("123", item.getEntries().get(1).getDataAsUTF8String());

		assertEquals(TypeIdentifier.PICTURE, item.getEntries().get(2).getType());
		assertEquals("efg", item.getEntries().get(2).getDataAsUTF8String());

		assertEquals(TypeIdentifier.ENGLISH_PHONETIC, item.getEntries().get(3).getType());
		assertEquals("456", item.getEntries().get(3).getDataAsUTF8String());
	}

}
