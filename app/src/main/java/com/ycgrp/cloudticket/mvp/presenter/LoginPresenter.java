package com.ycgrp.cloudticket.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ycgrp.cloudticket.mvp.model.impl.UserLoginModelImpl;
import com.ycgrp.cloudticket.mvp.model.deal.UserLoginListener;
import com.ycgrp.cloudticket.mvp.view.LoginView;


public class LoginPresenter {

    private LoginView mLoginView;
    private UserLoginModelImpl mUserLoginModel;
    private Context mContext;

    public LoginPresenter(Context context, LoginView loginView) {

        this.mLoginView=loginView;
        this.mContext = context;
        mUserLoginModel = new UserLoginModelImpl(context);

    }

    public void login(String phonenumber, String passWord) {
        mLoginView.showLoading();
        if (TextUtils.isEmpty(phonenumber) || TextUtils.isEmpty(passWord)) {
            mLoginView.hideLoading();
            Toast.makeText(mContext,"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();

        }else {
            mUserLoginModel.login(phonenumber, passWord, new UserLoginListener() {
                @Override
                public void loginSuccess() {
                    mLoginView.success();
                    mLoginView.hideLoading();


                }

                @Override
                public void loginFailed() {
                    mLoginView.failed();
                    mLoginView.hideLoading();
                }
            });
        }

    }


}
