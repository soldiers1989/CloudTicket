package com.ycgrp.cloudticket.model;

import android.os.Handler;


public class UserLoginModelImpl implements  UserLoginModel {
    @Override
    public void login(final String account, final String password, final UserLoginListener userLoginListener) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (account.equals("revstar")&&password.equals("123")){
                    userLoginListener.loginSuccess();
                }else {
                    userLoginListener.loginFailed();
                }
            }
        },3000);


    }
}
