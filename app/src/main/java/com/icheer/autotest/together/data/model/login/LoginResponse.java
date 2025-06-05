package com.icheer.autotest.together.data.model.login;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * 登录响应数据模型
 * 映射登录成功时返回的result字段
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class LoginResponse {

    /**
     * 刷新令牌
     */
    @SerializedName("refresh_token")
    private String refreshToken;

    /**
     * 登录用户信息
     */
    @SerializedName("loginUser")
    private LoginUser loginUser;

    /**
     * 用户列表（可能包含多个账户）
     */
    @SerializedName("list")
    private List<LoginUser> list;

    /**
     * 访问令牌
     */
    @SerializedName("token")
    private String token;

    // 默认构造函数
    public LoginResponse() {}

    // Getter和Setter方法
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public List<LoginUser> getList() {
        return list;
    }

    public void setList(List<LoginUser> list) {
        this.list = list;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取访问令牌（兼容旧方法名）
     *
     * @return 访问令牌
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return loginUser != null ? loginUser.getName() : null;
    }

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    public String getMobile() {
        return loginUser != null ? loginUser.getMobile() : null;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginResponse{" +
                "refreshToken='" + refreshToken + '\'' +
                ", loginUser=" + loginUser +
                ", list=" + list +
                ", token='" + token + '\'' +
                '}';
    }
}
