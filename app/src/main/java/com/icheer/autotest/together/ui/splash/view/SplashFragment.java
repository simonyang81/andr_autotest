package com.icheer.autotest.together.ui.splash.view;

import android.os.Bundle;
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

    private static final String TAG = "SplashFragment";

    private FragmentSplashBinding binding;

    private SplashViewModel splashViewModel;

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

        // 设置监听
        setupClickListeners();

        // 观察ViewModel的LiveData
        observeViewModel();
        
        return binding.getRoot();
    }

    /**
     * 初始化视图组件
     */
    private void initViews() {
        // 设置初始状态
        splashViewModel.checkDeviceState();

    }

    private void setupClickListeners() {
        binding.btnSettings.setOnClickListener(v -> {
            SettingsDialogFragment settingsDialogFragment = SettingsDialogFragment.newInstance();
            settingsDialogFragment.show(getParentFragmentManager(), "SettingsDialog");
        });
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, "onDestroyView()");

    }
}