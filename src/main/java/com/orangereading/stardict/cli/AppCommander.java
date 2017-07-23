package com.orangereading.stardict.cli;

import com.beust.jcommander.JCommander;

/**
 * 
 * Parse command-line arguments.
 * 
 * @author sean
 *
 */
public class AppCommander {

	// main command
	private final CommandMain main = new CommandMain();

	// validate command
	private final CommandValidate validate = new CommandValidate();

	// convert command
	private final CommandConvert convert = new CommandConvert();

	private final JCommander commander;

	private AppCommander() {
		this.commander = JCommander.newBuilder().addObject(main).addCommand("validate", validate)
				.addCommand("convert", convert).build();
	}

	public CommandMain getMain() {
		return main;
	}

	public CommandValidate getValidate() {
		return validate;
	}

	public CommandConvert getConvert() {
		return convert;
	}

	public String getCommand() {
		return this.commander.getParsedCommand();
	}

	public void printUsage() {
		this.commander.usage();
	}

	/**
	 * 
	 * Parse command-line arguments.
	 * 
	 * @param args
	 *            Command-line arguments
	 * 
	 * @return Parsed argument object
	 * 
	 */
	public static AppCommander parse(final String... args) {
		final AppCommander c = new AppCommander();
		c.commander.parse(args);
		if (c.getMain().isHelp()) {
			c.printUsage();
		}
		return c;
	}

}
