package com.pingan.baselibs.pref;

import com.pingan.baselibs.ApplicationManager;
import com.pingan.baselibs.utils.AbstractPreferences;

/**
 * Pref缓存
 */
public class ExchangePreference extends AbstractPreferences {

	private static final String KEY_LANGUAGE_ID = "language";
	private static final String KEY_CACULATE_WAY_ID = "caculate_way";
	private static final String KEY_COUNTRY_NAME = "key_country_name";
	private static final String KEY_COUNTRY_SYSTEM = "key_country_system";
	private static final String KEY_FIRST_BOOT = "first_boot";
	private static final String KEY_VOD_GIVING_NOTICE = "vod_giving_notice";
	public static final String KEY_LZ_GIFT_GIVING = "key_lz_gift_giving";
	private static final String KEY_IS_SEE_LIVE = "key_is_see_live";
	private static final String KEY_LOCAL_TIME = "key_local_time";
	private static final String KEY_TASK_CODE = "key_task_code";
	private static final String KEY_PLAY_APP_TASK_RECODE_ID = "key_play_app_task_recode_id";
	private static final String KEY_SPLASH_AD_SHOW_NUM = "key_splash_ad_show_num";
	private static final String KEY_SPLASH_AD_SHOW_TIME = "key_splash_ad_show_time";

	private static final ExchangePreference INSTANCE = new ExchangePreference();

	public static ExchangePreference getInstance() {
		return INSTANCE;
	}

	private ExchangePreference() {
		super(ApplicationManager.getContext(), null);
	}

	public void setLanguageId(int value) {
		putInt(KEY_LANGUAGE_ID, value);
	}

	public int getLanguageId() {
		return getLanguageId(0);
	}

	public int getLanguageId(int defaultValue) {
		return getInt(KEY_LANGUAGE_ID, defaultValue);
	}

	public void setCaculateWayId(int value) {
		putInt(KEY_CACULATE_WAY_ID, value);
	}

	public int getCaculateWayId() {
		return getCaculateWayId(0);
	}

	public int getCaculateWayId(int defaultValue) {
		return getInt(KEY_CACULATE_WAY_ID, defaultValue);
	}

	public void setCountryName(String countryName) {
		putString(KEY_COUNTRY_NAME, countryName);
	}

	public String getCountryName() {
		return getString(KEY_COUNTRY_NAME, "");
	}

	public void setSystemCountry(String countryName) {
		putString(KEY_COUNTRY_SYSTEM, countryName);
	}

	public String getSystemCountry() {
		return getString(KEY_COUNTRY_SYSTEM, "");
	}

	public void setStarState(int state) {
		putInt(KEY_FIRST_BOOT, state);
	}

	public int getStarState() {
		return getInt(KEY_FIRST_BOOT, 0);
	}

	public void setGiftGivingState(int state) {
		putInt(KEY_VOD_GIVING_NOTICE, state);
	}

	public int getGiftGivingState() {
		return getInt(KEY_VOD_GIVING_NOTICE, 0);
	}

	public void setLzGiftGivingState(int state) {
		putInt(KEY_LZ_GIFT_GIVING, state);
	}

	public int getLzGiftGivingState() {
		return getInt(KEY_LZ_GIFT_GIVING, 0);
	}

	public void setLocalTime(String localTime) {
		putString(KEY_LOCAL_TIME, localTime);
	}

	public String getLocalTime() {
		return getString(KEY_LOCAL_TIME, "");
	}

	/** 进入直播间传true 退出直播间传false */
	public void setSeeLiveState(boolean isSeeLive) {
		putBoolean(KEY_IS_SEE_LIVE, isSeeLive);
	}

	public boolean getSeeLiveState() {
		return getBoolean(KEY_IS_SEE_LIVE, false);
	}

	/** 记录当前领取的任务类型 */
	public void setTaskCode(String taskCode) {
		putString(KEY_TASK_CODE, taskCode);
	}

	public String getTaskCode() {
		return getString(KEY_TASK_CODE, "");
	}

	/** 玩应用id */
	public void setPlayAppTaskRecodeId(String recodeId) {
		putString(KEY_PLAY_APP_TASK_RECODE_ID, recodeId);
	}

	public String getPlayAppTaskRecodeId() {
		return getString(KEY_PLAY_APP_TASK_RECODE_ID, "");
	}

	/** 记录已经显示的次数 */
	public void setSplashAdHaveShowNum(int showNum) {
		putInt(KEY_SPLASH_AD_SHOW_NUM, showNum);
	}

	public int getSplashAdHaveShowNum() {
		return getInt(KEY_SPLASH_AD_SHOW_NUM, 0);
	}

	/** 记录已经显示时间 */
	public void setSplashAdHaveShowTime(long time) {
		putLong(KEY_SPLASH_AD_SHOW_TIME, time);
	}

	public long getSplashAdHaveShowTime() {
		return getLong(KEY_SPLASH_AD_SHOW_TIME, 0);
	}
}
