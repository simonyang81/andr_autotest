package com.icheer.autotest.together.ui.splash.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icheer.autotest.together.R;
import com.icheer.autotest.together.databinding.FragmentSplashBinding;
import com.icheer.autotest.together.ui.base.view.BaseFragment;
import com.icheer.autotest.together.ui.auth.view.AuthFragment;
import com.icheer.autotest.together.ui.splash.viewmodel.CheckProgress;
import com.icheer.autotest.together.ui.splash.viewmodel.SplashViewModel;

/**
 * 启动页Fragment
 * 显示应用启动画面，模拟加载进度，完成后自动跳转到首页
 * 使用 {@link SplashFragment#newInstance} 工厂方法创建实例
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class SplashFragment extends BaseFragment {

    /**
     * 总的启动时间（毫秒）
     */
//    private static final int TOTAL_SPLASH_TIME = 3000;
    private static final String TAG = "SplashFragment";

    private FragmentSplashBinding binding;

    private SplashViewModel splashViewModel;

    /**
     * 用于进度更新的Handler
     */
    private Handler progressHandler;
    
    /**
     * 进度更新的Runnable
     */
    private Runnable progressRunnable;
    
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载启动页布局
        binding = FragmentSplashBinding.inflate(inflater, container, false);

        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        
        // 初始化UI组件
        initViews();

        
//        // 开始进度动画
//        startProgressAnimation();

        // 观察ViewModel的LiveData
        observeViewModel();
        
        return binding.getRoot();
    }

    /**
     * 初始化视图组件
     */
    private void initViews() {
        // 设置初始状态
//        binding.progressBar.setProgress(0);
//        binding.tvLoading.setText(loadingTexts[0]);

        splashViewModel.checkDeviceState();

    }




    /**
     * 开始进度动画
     * 模拟应用加载过程，动态更新进度条和文字
     */
//    private void startProgressAnimation() {
//        progressHandler = new Handler(Looper.getMainLooper());
//        progressRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (currentProgress < 100) {
//                    // 更新进度条
//                    currentProgress++;
//                    binding.progressBar.setProgress(currentProgress);
//
//                    // 根据进度更新加载文字
//                    updateLoadingText();
//
//                    // 继续更新
//                    progressHandler.postDelayed(this, TOTAL_SPLASH_TIME / 100);
//                } else {
//                    // 进度完成，跳转到首页
//                    navigateToHome();
//                }
//            }
//        };
//
//        // 开始执行进度更新
//        progressHandler.post(progressRunnable);
//    }

    /**
     * 根据当前进度更新加载文字
     */
    private void updateLoadingText() {
        int textIndex = Math.min(currentProgress / 17, loadingTexts.length - 1);
        String loadingMessage = loadingTexts[textIndex];
        
        // 显示当前进度百分比
        String displayText = loadingMessage + " " + currentProgress + "%";
        binding.tvLoading.setText(displayText);
    }

    /**
     * 跳转到首页Fragment
     */
    private void navigateToHome() {
        if (getActivity() != null && isAdded()) {
            try {
                AuthFragment loginFragment = new AuthFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.commit();
                
                // 显示ActionBar（在跳转到首页时）
                showActionBar();
            } catch (Exception e) {
                Log.e("SplashFragment", "Navigation error: " + e.getMessage());
            }
        }
    }

    private void observeViewModel() {

        splashViewModel.getProductKey().observe(getViewLifecycleOwner(), productKey -> {
            Log.d(TAG, "The product key: " + productKey);
        });

        splashViewModel.getNetworkState().observe(getViewLifecycleOwner(), networkState -> {
            Log.d(TAG, "The network state: " + networkState);
        });

        splashViewModel.getProductState().observe(getViewLifecycleOwner(), productState -> {
            Log.d(TAG, "The product state: " + productState);
        });

        splashViewModel.getServerState().observe(getViewLifecycleOwner(), serverState -> {
            Log.d(TAG, "The server state: " + serverState);
        });

        splashViewModel.getCheckProgress().observe(getViewLifecycleOwner(), progressArray -> {

            // 更新四个进度段的显示
            updateSegment(binding.progressSegment1, progressArray[0]); // 产品密钥
            updateSegment(binding.progressSegment2, progressArray[1]); // 网络连接
            updateSegment(binding.progressSegment3, progressArray[2]); // 系统状态
            updateSegment(binding.progressSegment4, progressArray[3]); // 服务器通信

        });

    }

    private void updateSegment(View segment, CheckProgress progress) {
        switch (progress) {
            case INIT:
                segment.setBackgroundResource(R.drawable.progress_segment_background);
                break;
            case CHECKING:
                segment.setBackgroundResource(R.drawable.progress_segment_checking);
                break;
            case SUCCESS:
                segment.setBackgroundResource(R.drawable.progress_segment_success);
                break;
            case FAILED:
                segment.setBackgroundResource(R.drawable.progress_segment_error);
                break;
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

        // 当视图被销毁时，取消延迟任务
        if (progressHandler != null && progressRunnable != null) {
            progressHandler.removeCallbacks(progressRunnable);
        }
    }
}