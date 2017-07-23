package com.orangereading.stardict;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.io.DictionaryInfoReader;
import com.orangereading.stardict.io.StreamDictionaryInfoReader;

public class DictionaryImpl implements Dictionary {

	private final String path;

	private final String name;

	private final ImmutableDictionaryInfo info;

	DictionaryImpl(final String path, final String name) {
		this.path = path;
		this.name = name;

		// Read dictionary info file
		final Path infoFilePath = Paths.get(path, name + "ifo");
		try (final Stream<String> lines = Files.lines(infoFilePath)) {
			final DictionaryInfoReader reader = new StreamDictionaryInfoReader(lines);
			this.info = reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ImmutableDictionaryInfo getInfo() {
		return this.info;
	}

	@Override
	public void eachWord(final Consumer<DictionaryItem> consumer) {
		// TODO Auto-generated method stub

	}

}
