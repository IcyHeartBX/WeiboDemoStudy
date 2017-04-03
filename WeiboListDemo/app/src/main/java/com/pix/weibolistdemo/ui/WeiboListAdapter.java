package com.pix.weibolistdemo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pix.weibolistdemo.R;
import com.pix.weibolistdemo.bean.StatusContent;

import java.util.ArrayList;

/**
 * 展示微博大廳列表
 * Created by pix on 2017/3/25.
 */

public class WeiboListAdapter extends RecyclerView.Adapter<WeiboListAdapter.ViewHolder> {
    private static final String TAG = "WeiboListAdapter";
    private ArrayList<StatusContent> mList;
    private Context mContext;

    public WeiboListAdapter(Context ctx, ArrayList<StatusContent> list) {
        mContext = ctx;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(
                mContext).inflate(R.layout.item_weibo_card,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder()");
        holder.nameTV.setText(mList.get(position).getUser().getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.txtName);
        }
    }
}
