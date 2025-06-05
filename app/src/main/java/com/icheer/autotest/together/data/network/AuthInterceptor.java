package com.icheer.autotest.together.data.network;

import android.content.Context;

import androidx.annotation.NonNull;

import com.icheer.autotest.together.data.manager.UserSessionManager;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 认证拦截器
 * 自动为请求添加认证头部
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class AuthInterceptor implements Interceptor {

    private final UserSessionManager sessionManager;

    public AuthInterceptor(Context context) {
        this.sessionManager = UserSessionManager.getInstance(context);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // 获取存储的Token
        String token = sessionManager.getAccessToken();
        if (token != null && !originalRequest.url().encodedPath().contains("factoryAccount/login")) {
            Request authenticatedRequest = originalRequest.newBuilder()
                    .header("Authorization", token)
                    .build();
            return chain.proceed(authenticatedRequest);
        }

        return chain.proceed(originalRequest);
    }
}