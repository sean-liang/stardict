package com.orangereading.stardict.exporter;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.orangereading.stardict.domain.DictionaryIndexItem;
import com.orangereading.stardict.domain.DictionaryInfo;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.DictionaryItemEntry;
import com.orangereading.stardict.domain.TypeIdentifier;

public class JAXBXmlDictionaryExporterTest {

	@Test
	public void testExport() throws Exception {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		final DictionaryInfo info = new DictionaryInfo();
		info.setBookname("Test Book");
		info.setVersion("1.0");
		info.setWordCount(2);
		info.setDate("2000-01-01");
		info.setAuthor("anonymous");
		info.setEmail("anonymous@somecorp.com");
		info.setWebsite("http://www.somecorp.com");
		info.setDescription("Test Dict");

		final JAXBXmlDictionaryExporter exporter = new JAXBXmlDictionaryExporter(info, os);

		final DictionaryItem item1 = new DictionaryItem(new DictionaryIndexItem("word1", 0L, 5));
		item1.getEntries().add(new DictionaryItemEntry(TypeIdentifier.TEXT, "translate1a".getBytes()));
		item1.getEntries().add(new DictionaryItemEntry(TypeIdentifier.TEXT_LOCALE, "translate1b".getBytes()));
		exporter.append(item1);

		final DictionaryItem item2 = new DictionaryItem(new DictionaryIndexItem("word2", 5L, 5));
		item2.getEntries().add(new DictionaryItemEntry(TypeIdentifier.PICTURE, "image".getBytes()));
		exporter.append(item2);

		exporter.done();

		System.out.println(os.toString());
	}

}
