package com.pingan.baselibs.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.umeng.analytics.MobclickAgent;

/**
 * @Description: 懒加载的Fragment
 * @CreateTime: 2016/11/8 10:06.
 * @CreateAuthor: lyl
 */
public abstract class LazyBaseFragment extends BaseFragment {
    protected Context mContext;
    protected Resources mResources;
    private boolean isPrepared;
    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;
    /**
     * 是否可见
     */
    private boolean isFirstVisible = true;
    /**
     * 是否不可见
     */
    private boolean isFirstInvisible = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mResources = mContext.getResources();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    public synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {
        logger.i("lyl", "onFirstUserVisible===>第一次fragment可见");
        initData();
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible() {
        logger.i("lyl", "onUserVisible===>fragment可见");
    }

    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {
        logger.i("lyl", "onFirstUserInvisible===>第一次fragment不可见");
    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {
        logger.i("lyl", "onUserInvisible===>fragment不可见");
    }
}
