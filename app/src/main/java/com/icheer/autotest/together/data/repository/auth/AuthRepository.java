package com.icheer.autotest.together.data.repository.auth;

import android.content.Context;
import android.util.Log;

import com.icheer.autotest.together.data.model.base.BaseResponse;
import com.icheer.autotest.together.data.model.login.LoginRequest;
import com.icheer.autotest.together.data.model.login.LoginResponse;
import com.icheer.autotest.together.data.network.NetworkClient;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 认证相关的Repository
 * 负责处理登录、登出等认证相关的数据操作
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class AuthRepository {

    private static final String TAG = "AuthRepository";

    private static AuthRepository instance;
    private final NetworkClient networkClient;

    /**
     * 私有构造函数
     *
     * @param context 上下文
     */
    private AuthRepository(Context context) {
        this.networkClient = NetworkClient.getInstance(context);
    }

    /**
     * 获取AuthRepository单例实例
     *
     * @param context 上下文
     * @return AuthRepository实例
     */
    public static synchronized AuthRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AuthRepository(context.getApplicationContext());
        }
        return instance;
    }


    /**
     * 执行用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果的Observable
     */
    public Observable<BaseResponse<LoginResponse>> login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);

        return networkClient.getApiService()
                .login(loginRequest)
                .subscribeOn(Schedulers.io())
                .doOnNext(response -> {

                    Log.d(TAG, "登录后的Response: " + response);
                    if (response.isSuccess()) {
                        Log.d(TAG, "登录成功");
                    } else {
                        Log.e(TAG, "登录失败: " + response.getMessage());
                    }

                });

    }

}
