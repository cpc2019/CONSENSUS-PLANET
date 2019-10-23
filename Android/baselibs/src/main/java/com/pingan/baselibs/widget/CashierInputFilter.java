package com.pingan.baselibs.widget;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author :tony
 * date:2019/5/23 10:30
 * describe:金额输入控制器
 */
public class CashierInputFilter implements InputFilter {

	private Pattern mPattern;

	/** 输入最大数 */
	private static final int MAX_VALUE = Integer.MAX_VALUE;
	/** 小数点后的位数 */
	private static final int POINTER_LENGTH = 2;
	private static final String POINTER = ".";
	private static final String ZERO = "0";

	public CashierInputFilter() {
		mPattern = Pattern.compile("([0-9]||\\.)*");
	}

	/**
	 * @param source 新输入的字符串
	 * @param start 新输入的字符串起始下标，一般都是0
	 * @param end 新输入的字符终点下标，一般是source的长度-1
	 * @param dest 输入之前的文本框内容
	 * @param dstart 原内容的起始坐标，一般为0
	 * @param dend 原内容终点坐标，一般为dest的长度-1
	 */
	@Override public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
		int dstart, int dend) {

		String sourceText = source.toString();
		String destText = dest.toString();

		//验证删除等按钮
		if (TextUtils.isEmpty(sourceText)) {
			return "";
		}
		Matcher matcher = mPattern.matcher(source);
		//只能输入小数点的情况下，只能输入数字
		if (destText.contains(POINTER)) {
			if (!matcher.matches()) {
				return "";
			} else {
				//只能输入一个小数点
				if (POINTER.equals(sourceText)) {
					return "";
				}
			}
			//验证小数点精度，保证小数点后只能输入两位
			int index = destText.indexOf(POINTER);
			int length = dend - index;
			if (length > POINTER_LENGTH) {
				return dest.subSequence(dstart, dend);
			}
		} else {
			//没有小数点的情况下只能输小数点和数字
			//1.首位不能输入小数点
			//如果首位为0，则后面只能数入小数点了
			if (!matcher.matches()) {
				return "";
			} else {
				if (POINTER.equals(sourceText) && TextUtils.isEmpty(destText)) {
					//首位不能输入小数点
					return "";
				} else if (!POINTER.equals(sourceText) && ZERO.equals(destText)) {
					//如果首位为0，后面只能输入小数点
					return "";
				}
			}
		}

		//验证输入金额的大小
		double sumText = Double.parseDouble(destText + sourceText);
		if (sumText > MAX_VALUE) {
			return dest.subSequence(dstart, dend);
		}
		return dest.subSequence(dstart, dend) + sourceText;
	}
}
