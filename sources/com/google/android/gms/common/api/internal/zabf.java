package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.util.concurrent.NumberedThreadFactory;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final class zabf {
    private static final ExecutorService zahy = com.google.android.gms.internal.base.zan.zact().zaa(2, new NumberedThreadFactory("GAC_Executor"), com.google.android.gms.internal.base.zao.zasg);

    public static ExecutorService zaaz() {
        return zahy;
    }
}
