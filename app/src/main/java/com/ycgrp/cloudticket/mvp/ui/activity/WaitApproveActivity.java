package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.adapter.WaitApproveAdapter;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.mvp.view.ApprovedOrRejected;
import com.ycgrp.cloudticket.utils.GsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 待审批贷款
 */
public class WaitApproveActivity extends BaseActivity implements ApprovedOrRejected {


    /**
     * recyclerview
     */
    @BindView(R.id.get_wait_approve_recycler_view)
    RecyclerView get_wait_approve_recycler_view;


    /**
     * 动画
     */
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    /**
     * 获取Context
     */
    private Context mContext;
    /**
     * 没有需要审批的贷款
     */
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    /**
     * 下拉刷新
     */

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 数据
     */
    private WaitApproveBean mWaitApproveBean ;

    /**
     * 适配器
     */
    private WaitApproveAdapter mWaitApproveAdapter ;
    /**
     * avi动画布局
     */
    @BindView(R.id.rel_av)
    RelativeLayout rel_av;
    /**
     * 整个布局
     */
    @BindView(R.id.rel_wait_approve)
    RelativeLayout rel_wait_approve;

    /**
     * 是否非第一次jiaz
     */
    private boolean isFirstLoading = true;
    /**
     * 接口
     */
    private ApprovedOrRejected mApprovedOrRejected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_approve);
        ButterKnife.bind(this);
        mWaitApproveBean=new WaitApproveBean();
        mApprovedOrRejected=this;
//        initRecyclerView();


        //获取待审批列表
        mContext = WaitApproveActivity.this;

        //显示动画
        rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.rel_av_bg_transparent_color));
        rel_av.setVisibility(View.VISIBLE);
        avi.setVisibility(View.VISIBLE);
        avi.show();
        getWaitApproveList(false);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                                getWaitApproveList(true);

            }
        });


    }


    /**
     * 获取待审批列表
     */
    public void getWaitApproveList(final boolean isRefresh) {

        NetServer.getInstance().getWaitApproveList(new BaseCallBackListener<WaitApproveBean>() {

            @Override
            public void onSuccess(WaitApproveBean result) {
                super.onSuccess(result);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);

                } else {
                    //隐藏动画
                    rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.white));
                    rel_av.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);
                    avi.hide();
                }
                //打印

                if (result != null) {

                    Logger.addLogAdapter(new AndroidLogAdapter());
                    Logger.json(GsonUtil.toJson(result));

                    //没有审批贷款显示
                    if (result.getData().size() == 0) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_data.setVisibility(View.INVISIBLE);

                    }
                    mWaitApproveBean = result;



                    if (isFirstLoading) {
                        //初始化recyclerview

                        mWaitApproveAdapter = new WaitApproveAdapter(mContext, mWaitApproveBean,mApprovedOrRejected);
//                    初始化recyclerview
                        get_wait_approve_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        get_wait_approve_recycler_view.setAdapter(mWaitApproveAdapter);
                        isFirstLoading = false;
                    } else {
                        mWaitApproveAdapter.setWaitApproveData(mWaitApproveBean);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

                if (!isRefresh) {
                    rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.white));
                    rel_av.setVisibility(View.GONE);
                    avi.hide();
                    avi.setVisibility(View.GONE);
                    showTastTips("获取失败" + e.getMessage());
                } else {
                    refreshLayout.finishRefresh(true);
                    showTastTips("刷新失败");
                }

                super.onError(e);

            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

    }

    /**
     * 同通过
     */

    @Override
    public void approved() {
        getWaitApproveList(false);

    }

    /**
     * 拒绝
     */
    @Override
    public void rejected() {

    }

}
