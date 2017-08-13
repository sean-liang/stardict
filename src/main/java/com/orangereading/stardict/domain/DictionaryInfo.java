package com.orangereading.stardict.domain;

import java.util.Arrays;

/**
 * 
 * StarDict dictionary meta.
 * 
 * @author sean
 *
 */
public class DictionaryInfo implements ImmutableDictionaryInfo {

	private static final long serialVersionUID = 8843514996627435927L;

	private String version;

	private String bookname;

	// the count of word entries in .idx file, it must be right
	private Integer wordCount;

	// required if ".syn" file exists
	private Integer synWordCount;

	// the size(in bytes) of the .idx file, even the .idx is compressed to a
	// .idx.gz file, this entry must record the original .idx file's size, and
	// it must be right
	private Long idxFileSize;

	// can be 32 or 64, If "idxoffsetbits=64", the offset field of the .idx file
	// will be 64 bits.
	private Integer idxOffsetBits;

	private String author;

	private String email;

	private String website;

	private String description;

	private String date;

	// If the sametypesequence option is set, it tells StarDict that each word's
	// data in the .dict file will have the same sequence of datatypes. In this
	// case, we expect a .dict file that's been optimized in two ways: the type
	// identifiers should be omitted, and the size marker for the last data
	// entry of each word should be omitted.
	private TypeIdentifier[] sameTypeSequence;

	@Override
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	@Override
	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	@Override
	public Integer getSynWordCount() {
		return synWordCount;
	}

	public void setSynWordCount(Integer synWordCount) {
		this.synWordCount = synWordCount;
	}

	@Override
	public Long getIdxFileSize() {
		return idxFileSize;
	}

	public void setIdxFileSize(Long idxFileSize) {
		this.idxFileSize = idxFileSize;
	}

	@Override
	public Integer getIdxOffsetBits() {
		return idxOffsetBits;
	}

	public void setIdxOffsetBits(Integer idxOffsetBits) {
		this.idxOffsetBits = idxOffsetBits;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public TypeIdentifier[] getSameTypeSequence() {
		return sameTypeSequence;
	}

	public void setSameTypeSequence(TypeIdentifier[] sameTypeSequence) {
		this.sameTypeSequence = sameTypeSequence;
	}

	public boolean isSameTypeSequence() {
		return null != this.sameTypeSequence && this.sameTypeSequence.length > 0;
	}

	@Override
	public String toString() {
		return "DictionaryInfo [version=" + version + ", bookname=" + bookname + ", wordCount=" + wordCount
				+ ", synWordCount=" + synWordCount + ", idxFileSize=" + idxFileSize + ", idxOffsetBits=" + idxOffsetBits
				+ ", author=" + author + ", email=" + email + ", website=" + website + ", description=" + description
				+ ", date=" + date + ", sameTypeSequence=" + Arrays.toString(sameTypeSequence) + "]";
	}

}
