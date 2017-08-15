package com.orangereading.stardict.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "export dictionary to other format")
public class CommandExport {

	@Parameter(description = "[directory contains stardict info files]")
	private String file;

	@Parameter(names = { "-o", "--output" }, description = "output folder")
	private String output;

	@Parameter(names = { "-f", "--format" }, description = "target format, default is xml")
	private String format = "xml";

	@Parameter(names = { "-x", "--extra-args" }, description = "extra arguments passed to the converter")
	private String args;

	public String getFile() {
		return this.file;
	}

	public String getOutput() {
		return output;
	}

	public String getFormat() {
		return format;
	}

	public String getArgs() {
		return args;
	}

}
