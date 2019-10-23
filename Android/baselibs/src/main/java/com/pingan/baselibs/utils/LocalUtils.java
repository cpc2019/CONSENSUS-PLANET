package com.pingan.baselibs.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.pingan.baselibs.ApplicationManager;
import com.pingan.baselibs.SettingsDataProvider;
import com.pingan.baselibs.pref.ExchangePreference;

import java.util.Locale;

public class LocalUtils {

	public static void switchLanguage(Context context, int language) {
		//Context使用Application的，否则在8.0以上机型无效。
		Resources resources = context.getApplicationContext().getResources();
		Configuration config = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		switch (language) {
			case SettingsDataProvider.LanguageId.SIMPLIFIED_CHINESE:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					config.setLocale(Locale.CHINA);
				} else {
					config.locale = Locale.CHINA;
				}
				resources.updateConfiguration(config, dm);
				break;
			case SettingsDataProvider.LanguageId.TRADITIONAL_CHINESE:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					config.setLocale(new Locale("zh", "HK"));
				} else {
					config.locale = new Locale("zh", "HK");
				}
				resources.updateConfiguration(config, dm);
				break;
			case SettingsDataProvider.LanguageId.ENGLISH:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					config.setLocale(Locale.ENGLISH);
				} else {
					config.locale = Locale.ENGLISH;
				}
				resources.updateConfiguration(config, dm);
				break;
			default:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
					config.setLocale(Locale.CHINA);
				} else {
					config.locale = Locale.CHINA;
				}
				resources.updateConfiguration(config, dm);
				break;
		}
	}

	/**
	 * @return
	 */
	public static Locale getCurrentLocale() {
		//        int currentLanguage = ExchangePreference.getInstance()
		//                .getLanguageId(SettingsDataProvider.LanguageId.SIMPLIFIED_CHINESE);
		//
		//        switch (currentLanguage) {
		//            case SettingsDataProvider.LanguageId.SIMPLIFIED_CHINESE:
		//                return Locale.CHINA;
		//            case SettingsDataProvider.LanguageId.TRADITIONAL_CHINESE:
		//                return new Locale("zh", "HK");
		//            case SettingsDataProvider.LanguageId.ENGLISH:
		//                return Locale.US;
		//            default:
		//                return Locale.CHINA;
		//        }
		return getSettingLanguage();
	}

	/**
	 * 原版本是判断是否是中文
	 * <p>
	 * 1.5.5版本改成判断是否是中国，只是根据手机系统语言区别国家
	 */
	public static boolean isChineseVersion() {
		String countryName = ExchangePreference.getInstance().getCountryName();
		return isChina(countryName);
	}

	/**
	 * 是否是设置了中文
	 */
	public static boolean isChineseLanguage() {
		return getSettingLanguage() == Locale.CHINA;
	}

	/**
	 * 判断是否是设置的语言，App显示语言跟当前手机国家没关系，语言知识手机文案显示而已
	 * 如果没有设置，就显示当前手机的国家语言
	 */
	public static Locale getSettingLanguage() {
		int currentLanguage = ExchangePreference.getInstance().getLanguageId(-1);
		switch (currentLanguage) {
			case -1:
				return getSystemLanguage();
			case SettingsDataProvider.LanguageId.SIMPLIFIED_CHINESE:
				return Locale.CHINA;
			case SettingsDataProvider.LanguageId.TRADITIONAL_CHINESE:
				return Locale.US;
			case SettingsDataProvider.LanguageId.ENGLISH:
				return Locale.US;
			default:
				return getSystemLanguage();
		}
	}

	/**
	 * 判断当前系统语言是否是中文简体 如果是就是中国，否则就是其他国家
	 */
	public static boolean isChina(String countryName) {
		if (TextUtils.isEmpty(countryName)) {//如果没有登录过就用系统的为准
			countryName = ExchangePreference.getInstance().getSystemCountry();
		}
		return Locale.CHINA.getCountry().equals(countryName);
	}

	/**
	 * App首次启动的时候用于判断手机系统语言
	 */
	public static Locale getSystemLanguage() {
		String countryName = ExchangePreference.getInstance().getSystemCountry();
		return isChina(countryName) ? Locale.CHINA : Locale.US;
	}

	public static boolean isSysChinese(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		return locale.equals(Locale.SIMPLIFIED_CHINESE);
	}

	public static String getLocalStr(Context context) {
		//        return getLanguage() + "_" + getCountry();
		StringBuilder builder = new StringBuilder();
		if (isChineseLanguage()) {
			builder.append(Locale.CHINA.getLanguage())
				.append("_")
				.append(Locale.CHINA.getCountry());
		} else {
			builder.append(Locale.US.getLanguage()).append("_").append(Locale.US.getCountry());
		}
		return builder.toString();
	}

	public static String getLanguage() {
		return getCurrentLocale().getLanguage();
	}

	public static String getCountry() {
		return getCurrentLocale().getCountry();
	}
}
