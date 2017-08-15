package com.orangereading.stardict;

import java.io.File;

import com.orangereading.stardict.cli.AppCommander;
import com.orangereading.stardict.exporter.ExporterRegistry;
import com.orangereading.stardict.worker.DictionaryRecursiveRunner;
import com.orangereading.stardict.worker.ExportWorker;
import com.orangereading.stardict.worker.ValidateWorker;

public class App {

	public static void main(String[] args) {
		final AppCommander commander = AppCommander.parse(args);
		if ("validate".equals(commander.getCommand())) {
			final DictionaryRecursiveRunner runner = new DictionaryRecursiveRunner(new ValidateWorker());
			runner.run(new File(commander.getValidate().getFile()));
		} else if ("export".equals(commander.getCommand())) {
			ExporterRegistry.init();
			final DictionaryRecursiveRunner runner = new DictionaryRecursiveRunner(
					new ExportWorker(commander.getExport()));
			runner.run(new File(commander.getExport().getFile()));
		} else {
			commander.printUsage();
		}
	}
}
