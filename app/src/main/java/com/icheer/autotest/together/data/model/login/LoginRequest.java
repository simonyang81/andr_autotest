package com.icheer.autotest.together.data.model.login;

public class LoginRequest {
    private String mobile;
    private String password;
    private String sense;

    public LoginRequest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
        this.sense = "device";
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }
}
