package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.ycgrp.cloudticket.bean.CloudTticketDetailsBean;
import com.ycgrp.cloudticket.bean.ReleaseCloudBean;
import com.ycgrp.cloudticket.bean.WaitApproveBean;
import com.ycgrp.cloudticket.event.MyCloudTicketBean;
import com.ycgrp.cloudticket.mvp.view.ApprovedOrRejected;
import com.ycgrp.cloudticket.mvp.view.GetDetail;
import com.ycgrp.cloudticket.mvp.view.ReleaseOrRecallView;
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

/**
 * 审批
 */
public class WaitApproveAdapter extends RecyclerView.Adapter<WaitApproveAdapter.WaitApproveHolder>  {


    private LayoutInflater mLayoutInflater;
    private WaitApproveBean mWaitApproveBeans;
    private MyCloudTicketBean mMyCloudTicketBean;
    private Context mContext;
    private ApprovedOrRejected mApprovedOrRejected;//接口
    private ReleaseOrRecallView mReleaseOrRecallView;//接口发布和撤回
    private GetDetail mGetDetail;//获取云票详情
    public WaitApproveAdapter(Context context, WaitApproveBean waitApproveBeans, MyCloudTicketBean myCloudTicketBean, ApprovedOrRejected ApprovedOrRejected,ReleaseOrRecallView releaseOrRecallView,GetDetail getDetail) {

        mContext = context;
        mApprovedOrRejected = ApprovedOrRejected;
        mReleaseOrRecallView=releaseOrRecallView;
        setWaitApproveData(waitApproveBeans, myCloudTicketBean);
        mGetDetail=getDetail;
        mLayoutInflater = LayoutInflater.from(CloudTicketApplication.getContext());
    }

    public void setWaitApproveData(WaitApproveBean waitApproveBeans, MyCloudTicketBean myCloudTicketBean) {
        this.mMyCloudTicketBean = myCloudTicketBean;
        this.mWaitApproveBeans = waitApproveBeans;
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


        //这三项相同一起设置
        //金额
        holder.tv_apply_number.setText("¥" + mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - position - 1).getAttributes().getAmount());
        //申请时间
        holder.tv_start_data_nun.setText(mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - position - 1).getAttributes().getDate_of_issue());
        //设置申请人
        for (WaitApproveBean.IncludedBean includedBean : mWaitApproveBeans.getIncluded()) {
            if (mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - position - 1).getRelationships().getLoanee().getData().getId().equals(includedBean.getId())) {
                holder.tv_set_borrower.setText(includedBean.getAttributes().getName());
            }
        }
        //获取状态初始化按钮状态 status: waiting_for_review(等待审批) | rejected(被拒绝) | approved(通过) | paid(已偿还)
        String status = mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - position - 1).getAttributes().getStatus();
        if (status.equals("waiting_for_review")) {
            //显示状态
            holder.tv_wait_approve.setText("待审核");
            holder.tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape_two);
            //批准按钮
            holder.tv_approve.setText("批准");
            //拒绝按钮
            holder.tv_reject.setText("拒绝");

        } else if (status.equals("approved")) {

            //云票id 设置按钮
            String bill_id = mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - position - 1).getRelationships().getBill().getData().getId();
            // 今天的日期
            String currentDate = DateUtils.getSystemDate();
            String maturity_dates= mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - position - 1).getAttributes().getMaturity_date();
                //根据是否已经发布设置按钮
            for (WaitApproveBean.IncludedBean list : mWaitApproveBeans.getIncluded()) {
                if (list.getId().equals(bill_id)) {
                    //持有
                    if (list.getAttributes().getStatus().equals("held")) {
                        holder.tv_approve.setText("发布");
                        holder.tv_reject.setText("还款");
                    } else if (list.getAttributes().getStatus().equals("ready_for_sale")) {
                        //已发布
                        holder.tv_approve.setText("查看云票");
                        holder.tv_reject.setText("还款");
                    }
                }
            }
            //设置状态
               if (DateUtils.compareDate(currentDate,maturity_dates)){
                   holder.tv_wait_approve.setText("逾期");
                   holder.tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape_two);
               }else {
                   //没有逾期显示显示已通过
                   holder.tv_wait_approve.setText("已通过");
                   holder.tv_wait_approve.setBackgroundResource(R.drawable.wait_approve_shape);

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
        //批准
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


        /**
         * AlertDialog对话框
         */
        AlertDialog.Builder builder;


        public WaitApproveHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }



        @OnClick(R.id.tv_approve)
        public void approve() {

            if (tv_approve.getText().toString().equals("批准")) {
//                今天的日期
                String currentDate = DateUtils.getSystemDate();

                int year = Integer.parseInt(currentDate.substring(0, 4));
                int month = Integer.parseInt(currentDate.substring(5, 7));
                int date = Integer.parseInt(currentDate.substring(8, 10));
                //设置开始时间和结束时间
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();

                L.e("year=" + year + "month=" + month + "date" + date);

                startDate.set(year, month - 1, date);
                endDate.set(2038, 11, 31);


                //时间选择器
                TimePickerView timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {

                        approveOrReject(DateUtils.getTime(date), "approved");
                        Toast.makeText(mContext, DateUtils.getTime(date), Toast.LENGTH_SHORT).show();
                    }
                })
                        .setSubmitText("批准")
                        .setTitleText("选择还款日期")
                        .setTitleSize(16)
                        .setTitleColor(Color.BLACK)
                        .setCancelColor(Color.BLACK)
                        .setSubmitColor(Color.BLACK)
                        .setRangDate(startDate, endDate)
                        .build();
                timePickerView.show();
            } else if (tv_approve.getText().toString().equals("发布")){
                Toast.makeText(mContext, "发布", Toast.LENGTH_SHORT).show();
                setAlertDialog();
            } else if (tv_approve.getText().toString().equals("查看云票")) {
//                Toast.makeText(mContext, "查看云票", Toast.LENGTH_SHORT).show();
                String id=mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size()-getPosition()-1).getRelationships().getBill().getData().getId();
                String releaseID;
                for (WaitApproveBean.IncludedBean ic:mWaitApproveBeans.getIncluded()){
                    if (ic.getId().equals(id)&&ic.getType().equals("bill")){
                        releaseID=ic.getRelationships().getReleases().getData().get(ic.getRelationships().getReleases().getData().size()-1).getId();
                        //获取详情
                        mGetDetail.getDetail(id, releaseID);
                    }
                }

            }
//

        }



        @OnClick(R.id.tv_reject)
        public void reject() {
            if (tv_reject.getText().toString().equals("拒绝")) {
                approveOrReject(null, "rejected");
            } else if (tv_reject.getText().toString().equals("还款")){
                Toast.makeText(mContext, "还款", Toast.LENGTH_SHORT).show();
            }
        }


        /**
         * 同意或者拒绝
         *
         * @param maturity_date 还款日期
         * @param status        同意或者拒绝
         */
        public void approveOrReject(String maturity_date, final String status) {

            NetServer.getInstance().approveOrRejet(mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size() - 1 - getPosition()).getId(), maturity_date, status, new BaseCallBackListener<ApproveordrejetBean>() {
                @Override
                public void onSuccess(ApproveordrejetBean result) {
                    super.onSuccess(result);
                    mWaitApproveBeans.getData().remove(getPosition());

                    if (status.equals("approved")) {

                        mApprovedOrRejected.approved();

                    } else {
                        notifyItemRemoved(getPosition());
//                            notifyItemRangeChanged(getPosition() , mWaitApproveBeans.getData().size());

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

        /**
         * 弹出对话框输入利率
         */
        public void setAlertDialog() {
            builder = new AlertDialog.Builder(mContext);
            TextView title = new TextView(mContext);
            title.setText(R.string.please_input_rate);
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.BLACK);
            title.setTextSize(20);
            builder.setCustomTitle(title);
            final EditText et_input_intersrest_rate=new EditText(mContext);
            et_input_intersrest_rate.setId(R.id.input_intersrest_rate);
            builder.setView(et_input_intersrest_rate);
            builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(mContext, "取消" + mMyCloudTicketBean.getData().get(getPosition()).getAttributes().getStatus(), Toast.LENGTH_SHORT).show();
                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String txt_interest_rate=et_input_intersrest_rate.getText().toString();
                    if (txt_interest_rate.isEmpty()){
                        Toast.makeText(mContext, "利率不能为空", Toast.LENGTH_SHORT).show();
                    }else {
                        if (Float.parseFloat(txt_interest_rate)<0){
                            Toast.makeText(mContext, "利率不能小于0", Toast.LENGTH_SHORT).show();
                        }else {
                            sendRelease(txt_interest_rate);
                        }
                    }
                }
            });
            builder.show();
        }


        /**
         * 通过接口发布到市场
         * @param interest_rate 利率
         */
        public void  sendRelease(String interest_rate){
            String bill_id=mWaitApproveBeans.getData().get(mWaitApproveBeans.getData().size()-getPosition()-1).getRelationships().getBill().getData().getId();
            if (bill_id!=null){
                NetServer.getInstance().release(bill_id, interest_rate, new BaseCallBackListener<ReleaseCloudBean>() {
                    @Override
                    public void onSuccess(ReleaseCloudBean result) {
                        super.onSuccess(result);
                        Logger.addLogAdapter(new AndroidLogAdapter());
                        if (result!=null){
                            Logger.json(GsonUtil.toJson(result));
                        }
                        mReleaseOrRecallView.releaseSuccess();
                        Toast.makeText(mContext,R.string.release_success,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        Toast.makeText(mContext,mContext.getString(R.string.release_failed)+e.getMessage(),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
            }else {
                Toast.makeText(mContext,mContext.getString(R.string.release_failed),Toast.LENGTH_SHORT).show();
            }

        }

    }

}
