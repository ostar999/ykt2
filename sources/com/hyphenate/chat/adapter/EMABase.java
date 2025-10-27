package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMABase {
    long nativeHandler = 0;

    public native boolean _equals(EMABase eMABase);

    public native int _hashCode();

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof EMABase)) {
            EMABase eMABase = (EMABase) obj;
            if (this.nativeHandler == eMABase.nativeHandler || _equals(eMABase)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i_hashCode = _hashCode();
        return i_hashCode == 0 ? super.hashCode() : i_hashCode;
    }
}
