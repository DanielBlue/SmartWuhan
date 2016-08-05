package com.example.zhwh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.zhwh.R;

/**
 * 欢迎页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.layout_splash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        alphaAnimation.setDuration(1000);
        scaleAnimation.setDuration(1000);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        rl.setAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(),GuideActivity.class);
                startActivity(intent);
                finish();
//                if(SpUtils.getBoolean(getApplicationContext(), Constant.IS_FIRST_ENTER)){
//                    Intent intent = new Intent(getApplicationContext(),GuideActivity.class);
//                    startActivity(intent);
//                    SpUtils.setBoolean(getApplicationContext(), Constant.IS_FIRST_ENTER,false);
//                    finish();
//                }else{
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
