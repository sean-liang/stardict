package com.orangereading.stardict.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryInfo;

import junit.framework.TestCase;

public class InputStreamDictionaryIndexReaderTest extends TestCase {

	@Test
	public void testRead32bitOffset() throws Exception {
		final DictionaryInfo info = new DictionaryInfo();
		info.setWordCount(2);

		final DictionaryIndexReader reader = new InputStreamDictionaryIndexReader(create32BitInput());
		final List<DictionaryIndexItem> indexes = new ArrayList<>();
		
		reader.eachItem(info, item -> indexes.add(item));

		assertEquals(2, indexes.size());

		assertNotNull(indexes.get(0));
		assertEquals("a", indexes.get(0).getWord());
		assertEquals(Long.valueOf(0), indexes.get(0).getOffset());
		assertEquals(Integer.valueOf(128), indexes.get(0).getSize());

		assertNotNull(indexes.get(1));
		assertEquals("b", indexes.get(1).getWord());
		assertEquals(Long.valueOf(128), indexes.get(1).getOffset());
		assertEquals(Integer.valueOf(64), indexes.get(1).getSize());
		
		reader.close();
	}

	@Test
	public void testRead64bitOffset() throws Exception {
		final DictionaryInfo info = new DictionaryInfo();
		info.setWordCount(2);
		info.setIdxOffsetBits(64);

		final DictionaryIndexReader reader = new InputStreamDictionaryIndexReader(create64BitInput());
		final List<DictionaryIndexItem> indexes = new ArrayList<>();
		
		reader.eachItem(info, item -> indexes.add(item));

		assertEquals(2, indexes.size());

		assertNotNull(indexes.get(0));
		assertEquals("a", indexes.get(0).getWord());
		assertEquals(Long.valueOf(0), indexes.get(0).getOffset());
		assertEquals(Integer.valueOf(128), indexes.get(0).getSize());

		assertNotNull(indexes.get(1));
		assertEquals("b", indexes.get(1).getWord());
		assertEquals(Long.valueOf(128), indexes.get(1).getOffset());
		assertEquals(Integer.valueOf(64), indexes.get(1).getSize());
		
		reader.close();
	}

	@Test
	public void testReadWithLessWordCount() throws Exception {
		final DictionaryInfo info = new DictionaryInfo();
		info.setWordCount(1);

		final DictionaryIndexReader reader = new InputStreamDictionaryIndexReader(create32BitInput());
		final List<DictionaryIndexItem> indexes = new ArrayList<>();
		
		reader.eachItem(info, item -> indexes.add(item));

		assertEquals(1, indexes.size());
		assertEquals("a", indexes.get(0).getWord());
		
		reader.close();
	}

	@Test
	public void testReadWithMoreWordCount() throws Exception {
		final DictionaryInfo info = new DictionaryInfo();
		info.setWordCount(3);

		final DictionaryIndexReader reader = new InputStreamDictionaryIndexReader(create32BitInput());
		final List<DictionaryIndexItem> indexes = new ArrayList<>();
		
		reader.eachItem(info, item -> indexes.add(item));

		assertEquals(2, indexes.size());
		assertEquals("a", indexes.get(0).getWord());
		assertEquals("b", indexes.get(1).getWord());
		
		reader.close();
	}

	private InputStream create32BitInput() {
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
		return new ByteArrayInputStream(buffer.array());
	}

	private InputStream create64BitInput() {
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
		return new ByteArrayInputStream(buffer.array());
	}

}
