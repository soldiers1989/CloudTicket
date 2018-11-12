package com.ycgrp.cloudticket.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.AccountBlanceBean;
import com.ycgrp.cloudticket.bean.CloudTticketDetailsBean;
import com.ycgrp.cloudticket.bean.MyInfoBean;
import com.ycgrp.cloudticket.event.MessageEvent;
import com.ycgrp.cloudticket.mvp.presenter.LoginBackPS;
import com.ycgrp.cloudticket.mvp.ui.activity.LoginActivity;
import com.ycgrp.cloudticket.mvp.ui.activity.SettingActivity;
import com.ycgrp.cloudticket.utils.Constants;
import com.ycgrp.cloudticket.utils.L;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MeFragment extends BaseFragment implements View.OnClickListener {


    public static final String TAG="MeFragment";

    /**
     * 切换刷新
     */
    protected boolean isCreated = false;
    /**
     * 退出登录
     */
    @BindView(R.id.btn_login_out)
   Button btn_login_out;
    /**
     * ImmersionBar
     */
    private ImmersionBar mImmersionBar;

    /**
     * 名字
     */
    @BindView(R.id.name)
    TextView name;
    /**
     * phoneNumber
     */
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    /**
     * 可用余额
     */
    @BindView(R.id.tv_avaiavle_num)
    TextView tv_avaiavle_num;

    /**
     * 累计收益
     */
    @BindView(R.id.tv_cumulative_income_num)
    TextView tv_cumulative_income_num;
    /**
     * 设置
     */
    @BindView(R.id.rel_setting)
    RelativeLayout rel_setting;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 标记
        isCreated = true;
        mImmersionBar = ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true);
        mImmersionBar.init();

    }

    /**
     * 设置个人信息
     */
    private void setInfo() {
//        设置名字和手机
        NetServer.getInstance().getMyInfo(new BaseCallBackListener<MyInfoBean>() {
            @Override
            public void onSuccess(MyInfoBean result) {
                super.onSuccess(result);
                if (result!=null){
                    name.setText(result.getData().getAttributes().getName());
                    phoneNumber.setText(result.getData().getAttributes().getPhone_number());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
//        设置可用余额和累计收益
        NetServer.getInstance().getAccountBlance(new BaseCallBackListener<AccountBlanceBean>() {
            @Override
            public void onSuccess(AccountBlanceBean result) {
                super.onSuccess(result);
                if (result!=null){
                    tv_avaiavle_num.setText(result.getData().getAttributes().getBalance());
                    tv_cumulative_income_num.setText(result.getData().getAttributes().getAccumulated_earnings());

                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_me,container,false);
        ButterKnife.bind(this,view);
        initListener();
        setInfo();
        return view;
    }

    private void initListener() {
        btn_login_out.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
//            setTitle("我的");
            mImmersionBar.transparentStatusBar()
                    .statusBarDarkFont(true)
                    .init();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_out:
                LoginBackPS.getInstance().clearCache(getContext());
                EventBus.getDefault().post(new MessageEvent(Constants.loginout));
                break;
        }

    }

    /**
     * 设置
     */
    @OnClick(R.id.rel_setting)
    public  void setting(){
        startActivity(new Intent(getActivity(),SettingActivity.class));
    }
}
