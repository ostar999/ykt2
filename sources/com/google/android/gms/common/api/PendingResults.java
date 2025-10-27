package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.api.internal.OptionalPendingResultImpl;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
/* loaded from: classes3.dex */
public final class PendingResults {

    public static final class zaa<R extends Result> extends BasePendingResult<R> {
        private final R zack;

        public zaa(R r2) {
            super(Looper.getMainLooper());
            this.zack = r2;
        }

        @Override // com.google.android.gms.common.api.internal.BasePendingResult
        public final R createFailedResult(Status status) {
            if (status.getStatusCode() == this.zack.getStatus().getStatusCode()) {
                return this.zack;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    public static final class zab<R extends Result> extends BasePendingResult<R> {
        public zab(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override // com.google.android.gms.common.api.internal.BasePendingResult
        public final R createFailedResult(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    public static final class zac<R extends Result> extends BasePendingResult<R> {
        private final R zacl;

        public zac(GoogleApiClient googleApiClient, R r2) {
            super(googleApiClient);
            this.zacl = r2;
        }

        @Override // com.google.android.gms.common.api.internal.BasePendingResult
        public final R createFailedResult(Status status) {
            return this.zacl;
        }
    }

    @KeepForSdk
    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        StatusPendingResult statusPendingResult = new StatusPendingResult(Looper.getMainLooper());
        statusPendingResult.cancel();
        return statusPendingResult;
    }

    @KeepForSdk
    public static <R extends Result> PendingResult<R> immediateFailedResult(R r2, GoogleApiClient googleApiClient) {
        Preconditions.checkNotNull(r2, "Result must not be null");
        Preconditions.checkArgument(!r2.getStatus().isSuccess(), "Status code must not be SUCCESS");
        zac zacVar = new zac(googleApiClient, r2);
        zacVar.setResult(r2);
        return zacVar;
    }

    @KeepForSdk
    public static PendingResult<Status> immediatePendingResult(Status status) {
        Preconditions.checkNotNull(status, "Result must not be null");
        StatusPendingResult statusPendingResult = new StatusPendingResult(Looper.getMainLooper());
        statusPendingResult.setResult(status);
        return statusPendingResult;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R r2) {
        Preconditions.checkNotNull(r2, "Result must not be null");
        Preconditions.checkArgument(r2.getStatus().getStatusCode() == 16, "Status code must be CommonStatusCodes.CANCELED");
        zaa zaaVar = new zaa(r2);
        zaaVar.cancel();
        return zaaVar;
    }

    @KeepForSdk
    public static PendingResult<Status> immediatePendingResult(Status status, GoogleApiClient googleApiClient) {
        Preconditions.checkNotNull(status, "Result must not be null");
        StatusPendingResult statusPendingResult = new StatusPendingResult(googleApiClient);
        statusPendingResult.setResult(status);
        return statusPendingResult;
    }

    @KeepForSdk
    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r2) {
        Preconditions.checkNotNull(r2, "Result must not be null");
        zab zabVar = new zab(null);
        zabVar.setResult(r2);
        return new OptionalPendingResultImpl(zabVar);
    }

    @KeepForSdk
    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r2, GoogleApiClient googleApiClient) {
        Preconditions.checkNotNull(r2, "Result must not be null");
        zab zabVar = new zab(googleApiClient);
        zabVar.setResult(r2);
        return new OptionalPendingResultImpl(zabVar);
    }
}
