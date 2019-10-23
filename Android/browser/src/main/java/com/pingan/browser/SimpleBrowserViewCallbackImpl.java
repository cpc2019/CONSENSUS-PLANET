package com.pingan.browser;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;

/**
 * Created by duyuan797 on 16/9/5.
 */

public class SimpleBrowserViewCallbackImpl extends BrowserViewCallback {

    protected BrowserViewCallback mInternalCallBack;

    private BrowserViewHelper mBrowserHelper;

    public SimpleBrowserViewCallbackImpl() {
        mBrowserHelper = new BrowserViewHelper(this);
    }

    public void setBrowserViewCallBack(BrowserViewCallback browserViewCallBack) {
        this.mInternalCallBack = browserViewCallBack;
        mBrowserHelper.setLoadingView(browserViewCallBack);
    }

    public boolean onPageStarted() {
        return false;
    }

    public boolean onPageFinished() {
        return false;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("tel:")) {// 拨号
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }
        return false;
    }

    public boolean onReceivedError(WebView view, WebResourceRequest request,
            WebResourceError error) {
        return false;
    }

    public boolean onReceivedTitle(String title) {
        return false;
    }

    @Override public boolean onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        if (mInternalCallBack != null && mInternalCallBack.onShowCustomView(view, callback)) {
            return true;
        }
        return super.onShowCustomView(view, callback);
    }

    @Override public boolean onHideCustomView() {
        if (mInternalCallBack != null && mInternalCallBack.onHideCustomView()) {
            return true;
        }
        return super.onHideCustomView();
    }

    public boolean onProgressChanged(int progress) {
        return false;
    }

    public boolean stopLoadingWhileSslErrorOccurred() {
        return false;
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override public void showLoading() {
    }

    @Override public void dismissLoading() {
    }
}