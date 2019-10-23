package com.pingan.baselibs.base;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 基础页面接口
 *
 * Created by yelongfei490 on 2017/12/28.
 */
public interface IBasePage {

    /**
     * 获取内容视图布局资源id
     *
     * @return 布局资源id or 0：不配置
     */
    @LayoutRes
    int getContentViewId();

    /**
     * 获取内容视图，优先这个方法获取视图
     *
     * @return null表示不设置视图
     */
    View getContentView();

    /**
     * 初始化视图
     */
    void initView();

    /**
     * 初始化，业务逻辑等
     */
    void init();

}
