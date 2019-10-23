package com.pingan.baselibs.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.pingan.baselibs.R;

/**
 * author :asus
 * date:2019/6/5 12:01
 * describe:
 */
public class LoadingDialogFragment extends DialogFragment {

	private RelativeLayout mCommonDialogLoadingRelativelayout;
	private TextView mCommonDialogLoadingText;

	public static LoadingDialogFragment newInstance() {
		Bundle args = new Bundle();
		LoadingDialogFragment fragment = new LoadingDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public static LoadingDialogFragment newInstance(String loadingTip) {
		Bundle args = new Bundle();
		args.putString("loadingTip", loadingTip);
		LoadingDialogFragment fragment = new LoadingDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable @Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState) {

		getDialog().getWindow().setWindowAnimations(R.style.loading_dialog_animation);

		View view = LayoutInflater.from(getContext()).inflate(R.layout.common_loading, null);
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		initView(view);

		return view;
	}

	@Override public void onStart() {
		super.onStart();

		WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
		params.dimAmount = 0.4f;
		getDialog().getWindow().setAttributes(params);
	}

	private void initView(View view) {
		mCommonDialogLoadingRelativelayout =
			view.findViewById(R.id.common_dialog_loading_relativelayout);
		mCommonDialogLoadingText = view.findViewById(R.id.common_dialog_loading_text);

		mCommonDialogLoadingRelativelayout.setBackgroundColor(Color.parseColor("#00000000"));

		if (null != getArguments()) {
			String contextStr = getArguments().getString("loadingTip");
			if (!TextUtils.isEmpty(contextStr)) {
				mCommonDialogLoadingText.setVisibility(View.VISIBLE);
				mCommonDialogLoadingText.setText(contextStr);
			}
		}
	}

	@Override public void show(FragmentManager manager, String tag) {
		try {
			//在每个add事务前增加一个remove事务，防止连续的add
			manager.beginTransaction().remove(this).commit();
			super.show(manager, tag);
		} catch (Exception e) {
			//同一实例使用不同的tag会异常,这里捕获一下
			e.printStackTrace();
		}
	}

	public boolean isShowDialog() {
		return getShowsDialog();
	}

	/**
	 * 设置弹出框的文字内容
	 */
	public void setContent(String content) {
		if (null != mCommonDialogLoadingText) {
			mCommonDialogLoadingText.setVisibility(View.VISIBLE);
			mCommonDialogLoadingText.setText(content);
		}
	}

	/**
	 * 设置弹出框的文字内容
	 */
	public void setContent(int resourceId) {
		if (null != mCommonDialogLoadingText) {
			mCommonDialogLoadingText.setText(resourceId);
			mCommonDialogLoadingText.setVisibility(View.VISIBLE);
		}
	}
}
