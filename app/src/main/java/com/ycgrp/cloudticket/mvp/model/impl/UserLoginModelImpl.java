package com.ycgrp.cloudticket.mvp.model.impl;



import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.LoginResponseBean;
import com.ycgrp.cloudticket.mvp.model.deal.UserLoginListener;
import com.ycgrp.cloudticket.mvp.model.deal.UserLoginModel;
import com.ycgrp.cloudticket.mvp.presenter.LoginBackPS;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;


public class UserLoginModelImpl implements UserLoginModel {

    private Context mContext;
    public  UserLoginModelImpl(Context context){
        this.mContext=context;
    }
    @Override
    public void login(final String phonenumber, final String password, final UserLoginListener userLoginListener) {

        NetServer.getInstance().login(phonenumber, password, new BaseCallBackListener<LoginResponseBean>() {
            @Override
            public void onSuccess(LoginResponseBean result) {
                super.onSuccess(result);
                userLoginListener.loginSuccess();
                if (result!=null){
                    //打印
                    Logger.addLogAdapter(new AndroidLogAdapter());
                    Logger.json(GsonUtil.toJson(result));
                    LoginBackPS.getInstance().writeToCache(mContext,GsonUtil.toJsonString(result));
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

                userLoginListener.loginFailed();

                if (e!=null){
                    //打印
                    Logger.addLogAdapter(new AndroidLogAdapter());
                    Logger.json(GsonUtil.toJson(e));
                    L.e("失败原因"+e.getMessage());
                }


            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });


    }
}
