package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes.dex */
public class MutableStateObservable<T> extends StateObservable<T> {
    private MutableStateObservable(@Nullable Object obj, boolean z2) {
        super(obj, z2);
    }

    @NonNull
    public static <T> MutableStateObservable<T> withInitialError(@NonNull Throwable th) {
        return new MutableStateObservable<>(th, true);
    }

    @NonNull
    public static <T> MutableStateObservable<T> withInitialState(@Nullable T t2) {
        return new MutableStateObservable<>(t2, false);
    }

    public void setError(@NonNull Throwable th) {
        updateStateAsError(th);
    }

    public void setState(@Nullable T t2) {
        updateState(t2);
    }
}
