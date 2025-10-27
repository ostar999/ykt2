package com.plv.livescenes.model;

import com.plv.foundationsdk.net.PLVResponseBean;

/* loaded from: classes5.dex */
public class PLVLiveStatusVO<T> extends PLVResponseBean<T> {
    private String data;

    public PLVLiveStatusVO(String str) {
        super(str);
        this.data = "";
    }

    public String getChannelType() {
        String[] strArrSplit = this.data.split(",");
        return strArrSplit.length < 2 ? "" : strArrSplit[1];
    }

    public String getData() {
        return this.data;
    }

    public String getLiveStatus() {
        String[] strArrSplit = this.data.split(",");
        return strArrSplit.length < 2 ? "" : strArrSplit[0];
    }

    public void setData(String str) {
        this.data = str;
    }
}
