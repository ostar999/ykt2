package com.google.android.gms.dynamic;

import com.google.android.gms.dynamic.DeferredLifecycleHelper;

/* loaded from: classes3.dex */
final class zaf implements DeferredLifecycleHelper.zaa {
    private final /* synthetic */ DeferredLifecycleHelper zart;

    public zaf(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zart = deferredLifecycleHelper;
    }

    @Override // com.google.android.gms.dynamic.DeferredLifecycleHelper.zaa
    public final int getState() {
        return 5;
    }

    @Override // com.google.android.gms.dynamic.DeferredLifecycleHelper.zaa
    public final void zaa(LifecycleDelegate lifecycleDelegate) {
        this.zart.zaru.onResume();
    }
}
