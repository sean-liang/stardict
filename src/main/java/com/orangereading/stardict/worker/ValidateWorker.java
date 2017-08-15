package com.orangereading.stardict.worker;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.io.DictionaryReader;

/**
 * 
 * Validate worker.
 * 
 * @author sean
 *
 */
public class ValidateWorker implements Worker {

	@Override
	public void run(final String name, final DictionaryReader reader) throws IOException {
		final ImmutableDictionaryInfo info = reader.getInfo();
		final AtomicInteger counter = new AtomicInteger(0);
		reader.eachWord(item -> counter.incrementAndGet());
		if (counter.get() == info.getWordCount()) {
			System.out.println(
					String.format("\t%s %s: %d words checked!", info.getBookname(), info.getVersion(), counter.get()));
		} else {
			throw new RuntimeException(
					"Word count not match, expect " + info.getWordCount() + ", found " + counter.get());
		}
	}

}
