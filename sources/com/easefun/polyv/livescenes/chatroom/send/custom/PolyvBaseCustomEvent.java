package com.easefun.polyv.livescenes.chatroom.send.custom;

import com.plv.livescenes.chatroom.send.custom.PLVBaseCustomEvent;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvBaseCustomEvent<DataBean> extends PLVBaseCustomEvent<DataBean> {
    public PolyvBaseCustomEvent(String str, DataBean databean) {
        super(str, databean);
    }

    public PolyvBaseCustomEvent(String str, String str2, DataBean databean) {
        super(str, str2, databean);
    }

    public PolyvBaseCustomEvent(String str, int i2, DataBean databean) {
        super(str, i2, databean);
    }

    public PolyvBaseCustomEvent(String str, int i2, String str2, DataBean databean) {
        super(str, i2, str2, databean);
    }
}
