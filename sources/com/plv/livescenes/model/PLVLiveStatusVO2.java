package com.plv.livescenes.model;

import com.plv.foundationsdk.net.PLVResponseBean;

/* loaded from: classes5.dex */
public class PLVLiveStatusVO2<T> extends PLVResponseBean<T> {
    private Object data;
    private boolean encryption;

    public PLVLiveStatusVO2(String str) {
        super(str);
    }

    public String getChannelType() {
        String[] strArrSplit = getData().split(",");
        return strArrSplit.length < 2 ? "" : strArrSplit[1];
    }

    public String getData() {
        return (String) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getLiveStatus() {
        String[] strArrSplit = getData().split(",");
        return strArrSplit.length < 2 ? "" : strArrSplit[0];
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setData(Object obj) {
        this.data = obj;
    }
}
