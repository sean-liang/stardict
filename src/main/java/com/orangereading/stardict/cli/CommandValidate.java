package com.orangereading.stardict.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "check whether the dictionary is supported")
public class CommandValidate {

	@Parameter(description = "[directory contains stardict info files]")
	private String file;

	public String getFile() {
		return this.file;
	}

}
