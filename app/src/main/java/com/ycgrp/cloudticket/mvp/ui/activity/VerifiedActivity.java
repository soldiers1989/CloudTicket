package com.ycgrp.cloudticket.mvp.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.api.BaseCallBackListener;
import com.ycgrp.cloudticket.api.NetServer;
import com.ycgrp.cloudticket.bean.MyInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class VerifiedActivity extends BaseActivity {

    /**
     * 身份证号码
     */
    @BindView(R.id.ed_id_card)
    TextInputEditText ed_id_card;
    /**
     * 地址
     */
    @BindView(R.id.ed_address)
    TextInputEditText ed_address;
    /**
     * 提交
     */
    @BindView(R.id.submit)
    Button submit;
    /**
     * 已完成实名认证
     */
    @BindView(R.id.tv_alerady_finish_verified)
    TextView tv_alerady_finish_verified;
    /**
     * 实名设置
     */
    @BindView(R.id.rela_verified)
    RelativeLayout rela_verified;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);
        ButterKnife.bind(this);
        judgeVerified();
    }

    @OnClick(R.id.submit)
    public  void Submit(){
        String ed_id_card_tx=ed_id_card.getText().toString();
        String ed_address_tx=ed_address.getText().toString();
        if (ed_id_card_tx==null){
            showTastTips("身份证号码为空");
        }else if (ed_address_tx==null){
            showTastTips("地址不能为空");
        }else {
            NetServer.getInstance().verified(ed_id_card_tx, ed_address_tx, new BaseCallBackListener<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody result) {
                    super.onSuccess(result);
                    showTastTips("实名认证成功");
                    judgeVerified();
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
        }
    }

    public  void judgeVerified(){

        NetServer.getInstance().getMyInfo(new BaseCallBackListener<MyInfoBean>() {
            @Override
            public void onSuccess(MyInfoBean result) {
                super.onSuccess(result);
                if (result!=null){
                    if (result.getData().getRelationships().getProfiles().getData().get(result.getData().getRelationships().getProfiles().getData().size()-1).getType().equals("user_profile")){
                        rela_verified.setVisibility(View.INVISIBLE);
                        tv_alerady_finish_verified.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }


}
