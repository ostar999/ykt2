package com.google.android.exoplayer2.upstream;

/* loaded from: classes3.dex */
public interface TransferListener {
    void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z2, int i2);

    void onTransferEnd(DataSource dataSource, DataSpec dataSpec, boolean z2);

    void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z2);

    void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z2);
}
