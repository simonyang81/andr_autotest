package com.icheer.autotest.together.data.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * 网络客户端单例类
 * 负责创建和管理Retrofit实例
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class NetworkClient {

    private static final String BASE_URL = "http://eikcloud.icheer.cn/api/";
    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    private static NetworkClient instance;
    private final ApiService apiService;

    /**
     * 私有构造函数
     *
     * @param context 上下文
     */
    private NetworkClient(Context context) {
        // 创建日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 创建OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new AuthInterceptor(context)) // 添加认证拦截器
                .build();

        // 创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    /**
     * 获取NetworkClient单例实例
     *
     * @param context 上下文
     * @return NetworkClient实例
     */
    public static synchronized NetworkClient getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkClient(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 获取API服务接口
     *
     * @return ApiService实例
     */
    public ApiService getApiService() {
        return apiService;
    }
}