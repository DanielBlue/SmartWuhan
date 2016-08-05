package com.example.zhwh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhwh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 新手引导页
 */
public class GuideActivity extends Activity {

    private ViewPager mViewPager;
    private int[] mImageId = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private List<ImageView> mImageList;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initData();
        mBtn = (Button) findViewById(R.id.bt_start);
        mBtn.setVisibility(View.INVISIBLE);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        mViewPager.setAdapter(new MyAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position ==2){
                    mBtn.setVisibility(View.VISIBLE);
                }else{
                    mBtn.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 将3个ImageView存入mImageList
     */
    private void initData() {
        mImageList = new ArrayList<>();
        for(int x =0;x<mImageId.length;x++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageId[x]);
            mImageList.add(imageView);
        }
    }

    private class MyAdapter extends PagerAdapter {
        //初始化item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mImageList.get(position);
            container.addView(view);
            return view;
        }
        //item的数量
        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        //销毁item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageList.get(position));
        }
    }
}
