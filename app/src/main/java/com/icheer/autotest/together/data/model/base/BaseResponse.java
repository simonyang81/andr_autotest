package com.icheer.autotest.together.data.model.base;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * API响应基类
 * 封装服务器返回的通用响应格式
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class BaseResponse<T> {

    /**
     * 响应状态码
     * 成功通常为0或正数，失败为负数
     */
    @SerializedName("code")
    private int code;

    /**
     * 错误代码（可选）
     */
    @SerializedName("errCode")
    private String errCode;

    /**
     * 响应消息
     */
    @SerializedName("message")
    private String message;

    /**
     * 时间戳
     */
    @SerializedName("timestamp")
    private long timestamp;

    /**
     * 响应数据
     */
    @SerializedName("result")
    private T result;

    // 默认构造函数
    public BaseResponse() {}

    // 带参数构造函数
    public BaseResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 判断请求是否成功
     *
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return code == 200;
    }

    /**
     * 判断请求是否失败
     *
     * @return true表示失败，false表示成功
     */
    public boolean isFailure() {
        return code != 200;
    }

    // Getter和Setter方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @NonNull
    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", errCode='" + errCode + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", result=" + result +
                '}';
    }
}