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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetAPI;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.AccountBlanceBean;
import com.ycgrp.cloudticket.bean.StartCloudTicketBean;
import com.ycgrp.cloudticket.event.MessageEvent;
import com.ycgrp.cloudticket.mvp.model.deal.StartCloudTicketListener;
import com.ycgrp.cloudticket.mvp.ui.activity.LoanRecordActivity;
import com.ycgrp.cloudticket.mvp.ui.activity.MyCloudTicketActivity;
import com.ycgrp.cloudticket.mvp.ui.activity.StartCloudTicketActivity;
import com.ycgrp.cloudticket.mvp.ui.activity.VerifiedActivity;
import com.ycgrp.cloudticket.mvp.ui.activity.WaitApproveActivity;
import com.ycgrp.cloudticket.utils.Constants;
import com.ycgrp.cloudticket.utils.DisplayUtils;
import com.ycgrp.cloudticket.utils.GlideImageLoader;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class IndexFragment extends Fragment  {


    public static final String TAG = "MeFragment";

    /**
     * 切换刷新
     */
    protected boolean isCreated = false;

//    /***
//     * Banner
//     */
//    @BindView(R.id.banner)
//    Banner mBanner;
    /**
     * 发起云票
     */
    @BindView(R.id.rel_start_cloud_ticket)
    RelativeLayout rel_start_cloud_ticket;
    /**
     * 审批云票
     */
    @BindView(R.id.rel_cheack_cloud_ticket)
    RelativeLayout rel_cheack_cloud_ticket;
    /**
     * 我的云票
     */
    @BindView(R.id.rel_my_cloud_ticket)
    RelativeLayout rel_my_cloud_ticket;
    /**
     * 借款记录
     */
    @BindView(R.id.rel_loan_record)
    RelativeLayout rel_loan_record;

    /**
     * 益才农业
     */
    @BindView(R.id.yicai)
    RelativeLayout yicai;

    /**
     * ImmersionBar
     */
    private ImmersionBar mImmersionBar;
    /**
     * 投资云票
     */
    @BindView(R.id.rel_investment_cloud_ticket)
    RelativeLayout rel_investment_cloud_ticket;

    /**
     * 审批记录
     */
    @BindView(R.id.rel_approval_record)
    RelativeLayout rel_approval_record;

    @BindView(R.id.rel_verified)
    RelativeLayout rel_verified;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 标记
        isCreated = true;
//        setTitleVisiable(false);
        mImmersionBar = ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary);
        mImmersionBar.init();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
//        mBanner.startAutoPlay();

    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
//        mBanner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
//        //图片
//        List<?> images = new ArrayList<>();
//        //轮播图片地址
//        String url1 = "http://bmob-cdn-21019.b0.upaiyun.com/2018/11/02/552f1a8f40276dac802db877e231aa4a.png";
//        String url2 = "http://bmob-cdn-21019.b0.upaiyun.com/2018/11/02/84334d7240ff28c4800dc12c813a18f4.png";
//        String[] urls = {url1, url2};
//        List list = Arrays.asList(urls);
//        images = new ArrayList<>(list);
//        mBanner.setImages(images)
//                .setImageLoader(new GlideImageLoader())
//                .start();


    }

    /**
     * 发布云票
     */
    @OnClick(R.id.rel_start_cloud_ticket)
    public void startCloudTicket() {

        startActivity(new Intent(getActivity(),StartCloudTicketActivity.class));



    }

    /**
     * 审核云票
     */
    @OnClick(R.id.cheack_cloud_ticket)
    public void checkCloudTicket() {
        startActivity(new Intent(getActivity(),WaitApproveActivity.class));
    }

    /**
     * 我的云票
     */
    @OnClick(R.id.rel_my_cloud_ticket)
    public void GetMyCloudTicket(){

        startActivity(new Intent(getActivity(), MyCloudTicketActivity.class));

    }
    @OnClick(R.id.rel_loan_record)
    public void getMyLoanRecord(){
        startActivity(new Intent(getActivity(),LoanRecordActivity.class));
    }

    /**
     * 投资云票
     */
    @OnClick(R.id.rel_investment_cloud_ticket)
    public  void investmentCloudTicket(){
        EventBus.getDefault().post(new MessageEvent(Constants.investment_cloud_ticket));
    }

    /**
     * 审批记录
     */
    @OnClick(R.id.rel_approval_record)
    public  void approveRecord(){
        startActivity(new Intent(getActivity(),WaitApproveActivity.class));
    }

    /**
     * 实名认证
     */
    @OnClick(R.id.rel_verified)
    public  void Verified(){
        startActivity(new Intent(getActivity(),VerifiedActivity.class));
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
//            setTitle("首页");
            mImmersionBar.statusBarColor(R.color.colorPrimary).init();
        }
    }


}
