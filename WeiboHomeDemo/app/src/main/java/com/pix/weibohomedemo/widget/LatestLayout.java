package com.pix.weibohomedemo.widget;

import android.content.Context;

import com.pix.weibohomedemo.HomeTabBaseFrameLayout;
import com.pix.weibohomedemo.R;

/**
 * Created by Administrator on 2017/3/26.
 */

public class LatestLayout extends HomeTabBaseFrameLayout {
    public LatestLayout(Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.li_layout_home_latest;
    }

    @Override
    protected void initView() {

    }
}
