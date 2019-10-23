package com.pingan.baselibs.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtils {
	/**
	 * 设置状态栏黑色字体图标，
	 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 *
	 * @return 1:MIUUI 2:Flyme 3:android6.0
	 */
	public static int setStatusBarBlackFontMode(Activity activity) {
		if (!AndroidVersionUtils.hasLollipop()) {
			return 0;
		}

		int result = 0;
		activity.getWindow()
			.getDecorView()
			.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		if (MIUISetStatusBarLightMode(activity, true)) {
			result = 1;
		} else if (FlymeSetStatusBarLightMode(activity, true)) {
			result = 2;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//         activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			result = 3;
		}
		return result;
	}

	public static void setStatusBarWhiteFontMode(Activity activity) {
		activity.getWindow()
			.getDecorView()
			.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
	}

	/**
	 * 设置状态栏字体图标为深色，需要MIUIV6以上
	 *
	 * @param activity 需要设置的窗口
	 * @param dark 是否把状态栏字体及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 */
	private static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
		boolean result = false;
		Window window = activity.getWindow();
		if (window != null) {
			Class clazz = window.getClass();
			try {
				int darkModeFlag = 0;
				Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
				Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
				darkModeFlag = field.getInt(layoutParams);
				Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
				if (dark) {
					extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
				} else {
					extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
				}
				result = true;
			} catch (Exception e) {

			}
		}
		return result;
	}

	/**
	 * 设置状态栏图标为深色和魅族特定的文字风格
	 * 可以用来判断是否为Flyme用户
	 *
	 * @param activity 需要设置的窗口
	 * @param dark 是否把状态栏字体及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 */
	private static boolean FlymeSetStatusBarLightMode(Activity activity, boolean dark) {
		boolean result = false;
		Window window = activity.getWindow();
		if (window != null) {
			try {
				WindowManager.LayoutParams lp = window.getAttributes();
				Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField(
					"MEIZU_FLAG_DARK_STATUS_BAR_ICON");
				Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
				darkFlag.setAccessible(true);
				meizuFlags.setAccessible(true);
				int bit = darkFlag.getInt(null);
				int value = meizuFlags.getInt(lp);
				if (dark) {
					value |= bit;
				} else {
					value &= ~bit;
				}
				meizuFlags.setInt(lp, value);
				window.setAttributes(lp);
				StatuBarUtilsForFlyMe.setStatusBarDarkIcon(activity, dark);
				result = true;
			} catch (Exception e) {
			}
		}
		return result;
	}
}
