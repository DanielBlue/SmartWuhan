package com.example.zhwh.TabPager;

import android.app.Activity;
import android.view.View;

/**
 * Created by 毛琦 on 2016/7/29.
 */
public class ServicePager extends BasePager {

    public ServicePager(Activity activity) {
        super(activity);
        initData();
    }

    @Override
    public void initData() {
        mTitle.setText("SmartService");
        mBtn.setVisibility(View.VISIBLE);
    }
}
