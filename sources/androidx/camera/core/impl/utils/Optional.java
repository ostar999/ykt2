package androidx.camera.core.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import androidx.core.util.Supplier;
import java.io.Serializable;

@RequiresApi(21)
/* loaded from: classes.dex */
public abstract class Optional<T> implements Serializable {
    private static final long serialVersionUID = 0;

    @NonNull
    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    @NonNull
    public static <T> Optional<T> fromNullable(@Nullable T t2) {
        return t2 == null ? absent() : new Present(t2);
    }

    @NonNull
    public static <T> Optional<T> of(@NonNull T t2) {
        return new Present(Preconditions.checkNotNull(t2));
    }

    public abstract boolean equals(@Nullable Object obj);

    @NonNull
    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    @NonNull
    public abstract Optional<T> or(@NonNull Optional<? extends T> optional);

    @NonNull
    public abstract T or(@NonNull Supplier<? extends T> supplier);

    @NonNull
    public abstract T or(@NonNull T t2);

    @Nullable
    public abstract T orNull();

    @NonNull
    public abstract String toString();
}
