package com.pingan.browser;

import android.content.Context;

/**
 * BrowserView工具类，用于处理业务
 * Created by duyuan797 on 16/9/5.
 */

public class BrowserViewHelper {

    public static final String TAG = "BrowserViewHelper";

    private ILoadingView mLoadingView;

    public BrowserViewHelper(ILoadingView loadingView) {
        this.mLoadingView = loadingView;
    }

    public void setLoadingView(ILoadingView loadingView) {
        this.mLoadingView = loadingView;
    }

    public void startCustomerService(final Context context, final int serviceId) {
        mLoadingView.showLoading();

    }

}