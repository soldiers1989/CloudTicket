package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import com.ycgrp.cloudticket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    ZXingView mZXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);


        mZXingView.setDelegate(this);



    }
    @Override
    protected void onStart() {
        super.onStart();

        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        if (mZXingView!=null){
            mZXingView.onDestroy(); // 销毁二维码扫描控件

        }
        super.onDestroy();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {

        showTastTips("扫描结果为"+result);
        vibrate();
        mZXingView.startSpot(); // 延迟0.1秒后开始识别
        Intent intent=new Intent(this,StartCloudTicketActivity.class);
        intent.putExtra("scanRetailerID",result);
        startActivity(intent);
        finish();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
       showTastTips("打开相机出错");
    }


}
