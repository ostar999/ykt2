package com.google.android.gms.dynamic;

import android.os.Bundle;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import java.util.Iterator;

/* loaded from: classes3.dex */
final class zaa implements OnDelegateCreatedListener<Object> {
    private final /* synthetic */ DeferredLifecycleHelper zart;

    public zaa(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.zart = deferredLifecycleHelper;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.dynamic.OnDelegateCreatedListener
    public final void onDelegateCreated(Object obj) {
        this.zart.zaru = obj;
        Iterator it = this.zart.zarw.iterator();
        while (it.hasNext()) {
            ((DeferredLifecycleHelper.zaa) it.next()).zaa(this.zart.zaru);
        }
        this.zart.zarw.clear();
        DeferredLifecycleHelper.zaa(this.zart, (Bundle) null);
    }
}
