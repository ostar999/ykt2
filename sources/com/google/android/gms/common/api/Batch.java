package com.google.android.gms.common.api;

import com.google.android.gms.common.api.internal.BasePendingResult;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class Batch extends BasePendingResult<BatchResult> {
    private final Object mLock;
    private int zabc;
    private boolean zabd;
    private boolean zabe;
    private final PendingResult<?>[] zabf;

    public static final class Builder {
        private List<PendingResult<?>> zabg = new ArrayList();
        private GoogleApiClient zabh;

        public Builder(GoogleApiClient googleApiClient) {
            this.zabh = googleApiClient;
        }

        public final <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken<>(this.zabg.size());
            this.zabg.add(pendingResult);
            return batchResultToken;
        }

        public final Batch build() {
            return new Batch(this.zabg, this.zabh, null);
        }
    }

    private Batch(List<PendingResult<?>> list, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.mLock = new Object();
        int size = list.size();
        this.zabc = size;
        PendingResult<?>[] pendingResultArr = new PendingResult[size];
        this.zabf = pendingResultArr;
        if (list.isEmpty()) {
            setResult(new BatchResult(Status.RESULT_SUCCESS, pendingResultArr));
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            PendingResult<?> pendingResult = list.get(i2);
            this.zabf[i2] = pendingResult;
            pendingResult.addStatusListener(new zaa(this));
        }
    }

    public static /* synthetic */ boolean zab(Batch batch, boolean z2) {
        batch.zabd = true;
        return true;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult, com.google.android.gms.common.api.PendingResult
    public final void cancel() {
        super.cancel();
        for (PendingResult<?> pendingResult : this.zabf) {
            pendingResult.cancel();
        }
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zabf);
    }

    public static /* synthetic */ boolean zaa(Batch batch, boolean z2) {
        batch.zabe = true;
        return true;
    }

    public static /* synthetic */ int zab(Batch batch) {
        int i2 = batch.zabc;
        batch.zabc = i2 - 1;
        return i2;
    }

    public /* synthetic */ Batch(List list, GoogleApiClient googleApiClient, zaa zaaVar) {
        this(list, googleApiClient);
    }
}
