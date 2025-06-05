package com.icheer.autotest.together.ui.base.view;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    /**
     * 隐藏ActionBar标题栏
     */
    protected void hideActionBar() {
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    /**
     * 显示ActionBar标题栏
     */
    protected void showActionBar() {
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
    protected void hideStatusBar() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            // 设置全屏模式，隐藏状态栏，显示导航栏
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 显示状态栏，恢复正常显示
     */
    protected void showStatusBar() {
        if (getActivity() != null) {
            View decorView = getActivity().getWindow().getDecorView();
            // 恢复正常显示模式
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

}
