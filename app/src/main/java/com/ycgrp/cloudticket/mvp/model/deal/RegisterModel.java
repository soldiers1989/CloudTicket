package com.ycgrp.cloudticket.mvp.model.deal;


public interface RegisterModel {

    void register(String phonenumber,String name,String password,RegisterListener registerListener);
}
