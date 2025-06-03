package com.icheer.autotest.together.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.icheer.autotest.together.R;

/**
 * 设置Fragment
 * 管理应用的配置选项和用户偏好设置
 */
public class SettingsFragment extends Fragment {

    private TextView settingsTitleTextView;
    private Switch notificationSwitch;
    private Switch darkModeSwitch;
    private Button aboutButton;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        
        // 初始化视图
        initViews(view);
        setupListeners();
        loadSettings();
        
        return view;
    }

    /**
     * 初始化视图组件
     * @param view 根视图
     */
    private void initViews(View view) {
        settingsTitleTextView = view.findViewById(R.id.tv_settings_title);
        notificationSwitch = view.findViewById(R.id.switch_notifications);
        darkModeSwitch = view.findViewById(R.id.switch_dark_mode);
        aboutButton = view.findViewById(R.id.btn_about);
    }

    /**
     * 设置事件监听器
     */
    private void setupListeners() {
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 保存通知设置
            saveNotificationSetting(isChecked);
        });

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 保存深色模式设置
            saveDarkModeSetting(isChecked);
        });

        aboutButton.setOnClickListener(v -> {
            // 显示关于信息
            showAboutInfo();
        });
    }

    /**
     * 加载设置信息
     */
    private void loadSettings() {
        settingsTitleTextView.setText("应用设置");
        // 这里可以从SharedPreferences加载用户设置
        notificationSwitch.setChecked(true);
        darkModeSwitch.setChecked(false);
    }

    /**
     * 保存通知设置
     * @param enabled 是否启用通知
     */
    private void saveNotificationSetting(boolean enabled) {
        // 使用SharedPreferences保存设置
        // SharedPreferences prefs = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        // prefs.edit().putBoolean("notifications_enabled", enabled).apply();
    }

    /**
     * 保存深色模式设置
     * @param enabled 是否启用深色模式
     */
    private void saveDarkModeSetting(boolean enabled) {
        // 使用SharedPreferences保存设置
        // SharedPreferences prefs = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        // prefs.edit().putBoolean("dark_mode_enabled", enabled).apply();
    }

    /**
     * 显示关于信息
     */
    private void showAboutInfo() {
        // 可以启动AboutActivity或显示对话框
        // Intent intent = new Intent(getContext(), AboutActivity.class);
        // startActivity(intent);
    }
} 