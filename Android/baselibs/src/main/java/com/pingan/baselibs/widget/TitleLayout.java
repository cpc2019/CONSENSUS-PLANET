package com.pingan.baselibs.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import com.pingan.baselibs.R;
import com.pingan.baselibs.utils.AndroidVersionUtils;
import com.pingan.baselibs.utils.DisplayUtils;

/**
 * Titlelayout
 */
public class TitleLayout extends RelativeLayout {
	private Context context;
	private Space mSpaceStatusBar;
	private TextView mBackView;
	private TextView mTitleView;
	private TextView mRightTitleView;
	private ImageView mIvLeft;
	private ImageView mIvRight;
	private View mTitleBarDivider;
	private RelativeLayout layoutContentBar;

	public TitleLayout(Context context) {
		this(context, null);
	}

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		ViewGroup.LayoutParams layoutParams =
			new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		setLayoutParams(layoutParams);

		LayoutInflater.from(getContext()).inflate(R.layout.common_title_layout, this, true);
		// 5.0以上版本需要做状态栏占位处理
		mSpaceStatusBar = findViewById(R.id.space_status_bar);
		mSpaceStatusBar.getLayoutParams().height =
			AndroidVersionUtils.hasLollipop() ? DisplayUtils.getStatusBarHeight(getContext()) : 0;

		mBackView = findViewById(R.id.tv_title_back);
		mTitleView = findViewById(R.id.tv_title_view);
		mRightTitleView = findViewById(R.id.tv_title_right);
		mIvLeft = findViewById(R.id.iv_title_left);
		mIvRight = findViewById(R.id.iv_title_right);
		mTitleBarDivider = findViewById(R.id.title_bar_divider);
		layoutContentBar = findViewById(R.id.layout_content_bar);
		setBackgroundResource(R.color.title_bar_color);
	}

	public RelativeLayout getLayoutContentBar() {
		return layoutContentBar;
	}

	/**
	 * 设置标题
	 */
	public TitleLayout setTitle(CharSequence text) {
		if (mTitleView != null) {
			mTitleView.setText(text);
		}
		return this;
	}

	/**
	 * 设置标题
	 */
	public TitleLayout setTitle(@StringRes int textResId) {
		if (mTitleView != null) {
			mTitleView.setText(textResId);
		}
		return this;
	}

	public String getTitleStr() {
		if (mTitleView != null) {
			return mTitleView.getText().toString();
		}
		return "";
	}
	//public TitleLayout setTitle(CharSequence titleText) {
	//    mTitleView.setText(titleText);
	//    return this;
	//}

	public TitleLayout setTitleLeftView(OnClickListener clickListener) {
		if (mIvLeft != null) {
			mIvLeft.setVisibility(VISIBLE);
			mIvLeft.setOnClickListener(clickListener);
		}
		return this;
	}

	public TitleLayout setTitleLeftView(@DrawableRes int res, OnClickListener clickListener) {
		if (mIvLeft != null) {
			mIvLeft.setVisibility(VISIBLE);
			mIvLeft.setImageResource(res);
			mIvLeft.setOnClickListener(clickListener);
		}
		return this;
	}

	public TitleLayout setTitleRightView(@DrawableRes int res, OnClickListener clickListener) {
		if (mIvRight != null) {
			if (res != 0) {
				mIvRight.setImageResource(res);
				mIvRight.setVisibility(VISIBLE);
			}
			mIvRight.setOnClickListener(clickListener);
		}
		return this;
	}

	public ImageView getTitleRightView() {
		return mIvRight;
	}

	/**
	 * 设置左边文字和点击
	 */
	public TitleLayout setLeftTextAndClick(CharSequence text, OnClickListener clickListener) {
		if (mBackView != null) {
			mBackView.setText(text);
			mBackView.setOnClickListener(clickListener);
			mBackView.setVisibility(VISIBLE);
		}
		return this;
	}

	/**
	 * 设置左边文字和点击
	 */
	public TitleLayout setLeftTextAndClick(@StringRes int textResId,
		OnClickListener clickListener) {
		setLeftTextAndClick(textResId, 0, clickListener);
		return this;
	}

	/**
	 * 设置左边文字显隐
	 */
	public TitleLayout setLeftTextVisibility(boolean visible) {
		if (mBackView == null) {
			return this;
		}
		mBackView.setVisibility(visible ? VISIBLE : GONE);
		return this;
	}

	/**
	 * 设置左边文字和点击
	 */

	public TitleLayout setLeftTextAndClick(@StringRes int textResId, @DrawableRes int icon,
		OnClickListener clickListener) {
		if (mBackView == null) {
			return this;
		}
		if (textResId != 0) {
			mBackView.setText(textResId);
		} else {
			mBackView.setText("");
		}
		mBackView.setOnClickListener(clickListener);
		mBackView.setVisibility(VISIBLE);
		if (icon != 0) {
			mBackView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
		}
		return this;
	}

	/**
	 * 展示返回按钮，设置点击监听器
	 */
	public TitleLayout setBack(OnClickListener clickListener) {
		setLeftTextAndClick(R.string.go_back, clickListener);
		return this;
	}

	/**
	 * 展示返回按钮，设置点击监听器
	 */
	public TitleLayout setBack(@DrawableRes int icon, OnClickListener clickListener) {
		setLeftTextAndClick(R.string.go_back, icon, clickListener);
		return this;
	}

	/**
	 * 设置右边文本和点击事件
	 */
	public TitleLayout setRightTextAndClick(CharSequence text, OnClickListener clickListener) {
		if (mRightTitleView == null) {
			return this;
		}
		mRightTitleView.setText(text);
		mRightTitleView.setOnClickListener(clickListener);
		mRightTitleView.setVisibility(VISIBLE);
		return this;
	}

	/**
	 * 设置右边文本和点击事件
	 */
	public TitleLayout setRightTextAndClick(CharSequence text, int color,
		OnClickListener clickListener) {
		if (mRightTitleView == null) {
			return this;
		}
		mRightTitleView.setText(text);
		mRightTitleView.setTextColor(color);
		mRightTitleView.setOnClickListener(clickListener);
		mRightTitleView.setVisibility(VISIBLE);
		return this;
	}

	public TitleLayout setRightIcon(int resId) {
		if (mRightTitleView == null) {
			return this;
		}
		mRightTitleView.setCompoundDrawablePadding(DisplayUtils.dp2px(context, 5));
		mRightTitleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
		return this;
	}

	/**
	 * 设置右边文本和点击事件
	 */
	public TitleLayout setRightTextAndClick(@StringRes int textResId,
		OnClickListener clickListener) {
		if (mRightTitleView == null) {
			return this;
		}
		mRightTitleView.setText(textResId);
		mRightTitleView.setOnClickListener(clickListener);
		mRightTitleView.setVisibility(VISIBLE);
		return this;
	}

	public TitleLayout setRightTextVisible(int visible) {
		if (mRightTitleView == null) {
			return this;
		}
		mRightTitleView.setVisibility(visible);
		return this;
	}

	/**
	 * 设置左边文本的颜色
	 */
	public TitleLayout setLeftTextColor(@ColorRes int colorResId) {
		if (mBackView == null) {
			return this;
		}
		mBackView.setTextColor(ContextCompat.getColor(context, colorResId));
		return this;
	}

	/**
	 * 设置右边文本的颜色
	 */
	public TitleLayout setRightTextColor(@ColorRes int colorResId) {
		if (mRightTitleView == null) {
			return this;
		}
		mRightTitleView.setTextColor(ContextCompat.getColor(context, colorResId));
		return this;
	}

	/**
	 * 设置标题文字颜色
	 */
	public TitleLayout setTitleTextColor(@ColorRes int colorResId) {
		if (mTitleView == null) {
			return this;
		}
		mTitleView.setTextColor(ContextCompat.getColor(context, colorResId));
		return this;
	}

	/**
	 * 设置底部分割线的颜色
	 */
	public TitleLayout setTitleBarDividerColor(@ColorRes int colorResId) {
		if (mTitleBarDivider == null) {
			return this;
		}
		mTitleBarDivider.setBackgroundColor(ContextCompat.getColor(context, colorResId));
		return this;
	}

	/**
	 * 设置底部分割线是否可见
	 */
	public TitleLayout setTitleBarDividerVisible(boolean visable) {
		if (mTitleBarDivider == null) {
			return this;
		}
		if (!visable) {
			mTitleBarDivider.setVisibility(GONE);
		} else {
			mTitleBarDivider.setVisibility(VISIBLE);
		}
		return this;
	}

	public TitleLayout setRightImageVisibility(int visibility) {
		if (mIvRight == null) {
			return this;
		}
		mIvRight.setVisibility(visibility);
		return this;
	}

	public ImageView getRightImage() {
		return mIvRight;
	}

	public TitleLayout setBackground(int color) {
		layoutContentBar.setBackgroundResource(color);
		return this;
	}

	public TitleLayout adjustLeftImageAndText() {
		int padding = getResources().getDimensionPixelSize(R.dimen.space_15);
		mIvLeft.setPadding(padding, 0, 0, 0);
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) mBackView.getLayoutParams();
		layoutParams.addRule(RelativeLayout.RIGHT_OF, mIvLeft.getId());
		return this;
	}
}
