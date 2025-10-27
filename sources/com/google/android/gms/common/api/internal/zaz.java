package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public final class zaz {
    private final Map<BasePendingResult<?>, Boolean> zafm = Collections.synchronizedMap(new WeakHashMap());
    private final Map<TaskCompletionSource<?>, Boolean> zafn = Collections.synchronizedMap(new WeakHashMap());

    public final void zaa(BasePendingResult<? extends Result> basePendingResult, boolean z2) {
        this.zafm.put(basePendingResult, Boolean.valueOf(z2));
        basePendingResult.addStatusListener(new zaac(this, basePendingResult));
    }

    public final boolean zaae() {
        return (this.zafm.isEmpty() && this.zafn.isEmpty()) ? false : true;
    }

    public final void zaaf() {
        zaa(false, GoogleApiManager.zaib);
    }

    public final void zaag() {
        zaa(true, zacp.zalb);
    }

    public final <TResult> void zaa(TaskCompletionSource<TResult> taskCompletionSource, boolean z2) {
        this.zafn.put(taskCompletionSource, Boolean.valueOf(z2));
        taskCompletionSource.getTask().addOnCompleteListener(new zaab(this, taskCompletionSource));
    }

    private final void zaa(boolean z2, Status status) {
        HashMap map;
        HashMap map2;
        synchronized (this.zafm) {
            map = new HashMap(this.zafm);
        }
        synchronized (this.zafn) {
            map2 = new HashMap(this.zafn);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (z2 || ((Boolean) entry.getValue()).booleanValue()) {
                ((BasePendingResult) entry.getKey()).zab(status);
            }
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            if (z2 || ((Boolean) entry2.getValue()).booleanValue()) {
                ((TaskCompletionSource) entry2.getKey()).trySetException(new ApiException(status));
            }
        }
    }
}
