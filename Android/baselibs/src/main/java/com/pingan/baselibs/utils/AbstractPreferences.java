package com.pingan.baselibs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 */
public abstract class AbstractPreferences {

    private SharedPreferences mPreferences;

    public AbstractPreferences(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public void putFloat(String key, float value) {
        mPreferences.edit().putFloat(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public void putLong(String key, long value) {
        mPreferences.edit().putLong(key, value).apply();
    }

}
