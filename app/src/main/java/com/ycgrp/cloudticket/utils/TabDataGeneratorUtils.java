package com.ycgrp.cloudticket.utils;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ycgrp.cloudticket.R;
import com.ycgrp.cloudticket.mvp.ui.fragment.IndexFragment;
import com.ycgrp.cloudticket.mvp.ui.fragment.MeFragment;
import com.ycgrp.cloudticket.mvp.ui.fragment.TradeFragment;

public class TabDataGeneratorUtils {

    public static final int []mTabRes = new int[]{R.drawable.tab_index_selector,R.drawable.tab_trade_selector,R.drawable.tab_me_selector};
//    public static final int []mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_category_selected,R.drawable.ic_tab_strip_icon_pgc_selected,R.drawable.ic_tab_strip_icon_profile_selected};
    public static final String []mTabTitle = new String[]{"首页","云票交易所","我的"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[3];
        fragments[0] = IndexFragment.newInstance(from);
        fragments[1] = TradeFragment.newInstance(from);
        fragments[2] = MeFragment.newInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(TabDataGeneratorUtils.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
