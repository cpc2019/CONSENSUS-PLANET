package com.pingan.baselibs.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class RxLifecycleUtils {

    private RxLifecycleUtils() {
        throw new IllegalStateException("Can't instance the RxLifecycleUtils");
    }

    /**
     * 绑定生命周期，Lifecycle.Event.ON_DESTROY表示指定在onDestroy()方法进行自动解绑，不指定的话会使用默认规则，如订阅发生在onResume()，则自动解绑在onPause()执行
     *
     * @param lifecycleOwner
     * @param <T>
     * @return
     */
    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner, Lifecycle.Event.ON_DESTROY)
        );
    }
}
