package com.orangereading.stardict.cli;

import java.util.Collections;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "check whether the dictionary is supported")
public class CommandValidate {

	@Parameter(description = "[paths of stardict index files]")
	private List<String> files;

	public List<String> getFiles() {
		return Collections.unmodifiableList(this.files);
	}

}
