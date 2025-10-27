package com.easefun.polyv.livescenes.chatroom.send.custom;

import com.plv.livescenes.chatroom.send.custom.PLVCustomEvent;

@Deprecated
/* loaded from: classes3.dex */
public class PolyvCustomEvent<T> extends PLVCustomEvent<T> {
    public PolyvCustomEvent(String str, T t2) {
        super(str, t2);
    }

    public PolyvCustomEvent(String str, String str2, T t2) {
        super(str, str2, t2);
    }

    public PolyvCustomEvent(String str, int i2, T t2) {
        super(str, i2, t2);
    }

    public PolyvCustomEvent(String str, int i2, String str2, T t2) {
        super(str, i2, str2, t2);
    }
}
