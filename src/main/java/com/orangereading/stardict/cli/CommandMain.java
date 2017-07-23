package com.orangereading.stardict.cli;

import com.beust.jcommander.Parameter;

public class CommandMain {
	
	@Parameter(names = { "-h", "--help" }, help = true)
	private boolean help;

	public boolean isHelp() {
		return help;
	}

}
