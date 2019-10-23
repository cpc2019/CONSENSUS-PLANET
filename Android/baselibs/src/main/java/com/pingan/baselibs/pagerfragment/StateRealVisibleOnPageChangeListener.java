package com.pingan.baselibs.pagerfragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.elvishew.xlog.XLog;

/**
 * 真实可见分页改变监听器
 *
 * @author Martin
 */
public class StateRealVisibleOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private static final String TAG = "RealVisibleOnPageChangeListener";

    private int mCurrentPosition, mNextPosition;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private BaseFragmentStatePagerAdapter mBaseFragmentPagerAdapter;

    private OnRealPositionListener mOnRealPositionListener;

    public StateRealVisibleOnPageChangeListener(BaseFragmentStatePagerAdapter baseFragmentPagerAdapter) {
        mBaseFragmentPagerAdapter = baseFragmentPagerAdapter;
    }

    public StateRealVisibleOnPageChangeListener(BaseFragmentStatePagerAdapter baseFragmentPagerAdapter, int currentPosition) {
        mBaseFragmentPagerAdapter = baseFragmentPagerAdapter;
        mCurrentPosition = currentPosition;
    }

    public StateRealVisibleOnPageChangeListener(BaseFragmentStatePagerAdapter baseFragmentPagerAdapter, ViewPager.OnPageChangeListener onPageChangeListener) {
        mBaseFragmentPagerAdapter = baseFragmentPagerAdapter;
        mOnPageChangeListener = onPageChangeListener;
    }

    public StateRealVisibleOnPageChangeListener(BaseFragmentStatePagerAdapter baseFragmentPagerAdapter, ViewPager.OnPageChangeListener onPageChangeListener, int currentPosition) {
        mBaseFragmentPagerAdapter = baseFragmentPagerAdapter;
        mOnPageChangeListener = onPageChangeListener;
        mCurrentPosition = currentPosition;
    }

    public void setOnRealPositionListener(OnRealPositionListener onRealPositionListener) {
        mOnRealPositionListener = onRealPositionListener;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null)
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        XLog.d("onPageSelected---position = %s", position);
        mNextPosition = position;

        if (mOnPageChangeListener != null)
            mOnPageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        XLog.d("onPageScrollStateChanged---state = %s", (state == ViewPager.SCROLL_STATE_DRAGGING ? "SCROLL_STATE_DRAGGING" : (state == ViewPager.SCROLL_STATE_IDLE ? "SCROLL_STATE_IDLE" : "SCROLL_STATE_SETTLING")));
        if (state == ViewPager.SCROLL_STATE_IDLE && mCurrentPosition != mNextPosition) {
            XLog.d("onRealVisible positon = %s", mNextPosition);
            Fragment current = mBaseFragmentPagerAdapter.getItem(mCurrentPosition);
            Fragment next = mBaseFragmentPagerAdapter.getItem(mNextPosition);
            if (current instanceof BasePagerFragment)
                ((BasePagerFragment) current).onRealVisible(false);
            if (next instanceof BasePagerFragment)
                ((BasePagerFragment) next).onRealVisible(true);
            mCurrentPosition = mNextPosition;
            if (mOnRealPositionListener != null) {
                mOnRealPositionListener.onRealPosition(mNextPosition);
            }
        }

        if (mOnPageChangeListener != null)
            mOnPageChangeListener.onPageScrollStateChanged(state);
    }

    public void reset() {
        mCurrentPosition = 0;
        mNextPosition = 0;
    }

    public interface OnRealPositionListener {

        void onRealPosition(int position);

    }

}
