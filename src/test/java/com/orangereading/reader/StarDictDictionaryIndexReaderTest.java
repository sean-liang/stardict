package com.orangereading.reader;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.orangereading.model.StarDictDictionaryIndex;
import com.orangereading.model.StarDictDictionaryInfo;

import junit.framework.TestCase;

public class StarDictDictionaryIndexReaderTest extends TestCase {

	@Test
	public void testRead32bitOffset() {
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(2);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, create32BitInput());

		assertEquals(2, indexes.size());

		assertNotNull(indexes.getItem(0));
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals(Long.valueOf(0), indexes.getItem(0).getOffset());
		assertEquals(Integer.valueOf(128), indexes.getItem(0).getSize());

		assertNotNull(indexes.getItem(1));
		assertEquals("b", indexes.getItem(1).getWord());
		assertEquals(Long.valueOf(128), indexes.getItem(1).getOffset());
		assertEquals(Integer.valueOf(64), indexes.getItem(1).getSize());
	}

	@Test
	public void testRead64bitOffset() {
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(2);
		info.setIdxOffsetBits(64);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, create64BitInput());

		assertEquals(2, indexes.size());

		assertNotNull(indexes.getItem(0));
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals(Long.valueOf(0), indexes.getItem(0).getOffset());
		assertEquals(Integer.valueOf(128), indexes.getItem(0).getSize());

		assertNotNull(indexes.getItem(1));
		assertEquals("b", indexes.getItem(1).getWord());
		assertEquals(Long.valueOf(128), indexes.getItem(1).getOffset());
		assertEquals(Integer.valueOf(64), indexes.getItem(1).getSize());
	}

	@Test
	public void testReadWithLessWordCount() {
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(1);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, create32BitInput());

		assertEquals(1, indexes.size());
		assertEquals("a", indexes.getItem(0).getWord());
	}

	@Test
	public void testReadWithMoreWordCount() {
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(3);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, create32BitInput());

		assertEquals(2, indexes.size());
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals("b", indexes.getItem(1).getWord());
	}

	private ByteBuffer create32BitInput() {
		final ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.put((byte) 97); // a
		buffer.position(buffer.position() + 1);
		buffer.putInt(0);
		buffer.putInt(128);
		buffer.put((byte) 98); // b
		buffer.position(buffer.position() + 1);
		buffer.putInt(128);
		buffer.putInt(64);
		buffer.flip();
		return buffer;
	}

	private ByteBuffer create64BitInput() {
		final ByteBuffer buffer = ByteBuffer.allocate(28);
		buffer.put((byte) 97); // a
		buffer.position(buffer.position() + 1);
		buffer.putLong(0);
		buffer.putInt(128);
		buffer.put((byte) 98); // b
		buffer.position(buffer.position() + 1);
		buffer.putLong(128);
		buffer.putInt(64);
		buffer.flip();
		return buffer;
	}

}
