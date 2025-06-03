package com.icheer.autotest.together.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.icheer.autotest.together.R;

/**
 * 首页Fragment
 * 展示应用的主要内容和功能入口
 */
public class HomeFragment extends Fragment {

    private TextView titleTextView;
    private TextView descriptionTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        // 初始化视图
        initViews(view);
        setupContent();
        
        return view;
    }

    /**
     * 初始化视图组件
     * @param view 根视图
     */
    private void initViews(View view) {
        titleTextView = view.findViewById(R.id.tv_home_title);
        descriptionTextView = view.findViewById(R.id.tv_home_description);
    }

    /**
     * 设置页面内容
     */
    private void setupContent() {
        titleTextView.setText("欢迎使用MyApp");
        descriptionTextView.setText("这是一个基于Fragment架构的Android原生应用\n使用传统XML布局和Java语言开发");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fragment显示时的逻辑
    }

    @Override
    public void onPause() {
        super.onPause();
        // Fragment隐藏时的逻辑
    }
} 