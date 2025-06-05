package com.icheer.autotest.together.ui.login.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.icheer.autotest.together.data.manager.UserSessionManager;
import com.icheer.autotest.together.data.model.base.BaseResponse;
import com.icheer.autotest.together.data.model.login.LoginResponse;
import com.icheer.autotest.together.data.repository.login.AuthRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 登录页面的ViewModel
 * 负责处理登录相关的业务逻辑和数据管理
 *
 * @author SIMON Y
 * @version 1.0
 * @since 2025
 */
public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final MutableLiveData<String> usernameError = new MutableLiveData<>();
    private final MutableLiveData<String> passwordError = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final AuthRepository authRepository;

    private final UserSessionManager sessionManager;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.authRepository = AuthRepository.getInstance(application);
        this.sessionManager = UserSessionManager.getInstance(application);
    }

    public void login(String username, String password) {

        // 清除之前的错误信息
        clearErrors();

        // 验证输入
        if (!validateInput(username, password)) {
            return;
        }

        // 执行登录请求
        disposables.add(
                authRepository.login(username, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::onLoginSuccess,
                                this::onLoginError
                        )
        );

        // 显示加载状态
        isLoading.setValue(true);

    }

    private void onLoginSuccess(BaseResponse<LoginResponse> response) {
        isLoading.setValue(false);

        Log.d(TAG, "response: " + response);
        if (response.isSuccess()) {
            // 业务成功 - 保存用户信息，设置登录成功状态
            // 保存登录会话信息
            sessionManager.saveLoginSession(response.getResult());
            loginSuccess.setValue(true);
        } else {
            // 业务失败 - 显示服务器返回的错误信息
            errorMessage.setValue(response.getMessage());
            loginSuccess.setValue(false);
        }

    }

    private void onLoginError(Throwable throwable) {
        isLoading.setValue(false);
        Log.e(TAG, "登录请求失败", throwable);
        errorMessage.setValue("登录请求失败");
        loginSuccess.setValue(false);
    }

    // Getters for LiveData
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getUsernameError() {
        return usernameError;
    }

    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    /**
     * 验证用户输入
     *
     * @param username 用户名
     * @param password 密码
     * @return true if valid, false otherwise
     */
    private boolean validateInput(String username, String password) {
        boolean isValid = true;

        // 验证用户名
        if (username == null || username.trim().isEmpty()) {
            usernameError.setValue("请输入用户名");
            isValid = false;
        } else if (username.length() < 3) {
            usernameError.setValue("用户名至少需要3个字符");
            isValid = false;
        }

        // 验证密码
        if (password == null || password.trim().isEmpty()) {
            passwordError.setValue("请输入密码");
            isValid = false;
        } else if (password.length() < 6) {
            passwordError.setValue("密码至少需要6个字符");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrors() {
        usernameError.setValue(null);
        passwordError.setValue(null);
        errorMessage.setValue(null);
    }

}
