package com.huantansheng.easyphotos.utils.String;

import android.text.TextUtils;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类
 * Created by huan on 2017/10/20.
 */
public class StringUtils {
	public static String getLastPathSegment(String content) {
		if (content == null || content.length() == 0) {
			return "";
		}
		String[] segments = content.split("/");
		if (segments.length > 0) {
			return segments[segments.length - 1];
		}
		return "";
	}

	/**
	 * 根据用户名的不同长度，来进行替换 ，达到保密效果
	 *
	 * @param userName 用户名
	 * @return 替换后的用户名
	 */
	public static String userNameReplaceWithStar(String userName) {
		String userNameAfterReplaced = "";

		if (userName == null) {
			userName = "";
		}

		int nameLength = userName.length();

		if (nameLength <= 1) {
			userNameAfterReplaced = "*";
		} else if (nameLength == 2) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{0})\\d(?=\\d{1})");
		} else if (nameLength >= 3 && nameLength <= 6) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{1})");
		} else if (nameLength == 7) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{1})\\d(?=\\d{2})");
		} else if (nameLength == 8) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{2})");
		} else if (nameLength == 9) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{2})\\d(?=\\d{3})");
		} else if (nameLength == 10) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{3})");
		} else if (nameLength >= 11) {
			userNameAfterReplaced = replaceAction(userName, "(?<=\\d{3})\\d(?=\\d{4})");
		}

		return userNameAfterReplaced;
	}

	/**
	 * 实际替换动作
	 *
	 * @param username username
	 * @param regular 正则
	 */
	private static String replaceAction(String username, String regular) {
		return username.replaceAll(regular, "*");
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	 * 此方法中前三位格式有：
	 * 13+任意数
	 * 145,147,149
	 * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
	 * 166
	 * 17+3,5,6,7,8
	 * 18+任意数
	 * 198,199
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		// ^ 匹配输入字符串开始的位置
		// \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
		// $ 匹配输入字符串结尾的位置
		//String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])"
		//	+ "|(18[0-9])|(19[8,9]))\\d{8}$";
		//Pattern p = Pattern.compile(regExp);
		//Matcher m = p.matcher(str);
		//return m.matches();
		return str.length() == 11;
	}

	/**
	 * 保留两个小数点
	 */
	public static String onDecimalFormat(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(value);
	}
}
