
package com.orangereading.stardict.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryIndex;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.parser.DictionaryParser;
import com.orangereading.stardict.parser.PlainDictionaryParser;
import com.orangereading.stardict.parser.SameTypeSequenceDictionaryParser;
import com.orangereading.stardict.util.FileExtensionUtils;

public class CommonFileDictionaryReader implements DictionaryReader {

	private String infoFilePath = null;

	private String indexFilePath = null;

	private String dictFilePath = null;

	private ImmutableDictionaryInfo info;

	private DictionaryParser parser;

	/**
	 * Load dictionary from files in the same path.<br>
	 * 
	 * The info file, index file and dictionary file should share the same file
	 * name.<br>
	 * 
	 * It will detect the file extension for index and dictionary file
	 * automatically.
	 * 
	 * @param path
	 *            path of the directory that contains the files
	 * @param name
	 *            file name
	 * 
	 */
	public CommonFileDictionaryReader(final String path, final String name) {
		final File dir = new File(path);
		if (!dir.isDirectory()) {
			return;
		}

		for (File f : dir.listFiles()) {
			if (!f.isFile()) {
				continue;
			} else if (FileExtensionUtils.isDictionaryInfoFile(f.getName())) {
				this.infoFilePath = f.getAbsolutePath();
			} else if (FileExtensionUtils.isDictionaryIndexFile(f.getName())) {
				this.indexFilePath = f.getAbsolutePath();
			} else if (FileExtensionUtils.isDictionaryDataFile(f.getName())) {
				this.dictFilePath = f.getAbsolutePath();
			}
		}

		this.info = this.loadInfo();
		if (null == this.info.getSameTypeSequence()) {
			this.parser = new PlainDictionaryParser();
		} else {
			this.parser = new SameTypeSequenceDictionaryParser(this.info.getSameTypeSequence());
		}
	}

	@Override
	public ImmutableDictionaryInfo getInfo() {
		return this.info;
	}

	@Override
	public void eachWord(final Consumer<DictionaryItem> consumer) throws IOException {
		final ImmutableDictionaryIndex index = this.loadIndex();

		if (null == this.dictFilePath) {
			throw new RuntimeException("Dictionary file absent.");
		}

		final AtomicReference<DictionaryDataReader> reader = new AtomicReference<>();
		if (FileExtensionUtils.isCompressedDictionaryDataFile(this.dictFilePath)) {
			reader.set(new DictZipDictionaryDataReader(this.parser, new File(this.dictFilePath)));
		} else {
			reader.set(new RandomAccessFileDictionaryDataReader(this.parser, new File(this.dictFilePath)));
		}
		index.getItems().forEach(item -> {
			try {
				consumer.accept(reader.get().read(item));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		reader.get().close();
	}

	private ImmutableDictionaryInfo loadInfo() {
		// Read dictionary info file
		ImmutableDictionaryInfo info;
		try (final Stream<String> lines = Files.lines(Paths.get(this.infoFilePath))) {
			final DictionaryInfoReader reader = new StreamDictionaryInfoReader(lines);
			info = reader.read();
			lines.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return info;
	}

	private ImmutableDictionaryIndex loadIndex() throws IOException {
		if (null == this.indexFilePath) {
			throw new RuntimeException("Index file absent.");
		}
		InputStream in = new FileInputStream(this.indexFilePath);
		if (FileExtensionUtils.isCompressedDictionaryIndexFile(this.indexFilePath)) {
			// read zip compressed index file (.idx.gz)
			in = new ZipInputStream(in);
		}
		final DictionaryIndexReader reader = new InputStreamDictionaryIndexReader(in);
		final ImmutableDictionaryIndex index = reader.read(this.info);
		in.close();
		return index;
	}
}
