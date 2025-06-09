package com.icheer.autotest.together.ui.splash.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.icheer.autotest.together.data.repository.splash.SplashRepository;
import com.icheer.autotest.together.ui.base.viewmodel.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

/**
 * 欢迎页面的ViewModel
 * 负责欢迎页面相关的业务逻辑和数据管理（主要检查环境）
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class SplashViewModel extends BaseViewModel {

    private static final String TAG = "SplashViewModel";

    private final MutableLiveData<String> productKey = new MutableLiveData<>();
    private final MutableLiveData<String> networkState = new MutableLiveData<>();
    private final MutableLiveData<String> productState = new MutableLiveData<>();
    private final MutableLiveData<String> serverState = new MutableLiveData<>();
    // 四个检查状态：产品密钥、网络连接、系统状态、服务器通信
    private final MutableLiveData<CheckProgress[]> checkProgress = new MutableLiveData<>(
            new CheckProgress[]{CheckProgress.INIT, CheckProgress.INIT, CheckProgress.INIT, CheckProgress.INIT}
    );

    private final SplashRepository splashRepository;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        this.splashRepository = SplashRepository.getInstance(application);
    }

    public void checkDeviceState() {

        clearLiveData();

        disposables.add(
                performProductKetGet()
                        .andThen(performNetworkCheck())
                        .andThen(performProductCheck())
                        .andThen(performApiConnectivityCheck())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    Log.d(TAG, "所有检测完成");
//                                    updateFinalStatus();
                                },
                                throwable -> {
                                    Log.e(TAG, "检测过程中发生错误", throwable);
//                                    handleCheckError(throwable);
                                }
                        )
        );


    }

    private Completable performNetworkCheck() {

        return Observable.timer(800, TimeUnit.MILLISECONDS) // 模拟检测时间
                .doOnSubscribe(disposable -> updateCheckProgress(1, CheckProgress.CHECKING))
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(ignore -> splashRepository.checkNetworkState())
                .doOnNext(networkState -> {
                    if (networkState.isConnected()) {
                        this.networkState.postValue("网络连接正常");
                        updateCheckProgress(1, CheckProgress.SUCCESS);
                        Log.d(TAG, "网络检测完成: 连接正常");
                    } else {
                        this.networkState.postValue("网络连接失败");
                        updateCheckProgress(1, CheckProgress.FAILED);
                        Log.d(TAG, "网络检测完成: 连接失败");
                    }

                })
                .doOnError(throwable -> {
                    this.networkState.postValue("网络检测异常");
                    updateCheckProgress(1, CheckProgress.FAILED);
                    Log.e(TAG, "网络检测失败", throwable);
                })
                .ignoreElements() // 转换为Completable
                .onErrorComplete(); // 即使出错也继续执行下一个

    }

    private Completable performProductKetGet() {
        return splashRepository.getProductKey()
                .doOnSubscribe(disposable -> updateCheckProgress(0, CheckProgress.CHECKING))
                .observeOn(AndroidSchedulers.mainThread()) // 确保在主线程执行UI更新
                .doOnNext(key -> {
                    this.productKey.postValue(key);
                    updateCheckProgress(0, CheckProgress.SUCCESS);
                    Log.d(TAG, "产品密钥获取完成: " + key);
                })
                .doOnError(throwable -> {
                    this.productKey.postValue("未知（获取异常）");
                    updateCheckProgress(0, CheckProgress.FAILED);
                    Log.e(TAG, "获取productKey异常", throwable);
                })
                .ignoreElements() // 转换为Completable
                .onErrorComplete(); // 即使出错也继续执行下一个
    }


    private Completable performProductCheck() {
        return splashRepository.checkProductState()
                .doOnSubscribe(disposable -> updateCheckProgress(2, CheckProgress.CHECKING))
                .observeOn(AndroidSchedulers.mainThread()) // 确保在主线程执行UI更新
                .doOnNext(productState -> {

                    // TODO, 添加其他的硬件检测
                    if (productState.isPCBAEnabled()) {
                        this.productState.postValue("硬件检测正常");
                        updateCheckProgress(2, CheckProgress.SUCCESS);
                    } else {
                        this.productState.postValue("硬件检测异常");
                        updateCheckProgress(2, CheckProgress.FAILED);
                    }

                    Log.d(TAG, "硬件检测完成: " + productState);
                })
                .doOnError(throwable -> {
                    this.productState.postValue("硬件检测未知（获取异常）");

                    updateCheckProgress(2, CheckProgress.FAILED);
                    Log.e(TAG, "硬件检测异常", throwable);
                })
                .ignoreElements() // 转换为Completable
                .onErrorComplete(); // 即使出错也继续执行下一个
    }

    private Completable performApiConnectivityCheck() {
        return splashRepository.checkApiConnectivity()
                .doOnSubscribe(disposable -> updateCheckProgress(3, CheckProgress.CHECKING))
                .observeOn(AndroidSchedulers.mainThread()) // 确保在主线程执行UI更新
                .doOnNext(apiState -> {

                    this.serverState.postValue(apiState.getStatusMessage());
                    if (apiState.isAvailable()) {
                        updateCheckProgress(3, CheckProgress.SUCCESS);
                    } else {
                        updateCheckProgress(3, CheckProgress.FAILED);
                    }

                    Log.d(TAG, "后台API检测完成: " + apiState);
                })
                .doOnError(throwable -> {
                    this.serverState.postValue("后台API检测未知（获取异常）");

                    updateCheckProgress(3, CheckProgress.FAILED);
                    Log.e(TAG, "后台API检测异常", throwable);
                })
                .ignoreElements() // 转换为Completable
                .onErrorComplete(); // 即使出错也继续执行下一个
    }


    /**
     * 更新特定位置的检测进度
     * @param index 检测项目索引（0-3）
     * @param progress 检测状态
     */
    private void updateCheckProgress(int index, CheckProgress progress) {
        CheckProgress[] currentProgress = checkProgress.getValue();
        if (currentProgress != null && index >= 0 && index < currentProgress.length) {
            currentProgress[index] = progress;
            checkProgress.postValue(currentProgress.clone()); // 使用clone确保LiveData能检测到变化
        }
    }

    public MutableLiveData<String> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<String> getProductKey() {
        return productKey;
    }

    public MutableLiveData<String> getProductState() {
        return productState;
    }

    public MutableLiveData<String> getServerState() {
        return serverState;
    }

    public MutableLiveData<CheckProgress[]> getCheckProgress() {
        return checkProgress;
    }


    private void clearLiveData() {
        productKey.postValue(null);
        networkState.postValue(null);
        // 重置所有检测状态为初始状态
        checkProgress.postValue(new CheckProgress[]{
                CheckProgress.INIT, CheckProgress.INIT, CheckProgress.INIT, CheckProgress.INIT
        });
    }



}
