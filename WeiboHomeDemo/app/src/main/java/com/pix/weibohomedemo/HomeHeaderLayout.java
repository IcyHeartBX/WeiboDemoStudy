package com.pix.weibohomedemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangying on 2016/8/1 .
 *  @version 1.5.1
 * @description 首页下拉刷新头
 */
public class HomeHeaderLayout extends LoadingLayoutBase {

    private FrameLayout mInnerLayout;
    private ImageView pullImg;

    private AnimationDrawable animBabyShake;
    private List<Integer> mCatBitmapList;

    public HomeHeaderLayout(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.li_home_pulltorefresh_header, this);
        mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        pullImg = (ImageView) mInnerLayout.findViewById(R.id.iv_pull_icon);
        LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        mCatBitmapList = new ArrayList<>();
        addResList();
        reset();
    }

    private void addResList() {
        mCatBitmapList.add(R.drawable.li_refresh_1);
        mCatBitmapList.add(R.drawable.li_refresh_2);
        mCatBitmapList.add(R.drawable.li_refresh_3);
        mCatBitmapList.add(R.drawable.li_refresh_4);
        mCatBitmapList.add(R.drawable.li_refresh_5);
        mCatBitmapList.add(R.drawable.li_refresh_6);
        mCatBitmapList.add(R.drawable.li_refresh_7);
        mCatBitmapList.add(R.drawable.li_refresh_8);
        mCatBitmapList.add(R.drawable.li_refresh_9);
        mCatBitmapList.add(R.drawable.li_refresh_10);
        mCatBitmapList.add(R.drawable.li_refresh_11);
        mCatBitmapList.add(R.drawable.li_refresh_12);
        mCatBitmapList.add(R.drawable.li_refresh_13);
        mCatBitmapList.add(R.drawable.li_refresh_14);
        mCatBitmapList.add(R.drawable.li_refresh_15);
        mCatBitmapList.add(R.drawable.li_refresh_16);
        mCatBitmapList.add(R.drawable.li_refresh_17);
        mCatBitmapList.add(R.drawable.li_refresh_18);
        mCatBitmapList.add(R.drawable.li_refresh_19);
        mCatBitmapList.add(R.drawable.li_refresh_20);
        mCatBitmapList.add(R.drawable.li_refresh_21);
        mCatBitmapList.add(R.drawable.li_refresh_22);
        mCatBitmapList.add(R.drawable.li_refresh_23);
    }

    // 获取"加载头部"高度
    @Override
    public int getContentSize() {
        return mInnerLayout.getHeight();
    }

    // 开始下拉时的回调
    @Override
    public void pullToRefresh() {
        pullImg.setImageResource(R.drawable.li_refresh_21);
    }

    // "加载头部"完全显示时的回调
    @Override
    public void releaseToRefresh() {
        pullImg.setImageResource(R.drawable.li_refresh_23);

    }


    // 下拉拖动时的回调
    @Override
    public void onPull(float scaleOfLayout) {
        if(scaleOfLayout>0&&scaleOfLayout<1.0f){
            int i = (int) (scaleOfLayout*22);
            if(i>1&&i<22){
                pullImg.setImageResource(mCatBitmapList.get(i-1));
            }
        }
       /* //缩放动画
        ViewHelper.setPivotY(pullImg, pullImg.getMeasuredHeight());   // 设置中心点
        ViewHelper.setPivotX(pullImg, pullImg.getMeasuredWidth() / 2);
        ObjectAnimator animPX = ObjectAnimator.ofFloat(pullImg, "scaleX", 0.25f, 1.0f).setDuration(300);
        animPX.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animPY = ObjectAnimator.ofFloat(pullImg, "scaleY", 0.25f, 1.0f).setDuration(300);
        animPY.setCurrentPlayTime((long) (scaleOfLayout * 300));*/
    }

    // 释放后刷新时的回调
    @Override
    public void refreshing() {
        pullImg.setImageResource(R.drawable.li_home_pullrefresh_head_cycle);
        if (animBabyShake == null) {
            animBabyShake = (AnimationDrawable) pullImg.getDrawable();
        }
        //animation-iteration-count
//        animBabyShake.n
        animBabyShake.start();
    }

    @Override
    public void reset() {
        if (animBabyShake != null) {
            animBabyShake.stop();
            animBabyShake = null;
        }

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }
}
