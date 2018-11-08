package com.ycgrp.cloudticket.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.adapter.MyCloudTicketAdapter;
import com.ycgrp.cloudticket.adapter.TradeAdapter;
import com.ycgrp.cloudticket.adapter.WaitApproveAdapter;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.TradeBean;
import com.ycgrp.cloudticket.event.MyCloudTicketBean;
import com.ycgrp.cloudticket.mvp.ui.activity.MyCloudTicketActivity;
import com.ycgrp.cloudticket.mvp.view.BuySuccess;
import com.ycgrp.cloudticket.utils.DateUtils;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投资云票
 */
public class TradeFragment extends BaseFragment implements BuySuccess {

    public static final String TAG = "TradeFragment";
    /**
     * 切换刷新
     */
    protected boolean isCreated = false;

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
     * 下拉刷新
     */

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * 布局
     */
    View trade_layout;

    /**
     * adapter
     */
    private TradeAdapter mTradeAdapter;
    /**
     * bean
     */
    private TradeBean mTradeBean;
    /**
     * 是否非第一次jiaz
     */
    private boolean isFirstLoading = true;
    /**
     * 接口买入成功回调刷新
     */
    private BuySuccess mBuySuccess;
    /**
     * ImmersionBar
     */
    private ImmersionBar mImmersionBar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this)
          .statusBarColor(R.color.white)
        .statusBarDarkFont(true);
        mImmersionBar.init();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        trade_layout=inflater.inflate(R.layout.fragment_trade, container, false);
        ButterKnife.bind(this,trade_layout);
        mBuySuccess=this;
        mContext = getActivity();
        mTradeBean=new TradeBean();
        //时间格式化
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        // 标记
        isCreated = true;
        getTrade(false);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getTrade(true);

            }
        });
        return trade_layout;
    }

    /**
     * 获取正在出售的云票
     */
    private void getTrade(final boolean isRefresh) {

        NetServer.getInstance().getTrade(new BaseCallBackListener<TradeBean>() {

            @Override
            public void onSuccess(TradeBean result) {
                super.onSuccess(result);
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {

                    //隐藏动画
                    avi.setVisibility(View.GONE);
                    avi.hide();
                }
                //打印
                if (result != null) {

                    Logger.addLogAdapter(new AndroidLogAdapter());
                    Logger.json(GsonUtil.toJson(result));
                    mTradeBean=result;
                    //没有审批贷款显示
                    if (result.getData().size() == 0) {
//                        tv_no_data.setVisibility(View.VISIBLE);
                    } else {
//                        tv_no_data.setVisibility(View.INVISIBLE);

                    }

                    if (isFirstLoading) {
                        //初始化recyclerview

                        mTradeAdapter=new TradeAdapter(mContext,mTradeBean,mBuySuccess);
                        //初始化recyclerview
                        get_my_cloud_ticket_recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
                        get_my_cloud_ticket_recycler_view.setAdapter(mTradeAdapter);

                        isFirstLoading = false;
                    } else {
                        mTradeAdapter.setTradeData(mTradeBean);
                    }



                }
            }

            @Override
            public void onError(Throwable e) {

                if (!isRefresh) {
                    avi.hide();
                    avi.setVisibility(View.GONE);
                    Toast.makeText(mContext, "获取失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    refreshLayout.finishRefresh(true);
                    Toast.makeText(mContext, "刷新失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(mContext, "获取失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                super.onError(e);

            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        L.d(TAG, "onStart()---");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(TAG, "onResume()---");
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
//            setTitle("云票交易所");
            mImmersionBar.transparentStatusBar()
                    .statusBarDarkFont(true)
                    .init();
        }
    }

    /**
     * 购买成功
     */
    @Override
    public void buySuccess() {
        getTrade(true);
    }
}
