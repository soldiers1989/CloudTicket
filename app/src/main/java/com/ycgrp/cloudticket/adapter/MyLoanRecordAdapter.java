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
import com.ycgrp.cloudticket.bean.MyLoanRecordBean;


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
    public void onBindViewHolder(@NonNull MyLoanRecordViewHolder holder, int position) {
        //设置金额
        if (mMyLoanRecordBean.getData() != null && mMyLoanRecordBean.getData().get(position).getAttributes() != null) {
            holder.loan_amount.setText("¥"+mMyLoanRecordBean.getData().get(position).getAttributes().getAmount());
        }
//        设置担保人和借款人
        if (mMyLoanRecordBean.getData() != null && mMyLoanRecordBean.getData().get(position).getRelationships() != null && mMyLoanRecordBean.getData().get(position).getRelationships().getLoanee() != null && mMyLoanRecordBean.getData().get(position).getRelationships().getGuarantor() != null) {
            String loanee_id = mMyLoanRecordBean.getData().get(position).getRelationships().getLoanee().getData().getId();
            String guarantor_id = mMyLoanRecordBean.getData().get(position).getRelationships().getGuarantor().getData().getId();
            if (mMyLoanRecordBean.getIncluded() != null) {
                for (MyLoanRecordBean.IncludedBean ic : mMyLoanRecordBean.getIncluded()) {
                    if (ic.getId().equals(loanee_id)) {
                        //设置借款人
                        holder.tv_borrower_num.setText(ic.getAttributes().getName());
                    }
                    if (ic.getId().equals(guarantor_id)) {
                        //设置担保人
                        holder.tv_guarantor_num.setText(ic.getAttributes().getName());
                    }

                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return mMyLoanRecordBean == null ? 0 : mMyLoanRecordBean.getData().size();
    }

    class MyLoanRecordViewHolder extends RecyclerView.ViewHolder {

        /**
         * 借款人
         */
        @BindView(R.id.tv_borrower_num)
        TextView tv_borrower_num;
        /**
         * 担保人
         */
        @BindView(R.id.tv_guarantor_num)
        TextView tv_guarantor_num;
        /**
         * 欠款金额
         */
        @BindView(R.id.loan_amount)
        TextView loan_amount;
        /**
         * 还款
         */
        @BindView(R.id.tv_repayment)
        TextView tv_repayment;

        public MyLoanRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_repayment)
        public  void repayment(){
            Toast.makeText(mContext,"还款成功",Toast.LENGTH_SHORT).show();
        }
    }
}
