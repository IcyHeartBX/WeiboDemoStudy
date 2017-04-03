package com.pix.weibohomedemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.pix.weibohomedemo.bean.HomeAnchor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class HomeTabBaseFrameLayout extends FrameLayout implements  Handler.Callback{
    protected final static int PAGE_SIZE = 10;
    protected final static int VALID_TIME = 60000;//数据过期时间
    public static final int REFRESH_DELAY = 1500;
    private static final String TAG = "HomeTabBaseFrameLayout";

    protected Context mContext;
    protected Handler mHandler;
    protected ArrayList<HomeAnchor> mAnchors;
    protected SparseArray<Long> mUpdateTime;
    protected int mCurPage;
    protected int mTotalPage;

    public boolean isLoading = false;
    public boolean isHideTitle = true;

    protected PullToRefreshRecyclerView mPullToRefreshView;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;

    public HomeTabBaseFrameLayout(Context context) {
        super(context);
        this.mContext = context ;
        mHandler = new Handler(this);
        mAnchors = new ArrayList<>();
        mUpdateTime = new SparseArray<>();

        LayoutInflater.from(context).inflate(getContentViewId(), this, true);
        initView();
        setScrollListener();
        updateData(0,false);
        mUpdateTime.append(0, System.currentTimeMillis());
    }

    protected void setScrollListener() {
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                isHideTitle = false;
                if(!isLoading){
                    isLoading = true;
                    updateData(0,false);
                    mUpdateTime.append(0, System.currentTimeMillis());
                }
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.onRefreshComplete();
                        isHideTitle = true;
                    }
                }, REFRESH_DELAY);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                int pos;
                pos = mLayoutManager.findLastVisibleItemPosition();
                if (mCurPage <mTotalPage - 1 && pos>= mAnchors.size()&&!isLoading) {
                    isLoading = true;
                    updateData(mCurPage + 1, false);
                    mUpdateTime.append(0, System.currentTimeMillis());
                }else if(mCurPage >= mTotalPage - 1){
                    mPullToRefreshView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshView.onRefreshComplete();
                        }
                    }, 800);
                }
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.onRefreshComplete();
                    }
                }, 800);

            }
        });
         RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int pos;
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    pos = mLayoutManager.findLastVisibleItemPosition();
                    if (mCurPage <= mTotalPage - 1 && (pos == mAnchors.size() - 6 ||pos==mAnchors.size()-5||pos ==mAnchors.size()-4)&&!isLoading){
                        isLoading=true;
                        updateData(mCurPage + 1,false);
                        mUpdateTime.append(0, System.currentTimeMillis());
                    }
                }
            }

        };
        mRecyclerView.setOnScrollListener(mOnScrollListener);
    }

    protected void setData(List<HomeAnchor> dataList, int page, int totalPage) {

        mCurPage = page;
        mTotalPage = totalPage;
        mUpdateTime.append(page, System.currentTimeMillis());
        if (page == 0){
            mAnchors.clear();
        }
        int start = page * PAGE_SIZE;
        if (mAnchors.size() >= start + dataList.size()) {//数据已存在,更新操作
            for (int i = 0; i < dataList.size(); i++){
                mAnchors.set(start + i, dataList.get(i));
            }
        } else {
            mAnchors.addAll(dataList);
        }
        filterRepeatedData();
    }


    public List<HomeAnchor> getDatas() {
        return mAnchors;
    }

    //过滤重复数据
    private void filterRepeatedData() {
        HashSet<Long> ids = new HashSet<>();
        HomeAnchor anchor;
        for (int n = mAnchors.size(), i = n - 1; i >= 0; i--) {
            anchor = mAnchors.get(i);
            if (ids.contains(anchor.uid)){
                mAnchors.remove(i);
            } else {
                ids.add(anchor.uid);
            }
        }
    }



    public final void checkUpdate() {
        if (mLayoutManager == null) {
            return;
        }
        int firstPos = mLayoutManager.findFirstVisibleItemPosition();
        int topPage = firstPos / PAGE_SIZE;
        checkUpdate(topPage);

        int lastPos = mLayoutManager.findLastVisibleItemPosition();
        int bottomPage = lastPos / PAGE_SIZE;
        if (bottomPage != topPage) {
            checkUpdate(bottomPage);
        }
    }

    private void checkUpdate(int page) {
        if(mUpdateTime == null || mUpdateTime.size() == 0) {
            return ;
        }
        Long obj = mUpdateTime.get(page);
        long time = obj == null ? 0 : obj.longValue();
        long now = System.currentTimeMillis();
        if (now - time > VALID_TIME) {
            mUpdateTime.append(page, now);
            updateData(page,true);
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
    protected  void updateData(int page,boolean isAutoUpdate){
    }
    protected abstract int getContentViewId();
    protected abstract void initView();

}
