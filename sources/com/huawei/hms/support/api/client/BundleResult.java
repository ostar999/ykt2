package com.huawei.hms.support.api.client;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class BundleResult {

    /* renamed from: a, reason: collision with root package name */
    private int f8073a;

    /* renamed from: b, reason: collision with root package name */
    private Bundle f8074b;

    public BundleResult(int i2, Bundle bundle) {
        this.f8073a = i2;
        this.f8074b = bundle;
    }

    public int getResultCode() {
        return this.f8073a;
    }

    public Bundle getRspBody() {
        return this.f8074b;
    }

    public void setResultCode(int i2) {
        this.f8073a = i2;
    }

    public void setRspBody(Bundle bundle) {
        this.f8074b = bundle;
    }
}
