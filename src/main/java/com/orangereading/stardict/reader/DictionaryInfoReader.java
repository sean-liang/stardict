package com.orangereading.stardict.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.orangereading.stardict.model.DictionaryInfo;

/**
 * 
 * Read StarDict .ifo file.
 * 
 * @author sean
 *
 */
public class DictionaryInfoReader {

	private final static Pattern PROP_PATTERN = Pattern.compile("^([^=]+)=([^=]+)$");

	/**
	 * 
	 * Read StarDict .ifo file.
	 * 
	 * @param stream
	 *            .ifo file stream
	 * 
	 * @return meta data
	 * 
	 */
	public DictionaryInfo read(final Stream<String> stream) {
		final DictionaryInfo info = new DictionaryInfo();
		stream.forEach(line -> {
			final Matcher matcher = PROP_PATTERN.matcher(line);
			if (matcher.matches()) {
				final String key = matcher.group(1);
				final String val = matcher.group(2);
				switch (key) {
				case "version":
					info.setVersion(val);
					break;
				case "bookname":
					info.setBookname(val);
					break;
				case "wordcount":
					info.setWordCount(Integer.parseInt(val));
					break;
				case "synwordcount":
					info.setSynWordCount(Integer.parseInt(val));
					break;
				case "idxfilesize":
					info.setIdxFileSize(Long.parseLong(val));
					break;
				case "idxoffsetbits":
					info.setIdxOffsetBits(Integer.parseInt(val));
					break;
				case "author":
					info.setAuthor(val);
					break;
				case "email":
					info.setEmail(val);
					break;
				case "website":
					info.setWebsite(val);
					break;
				case "description":
					info.setDescription(val);
					break;
				case "date":
					info.setDate(val);
					break;
				case "sametypesequence":
					info.setSameTypeSequence(val);
					break;
				}
			}
		});

		return info;
	}

}
