package com.pix.weibolistdemo.utils;

import android.text.TextUtils;

import com.pix.weibolistdemo.bean.StatusComment;
import com.pix.weibolistdemo.bean.StatusComments;

import org.aisen.android.support.paging.IPaging;

/**
 * Created by wangdan on 16/1/8.
 */
public class CommentPaging implements IPaging<StatusComment, StatusComments> {

    private static final long serialVersionUID = -2363918217556704381L;

    private String firstId;

    private String lastId;

    @Override
    public void processData(StatusComments newDatas, StatusComment firstData, StatusComment lastData) {
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
