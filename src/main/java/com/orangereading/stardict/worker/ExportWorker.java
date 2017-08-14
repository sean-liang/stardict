package com.orangereading.stardict.worker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;

import com.orangereading.stardict.cli.CommandExport;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.exporter.DictionaryExporter;
import com.orangereading.stardict.exporter.JAXBXmlDictionaryExporter;
import com.orangereading.stardict.io.DictionaryReader;

public class ExportWorker implements Worker {

	private final CommandExport command;

	public ExportWorker(final CommandExport command) {
		this.command = command;
	}

	@Override
	public void run(final String name, final DictionaryReader reader) throws IOException {
		final ImmutableDictionaryInfo info = reader.getInfo();
		final AtomicReference<DictionaryExporter> exporter = new AtomicReference<>();
		File output = null;
		if ("xml".equalsIgnoreCase(command.getFormat())) {
			output = Paths.get(this.command.getOutput(), name + ".xml").toFile();
			exporter.set(new JAXBXmlDictionaryExporter(info, new FileOutputStream(output)));
		} else if ("json".equalsIgnoreCase(command.getFormat())) {
			// TODO
		} else {
			throw new RuntimeException("Illegal export format: " + command.getFormat());
		}
		reader.eachWord(item -> exporter.get().append(item));
		exporter.get().done();
		System.out.println(String.format("\t%s, %d words dump to %s", info.getBookname(), info.getWordCount(),
				output.getAbsolutePath()));
	}

}
