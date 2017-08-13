package com.orangereading.stardict;

import java.io.File;

import com.orangereading.stardict.cli.AppCommander;
import com.orangereading.stardict.worker.DictionaryRecursiveRunner;
import com.orangereading.stardict.worker.ValidateWorker;

public class App {

	public static void main(String[] args) {
		final AppCommander commander = AppCommander.parse(args);
		if ("validate".equals(commander.getCommand())) {
			final DictionaryRecursiveRunner runner = new DictionaryRecursiveRunner(new ValidateWorker());
			runner.run(new File(commander.getValidate().getFile()));
		} else if ("convert".equals(commander.getCommand())) {

		} else {
			commander.printUsage();
		}
	}
}
