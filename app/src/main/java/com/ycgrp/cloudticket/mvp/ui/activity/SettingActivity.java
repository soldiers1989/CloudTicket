package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.ycgrp.cloudticket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {


    /**
     * 显示我的二维码
     */
    @BindView(R.id.my_qr_code)
    Button my_qr_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.my_qr_code)
    public void  displayMyQRCODE(){
        startActivity(new Intent(SettingActivity.this,MyQRCodeActivity.class));
    }
}
