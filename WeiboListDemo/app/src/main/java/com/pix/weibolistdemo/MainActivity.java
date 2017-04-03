package com.pix.weibolistdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.pix.weibolistdemo.ui.WeiboListFragment;

public class MainActivity extends AppCompatActivity {
    private FrameLayout mContentLayout;
    private FragmentManager mFragmentManager;
    private WeiboListFragment mWeiboListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContentLayout = (FrameLayout) findViewById(R.id.content_frame);
        mWeiboListFragment = new WeiboListFragment();
        mFragmentManager = getFragmentManager();
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        trans.add(R.id.content_frame,mWeiboListFragment,"list");
        trans.commit();
    }

}
