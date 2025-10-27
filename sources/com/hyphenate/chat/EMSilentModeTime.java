package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMASilentModeTime;

/* loaded from: classes4.dex */
public class EMSilentModeTime extends EMBase<EMASilentModeTime> {
    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.hyphenate.chat.adapter.EMASilentModeTime] */
    public EMSilentModeTime(int i2, int i3) {
        this.emaObject = new EMASilentModeTime(i2, i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMSilentModeTime(EMASilentModeTime eMASilentModeTime) {
        this.emaObject = eMASilentModeTime;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getHour() {
        T t2 = this.emaObject;
        if (t2 != 0) {
            return ((EMASilentModeTime) t2).getHour();
        }
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMinute() {
        T t2 = this.emaObject;
        if (t2 != 0) {
            return ((EMASilentModeTime) t2).getMinute();
        }
        return 0;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setHour(int i2) {
        ((EMASilentModeTime) this.emaObject).setHour(i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setMinute(int i2) {
        ((EMASilentModeTime) this.emaObject).setMinute(i2);
    }
}
