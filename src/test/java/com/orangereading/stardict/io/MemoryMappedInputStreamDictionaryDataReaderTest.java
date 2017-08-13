package com.orangereading.stardict.io;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.TypeIdentifier;
import com.orangereading.stardict.parser.DictionaryParser;
import com.orangereading.stardict.parser.SameTypeSequenceDictionaryParser;

import junit.framework.TestCase;

public class MemoryMappedInputStreamDictionaryDataReaderTest extends TestCase {

	@Test
	public void testRead() throws Exception {
		final ByteBuffer buffer = ByteBuffer.allocate(3);
		buffer.put("abc".getBytes());

		final ByteArrayInputStream in = new ByteArrayInputStream(buffer.array());
		final DictionaryParser parser = new SameTypeSequenceDictionaryParser(new TypeIdentifier[] { TypeIdentifier.WAVE });

		final MemoryMappedInputStreamDictionaryDataReader reader = new MemoryMappedInputStreamDictionaryDataReader(parser, in);

		final DictionaryIndexItem index = new DictionaryIndexItem("Test Word", 0L, 3);
		final DictionaryItem item = reader.read(index);
		assertEquals(index, item.getIndex());
		assertEquals(1, item.getEntries().size());
		assertEquals("abc", item.getEntries().get(0).getDataAsUTF8String());
	}

}
