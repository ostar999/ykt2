package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMADeviceInfo;

/* loaded from: classes4.dex */
public class EMDeviceInfo extends EMBase<EMADeviceInfo> {
    EMADeviceInfo emaObject;

    public EMDeviceInfo(EMADeviceInfo eMADeviceInfo) {
        this.emaObject = eMADeviceInfo;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getDeviceName() {
        return this.emaObject.getDeviceName();
    }

    public String getDeviceUUID() {
        return this.emaObject.getDeviceUUID();
    }

    public String getResource() {
        return this.emaObject.getResource();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
