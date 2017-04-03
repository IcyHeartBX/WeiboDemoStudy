package com.pix.weibohomedemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pix.weibohomedemo.widget.LatestLayout;
import com.pix.weibolistlibrary.WeiboListLayout;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/26.
 */

public class HomeTabFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private static final String TAG = "HomeTabFragment";
    private static final int COLOR_TAB_SELECTED = 0xFFE5006E;
    private static final int COLOR_TAB_NORMAL = 0xB3363535;
    private static final float SIZE_TAB_SELECTED = 18;
    private static final float SIZE_TAB_NORMAL = 17;
    private static final int COLOR_TAB_DOT_SELECTED = 0xFFE5006E;
    private static final int COLOR_TAB_DOT_NORMAL = 0x00000000;
    public static final int POS_HOT = 0;
    public static final int POS_LASTED = 1;


    /** 根布局 */
    private RelativeLayout mRlRoot;
    /** 整体内容ViewPager */
    private ViewPager mContentVP;
    /** 导航条布局 */
    private LinearLayout mLayout;
    //热门的TAB
    private TextView mHotTab;
    //广场的TAB
    private TextView mNewestTab;
    // TAB集合
    private ArrayList<TextView> mTabList;
    // 热门下面的条
    private TextView mHotDot;
    // 广场下面的调
    private TextView mNewestDot;
    // 条的集合
    private ArrayList<TextView> mTabDotList;

    // 处理下面的内容
    private ArrayList<ViewGroup> mVPList;
    //热门的页面
    private HotLayout mHotLayout;
    //广场（最新）的页面
    private LatestLayout mLatestLayout;

    private WeiboListLayout mWeiboLayout;

    //vp的数据源适配器
    private HomePagerAdapter mHomePagerAdapter;

    // 下面的大按钮
    private ImageView creatRoomButton;

    private boolean mIsAnim = false;

    private float lastX = 0;
    private float lastY = 0;
    private boolean isDown = false;
    private boolean isUp = false;
    private boolean mIsTitleHide = false;

    private OldActivity.MyOnTouchListener myOnTouchListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.li_fragment_tabmain_home, null);
        initView(view);
        return view;
    }

    private void initView(View v) {
        this.mRlRoot = (RelativeLayout) v.findViewById(R.id.rl_root);
        this.mContentVP = (ViewPager) v.findViewById(R.id.vp_tabmain_home);
        //mContentVP.setLayoutParams();
        this.mLayout = (LinearLayout) v.findViewById(R.id.tab_layout);

        // 设置TAB标签
        this.mHotTab = (TextView) v.findViewById(R.id.tab_home_hot);
        this.mNewestTab = (TextView) v.findViewById(R.id.tab_home_newest);
        this.mHotTab.setOnClickListener(this);
        this.mNewestTab.setOnClickListener(this);
        this.mTabList = new ArrayList<>();
        //this.mTabList.add(mAttentionTab);
        this.mTabList.add(mHotTab);
        this.mTabList.add(mNewestTab);

        // TAB 下面的小条
        this.mHotDot = (TextView) v.findViewById(R.id.dot_home_hot);
        this.mNewestDot = (TextView) v.findViewById(R.id.dot_home_newest);
        this.mTabDotList = new ArrayList<>();
        //this.mTabDotList.add(this.mAttentionDot);
        this.mTabDotList.add(this.mHotDot);
        this.mTabDotList.add(this.mNewestDot);
        selectedTab(POS_HOT);
        selectedTabDot(POS_HOT);

        // ViewPager 的内容
        this.mContentVP = (ViewPager) v.findViewById(R.id.vp_tabmain_home);
        //mContentVP.setLayoutParams();
        this.mLayout = (LinearLayout) v.findViewById(R.id.tab_layout);
        this.mContentVP.setOnPageChangeListener(this);
        this.mContentVP.setOffscreenPageLimit(3);
        this.mVPList = new ArrayList<>();
//        this.mAttentionLayout = new AttentionLayout(getContext());
//        this.mAttentionLayout.setOnSelectedHomeTABListener(this);
        this.mWeiboLayout = new WeiboListLayout(getActivity());
        this.mVPList.add(mWeiboLayout);
        this.mHotLayout = new HotLayout(getContext());
        //this.mLatestLayout = new LatestLayout(getContext());
//        this.mVPList.add(this.mAttentionLayout);
        this.mVPList.add(this.mHotLayout);

        //this.mVPList.add(this.mLatestLayout);
        this.mHomePagerAdapter = new HomePagerAdapter(this.mVPList);
        this.mContentVP.setAdapter(this.mHomePagerAdapter);
        this.creatRoomButton = (ImageView) v.findViewById(R.id.btn_main_create_room);

        initListener();

    }

    private void selectedTab(int position) {
        for (int i = 0; i < mTabList.size(); i++) {
            mTabList.get(i).setTextColor(COLOR_TAB_NORMAL);
            mTabList.get(i).setTextSize(SIZE_TAB_NORMAL);
        }
        mTabList.get(position).setTextColor(COLOR_TAB_SELECTED);
        mTabList.get(position).setTextSize(SIZE_TAB_SELECTED);
    }

    private void selectedTabDot(int position) {
        for (int i = 0; i < mTabDotList.size(); i++) {
            mTabDotList.get(i).setBackgroundColor(COLOR_TAB_DOT_NORMAL);
        }
        mTabDotList.get(position).setBackgroundColor(COLOR_TAB_DOT_SELECTED);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_home_hot:
                this.mContentVP.setCurrentItem(POS_HOT);
                selectedTabDot(POS_HOT);
                selectedTab(POS_HOT);
                //mHotLayout.checkUpdate();
                break;
            case R.id.tab_home_newest:
                this.mContentVP.setCurrentItem(POS_LASTED);
                selectedTabDot(POS_LASTED);
                selectedTab(POS_LASTED);
                //mLatestLayout.checkUpdate();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectedTab(position);
        selectedTabDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void initListener() {
        myOnTouchListener = new OldActivity.MyOnTouchListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return dispathTouchEvent(ev);
            }
        };
        ((OldActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);
    }

    private boolean dispathTouchEvent(MotionEvent event) {
        if (mIsAnim) {
            return false;
        }
        final int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY ? true : false;
                lastY = y;
                lastX = x;
//                isUp = dY > dX && dY > 20 && !mIsTitleHide && !down && mHotLayout.isHideTitle && mLatestLayout.isHideTitle;
                isUp = dY > dX && dY > 20 && !mIsTitleHide && !down && mHotLayout.isHideTitle ;
                isDown = dY > dX && dY > 20 && mIsTitleHide && down;
                if (isUp) {
                    startAnimator(true);
                } else if (isDown) {
                    startAnimator(false);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                mIsAnim = true;
                return true;
            default:
                return false;
        }
    }


    private void startAnimator(boolean UpOrDown) {
        float[] mLayoutF = new float[2];
        int[] mLf = new int[2];
        float[] startLiveF = new float[2];
        final RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        if (UpOrDown) {
            mLayoutF[0] = 0f;
            mLayoutF[1] = -mLayout.getHeight();
            mLf[0] = mLayout.getHeight();
            mLf[1] = 0;
            startLiveF[0] = 0f;
            startLiveF[1] = creatRoomButton.getHeight() + 60;
        } else {
            mLayoutF[0] = -mLayout.getHeight();
            mLayoutF[1] = 0f;
            mLf[0] =0;
            mLf[1] = mLayout.getHeight();
            startLiveF[0] = creatRoomButton.getHeight() + 60;
            startLiveF[1] = 0f;
        }
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mLayout, "translationY", mLayoutF);
        ValueAnimator anim = ValueAnimator.ofInt(mLf);
        anim.setDuration(199);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(creatRoomButton, "translationY", startLiveF);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.setDuration(200);
        animator2.setDuration(200);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnim = false;
            }
        });
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                layoutParam.setMargins(0, currentValue, 0, 0);
                mContentVP.setLayoutParams(layoutParam);
            }
        });
        animator1.start();
        animator2.start();
        anim.start();
        // setMarginTop(0);
    }
}
