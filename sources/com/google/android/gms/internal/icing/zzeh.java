package com.google.android.gms.internal.icing;

import java.io.IOException;

/* loaded from: classes3.dex */
public class zzeh extends IOException {
    private zzfh zzld;

    public zzeh(String str) {
        super(str);
        this.zzld = null;
    }

    public static zzeg zzbz() {
        return new zzeg("Protocol message tag had invalid wire type.");
    }
}
