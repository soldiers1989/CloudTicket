package com.ycgrp.cloudticket.mvp.model.deal;


public interface StartCloudTicketListener {

    void startSuccess();
    void startFailed();
    void showLoading();
    void hideLoading();
}
