package com.pix.weibohomedemo.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pix.weibohomedemo.bean.LiveAct;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeViewPagerAdapter extends PagerAdapter {
    public static final String TAG = HomeViewPagerAdapter.class.getSimpleName();
    private List<LiveAct> list;
    private Context context;
    private SparseArray<SimpleDraweeView> views;
    private LoopViewPager mPager;

    public HomeViewPagerAdapter(Context context, List<LiveAct> list, LoopViewPager viewPager) {
        this.list = list;
        this.context = context;
        mPager = viewPager;
        views = new SparseArray<SimpleDraweeView>(list.size() + 2);
    }

    public void setData(List<LiveAct> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int virtualPos;
        if (list.size() != 1) {
            virtualPos = LoopPagerAdapterWrapper.getVirtual(position);
        } else {
            virtualPos = position;
        }
        container.removeView(views.get(virtualPos));
    }

    @Override
    public Object instantiateItem(ViewGroup arg0, int position) {
        final int virtualPos;
        final int realPos;

        if (list.size() != 1) {
            virtualPos = LoopPagerAdapterWrapper.getVirtual(position);
            realPos = LoopPagerAdapterWrapper.getReal(position);
        } else {
            virtualPos = realPos = position;
        }

        final LiveAct activity = list.get(realPos);
        SimpleDraweeView sdv = views.get(virtualPos);
        /*if(sdv!=null){
            arg0.addView(sdv,0);
			return sdv;
		}*/
        if (sdv == null) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            sdv = new SimpleDraweeView(context);
            sdv.setLayoutParams(params);
            GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(context.getResources())
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                    .build();
//			gdh.setPlaceholderImage(R.mic_connect_frame.);
            sdv.setHierarchy(gdh);
            views.put(virtualPos, sdv);
        }
        sdv.setImageURI(Uri.parse(list.get(realPos).actImg));

        sdv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        try {
            arg0.addView(sdv, 0);
        } catch (Exception ignored) {
        }

        return sdv;
    }


    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }

}
