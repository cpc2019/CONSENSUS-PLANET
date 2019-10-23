package com.pingan.baselibs.utils;

import com.pingan.baselibs.ApplicationManager;

/**
 * Created by yangwen881 on 17/2/27.
 */

public class ResourceUtils {

    public static String getString(int resId) {
        try {
            return ApplicationManager.getApplication().getString(resId);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(int resId, Object... formatArgs) {
        try {
            return ApplicationManager.getApplication().getString(resId, formatArgs);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInteger(int resId) {
        try {
            return ApplicationManager.getContext().getResources().getInteger(resId);
        } catch (Exception e) {
            return 0;
        }
    }
}