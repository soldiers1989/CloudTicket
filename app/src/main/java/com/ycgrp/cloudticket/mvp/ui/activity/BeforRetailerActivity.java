package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.adapter.BeforRetailerAdapter;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.BeforRetailerBean;
import com.ycgrp.cloudticket.mvp.view.AddRetailerID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 过往零售商
 */
public class BeforRetailerActivity extends BaseActivity implements AddRetailerID {


    /**
     * recyclerview显示过往零售商
     */
    @BindView(R.id.recy_befor_retailer)
    RecyclerView recyBeforRetailer;

    /**
     * adapter
     */
    BeforRetailerAdapter mBeforRetailerAdapter;


    /**
     * 没有过往零售商记录
     */
    @BindView(R.id.tv_not_retailer)
    TextView tvNotRetailer;
    /**
     * 接口返回ID
     */
    private AddRetailerID mAddRetailerID;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_befor_retailer);
        mContext = this;
        mAddRetailerID=this;
        ButterKnife.bind(this);
        NetServer.getInstance().getBeforRetailer(new BaseCallBackListener<BeforRetailerBean>() {
            @Override
            public void onSuccess(BeforRetailerBean result) {
                super.onSuccess(result);

                if (result != null) {
                    if (result.getData().size()==0){
                        //显示没有零售商记录
                        tvNotRetailer.setVisibility(View.VISIBLE);
                    }else{
                            // 设置数据
                        mBeforRetailerAdapter = new BeforRetailerAdapter(mContext, result,mAddRetailerID);
                        recyBeforRetailer.setLayoutManager(new LinearLayoutManager(mContext));
                        recyBeforRetailer.setAdapter(mBeforRetailerAdapter);
                    }

                }else {
                    //显示没有零售商记录
                    tvNotRetailer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showTastTips("获取失败" + e.getMessage());
            }
        });


    }

    @Override
    public void getSanRetailerID(String RetailerID) {
        if (RetailerID!=null){
            Intent intent=new Intent(this,StartCloudTicketActivity.class);
            intent.putExtra("scanRetailerID",RetailerID);
            startActivity(intent);
            finish();
        }else {
            showTastTips("添加零售商失败");
        }
    }
}
