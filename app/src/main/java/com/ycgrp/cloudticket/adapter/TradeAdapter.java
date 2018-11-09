package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.graphics.Paint;
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
import com.ycgrp.cloudticket.bean.TradeBean;
import com.ycgrp.cloudticket.mvp.view.BuySuccess;
import com.ycgrp.cloudticket.utils.DateUtils;
import com.ycgrp.cloudticket.utils.L;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {


    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private TradeBean mTradeBean;
    private BuySuccess mBuySuccess;

    public TradeAdapter(Context context, TradeBean tradeBean, BuySuccess buySuccess) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mBuySuccess = buySuccess;
        setTradeData(tradeBean);
    }

    public void setTradeData(TradeBean tradeBean) {
        this.mTradeBean = tradeBean;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.trade_item, parent, false);
        TradeViewHolder tradeViewHolder = new TradeViewHolder(view);
        return tradeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        holder.tv_draw_ticket_data_num.setText(mTradeBean.getData().get(position).getAttributes().getDate_of_issue());
        holder.tv_accept_num.setText(mTradeBean.getData().get(position).getAttributes().getDate_of_issue());
        holder.tv_expire_num.setText(mTradeBean.getData().get(position).getAttributes().getMaturity_date());
        holder.tv_value_number.setText("¥" + mTradeBean.getData().get(position).getAttributes().getAmount());
        String date_of_issue = mTradeBean.getData().get(position).getAttributes().getDate_of_issue();
        String maturity_date = mTradeBean.getData().get(position).getAttributes().getMaturity_date();

        //设置折扣价
        String interest_rate;
        for (TradeBean.IncludedBean ic : mTradeBean.getIncluded()) {

            if (ic.getId()!=null&&mTradeBean.getData()!=null&&mTradeBean.getData().get(position).getRelationships()!=null&&
                    mTradeBean.getData().get(position).getRelationships().getRelease()!=null&&
                    mTradeBean.getData().get(position).getRelationships().getRelease().getData()!=null&&
                    mTradeBean.getData().get(position).getRelationships().getRelease().getData().getId()!=null&&
                    ic.getId().equals(mTradeBean.getData().get(position).getRelationships().getRelease().getData().getId())) {
                interest_rate = ic.getAttributes().getInterest_rate();
                holder.current_prince.setText("¥" + holder.getCurrentPrice(date_of_issue, maturity_date, interest_rate) + "");
            }
        }
        //设置发布者
        //云票发布
        String release_id;
        if (mTradeBean.getData()!=null&&mTradeBean.getData().get(position).getRelationships()!=null
                &&mTradeBean.getData().get(position).getRelationships().getRelease()!=null&&
                mTradeBean.getData().get(position).getRelationships().getRelease().getData()!=null&&
                mTradeBean.getData().get(position).getRelationships().getRelease().getData().getId()!=null){

            //获取发布者id
            release_id = mTradeBean.getData().get(position).getRelationships().getRelease().getData().getId();
            L.e("release_id---" + release_id);
            for (TradeBean.IncludedBean ic : mTradeBean.getIncluded()) {

                if (ic.getId().equals(release_id) && ic.getRelationships() != null && ic.getRelationships().getUser() != null && ic.getRelationships().getUser().getData() != null) {
                    String publisher_id = ic.getRelationships().getUser().getData().getId();
                    //获取发布者姓名
                    L.e("publisher_id---" + publisher_id);
                    for (TradeBean.IncludedBean ic_two : mTradeBean.getIncluded()) {
                        if (ic_two.getId().equals(publisher_id) && ic_two.getAttributes() != null && ic_two.getAttributes().getName() != null) {
                            String publisher_name = ic_two.getAttributes().getName();
                            L.e("publisher_name---" + publisher_name);
                            holder.set_published.setText(publisher_name);

                        }
                    }
                }
            }
        }



    }

    @Override
    public int getItemCount() {
        return mTradeBean == null ? 0 : mTradeBean.getData().size();
    }

    class TradeViewHolder extends RecyclerView.ViewHolder {

        /**
         * 出票日期
         */
        @BindView(R.id.tv_draw_ticket_data_num)
        TextView tv_draw_ticket_data_num;
        /**
         * 到期日期
         */
        @BindView(R.id.tv_expire_num)
        TextView tv_expire_num;
        /**
         * 价值
         */
        @BindView(R.id.tv_value_number)
        TextView tv_value_number;
        /**
         * 购买
         */
        @BindView(R.id.tv_buy)
        TextView tv_buy;
        /**
         * 现价
         */
        @BindView(R.id.current_prince)
        TextView current_prince;
        /**
         * 发布者
         */
        @BindView(R.id.set_published)
        TextView set_published;

        /**
         *承兑日期
         */
        @BindView(R.id.tv_accept_num)
        TextView tv_accept_num;

        public TradeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tv_value_number.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//加横线
            tv_value_number.getPaint().setAntiAlias(true);//抗锯齿
        }


        @OnClick(R.id.tv_buy)
        public void buy() {
            if (mTradeBean.getData()!=null&&mTradeBean.getData().get(getPosition()).getRelationships()!=null&&mTradeBean.getData().get(getPosition()).getRelationships().getRelease()!=null&&mTradeBean.getData().get(getPosition()).getRelationships().getRelease().getData()!=null&&mTradeBean.getData().get(getPosition()).getRelationships().getRelease().getData().getId()!=null){

                NetServer.getInstance().buyCloudTicket(mTradeBean.getData().get(getPosition()).getRelationships().getRelease().getData().getId(), new BaseCallBackListener<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody result) {
                        Toast.makeText(mContext, "购买成功", Toast.LENGTH_SHORT).show();
                        mBuySuccess.buySuccess();
                        super.onSuccess(result);
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


        /**
         * @param date_of_issue 发售时间
         * @param maturity_date 过期时间
         * @param interest_rate 利率
         */
        public String getCurrentPrice(String date_of_issue, String maturity_date, String interest_rate) {

            //时间格式化
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //折扣价格
            double disscount = 0;
            try {

                Date date_start = simpleDateFormat.parse(date_of_issue);
                Date date_end = simpleDateFormat.parse(maturity_date);
                double price=0;
                if (mTradeBean.getData()!=null&&mTradeBean.getData().get(getPosition()).getAttributes()!=null&&mTradeBean.getData().get(getPosition()).getAttributes().getAmount()!=null){
                    //当天价格
                    price= Float.parseFloat(mTradeBean.getData().get(getPosition()).getAttributes().getAmount());
                }

                //利率
                double f_rate = Float.parseFloat(interest_rate);
                //间隔天数
                int day_count = DateUtils.getGapCount(date_start, date_end);

                L.e("天数---" + day_count);
                //折扣价格
                disscount = price * (1 - f_rate * day_count / 100);
                //保留两位小数
                String stringDiscount = String.format("%.1f", disscount);
                return stringDiscount;

            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }

        }
    }

}
