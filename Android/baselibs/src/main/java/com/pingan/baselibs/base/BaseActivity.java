package com.pingan.baselibs.base;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import butterknife.ButterKnife;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.pingan.baselibs.ApplicationManager;
import com.pingan.baselibs.BaseLibs;
import com.pingan.baselibs.R;
import com.pingan.baselibs.utils.AndroidBug5497Workaround;
import com.pingan.baselibs.utils.AndroidVersionUtils;
import com.pingan.baselibs.utils.LocalUtils;
import com.pingan.baselibs.utils.RxLifecycleUtils;
import com.pingan.baselibs.utils.StatusBarUtils;
import com.pingan.baselibs.utils.TextToolUtils;
import com.pingan.baselibs.widget.LoadingDialogFragment;
import com.pingan.baselibs.widget.TitleLayout;
import com.uber.autodispose.AutoDisposeConverter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

/**
 * @version V1.0
 * @Title: BaseAppCompatActivity.java
 * @Description:
 * @date 2017/5/25 18:14A
 */
public abstract class BaseActivity extends AppCompatActivity
	implements View.OnClickListener, IBasePage {

	private Logger logger = XLog.tag(this.getClass().getSimpleName()).nst().build();

	private TitleLayout mTitleLayout;

	//private LoadingDialog mLoadingDialog;
	private LoadingDialogFragment mLoadingDialogFragment;

	public boolean isExceptionStart = false;// 是否为异常启动

	/**
	 * 沉浸式标题栏
	 *
	 * @return true：添加标题栏，否则不添加
	 */
	protected boolean showImmersiveBar() {
		return true;
	}

	/**
	 * 展示标题栏
	 *
	 * @return true：添加标题栏，否则不添加
	 */
	protected boolean showTitleBar() {
		return true;
	}

	/**
	 * 状态栏默认字体颜色为黑色
	 */
	protected boolean showBlackFontStatusBar() {
		return true;
	}

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLocaleLanguage();
		requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);//长按出现复制粘贴栏在顶部占位
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (showImmersiveBar()) {
			adjustStatusBar();
		}
		// 添加titlebar
		if (showTitleBar()) {
			mTitleLayout = new TitleLayout(this);
			getRootView().addView(mTitleLayout, 0);
		}
		// 设置contentView
		View view = getContentView();
		if (view == null) {
			int contentId = getContentViewId();
			if (contentId != 0) {
				setContentView(contentId);
				ButterKnife.bind(this);
			}
		} else {
			setContentView(view);
			ButterKnife.bind(this, view);
		}
		if (savedInstanceState != null) {
			isExceptionStart = savedInstanceState.getBoolean("isExceptionStart", false);
		}

		if (BaseLibs.isDarkTheme) {
			StatusBarUtils.setStatusBarWhiteFontMode(this);
		}
		PushAgent.getInstance(this).onAppStart();
		initView();
		init();
	}

	protected void initLocaleLanguage() {
		Resources resources = getResources();
		Configuration configuration = resources.getConfiguration();
		configuration.locale = LocalUtils.getCurrentLocale();
		//        configuration.locale = LocalUtils.getSystemLanguage();
		resources.updateConfiguration(configuration, resources.getDisplayMetrics());//更新配置
	}

	/**
	 * 调整状态栏
	 */
	private void adjustStatusBar() {
		// 为尽量保持效果统一，不做4.4的适配
		//// Android4.4-5.0（不包括5.0）设置全透明状态栏，contentView顶入状态栏
		//if (AndroidVersionUtils.hasKitKat() && !AndroidVersionUtils.hasLollipop()) {
		//    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		//}
		// Android5.0后设置全透明状态栏，contentView顶入状态栏
		if (AndroidVersionUtils.hasLollipop()) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
			getWindow().getDecorView()
				.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
		if (showBlackFontStatusBar()) {
			StatusBarUtils.setStatusBarBlackFontMode(this);
		}
	}

	@Override public View getContentView() {
		return null;
	}

	/**
	 * 获取android content view
	 */
	protected View getAndroidContentView() {
		return findViewById(android.R.id.content);
	}

	/**
	 * 获取根视图，就是我们自己设置的视图的父容器，例如这里就是titlebar的容器
	 */
	private ViewGroup getRootView() {
		return (ViewGroup) getAndroidContentView().getParent();
	}

	/**
	 * 用这个方法来解决开启沉浸式状态栏后，有编辑框的页面设置了adjustResize会失效，导致编辑框被输入法挡住的问题
	 */
	public void fixKeyboardBlockEditText() {
		if (AndroidVersionUtils.hasLollipop()) {
			AndroidBug5497Workaround.assistActivity(getRootView());
		}
	}

	/**
	 * 设置窗口背景，即修改styles中定义的windowbackground
	 */
	public void setWindowBackgroundDrawableResource(@DrawableRes int resId) {
		getWindow().setBackgroundDrawableResource(resId);
	}

	@Override public void setTitle(int titleId) {
		//super.setTitle(titleId);
		setTitleText(titleId);
	}

	@Override public void setTitle(CharSequence title) {
		//super.setTitle(title);
		setTitleText(title);
	}

	/**
	 * 返回按钮显示
	 */
	public void setBack() {
		//mTitleLayout.setLeftTextAndClick(R.string.go_back,this);
		mTitleLayout.setLeftTextAndClick(0, R.mipmap.nav_back_b, this);
	}

	private void setTitleText(@StringRes int titleRes) {
		setTitleText(getString(titleRes));
	}

	/**
	 * 设置title显示
	 */
	private void setTitleText(@Nullable CharSequence titleText) {
		mTitleLayout.setTitle(titleText);
	}

	/**
	 * 设置title显示
	 */
	public void setTitleRightText(@Nullable String titleText) {
		mTitleLayout.setRightTextAndClick(titleText, this);
	}

	/**
	 * 设置title显示
	 */
	public void setTitleRightText(@Nullable String titleText,
		View.OnClickListener onClickListener) {
		mTitleLayout.setRightTextAndClick(titleText, onClickListener);
	}

	public void setTitleRightText(@Nullable String titleText, int resColor) {
		mTitleLayout.setRightTextAndClick(titleText, this);
		mTitleLayout.setRightTextColor(resColor);
	}

	public void setTitleRightText(@Nullable String titleText, @ColorRes int resColor,
		View.OnClickListener onClickListener) {
		mTitleLayout.setRightTextAndClick(titleText, onClickListener);
		mTitleLayout.setRightTextColor(resColor);
	}

	public void setTitleLeftView(@DrawableRes int res) {
		mTitleLayout.setTitleLeftView(this);
	}

	public void setTitleRightView(@DrawableRes int res, View.OnClickListener onClickListener) {
		mTitleLayout.setTitleRightView(res, onClickListener);
	}

	public ImageView getTitleRightView() {
		return mTitleLayout.getTitleRightView();
	}

	public void showLoading() {
		//if (mLoadingDialog == null) {
		//	mLoadingDialog = new LoadingDialog(this);
		//	mLoadingDialog.setCancelable(true);
		//}
		//mLoadingDialog.show();
		if (BaseActivity.this.isFinishing()){
			return;
		}
		if (mLoadingDialogFragment == null) {
			mLoadingDialogFragment = LoadingDialogFragment.newInstance();
		}
		mLoadingDialogFragment.show(getSupportFragmentManager(), getClass().getName());
	}

	public void showLoading(@StringRes int resId) {
		showLoading(getString(resId));
	}

	public void showLoading(String loadingTip) {
		//if (mLoadingDialog == null) {
		//	mLoadingDialog = new LoadingDialog(this, loadingTip);
		//} else {
		//	mLoadingDialog.setContent(loadingTip);
		//}
		//mLoadingDialog.show();
		if (BaseActivity.this.isFinishing()){
			return;
		}
		if (mLoadingDialogFragment == null) {
			mLoadingDialogFragment = LoadingDialogFragment.newInstance(loadingTip);
		}
		mLoadingDialogFragment.show(getSupportFragmentManager(), getClass().getName());
	}

	public boolean isShowLoading() {
		//return mLoadingDialog != null && mLoadingDialog.isShowing();
		return mLoadingDialogFragment != null && mLoadingDialogFragment.isShowDialog();
	}

	public void dismissLoading() {
		//if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
		//	mLoadingDialog.dismiss();
		//}
		if (mLoadingDialogFragment != null) {
			mLoadingDialogFragment.dismissAllowingStateLoss();
		}
	}

	@Override public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.tv_title_back) {
			finish();
		}
	}

	/**
	 * 获取标题栏控件
	 */
	@Nullable public TitleLayout getTitleBar() {
		return mTitleLayout;
	}

	@Override protected void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);
		String strName = null == mTitleLayout || TextUtils.isEmpty(mTitleLayout.getTitleStr())
			? getClass().getName() : mTitleLayout.getTitleStr();
		MobclickAgent.onPageStart(strName);
		/*if (BaseLibs.openServerTime && !TextUtils.isEmpty(BaseLibs.timsStamp)) {
			try {
				ClipboardManager cm = (ClipboardManager) ApplicationManager.getContext()
					.getSystemService(Context.CLIPBOARD_SERVICE);
				String temp = (String) cm.getText();
				if (temp.equals(BaseLibs.timsStamp)) {
					cm.setText("");
				}
			} catch (Exception e) {

			}
		}*/
	}

	@Override protected void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);
		String strName = null == mTitleLayout || TextUtils.isEmpty(mTitleLayout.getTitleStr())
			? getClass().getName() : mTitleLayout.getTitleStr();
		MobclickAgent.onPageEnd(strName);
		if (!TextUtils.isEmpty(BaseLibs.timsStamp)) {
			try {
				ClipboardManager cm = (ClipboardManager) ApplicationManager.getContext()
					.getSystemService(Context.CLIPBOARD_SERVICE);
				if (TextUtils.isEmpty(cm.getText().toString().trim())) {
					TextToolUtils.copy(this, BaseLibs.timsStamp);
				}
			} catch (Exception e) {
				XLog.e(e.getMessage());
			}
		}
	}

	/**
	 * 防止网络请求内存泄漏
	 */
	protected <T> AutoDisposeConverter<T> bindLifecycle() {
		return RxLifecycleUtils.bindLifecycle(this);
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		logger.i("onDestroy");
		dismissLoading();
	}
}
