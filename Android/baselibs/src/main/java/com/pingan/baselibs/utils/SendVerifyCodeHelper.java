package com.pingan.baselibs.utils;

import android.arch.lifecycle.LifecycleOwner;
import android.widget.TextView;
import com.pingan.baselibs.ApplicationManager;
import com.pingan.baselibs.R;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.concurrent.TimeUnit;

public class SendVerifyCodeHelper {

    /**
     * 倒数后启用发送按钮
     *
     * @param lifecycleOwner Activity or Fragment context
     * @param sendView 发送按钮
     * @param timeInSeconds 倒数秒数
     * @param countDownTextColor 倒数文本颜色
     */
    public static void countDown2EnableSendView(final LifecycleOwner lifecycleOwner, final TextView sendView, final long timeInSeconds, final int countDownTextColor) {
        final String originalText = sendView.getText().toString();
        final int originalColor = sendView.getCurrentTextColor();
        sendView.setClickable(false);
        Flowable.intervalRange(0, timeInSeconds, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .as(RxLifecycleUtils.<Long>bindLifecycle(lifecycleOwner))
                .subscribe(new DefaultSubscriber<Long>() {

                    @Override public void onNext(Long aLong) {
                        long leftSeconds = timeInSeconds - aLong;
                        if (leftSeconds != 0) {
                            sendView.setTextColor(countDownTextColor);
                            sendView.setText(ApplicationManager.getApplication()
                                    .getString(R.string.format_count_down_to_fetch, leftSeconds));
                        }
                    }

                    @Override public void onError(Throwable t) {
                    }

                    @Override public void onComplete() {
                        sendView.setText(originalText);
                        sendView.setTextColor(originalColor);
                        sendView.setClickable(true);
                    }
                });
    }

}
