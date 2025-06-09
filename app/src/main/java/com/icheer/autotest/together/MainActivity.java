package com.icheer.autotest.together;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;

import com.icheer.autotest.together.ui.splash.view.SplashFragment;

/**
 * 主Activity
 * 使用Fragment架构和底部导航进行页面管理
 */
public class MainActivity extends BaseActivity {

//    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化组件
        initViews();
        
        // 默认显示SplashFragment，并隐藏ActionBar
        if (savedInstanceState == null) {
            hideActionBar(); // 启动时隐藏ActionBar
            loadFragment(new SplashFragment());
        }
    }

    /**
     * 初始化视图组件
     */
    private void initViews() {
//        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * 隐藏ActionBar
     */
    public void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 显示ActionBar
     */
    public void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    /**
     * 加载Fragment到容器中
     * @param fragment 要加载的Fragment
     */
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    /**
     * 加载Fragment到容器中，并控制ActionBar显示
     * @param fragment 要加载的Fragment
     * @param showActionBar 是否显示ActionBar
     */
    public void loadFragment(Fragment fragment, boolean showActionBar) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        
        // 控制ActionBar显示
        if (showActionBar) {
            showActionBar();
        } else {
            hideActionBar();
        }
    }
} 