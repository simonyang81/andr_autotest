package com.icheer.autotest.together.data.model.login;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * 登录用户信息模型
 * 映射登录响应中的loginUser对象
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class LoginUser {

    /**
     * 工厂员工ID
     */
    @SerializedName("factoryStaffId")
    private int factoryStaffId;

    /**
     * 用户姓名
     */
    @SerializedName("name")
    private String name;

    /**
     * 工厂账户ID
     */
    @SerializedName("factoryAccountId")
    private int factoryAccountId;

    /**
     * 工厂名称
     */
    @SerializedName("factoryName")
    private String factoryName;

    /**
     * 手机号
     */
    @SerializedName("mobile")
    private String mobile;

    /**
     * 工厂ID
     */
    @SerializedName("factoryId")
    private int factoryId;

    /**
     * 工厂类型
     */
    @SerializedName("factoryType")
    private int factoryType;

    /**
     * 用户角色
     */
    @SerializedName("role")
    private int role;

    /**
     * 是否登录
     */
    @SerializedName("isLogin")
    private int isLogin;

    /**
     * 是否删除
     */
    @SerializedName("isDel")
    private int isDel;

    /**
     * 删除状态字典
     */
    @SerializedName("isDelDic")
    private String isDelDic;

    /**
     * 登录状态字典
     */
    @SerializedName("isLoginDic")
    private String isLoginDic;

    /**
     * 扩展信息
     */
    @SerializedName("extInfos")
    private Object extInfos;

    // 默认构造函数
    public LoginUser() {}

    // Getter和Setter方法
    public int getFactoryStaffId() {
        return factoryStaffId;
    }

    public void setFactoryStaffId(int factoryStaffId) {
        this.factoryStaffId = factoryStaffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFactoryAccountId() {
        return factoryAccountId;
    }

    public void setFactoryAccountId(int factoryAccountId) {
        this.factoryAccountId = factoryAccountId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public int getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(int factoryType) {
        this.factoryType = factoryType;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getIsDelDic() {
        return isDelDic;
    }

    public void setIsDelDic(String isDelDic) {
        this.isDelDic = isDelDic;
    }

    public String getIsLoginDic() {
        return isLoginDic;
    }

    public void setIsLoginDic(String isLoginDic) {
        this.isLoginDic = isLoginDic;
    }

    public Object getExtInfos() {
        return extInfos;
    }

    public void setExtInfos(Object extInfos) {
        this.extInfos = extInfos;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginUser{" +
                "factoryStaffId=" + factoryStaffId +
                ", name='" + name + '\'' +
                ", factoryAccountId=" + factoryAccountId +
                ", factoryName='" + factoryName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", factoryId=" + factoryId +
                ", factoryType=" + factoryType +
                ", role=" + role +
                ", isLogin=" + isLogin +
                ", isDel=" + isDel +
                ", isDelDic='" + isDelDic + '\'' +
                ", isLoginDic='" + isLoginDic + '\'' +
                ", extInfos=" + extInfos +
                '}';
    }
}