package com.ycgrp.cloudticket.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.event.MessageEvent;
import com.ycgrp.cloudticket.mvp.presenter.LoginBackPS;
import com.ycgrp.cloudticket.mvp.ui.activity.LoginActivity;
import com.ycgrp.cloudticket.utils.Constants;
import com.ycgrp.cloudticket.utils.L;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_me,container,false);
        ButterKnife.bind(this,view);
        initListener();
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
}
