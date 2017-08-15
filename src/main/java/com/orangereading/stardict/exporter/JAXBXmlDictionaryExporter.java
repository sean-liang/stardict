package com.orangereading.stardict.exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.orangereading.stardict.cli.CommandExport;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.DictionaryItemEntry;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

/**
 * 
 * Export dictionary to xml format with JAXB.
 * 
 * @author sean
 *
 */
public class JAXBXmlDictionaryExporter implements DictionaryExporter {

	private OutputStream stream;

	private XMLDictRoot dict;

	public void init(final ImmutableDictionaryInfo info, final String name, final CommandExport command) {
		this.dict = new XMLDictRoot(info);
		try {
			this.stream = new FileOutputStream(Paths.get(command.getOutput(), name + ".xml").toFile());
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void append(final DictionaryItem item) {
		this.dict.addItem(item);
	}

	@Override
	public void done() throws IOException {
		try {
			final JAXBContext ctx = JAXBContext.newInstance(XMLDictRoot.class);
			final Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(this.dict, this.stream);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			this.stream.close();
		}
	}

	@XmlType(name = "entry")
	private static class XMLDictItemEntry {

		@XmlAttribute
		private String type;

		@XmlValue
		private String data;

		public XMLDictItemEntry() {

		}

		public XMLDictItemEntry(final DictionaryItemEntry entry) {
			this.type = entry.getType().name();
			this.data = entry.getDataAsBase64String();
		}

	}

	private static class XMLDictItem {

		@XmlElement
		private String word;

		@XmlElementWrapper(name = "entries")
		@XmlElement(name = "entry")
		private List<XMLDictItemEntry> entries;

		public XMLDictItem() {

		}

		public XMLDictItem(final DictionaryItem item) {
			this.word = item.getIndex().getWordAsBase64String();
			this.entries = new ArrayList<>();
			item.getEntries().forEach(e -> this.entries.add(new XMLDictItemEntry(e)));
		}
	}

	@XmlRootElement(name = "dict")
	private static class XMLDictRoot {

		@XmlElement
		private String bookname;

		@XmlElement
		private String version;

		@XmlElement
		private Integer wordCount;

		@XmlElement
		private String author;

		@XmlElement
		private String email;

		@XmlElement
		private String website;

		@XmlElement
		private String description;

		@XmlElement
		private String date;

		@XmlElementWrapper(name = "items")
		@XmlElement(name = "item")
		private List<XMLDictItem> items;

		public XMLDictRoot() {

		}

		public XMLDictRoot(final ImmutableDictionaryInfo info) {
			this.bookname = info.getBookname();
			this.version = info.getVersion();
			this.wordCount = info.getWordCount();
			this.author = info.getAuthor();
			this.email = info.getEmail();
			this.website = info.getWebsite();
			this.description = info.getDescription();
			this.date = info.getDate();
			this.items = new ArrayList<>();
		}

		public void addItem(final DictionaryItem item) {
			this.items.add(new XMLDictItem(item));
		}

	}

}
