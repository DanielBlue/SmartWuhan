package com.example.zhwh;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhwh.TabMenuListPager.BaseTabPager;
import com.example.zhwh.activity.NewsDetailActivity;
import com.example.zhwh.utils.Constant;
import com.example.zhwh.utils.OkHttpManager;
import com.example.zhwh.utils.SpUtils;
import com.example.zhwh.utils.ToastUtils;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 毛琦 on 2016/8/2.
 */
public class BaseTab extends BaseTabPager implements SwipeRefreshLayout.OnRefreshListener{
    private static final int LAYOUT_REFRESH = 100;
    private static final int PIC_SCROLL = 101;
    private String mJsonData;
    private ViewPager mViewPager;
    private ArrayList<TopNews.TopImage> mTopImage;
    private ArrayList<TopNews.NewsItem> mNewsItem;
    private ArrayList<String> mImageTitle;
    private String tag = "BaseTab";
    private NewsMenu.NewsTitleData mNewsTitleData;
    private TopNews mTopNews;
    private TextView mTextView;
    private CirclePageIndicator mPageIndicator;
    private ListView mListView;
    private View mHeaderView;
    private SwipeRefreshLayout mRefreshLayout;
    private Handler mHandler;
    private NewsAdapter mNewsAdapter;

    public BaseTab(Activity activity, NewsMenu.NewsTitleData newsTitleData) {
        super(activity);
        mNewsTitleData = newsTitleData;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity,R.layout.news_detail_tab,null);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        mHeaderView = View.inflate(mActivity,R.layout.list_header_item,null);
        mTextView = (TextView) mHeaderView.findViewById(R.id.tv_image_title);
        mViewPager = (ViewPager) mHeaderView.findViewById(R.id.vp_news_detail);
        mPageIndicator = (CirclePageIndicator) mHeaderView.findViewById(R.id.image_indicator);
        mListView = (ListView) view.findViewById(R.id.lv_news);
        return view;
    }
    @Override
    public void initData() {
        mJsonData = SpUtils.getString(mActivity,Constant.TOP_NEWS);
        if(TextUtils.isEmpty(mJsonData)){
            String url = Constant.BASE_URL+ mNewsTitleData.url;
            getDataFromServicer(url);
        }else {
            resolveJson();
        }

        while(mTopNews==null){}
        mTopImage = mTopNews.data.topnews;
        mNewsItem = mTopNews.data.news;
        mImageTitle = new ArrayList<>();
        for(int x=0;x<mTopImage.size();x++){
            mImageTitle.add(mTopImage.get(x).title);
        }
        mTextView.setText(mImageTitle.get(0));
        mViewPager.setAdapter(new TopNewsAdapter());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Message message_cancel = new Message();
                        message_cancel.what = PIC_SCROLL;
                        mHandler.sendMessageDelayed(message_cancel,3000);
                        break;
                    case MotionEvent.ACTION_UP:
                        Message message_up = new Message();
                        message_up.what = PIC_SCROLL;
                        mHandler.sendMessageDelayed(message_up,3000);
                        break;
                }
                return true;
            }
        });
        mPageIndicator.setViewPager(mViewPager);
        mPageIndicator.setSnap(true);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                                            android.R.color.holo_orange_light,
                                            android.R.color.holo_green_light,
                                            android.R.color.holo_blue_bright);
        mRefreshLayout.setOnRefreshListener(this);
        mNewsAdapter = new NewsAdapter();
        mListView.setAdapter(mNewsAdapter);
        mListView.addHeaderView(mHeaderView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SpUtils.setBoolean(mActivity,""+(i-1),false);
                System.out.println("第"+(i-1)+"个被点击了");
                TextView textView = (TextView) view.findViewById(R.id.list_item_title);
                textView.setTextColor(Color.parseColor("#8E8E8E"));
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                String url = mNewsItem.get(0).url;
                intent.putExtra("url",url);
                mActivity.startActivity(intent);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTextView.setText(mImageTitle.get(position));
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
        if(mHandler ==null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case LAYOUT_REFRESH:
                            ToastUtils.show(mActivity, "刷新成功");
                            break;
                        case PIC_SCROLL:
                            int x = mViewPager.getCurrentItem();
                            if(x==mTopImage.size()-1){
                                x=-1;
                            }
                            mViewPager.setCurrentItem(++x);
                            Message message = new Message();
                            message.what = PIC_SCROLL;
                            mHandler.sendMessageDelayed(message,3000);
                            break;
                    }
                }
            };
            Message message = new Message();
            message.what = PIC_SCROLL;
            mHandler.sendMessageDelayed(message,3000);
        }

    }

    private void resolveJson() {
        Gson gson = new Gson();
        mTopNews = gson.fromJson(mJsonData,TopNews.class);
        System.out.println(mTopNews);
    }

    public void getDataFromServicer(String url) {
        OkHttpManager httpManager = new OkHttpManager();
        httpManager.send(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(tag,"接受失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mJsonData = response.body().string();
                Log.d(tag,"接受成功");
                Log.d(tag,mJsonData);
                SpUtils.setString(mActivity,Constant.TOP_NEWS,mJsonData);
                resolveJson();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                Message message = new Message();
                message.what = LAYOUT_REFRESH;
                mHandler.sendMessage(message);
            }
        },2000);

    }

    private class TopNewsAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(mActivity).load(mTopImage.get(position).topimage).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mTopImage.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class NewsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mNewsItem.size();
        }

        @Override
        public TopNews.NewsItem getItem(int i) {
            return mNewsItem.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if(view ==null){
                holder = new ViewHolder();
                view = View.inflate(mActivity,R.layout.news_list_item,null);
                holder.list_item_image = (ImageView) view.findViewById(R.id.list_item_image);
                holder.list_item_title = (TextView) view.findViewById(R.id.list_item_title);
                holder.list_item_time = (TextView) view.findViewById(R.id.list_item_time);
                if(!SpUtils.getBoolean(mActivity,i+"")){
                    holder.list_item_title.setTextColor(Color.parseColor("#8E8E8E"));
                }else {
                    holder.list_item_title.setTextColor(Color.BLACK);
                }
                view.setTag(holder);
            }else {
                holder = (ViewHolder) view.getTag();
            }
            Picasso.with(mActivity).load(getItem(i).listimage).into(holder.list_item_image);
            holder.list_item_title.setText(getItem(i).title);
            holder.list_item_time.setText(getItem(i).pubdate);
            return view;
        }
    }
    class ViewHolder{
        public ImageView list_item_image;
        public TextView list_item_title;
        public TextView list_item_time;
    }
}
