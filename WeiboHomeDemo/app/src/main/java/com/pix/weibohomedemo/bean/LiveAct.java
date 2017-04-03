package com.pix.weibohomedemo.bean;

/**
 * Created by Administrator on 2017/3/26.
 */

public class LiveAct {
    public int actId = 110;
    public String actParam = ""; //活动参数，遵从web页交互协议，即com.guagua.modules.webcmd,如果包中没有这个字段，直接走进入房间功能
    public String actImg = "https://r.jufan.tv/jufan/ad20170308/406d8c2dc8fa20fa8ab4.jpg";
    public String actTitle= "三月主播福利";
    public int actType = 1;
    public boolean isShow = true;
}
