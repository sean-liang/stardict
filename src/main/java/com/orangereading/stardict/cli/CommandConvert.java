package com.orangereading.stardict.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "convert dictionary to other format")
public class CommandConvert {

	@Parameter(description = "[directory contains stardict info files]")
	private String file;

	@Parameter(names = { "-o", "--output" }, description = "output folder")
	private String output;

	@Parameter(names = { "-f", "--format" }, description = "target format, xml or json")
	private String format;

	public String getFile() {
		return this.file;
	}

	public String getOutput() {
		return output;
	}

	public String getFormat() {
		return format;
	}

}
