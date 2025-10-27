package io.reactivex.internal.fuseable;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/* loaded from: classes8.dex */
public interface SimpleQueue<T> {
    void clear();

    boolean isEmpty();

    boolean offer(@NonNull T t2);

    boolean offer(@NonNull T t2, @NonNull T t3);

    @Nullable
    T poll() throws Exception;
}
