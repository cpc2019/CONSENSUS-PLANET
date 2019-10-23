package com.pingan.baselibs.utils;

/**
 * author :tony
 * date:2019/5/10 15:19
 * describe:防止按钮被多次点击
 */
public class OnClickDoubleUtils {
	private static final int MIN_DELAY_TIME = 500;//限制一秒内只能点击一次
	private static long lastClickTime;//最新更新时间

	public static boolean isFastClick() {
		boolean flag = true;
		long currentClickTime = System.currentTimeMillis();
		if (currentClickTime - lastClickTime <= MIN_DELAY_TIME) {
			flag = false;
		}
		lastClickTime = currentClickTime;
		return flag;
	}
}
