package com.example.zhwh.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.zhwh.R;
import com.example.zhwh.TabPager.BasePager;
import com.example.zhwh.TabPager.GovPager;
import com.example.zhwh.TabPager.HomePager;
import com.example.zhwh.TabPager.NewsPager;
import com.example.zhwh.TabPager.ServicePager;
import com.example.zhwh.TabPager.SettingPager;
import com.example.zhwh.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容展示区Fragment
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{
    private ViewPager mPager;
    private List<BasePager> mPagerList;
    private RadioButton mHome;
    private RadioButton mNews;
    private RadioButton mService;
    private RadioButton mGov;
    private RadioButton mSetting;
    public NewsPager mNewsPager;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.frag_main,null);
        initData();
        mHome = (RadioButton) view.findViewById(R.id.btn_home);
        mNews = (RadioButton) view.findViewById(R.id.btn_news);
        mService = (RadioButton) view.findViewById(R.id.btn_service);
        mGov = (RadioButton) view.findViewById(R.id.btn_gov);
        mSetting = (RadioButton) view.findViewById(R.id.btn_setting);
        mHome.setOnClickListener(this);
        mNews.setOnClickListener(this);
        mService.setOnClickListener(this);
        mGov.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mPager = (ViewPager) view.findViewById(R.id.vp_content_pager);
        mPager.setAdapter(new MyAdapter());
        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<>();
        mNewsPager = new NewsPager(mActivity);
        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(mNewsPager);
        mPagerList.add(new ServicePager(mActivity));
        mPagerList.add(new GovPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_home:
                mPager.setCurrentItem(0,false);
                showSlidingBtn(false);
                break;
            case R.id.btn_news:
                mPager.setCurrentItem(1,false);
                showSlidingBtn(true);
                break;
            case R.id.btn_service:
                mPager.setCurrentItem(2,false);
                showSlidingBtn(true);
                break;
            case R.id.btn_gov:
                mPager.setCurrentItem(3,false);
                showSlidingBtn(true);
                break;
            case R.id.btn_setting:
                mPager.setCurrentItem(4,false);
                showSlidingBtn(false);
                break;
            default:
                break;

        }
    }

    private void showSlidingBtn(Boolean b) {
        MainActivity activity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = activity.getSlidingMenu();
        if(b){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mPagerList.get(position).mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
