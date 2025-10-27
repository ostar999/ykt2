package com.google.android.gms.common;

import android.content.Intent;

/* loaded from: classes3.dex */
public class GooglePlayServicesRepairableException extends UserRecoverableException {
    private final int zzag;

    public GooglePlayServicesRepairableException(int i2, String str, Intent intent) {
        super(str, intent);
        this.zzag = i2;
    }

    public int getConnectionStatusCode() {
        return this.zzag;
    }
}
