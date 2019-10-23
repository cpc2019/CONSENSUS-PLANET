package com.pingan.baselibs.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.pingan.baselibs.BaseLibs;
import com.pingan.baselibs.utils.RxLifecycleUtils;
import com.pingan.baselibs.widget.LoadingDialogFragment;
import com.uber.autodispose.AutoDisposeConverter;

public abstract class BaseFragment extends Fragment implements IBasePage {

	protected Logger logger = XLog.tag(this.getClass().getSimpleName()).nst().build();

	private Unbinder unbinder;
	//private LoadingDialog mLoadingDialog;
	private LoadingDialogFragment mLoadingDialogFragment;

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		logger.i("onAttach");
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logger.i("onCreate");
	}

	@Nullable @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {
		logger.i("onCreateView");
		View view = getContentView();
		if (view == null) {
			int contentId = getContentViewId();
			if (contentId != 0) {
				view = inflater.inflate(getContentViewId(), container, false);
			}
		}
		if (null != view) {
			unbinder = ButterKnife.bind(this, view);
			initView();
			return view;
		}

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override public View getContentView() {
		return null;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logger.i("onActivityCreated");
		init();
	}

	@Override public void onResume() {
		super.onResume();
		logger.i("onResume");
	}

	@Override public void onPause() {
		super.onPause();
		logger.i("onPaused");
	}

	public void showLoading() {
		//if (mLoadingDialog == null) {
		//	mLoadingDialog = new LoadingDialog(getActivity());
		//	mLoadingDialog.setCancelable(true);
		//}
		//mLoadingDialog.show();

		if (mLoadingDialogFragment == null) {
			mLoadingDialogFragment = LoadingDialogFragment.newInstance();
		}
		mLoadingDialogFragment.show(getActivity().getSupportFragmentManager(), getClass().getName());
	}

	public void showLoading(@StringRes int resId) {
		showLoading(getString(resId));
	}

	public void showLoading(String loadingTip) {
		//if (mLoadingDialog == null) {
		//	mLoadingDialog = new LoadingDialog(getActivity(), loadingTip);
		//} else {
		//	mLoadingDialog.setContent(loadingTip);
		//}
		//mLoadingDialog.show();
		if (mLoadingDialogFragment == null) {
			mLoadingDialogFragment = LoadingDialogFragment.newInstance(loadingTip);
		}
		mLoadingDialogFragment.show(getActivity().getSupportFragmentManager(), getClass().getName());
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

	/**
	 * 防止网络请求内存泄漏
	 */
	protected <T> AutoDisposeConverter<T> bindLifecycle() {
		return RxLifecycleUtils.bindLifecycle(this);
	}

	@Override public void onDestroyView() {
		super.onDestroyView();
		logger.i("onDestroyView");
		if (unbinder != null) {
			unbinder.unbind();
		}
	}

	@Override public void onDestroy() {
		super.onDestroy();
		logger.i("onDestroy");
		if (BaseLibs.refWatcher != null) {
			BaseLibs.refWatcher.watch(this);
		}
	}

	@Override public void onDetach() {
		super.onDetach();
		logger.i("onDetach");
	}
}
