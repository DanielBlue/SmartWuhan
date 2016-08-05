package com.example.zhwh.TabPager;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zhwh.R;
import com.example.zhwh.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by 毛琦 on 2016/7/29.
 */
public class BasePager {
    public Activity mActivity;
    public TextView mTitle;
    public ImageButton mBtn;
    public FrameLayout mFlContent;
    public View mRootView;

    public BasePager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager,null);
        mTitle = (TextView) view.findViewById(R.id.tv_title);
        mBtn = (ImageButton) view.findViewById(R.id.btn_open_menu);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) mActivity;
                SlidingMenu slidingMenu = activity.getSlidingMenu();
                slidingMenu.toggle();
            }
        });
        mFlContent = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    public void initData(){}

}
