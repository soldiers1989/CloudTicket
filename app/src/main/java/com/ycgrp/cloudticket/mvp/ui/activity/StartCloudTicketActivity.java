package com.ycgrp.cloudticket.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.mvp.model.deal.StartCloudTicketListener;
import com.ycgrp.cloudticket.mvp.presenter.StartCloudTicketPresenter;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StartCloudTicketActivity extends BaseActivity implements StartCloudTicketListener {

    /**
     * 金额
     */
    @BindView(R.id.input_amount)
    EditText input_amount;
    /**
     * 担保人ID
     */
    @BindView(R.id.input_guarantor_id)
    EditText input_guarantor_id;
    /**
     * 发起云票
     */
    @BindView(R.id.btn_start_cloud_ticket)
    Button btn_start_cloud_ticket;
    /**
     * 金额和担保人id
     */
    private String amount, guarantor_id;

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
     * 添加更多
     */
    @BindView(R.id.add)
    ImageView add;

    /**
     * item
     */
    View popView;

    /**
     * 弹出框收钱
     */

    /**
     * 右上角弹出
     */
    TopRightMenu mTopRightMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_cloud_ticket);
        ButterKnife.bind(this);
        mStartCloudTicketPresenter = new StartCloudTicketPresenter(this);

        //发起云票
        btn_start_cloud_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                if (amount.isEmpty()) {
                    showTastTips("输入金额不能为空");
                } else if (Integer.parseInt(amount) <= 0) {
                    showTastTips("输入金额必须大于0");
                } else if (guarantor_id.isEmpty()) {
                    showTastTips("担保人id不能为空");
                } else {
                    mStartCloudTicketPresenter.startCloudTicket(amount, guarantor_id);

                }
            }
        });

    }

    private void init() {


        mTopRightMenu = new TopRightMenu(StartCloudTicketActivity.this);

            //添加菜单项
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.scan, "扫一扫"));
        menuItems.add(new MenuItem(R.drawable.collect_money, "收钱码"));


        mTopRightMenu
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)     //默认高度480
                .setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        Toast.makeText(StartCloudTicketActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .showAsDropDown(add,0,0);    //带偏移量

    }


    public void getData() {

        amount = input_amount.getText().toString();
        guarantor_id = input_guarantor_id.getText().toString();
    }

    /**
     * 云票发起成功
     */

    @Override
    public void startSuccess() {
        showTastTips("发布云票成功");
        //清除文本
        input_amount.getText().clear();
        input_guarantor_id.getText().clear();
    }

    /**
     * 云票发起失败
     */
    @Override
    public void startFailed() {
        showTastTips("发布云票失败");
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

    /**
     * +号
     */
    @OnClick(R.id.add)
    public void add() {

        Toast.makeText(this, "添加更多", Toast.LENGTH_SHORT).show();
        init();
//

    }


}
