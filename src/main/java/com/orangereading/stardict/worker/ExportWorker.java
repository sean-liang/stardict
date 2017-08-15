package com.orangereading.stardict.worker;

import java.io.IOException;

import com.orangereading.stardict.cli.CommandExport;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.exporter.DictionaryExporter;
import com.orangereading.stardict.exporter.ExporterRegistry;
import com.orangereading.stardict.io.DictionaryReader;

/**
 * 
 * Export dictionary to other format.
 * 
 * @author sean
 *
 */
public class ExportWorker implements Worker {

	private final CommandExport command;

	public ExportWorker(final CommandExport command) {
		this.command = command;
	}

	@Override
	public void run(final String name, final DictionaryReader reader) throws IOException {
		final ImmutableDictionaryInfo info = reader.getInfo();
		final Class<? extends DictionaryExporter> exporterClass = ExporterRegistry.getExporter(command.getFormat());
		if (null == exporterClass) {
			throw new RuntimeException("Illegal export format: " + command.getFormat());
		}
		try {
			final DictionaryExporter exporter = exporterClass.newInstance();
			exporter.init(info, name, command);
			reader.eachWord(item -> exporter.append(item));
			exporter.done();
			System.out.println(String.format("\t%s, %d words exported.", info.getBookname(), info.getWordCount()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
