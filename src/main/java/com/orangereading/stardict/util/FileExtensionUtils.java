package com.orangereading.stardict.util;

public final class FileExtensionUtils {

	private FileExtensionUtils() {

	}

	/**
	 * 
	 * Check if the file name ends with .ifo
	 * 
	 * @param fname
	 *            File Name
	 *
	 */
	public static boolean isDictionaryInfoFile(final String fname) {
		return fname != null && fname.toLowerCase().endsWith(".ifo");
	}

	/**
	 * 
	 * Check if the file name ends with .idx or .idx.gz
	 * 
	 * @param fname
	 *            File Name
	 *
	 */
	public static boolean isDictionaryIndexFile(final String fname) {
		return fname != null && (fname.toLowerCase().endsWith(".idx") || fname.toLowerCase().endsWith(".idx.gz"));
	}

	/**
	 * 
	 * Check if the file name ends with .dict or .dict.dz
	 * 
	 * @param fname
	 *            File Name
	 *
	 */
	public static boolean isDictionaryDataFile(final String fname) {
		return fname != null && (fname.toLowerCase().endsWith(".dict") || fname.toLowerCase().endsWith(".dict.dz"));
	}

	/**
	 * 
	 * Check if the file name ends with .idx.gz
	 * 
	 * @param fname
	 *            File Name
	 *
	 */
	public static boolean isCompressedDictionaryIndexFile(final String fname) {
		return fname != null && fname.toLowerCase().endsWith(".idx.gz");
	}

	/**
	 * 
	 * Check if the file name ends with .dict.dz
	 * 
	 * @param fname
	 *            File Name
	 *
	 */
	public static boolean isCompressedDictionaryDataFile(final String fname) {
		return fname != null && fname.toLowerCase().endsWith(".dict.dz");
	}

}
