package com.orangereading.stardict.exporter;

import java.io.IOException;

import com.orangereading.stardict.annotation.Exporter;
import com.orangereading.stardict.cli.CommandExport;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

@Exporter("cli")
public class ConsoleExporter implements DictionaryExporter {

	@Override
	public void init(final ImmutableDictionaryInfo info, final String name, final CommandExport command) {
		System.out.println(String.format("Init dictionary: %s, name: %s, extra-args: %s", info.getBookname(), name,
				command.getExtraArgs()));
	}

	@Override
	public void append(final DictionaryItem item) {
		System.out.println("Append: " + item.getIndex().getWord());
	}

	@Override
	public void done() throws IOException {
		System.out.println("Done!");
	}

}
