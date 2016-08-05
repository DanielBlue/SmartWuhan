package com.example.zhwh.TabPager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.zhwh.NewsMenu;
import com.example.zhwh.TabMenuListPager.NewsTabPager;
import com.example.zhwh.activity.MainActivity;
import com.example.zhwh.fragment.MenuFragment;
import com.example.zhwh.utils.Constant;
import com.example.zhwh.utils.OkHttpManager;
import com.example.zhwh.utils.SpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 毛琦 on 2016/7/29.
 */
public class NewsPager extends BasePager {
    public final String tag = "NewsPager";
    public NewsMenu mNewsMenu;
    private String mJsonData;

    public NewsPager(Activity activity) {
        super(activity);
        initData();
    }

    @Override
    public void initData() {
        mTitle.setText("News");
        mBtn.setVisibility(View.VISIBLE);
        mJsonData = SpUtils.getString(mActivity,Constant.JSON_FROM_SERVICER);
        Log.d(tag,mJsonData);
        if(TextUtils.isEmpty(mJsonData)) {
            getDataFromServicer();
        }else {
            resolveData(mJsonData);
        }
        while (mNewsMenu==null){}
        MenuFragment leftMenuFragment = ((MainActivity) mActivity).findLeftMenuFragment();
        leftMenuFragment.setMenuData(mNewsMenu.data);
        mFlContent.addView(new NewsTabPager(mActivity).mRootView);
    }

    private void getDataFromServicer() {
        OkHttpManager httpManager = new OkHttpManager();
        httpManager.send(Constant.CATEGORY_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(tag,"接收失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(tag,"接收成功");
                String rezult = response.body().string();
                Log.d(tag,rezult);
                SpUtils.setString(mActivity,Constant.JSON_FROM_SERVICER, rezult);
                resolveData(rezult);
            }
        });
    }

    private void resolveData (String data) {
        Gson gson = new Gson();
        Log.d(tag,data);
        mNewsMenu = gson.fromJson(data,NewsMenu.class);
        System.out.println(mNewsMenu);
    }
}
