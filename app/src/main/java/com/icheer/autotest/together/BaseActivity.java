package com.icheer.autotest.together;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础Activity类
 * 统一处理横屏限制和通用功能
 * 
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 强制横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
} 