package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class TaskApiCall<A extends Api.AnyClient, ResultT> {
    private final Feature[] zakh;
    private final boolean zako;

    @KeepForSdk
    public static class Builder<A extends Api.AnyClient, ResultT> {
        private Feature[] zakh;
        private boolean zako;
        private RemoteCall<A, TaskCompletionSource<ResultT>> zakp;

        private Builder() {
            this.zako = true;
        }

        @KeepForSdk
        public TaskApiCall<A, ResultT> build() {
            Preconditions.checkArgument(this.zakp != null, "execute parameter required");
            return new zacj(this, this.zakh, this.zako);
        }

        @KeepForSdk
        @Deprecated
        public Builder<A, ResultT> execute(final BiConsumer<A, TaskCompletionSource<ResultT>> biConsumer) {
            this.zakp = new RemoteCall(biConsumer) { // from class: com.google.android.gms.common.api.internal.zaci
                private final BiConsumer zakj;

                {
                    this.zakj = biConsumer;
                }

                @Override // com.google.android.gms.common.api.internal.RemoteCall
                public final void accept(Object obj, Object obj2) {
                    this.zakj.accept((Api.AnyClient) obj, (TaskCompletionSource) obj2);
                }
            };
            return this;
        }

        @KeepForSdk
        public Builder<A, ResultT> run(RemoteCall<A, TaskCompletionSource<ResultT>> remoteCall) {
            this.zakp = remoteCall;
            return this;
        }

        @KeepForSdk
        public Builder<A, ResultT> setAutoResolveMissingFeatures(boolean z2) {
            this.zako = z2;
            return this;
        }

        @KeepForSdk
        public Builder<A, ResultT> setFeatures(Feature... featureArr) {
            this.zakh = featureArr;
            return this;
        }
    }

    @KeepForSdk
    @Deprecated
    public TaskApiCall() {
        this.zakh = null;
        this.zako = false;
    }

    @KeepForSdk
    public static <A extends Api.AnyClient, ResultT> Builder<A, ResultT> builder() {
        return new Builder<>();
    }

    @KeepForSdk
    public abstract void doExecute(A a3, TaskCompletionSource<ResultT> taskCompletionSource) throws RemoteException;

    @KeepForSdk
    public boolean shouldAutoResolveMissingFeatures() {
        return this.zako;
    }

    @Nullable
    public final Feature[] zabr() {
        return this.zakh;
    }

    @KeepForSdk
    private TaskApiCall(Feature[] featureArr, boolean z2) {
        this.zakh = featureArr;
        this.zako = z2;
    }
}
