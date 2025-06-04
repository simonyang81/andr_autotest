package com.icheer.autotest.together.ui.splash.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icheer.autotest.together.R;
import com.icheer.autotest.together.ui.fragments.HomeFragment;

/**
 * 启动页Fragment
 * 显示应用启动画面，模拟加载进度，完成后自动跳转到首页
 * 使用 {@link SplashFragment#newInstance} 工厂方法创建实例
 */
public class SplashFragment extends Fragment {

    /**
     * 总的启动时间（毫秒）
     */
    private static final int TOTAL_SPLASH_TIME = 3000;
    private static final String TAG = "SplashFragment";

    /**
     * 用于进度更新的Handler
     */
    private Handler progressHandler;
    
    /**
     * 进度更新的Runnable
     */
    private Runnable progressRunnable;
    
    /**
     * 进度条组件
     */
    private ProgressBar progressBar;
    
    /**
     * 加载文字组件
     */
    private TextView loadingText;
    
    /**
     * 当前进度值
     */
    private int currentProgress = 0;
    
    /**
     * 加载文字数组
     */
    private String[] loadingTexts = {
        "初始化系统...",
        "加载配置文件...",
        "连接数据库...",
        "检查网络连接...",
        "加载用户界面...",
        "启动完成！"
    };

    public SplashFragment() {
        Log.d("SplashFragment", "SplashFragment constructor");
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

        // 隐藏状态栏，设置全屏模式
        hideStatusBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载启动页布局
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        
        // 初始化UI组件
        initViews(view);
        
        // 开始进度动画
        startProgressAnimation();
        
        return view;
    }

    /**
     * 初始化视图组件
     *
     * @param view 根视图
     */
    private void initViews(View view) {
        progressBar = view.findViewById(R.id.progress_bar);
        loadingText = view.findViewById(R.id.tv_loading);
        
        // 设置初始状态
        if (progressBar != null) {
            progressBar.setProgress(0);
        }
        if (loadingText != null) {
            loadingText.setText(loadingTexts[0]);
        }
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
     * 开始进度动画
     * 模拟应用加载过程，动态更新进度条和文字
     */
    private void startProgressAnimation() {
        progressHandler = new Handler(Looper.getMainLooper());
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentProgress < 100) {
                    // 更新进度条
                    currentProgress++;
                    if (progressBar != null) {
                        progressBar.setProgress(currentProgress);
                    }
                    
                    // 根据进度更新加载文字
                    updateLoadingText();
                    
                    // 继续更新
                    progressHandler.postDelayed(this, TOTAL_SPLASH_TIME / 100);
                } else {
                    // 进度完成，跳转到首页
                    navigateToHome();
                }
            }
        };
        
        // 开始执行进度更新
        progressHandler.post(progressRunnable);
    }

    /**
     * 根据当前进度更新加载文字
     */
    private void updateLoadingText() {
        int textIndex = Math.min(currentProgress / 17, loadingTexts.length - 1);
        String loadingMessage = loadingTexts[textIndex];
        
        // 显示当前进度百分比
        String displayText = loadingMessage + " " + currentProgress + "%";
        if (loadingText != null) {
            loadingText.setText(displayText);
        }
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
//                transaction.addToBackStack(null); // 添加到回退栈
                transaction.commit();
                
                // 显示ActionBar（在跳转到首页时）
                showActionBar();
            } catch (Exception e) {
                Log.e("SplashFragment", "Navigation error: " + e.getMessage());
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

    /**
     * 隐藏状态栏，设置全屏显示
     */
    private void hideStatusBar() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            // 设置全屏模式，隐藏状态栏和导航栏
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 显示状态栏，恢复正常显示
     */
    private void showStatusBar() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            // 恢复正常显示模式
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy()");

        // 清理Handler和Runnable，防止内存泄漏
        if (progressHandler != null && progressRunnable != null) {
            progressHandler.removeCallbacks(progressRunnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, "onDestroyView()");

        // 恢复状态栏显示
        showStatusBar();

        // 当视图被销毁时，取消延迟任务
        if (progressHandler != null && progressRunnable != null) {
            progressHandler.removeCallbacks(progressRunnable);
        }
    }
}