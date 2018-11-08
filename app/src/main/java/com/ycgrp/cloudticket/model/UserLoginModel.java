package com.ycgrp.cloudticket.model;


public interface UserLoginModel {

    void login(String account, String password, UserLoginListener userLoginListener);
}
