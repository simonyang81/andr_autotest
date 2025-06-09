package com.icheer.autotest.together.data.repository.splash;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.icheer.autotest.together.data.model.login.LoginRequest;
import com.icheer.autotest.together.data.network.NetworkClient;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashRepository {

    private static final String TAG = "SplashRepository";

    private static SplashRepository instance;

    private final NetworkClient networkClient;

    private final Context context;

    private SplashRepository(Context context) {
        this.context = context;
        this.networkClient = NetworkClient.getInstance(context);
    }


    public static synchronized SplashRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SplashRepository(context);
        }
        return instance;
    }

    /**
     * 网络状态数据模型
     */
    public static class NetworkState {
        private final boolean isConnected;
        private final boolean isWifiEnabled;
        private final boolean isMobileConnected;
        private final String statusMessage;

        public NetworkState(boolean isConnected, boolean isWifiEnabled,
                            boolean isMobileConnected, String statusMessage) {
            this.isConnected = isConnected;
            this.isWifiEnabled = isWifiEnabled;
            this.isMobileConnected = isMobileConnected;
            this.statusMessage = statusMessage;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public boolean isWifiEnabled() {
            return isWifiEnabled;
        }

        public boolean isMobileConnected() {
            return isMobileConnected;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        @NonNull
        @Override
        public String toString() {
            return "NetworkState{" +
                    "isConnected=" + isConnected +
                    ", isWifiEnabled=" + isWifiEnabled +
                    ", isMobileConnected=" + isMobileConnected +
                    ", statusMessage='" + statusMessage + '\'' +
                    '}';
        }
    }

    public static class ProductState {

        private final boolean isPCBAEnabled;
        // TODO, 添加硬件其他状态

        public ProductState(boolean isPCBAEnabled) {
            this.isPCBAEnabled = isPCBAEnabled;
        }

        public boolean isPCBAEnabled() {
            return isPCBAEnabled;
        }

        @NonNull
        @Override
        public String toString() {
            return "ProductState{" +
                    "isPCBAEnabled=" + isPCBAEnabled +
                    '}';
        }
    }

    /**
     * API状态数据模型
     */
    public static class ApiState {
        private final boolean isAvailable;
        private final long responseTime;
        private final String statusMessage;
        private final int httpStatusCode;

        public ApiState(boolean isAvailable, long responseTime,
                        String statusMessage, int httpStatusCode) {
            this.isAvailable = isAvailable;
            this.responseTime = responseTime;
            this.statusMessage = statusMessage;
            this.httpStatusCode = httpStatusCode;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public long getResponseTime() {
            return responseTime;
        }

        public String getStatusMessage() {
            return statusMessage;
        }

        public int getHttpStatusCode() {
            return httpStatusCode;
        }

        @NonNull
        @Override
        public String toString() {
            return "ApiState{" +
                    "isAvailable=" + isAvailable +
                    ", responseTime=" + responseTime + "ms" +
                    ", statusMessage='" + statusMessage + '\'' +
                    ", httpStatusCode=" + httpStatusCode +
                    '}';
        }
    }

    /**
     * 检查设备网络连接状态
     * 使用RxJava进行异步处理
     *
     * @return 网络状态的Observable
     */
    public Observable<NetworkState> checkNetworkState() {
        return Observable.fromCallable(() -> {
                    Log.d(TAG, "开始检查网络状态...");

                    ConnectivityManager connectivityManager = (ConnectivityManager)
                            context.getSystemService(Context.CONNECTIVITY_SERVICE);

                    if (connectivityManager == null) {
                        Log.e(TAG, "无法获取ConnectivityManager");
                        return new NetworkState(false, false, false, "系统服务不可用");
                    }

                    // 检查网络连接状态
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();

                    // 检查WIFI状态
                    WifiManager wifiManager = (WifiManager)
                            context.getSystemService(Context.WIFI_SERVICE);
                    boolean isWifiEnabled = wifiManager != null && wifiManager.isWifiEnabled();

                    // 检查移动数据状态
                    NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    boolean isMobileConnected = mobileInfo != null && mobileInfo.isConnected();

                    // 记录网络状态信息
                    Log.d(TAG, "网络连接状态: " + isConnected);
                    Log.d(TAG, "WIFI开启状态: " + isWifiEnabled);
                    Log.d(TAG, "移动数据连接状态: " + isMobileConnected);

                    // 生成状态消息
                    String statusMessage;
                    if (!isConnected) {
                        statusMessage = "当前无网络连接";
                        Log.w(TAG, "设备当前无网络连接");
                    } else {
                        statusMessage = "已有网络连接";
                        Log.d(TAG, "设备网络连接正常");
                    }

                    return new NetworkState(isConnected, isWifiEnabled, isMobileConnected, statusMessage);
                })
                .subscribeOn(Schedulers.io()) // 在IO线程执行
                .doOnNext(networkState -> {
                    Log.d(TAG, "网络状态检查完成: " + networkState);
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "网络状态检查失败", throwable);
                });
    }

    public Observable<String> getProductKey() {
        return Observable.fromCallable(() -> {
                    Log.d(TAG, "开始获取产品编码...");

                    // TODO, 具体获取产品编码操作

                    return "eamy220";
                })
                .subscribeOn(Schedulers.io()) // 在IO线程执行
                .doOnNext(productKey -> {
                    Log.d(TAG, "获取产品编码: " + productKey);
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "获取产品编码失败", throwable);
                });
    }


    public Observable<ProductState> checkProductState() {
        return Observable.fromCallable(() -> {
                    // TODO, 模拟PCBA检查OK
                    return new ProductState(true);
                }).subscribeOn(Schedulers.io()) // 在IO线程执行

                .doOnNext(productState -> {
                    Log.d(TAG, "检查硬件状态: " + productState);
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "检查硬件状态失败", throwable);
                });

    }


    /**
     * 检测后台API连通性（简化版本）
     * 仅检查是否能够连接到服务器，不依赖特定的健康检查接口
     *
     * @return API连通性状态的Observable
     */
    public Observable<ApiState> checkApiConnectivity() {
        return Observable.fromCallable(() -> {
                    Log.d(TAG, "开始检测后台API连通性...");
                    long startTime = System.currentTimeMillis();

                    try {
                        // 使用登录接口进行连通性测试（使用空参数）
                        networkClient.getApiService()
                                .login(new LoginRequest("", ""))
                                .timeout(5, java.util.concurrent.TimeUnit.SECONDS)
                                .blockingFirst();

                        long responseTime = System.currentTimeMillis() - startTime;
                        Log.d(TAG, "API连通性检测完成，响应时间: " + responseTime + "ms");

                        return new ApiState(true, responseTime,
                                "后台API服务可连接", 200);

                    } catch (Exception e) {
                        long responseTime = System.currentTimeMillis() - startTime;
                        Log.e(TAG, "API连通性检测失败", e);

                        // 如果是HTTP错误但能收到响应，说明服务器可达
                        if (e instanceof retrofit2.HttpException) {
                            return new ApiState(true, responseTime,
                                    "后台API服务可连接",
                                    ((retrofit2.HttpException) e).code());
                        }

                        String errorMessage = "后台API连接失败";

                        return new ApiState(false, responseTime, errorMessage, -1);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(apiState -> {
                    Log.d(TAG, "后台API连通性检测完成: " + apiState);
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "后台API连通性检测失败", throwable);
                });
    }

    
}
