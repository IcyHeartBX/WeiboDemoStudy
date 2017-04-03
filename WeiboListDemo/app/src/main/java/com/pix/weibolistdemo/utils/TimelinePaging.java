package com.pix.weibolistdemo.utils;

import android.text.TextUtils;

import com.pix.weibolistdemo.bean.StatusContent;
import com.pix.weibolistdemo.bean.StatusContents;

import org.aisen.android.support.paging.IPaging;

/**
 * Created by wangdan on 16/1/7.
 */
public class TimelinePaging implements IPaging<StatusContent, StatusContents> {

    private static final long serialVersionUID = -1563104012290641720L;

    private String firstId;

    private String lastId;

    @Override
    public void processData(StatusContents newDatas, StatusContent firstData, StatusContent lastData) {
        if (firstData != null)
            firstId = AisenUtils.getId(firstData);
        if (lastData != null)
            lastId = AisenUtils.getId(lastData);
    }

    @Override
    public String getPreviousPage() {
        return firstId;
    }

    @Override
    public String getNextPage() {
        if (TextUtils.isEmpty(lastId))
            return null;

        return (Long.parseLong(lastId) - 1) + "";
    }

}
