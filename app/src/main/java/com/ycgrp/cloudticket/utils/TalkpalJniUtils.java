package com.ycgrp.cloudticket.utils;

/**
 * Created by Administrator on 2016/9/12.
 */
public class TalkpalJniUtils {
    public static native String getStringFormC();
    static {
        System.loadLibrary("Talkpalkey");//之前在build.gradle里面设置的so名字，必须一致
         }
}
