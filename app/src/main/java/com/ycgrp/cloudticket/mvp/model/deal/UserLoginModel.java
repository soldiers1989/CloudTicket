package com.ycgrp.cloudticket.mvp.model.deal;


public interface UserLoginModel {

    void login(String account, String password, UserLoginListener userLoginListener);
}
