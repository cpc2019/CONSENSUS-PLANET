package com.pingan.baselibs.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import com.pingan.baselibs.R;

/**
 */

public class DisplayUtils {

    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            display.getSize(screenResolution);
        } else {
            screenResolution.set(display.getWidth(), display.getHeight());
        }
        return screenResolution;
    }


    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    public static int calcOfProportionWidth(Context context, int realTotalWidth, int realWidth) {
        return realWidth * getScreenWidth(context) / realTotalWidth;
    }

    //public static int getStatusBarHeightInner(@NonNull Activity activity) {
    //    int statusBarHeight = 0;
    //    try {
    //        Class<?> c = Class.forName("com.android.internal.R$dimen");
    //        Object obj = c.newInstance();
    //        Field field = c.getField("status_bar_height");
    //        int x = Integer.parseInt(field.get(obj).toString());
    //        statusBarHeight = activity.getResources().getDimensionPixelSize(x);
    //    } catch (Exception e) {
    //        Rect rect = new Rect();
    //        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    //        statusBarHeight = rect.top;
    //    }
    //    return statusBarHeight;
    //}

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        // 使用默认24dp
        if (result == 0) {
            result = res.getDimensionPixelSize(R.dimen.common_status_bar_height);
        }
        return result;
    }

}