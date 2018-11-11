package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetAPI;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.ApproveordrejetBean;
import com.ycgrp.cloudticket.bean.ReleaseCloudBean;
import com.ycgrp.cloudticket.event.MyCloudTicketBean;
import com.ycgrp.cloudticket.mvp.view.GetDetail;
import com.ycgrp.cloudticket.mvp.view.ReleaseOrRecallView;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;



import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class MyCloudTicketAdapter extends RecyclerView.Adapter<MyCloudTicketAdapter.MyCloudTicketHolder>   {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private MyCloudTicketBean mMyCloudTicketBean;
    private String mName;//我的名字
    private  ReleaseOrRecallView mReleaseOrRecallView;//接口发布和撤回
    private GetDetail mGetDetail;//获取云票详情
    public MyCloudTicketAdapter(Context context,ReleaseOrRecallView releaseOrRecallView,GetDetail getDetail, MyCloudTicketBean myCloudTicketBean, String name) {

        mContext = context;
        mReleaseOrRecallView=releaseOrRecallView;
        mLayoutInflater = LayoutInflater.from(mContext);
        mGetDetail=getDetail;
        setCloudData(myCloudTicketBean);
        mName = name;

    }

   public void setCloudData(MyCloudTicketBean myCloudTicketBean){
        this.mMyCloudTicketBean=myCloudTicketBean;
        notifyDataSetChanged();
    }
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public MyCloudTicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.my_cloud_ticket_view_item, parent, false);
        MyCloudTicketHolder myCloudTicketHolder = new MyCloudTicketHolder(view);
        return myCloudTicketHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyCloudTicketHolder holder, int position) {

        if (mMyCloudTicketBean.getData()!=null&&mMyCloudTicketBean.getData().get(position).getAttributes()!=null){
            if ( mMyCloudTicketBean.getData().get(position).getAttributes().getAmount()!=null){
                holder.tv_apply_number.setText("¥" + mMyCloudTicketBean.getData().get(position).getAttributes().getAmount());

            }
            if (mMyCloudTicketBean.getData().get(position).getAttributes().getDate_of_issue()!=null){
                holder.tv_draw_ticket_data_num.setText(mMyCloudTicketBean.getData().get(position).getAttributes().getDate_of_issue());
                holder.tv_accept_num.setText(mMyCloudTicketBean.getData().get(position).getAttributes().getDate_of_issue());
            }
            if (    mMyCloudTicketBean.getData().get(position).getAttributes().getMaturity_date()!=null){
                holder.tv_expire_num.setText(mMyCloudTicketBean.getData().get(position).getAttributes().getMaturity_date());

            }
            if (  mMyCloudTicketBean.getData().get(position).getAttributes().getStatus()!=null){
                //设置持有状态
                if (mMyCloudTicketBean.getData().get(position).getAttributes().getStatus().equals("ready_for_sale")) {
                    holder.tv_wait_approve.setText(R.string.already_release);
                    holder.tv_wait_approve.setBackgroundResource(R.drawable.already_release);

                } else if (mMyCloudTicketBean.getData().get(position).getAttributes().getStatus().equals("held")) {
                    holder.tv_wait_approve.setText(R.string.tv_holder);
                    holder.tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape);
                }
            }

        }
        holder.set_tv_rel_out_glod.setText(mName);

    }

    @Override
    public int getItemCount() {
        return mMyCloudTicketBean.getData().size();
    }


    class MyCloudTicketHolder extends RecyclerView.ViewHolder {

        /**
         * 出票日期
         */
        @BindView(R.id.tv_draw_ticket_data_num)
        TextView tv_draw_ticket_data_num;
        /**
         * 申请日期
         */
        @BindView(R.id.tv_accept_num)
        TextView tv_accept_num;
        /**
         * 到期日期
         */
        @BindView(R.id.tv_expire_num)
        TextView tv_expire_num;
        /**
         * 金额
         */
        @BindView(R.id.tv_apply_number)
        TextView tv_apply_number;
        /**
         * 收款人
         */
        @BindView(R.id.set_tv_rel_out_glod)
        TextView set_tv_rel_out_glod;
        /**
         * 持有状态
         */
        @BindView(R.id.tv_wait_approve)
        TextView tv_wait_approve;

        /**
         * 云票item
         */
        @BindView(R.id.rel_my_cloud_ticket)
        CardView rel_my_cloud_ticket;


        /**
         * AlertDialog对话框
         */
        AlertDialog.Builder builder;

        public MyCloudTicketHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.rel_my_cloud_ticket)
        public  void getDetails(){
            mGetDetail.getDetail(mMyCloudTicketBean.getData().get(getPosition()).getId(),mMyCloudTicketBean.getData().get(getPosition()).getRelationships().getReleases().getData().get(mMyCloudTicketBean.getData().get(getPosition()).getRelationships().getReleases().getData().size()-1).getId());
        }


    }
}
