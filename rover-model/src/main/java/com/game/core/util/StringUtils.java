package com.game.core.util;

public class StringUtils {
	
	public static boolean hasLength(String str) {
		return (str!=null && !str.isEmpty());
	}
	
	public static boolean containsText(CharSequence str) {
		int strlen = str.length();
		for (int i=0; i< strlen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasText(String str) {
		return hasLength(str) && containsText(str);
	}

}
