package com.orangereading.stardict.reader;

import java.util.stream.Stream;

import com.orangereading.stardict.model.DictionaryInfo;
import com.orangereading.stardict.reader.DictionaryInfoReader;

import junit.framework.TestCase;

public class DictionaryInfoReaderTest extends TestCase {

	public void testRead() {
		final DictionaryInfoReader reader = new DictionaryInfoReader();
		final DictionaryInfo info = reader.read(Stream.of("StarDict's dict ifo file", "version=2.4.2",
				"wordcount=100", "synwordcount=200", "idxfilesize=300", "idxoffsetbits=64", "bookname=Test Dict",
				"author=Tester", "email=test@test.com", "website=test.com", "description=some desc", "date=2003.08.26",
				"sametypesequence=m"));

		assertEquals("2.4.2", info.getVersion());
		assertEquals("Test Dict", info.getBookname());
		assertEquals(Integer.valueOf(100), info.getWordCount());
		assertEquals(Integer.valueOf(200), info.getSynWordCount());
		assertEquals(Long.valueOf(300L), info.getIdxFileSize());
		assertEquals(Integer.valueOf(64), info.getIdxOffsetBits());
		assertEquals("Tester", info.getAuthor());
		assertEquals("test@test.com", info.getEmail());
		assertEquals("test.com", info.getWebsite());
		assertEquals("some desc", info.getDescription());
		assertEquals("2003.08.26", info.getDate());
		assertEquals("m", info.getSameTypeSequence());
	}

}
