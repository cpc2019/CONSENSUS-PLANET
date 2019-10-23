package com.pingan.baselibs.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/11.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();    //缓存数据

    public BaseFragmentPagerAdapter(FragmentManager fm, SparseArray<Fragment> registeredFragments) {
        super(fm);
        this.registeredFragments = registeredFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        return registeredFragments.size(); // 代表页数
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}

