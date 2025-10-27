package com.google.android.gms.common.api;

import com.google.android.gms.common.api.PendingResult;

/* loaded from: classes3.dex */
final class zaa implements PendingResult.StatusListener {
    private final /* synthetic */ Batch zabb;

    public zaa(Batch batch) {
        this.zabb = batch;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        synchronized (this.zabb.mLock) {
            if (this.zabb.isCanceled()) {
                return;
            }
            if (status.isCanceled()) {
                Batch.zaa(this.zabb, true);
            } else if (!status.isSuccess()) {
                Batch.zab(this.zabb, true);
            }
            Batch.zab(this.zabb);
            if (this.zabb.zabc == 0) {
                if (this.zabb.zabe) {
                    super/*com.google.android.gms.common.api.internal.BasePendingResult*/.cancel();
                } else {
                    Status status2 = this.zabb.zabd ? new Status(13) : Status.RESULT_SUCCESS;
                    Batch batch = this.zabb;
                    batch.setResult(new BatchResult(status2, batch.zabf));
                }
            }
        }
    }
}
