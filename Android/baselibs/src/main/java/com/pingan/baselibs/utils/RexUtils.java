package com.pingan.baselibs.utils;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RexUtils {

	/**
	 * 是否是8-20位数字加字母
	 */
	public static boolean isContainNumberAndLetter(String content) {
		String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,20})$";
		boolean isMatch = Pattern.matches(pattern, content);
		return isMatch;
	}

	/**
	 * 是否包含emoji
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		boolean isEmoji = false;
		for (int i = 0; i < len; i++) {
			char hs = source.charAt(i);
			if (0xd800 <= hs && hs <= 0xdbff) {
				if (source.length() > 1) {
					char ls = source.charAt(i + 1);
					int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
					if (0x1d000 <= uc && uc <= 0x1f77f) {
						return true;
					}
				}
			} else {
				// non surrogate
				if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
					return true;
				} else if (0x2B05 <= hs && hs <= 0x2b07) {
					return true;
				} else if (0x2934 <= hs && hs <= 0x2935) {
					return true;
				} else if (0x3297 <= hs && hs <= 0x3299) {
					return true;
				} else if (hs == 0xa9
					|| hs == 0xae
					|| hs == 0x303d
					|| hs == 0x3030
					|| hs == 0x2b55
					|| hs == 0x2b1c
					|| hs == 0x2b1b
					|| hs == 0x2b50
					|| hs == 0x231a) {
					return true;
				}
				if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
					char ls = source.charAt(i + 1);
					if (ls == 0x20e3) {
						return true;
					}
				}
			}
		}
		return isEmoji;
	}

	/**
	 * 是否是合法名字
	 */
	public static boolean isLegalName(String name) {
		if (name.contains("·") || name.contains("•")) {
			if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (name.matches("^[\\u4e00-\\u9fa5]+$")) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 是否包含汉字
	 */
	public static boolean containsHZ(String content) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(content);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是邀请码(大概判断)
	 */
	public static boolean isInviteCode(String content) {
		if (content.length() == 6) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是邀请链接(大概判断)
	 */
	public static boolean isInviteUrl(String content) {
		if (!TextUtils.isEmpty(content)) {
			if (content.startsWith("http")) {
				return true;
			}
		}
		return false;
	}
}
