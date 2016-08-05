package com.example.zhwh.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.zhwh.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends Activity implements View.OnClickListener{

    private WebView mWebView;
    private String mUrl;
    private ProgressBar mProgressBar;
    private int mCurrentItem;
    private int mEndItem = 2;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ShareSDK.initSDK(this,"15bc0e2ebdad0");
        initView();
        initData();
        mUrl = getIntent().getStringExtra("url");
        mWebView.loadUrl(mUrl);
    }

    private void initData() {

    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
        mWebView = (WebView) findViewById(R.id.wv_news_detail);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("开始加载网页");
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view,url);
                System.out.println("加载网页完成");
                mProgressBar.setVisibility(View.INVISIBLE);
            }

        });
        mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        ImageButton btn_back = (ImageButton) findViewById(R.id.btn_back);
        ImageButton btn_textsize = (ImageButton) findViewById(R.id.btn_textsize);
        ImageButton btn_share = (ImageButton) findViewById(R.id.btn_share);
        btn_back.setOnClickListener(this);
        btn_textsize.setOnClickListener(this);
        btn_share.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_textsize:
                showTextSizeDialog();
                break;
            case R.id.btn_share:
                showShare();
                break;
        }
    }

    private void showTextSizeDialog() {
        String[] textSize = new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体",};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(textSize, mEndItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mCurrentItem = i;
                System.out.println("选中的是"+i);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEndItem = mCurrentItem;
                switch (mCurrentItem){
                    case 0:
                        mSettings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        mSettings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        mSettings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        mSettings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        mSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
