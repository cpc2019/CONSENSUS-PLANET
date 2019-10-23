package com.pingan.browser;

import android.view.View;
import android.webkit.WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;

public class BrowserViewCallback implements ILoadingView {

	public boolean onPageStarted() {
		return false;
	}

	public boolean onPageFinished() {
		return false;
	}

	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		return false;
	}

	public boolean onReceivedError(WebView view, WebResourceRequest request, int errorCode) {
		return false;
	}

	public boolean onReceivedTitle(String title) {
		return false;
	}

	public boolean onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
		return false;
	}

	public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
	}

	public boolean onHideCustomView() {
		return false;
	}

	public View getVideoLoadingProgressView() {
		return null;
	}

	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
		JsPromptResult result) {
		return false;
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