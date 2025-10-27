package com.google.android.gms.common.api;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Result;

/* loaded from: classes3.dex */
public class Response<T extends Result> {
    private T zzap;

    public Response() {
    }

    @NonNull
    public T getResult() {
        return this.zzap;
    }

    public void setResult(@NonNull T t2) {
        this.zzap = t2;
    }

    public Response(@NonNull T t2) {
        this.zzap = t2;
    }
}
