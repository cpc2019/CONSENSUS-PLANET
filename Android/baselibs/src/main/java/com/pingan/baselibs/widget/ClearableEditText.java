package com.pingan.baselibs.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.pingan.baselibs.R;

/**
 * 可清除文字的编辑框
 */
public class ClearableEditText extends RelativeLayout implements TextWatcher, View.OnClickListener {

	private static final int DEFAULT_TEXT_SIZE = 28;

	private EditText mEditText;
	private ImageView mIvClear;

	public ClearableEditText(Context context) {
		this(context, null);
	}

	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClearableEditText);
		CharSequence hint = null;
		ColorStateList textColor = null;
		ColorStateList textColorHint = null;
		int textSize = DEFAULT_TEXT_SIZE;
		int maxLength = -1;
		int inputType = EditorInfo.TYPE_CLASS_TEXT;
		Drawable iconClear = null;
		int gravity = Gravity.NO_GRAVITY;
		int imeOptions = -1;
		CharSequence text = null;
		for (int i = 0; i < a.getIndexCount(); i++) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.ClearableEditText_android_hint) {
				hint = a.getText(R.styleable.ClearableEditText_android_hint);
			} else if (attr == R.styleable.ClearableEditText_android_textColor) {
				textColor = a.getColorStateList(R.styleable.ClearableEditText_android_textColor);
			} else if (attr == R.styleable.ClearableEditText_android_textColorHint) {
				textColorHint =
					a.getColorStateList(R.styleable.ClearableEditText_android_textColorHint);
			} else if (attr == R.styleable.ClearableEditText_android_textSize) {
				textSize = a.getDimensionPixelSize(R.styleable.ClearableEditText_android_textSize,
					DEFAULT_TEXT_SIZE);
			} else if (attr == R.styleable.ClearableEditText_android_maxLength) {
				maxLength = a.getInt(R.styleable.ClearableEditText_android_maxLength, -1);
			} else if (attr == R.styleable.ClearableEditText_android_inputType) {
				inputType = a.getInt(R.styleable.ClearableEditText_android_inputType,
					EditorInfo.TYPE_CLASS_TEXT);
			} else if (attr == R.styleable.ClearableEditText_iconClear) {
				iconClear = a.getDrawable(R.styleable.ClearableEditText_iconClear);
			} else if (attr == R.styleable.ClearableEditText_android_gravity) {
				gravity =
					a.getInt(R.styleable.ClearableEditText_android_gravity, Gravity.NO_GRAVITY);
			} else if (attr == R.styleable.ClearableEditText_android_text) {
				text = a.getText(R.styleable.ClearableEditText_android_text);
			} else if (attr == R.styleable.ClearableEditText_android_imeOptions) {
				imeOptions = a.getInt(R.styleable.ClearableEditText_android_imeOptions,
					EditorInfo.IME_ACTION_NONE);
			}
		}
		a.recycle();

		LayoutInflater.from(context).inflate(R.layout.clearable_edit_text, this, true);
		mEditText = findViewById(R.id.edit_text);
		mIvClear = findViewById(R.id.iv_clear);

		mEditText.addTextChangedListener(this);
		mEditText.setHint(hint);
		if (textColor != null) {
			mEditText.setTextColor(textColor);
		}
		if (textColorHint != null) {
			mEditText.setHintTextColor(textColorHint);
		}
		mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		if (maxLength >= 0) {
			mEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
		} else {
			mEditText.setFilters(new InputFilter[0]);
		}
		mEditText.setInputType(inputType);
		if (iconClear != null) {
			mIvClear.setImageDrawable(iconClear);
		}
		mEditText.setGravity(gravity);
		mEditText.setText(text);
		if (imeOptions > 0) {
			mEditText.setImeOptions(imeOptions);
		}
		mIvClear.setOnClickListener(this);
	}

	@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override public void afterTextChanged(Editable s) {
		if (mEditText.isEnabled()) {
			mIvClear.setVisibility(s.length() > 0 ? VISIBLE : GONE);
		} else {
			mIvClear.setVisibility(GONE);
		}
	}

	@Override public void onClick(View v) {
		clear();
	}

	public Editable getText() {
		return mEditText.getText();
	}

	public String getTextString() {
		return mEditText.getText().toString().trim();
	}

	public void setText(@StringRes int resid) {
		mEditText.setText(resid);
	}

	public void setText(CharSequence text) {
		mEditText.setText(text);
	}

	public void clear() {
		mEditText.getText().clear();
	}

	public void setSelection(int index) {
		mEditText.setSelection(index);
	}

	public int length() {
		return mEditText.length();
	}

	public void setHint(int resId) {
		mEditText.setHint(resId);
	}

	public void setHint(String res) {
		mEditText.setHint(res);
	}

	public void addTextChangedListener(TextWatcher watcher) {
		mEditText.addTextChangedListener(watcher);
	}

	public EditText getEditText() {
		return mEditText;
	}

	public void setEditEnabled(boolean enabled) {
		if (mEditText != null) {
			mEditText.setEnabled(enabled);
			mIvClear.setVisibility(enabled ? VISIBLE : GONE);
		}
	}
}
