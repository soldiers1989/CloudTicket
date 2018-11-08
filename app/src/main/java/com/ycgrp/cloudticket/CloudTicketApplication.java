package com.ycgrp.cloudticket;

import android.app.Application;
import android.content.Context;

import com.ycgrp.cloudticket.bean.LoginResponseBean;
import com.ycgrp.cloudticket.bean.VisitBean;
import com.ycgrp.cloudticket.mvp.presenter.LoginBackPS;
import com.ycgrp.cloudticket.utils.GsonUtils;


public class CloudTicketApplication extends Application {

    private static CloudTicketApplication app;
    private static Context context;
    //用户登录返回
    private LoginResponseBean mLoginResponseBean;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = getApplicationContext();

    }


    public static Context getContext() {

        return context;
    }


    public static CloudTicketApplication getInstance() {

        return app;

    }


    public void setmLoginResponse(LoginResponseBean mLoginResponseBean) {
        this.mLoginResponseBean = mLoginResponseBean;
    }


    /**
     * 获取登录信息
     *
     * @return
     */
    public LoginResponseBean getmLoginResponse() {
        if (mLoginResponseBean == null) {
            String cache = LoginBackPS.getInstance().getCache(this);
            this.mLoginResponseBean = GsonUtils.getBean(cache, LoginResponseBean.class);

        }
        return mLoginResponseBean;
    }





}
