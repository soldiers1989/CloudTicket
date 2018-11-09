package com.ycgrp.cloudticket.mvp.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ycgrp.cloudticket.CloudTicketApplication;
import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.event.MessageEvent;
import com.ycgrp.cloudticket.mvp.ui.fragment.TradeFragment;
import com.ycgrp.cloudticket.mvp.ui.fragment.MeFragment;
import com.ycgrp.cloudticket.mvp.ui.fragment.IndexFragment;
import com.ycgrp.cloudticket.utils.Constants;
import com.ycgrp.cloudticket.utils.TabDataGeneratorUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    /**
     * tablayout
     */
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    /**
     * fragment
     */
    private Fragment[] mFragmensts;
    /**
     * ImmesionBar
     */
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        mFragmensts = TabDataGeneratorUtils.getFragments("TabLayout Tab");
        initTab();

    }

    private void initTab() {

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    icon.setImageResource(TabDataGeneratorUtils.mTabRes[i]);
                    if (i == tab.getPosition()) {
//                        icon.setImageResource(TabDataGeneratorUtils.mTabResPressed[i]);
                        text.setTextColor(getResources().getColor(R.color.primary));
                    } else {
//                        icon.setImageResource(TabDataGeneratorUtils.mTabRes[i]);
                        text.setTextColor(getResources().getColor(R.color.black));
                    }
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < 3; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(TabDataGeneratorUtils.getTabView(this, i)));
        }


    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;

        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).commit();
        }
    }

    private void initViews() {
        //注册EventBus
        EventBus.getDefault().register(this);


    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEvent messageEvent) {

        Toast.makeText(this, messageEvent.getMessage(), Toast.LENGTH_LONG).show();
        //退出登录，跳转到登录界面
        if (messageEvent.getMessage().equals(Constants.loginout)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            restartApp();
        }

    }

    /**
     * 重启app
     */
    public void restartApp() {
        //启动页
        Intent intent = new Intent(CloudTicketApplication.getInstance(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        CloudTicketApplication.getInstance().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //eventBus反注册
        EventBus.getDefault().unregister(this);
//        mImmersionBar destory
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }
}
