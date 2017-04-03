package com.pix.weibolistdemo.timeline;

import android.content.Intent;

import com.pix.weibolistdemo.bean.Group;
import com.pix.weibolistdemo.bean.WeiBoUser;
import com.pix.weibolistdemo.utils.AisenUtils;

import org.aisen.android.common.context.GlobalContext;
import org.aisen.android.common.utils.ActivityHelper;


/**
 * Created by wangdan on 16/1/27.
 */
public abstract class TimelineMainFragment extends ATimelineFragment {

    public static void sendBroadcast() {
        GlobalContext.getInstance().sendBroadcast(new Intent("org.aisen.weibo.sina.OFFLINE_END"));
    }

    public static void clearLastRead(Group group, WeiBoUser user) {
        String key = AisenUtils.getUserKey(group.getIdstr(), user);
        ActivityHelper.putIntShareData(GlobalContext.getInstance(), key + "Position", 0);
        ActivityHelper.putIntShareData(GlobalContext.getInstance(), key + "Top", 0);
    }

}
