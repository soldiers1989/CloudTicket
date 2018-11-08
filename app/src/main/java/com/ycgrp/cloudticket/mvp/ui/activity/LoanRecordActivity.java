package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.adapter.MyLoanRecordAdapter;
import com.ycgrp.cloudticket.adapter.WaitApproveAdapter;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.MyLoanRecordBean;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.utils.GsonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 借款记录
 */
public class LoanRecordActivity extends BaseActivity {

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
     * 没有借款记录
     */
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    /**
     * 显示我的借款
     */
    @BindView(R.id.get_my_loan_record_recycler_view)
    RecyclerView get_my_loan_record_recycler_view;
    /**
     * 是否非第一次jiaz
     */
    private boolean isFirstLoading = true;

    /**
     * 整个布局
     */
    @BindView(R.id.line_loan_record)
    RelativeLayout  line_loan_record;


    /**
     * 动画
     */
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    /**
     * bean
     */
    MyLoanRecordBean mMyLoanRecordBean;
    /**
     * adapter
     */
    MyLoanRecordAdapter mMyLoanRecordAdapter;

    /**
     * 获取Context
     */
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_record);
        ButterKnife.bind(this);
        mMyLoanRecordBean=new MyLoanRecordBean();
        mContext=LoanRecordActivity.this;

        //显示动画
        line_loan_record.setBackgroundColor(getResources().getColor(R.color.rel_av_bg_transparent_color));
        rel_av.setVisibility(View.VISIBLE);
        avi.setVisibility(View.VISIBLE);
        avi.show();
        getMyLoanRecord(false);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMyLoanRecord(true);

            }
        });


    }

    /**
     * 获取借款
     */
    public void getMyLoanRecord(final boolean isRefresh){
        NetServer.getInstance().getMyLoanRecord(new BaseCallBackListener<MyLoanRecordBean>() {
            @Override
            public void onSuccess(MyLoanRecordBean result) {
                super.onSuccess(result);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);

                } else {
                    //隐藏动画
                    line_loan_record.setBackgroundColor(getResources().getColor(R.color.white));
                    rel_av.setVisibility(View.GONE);
                    avi.setVisibility(View.GONE);
                    avi.hide();
                }

                if (result != null) {

                    Logger.addLogAdapter(new AndroidLogAdapter());
                    Logger.json(GsonUtil.toJson(result));

                    //没有审批贷款显示
                    if (result.getData().size() == 0) {
                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
                        tv_no_data.setVisibility(View.INVISIBLE);

                    }
                    mMyLoanRecordBean = result;



                    if (isFirstLoading) {
                        //初始化recyclerview

                        mMyLoanRecordAdapter = new MyLoanRecordAdapter(mContext, mMyLoanRecordBean);
//                    初始化recyclerview
                        get_my_loan_record_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        get_my_loan_record_recycler_view.setAdapter(mMyLoanRecordAdapter);
                        isFirstLoading = false;
                    } else {
                        mMyLoanRecordAdapter.setMyLoanRecordData(mMyLoanRecordBean);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }




}
