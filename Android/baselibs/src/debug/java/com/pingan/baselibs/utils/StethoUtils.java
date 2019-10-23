package com.pingan.baselibs.utils;

import android.content.Context;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.Interceptor;

/**
 * Created by yelongfei490 on 2017/12/22.
 */

public class StethoUtils {

    public static void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    public static Interceptor getInterceptor() {
        return new StethoInterceptor();
    }

}
