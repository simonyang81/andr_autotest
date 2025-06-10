package com.icheer.autotest.together.ui.splash.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.icheer.autotest.together.databinding.FragmentSettingsBinding;
import com.icheer.autotest.together.ui.splash.viewmodel.SettingsViewModel;

public class SettingsDialogFragment extends DialogFragment {

    private static final String TAG = "SettingsDialogFragment";

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingsViewModel;

    private SettingsDialogFragment() {
    }

    /**
     * 使用工厂方法创建SettingsFragment实例
     *
     * @return SettingsFragment的新实例
     */
    public static SettingsDialogFragment newInstance() {

        Log.d(TAG, "New SettingsFragment instance");

        SettingsDialogFragment fragment = new SettingsDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置对话框样式
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                // 设置对话框占满整个屏幕
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                // 设置透明背景
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // 设置状态栏和导航栏透明
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );

                // 设置系统UI可见性
                View decorView = window.getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 使用ViewBinding加载布局
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        // 初始化ViewModel
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // 初始化视图
        initViews();

        // 设置点击事件监听器
        setupClickListeners();

        // 观察ViewModel的LiveData
        observeViewModel();

        return binding.getRoot();
    }

    private void initViews() {
        // 从ViewModel获取当前设置状态并更新UI
        settingsViewModel.loadSettings();

        Log.d(TAG, "初始化设置界面");
    }

    private void setupClickListeners() {

        // 关闭按钮点击事件
        binding.btnClose.setOnClickListener(v -> onCloseButtonClicked());

        // 背景点击关闭（点击对话框外部区域）
        binding.dialogBackground.setOnClickListener(v -> onCloseButtonClicked());

        // 关于应用点击事件
        binding.layoutAboutApp.setOnClickListener(v -> onAboutAppClicked());

        // 防止内容区域点击时关闭对话框
        binding.contentCard.setOnClickListener(v -> {
            // 空实现，阻止事件冒泡
        });

        // 重置设置点击事件
        binding.btnResetSettings.setOnClickListener(v -> onResetSettingsClicked());

        // 消息通知开关点击事件
        binding.switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) { // 只处理用户主动点击，避免程序设置时触发
                settingsViewModel.setNotificationEnabled(isChecked);
                Log.d(TAG, "消息通知设置: " + isChecked);
            }
        });

        // 深色模式开关点击事件
        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) { // 只处理用户主动点击，避免程序设置时触发
                settingsViewModel.setDarkModeEnabled(isChecked);
                Log.d(TAG, "深色模式设置: " + isChecked);
            }
        });

        // 声音开关点击事件
        binding.switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) { // 只处理用户主动点击，避免程序设置时触发
                settingsViewModel.setSoundEnabled(isChecked);
                Log.d(TAG, "声音设置: " + isChecked);
            }
        });


    }

    private void observeViewModel() {

        // 观察消息通知设置状态
        settingsViewModel.getNotificationEnabled().observe(getViewLifecycleOwner(), enabled -> {
            if (enabled != null) {
                binding.switchNotifications.setChecked(enabled);
            }
        });

        // 观察深色模式设置状态
        settingsViewModel.getDarkModeEnabled().observe(getViewLifecycleOwner(), enabled -> {
            if (enabled != null) {
                binding.switchDarkMode.setChecked(enabled);
            }
        });

        // 观察声音设置状态
        settingsViewModel.getSoundEnabled().observe(getViewLifecycleOwner(), enabled -> {
            if (enabled != null) {
                binding.switchSound.setChecked(enabled);
            }
        });

        // 观察设置保存状态
        settingsViewModel.getSettingsSaved().observe(getViewLifecycleOwner(), saved -> {
            if (saved != null && saved) {
                showToast("设置已保存");
            }
        });

        // 观察设置重置状态
        settingsViewModel.getSettingsReset().observe(getViewLifecycleOwner(), reset -> {
            if (reset != null && reset) {
                showToast("设置已重置为默认值");
            }
        });

    }

    private void onResetSettingsClicked() {
        settingsViewModel.resetSettings();
    }

    /**
     * 关闭按钮点击处理
     */
    private void onCloseButtonClicked() {
        Log.d(TAG, "关闭设置对话框");
        dismiss();
    }

    /**
     * 关于应用点击处理
     */
    private void onAboutAppClicked() {
        showToast("关于应用功能待实现");
    }

    /**
     * 显示Toast消息
     */
    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }





}
