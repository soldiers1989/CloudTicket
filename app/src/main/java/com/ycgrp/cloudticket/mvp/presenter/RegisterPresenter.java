package com.ycgrp.cloudticket.mvp.presenter;


import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.ycgrp.cloudticket.mvp.model.deal.RegisterListener;
import com.ycgrp.cloudticket.mvp.model.impl.RegisterModelImpl;
import com.ycgrp.cloudticket.mvp.view.RegisterView;

public class RegisterPresenter {
    private RegisterModelImpl mRegisterModelImpl;
    private RegisterView mRegisterView;
    private Context mContext;

    public RegisterPresenter(Context context,RegisterView registerView){
        this.mRegisterView=registerView;
        this.mContext=context;
        mRegisterModelImpl=new RegisterModelImpl();
    }
    public void register(String phonenumber,String name,String passwordOne,String passwordTow){
        if (TextUtils.isEmpty(name)){
            Toast.makeText(mContext,"用户名不能为空",Toast.LENGTH_SHORT).show();
            mRegisterView.failed();
        }else if (TextUtils.isEmpty(phonenumber)){
            Toast.makeText(mContext,"手机号不能为空",Toast.LENGTH_SHORT).show();
            mRegisterView.failed();
        }else if (TextUtils.isEmpty(passwordOne)||TextUtils.isEmpty(passwordTow)){
            Toast.makeText(mContext,"密码不能为空",Toast.LENGTH_SHORT).show();
            mRegisterView.failed();
        }else if (!passwordOne.equals(passwordTow)){
            Toast.makeText(mContext,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            mRegisterView.failed();
        }else {
            mRegisterModelImpl.register(phonenumber, name, passwordOne, new RegisterListener() {
                @Override
                public void registerSuccess() {
                    mRegisterView.success();
                }

                @Override
                public void registerFaild() {
                    mRegisterView.failed();
                }
            });
        }
    }
}
