package com.example.zhwh.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhwh.NewsMenu;
import com.example.zhwh.R;
import com.example.zhwh.TabMenuListPager.DoWithTabPager;
import com.example.zhwh.TabMenuListPager.NewsTabPager;
import com.example.zhwh.TabMenuListPager.PicTabPager;
import com.example.zhwh.TabMenuListPager.SpecialTabPager;
import com.example.zhwh.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧边栏Fragment
 */
public class MenuFragment extends BaseFragment {
    List<String> listData = new ArrayList<>();
    private final String tag = "MenuFragment";
    private ListView mListView;
    private int currentPos;
    private MyMenuAdapter mMenuAdapter;
    private MainActivity activity;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.frag_left_menu,null);
        mListView = (ListView) view.findViewById(R.id.lv_news_menu);
        mMenuAdapter = new MyMenuAdapter();
        mListView.setAdapter(mMenuAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentPos = i;
                mMenuAdapter.notifyDataSetChanged();
                activity =  (MainActivity) mActivity;
                SlidingMenu menu = activity.getSlidingMenu();
                menu.toggle();
                setCurrentMenuPager(activity,i);
            }
        });
        return view;
    }

    private void setCurrentMenuPager(MainActivity activity,int position) {
        MainFragment fragment =  activity.findMainFragment();
        FrameLayout fl = activity.findMainFragment().mNewsPager.mFlContent;
        TextView title = fragment.mNewsPager.mTitle;
        fl.removeAllViews();
        switch (position){
            case 0:
                fl.addView(new NewsTabPager(mActivity).mRootView);
                title.setText("News");
                break;
            case 1:
                fl.addView(new SpecialTabPager(mActivity).mRootView);
                title.setText("专题");
                break;
            case 2:
                fl.addView(new PicTabPager(mActivity).mRootView);
                title.setText("组图");
                break;
            case 3:
                fl.addView(new DoWithTabPager(mActivity).mRootView);
                title.setText("互动");
                break;
            default:
                break;
        }

    }

    @Override
    public void initData() {

    }

    public void setMenuData(List<NewsMenu.NewsMenuData> data){
        for(int x=0;x<data.size();x++){
            listData.add(data.get(x).title);
            Log.d(tag,data.get(x).title);
        }
    }

    private class MyMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public String getItem(int i) {
            Log.d(tag,listData.get(i));
            return listData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view1, ViewGroup viewGroup) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.news_list_menu_item,null,false);
            TextView text = (TextView) view.findViewById(R.id.tv_list_menu_title);
            if(i ==currentPos){
                text.setEnabled(true);
            }else{
                text.setEnabled(false);
            }
            text.setText(getItem(i));
            return view;
        }
    }
}
