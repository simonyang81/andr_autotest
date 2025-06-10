package com.icheer.autotest.together.data.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 应用设置管理器
 * 负责管理应用的各种设置选项，如消息通知、主题等
 * 使用SharedPreferences进行数据持久化
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class SettingsManager {

    private static final String TAG = "SettingsManager";

    // SharedPreferences文件名
    private static final String PREF_NAME = "app_settings";

    // 设置键名常量
    private static final String KEY_NOTIFICATION_ENABLED = "notification_enabled";
    private static final String KEY_DARK_MODE_ENABLED = "dark_mode_enabled";
    private static final String KEY_AUTO_LOGIN_ENABLED = "auto_login_enabled";
    private static final String KEY_SOUND_ENABLED = "sound_enabled";

    // 默认值常量
    public static final boolean DEFAULT_NOTIFICATION_ENABLED = true;
    public static final boolean DEFAULT_DARK_MODE_ENABLED = false;
    public static final boolean DEFAULT_AUTO_LOGIN_ENABLED = false;
    public static final boolean DEFAULT_SOUND_ENABLED = true;

    private static SettingsManager instance;
    private final SharedPreferences preferences;
    private final Context context;

    /**
     * 私有构造函数
     */
    private SettingsManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取单例实例
     *
     * @param context 上下文
     * @return SettingsManager实例
     */
    public static synchronized SettingsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsManager(context);
        }
        return instance;
    }

    /**
     * 检查消息通知是否启用
     *
     * @return true表示启用，false表示禁用
     */
    public boolean isNotificationEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATION_ENABLED, DEFAULT_NOTIFICATION_ENABLED);
    }

    /**
     * 设置消息通知开关状态
     *
     * @param enabled 是否启用消息通知
     */
    public void setNotificationEnabled(boolean enabled) {
        preferences.edit()
                .putBoolean(KEY_NOTIFICATION_ENABLED, enabled)
                .apply();
        Log.d(TAG, "消息通知设置已更新: " + enabled);
    }

    /**
     * 检查深色模式是否启用
     *
     * @return true表示启用深色模式，false表示浅色模式
     */
    public boolean isDarkModeEnabled() {
        return preferences.getBoolean(KEY_DARK_MODE_ENABLED, DEFAULT_DARK_MODE_ENABLED);
    }

    /**
     * 设置深色模式开关状态
     *
     * @param enabled 是否启用深色模式
     */
    public void setDarkModeEnabled(boolean enabled) {
        preferences.edit()
                .putBoolean(KEY_DARK_MODE_ENABLED, enabled)
                .apply();
        Log.d(TAG, "深色模式设置已更新: " + enabled);
    }

    /**
     * 检查自动登录是否启用
     *
     * @return true表示启用，false表示禁用
     */
    public boolean isAutoLoginEnabled() {
        return preferences.getBoolean(KEY_AUTO_LOGIN_ENABLED, DEFAULT_AUTO_LOGIN_ENABLED);
    }

    /**
     * 设置自动登录开关状态
     *
     * @param enabled 是否启用自动登录
     */
    public void setAutoLoginEnabled(boolean enabled) {
        preferences.edit()
                .putBoolean(KEY_AUTO_LOGIN_ENABLED, enabled)
                .apply();
        Log.d(TAG, "自动登录设置已更新: " + enabled);
    }

    /**
     * 检查声音是否启用
     *
     * @return true表示启用，false表示禁用
     */
    public boolean isSoundEnabled() {
        return preferences.getBoolean(KEY_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
    }

    /**
     * 设置声音开关状态
     *
     * @param enabled 是否启用声音
     */
    public void setSoundEnabled(boolean enabled) {
        preferences.edit()
                .putBoolean(KEY_SOUND_ENABLED, enabled)
                .apply();
        Log.d(TAG, "声音设置已更新: " + enabled);
    }

    /**
     * 重置所有设置为默认值
     */
    public void resetToDefaults() {
        SharedPreferences.Editor editor = preferences.edit();
        
        editor.putBoolean(KEY_NOTIFICATION_ENABLED, DEFAULT_NOTIFICATION_ENABLED);
        editor.putBoolean(KEY_DARK_MODE_ENABLED, DEFAULT_DARK_MODE_ENABLED);
        editor.putBoolean(KEY_AUTO_LOGIN_ENABLED, DEFAULT_AUTO_LOGIN_ENABLED);
        editor.putBoolean(KEY_SOUND_ENABLED, DEFAULT_SOUND_ENABLED);
        
        editor.apply();
        
        Log.d(TAG, "所有设置已重置为默认值");
    }

    /**
     * 清除所有设置数据
     */
    public void clearAllSettings() {
        preferences.edit().clear().apply();
        Log.d(TAG, "所有设置数据已清除");
    }

    /**
     * 获取设置摘要信息（用于调试）
     *
     * @return 设置摘要字符串
     */
    public String getSettingsSummary() {
        return String.format(
                "设置摘要: 消息通知=%s, 深色模式=%s, 自动登录=%s, 声音=%s",
                isNotificationEnabled(),
                isDarkModeEnabled(),
                isAutoLoginEnabled(),
                isSoundEnabled()
        );
    }
} 