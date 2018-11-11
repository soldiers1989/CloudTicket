package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.TradeBean;
import com.ycgrp.cloudticket.bean.UserInfoBean;
import com.ycgrp.cloudticket.mvp.view.BuySuccess;
import com.ycgrp.cloudticket.mvp.view.GetDetail;
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
    private GetDetail mGetDetail;//获取云票详情
    public TradeAdapter(Context context, TradeBean tradeBean, BuySuccess buySuccess,GetDetail getDetail) {
        mContext = context;
        mGetDetail=getDetail;
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
    public void onBindViewHolder(@NonNull final TradeViewHolder holder, int position) {

        holder.tv_draw_ticket_data_num.setText(mTradeBean.getData().get(position).getAttributes().getDate_of_issue());
        holder.tv_accept_num.setText(mTradeBean.getData().get(position).getAttributes().getDate_of_issue());
        holder.tv_expire_num.setText(mTradeBean.getData().get(position).getAttributes().getMaturity_date());
        holder.tv_value_number.setText("¥" + mTradeBean.getData().get(position).getAttributes().getAmount());
        String date_of_issue = mTradeBean.getData().get(position).getAttributes().getDate_of_issue();
        String maturity_date = mTradeBean.getData().get(position).getAttributes().getMaturity_date();

//        //设置折扣价
//        String interest_rate;
//        for (TradeBean.IncludedBean ic : mTradeBean.getIncluded()) {
//
//            if (mTradeBean.getData().get(position).getRelationships().getReleases().getData().get(mTradeBean.getData().get(position).getRelationships().getReleases().getData().get()-1).getId().equals(ic.getId())){
//                interest_rate = ic.getRelationships().getBill().getData().getId();
//                holder.current_prince.setText("¥" + holder.getCurrentPrice(date_of_issue, maturity_date, interest_rate) + "");
//            }
//
//        }
        //设置发布者
        //云票发布

            for (TradeBean.IncludedBean ic : mTradeBean.getIncluded()) {



                    if (ic.getRelationships().getGuarantor()!=null){

                        String publisher_id = ic.getRelationships().getGuarantor().getData().getId();
                        //获取发布者姓名
                        L.e("publisher_id---" + publisher_id);
                        NetServer.getInstance().getUserInfo(publisher_id, new BaseCallBackListener<UserInfoBean>() {
                            @Override
                            public void onSuccess(UserInfoBean result) {
                                super.onSuccess(result);
                                holder.set_published.setText(result.getData().getAttributes().getName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }
                        });
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
        /**
         * 云票item
         */
        @BindView(R.id.item_cloudTicket)
        CardView item_cloudTicket;

        public TradeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tv_value_number.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//加横线
            tv_value_number.getPaint().setAntiAlias(true);//抗锯齿
        }

        @OnClick(R.id.item_cloudTicket)
        public void getDetail(){
            mGetDetail.getDetail(mTradeBean.getData().get(getPosition()).getId(),mTradeBean.getData().get(getPosition()).getRelationships().getReleases().getData().get(mTradeBean.getData().get(getPosition()).getRelationships().getReleases().getData().size()-1).getId());
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
