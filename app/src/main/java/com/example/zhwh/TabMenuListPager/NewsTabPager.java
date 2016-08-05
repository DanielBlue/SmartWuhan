package com.example.zhwh.TabMenuListPager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.zhwh.BaseTab;
import com.example.zhwh.NewsMenu;
import com.example.zhwh.R;
import com.example.zhwh.utils.Constant;
import com.example.zhwh.utils.OkHttpManager;
import com.example.zhwh.utils.SpUtils;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 菜单标签页的新闻页
 */
public class NewsTabPager extends BaseTabPager {
    List<NewsMenu.NewsTitleData> mNewsTitleData ;
    List<BaseTab> mTabTitleData;
    private String tag = "NewsTabPager";
    private ViewPager mViewpager;
    private PagerSlidingTabStrip mTab;
    private String mJsonData;
    private NewsMenu mNewsMenu;
    private BaseTab mBaseTab;

    public NewsTabPager(Activity activity) {
        super(activity);
        initData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.menu_news_tab,null);
        mViewpager = (ViewPager) view.findViewById(R.id.vp_news_tab);
        mTab = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        mTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mMainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                }else {
                    mMainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void initData() {
        mJsonData = SpUtils.getString(mActivity, Constant.JSON_FROM_SERVICER);
        if(TextUtils.isEmpty(mJsonData)){
            OkHttpManager httpManager = new OkHttpManager();
            httpManager.send(Constant.CATEGORY_URL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(tag,"接收失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(tag,"接收成功");
                    mJsonData = response.body().string();
                    SpUtils.setString(mActivity,Constant.JSON_FROM_SERVICER,mJsonData);
                }
            });
        }
        Gson gson = new Gson();
        mNewsMenu = gson.fromJson(mJsonData,NewsMenu.class);
        mNewsTitleData = mNewsMenu.data.get(0).children;
        mTabTitleData = new ArrayList<>();
        for(int x=0;x<mNewsTitleData.size();x++){
            mBaseTab = new BaseTab(mActivity,mNewsTitleData.get(x));
            mBaseTab.initData();
            Log.d(tag,mNewsTitleData.get(x).title);
            mTabTitleData.add(mBaseTab);
        }
        mViewpager.setAdapter(new NewsTabAdapter());
        mTab.setViewPager(mViewpager);
    }

    private class NewsTabAdapter extends PagerAdapter {
        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTitleData.get(position).title;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mTabTitleData.get(position).mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mTabTitleData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
