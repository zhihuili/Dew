package com.intel.sto.bigdata.dew.utils;

public class Util {
	// Because posting http stream use header to transfer parameter, add a
	// prefix to differ from http protocol.
	private static String PREFIX = "dew-";

	public static String addPrefix(String s) {
		return PREFIX + s;
	}

	public static String removePrefix(String s) {
		if (s.startsWith(PREFIX)) {
			return s.substring(4);
		}
		return null;
	}
}
