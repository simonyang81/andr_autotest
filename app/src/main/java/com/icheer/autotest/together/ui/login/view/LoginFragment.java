package com.icheer.autotest.together.ui.login.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.icheer.autotest.together.R;
import com.icheer.autotest.together.ui.base.view.BaseFragment;

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

    
    // UI组件
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvCreateAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏ActionBar标题栏
        hideActionBar();

        // 隐藏状态栏，设置全屏模式
        hideStatusBar();

    }

    /**
     * 创建Fragment视图
     * 
     * @param inflater 布局加载器
     * @param container 父容器
     * @param savedInstanceState 保存的实例状态
     * @return 创建的视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                             @Nullable Bundle savedInstanceState) {
        // 加载登录界面布局
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        
        // 初始化UI组件
        initViews(view);
        
        // 设置点击事件监听器
        setupClickListeners();
        
        return view;
    }
    
    /**
     * 初始化UI组件
     * 绑定布局中的视图元素
     * 
     * @param view 根视图
     */
    private void initViews(View view) {
        etUsername = view.findViewById(R.id.et_username);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvForgotPassword = view.findViewById(R.id.tv_forgot_password);
        tvCreateAccount = view.findViewById(R.id.tv_create_account);
    }
    
    /**
     * 设置点击事件监听器
     * 为各个交互元素设置点击处理逻辑
     */
    private void setupClickListeners() {
        // 登录按钮点击事件
        btnLogin.setOnClickListener(v -> onLoginButtonClicked());
        
        // 忘记密码链接点击事件
        tvForgotPassword.setOnClickListener(v -> onForgotPasswordClicked());
        
        // 创建账号链接点击事件
        tvCreateAccount.setOnClickListener(v -> onCreateAccountClicked());
    }
    
    /**
     * 处理登录按钮点击事件
     * TODO: 后续需要实现具体的登录逻辑
     */
    private void onLoginButtonClicked() {
        // 获取输入的用户名和密码
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        
        // TODO: 实现登录验证逻辑
        // 1. 输入验证
        // 2. 调用ViewModel进行登录
        // 3. 处理登录结果
        
        // 临时提示 - 后续可删除
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), 
                "登录功能待实现 - 用户名: " + username, 
                android.widget.Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 处理忘记密码链接点击事件
     * TODO: 后续需要实现忘记密码功能
     */
    private void onForgotPasswordClicked() {
        // TODO: 跳转到忘记密码页面或弹出对话框
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), 
                "忘记密码功能待实现", 
                android.widget.Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 处理创建账号链接点击事件
     * TODO: 后续需要实现注册功能
     */
    private void onCreateAccountClicked() {
        // TODO: 跳转到注册页面
        if (getContext() != null) {
            android.widget.Toast.makeText(getContext(), 
                "创建账号功能待实现", 
                android.widget.Toast.LENGTH_SHORT).show();
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
        
        // TODO: 可在此处进行以下操作：
        // 1. 绑定ViewModel
        // 2. 观察LiveData
        // 3. 设置动画效果
        // 4. 检查自动登录状态
    }
    
    /**
     * Fragment销毁时清理资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        // 清理引用，避免内存泄漏
        etUsername = null;
        etPassword = null;
        btnLogin = null;
        tvForgotPassword = null;
        tvCreateAccount = null;
    }
}
