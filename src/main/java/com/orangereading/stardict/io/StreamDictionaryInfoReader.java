package com.orangereading.stardict.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.orangereading.stardict.domain.DictionaryInfo;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;
import com.orangereading.stardict.domain.TypeIdentifier;

/**
 * 
 * Read StarDict .ifo file from stream.
 * 
 * @author sean
 *
 */
public class StreamDictionaryInfoReader implements DictionaryInfoReader {

	private final static Pattern PROP_PATTERN = Pattern.compile("^([^=]+)=([^=]+)$");

	private final Stream<String> stream;

	public StreamDictionaryInfoReader(final Stream<String> stream) {
		this.stream = stream;
	}

	@Override
	public ImmutableDictionaryInfo read() {
		final DictionaryInfo info = new DictionaryInfo();
		this.stream.forEach(line -> {
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
					final int len = val.length();
					final TypeIdentifier[] types = new TypeIdentifier[len];
					for (int i = 0; i < len; i++) {
						types[i] = TypeIdentifier.valueOf(val.charAt(i));
					}
					info.setSameTypeSequence(types);
					break;
				}
			}
		});

		return info;
	}

}
