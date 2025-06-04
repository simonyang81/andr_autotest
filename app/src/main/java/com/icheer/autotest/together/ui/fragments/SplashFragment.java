package com.icheer.autotest.together.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icheer.autotest.together.R;

/**
 * 启动页Fragment
 * 显示应用启动画面，延迟2秒后自动跳转到首页
 * 使用 {@link SplashFragment#newInstance} 工厂方法创建实例
 */
public class SplashFragment extends Fragment {

    /**
     * 延迟跳转时间（毫秒）
     */
    private static final int SPLASH_DELAY = 2000;
    
    /**
     * 用于延迟跳转的Handler
     */
    private Handler splashHandler;
    
    /**
     * 延迟跳转的Runnable
     */
    private Runnable splashRunnable;

    public SplashFragment() {
        // Required empty public constructor
    }

    /**
     * 使用工厂方法创建SplashFragment实例
     *
     * @return SplashFragment的新实例
     */
    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 隐藏ActionBar标题栏
        hideActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载启动页布局
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        
        // 初始化延迟跳转逻辑
        initSplashTimer();
        
        return view;
    }

    /**
     * 隐藏ActionBar标题栏
     */
    private void hideActionBar() {
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    /**
     * 初始化启动页定时器
     * 设置2秒后自动跳转到首页
     */
    private void initSplashTimer() {
        splashHandler = new Handler(Looper.getMainLooper());
        splashRunnable = new Runnable() {
            @Override
            public void run() {
                navigateToHome();
            }
        };
        
        // 延迟执行跳转
        splashHandler.postDelayed(splashRunnable, SPLASH_DELAY);
    }

    /**
     * 跳转到首页Fragment
     */
    private void navigateToHome() {
        if (getActivity() != null && isAdded()) {
            try {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.addToBackStack(null); // 添加到回退栈
                transaction.commit();
                
                // 显示ActionBar（在跳转到首页时）
                showActionBar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示ActionBar标题栏
     */
    private void showActionBar() {
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 清理Handler和Runnable，防止内存泄漏
        if (splashHandler != null && splashRunnable != null) {
            splashHandler.removeCallbacks(splashRunnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 当视图被销毁时，取消延迟任务
        if (splashHandler != null && splashRunnable != null) {
            splashHandler.removeCallbacks(splashRunnable);
        }
    }
}