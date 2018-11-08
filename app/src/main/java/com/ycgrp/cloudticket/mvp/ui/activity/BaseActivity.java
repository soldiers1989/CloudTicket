package com.ycgrp.cloudticket.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {

    /**
     * 沉浸式状态栏
     */
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }



    private void initData() {
        //沉浸式状态栏
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();


    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    public void showTastTips(String tips) {
        if (tips != null) {

            Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();
        }
    }

}
