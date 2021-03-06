package com.pix.weibohomedemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.pix.weibohomedemo.R;


public class GImageButton extends ImageButton {
	private static final int CLICK_INTERVAL_DEFAULT=1000;
	private long lastClickTime;
	private int clickInterval=0;

	public GImageButton(Context context) {
		super(context);
		init(context,null);
	}
	
	public GImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}

	public GImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}

	public void init(Context context,AttributeSet attrs) {
		
		if(attrs!=null){
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.li_GButton);
			clickInterval=a.getInt(R.styleable.li_GButton_li_clickInterval, CLICK_INTERVAL_DEFAULT);
			a.recycle();	
		}else{
			clickInterval=CLICK_INTERVAL_DEFAULT;	
		}
	}

	@Override
	public boolean performClick() {
		if (isFastDoubleClick())
			return true;
		return super.performClick();
	}

	public boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeSpan = time - lastClickTime;
		if (0 < timeSpan && timeSpan < clickInterval) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
