package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.data.DataHolder;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class DataHolderNotifier<L> implements ListenerHolder.Notifier<L> {
    private final DataHolder mDataHolder;

    @KeepForSdk
    public DataHolderNotifier(DataHolder dataHolder) {
        this.mDataHolder = dataHolder;
    }

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    @KeepForSdk
    public final void notifyListener(L l2) {
        notifyListener(l2, this.mDataHolder);
    }

    @KeepForSdk
    public abstract void notifyListener(L l2, DataHolder dataHolder);

    @Override // com.google.android.gms.common.api.internal.ListenerHolder.Notifier
    @KeepForSdk
    public void onNotifyListenerFailed() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
