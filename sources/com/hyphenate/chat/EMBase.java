package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMABase;

/* loaded from: classes4.dex */
class EMBase<T> {
    protected T emaObject;

    public boolean equals(Object obj) {
        T t2 = this.emaObject;
        return (t2 == null || !(t2 instanceof EMABase) || obj == null || !(obj instanceof EMBase)) ? (t2 == null || !(t2 instanceof EMABase) || obj == null || !(obj instanceof EMABase)) ? super.equals(obj) : t2.equals(obj) : t2.equals(((EMBase) obj).emaObject);
    }

    public int hashCode() {
        T t2 = this.emaObject;
        return (t2 == null || !(t2 instanceof EMABase)) ? super.hashCode() : t2.hashCode();
    }
}
