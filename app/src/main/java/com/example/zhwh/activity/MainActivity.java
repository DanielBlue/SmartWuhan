package com.example.zhwh.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.zhwh.R;
import com.example.zhwh.fragment.MainFragment;
import com.example.zhwh.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 主界面
 */
public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        setBehindContentView(R.layout.menu_left);
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindWidth(500);
    }
    /**
     * 初始化Fragment
     */

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_main,new MainFragment(),"MainFragment");
        transaction.replace(R.id.fl_left_menu,new MenuFragment(),"MenuFragment");
        transaction.commit();
    }

    public MenuFragment findLeftMenuFragment(){
        FragmentManager manager = getSupportFragmentManager();
        MenuFragment fragment = (MenuFragment) manager.findFragmentByTag("MenuFragment");
        return fragment;
    }

    public MainFragment findMainFragment(){
        FragmentManager manager = getSupportFragmentManager();
        MainFragment fragment = (MainFragment) manager.findFragmentByTag("MainFragment");
        return fragment;
    }
}
