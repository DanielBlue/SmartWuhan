package com.example.zhwh.TabMenuListPager;

import android.app.Activity;
import android.view.View;

import com.example.zhwh.activity.MainActivity;

/**
 * 菜单标签页的基类
 */
public abstract class BaseTabPager {


    public Activity mActivity;
    public View mRootView;
    public MainActivity mMainActivity;

    public BaseTabPager(Activity activity){
        mMainActivity = (MainActivity) activity;
        mActivity = activity;
        mRootView = initView();
    }

    public abstract View initView();

    public abstract void initData();
}
