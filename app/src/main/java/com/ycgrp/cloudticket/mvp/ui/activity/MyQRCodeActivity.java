package com.ycgrp.cloudticket.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.MyInfoBean;
import com.ycgrp.cloudticket.utils.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyQRCodeActivity extends AppCompatActivity {


    /**
     * 二维码
     */
    @BindView(R.id.QR_CODE)
    ImageView QRCODE;

    private Bitmap bitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);
        ButterKnife.bind(this);
        setQRCODE();

    }

    /**
     * 设置二维码
     *
     */
    public void setQRCODE() {
        NetServer.getInstance().getMyInfo(new BaseCallBackListener<MyInfoBean>() {
            @Override
            public void onSuccess(MyInfoBean result) {
                super.onSuccess(result);
                bitmap=CodeUtils.Create2DCode(result.getData().getId(),250,250);
                QRCODE.setImageBitmap(bitmap);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap!=null){
            bitmap.recycle();
        }
    }
}
