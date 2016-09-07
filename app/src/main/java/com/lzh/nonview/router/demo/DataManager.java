package com.lzh.nonview.router.demo;

/**
 * Created by admin on 16/9/7.
 */
public enum DataManager {
    INSTANCE;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
