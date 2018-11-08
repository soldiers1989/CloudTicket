package com.ycgrp.cloudticket.mvp.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.bean.LoginResponseBean;
import com.ycgrp.cloudticket.mvp.presenter.LoginBackPS;
import com.ycgrp.cloudticket.mvp.presenter.LoginPresenter;
import com.ycgrp.cloudticket.mvp.view.LoginView;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.GsonUtils;
import com.ycgrp.cloudticket.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity implements LoginView {


    /**
     * 输入手机号
     */
    @BindView(R.id.input_email)
    EditText _emailText;
    /**
     * 输入密码
     */
    @BindView(R.id.input_password)
    EditText _passwordText;
    /**
     * 登录
     */
    @BindView(R.id.btn_login)
    Button _loginButton;
    /**
     * 注册
     */
    @BindView(R.id.link_register)
    TextView link_register;
    /**
     * 动画
     */
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    /**
     * 登录Presenter
     */
    LoginPresenter mLoginPresenter;
    /**
     * 手机号和密码
     */
    String phonenumber, password;

    /**
     * rxPermission
     */
    RxPermissions rxPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getPermission();
        autoLogin();
        mLoginPresenter = new LoginPresenter(this, this);
        getData();
        //申请权限
//        Logger.addLogAdapter(new AndroidLogAdapter());
//        L.e("读取数据为"+LoginBackPS.getInstance().getCache(getApplicationContext()));
//        L.e("读取数据为2"+GsonUtil.toJsonString(LoginBackPS.getInstance().getCache(getApplicationContext())));
//        LoginResponseBean loginResponseBean=GsonUtils.getBean(LoginBackPS.getInstance().getCache(getApplicationContext()),LoginResponseBean.class);
//        L.e("读取数据为3"+loginResponseBean.getRefresh_token());
//        Logger.json(GsonUtil.toJsonString(GsonUtil.toJson(loginResponseBean)));
//        L.e("读取数据为4"+CloudTicketApplication.getInstance().getmLoginResponse().getRefresh_token());
//        L.e("读取数据为5"+CloudTicketApplication.getInstance().getmLoginResponse().getToken());
    }

    /**
     * 获取到手机号和密码
     */
    private void getData() {
        phonenumber = _emailText.getText().toString();
        password = _passwordText.getText().toString();
    }

    /**
     * 跳转到注册页面
     */
    @OnClick(R.id.link_register)
    public void toRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * 登录
     */
    @OnClick(R.id.btn_login)
    public void login() {
        getData();
        mLoginPresenter.login(phonenumber, password);
    }

    /**
     * 自动登录
     */

    public void autoLogin () {

        if (CloudTicketApplication.getInstance().getmLoginResponse()!=null&&CloudTicketApplication.getInstance().getmLoginResponse().getToken() != null) {
            L.e("Token值为"+CloudTicketApplication.getInstance().getmLoginResponse().getToken());
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }


    /**
     * 获取权限
     */
    public void getPermission() {

        rxPermissions = new RxPermissions(this);

        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                        if (aBoolean){

                        }
                        if (!aBoolean) {
                            showTastTips("权限被拒绝");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showLoading() {
        avi.setVisibility(View.VISIBLE);
        avi.show();
    }

    @Override
    public void hideLoading() {
        avi.setVisibility(View.GONE);
        avi.hide();
    }

    @Override
    public void success() {
        showTastTips("登录成功");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void failed() {
        showTastTips("登录失败,手机号或密码错误,请重新输入");
        _emailText.getText().clear();
        _passwordText.getText().clear();


    }
}
