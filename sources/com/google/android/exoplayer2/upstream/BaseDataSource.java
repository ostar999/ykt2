package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes3.dex */
public abstract class BaseDataSource implements DataSource {

    @Nullable
    private DataSpec dataSpec;
    private final boolean isNetwork;
    private int listenerCount;
    private final ArrayList<TransferListener> listeners = new ArrayList<>(1);

    public BaseDataSource(boolean z2) {
        this.isNetwork = z2;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public final void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        if (this.listeners.contains(transferListener)) {
            return;
        }
        this.listeners.add(transferListener);
        this.listenerCount++;
    }

    public final void bytesTransferred(int i2) {
        DataSpec dataSpec = (DataSpec) Util.castNonNull(this.dataSpec);
        for (int i3 = 0; i3 < this.listenerCount; i3++) {
            this.listeners.get(i3).onBytesTransferred(this, dataSpec, this.isNetwork, i2);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public /* synthetic */ Map getResponseHeaders() {
        return f.a(this);
    }

    public final void transferEnded() {
        DataSpec dataSpec = (DataSpec) Util.castNonNull(this.dataSpec);
        for (int i2 = 0; i2 < this.listenerCount; i2++) {
            this.listeners.get(i2).onTransferEnd(this, dataSpec, this.isNetwork);
        }
        this.dataSpec = null;
    }

    public final void transferInitializing(DataSpec dataSpec) {
        for (int i2 = 0; i2 < this.listenerCount; i2++) {
            this.listeners.get(i2).onTransferInitializing(this, dataSpec, this.isNetwork);
        }
    }

    public final void transferStarted(DataSpec dataSpec) {
        this.dataSpec = dataSpec;
        for (int i2 = 0; i2 < this.listenerCount; i2++) {
            this.listeners.get(i2).onTransferStart(this, dataSpec, this.isNetwork);
        }
    }
}
