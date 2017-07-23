package com.orangereading.stardict;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.orangereading.stardict.cli.AppCommander;

/**
 * Hello world!
 *
 */
public class App {

	private static final Logger log = LogManager.getLogger();

	public static void main(String[] args) {
		final AppCommander commander = AppCommander.parse(args);
		if ("validate".equals(commander.getCommand())) {

		} else if ("convert".equals(commander.getCommand())) {

		} else {
			commander.printUsage();
		}
	}
}
