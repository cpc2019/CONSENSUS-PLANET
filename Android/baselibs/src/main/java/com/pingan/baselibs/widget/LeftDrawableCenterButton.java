package com.pingan.baselibs.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * drawableLeft与文本一起居中显示的Button
 *
 * @author Martin
 *
 *         <p>
 *         <a href="http://www.cnblogs.com/over140/p/3464348.html">农民伯伯</a>
 *         </p>
 */
public class LeftDrawableCenterButton extends AppCompatButton {

    public LeftDrawableCenterButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
    }

    public LeftDrawableCenterButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftDrawableCenterButton(Context context) {
        this(context, null);
    }

    @Override protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableLeft = drawables[0];
        if (drawableLeft != null) {
            float textWidth = getPaint().measureText(getText().toString());
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = 0;
            drawableWidth = drawableLeft.getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
        super.onDraw(canvas);
    }
}