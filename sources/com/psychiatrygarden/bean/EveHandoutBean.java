package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.HandoutChannelBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class EveHandoutBean implements Serializable {
    public String mEveStr;
    List<HandoutChannelBean.DataBean.ListBean> otherChannelList;
    List<HandoutChannelBean.DataBean.DefaultBean> userChannelList;

    public EveHandoutBean(String mEveStr, List<HandoutChannelBean.DataBean.ListBean> otherChannelList, List<HandoutChannelBean.DataBean.DefaultBean> userChannelList) {
        this.otherChannelList = new ArrayList();
        new ArrayList();
        this.mEveStr = mEveStr;
        this.otherChannelList = otherChannelList;
        this.userChannelList = userChannelList;
    }

    public List<HandoutChannelBean.DataBean.ListBean> getOtherChannelList() {
        return this.otherChannelList;
    }

    public List<HandoutChannelBean.DataBean.DefaultBean> getUserChannelList() {
        return this.userChannelList;
    }

    public String getmEveStr() {
        return this.mEveStr;
    }

    public void setOtherChannelList(List<HandoutChannelBean.DataBean.ListBean> otherChannelList) {
        this.otherChannelList = otherChannelList;
    }

    public void setUserChannelList(List<HandoutChannelBean.DataBean.DefaultBean> userChannelList) {
        this.userChannelList = userChannelList;
    }

    public void setmEveStr(String mEveStr) {
        this.mEveStr = mEveStr;
    }
}
