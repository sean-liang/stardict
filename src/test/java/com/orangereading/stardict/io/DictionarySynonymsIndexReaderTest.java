package com.orangereading.stardict.io;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.orangereading.stardict.domain.DictionaryInfo;
import com.orangereading.stardict.domain.DictionarySynonymsIndex;
import com.orangereading.stardict.io.DictionarySynonymsIndexReader;

import junit.framework.TestCase;

public class DictionarySynonymsIndexReaderTest extends TestCase {

	@Test
	public void testRead() {
		final DictionaryInfo info = new DictionaryInfo();
		info.setSynWordCount(2);

		final DictionarySynonymsIndexReader reader = new DictionarySynonymsIndexReader();
		final DictionarySynonymsIndex indexes = reader.read(info, createInput());

		assertEquals(2, indexes.size());

		assertNotNull(indexes.getItem(0));
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals(Integer.valueOf(10), indexes.getItem(0).getIndex());

		assertNotNull(indexes.getItem(1));
		assertEquals("b", indexes.getItem(1).getWord());
		assertEquals(Integer.valueOf(20), indexes.getItem(1).getIndex());
	}

	@Test
	public void testReadWithLessWordCount() {
		final DictionaryInfo info = new DictionaryInfo();
		info.setSynWordCount(1);

		final DictionarySynonymsIndexReader reader = new DictionarySynonymsIndexReader();
		final DictionarySynonymsIndex indexes = reader.read(info, createInput());

		assertEquals(1, indexes.size());
		assertEquals("a", indexes.getItem(0).getWord());
	}

	@Test
	public void testReadWithMoreWordCount() {
		final DictionaryInfo info = new DictionaryInfo();
		info.setSynWordCount(3);

		final DictionarySynonymsIndexReader reader = new DictionarySynonymsIndexReader();
		final DictionarySynonymsIndex indexes = reader.read(info, createInput());

		assertEquals(2, indexes.size());
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals("b", indexes.getItem(1).getWord());
	}

	private ByteBuffer createInput() {
		final ByteBuffer buffer = ByteBuffer.allocate(12);
		buffer.put((byte) 97); // a
		buffer.position(buffer.position() + 1);
		buffer.putInt(10);
		buffer.put((byte) 98); // b
		buffer.position(buffer.position() + 1);
		buffer.putInt(20);
		buffer.flip();
		return buffer;
	}

}
