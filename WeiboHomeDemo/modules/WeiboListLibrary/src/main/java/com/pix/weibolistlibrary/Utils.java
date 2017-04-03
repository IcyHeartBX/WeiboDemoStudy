package com.pix.weibolistlibrary;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * Created by Administrator on 2017/3/27.
 */

public class Utils {
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;
    public static int getScreenWidth() {
        if (SCREEN_WIDTH == 0) {
            readScreenSize();
        }
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        if (SCREEN_HEIGHT == 0) {
            readScreenSize();
        }
        return SCREEN_HEIGHT;
    }
    private static void readScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        boolean isFlag = (BaseApplication.getInstance().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (SCREEN_HEIGHT < SCREEN_WIDTH && !isFlag) {
            SCREEN_HEIGHT = SCREEN_HEIGHT ^ SCREEN_WIDTH;
            SCREEN_WIDTH = SCREEN_HEIGHT ^ SCREEN_WIDTH;
            SCREEN_HEIGHT = SCREEN_HEIGHT ^ SCREEN_WIDTH;
        }
    }
    /**
     * 根据手机分辨率从dp转成px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
