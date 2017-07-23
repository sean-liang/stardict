package com.orangereading.stardict.cli;

import java.util.Collections;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "convert dictionary to other format")
public class CommandConvert {

	@Parameter(description = "[paths of stardict index files]")
	private List<String> files;

	@Parameter(names = { "-o", "--output" }, description = "output folder")
	private String output;

	@Parameter(names = { "-f", "--format" }, description = "target format, xml or json")
	private String format;

	public List<String> getFiles() {
		return Collections.unmodifiableList(this.files);
	}

	public String getOutput() {
		return output;
	}

	public String getFormat() {
		return format;
	}

}
