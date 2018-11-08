package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.mvp.presenter.RegisterPresenter;
import com.ycgrp.cloudticket.mvp.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterView {
    private static final String TAG = "RegisterActivity";
    private RegisterPresenter mRegisterPresenter;
    private String name,phonenumber,passwordOne,passwordTwo;
    @BindView(R.id.input_name) EditText _nameText;//
    @BindView(R.id.input_phone) EditText _phone;//
    @BindView(R.id.input_password_one) EditText _input_password_one;//
    @BindView(R.id.input_password_two) EditText _input_password_two;//
    @BindView(R.id.btn_signup) Button _signupButton;//
    @BindView(R.id.link_login) TextView _loginLink;//

    /**
     * 动画
     */
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

    }

    /**
     * 初始化数据
     */
    private void getData() {
        mRegisterPresenter=new RegisterPresenter(this,this);
        name=_nameText.getText().toString();
        phonenumber=_phone.getText().toString();
        passwordOne=_input_password_one.getText().toString();
        passwordTwo=_input_password_two.getText().toString();


    }


    /**
     * 点击按钮注册
     */
    @OnClick(R.id.btn_signup)
    public void register(){
        getData();
        //加载动画
        avi.setVisibility(View.VISIBLE);
        avi.show();
        mRegisterPresenter.register(phonenumber,name,passwordOne,passwordTwo);
    }

    /**
     * 已有账号跳转到登录
     */
    @OnClick(R.id.link_login)
    public void link_login(){
        startActivity(new Intent(this,LoginActivity.class));
    }

    /**
     * 注册成功
     */
    @Override
    public void success() {
        //隐藏动画
        avi.setVisibility(View.GONE);
        avi.hide();
        showTastTips("注册成功,请登录");
        finish();

    }

    /**
     * 注册失败
     */
    @Override
    public void failed() {
        //隐藏动画
        avi.hide();
        showTastTips("注册失败");

    }
}