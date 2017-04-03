package com.pix.weibohomedemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.kevin.wraprecyclerview.WrapAdapter;
import com.pix.weibohomedemo.bean.HomeAnchor;
import com.pix.weibohomedemo.bean.LiveAct;
import com.pix.weibolistlibrary.Utils;
import com.pix.weibohomedemo.widget.EnableChildSlideFrameLayout;
import com.pix.weibohomedemo.widget.HomeHotListAdapter;
import com.pix.weibohomedemo.widget.HomeViewPagerAdapter;
import com.pix.weibohomedemo.widget.LoopViewPager;
import com.pix.weibohomedemo.widget.ViewPagerModify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/26.
 */

public class HotLayout extends HomeTabBaseFrameLayout implements HomeAnchorClickListener ,ViewPagerModify.OnPageChangeListener{

    private final static int VIEWPAGER_AUTO_SLIDE = 1001;
    private final static int LOAD_NEXT_PAGE_DATA = 1002;
    private final static int REFRESH_LISTVIEW = 1003;
    private final static int UPDATE_LIST_POSITION = 1004;
    private final static int REFRESH_PAGE = 1005;
    private static final int VIEWPAGER_AUTO_SLIDE_DELAY_TIME = 5 * 1000;

    private HomeHotListAdapter mHomeHotListAdapter;
    protected ArrayList<HomeAnchor> mAnchors ;
    protected WrapAdapter mWrapAdapter;//包裹adapter

    private EnableChildSlideFrameLayout mHeaderView;

    // 广告播放
    private LoopViewPager mLoopViewPager;
    private HomeViewPagerAdapter mHomeViewPagerAdapter;
    private List<LiveAct> vp_list;
    private LinearLayout ll_dots;

    private int mMiddleBannerWidth;
    private int mMiddleBannerHeight;


    public HotLayout(Context context) {
        super(context);
    }

    @Override
    protected int getContentViewId() {
         return R.layout.li_layout_home_hot;
    }

    @Override
    protected void initView() {
        mMiddleBannerWidth = Utils.getScreenWidth();
        mMiddleBannerHeight = mMiddleBannerWidth * 200 / 640;
        // 下拉刷新
        mPullToRefreshView = (PullToRefreshRecyclerView) findViewById(R.id.hot_pull_torefresh);
        mPullToRefreshView.setHeaderLayout(new HomeHeaderLayout(getContext()));
        mPullToRefreshView.setFooterLayout(new HomeFooterLayout(getContext()));
        //列表控件
        mRecyclerView = mPullToRefreshView.getRefreshableView();
        mRecyclerView.setId(R.id.home_hot_recylerview);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAnchors = new ArrayList();
        mHomeHotListAdapter = new HomeHotListAdapter(mAnchors,this);
        mWrapAdapter = new WrapAdapter<>(mHomeHotListAdapter);
        mRecyclerView.setAdapter(mWrapAdapter);

        // 列表头
        mHeaderView = (EnableChildSlideFrameLayout) LayoutInflater.from(mContext).inflate(R.layout.li_home_headerview, null);
        mHeaderView.setDisableLoop(false);
        mHeaderView.setVisibility(View.GONE);

        // 广告自动播放ViewPager
        mLoopViewPager = (LoopViewPager) mHeaderView.findViewById(R.id.pager_container);
        mLoopViewPager.setVisibility(View.GONE);
        ll_dots = (LinearLayout) mHeaderView.findViewById(R.id.home_dot_ll);
        ll_dots.setMinimumWidth(Utils.getScreenWidth());
        mLoopViewPager.setOnPageChangeListener(this);
        mLoopViewPager.setOffscreenPageLimit(3);
        vp_list = new ArrayList<>();
        mHomeViewPagerAdapter = new HomeViewPagerAdapter(mContext, vp_list, mLoopViewPager);
        initData();
    }

    @Override
    public void clickAnchor(HomeAnchor anchor) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (ll_dots == null)
            return;
        int childCount = ll_dots.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (position == i) {
                ll_dots.getChildAt(position).setBackgroundResource(R.drawable.li_icon_dot_selected);
            }
            else {
                ll_dots.getChildAt(i).setBackgroundResource(R.drawable.li_icon_dot_normal);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPagerModify.SCROLL_STATE_IDLE:
                startAutoScroll();
                break;
            case ViewPagerModify.SCROLL_STATE_DRAGGING:
            case ViewPagerModify.SCROLL_STATE_SETTLING:
                releaseAutoScroll();
        }
    }

    private void refreshBannerData() {
        //广告banner数据
        if (vp_list.size() > 0) {
            mHomeViewPagerAdapter.setData(vp_list);
            mLoopViewPager.setAdapter(mHomeViewPagerAdapter);
            mHeaderView.setVisibility(View.VISIBLE);
            mLoopViewPager.setVisibility(View.VISIBLE);
            //处理小点点
            addDot();
            //处理是否可以循环滑动
            if (vp_list.size() == 1) {
                mHeaderView.setDisableLoop(true);
                android.view.ViewGroup.LayoutParams layoutParams = mLoopViewPager.getLayoutParams();
                layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
                final int width = Utils.getScreenWidth();
                layoutParams.height = width / 12 * 5;//mMiddleBannerHeight;
                mLoopViewPager.setLayoutParams(layoutParams);
                releaseAutoScroll();
            }
            else {
                android.view.ViewGroup.LayoutParams layoutParams = mLoopViewPager.getLayoutParams();
                layoutParams.width = mMiddleBannerWidth;
                layoutParams.height = mMiddleBannerHeight;
                mLoopViewPager.setLayoutParams(layoutParams);
                mHeaderView.setDisableLoop(false);
                startAutoScroll();
            }

        }
        else {
            android.view.ViewGroup.LayoutParams layoutParams = mLoopViewPager.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.height = 0;
            mHeaderView.setDisableLoop(false);
            mLoopViewPager.setLayoutParams(layoutParams);
            mHeaderView.setVisibility(View.GONE);
            mLoopViewPager.setVisibility(View.GONE);
            releaseAutoScroll();
        }
    }

    /**
     * 停止banner广告侧滑
     *
     * @return: void
     */
    private void releaseAutoScroll() {
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * banner广告自动侧滑
     *
     * @return: void
     */
    private void startAutoScroll() {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(VIEWPAGER_AUTO_SLIDE,VIEWPAGER_AUTO_SLIDE_DELAY_TIME);
        }
    }

    /**
     * 为banner广告添加小红点
     *
     * @return: void
     */
    public void addDot() {
        try {
            ll_dots.removeAllViews();
            ImageView imageView = null;

            int size = vp_list.size();
            if (size == 1)
                return;

            for (int i = 0; i < size; i++) {
                imageView = new ImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 0, 0,5);
                params.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params);
                if (i == 0) {
                    imageView.setBackgroundResource(R.drawable.li_icon_dot_selected);
                }
                else {
                    imageView.setBackgroundResource(R.drawable.li_icon_dot_normal);
                }

                ll_dots.addView(imageView);
            }
        }
        catch (Exception e) {

        }
    }


    private void initData() {
        for (int i = 0;i < 10;i++) {
            mAnchors.add(new HomeAnchor());
        }
        for (int i = 0;i < 3;i++) {
            vp_list.add(new LiveAct());
        }

        setData(mAnchors, 1, 1);
        mHomeHotListAdapter.setHeaderView(mHeaderView);
        mWrapAdapter.notifyDataSetChanged();
        refreshBannerData();
    }

}
