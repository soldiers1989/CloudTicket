package com.ycgrp.cloudticket.mvp.presenter;


import com.ycgrp.cloudticket.mvp.model.deal.StartCloudTicketListener;
import com.ycgrp.cloudticket.mvp.model.impl.StartCloudTicketImpl;

public class StartCloudTicketPresenter {

    private StartCloudTicketImpl mStartCloudTicketImpl;
    StartCloudTicketListener mStartCloudTicketListener;

    public StartCloudTicketPresenter(StartCloudTicketListener startCloudTicketListener) {

        this.mStartCloudTicketListener=startCloudTicketListener;
        mStartCloudTicketImpl=new StartCloudTicketImpl();

    }

    public void startCloudTicket(String amount,String guarantor_id){

        mStartCloudTicketImpl.startTicket(amount, guarantor_id, new StartCloudTicketListener() {
            @Override
            public void startSuccess() {
                mStartCloudTicketListener.startSuccess();
            }

            @Override
            public void startFailed() {
                mStartCloudTicketListener.startFailed();
            }

            @Override
            public void showLoading() {

                mStartCloudTicketListener.showLoading();
            }

            @Override
            public void hideLoading() {

                mStartCloudTicketListener.hideLoading();
            }
        });

    }


}
