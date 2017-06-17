package com.orangereading.stardict.domain;

import java.io.Serializable;

public interface ImmutableDictionaryInfo extends Serializable {

	String getVersion();

	String getBookname();

	Integer getWordCount();

	Integer getSynWordCount();

	Long getIdxFileSize();

	Integer getIdxOffsetBits();

	String getAuthor();

	String getEmail();

	String getWebsite();

	String getDescription();

	String getDate();

	TypeIdentifier[] getSameTypeSequence();

}