package com.ycgrp.cloudticket.mvp.model.impl;


import android.util.Log;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.StartCloudTicketBean;
import com.ycgrp.cloudticket.mvp.model.deal.StartCloudTicket;
import com.ycgrp.cloudticket.mvp.model.deal.StartCloudTicketListener;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;

public class StartCloudTicketImpl  implements StartCloudTicket {

    @Override
    public void startTicket(final String amount, String guarantor_id, final StartCloudTicketListener startCloudTicketListener) {

        startCloudTicketListener.showLoading();
        NetServer.getInstance().startCloudTicket(amount, guarantor_id, new BaseCallBackListener<StartCloudTicketBean>() {
            @Override
            public void onSuccess(StartCloudTicketBean result) {
                super.onSuccess(result);

                Logger.addLogAdapter(new AndroidLogAdapter());
                Logger.json(GsonUtil.toJson(result));
                startCloudTicketListener.startSuccess();
                startCloudTicketListener.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.d("失败原因",e.getMessage());
                Toast.makeText(CloudTicketApplication.getContext(), "获取失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                startCloudTicketListener.startFailed();
                startCloudTicketListener.hideLoading();
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }
}
