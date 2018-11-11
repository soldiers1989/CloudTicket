package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.adapter.MyCloudTicketAdapter;
import com.ycgrp.cloudticket.adapter.WaitApproveAdapter;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.MyInfoBean;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.event.MyCloudTicketBean;
import com.ycgrp.cloudticket.mvp.view.GetDetail;
import com.ycgrp.cloudticket.mvp.view.ReleaseOrRecallView;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的云票
 */
public class MyCloudTicketActivity extends BaseActivity implements ReleaseOrRecallView,GetDetail {

    /**
     * 显示我的云票
     */
    @BindView(R.id.get_my_cloud_ticket_recycler_view)
    RecyclerView get_my_cloud_ticket_recycler_view;

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
     * name
     */
    public String name = "";
    /**
     * 下拉刷新
     */

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
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
    private ReleaseOrRecallView mReleaseOrRecallView;
    /**
     * 是否非第一次jiaz
     */
    private boolean isFirstLoading = true;
    /**
     * adapter
     */
    private MyCloudTicketAdapter mMyCloudTicketAdapter;
    /**
     * dateBean
     */
    private MyCloudTicketBean mMyCloudTicketBean;
    /**
     * 没有获得云票
     */
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    private GetDetail mGetDetail;//获取云票详情

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cloud_ticket);
        ButterKnife.bind(this);
        mContext = MyCloudTicketActivity.this;
        mReleaseOrRecallView = this;
       mGetDetail=this;
        mMyCloudTicketBean = new MyCloudTicketBean();
        L.e("MyCloudTickrt--"+"Oncreate()");
        getMyInfo();
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getWaitApproveList(true);

            }
        });

    }


    /**
     * 获取自己的信息
     */
    private void getMyInfo() {

        //显示加载
        //显示动画
        rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.rel_av_bg_transparent_color));
        rel_av.setVisibility(View.VISIBLE);
        avi.setVisibility(View.VISIBLE);
        avi.show();

        NetServer.getInstance().getMyInfo(new BaseCallBackListener<MyInfoBean>() {
            @Override
            public void onSuccess(MyInfoBean result) {
                super.onSuccess(result);
                if (result!=null&&result.getData()!=null&&result.getData().getAttributes()!=null&&result.getData().getAttributes().getName()!=null){
                    name = result.getData().getAttributes().getName();

                }
                getWaitApproveList(false);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //显示动画
                rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.white));
                rel_av.setVisibility(View.GONE);
                avi.setVisibility(View.GONE);
                avi.hide();

            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }

    /**
     * 获取自己的云票
     */
    public void getWaitApproveList(final Boolean isRefresh) {

        NetServer.getInstance().getMyCloudTicket(new BaseCallBackListener<MyCloudTicketBean>() {

            @Override
            public void onSuccess(MyCloudTicketBean result) {
                super.onSuccess(result);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {

                    //隐藏动画
                    //显示动画
                    rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.white));
                    rel_av.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);
                    avi.hide();
                }
                //打印
                if (result != null) {

                    Logger.addLogAdapter(new AndroidLogAdapter());
                    Logger.json(GsonUtil.toJson(result));

                    mMyCloudTicketBean = result;
                    //没有审批贷款显示
                    if (result.getData().size() == 0) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_data.setVisibility(View.INVISIBLE);

                    }
                    if (isFirstLoading) {
                        //初始化recyclerview

                        mMyCloudTicketAdapter = new MyCloudTicketAdapter(mContext, mReleaseOrRecallView, mGetDetail,mMyCloudTicketBean, name);
                        get_my_cloud_ticket_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        get_my_cloud_ticket_recycler_view.setAdapter(mMyCloudTicketAdapter);
                        isFirstLoading = false;
                    } else {
                        mMyCloudTicketAdapter.setCloudData(mMyCloudTicketBean);
                    }


                }
            }

            @Override
            public void onError(Throwable e) {

                if (!isRefresh) {
                    //显示动画
                    rel_wait_approve.setBackgroundColor(getResources().getColor(R.color.white));
                    rel_av.setVisibility(View.GONE);
                    avi.hide();
                    avi.setVisibility(View.GONE);
                    showTastTips("获取失败" + e.getMessage());
                } else {
                    refreshLayout.finishRefresh(true);
                    showTastTips("刷新失败");
                }

                showTastTips("获取失败" + e.getMessage());
                super.onError(e);

            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

    }

    @Override
    public void releaseSuccess() {

        showTastTips("发布成功");
        getWaitApproveList(false);

    }

    @Override
    public void recallSuccess() {

        getWaitApproveList(false);
    }

    @Override
    public void getDetail(String id, String releaseID) {
        Intent intent=new Intent(this,CloudTicketDetailsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("releaseID",releaseID);
        startActivity(intent);
    }
}
