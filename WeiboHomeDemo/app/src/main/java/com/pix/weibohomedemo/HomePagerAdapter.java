package com.pix.weibohomedemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HomePagerAdapter extends PagerAdapter {
    private List<ViewGroup> list  ;

    public HomePagerAdapter (List<ViewGroup> list) {

        this.list  = list ;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1 ;

    }

    /** -- 滑动加入的组件 -- */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(list.get(position));

        return list.get(position) ;

    }

    /**-- 滑动移除的组件 --*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(this.list.get(position));

    }

}
