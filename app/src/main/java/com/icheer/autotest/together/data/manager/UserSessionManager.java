package com.icheer.autotest.together.data.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.icheer.autotest.together.data.model.login.LoginResponse;
import com.icheer.autotest.together.data.model.login.LoginUser;

/**
 * 用户会话管理器
 * 负责管理用户登录状态、用户信息存储和token管理
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class UserSessionManager {

    private static final String TAG = "UserSessionManager";

    // SharedPreferences文件名
    private static final String PREF_NAME = "user_session";

    // 存储键名常量
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_FACTORY_NAME = "factory_name";
    private static final String KEY_FACTORY_ID = "factory_id";
    private static final String KEY_FACTORY_STAFF_ID = "factory_staff_id";
    private static final String KEY_FACTORY_ACCOUNT_ID = "factory_account_id";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_LOGIN_TIME = "login_time";
    private static final String KEY_TOKEN_EXPIRE_TIME = "token_expire_time";

    private static UserSessionManager instance;
    private final SharedPreferences preferences;
    private final Context context;

    /**
     * 私有构造函数
     */
    private UserSessionManager(Context context) {
        this.context = context.getApplicationContext();
        this.preferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取单例实例
     */
    public static synchronized UserSessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserSessionManager(context);
        }
        return instance;
    }

    /**
     * 保存登录会话信息
     *
     * @param loginResponse 登录响应数据
     */
    public void saveLoginSession(LoginResponse loginResponse) {
        if (loginResponse == null) {
            Log.w(TAG, "登录响应为空，无法保存会话信息");
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();

        try {
            // 保存登录状态
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putLong(KEY_LOGIN_TIME, System.currentTimeMillis());

            // 保存token信息
            if (loginResponse.getAccessToken() != null) {
                editor.putString(KEY_ACCESS_TOKEN, loginResponse.getAccessToken());
                Log.d(TAG, "Access token 已保存");
            }

            if (loginResponse.getRefreshToken() != null) {
                editor.putString(KEY_REFRESH_TOKEN, loginResponse.getRefreshToken());
                Log.d(TAG, "Refresh token 已保存");
            }

            // 保存基本用户信息
            if (loginResponse.getUsername() != null) {
                editor.putString(KEY_USERNAME, loginResponse.getUsername());
            }

            if (loginResponse.getMobile() != null) {
                editor.putString(KEY_MOBILE, loginResponse.getMobile());
            }

            // 保存详细用户信息
            LoginUser loginUser = loginResponse.getLoginUser();
            if (loginUser != null) {
                if (loginUser.getFactoryName() != null) {
                    editor.putString(KEY_FACTORY_NAME, loginUser.getFactoryName());
                }
                editor.putInt(KEY_FACTORY_ID, loginUser.getFactoryId());
                editor.putInt(KEY_FACTORY_STAFF_ID, loginUser.getFactoryStaffId());
                editor.putInt(KEY_FACTORY_ACCOUNT_ID, loginUser.getFactoryAccountId());
                editor.putInt(KEY_USER_ROLE, loginUser.getRole());
            }

            // 设置token过期时间（默认7天）
            long expireTime = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000L);
            editor.putLong(KEY_TOKEN_EXPIRE_TIME, expireTime);

            editor.apply();

            Log.d(TAG, "用户会话信息保存成功");

        } catch (Exception e) {
            Log.e(TAG, "保存用户会话信息失败", e);
        }
    }

    /**
     * 检查用户是否已登录
     */
    public boolean isLoggedIn() {
        boolean isLoggedIn = preferences.getBoolean(KEY_IS_LOGGED_IN, false);

        // 检查token是否过期
        if (isLoggedIn && isTokenExpired()) {
            Log.d(TAG, "Token已过期，清除登录状态");
            clearSession();
            return false;
        }

        return isLoggedIn;
    }

    /**
     * 检查token是否过期
     */
    public boolean isTokenExpired() {
        long expireTime = preferences.getLong(KEY_TOKEN_EXPIRE_TIME, 0);
        return System.currentTimeMillis() > expireTime;
    }

    /**
     * 获取访问token
     */
    public String getAccessToken() {
        return preferences.getString(KEY_ACCESS_TOKEN, null);
    }

    /**
     * 获取刷新token
     */
    public String getRefreshToken() {
        return preferences.getString(KEY_REFRESH_TOKEN, null);
    }

    /**
     * 获取用户名
     */
    public String getUsername() {
        return preferences.getString(KEY_USERNAME, null);
    }

    /**
     * 获取手机号
     */
    public String getMobile() {
        return preferences.getString(KEY_MOBILE, null);
    }

    /**
     * 获取工厂名称
     */
    public String getFactoryName() {
        return preferences.getString(KEY_FACTORY_NAME, null);
    }

    /**
     * 获取工厂ID
     */
    public int getFactoryId() {
        return preferences.getInt(KEY_FACTORY_ID, 0);
    }

    /**
     * 获取用户角色
     */
    public int getUserRole() {
        return preferences.getInt(KEY_USER_ROLE, 0);
    }

    /**
     * 获取登录时间
     */
    public long getLoginTime() {
        return preferences.getLong(KEY_LOGIN_TIME, 0);
    }

    /**
     * 更新访问token
     */
    public void updateAccessToken(String newToken) {
        if (newToken != null) {
            preferences.edit()
                    .putString(KEY_ACCESS_TOKEN, newToken)
                    .apply();
            Log.d(TAG, "Access token 已更新");
        }
    }

    /**
     * 清除用户会话（登出）
     */
    public void clearSession() {
        preferences.edit().clear().apply();
        Log.d(TAG, "用户会话已清除");
    }

    /**
     * 获取用户详细信息（用于显示）
     */
    public UserInfo getUserInfo() {
        if (!isLoggedIn()) {
            return null;
        }

        return new UserInfo(
                getUsername(),
                getMobile(),
                getFactoryName(),
                getFactoryId(),
                getUserRole(),
                getLoginTime()
        );
    }

    /**
     * 用户信息数据类
     */
    public static class UserInfo {
        private final String username;
        private final String mobile;
        private final String factoryName;
        private final int factoryId;
        private final int userRole;
        private final long loginTime;

        public UserInfo(String username, String mobile, String factoryName,
                        int factoryId, int userRole, long loginTime) {
            this.username = username;
            this.mobile = mobile;
            this.factoryName = factoryName;
            this.factoryId = factoryId;
            this.userRole = userRole;
            this.loginTime = loginTime;
        }

        // Getter方法
        public String getUsername() { return username; }
        public String getMobile() { return mobile; }
        public String getFactoryName() { return factoryName; }
        public int getFactoryId() { return factoryId; }
        public int getUserRole() { return userRole; }
        public long getLoginTime() { return loginTime; }

        @NonNull
        @Override
        public String toString() {
            return "UserInfo{" +
                    "username='" + username + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", factoryName='" + factoryName + '\'' +
                    ", factoryId=" + factoryId +
                    ", userRole=" + userRole +
                    ", loginTime=" + loginTime +
                    '}';
        }
    }
}