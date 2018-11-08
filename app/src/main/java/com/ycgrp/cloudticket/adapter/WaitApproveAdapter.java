package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.ApproveordrejetBean;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.mvp.view.ApprovedOrRejected;
import com.ycgrp.cloudticket.utils.DateUtils;
import com.ycgrp.cloudticket.utils.GsonUtil;
import com.ycgrp.cloudticket.utils.L;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaitApproveAdapter extends RecyclerView.Adapter<WaitApproveAdapter.WaitApproveHolder> {


    private LayoutInflater mLayoutInflater;
    private WaitApproveBean mWaitApproveBeans;
    private Context mContext;
    private ApprovedOrRejected mApprovedOrRejected;//接口

    public WaitApproveAdapter(Context context, WaitApproveBean waitApproveBeans,ApprovedOrRejected ApprovedOrRejected) {

        mContext = context;
        mApprovedOrRejected=ApprovedOrRejected;
        setWaitApproveData(waitApproveBeans);
        mLayoutInflater = LayoutInflater.from(CloudTicketApplication.getContext());
    }

    public  void setWaitApproveData(WaitApproveBean waitApproveBeans){
        this.mWaitApproveBeans=waitApproveBeans;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public WaitApproveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WaitApproveHolder waitApproveHolder = new WaitApproveHolder(
                mLayoutInflater.inflate(R.layout.get_wait_approve_recycler_view_item, parent, false));
        return waitApproveHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WaitApproveHolder holder, int position) {

        if (mWaitApproveBeans.getData()!=null&&mWaitApproveBeans.getData().get(position).getAttributes()!=null){
            //贷款日期
            if (mWaitApproveBeans.getData().get(position).getAttributes().getDate_of_issue()!=null){
                holder.tv_start_data_nun.setText(mWaitApproveBeans.getData().get(position).getAttributes().getDate_of_issue());
            }
            //金额
            if (mWaitApproveBeans.getData().get(position).getAttributes().getAmount()!=null){
                holder.tv_apply_number.setText("¥" + mWaitApproveBeans.getData().get(position).getAttributes().getAmount());
            }
            //审核状态
            if (mWaitApproveBeans.getData().get(position).getAttributes().getStatus()!=null){

                if (mWaitApproveBeans.getData().get(position).getAttributes().getStatus().equals("waiting_for_review")) {
                    holder.tv_wait_approve.setText("待审核");
                    holder.tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape_two);
                } else {
                    holder.tv_wait_approve.setText("已审核");
                    holder.tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape);
                }
            }
        }


        //设置贷款人
        for (WaitApproveBean.IncludedBean includedBean : mWaitApproveBeans.getIncluded()) {
            if (mWaitApproveBeans.getData()!=null&&
                    mWaitApproveBeans.getData().get(position).getRelationships()!=null&&
                    mWaitApproveBeans.getData().get(position).getRelationships().getLoanee()!=null&&
                    mWaitApproveBeans.getData().get(position).getRelationships().getLoanee().getData()!=null&&
                    mWaitApproveBeans.getData().get(position).getRelationships().getLoanee().getData().getId()!=null&&
                    mWaitApproveBeans.getData().get(position).getRelationships().getLoanee().getData().getId().equals(includedBean.getId())) {
                holder.tv_set_borrower.setText(includedBean.getAttributes().getName());

            }

        }

    }

    @Override
    public int getItemCount() {


        return mWaitApproveBeans == null ? 0 : mWaitApproveBeans.getData().size();
    }

    class WaitApproveHolder extends RecyclerView.ViewHolder {


        //开始日期
        @BindView(R.id.tv_start_data_nun)
        TextView tv_start_data_nun;
        //申请金额
        @BindView(R.id.tv_apply_number)
        TextView tv_apply_number;
        //借款人
        @BindView(R.id.tv_set_borrower)
        TextView tv_set_borrower;
        //同意
        @BindView(R.id.tv_approve)
        TextView tv_approve;
        //拒绝
        @BindView(R.id.tv_reject)
        TextView tv_reject;
        /**
         * 审核状态
         */
        @BindView(R.id.tv_wait_approve)
        TextView tv_wait_approve;


        public WaitApproveHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        @OnClick(R.id.tv_approve)
        public void approve() {

            if (mWaitApproveBeans.getData()!=null&&
                    mWaitApproveBeans.getData().get(getAdapterPosition()).getAttributes()!=null&&
                    mWaitApproveBeans.getData().get(getAdapterPosition()).getAttributes().getStatus()!=null&&
                    mWaitApproveBeans.getData().get(getPosition()).getAttributes().getStatus().equals("waiting_for_review")) {
//                今天的日期
                String currentDate = DateUtils.getSystemDate();

                int year = Integer.parseInt(currentDate.substring(0, 4));
                int month = Integer.parseInt(currentDate.substring(5, 7));
                int date = Integer.parseInt(currentDate.substring(8, 10));
                //设置开始时间和结束时间
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();

                L.e("year=" + year + "month=" + month + "date" + date);

                startDate.set(year, month-1, date);
                endDate.set(2038, 11, 31);


                //时间选择器
                TimePickerView timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                        approveOrReject(DateUtils.getTime(date), "approved");
                        Toast.makeText(mContext, DateUtils.getTime(date), Toast.LENGTH_SHORT).show();
                    }
                })
                        .setSubmitText("通过")
                        .setTitleText("选择还款日期")
                        .setTitleSize(16)
                        .setTitleColor(Color.BLACK)
                        .setCancelColor(Color.BLACK)
                        .setSubmitColor(Color.BLACK)
                        .setRangDate(startDate, endDate)
                        .build();
                timePickerView.show();
            } else {
                Toast.makeText(mContext, "已审核", Toast.LENGTH_SHORT).show();
            }
//

        }

        @OnClick(R.id.tv_reject)
        public void reject() {
            if (mWaitApproveBeans.getData()!=null&&
                    mWaitApproveBeans.getData().get(getPosition()).getAttributes()!=null&&
                    mWaitApproveBeans.getData().get(getPosition()).getAttributes().getStatus()!=null&&
                    mWaitApproveBeans.getData().get(getPosition()).getAttributes().getStatus().equals("waiting_for_review")) {
                approveOrReject(null, "rejected");
            } else {
                Toast.makeText(mContext, "已审核", Toast.LENGTH_SHORT).show();
            }
        }


        /**
         * 同意或者拒绝
         *
         * @param maturity_date 还款日期
         * @param status        同意或者拒绝
         */
        public void approveOrReject(String maturity_date, final String status) {
            //
            if (mWaitApproveBeans.getData()!=null&&
                    mWaitApproveBeans.getData().get(getPosition()).getId()!=null){
                NetServer.getInstance().approveOrRejet(mWaitApproveBeans.getData().get(getPosition()).getId(), maturity_date, status, new BaseCallBackListener<ApproveordrejetBean>() {
                    @Override
                    public void onSuccess(ApproveordrejetBean result) {
                        super.onSuccess(result);
                        mWaitApproveBeans.getData().remove(getPosition());

                        if (status.equals("approved")) {
//                        tv_wait_approve.setText("已审核");
//                        tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape);
                            mApprovedOrRejected.approved();

                        } else {
                            notifyItemRemoved(getPosition());
                            notifyItemRangeChanged(getPosition(), mWaitApproveBeans.getData().size());
//                        mApprovedOrRejected.rejected();

                        }
                        Logger.addLogAdapter(new AndroidLogAdapter());
                        Logger.json(GsonUtil.toJson(result));
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


    }
}
