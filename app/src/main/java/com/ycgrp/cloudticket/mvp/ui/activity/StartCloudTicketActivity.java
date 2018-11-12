package com.ycgrp.cloudticket.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.UserInfoBean;
import com.ycgrp.cloudticket.mvp.model.deal.StartCloudTicketListener;
import com.ycgrp.cloudticket.mvp.presenter.StartCloudTicketPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class StartCloudTicketActivity extends BaseActivity implements StartCloudTicketListener {




    /**
     * 零售商名字
     */
    @BindView(R.id.retailer_name)
    TextView retailerName;
    /**
     * 零售商地址
     */
    @BindView(R.id.retailer_address)
    TextView retailerAddress;
    /**
     * 扫码添加零售商
     */
    @BindView(R.id.rel_add_retailer)
    RelativeLayout tvAddRetailer;
    /**
     * 选择过往零售商
     */
    @BindView(R.id.rel_select_befor_retailer)
    RelativeLayout rel_select_befor_retailer;
    /**
     * 输入价格
     */
    @BindView(R.id.input_price)
    TextInputEditText inputPrice;
    /**
     * 提交申请
     */
    @BindView(R.id.submit)
    TextView submit;

    /**
     * 金额和担保人id
     */
    private String amount;

    /**
     * 发起云票
     */
    private StartCloudTicketPresenter mStartCloudTicketPresenter;

    /**
     * 动画avi
     */
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;


    /**
     * 零售商ID
     */
    String scanRetailerID;

    /**
     * rxPermission
     */
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_cloud_ticket);
        ButterKnife.bind(this);
        mStartCloudTicketPresenter = new StartCloudTicketPresenter(this);
        //获取相机存储权限
        getPermission();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        scanRetailerID = intent.getStringExtra("scanRetailerID");
//        showTastTips("扫码获取到零售商的ID"+scanRetailerID);
        //设置零售商信息
        if (scanRetailerID != null) {
            NetServer.getInstance().getUserInfo(scanRetailerID, new BaseCallBackListener<UserInfoBean>() {
                @Override
                public void onSuccess(UserInfoBean result) {
                    super.onSuccess(result);
                    if (result != null) {
//                        设置姓名
                        retailerName.setText(result.getData().getAttributes().getName());
                        //最后一次实名认证的id
                        String lastProfilesID = result.getData().getRelationships().getProfiles().getData().get(result.getData().getRelationships().getProfiles().getData().size() - 1).getId();
                        for (UserInfoBean.IncludedBean ic : result.getIncluded()) {
                            if (ic.getId().equals(lastProfilesID)) {
                                //设置地址
                                retailerAddress.setText(ic.getAttributes().getAddress());
                            }
                        }
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
        }
    }


    /**
     * 云票发起成功
     */

    @Override
    public void startSuccess() {
        showTastTips("发布云票成功");
        //清除文本
        retailerName.setText("");
        retailerAddress.setText("");
        inputPrice.getText().clear();
        scanRetailerID=null;

    }

    /**
     * 云票发起失败
     */
    @Override
    public void startFailed() {
        showTastTips("发布云票失败");
        //清除文本
        retailerName.setText("");
        retailerAddress.setText("");
        inputPrice.getText().clear();
        scanRetailerID=null;
    }

    /**
     * 显示动画
     */
    @Override
    public void showLoading() {
        avi.setVisibility(View.VISIBLE);
        avi.show();
    }

    /**
     * 隐藏动画
     */
    @Override
    public void hideLoading() {
        avi.setVisibility(View.GONE);
        avi.hide();

    }

    @OnClick(R.id.rel_add_retailer)
    public void scanAddRetailer() {
        //获取相机存储权限
        getPermission();
        startActivity(new Intent(this, ScanActivity.class));

    }




    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @OnClick(R.id.submit)
    public void submit() {
        amount = inputPrice.getText().toString();
        if (scanRetailerID==null){
            showTastTips("请添加零售商");
        }else if (amount.isEmpty()) {
            showTastTips("输入金额不能为空");
        } else if (Integer.parseInt(amount) <= 0) {
            showTastTips("输入金额必须大于0");
        }  else {
            mStartCloudTicketPresenter.startCloudTicket(amount, scanRetailerID);

        }
    }

    @OnClick(R.id.rel_select_befor_retailer)
    public void selectBeforRetailer(){
        startActivity(new Intent(this,BeforRetailerActivity.class));
    }
    /**
     * 获取权限
     */
    public void getPermission() {

        rxPermissions = new RxPermissions(this);

        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                        if (aBoolean) {

                        }
                        if (!aBoolean) {
                            showTastTips("相机权限被拒绝");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
