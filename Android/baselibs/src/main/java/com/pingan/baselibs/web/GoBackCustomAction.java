package com.pingan.baselibs.web;

import android.webkit.WebView;
import java.io.Serializable;

public interface GoBackCustomAction extends Serializable {

    void onGoBack(WebView webView);
}