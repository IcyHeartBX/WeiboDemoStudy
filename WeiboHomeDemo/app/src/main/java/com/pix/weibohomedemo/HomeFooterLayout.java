package com.pix.weibohomedemo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;

/**
 * Created by zhangying on 2016/8/18 .
 *  @version 1.6.1
 * @description 大厅上拉加载效果
 */
public class HomeFooterLayout extends LoadingLayoutBase {

    private FrameLayout mInnerLayout;
    private TextView mHeaderText;
    private ImageView mpullRefreshing;
    private ImageView pull_icon;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    // 下拉箭头的转180°动画
    private RotateAnimation rotateAnimation;
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;


    public HomeFooterLayout(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.li_home_footer_loadinglayout, this);
        mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        mHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        mpullRefreshing = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refreshing);
        pull_icon = (ImageView) mInnerLayout.findViewById(R.id.pull_icon);
        //创建指针和旋转动画
        createAnimation();
        LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();
        lp.gravity = Gravity.TOP;
        // Load in labels
        mPullLabel = context.getString(R.string.li_home_pull_label);
        mRefreshingLabel = context.getString(R.string.li_home_refreshing_label);
        mReleaseLabel = context.getString(R.string.li_home_release_label);
        pull_icon.setVisibility(VISIBLE);
        reset();
    }

    private void createAnimation() {
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                getContext(), R.anim.li_reverse_anim);

        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                getContext(), R.anim.li_rotating);
        refreshingAnimation.setInterpolator(lir);
    }

    // 获取"加载头部"高度
    @Override
    public int getContentSize() {
        return mInnerLayout.getHeight();
    }

    // 开始下拉时的回调
    @Override
    public void pullToRefresh() {
        pull_icon.clearAnimation();
            pull_icon.setVisibility(View.VISIBLE);
        mHeaderText.setText(mPullLabel);
    }

    // "加载头部"完全显示时的回调
    @Override
    public void releaseToRefresh() {
        pull_icon.startAnimation(rotateAnimation);
        mHeaderText.setText(mReleaseLabel);

    }

    // 下拉拖动时的回调
    @Override
    public void onPull(float scaleOfLayout) {
    }

    // 释放后刷新时的回调
    @Override
    public void refreshing() {
        mHeaderText.setText(mRefreshingLabel);
        mpullRefreshing.setVisibility(View.VISIBLE);
        pull_icon.clearAnimation();
        pull_icon.setVisibility(View.GONE);
        mpullRefreshing.startAnimation(refreshingAnimation);

    }

    @Override
    public void reset() {
        pull_icon.clearAnimation();
        mpullRefreshing.clearAnimation();
        mpullRefreshing.setVisibility(View.GONE);

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }

}