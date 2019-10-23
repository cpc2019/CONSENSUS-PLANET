package com.pingan.baselibs.pagerfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.pingan.baselibs.base.BaseFragment;

/**
 * 用于Pager中的Fragment基类
 *
 * @author Martin
 */
public abstract class BasePagerFragment extends BaseFragment {

    private static final String KEY_AUTO_LOAD = "autoLoad";

    /**
     * 新建实例
     *
     * @param context
     * @param clazz 具体要实例化的类，继承自BasePagerFragment
     * @param bundle 参数
     * @param autoLoad 是否自动加载
     * @return
     */
    public static Fragment newInstance(Context context, Class<? extends BasePagerFragment> clazz, Bundle bundle, Boolean autoLoad) {
        if (autoLoad != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(KEY_AUTO_LOAD, autoLoad);
        }
        return Fragment.instantiate(context, clazz.getName(), bundle);
    }

    /**
     * 自动加载数据标识
     * <p/>
     * <br>
     * ViewPager预加载Fragment导致Fragment生命周期在还未可见的情况下就被执行，此时若Fragment初始化操作比较耗时，就会导致卡顿。
     * 通过监听Pager的滑动停止状态，再去调用自定义的onRealVisible()方法来执行初始化操作来解决这个问题。但是ViewPager和相应Fragment
     * 初始化后并不会引起滑动状态的改变，所以定义一个自动加载标识来判定是否这个Fragment要自动进行初始化操作。
     * </br>
     */
    private boolean mAutoLoad;

    private boolean mOnCreate = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!mOnCreate) {
            return;
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAutoLoad = bundle.getBoolean(KEY_AUTO_LOAD);
        }
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mAutoLoad) {
            onRealVisible(true);
            mAutoLoad = false;
        }
    }

    @Override
    public void init() {
    }

    /**
     * 当Fragment真正可见时调用
     *
     * @param visible
     */
    public final void onRealVisible(boolean visible) {
        onRealVisible(mOnCreate, visible);
        if (mOnCreate)
            mOnCreate = false;
    }

    /**
     * 当Fragment真正可见时调用
     *
     * @param onCreate 是否首次创建
     * @param visible  是否可见
     */
    public abstract void onRealVisible(boolean onCreate, boolean visible);

}
