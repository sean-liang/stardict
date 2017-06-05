package com.orangereading;

import javax.xml.bind.DatatypeConverter;

public interface TestUtils {

	public static byte[] hexStringToByteArray(final String s) {
		final String hex = s.replaceAll("[^A-Za-z0-9]", "");
		return DatatypeConverter.parseHexBinary(hex);
	}

}
