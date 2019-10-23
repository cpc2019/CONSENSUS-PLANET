package com.pingan.baselibs.widget;

import android.support.v4.app.Fragment;

public class TabEntity {
    public String text;
    public Fragment fragment;

    public int buyType;//add by jadon

    public TabEntity(String text, Fragment fragment) {
        this.text = text;
        this.fragment = fragment;
    }

    public TabEntity(String text, Fragment fragment, int buyType) {
        this.text = text;
        this.fragment = fragment;
        this.buyType = buyType;
    }
}
