package com.icheer.autotest.together.ui.splash.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.icheer.autotest.together.data.manager.SettingsManager;
import com.icheer.autotest.together.ui.base.viewmodel.BaseViewModel;

/**
 * 设置页面的ViewModel
 * 负责处理设置相关的业务逻辑和数据管理
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class SettingsViewModel extends BaseViewModel {

    private static final String TAG = "SettingsViewModel";

    private final MutableLiveData<Boolean> notificationEnabled = new MutableLiveData<>();
    private final MutableLiveData<Boolean> darkModeEnabled = new MutableLiveData<>();
    private final MutableLiveData<Boolean> soundEnabled = new MutableLiveData<>();
    private final MutableLiveData<Boolean> settingsSaved = new MutableLiveData<>();
    private final MutableLiveData<Boolean> settingsReset = new MutableLiveData<>();

    private final SettingsManager settingsManager;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        this.settingsManager = SettingsManager.getInstance(application);
    }

    /**
     * 加载设置数据
     */
    public void loadSettings() {
        Log.d(TAG, "加载设置数据");
        
        // 从SettingsManager获取当前设置状态
        notificationEnabled.setValue(settingsManager.isNotificationEnabled());
        darkModeEnabled.setValue(settingsManager.isDarkModeEnabled());
        soundEnabled.setValue(settingsManager.isSoundEnabled());
    }

    /**
     * 设置消息通知开关状态
     *
     * @param enabled 是否启用消息通知
     */
    public void setNotificationEnabled(boolean enabled) {
        Log.d(TAG, "设置消息通知: " + enabled);
        
        settingsManager.setNotificationEnabled(enabled);
        notificationEnabled.setValue(enabled);
        settingsSaved.setValue(true);
        
        // 重置保存状态
        settingsSaved.postValue(false);
    }

    /**
     * 设置深色模式开关状态
     *
     * @param enabled 是否启用深色模式
     */
    public void setDarkModeEnabled(boolean enabled) {
        Log.d(TAG, "设置深色模式: " + enabled);
        
        settingsManager.setDarkModeEnabled(enabled);
        darkModeEnabled.setValue(enabled);
        settingsSaved.setValue(true);
        
        // 重置保存状态
        settingsSaved.postValue(false);
    }

    /**
     * 设置声音开关状态
     *
     * @param enabled 是否启用声音
     */
    public void setSoundEnabled(boolean enabled) {
        Log.d(TAG, "设置声音: " + enabled);
        
        settingsManager.setSoundEnabled(enabled);
        soundEnabled.setValue(enabled);
        settingsSaved.setValue(true);
        
        // 重置保存状态
        settingsSaved.postValue(false);
    }

    /**
     * 重置所有设置为默认值
     */
    public void resetSettings() {
        Log.d(TAG, "重置设置为默认值");
        
        settingsManager.resetToDefaults();
        
        // 更新LiveData为默认值
        notificationEnabled.setValue(SettingsManager.DEFAULT_NOTIFICATION_ENABLED);
        darkModeEnabled.setValue(SettingsManager.DEFAULT_DARK_MODE_ENABLED);
        soundEnabled.setValue(SettingsManager.DEFAULT_SOUND_ENABLED);
        settingsReset.setValue(true);
        
        // 重置重置状态
        settingsReset.postValue(false);
    }

    // Getters for LiveData
    public MutableLiveData<Boolean> getNotificationEnabled() {
        return notificationEnabled;
    }

    public MutableLiveData<Boolean> getDarkModeEnabled() {
        return darkModeEnabled;
    }

    public MutableLiveData<Boolean> getSoundEnabled() {
        return soundEnabled;
    }

    public MutableLiveData<Boolean> getSettingsSaved() {
        return settingsSaved;
    }

    public MutableLiveData<Boolean> getSettingsReset() {
        return settingsReset;
    }
} 