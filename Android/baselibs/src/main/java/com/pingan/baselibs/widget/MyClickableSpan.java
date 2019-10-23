package com.pingan.baselibs.widget;

import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class MyClickableSpan extends ClickableSpan {
    private int mColor;
    private boolean mHasUnderline;
    private View.OnClickListener mListener;

    public MyClickableSpan(int mColor, boolean mHasUnderline, View.OnClickListener listener) {
        this.mColor = mColor;
        this.mHasUnderline = mHasUnderline;
        mListener = listener;
    }

    @Override public void onClick(@NonNull View view) {
        view.setOnClickListener(mListener);
    }

    @Override public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mColor);
        ds.setUnderlineText(mHasUnderline);
    }
}
