package com.ycgrp.cloudticket.mvp.model.impl;


import android.widget.Toast;

import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.GetRegisterInfoBean;
import com.ycgrp.cloudticket.mvp.model.deal.RegisterListener;
import com.ycgrp.cloudticket.mvp.model.deal.RegisterModel;


public class RegisterModelImpl implements RegisterModel {


    @Override
    public void register(String phonenumber, String name, String password, final RegisterListener registerListener) {
        NetServer.getInstance().register(phonenumber, name, password, new BaseCallBackListener<GetRegisterInfoBean>() {
            @Override
            public void onSuccess(GetRegisterInfoBean result) {
                registerListener.registerSuccess();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(CloudTicketApplication.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                registerListener.registerFaild();
                super.onError(e);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }
}
