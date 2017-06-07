package com.orangereading.stardict.io;

import static org.mockito.Mockito.*;

import java.io.RandomAccessFile;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.parser.DictionaryParser;

import junit.framework.TestCase;

public class RandomAccessFileDictionaryReaderTest extends TestCase {

	@Test
	public void testRead() throws Exception {
		final DictionaryParser parser = mock(DictionaryParser.class);
		final RandomAccessFile file = mock(RandomAccessFile.class);

		final RandomAccessFileDictionaryReader reader = new RandomAccessFileDictionaryReader(parser, file);
		final DictionaryIndexItem index = new DictionaryIndexItem("Test Word", 100L, 20);
		final DictionaryItem result = new DictionaryItem(index);

		final ArgumentCaptor<byte[]> bytes = ArgumentCaptor.forClass(byte[].class);

		when(parser.parse(eq(index), any())).thenReturn(result);

		final DictionaryItem item = reader.read(index);

		verify(file, times(1)).readFully(bytes.capture(), eq(100), eq(20));
		assertEquals(20, bytes.getValue().length);
		assertEquals(result, item);

	}

}
