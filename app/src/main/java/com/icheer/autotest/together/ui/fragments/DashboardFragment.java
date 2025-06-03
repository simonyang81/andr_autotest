package com.icheer.autotest.together.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.icheer.autotest.together.R;

/**
 * 仪表板Fragment
 * 展示应用的数据统计和分析信息
 */
public class DashboardFragment extends Fragment {

    private TextView statsTextView;
    private TextView dataTextView;
    private Button refreshButton;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 加载布局文件
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        // 初始化视图
        initViews(view);
        setupListeners();
        loadDashboardData();
        
        return view;
    }

    /**
     * 初始化视图组件
     * @param view 根视图
     */
    private void initViews(View view) {
        statsTextView = view.findViewById(R.id.tv_stats);
        dataTextView = view.findViewById(R.id.tv_data);
        refreshButton = view.findViewById(R.id.btn_refresh);
    }

    /**
     * 设置事件监听器
     */
    private void setupListeners() {
        refreshButton.setOnClickListener(v -> {
            loadDashboardData();
            // 可以添加刷新动画或提示
        });
    }

    /**
     * 加载仪表板数据
     */
    private void loadDashboardData() {
        // 模拟数据加载
        statsTextView.setText("统计数据");
        dataTextView.setText("用户数量: 1,234\n活跃用户: 567\n今日访问: 89");
    }

    @Override
    public void onResume() {
        super.onResume();
        // 刷新数据
        loadDashboardData();
    }
}