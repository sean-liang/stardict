package com.orangereading.stardict;

import com.orangereading.stardict.cli.AppCommander;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		final AppCommander commander = AppCommander.parse(args);
		if ("validate".equals(commander.getCommand())) {

		} else if ("convert".equals(commander.getCommand())) {

		} else {
			commander.printUsage();
		}
	}
}
