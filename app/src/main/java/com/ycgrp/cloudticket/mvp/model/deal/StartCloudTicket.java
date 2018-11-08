package com.ycgrp.cloudticket.mvp.model.deal;


public interface StartCloudTicket {

    void startTicket(String amount,String guarantor_id,StartCloudTicketListener startCloudTicketListener);
}
