package com.pix.weibohomedemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.pix.weibohomedemo.R;


public class EnableChildSlideFrameLayout extends FrameLayout {
	private ViewPagerModify vp;

	public EnableChildSlideFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private float lastX;
	private float lastY;
	private boolean isScrolling = false;
	private boolean isDisableLoop = true;

	public void setDisableLoop(boolean disabled) {
		isDisableLoop = disabled;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return vp.onTouchEvent(event);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return vp.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (vp == null) {
			vp = (ViewPagerModify) findViewById(R.id.pager_container);
		}
		if (vp == null) {
			return super.dispatchTouchEvent(ev);
		}
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = ev.getX();
				lastY = ev.getY();
				isScrolling = false;
				requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				float distanceX = ev.getX() - lastX;
				float distanceY = ev.getY() - lastY;
				if (vp.getCurrentItem() == 0 && isDisableLoop && distanceX >= 0) { //第一个
					if (isScrolling) {
						break;
					}

					if (distanceX > 10 && distanceX > Math.abs(distanceY)) {
						requestDisallowInterceptTouchEvent(true);
						requestDisallowInterceptTouchEvent(false);
						return false;
					}
					else if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > 10) {
						isScrolling = true;
						requestDisallowInterceptTouchEvent(true);
						requestDisallowInterceptTouchEvent(false);
					}
					else if (distanceX < -10 && Math.abs(distanceX) > Math.abs(distanceY)) {
						requestDisallowInterceptTouchEvent(true);
					}
					else {
						requestDisallowInterceptTouchEvent(true);
					}
				}
				else if ((vp.getCurrentItem() == vp.getChildCount() - 1) && isDisableLoop && distanceX <= 0) {//最后一个
					if (isScrolling) {
						break;
					}
					if (distanceX > 10 && distanceX > Math.abs(distanceY)) {
						requestDisallowInterceptTouchEvent(true);

					}
					else if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > 10) {
						isScrolling = true;
						requestDisallowInterceptTouchEvent(true);
						requestDisallowInterceptTouchEvent(false);
					}
					else if (distanceX < -10 && Math.abs(distanceX) > Math.abs(distanceY)) {
						requestDisallowInterceptTouchEvent(true);
						requestDisallowInterceptTouchEvent(false);
						return false;
					}
					else {
						requestDisallowInterceptTouchEvent(true);
					}
				}
				else {
					if (Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > 50) {//上下滑动
						requestDisallowInterceptTouchEvent(false);
					}
					else {
						requestDisallowInterceptTouchEvent(true);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				isScrolling = false;
				break;
			default:
				break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
