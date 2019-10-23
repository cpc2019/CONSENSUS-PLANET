package com.pingan.baselibs.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * author :tony
 * date:2019/5/23 11:17
 * describe:金额输入框
 */
public class AmountEditText extends ClearableEditText {
	public AmountEditText(Context context) {
		super(context);
		initEdit();
	}

	public AmountEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initEdit();
	}

	private void initEdit() {
		//设置输入控制器
		InputFilter[] filters = new InputFilter[] { new CashierInputFilter() };
		getEditText().setFilters(filters);
	}

	@Override public String getTextString() {
		String amount = getEditText().getText().toString().trim();
		if (TextUtils.isEmpty(amount)) {
			return "";
		} else {
			if (amount.endsWith(".")) {
				return amount.substring(0, amount.length() - 1);
			}
		}
		return super.getTextString();
	}
}
