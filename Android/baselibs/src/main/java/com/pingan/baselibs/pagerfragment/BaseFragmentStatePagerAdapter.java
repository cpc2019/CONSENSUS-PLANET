package com.pingan.baselibs.pagerfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentPagerAdapter基类
 *
 *
 * @author Martin
 */
public abstract class BaseFragmentStatePagerAdapter<DATA> extends FragmentStatePagerAdapter {

    protected FragmentActivity mContext;

    private FragmentManager mFragmentManager;

    private List<DATA> mDataList;
    private List<Fragment> mFragmentList;

    private int mAutoLoadPosition;

    public BaseFragmentStatePagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity.getSupportFragmentManager());
        mContext = fragmentActivity;
        mFragmentManager = fragmentActivity.getSupportFragmentManager();
        mDataList = new ArrayList<DATA>();
        mFragmentList = new ArrayList<Fragment>();
    }

    public BaseFragmentStatePagerAdapter(FragmentActivity fragmentActivity, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = fragmentActivity;
        mFragmentManager = fragmentManager;
        mDataList = new ArrayList<DATA>();
        mFragmentList = new ArrayList<Fragment>();
    }

    public void setAutoLoadPosition(int autoLoadPosition) {
        mAutoLoadPosition = autoLoadPosition;
    }

    public int getAutoLoadPosition() {
        return mAutoLoadPosition;
    }

    /**
     * 获取特定位置上的数据
     *
     * @param position
     * @return
     */
    public DATA getData(int position) {
        return mDataList.get(position);
    }

    public List<DATA> getDataList() {
        return mDataList;
    }

    @Override
    public final Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public final int getCount() {
        return mDataList.size();
    }

    @Override
    public final CharSequence getPageTitle(int position) {
        return getPageTitle(position, mDataList.get(position));
    }

    /**
     * 获取分页标题
     *
     * @param position
     * @param data
     * @return
     */
    public abstract CharSequence getPageTitle(int position, DATA data);

    /**
     * 设置标签数据
     *
     * @param dataList
     */
    public void setData(List<DATA> dataList) {
        if (!mFragmentList.isEmpty()) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : mFragmentList) {
                transaction.remove(fragment);
            }
            //transaction.commitAllowingStateLoss();
            //mFragmentManager.executePendingTransactions();
            transaction.commitNowAllowingStateLoss();
        }

        mFragmentList.clear();
        if (dataList == null) {
            mDataList.clear();
        } else {
            mDataList = dataList;
            for (int i = 0; i < mDataList.size(); i++) {
                mFragmentList.add(generateFragment(i, mDataList.get(i)));
            }
        }
    }

    /**
     * 添加标签数据
     *
     * @param dataList
     */
    public void addData(List<DATA> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            mFragmentList.add(generateFragment(i + mDataList.size(), dataList.get(i)));
        }
        mDataList.addAll(dataList);
    }

    /**
     * 生成Fragment
     *
     * @param position
     * @param data
     * @return
     */
    public abstract Fragment generateFragment(int position, DATA data);


}
