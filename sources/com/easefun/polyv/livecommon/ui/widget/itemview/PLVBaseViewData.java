package com.easefun.polyv.livecommon.ui.widget.itemview;

/* loaded from: classes3.dex */
public class PLVBaseViewData<Data> {
    public static final int ITEMTYPE_UNDEFINED = 0;
    private Data data;
    private int itemType;
    private Object tag;

    public PLVBaseViewData(Data data, int itemType) {
        this.data = data;
        this.itemType = itemType;
    }

    public Data getData() {
        return this.data;
    }

    public int getItemType() {
        return this.itemType;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public PLVBaseViewData(Data data, int itemType, Object tag) {
        this.data = data;
        this.itemType = itemType;
        this.tag = tag;
    }
}
