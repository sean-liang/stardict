package com.orangereading;

public interface TestUtils {

	public static byte[] hexStringToByteArray(final String s) {
		final String hex = s.replaceAll("[^A-Za-z0-9]", "");
		int len = hex.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}

}
