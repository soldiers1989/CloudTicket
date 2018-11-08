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

    public MyCloudTicketAdapter(Context context,ReleaseOrRecallView releaseOrRecallView, MyCloudTicketBean myCloudTicketBean, String name) {

        mContext = context;
        mReleaseOrRecallView=releaseOrRecallView;
        mLayoutInflater = LayoutInflater.from(mContext);
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
         * 发布
         */
        @BindView(R.id.tv_release)
        TextView tv_release;
        /**
         * 撤回
         */
        @BindView(R.id.tv_recall)
        TextView tv_recall;

        /**
         * AlertDialog对话框
         */
        AlertDialog.Builder builder;

        public MyCloudTicketHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_release)
        public void release() {
            if (mMyCloudTicketBean.getData().get(getPosition()).getAttributes().getStatus().equals("ready_for_sale")) {
                Toast.makeText(mContext, "该云票已发布", Toast.LENGTH_SHORT).show();
            } else {
//                NetServer.getInstance().release(mMyCloudTicketBean.getData().get(getPosition()).getId(),);
                setAlertDialog();


            }

        }

        /**
         * 撤回
         */

        @OnClick(R.id.tv_recall)
        public void recall() {
            if (mMyCloudTicketBean.getData()!=null&&mMyCloudTicketBean.getData().get(getPosition()).getAttributes()!=null
                   &&mMyCloudTicketBean.getData().get(getPosition()).getAttributes().getStatus()!=null &&mMyCloudTicketBean.getData().get(getPosition()).getAttributes().getStatus().equals("held")) {
                Toast.makeText(mContext, "该云票还没有发布", Toast.LENGTH_SHORT).show();

            } else {
                sendRecall(mMyCloudTicketBean.getData().get(getPosition()).getId(),mMyCloudTicketBean.getData().get(getPosition()).getRelationships().getRelease().getData().getId());
            }
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
                    Toast.makeText(mContext, "取消" + mMyCloudTicketBean.getData().get(getPosition()).getAttributes().getStatus(), Toast.LENGTH_SHORT).show();
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
            NetServer.getInstance().release(mMyCloudTicketBean.getData().get(getPosition()).getId(), interest_rate, new BaseCallBackListener<ReleaseCloudBean>() {
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
        }

        /**
         *
         * @param bill_id 云票id
         * @param id 发布id
         */
        public void sendRecall(String bill_id,String id){
            NetServer.getInstance().recall(bill_id, id, new BaseCallBackListener<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody result) {
                    super.onSuccess(result);
                    L.e("撤回状态---"+result.toString());
                    mReleaseOrRecallView.recallSuccess();
                    Toast.makeText(mContext,R.string.recall_success,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(mContext,mContext.getString(R.string.recall_failed)+e.getMessage(),Toast.LENGTH_SHORT).show();
                    L.e("撤回失败---"+e.getMessage());
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
