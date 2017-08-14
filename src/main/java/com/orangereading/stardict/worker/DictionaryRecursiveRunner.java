package com.orangereading.stardict.worker;

import java.io.File;

import com.orangereading.stardict.io.CommonFileDictionaryReader;
import com.orangereading.stardict.io.DictionaryReader;
import com.orangereading.stardict.util.FileExtensionUtils;

public class DictionaryRecursiveRunner {

	private final Worker worker;

	public DictionaryRecursiveRunner(final Worker worker) {
		this.worker = worker;
	}

	public void run(final File path) {
		if (!path.isDirectory()) {
			throw new RuntimeException("Not directory");
		}
		for (File f : path.listFiles()) {
			if (f.isFile() && FileExtensionUtils.isDictionaryInfoFile(f.getName())) {
				try {
					final String name = f.getName().substring(0, f.getName().lastIndexOf('.'));
					final DictionaryReader reader = new CommonFileDictionaryReader(path.getAbsolutePath(), name);
					System.out.println(f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + " ...");
					this.worker.run(name, reader);
				} catch (Throwable t) {
					System.out.println(String.format("\tError: %s", t.getMessage()));
				}
			} else if (f.isDirectory()) {
				this.run(f);
			}
		}
	}

}
