package com.orangereading.stardict.io;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.parser.DictionaryParser;

import junit.framework.TestCase;

public class RandomAccessFileDictionaryDataReaderTest extends TestCase {

	@Test
	public void testRead() throws Exception {
		final DictionaryParser parser = mock(DictionaryParser.class);
		final RandomAccessFile file = mock(RandomAccessFile.class);

		final Field field = RandomAccessFileDictionaryDataReader.class.getDeclaredField("file");
		field.setAccessible(true);
		
		final RandomAccessFileDictionaryDataReader reader = new RandomAccessFileDictionaryDataReader(parser,
				new File(RandomAccessFileDictionaryDataReaderTest.class.getResource("/mockfile").toURI()));
		field.set(reader, file);
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
