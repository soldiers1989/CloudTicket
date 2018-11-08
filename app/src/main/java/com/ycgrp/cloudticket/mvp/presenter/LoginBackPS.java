package com.ycgrp.cloudticket.mvp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.utils.AESUtil;
import com.ycgrp.cloudticket.utils.L;
import com.ycgrp.cloudticket.utils.TalkpalJniUtils;


/**
 * 自己的状态数据
 */
public class LoginBackPS {
    private String loginJson = "loginJson";
    private static final String LOGIN_BACK = "loginback";
    private static LoginBackPS ourInstance = new LoginBackPS();
    private String key;

    public static LoginBackPS getInstance() {

        return ourInstance;
    }


    private String getInitpref(Context context) {
        key = getDefaultKey(context);
//        L.i("获取到的key为：" + key);
        return key;
    }


    public void writeToCache(Context context, String json) {
        try {
            if(TextUtils.isEmpty(key)){
                getInitpref(context);
            }
            String encrypt =null;
            try {
                encrypt = AESUtil.encrypt(key, json);
            } catch (java.security.NoSuchProviderException e) {
                L.e("加密失败");
                encrypt=json;
                e.printStackTrace();
            }
            SharedPreferences settings = context.getSharedPreferences(LOGIN_BACK, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(loginJson, encrypt);
            editor.commit();
//            Toast.makeText(context,"写入成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getCache(Context context) {

        SharedPreferences sharedata = context.getSharedPreferences(LOGIN_BACK, 0);
        String string = sharedata.getString(loginJson, null);
        try {
            if(!TextUtils.isEmpty(string)){
                if(TextUtils.isEmpty(key)){
                    getInitpref(context);
                    Toast.makeText(context,"读取成功",Toast.LENGTH_SHORT).show();
                }
                String decrypt =null;
                try {
                    decrypt = AESUtil.decrypt(key, string);
                    return decrypt;
                } catch (java.security.NoSuchProviderException e) {
//                    L.e("解密失败");
                    e.printStackTrace();
                    return string;
                }

//                String decrypt = AESUtil.decrypt(key, string);
//                return decrypt;
            }else{
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearCache(Context context) {
        SharedPreferences settings = context.getSharedPreferences(LOGIN_BACK, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    private String getBk4(Context context) {
        String string = TalkpalJniUtils.getStringFormC();
        return string;
    }

    private String getPrename(Context context) {
        return context.getResources().getString(R.string.myprefname);
    }

    /**
     * 获取最终的appKey字符串
     */
    private String getDefaultKey(Context context) {
        StringBuffer sb = new StringBuffer();
//        L.i("获取key");
//        sb.append(AppUtils.getBK1()).append(AppUtils.getBk2()).append(AESUtil.getBK3()).append(getBk4(context));
//        L.i("获取key完毕");
        return sb.toString();
    }

}