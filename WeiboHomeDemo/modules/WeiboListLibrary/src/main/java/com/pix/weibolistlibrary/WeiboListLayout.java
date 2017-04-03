package com.pix.weibolistlibrary;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.pix.weibolistlibrary.bean.StatusContent;
import com.pix.weibolistlibrary.bean.WeiBoUser;

import java.util.ArrayList;

/**
 * Created by pix on 2017/3/25.
 */

public class WeiboListLayout extends FrameLayout {
    private static final String TAG = "WeiboListFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WeiboListAdapter mWeiboListAdapter;
    private ArrayList<StatusContent> mContentList = new ArrayList<>();

    public WeiboListLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_recycler_list,this);
        initView(this);
        initData();
    }
    private  void initView(View v) {
        Log.d(TAG,"initView()");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rcv_list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mWeiboListAdapter = new WeiboListAdapter(getContext(),mContentList);
        mRecyclerView.setAdapter(mWeiboListAdapter);
    }

    private void initData() {
        Log.d(TAG,"initData()");
        for (int i = 0 ;i < 10 ;i++) {
            StatusContent bean = new StatusContent();
            WeiBoUser user = new WeiBoUser();
            user.setName("微博達人：" + i);
            bean.setUser(user);
            mContentList.add(bean);
        }
        mWeiboListAdapter.notifyDataSetChanged();
    }
}
