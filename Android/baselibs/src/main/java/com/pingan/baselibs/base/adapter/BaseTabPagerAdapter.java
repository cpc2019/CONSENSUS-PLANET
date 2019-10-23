package com.pingan.baselibs.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

/**
 * Created by Administrator on 2016/7/11.
 */
public class BaseTabPagerAdapter extends BaseFragmentPagerAdapter {
    String tabTitles[];
    public BaseTabPagerAdapter(FragmentManager fm, SparseArray<Fragment> registeredFragments, String tabTitles[]) {
        super(fm, registeredFragments);
        this.tabTitles = tabTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
