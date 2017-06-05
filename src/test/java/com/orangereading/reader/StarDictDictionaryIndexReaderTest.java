package com.orangereading.reader;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.orangereading.TestUtils;
import com.orangereading.model.StarDictDictionaryIndex;
import com.orangereading.model.StarDictDictionaryInfo;

import junit.framework.TestCase;

public class StarDictDictionaryIndexReaderTest extends TestCase {

	@Test
	public void testRead32bitOffset() {
		final ByteBuffer in = ByteBuffer.wrap(TestUtils.hexStringToByteArray(
				"61000000 00000000 00844120 616E6420 42206167 676C7574 696E6F67 656E7300 00000084 00000018"));
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(2);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, in);

		assertEquals(2, indexes.size());

		assertNotNull(indexes.getItem(0));
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals(Long.valueOf(0), indexes.getItem(0).getOffset());
		assertEquals(Integer.valueOf(132), indexes.getItem(0).getSize());

		assertNotNull(indexes.getItem(1));
		assertEquals("A and B agglutinogens", indexes.getItem(1).getWord());
		assertEquals(Long.valueOf(132), indexes.getItem(1).getOffset());
		assertEquals(Integer.valueOf(24), indexes.getItem(1).getSize());
	}

	@Test
	public void testRead64bitOffset() {
		// TODO need to find dictionary with 64bit offset
	}

	@Test
	public void testReadWithLessWordCount() {
		final ByteBuffer in = ByteBuffer.wrap(TestUtils.hexStringToByteArray(
				"61000000 00000000 00844120 616E6420 42206167 676C7574 696E6F67 656E7300 00000084 00000018"));
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(1);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, in);

		assertEquals(1, indexes.size());
		assertEquals("a", indexes.getItem(0).getWord());
	}

	@Test
	public void testReadWithMoreWordCount() {
		final ByteBuffer in = ByteBuffer.wrap(TestUtils.hexStringToByteArray(
				"61000000 00000000 00844120 616E6420 42206167 676C7574 696E6F67 656E7300 00000084 00000018"));
		final StarDictDictionaryInfo info = new StarDictDictionaryInfo();
		info.setWordCount(3);

		final StarDictDictionaryIndexReader reader = new StarDictDictionaryIndexReader();
		final StarDictDictionaryIndex indexes = reader.read(info, in);

		assertEquals(2, indexes.size());
		assertEquals("a", indexes.getItem(0).getWord());
		assertEquals("A and B agglutinogens", indexes.getItem(1).getWord());
	}

}
