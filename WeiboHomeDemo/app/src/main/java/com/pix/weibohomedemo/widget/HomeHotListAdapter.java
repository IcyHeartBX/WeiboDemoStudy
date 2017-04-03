package com.pix.weibohomedemo.widget;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pix.weibohomedemo.HomeAnchorClickListener;
import com.pix.weibohomedemo.R;
import com.pix.weibohomedemo.bean.HomeAnchor;

import java.util.List;

public class HomeHotListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private HomeAnchorClickListener mAnchorListener ;
    public static final int TYPE_HEADER = 0 ;
    public static final int TYPE_NORMAL = 1 ;
    private View mHeaderView ;
    protected List<HomeAnchor> mHotAnchorList;


    public HomeHotListAdapter( List<HomeAnchor> list,HomeAnchorClickListener listener) {
        this.mHotAnchorList = list;
        this.mAnchorListener = listener;
    }

    public void setHeaderView (View  headerView) {
        mHeaderView = headerView ;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_HEADER ;
        }
        return TYPE_NORMAL ;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ContentViewHolder (mHeaderView);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_item_home_hot, parent, false);
        return new ContentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if(getItemViewType(pos) == TYPE_HEADER) {
            return ;
        }
        if(getItemViewType(pos) == TYPE_NORMAL){
            if(mHeaderView != null) {
                pos -= 1;
            }
            HomeAnchor data = mHotAnchorList.get(pos);
            ((ContentViewHolder) holder).bindData(data);
        }


    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(mHotAnchorList != null) {
            size = mHotAnchorList.size();
        }
        return mHeaderView == null ? size : size + 1;
    }

    public  class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //        public SimpleDraweeView mHotAnchorHeadIV;
        public TextView mHotAnchorNameTV ;
        public TextView mHotAnchorAddrTV ;
        public TextView mHotLiveTitleTV ;
        public TextView mHotLiveCountTV ;

        public SimpleDraweeView mHotAnchorImgIv ;
        public ContentViewHolder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) {
                return ;
            }
//            mHotAnchorHeadIV = (SimpleDraweeView) itemView.findViewById(R.id.iv_hot_anchor_head);
            mHotAnchorNameTV = (TextView) itemView.findViewById(R.id.tv_hot_anchor_name);
            mHotAnchorAddrTV = (TextView) itemView.findViewById(R.id.tv_hot_anchor_addr);
            mHotLiveTitleTV =  (TextView)itemView.findViewById(R.id.tv_hot_live_title);
            mHotAnchorImgIv = (SimpleDraweeView) itemView.findViewById(R.id.iv_hot_anchor_img);
            mHotLiveCountTV = (TextView) itemView.findViewById(R.id.tv_hot_live_count);
//            mHotLiveTitileL = (LinearLayout) itemView.findViewById(R.id.tv_hot_ll);
            itemView.setOnClickListener(this);
        }

        public void bindData(HomeAnchor data) {
            itemView.setTag(data);
//            mHotAnchorHeadIV.setImageURI(Uri.parse(data.bigHeadImg));
            mHotAnchorNameTV.setText(data.userName);

            mHotAnchorAddrTV.setText(data.place);
            if(TextUtils.isEmpty(data.place)) {
                mHotAnchorAddrTV.setText("地理位置");
            }
            if(TextUtils.isEmpty(data.liveName)){
//                mHotLiveTitileL.setVisibility(View.INVISIBLE);
                mHotLiveTitleTV.setVisibility(View.GONE);
            }else{
//                mHotLiveTitileL.setVisibility(View.VISIBLE);
                mHotLiveTitleTV.setVisibility(View.VISIBLE);
                mHotLiveTitleTV.setText(String.valueOf(data.liveName));
            }

            //mHotAnchorImgIv.setMaxHeight(UIUtils.getScreenWidth());
            mHotAnchorImgIv.setImageURI(Uri.parse(data.bigHeadImg));
            RoundingParams bitmapOnlyParams = RoundingParams.fromCornersRadius(20).setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
            //bitmapOnlyParams.setCornersRadii(14,14,14,14);
            mHotAnchorImgIv.getHierarchy().setRoundingParams(bitmapOnlyParams);
            int newOnlineNum = data.onlineNum;
            //mHotLiveCountTV.setText( newOnlineNum+ "人在看");
            mHotLiveCountTV.setText(String.valueOf(newOnlineNum));
        }

        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag instanceof HomeAnchor) {
                HomeAnchor homeAnchor = (HomeAnchor) tag;
                if(mAnchorListener !=null) {
                    mAnchorListener.clickAnchor(homeAnchor);
                }
                else {

                }
            }
        }
    }
}
