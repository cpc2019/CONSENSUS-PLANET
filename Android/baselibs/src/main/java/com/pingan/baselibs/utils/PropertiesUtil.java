package com.pingan.baselibs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PropertiesUtil {

    private static final String fileName = "txprop";

    private SharedPreferences sp;

    private Editor editor;

    private Context context;

    public Context getContext() {
        return context;
    }

    public void init(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    private static PropertiesUtil INSTANCE = null;

    public static PropertiesUtil getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new PropertiesUtil();
        }
        return INSTANCE;
    }

    private PropertiesUtil() {
    }

    public void remove(SpKey e) {
        editor.remove(e.getText());
        editor.commit();
    }

    public void setString(SpKey e, String value) {
        editor.putString(e.getText(), value);
        editor.commit();
    }

    public String getString(SpKey e, String defValue) {
        return sp.getString(e.getText(), defValue);
    }

    public void setString(String e, String value) {
        editor.putString(e, value);
        editor.commit();
    }

    public String getString(String e, String defValue) {
        return sp.getString(e, defValue);
    }

    public void setInt(SpKey e, int value) {
        editor.putInt(e.getText(), value);
        editor.commit();
    }

    public int getInt(SpKey e, int defValue) {
        return sp.getInt(e.getText(), defValue);
    }

    public void setLong(SpKey e, long value) {
        editor.putLong(e.getText(), value);
        editor.commit();
    }

    public long getLong(SpKey e, long defValue) {
        return sp.getLong(e.getText(), defValue);
    }

    public void setLong(String e, long value) {
        editor.putLong(e, value);
        editor.commit();
    }

    public long getLong(String e, long defValue) {
        return sp.getLong(e, defValue);
    }

    public void setBoolean(SpKey e, boolean value) {
        editor.putBoolean(e.getText(), value);
        editor.commit();
    }

    public boolean getBoolean(SpKey e, boolean defValue) {
        return sp.getBoolean(e.getText(), defValue);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static enum SpKey {

        LIMITED("LIMITED");

        public String text;

        private SpKey(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }
}