package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.MyLoanRecordBean;
import com.ycgrp.cloudticket.bean.UserInfoBean;
import com.ycgrp.cloudticket.utils.DateUtils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLoanRecordAdapter extends RecyclerView.Adapter<MyLoanRecordAdapter.MyLoanRecordViewHolder> {


    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private MyLoanRecordBean mMyLoanRecordBean;

    public MyLoanRecordAdapter(Context context, MyLoanRecordBean mMyLoanRecordBean) {

        setMyLoanRecordData(mMyLoanRecordBean);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    public void setMyLoanRecordData(MyLoanRecordBean loanRecordData) {
        this.mMyLoanRecordBean = loanRecordData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyLoanRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.my_locan_record_item, parent, false);
        MyLoanRecordAdapter.MyLoanRecordViewHolder myLoanRecordViewHolder = new MyLoanRecordViewHolder(view);
        return myLoanRecordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyLoanRecordViewHolder holder, int position) {


        //设置金额
        holder.price.setText(mMyLoanRecordBean.getData().get(position).getAttributes().getAmount());
        //设置到期日
        if (mMyLoanRecordBean.getData().get(position).getAttributes().getMaturity_date()==null){
            holder.date_of_issue.setText("");
        }else {
            holder.date_of_issue.setText(mMyLoanRecordBean.getData().get(position).getAttributes().getMaturity_date());
        }

        //设置状态
        String status=mMyLoanRecordBean.getData().get(position).getAttributes().getStatus();

        if (status.equals("waiting_for_review")){
            holder.status.setText("等待审批");
        }else if (status.equals("rejected")){
            holder.status.setText("已拒绝");
        }else if (status.equals("approved")){
            holder.status.setText("进行中");
            if (mMyLoanRecordBean.getData().get(position).getAttributes().getMaturity_date()!=null){

                if (DateUtils.compareDate(DateUtils.getSystemDate(),mMyLoanRecordBean.getData().get(position).getAttributes().getMaturity_date())){
                    holder.status.setText("逾期");
                }

            }
        }else if (status.equals("paid")){
            holder.status.setText("已还款");
        }
        //设置零售商姓名
        NetServer.getInstance().getUserInfo(mMyLoanRecordBean.getData().get(position).getRelationships().getLoanee().getData().getId(), new BaseCallBackListener<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                super.onSuccess(result);
                holder.retailer.setText(result.getData().getAttributes().getName());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMyLoanRecordBean == null ? 0 : mMyLoanRecordBean.getData().size();
    }

    class MyLoanRecordViewHolder extends RecyclerView.ViewHolder {

        /**
         * 零售商
         */
        @BindView(R.id.retailer)
        TextView retailer;
        /**
         * 金额
         */
        @BindView(R.id.price)
        TextView price;
        /**
         * 到期日
         */
        @BindView(R.id.date_of_issue)
        TextView date_of_issue;
        /**
         * 状态
         */
        @BindView(R.id.status)
        TextView status;

        public MyLoanRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
