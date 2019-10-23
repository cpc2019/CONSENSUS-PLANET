package com.pingan.browser;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebHistoryItem;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装的Webview
 */

public class BrowserView extends WebView {

	public static final List<String> WHITE_HTTPS_HOST_FILTER = new ArrayList<String>() {{
		add("(.*)?qbox.me");
		add("(.*)?pingan.com.cn");
		add("(.*)?qq.com");
		add("202.69.28.21");
	}};

	/**
	 * web与native交互的时候，跟进该过滤列表隐藏title显示虚拟back按键
	 */
	public static final List<String> WEB_TITLE_HOST_FILTER = new ArrayList<String>() {{
		add("test-iicp-dmzstg.pingan.com.cn");
		add("iicp.pingan.com.cn");
		add("202.69.28.21");
	}};

	private String mUrl;
	private BrowserViewCallback callback;
	private Context context;
	private boolean enableHostCheck = false;

	public BrowserView(Context context) {
		this(context, null);
	}

	public BrowserView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public BrowserView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		init();
	}

	private void init() {
		super.setWebViewClient(webViewClient);
		super.setWebChromeClient(webChromeClient);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			removeJavascriptInterface("searchBoxJavaBridge_");
		}
		if (!isInEditMode()) {
			setHorizontalScrollBarEnabled(false);
			setVerticalScrollBarEnabled(false);
			getSettings().setUserAgentString(getSettings().getUserAgentString() + " 776_Android");
			getSettings().setJavaScriptEnabled(true);
			getSettings().setUseWideViewPort(true);
			getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
			getSettings().setLoadWithOverviewMode(true);
			getSettings().setAppCacheEnabled(true);
			getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
			getSettings().setAppCachePath("/data/data/" + getContext().getPackageName() + "/cache");
			getSettings().setDomStorageEnabled(true);
			setBackgroundColor(Color.WHITE);
		}
	}

	public static void addWhiteHttpsHostFiter(String... hosts) {
		if (hosts != null && hosts.length > 0) {
			for (String s : hosts) {
				WHITE_HTTPS_HOST_FILTER.add(s);
			}
		}
	}

	public void loadUrl(String url) {
		if (url != null/* && !url.equals(this.mUrl)*/) {
			this.mUrl = url;
			super.loadUrl(url);
		}
	}

	public void loadParamsUrl(String url) {
		if (url != null && !url.equals(this.mUrl)) {
			url = getInsecureUrl(url);
			super.loadUrl(url);
		}
	}

	private String getInsecureUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return url;
		}
		return url;
	}

	public void setCallback(BrowserViewCallback callback) {
		this.callback = callback;
	}

	public void setEnableHostCheck(boolean enableHostCheck) {
		this.enableHostCheck = enableHostCheck;
	}

	private WebViewClient webViewClient = new WebViewClient() {

		@Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (callback != null) {
				callback.onPageStarted();
			}
		}

		@Override public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
		}

		@Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (callback != null) {
				return callback.shouldOverrideUrlLoading(view, url);
			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (callback != null) {
				callback.onPageFinished();
			}
		}

		@Override public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			super.doUpdateVisitedHistory(view, url, isReload);
		}

		@Override public void onReceivedError(WebView view, WebResourceRequest request,
			WebResourceError error) {
			//            super.onReceivedError(view, request, error);
			int errorCode = error != null ? error.getErrorCode() : 0;

			if (callback != null) {
				callback.onReceivedError(view, request, errorCode);
			}
		}

		@Override public void onReceivedHttpError(WebView view, WebResourceRequest request,
			WebResourceResponse errorResponse) {
			super.onReceivedHttpError(view, request, errorResponse);
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			if (enableHostCheck) {
				if (view != null && !TextUtils.isEmpty(view.getUrl())) {
					String url = view.getUrl();
					Uri uri = Uri.parse(url);
					if (uri != null) {
						String host = uri.getHost();
						for (String filterHost : WHITE_HTTPS_HOST_FILTER) {
							if (host.matches(filterHost)) {
								handler.proceed();
								return;
							}
						}
					}
				}
				showSslErrorDialog(handler);
			} else {
				handler.proceed();
			}
		}
	};

	private WebChromeClient webChromeClient = new WebChromeClient() {
		@Override public void onProgressChanged(WebView view, int newProgress) {
			if (callback != null) {
				callback.onProgressChanged(newProgress);
			}
		}

		@Override public void onReceivedTitle(WebView view, String title) {
			if (callback != null) {
				callback.onReceivedTitle(title);
			}
		}

		@Override public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
			if (callback != null) {
				callback.onShowCustomView(view, customViewCallback);
			}
		}

		@Override public void onHideCustomView() {
			if (callback != null) {
				callback.onHideCustomView();
			}
		}

		@Override public View getVideoLoadingProgressView() {
			if (callback != null) {
				return callback.getVideoLoadingProgressView();
			}
			return super.getVideoLoadingProgressView();
		}

		@Override
		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
			JsPromptResult result) {
			if (callback != null) {
				return callback.onJsPrompt(view, url, message, defaultValue, result);
			}
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}
	};

	/**
	 * 写入数据到localstorage
	 * @param view
	 */
	private void setAppData(WebView view) {
		String token = BrowserManager.getInstance().token;
		Log.i("dd", "token: " + token);
		Log.i("dd", "time: " + BrowserManager.getInstance().time);
		Log.i("dd", "local: " + BrowserManager.getInstance().local);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			String tokenJs = "window.localStorage.setItem('token','" + token + "');";
			String timeJs =
				"window.localStorage.setItem('time','" + BrowserManager.getInstance().time + "');";
			String localJs = "window.localStorage.setItem('COIN_LANGUAGE','"
				+ BrowserManager.getInstance().local
				+ "');";
			view.evaluateJavascript(tokenJs, null);
			//view.evaluateJavascript(timeJs, null);
			view.evaluateJavascript(localJs, null);
		} else {
			String tokenJs =
				"javascript:(function({ var localStorage = window.localStorage; localStorage.setItem('token','"
					+ BrowserManager.getInstance().token
					+ "') })()";
			String timeJs =
				"javascript:(function({ var localStorage = window.localStorage; localStorage.setItem('time','"
					+ BrowserManager.getInstance().time
					+ "') })()";
			String localJs =
				"javascript:(function({ var localStorage = window.localStorage; localStorage.setItem('COIN_LANGUAGE','"
					+ BrowserManager.getInstance().local
					+ "') })()";
			view.loadUrl(tokenJs);
			//view.loadUrl(timeJs);
			view.loadUrl(localJs);
			view.reload();
		}
	}

	private void showSslErrorDialog(final SslErrorHandler handler) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle(R.string.prompt)
			.setMessage(String.format(getResources().getString(R.string.unsafe_link), mUrl))
			.setPositiveButton(R.string.ignore, new DialogInterface.OnClickListener() {
				@Override public void onClick(DialogInterface dialogInterface, int i) {
					handler.proceed();
					dialogInterface.dismiss();
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override public void onClick(DialogInterface dialogInterface, int i) {
					dialogInterface.dismiss();
					callback.stopLoadingWhileSslErrorOccurred();
				}
			})
			.create()
			.show();
	}

	public String getLastUrl() {
		WebBackForwardList backForwardList = copyBackForwardList();
		if (backForwardList != null && backForwardList.getSize() != 0) {
			//当前页面在历史队列中的位置
			int currentIndex = backForwardList.getCurrentIndex();
			WebHistoryItem historyItem = backForwardList.getItemAtIndex(currentIndex - 1);
			if (historyItem != null) {
				String backPageUrl = historyItem.getUrl();
				return backPageUrl;
			}
		}
		return "";
	}

	public void releaseAllWebViewCallback() {
		if (android.os.Build.VERSION.SDK_INT < 16) {
			try {
				Field field = WebView.class.getDeclaredField("mWebViewCore");
				field = field.getType().getDeclaredField("mBrowserFrame");
				field = field.getType().getDeclaredField("sConfigCallback");
				field.setAccessible(true);
				field.set(null, null);
			} catch (NoSuchFieldException e) {
				if (BuildConfig.DEBUG) {
					e.printStackTrace();
				}
			} catch (IllegalAccessException e) {
				if (BuildConfig.DEBUG) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
				if (sConfigCallback != null) {
					sConfigCallback.setAccessible(true);
					sConfigCallback.set(null, null);
				}
			} catch (NoSuchFieldException e) {
				if (BuildConfig.DEBUG) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				if (BuildConfig.DEBUG) {
					e.printStackTrace();
				}
			} catch (IllegalAccessException e) {
				if (BuildConfig.DEBUG) {
					e.printStackTrace();
				}
			}
		}
	}
}
