package com.pingan.baselibs.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

/**
 * Created by Administrator on 2016/7/11.
 */
public class SmartTabPagerAdapter extends BaseTabPagerAdapter {

    public SmartTabPagerAdapter(FragmentManager fm, SparseArray<Fragment> registeredFragments, String[] tabTitles) {
        super(fm, registeredFragments, tabTitles);
    }
}
