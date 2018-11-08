package com.ycgrp.cloudticket.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class BaseFragment extends Fragment {
    /**
     * 获取到activity
     */
    private Activity mActivity;
    /**
     * 标题
     */
    private TextView title;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity=getActivity();

//        title=mActivity.findViewById(R.id.title);
    }

    @Override
    public void onStart() {
        super.onStart();
        //eventBus注册
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //EventBus反注册
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode =ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent messageEvent){

    }



//    public void  setTitle(String txt){
//        if (txt!=null){
//            title.setText(txt);
//        }
//    }
//    public void setTitleVisiable(boolean visiable){
//        if (!visiable){
//            title.setVisibility(View.GONE);
//        }else {
//            title.setVisibility(View.VISIBLE);
//        }
//    }
}
