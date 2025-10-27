package io.reactivex.rxjava3.core;

import cn.hutool.core.text.StrPool;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.internal.util.NotificationLite;
import java.util.Objects;

/* loaded from: classes8.dex */
public final class Notification<T> {
    static final Notification<Object> COMPLETE = new Notification<>(null);
    final Object value;

    private Notification(@Nullable Object value) {
        this.value = value;
    }

    @NonNull
    public static <T> Notification<T> createOnComplete() {
        return (Notification<T>) COMPLETE;
    }

    @NonNull
    public static <T> Notification<T> createOnError(@NonNull Throwable error) {
        Objects.requireNonNull(error, "error is null");
        return new Notification<>(NotificationLite.error(error));
    }

    @NonNull
    public static <T> Notification<T> createOnNext(T value) {
        Objects.requireNonNull(value, "value is null");
        return new Notification<>(value);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Notification) {
            return Objects.equals(this.value, ((Notification) obj).value);
        }
        return false;
    }

    @Nullable
    public Throwable getError() {
        Object obj = this.value;
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    @Nullable
    public T getValue() {
        Object obj = this.value;
        if (obj == null || NotificationLite.isError(obj)) {
            return null;
        }
        return (T) this.value;
    }

    public int hashCode() {
        Object obj = this.value;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public boolean isOnComplete() {
        return this.value == null;
    }

    public boolean isOnError() {
        return NotificationLite.isError(this.value);
    }

    public boolean isOnNext() {
        Object obj = this.value;
        return (obj == null || NotificationLite.isError(obj)) ? false : true;
    }

    public String toString() {
        Object obj = this.value;
        if (obj == null) {
            return "OnCompleteNotification";
        }
        if (NotificationLite.isError(obj)) {
            return "OnErrorNotification[" + NotificationLite.getError(obj) + StrPool.BRACKET_END;
        }
        return "OnNextNotification[" + this.value + StrPool.BRACKET_END;
    }
}
