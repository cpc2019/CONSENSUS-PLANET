package com.pingan.baselibs;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 */

public class ApplicationManager {

    private static Application sApplication;

    private static Handler sMainHandler;

    public static void init(Application ctx) {
        ApplicationManager.sApplication = ctx;
    }

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return sApplication.getApplicationContext();
    }

    public static Handler getMainHandler() {
        if (sMainHandler != null) {
            return sMainHandler;
        }
        synchronized (ApplicationManager.class) {
            if (sMainHandler == null) {
                sMainHandler = new Handler();
            }
        }
        return sMainHandler;
    }

}
