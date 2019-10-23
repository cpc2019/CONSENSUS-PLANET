package com.pingan.baselibs.utils;

import android.text.TextUtils;
import com.pingan.baselibs.ApplicationManager;
import com.pingan.baselibs.R;
import com.raizlabs.android.dbflow.list.IFlowCursorIterator;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {
	public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
	public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN;
	public static final int ROUND_UP = BigDecimal.ROUND_UP;

	/**
	 * double 转 int,四舍五入
	 */
	public static int getIntNumber(double number) {
		return Integer.parseInt(new DecimalFormat("0").format(number));
	}

	/**
	 * 科学计数字符串转普通计数字符串
	 */
	public static String getStringNumber(String number) {
		if (TextUtils.isEmpty(number)) {
			return "0";
		}
		BigDecimal bd = new BigDecimal(number);
		String temp = bd.toPlainString();
		if (bd.toPlainString().endsWith(".")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		if (bd.toPlainString().endsWith(".0")) {
			temp = temp.substring(0, temp.length() - 2);
		}
		return temp;
	}

	public static String getStringNumber(double number) {
		String temp = String.valueOf(number);
		BigDecimal bd = new BigDecimal(temp);
		temp = bd.toPlainString();
		if (bd.toPlainString().endsWith(".")) {
			temp = temp.substring(0, temp.length() - 1);
		}
		if (bd.toPlainString().endsWith(".0")) {
			temp = temp.substring(0, temp.length() - 2);
		}
		return temp;
	}

	/**
	 * 格式化小数(字符串）
	 *
	 * @param decimalStr 数字字符串
	 * @param n 保留多少位小数（不足补零，超出去尾）
	 * @param roundMode 保留最后一位小数的方案，使用BigDecimal中的ROUND开头的常量
	 */
	public static String formatDecimal(String decimalStr, int n, int roundMode) {
		if (decimalStr == null) {
			return "0";
		}
		//清理空格
		decimalStr = decimalStr.trim();

		if (decimalStr.length() == 0) {
			return "0";
		}

		if (n <= 0) {
			return decimalStr;
		}

		if (!decimalStr.contains(".")) {
			decimalStr = decimalStr + ".";
		}

		try {
			BigDecimal bd = new BigDecimal(decimalStr);
			return bd.setScale(n, roundMode).toString();
		} catch (NumberFormatException e) {
			return decimalStr;
		}
	}

	public static String formatDecimalDown(String decimalStr, int n) {
		return formatDecimal(decimalStr, n, BigDecimal.ROUND_DOWN);
	}

	public static String formatDecimalHalfUp(String decimalStr, int n) {
		return formatDecimal(decimalStr, n, BigDecimal.ROUND_HALF_UP);
	}

	public static String formatDecimalUp(String decimalStr, int n) {
		return formatDecimal(decimalStr, n, BigDecimal.ROUND_UP);
	}

	public static String formatDecimalDown(double decimalStr, int n) {
		return formatDecimal(String.valueOf(decimalStr), n, BigDecimal.ROUND_DOWN);
	}

	public static String formatDecimalHalfUp(double decimalStr, int n) {
		return formatDecimal(String.valueOf(decimalStr), n, BigDecimal.ROUND_HALF_UP);
	}

	public static String formatDecimalUp(double decimalStr, int n) {
		return formatDecimal(String.valueOf(decimalStr), n, BigDecimal.ROUND_UP);
	}

	/**
	 * 保留n位小数
	 */
	public static String formatDouble(double d, int n, int mode) {
		if (!String.valueOf(d).contains(".")) {
			return getStringNumber(d);
		}
		BigDecimal bd1 = new BigDecimal(getStringNumber(d));
		BigDecimal bd2 = bd1.setScale(n, mode);
		return getStringNumber(bd2.doubleValue());
	}

	/**
	 * 保留n位小数
	 */
	public static String formatDouble(String d, int n, int mode) {
		if (!d.contains(".")) {
			return getStringNumber(d);
		}
		BigDecimal bd1 = new BigDecimal(d);
		BigDecimal bd2 = bd1.setScale(n, mode);
		return getStringNumber(bd2.doubleValue());
	}

	/**
	 * 保留N位小数，舍弃尾数
	 */
	public static String formatDoubleDrop(double d, int n) {
		if (!String.valueOf(d).contains(".")) {
			return getStringNumber(d);
		}
		BigDecimal bd1 = new BigDecimal(getStringNumber(d));
		BigDecimal bd2 = bd1.setScale(n, BigDecimal.ROUND_DOWN);
		return getStringNumber(bd2.doubleValue());
	}

	public static double formatDoubleDropExt(double d, int n) {
		if (!String.valueOf(d).contains(".")) {
			return d;
		}
		BigDecimal bd1 = new BigDecimal(getStringNumber(d));
		BigDecimal bd2 = bd1.setScale(n, BigDecimal.ROUND_DOWN);
		return bd2.doubleValue();
	}

	/**
	 * 保留N位小数，前一位+1
	 */
	public static String formatDoubleAdd(double d, int n) {
		if (!String.valueOf(d).contains(".")) {
			return getStringNumber(d);
		}
		BigDecimal bd1 = new BigDecimal(getStringNumber(d));
		BigDecimal bd2 = bd1.setScale(n, BigDecimal.ROUND_UP);
		return getStringNumber(bd2.doubleValue());
	}

	/**
	 * 判断数字是否包含N位小数
	 */
	public static boolean isContainNDecimal(String number, int dot) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		if (number.contains(".") && number.length() - number.indexOf(".") == dot + 1) {
			return true;
		}
		return false;
	}

	/**
	 * 是否超过N位小数
	 */
	public static boolean isBeyondNDecimal(String number, int dot) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		if (number.contains(".") && number.length() - number.indexOf(".") > dot + 1) {
			return true;
		}
		return false;
	}

	/**
	 * 是否少于N位小数
	 */
	public static boolean isSmallNDecimal(String number, int dot) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		if (number.contains(".") && number.length() - number.indexOf(".") < dot + 1) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param value 如果value与其整形值相等，则返回整形字符串，否则返回本身字符串
	 * @return
	 */
	public static String double2StringMayInt(double value){
		int valueInt = (int)value;
		if(value - valueInt > 0)
		{
			return value+"";
		}else{
		    return valueInt+"";
		}
	}

	/**
	 * 判断数字是否包含N位以上整数数
	 */
	public static boolean isContainNInteger(String number, int maxLength) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		StringBuilder maxNumberStr = new StringBuilder();
		for (int i = 1; i <= maxLength; i++) {
			maxNumberStr.append("9");
		}
		if (TextUtils.isEmpty(maxNumberStr)) {
			maxNumberStr.append("0");
		}
		long maxNumber = new Double(maxNumberStr.toString()).longValue();
		if (new Double(number).longValue() > maxNumber) {
			return true;
		}
		return false;
	}

	public static String formatNumWithUnit(double num) {
		return formatNumWithUnit(formatDouble(num, 2, BigDecimal.ROUND_HALF_UP));
	}

	public static String formatNumWithUnit(String num) {
		String formatNumStr = "";
		String unit = "";
		try {

			StringBuffer sb = new StringBuffer();
			BigDecimal b1 = new BigDecimal("10000");
			BigDecimal b2 = new BigDecimal("100000000");
			BigDecimal b3 = new BigDecimal(num);
			// 以万为单位处理
			if (b3.compareTo(b1) == -1) {
				formatNumStr = b3.toString();
				if (!"".equals(formatNumStr)) {
					int i = formatNumStr.indexOf(".");
					if (i == -1) {
						formatNumStr = sb.append(formatNumStr).append(unit).toString();
					} else {
						if (formatNumStr.substring(formatNumStr.length() - 1, formatNumStr.length())
							.equals("0")) {
							formatNumStr = formatNumStr.substring(0, formatNumStr.length() - 1);
							if (formatNumStr.substring(formatNumStr.length() - 1,
								formatNumStr.length()).equals("0")) {
								formatNumStr = formatNumStr.substring(0, formatNumStr.length() - 1);
								if (formatNumStr.substring(formatNumStr.length() - 1,
									formatNumStr.length()).equals(".")) {
									formatNumStr =
										formatNumStr.substring(0, formatNumStr.length() - 1);
								}
							}
						}
					}
					if (formatNumStr.endsWith(".")) {
						formatNumStr = formatNumStr.substring(0, formatNumStr.length() - 1);
					}
				}
			} else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1) || b3.compareTo(b2) == -1) {
				unit = ApplicationManager.getContext().getString(R.string.wan);
				b3 = new BigDecimal(b3.toBigInteger());
				formatNumStr = b3.divide(b1).toString();
				if (formatNumStr.indexOf(".") != -1) {
					formatNumStr =
						formatDouble(Double.parseDouble(formatNumStr), 2, BigDecimal.ROUND_HALF_UP);
				}
			} else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
				unit = ApplicationManager.getContext().getString(R.string.yi);
				b3 = new BigDecimal(b3.toBigInteger());
				formatNumStr = b3.divide(b2).toString();
				if (formatNumStr.indexOf(".") != -1) {
					formatNumStr =
						formatDouble(Double.parseDouble(formatNumStr), 2, BigDecimal.ROUND_HALF_UP);
				}
			}
		} catch (Exception e) {
			formatNumStr = num;
		}
		return formatNumStr + unit;
	}

	/**
	 * 加法
	 */
	public static double add(double d1, double d2) {
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		return bd1.add(bd2).doubleValue();
	}

	public static double add(String d1, String d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * 减法
	 */
	public static double subtract(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.subtract(bd2).doubleValue();
	}

	/**
	 * 除法
	 */
	public static double divide(double d1, double d2, int scale, int mode) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.divide(bd2, scale, mode).doubleValue();
	}

	/**
	 * 乘法
	 */
	public static double multiply(double d1, double d2) {
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		return bd1.multiply(bd2).doubleValue();
	}

	/**
	 * 乘法
	 */
	public static double multiply(String d1, String d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.multiply(bd2).doubleValue();
	}

	public static String getNumWanStr(int num) {
		String result = String.valueOf(num);
		try {
			if (num >= 10000) {
				String temp = NumberUtils.formatDecimalHalfUp(((double) num) / 10000, 1);
				if (Double.parseDouble(temp) == 1) {
					result = "1w";
				} else {
					result = temp + "w";
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * DecimalFormat转换最简便
	 */
	public static String onDoubleToString(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		//System.out.println(df.format(d));
		return df.format(d);
	}

	public static String onDoubleToString2(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}

	/**
	 * 金额格式化，号分割
	 */
	public static String big(double d) {
		NumberFormat nf = NumberFormat.getInstance();
		// 是否以逗号隔开, 默认true以逗号隔开,如[123,456,789.128]
		nf.setGroupingUsed(true);
		// 结果未做任何处理
		return nf.format(d);
	}

	/**
	 * add by jadon
	 * 格式化数量 k m b
	 *
	 * @param num 要格式化的数
	 * @return 格式化后的数
	 */
	public static String formatNumber(long num) {
		final long B_BIT = 1000000000;
		final String B_UNIT = "B";

		final long M_BIT = 1000000;
		final String M_UNIT = "M";

		final long K_BIT = 1000;
		final String K_UNIT = "K";

		if (num / B_BIT > 0) {
			long b = num / B_BIT;
			long d = (num % B_BIT) / (B_BIT / 10);
			if (d > 0) {
				return b + "." + d + B_UNIT;
			} else {
				return b + B_UNIT;
			}
		}

		if (num / M_BIT > 0) {
			long b = num / M_BIT;
			long d = (num % M_BIT) / (M_BIT / 10);
			if (d > 0) {
				return b + "." + d + M_UNIT;
			} else {
				return b + M_UNIT;
			}
		}

		if (num / K_BIT > 0) {
			long b = num / K_BIT;
			long d = (num % K_BIT) / (K_BIT / 10);
			if (d > 0) {
				return b + "." + d + K_UNIT;
			} else {
				return b + K_UNIT;
			}
		}
		return num + "";
	}

	/** double 全部显示，不显示科学计算法， */
	public static String onDoubleToStringAll(double value) {
		DecimalFormat format = new DecimalFormat("0");
		return format.format(value);
	}
}
