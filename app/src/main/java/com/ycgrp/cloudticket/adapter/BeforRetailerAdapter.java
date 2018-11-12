package com.ycgrp.cloudticket.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.BeforRetailerBean;
import com.ycgrp.cloudticket.bean.UserInfoBean;
import com.ycgrp.cloudticket.mvp.view.AddRetailerID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeforRetailerAdapter extends RecyclerView.Adapter<BeforRetailerAdapter.BeforRetailerViewHolder> {


    Context mContext;
    LayoutInflater mLayoutInflater;
    /**
     * 以前担保过的零售商
     */
    BeforRetailerBean mBeforRetailerBean;

    /**
     * 接口给activity返回ID
     */
    AddRetailerID mAddRetailerID;

    public BeforRetailerAdapter(Context context, BeforRetailerBean beforRetailerBean,AddRetailerID addRetailerID) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mAddRetailerID=addRetailerID;
        setBeforRetailerData(beforRetailerBean);
    }
    public void setBeforRetailerData(BeforRetailerBean beforRetailerData){

        mBeforRetailerBean=beforRetailerData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BeforRetailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.befor_retailer_item, parent,false);
        BeforRetailerViewHolder beforRetailerViewHolder = new BeforRetailerViewHolder(view);
        return beforRetailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BeforRetailerViewHolder holder, int position) {

        NetServer.getInstance().getUserInfo(mBeforRetailerBean.getData().get(position).getId(), new BaseCallBackListener<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                super.onSuccess(result);
                if (result!=null){
                    holder.retailer_name.setText(result.getData().getAttributes().getName());
                    //最后一次实名认证的id
                    String lastProfilesId=result.getData().getRelationships().getProfiles().getData().get(result.getData().getRelationships().getProfiles().getData().size()-1).getId();
                    for (UserInfoBean.IncludedBean ic:result.getIncluded()){
                        if (ic.getId().equals(lastProfilesId)&&ic.getType().equals("user_profile")){
                            //设置地址
                            holder.retailer_address.setText(ic.getAttributes().getAddress());
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mBeforRetailerBean==null?0:mBeforRetailerBean.getData().size();
    }

    class BeforRetailerViewHolder extends RecyclerView.ViewHolder {


        /**
         * 零售商名字
         */
        @BindView(R.id.retailer_name)
        TextView retailer_name;
        /**
         * 零售商地址
         */
        @BindView(R.id.retailer_address)
        TextView retailer_address;
        /**
         * 零售商item
         */
        @BindView(R.id.item_retailer)
        CardView item_retailer;

        public BeforRetailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * 选择零售商
         */
        @OnClick(R.id.item_retailer)
       public void  selectRetailer(){
            mAddRetailerID.getSanRetailerID(mBeforRetailerBean.getData().get(getPosition()).getId());
        }
    }
}
