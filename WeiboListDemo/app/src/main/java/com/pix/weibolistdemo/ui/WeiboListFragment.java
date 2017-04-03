package com.pix.weibolistdemo.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pix.weibolistdemo.R;
import com.pix.weibolistdemo.bean.StatusContent;
import com.pix.weibolistdemo.bean.WeiBoUser;
import com.pix.weibolistdemo.bean.WeiboUsers;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by pix on 2017/3/25.
 */

public class WeiboListFragment extends Fragment {
    private static final String TAG = "WeiboListFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WeiboListAdapter mWeiboListAdapter;
    private ArrayList<StatusContent> mContentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView()");
        View v = inflater.inflate(R.layout.fragment_recycler_list,container,false);
        initView(v);
        initData();
        return v;
    }

    private  void initView(View v) {
        Log.d(TAG,"initView()");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rcv_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mWeiboListAdapter = new WeiboListAdapter(getActivity(),mContentList);
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
