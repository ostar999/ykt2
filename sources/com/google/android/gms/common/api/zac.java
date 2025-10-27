package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.ShowFirstParty;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.concurrent.GuardedBy;

@ShowFirstParty
/* loaded from: classes3.dex */
public abstract class zac {

    @GuardedBy("sLock")
    private static final Map<Object, zac> zacm = new WeakHashMap();
    private static final Object sLock = new Object();

    public abstract void remove(int i2);
}
