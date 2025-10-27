package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CircleChannelBean implements Serializable {
    private List<ChannelItem> otherChannelList;
    private String tag;
    private List<ChannelItem> userChannelList;

    public CircleChannelBean() {
    }

    public List<ChannelItem> getOtherChannelList() {
        return this.otherChannelList;
    }

    public String getTag() {
        return this.tag;
    }

    public List<ChannelItem> getUserChannelList() {
        return this.userChannelList;
    }

    public void setOtherChannelList(List<ChannelItem> otherChannelList) {
        this.otherChannelList = otherChannelList;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUserChannelList(List<ChannelItem> userChannelList) {
        this.userChannelList = userChannelList;
    }

    public CircleChannelBean(String tag, List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
        this.tag = tag;
        this.userChannelList = userChannelList;
        this.otherChannelList = otherChannelList;
    }
}
