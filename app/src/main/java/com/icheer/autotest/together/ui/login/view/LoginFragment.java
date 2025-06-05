package com.icheer.autotest.together.ui.login.view;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.icheer.autotest.together.R;
import com.icheer.autotest.together.data.manager.UserSessionManager;
import com.icheer.autotest.together.databinding.FragmentLoginBinding;
import com.icheer.autotest.together.ui.base.view.BaseFragment;
import com.icheer.autotest.together.ui.fragments.HomeFragment;
import com.icheer.autotest.together.ui.login.viewmodel.LoginViewModel;

/**
 * 登录界面Fragment
 * 实现用户登录功能的UI展示
 * 采用MVVM架构模式
 * 
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;
    
    /** 密码是否可见的状态标志 */
    private boolean isPasswordVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化会话管理器
        UserSessionManager sessionManager = UserSessionManager.getInstance(requireContext());

        // 检查是否已登录
//        if (sessionManager.isLoggedIn()) {
//            // 如果已登录，直接跳转到主页
//            navigateToHome();
//            return;
//        }

        // 隐藏ActionBar标题栏
        hideActionBar();

        // 隐藏状态栏，设置全屏模式
        hideStatusBar();
    }

    /**
     * 创建Fragment视图
     *
     * @param inflater           布局加载器
     * @param container          父容器
     * @param savedInstanceState 保存的实例状态
     * @return 创建的视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // 初始化ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // 设置点击事件监听器
        setupClickListeners();

        // 观察ViewModel的LiveData
        observeViewModel();

        return binding.getRoot();
    }

    /**
     * 观察ViewModel的LiveData变化
     */
    private void observeViewModel() {
        // 观察加载状态
        loginViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.btnLogin.setEnabled(!isLoading);
                binding.btnLogin.setText(isLoading ? "登录中..." : "登录");
                binding.loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        // 观察用户名错误
        loginViewModel.getUsernameError().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && getContext() != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // 观察用户密码错误
        loginViewModel.getPasswordError().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && getContext() != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // 观察通用错误信息（网络错误、业务错误等）
        loginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && getContext() != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // 观察登录成功状态
        loginViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess != null) {
                if (isSuccess) {
                    // 处理登录成功
                    onLoginSuccessful();
                }
                // 失败的情况已经在errorMessage中处理了
            }
        });
    }

    /**
     * 处理登录成功
     */
    private void onLoginSuccessful() {
        Log.d(TAG, "登录成功，准备跳转到主页");

        // 显示成功提示
        if (getContext() != null) {
            Toast.makeText(getContext(), "登录成功！欢迎使用", Toast.LENGTH_SHORT).show();
        }

        // 延迟跳转，让用户看到成功提示
        binding.getRoot().postDelayed(this::navigateToHome, 500);
    }

    /**
     * 跳转到主页
     */
    private void navigateToHome() {
        if (getActivity() != null && isAdded()) {
            try {
                // 创建主页Fragment
                HomeFragment homeFragment = new HomeFragment();

                // 执行Fragment切换
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.commit();

                Log.d(TAG, "成功跳转到主页");

            } catch (Exception e) {
                Log.e(TAG, "跳转到主页失败: " + e.getMessage());
                if (getContext() != null) {
                    Toast.makeText(getContext(), "页面跳转失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 设置点击事件监听器
     * 为各个交互元素设置点击处理逻辑
     */
    private void setupClickListeners() {
        // 登录按钮点击事件
        binding.btnLogin.setOnClickListener(v -> onLoginButtonClicked());
        
        // 忘记密码链接点击事件
        binding.tvForgotPassword.setOnClickListener(v -> onForgotPasswordClicked());
        
        // 创建账号链接点击事件
        binding.tvCreateAccount.setOnClickListener(v -> onCreateAccountClicked());
        
        // 密码显示/隐藏切换按钮点击事件
        binding.ivPasswordToggle.setOnClickListener(v -> togglePasswordVisibility());
    }

    /**
     * 切换密码的显示和隐藏状态
     */
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // 当前密码可见，切换为隐藏
            hidePassword();
        } else {
            // 当前密码隐藏，切换为显示
            showPassword();
        }
    }

    /**
     * 显示密码明文
     */
    private void showPassword() {
        isPasswordVisible = true;
        
        // 设置输入类型为可见文本
        binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        
        // 更改图标为隐藏密码图标
        binding.ivPasswordToggle.setImageResource(R.drawable.ic_visibility);
        
        // 保持光标在文本末尾
        binding.etPassword.setSelection(binding.etPassword.getText().length());
        
        Log.d(TAG, "密码已显示");
    }

    /**
     * 隐藏密码，显示为点
     */
    private void hidePassword() {
        isPasswordVisible = false;
        
        // 设置输入类型为密码
        binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        
        // 更改图标为显示密码图标
        binding.ivPasswordToggle.setImageResource(R.drawable.ic_visibility_off);
        
        // 保持光标在文本末尾
        binding.etPassword.setSelection(binding.etPassword.getText().length());
        
        Log.d(TAG, "密码已隐藏");
    }
    
    /**
     * 处理登录按钮点击事件
     */
    private void onLoginButtonClicked() {
        Log.d(TAG, "点击了登录按钮");

        // 获取输入的用户名和密码
        String username = binding.etUsername.getText() != null ? binding.etUsername.getText().toString().trim() : "";
        String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";

        // 调用ViewModel执行登录
        loginViewModel.login(username, password);
    }
    
    /**
     * 处理忘记密码链接点击事件
     * TODO: 后续需要实现忘记密码功能
     */
    private void onForgotPasswordClicked() {
        Log.d(TAG, "点击了忘记密码按钮");

        // TODO: 跳转到忘记密码页面或弹出对话框
        if (getContext() != null) {
            Toast.makeText(getContext(), 
                "忘记密码功能待实现", 
                Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理创建账号链接点击事件
     * TODO: 后续需要实现注册功能
     */
    private void onCreateAccountClicked() {
        Log.d(TAG, "点击了创建用户按钮");
        // TODO: 跳转到注册页面
        if (getContext() != null) {
            Toast.makeText(getContext(),
                    "创建账号功能待实现",
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Fragment视图创建完成回调
     * 可在此处进行额外的初始化工作
     * 
     * @param view 创建的视图
     * @param savedInstanceState 保存的实例状态
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // 初始状态下密码是隐藏的
        hidePassword();
    }
    
    /**
     * Fragment销毁时清理资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        // 清理引用，避免内存泄漏
        binding = null;
    }
}
