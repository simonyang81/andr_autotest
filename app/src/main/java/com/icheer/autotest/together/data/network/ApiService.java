package com.icheer.autotest.together.data.network;

import com.icheer.autotest.together.data.model.base.BaseResponse;
import com.icheer.autotest.together.data.model.login.LoginRequest;
import com.icheer.autotest.together.data.model.login.LoginResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * API服务接口
 * 定义所有网络请求的接口方法
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public interface ApiService {

    /**
     * 用户登录接口
     *
     * @param loginRequest 登录请求数据
     * @return 登录响应的Observable
     */
    @POST("factoryAccount/login")
    Observable<BaseResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

}
