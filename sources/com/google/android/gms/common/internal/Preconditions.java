package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* loaded from: classes3.dex */
public final class Preconditions {
    private Preconditions() {
        throw new AssertionError("Uninstantiable");
    }

    @KeepForSdk
    public static void checkArgument(boolean z2, Object obj) {
        if (!z2) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    @KeepForSdk
    public static void checkHandlerThread(Handler handler) {
        checkHandlerThread(handler, "Must be called on the handler thread");
    }

    @KeepForSdk
    public static void checkMainThread(String str) {
        if (!com.google.android.gms.common.util.zzc.isMainThread()) {
            throw new IllegalStateException(str);
        }
    }

    @KeepForSdk
    public static String checkNotEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        return str;
    }

    @KeepForSdk
    public static void checkNotMainThread() {
        checkNotMainThread("Must not be called on the main application thread");
    }

    @NonNull
    @KeepForSdk
    public static <T> T checkNotNull(@Nullable T t2) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException("null reference");
    }

    @KeepForSdk
    public static int checkNotZero(int i2, Object obj) {
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    @KeepForSdk
    public static void checkState(boolean z2) {
        if (!z2) {
            throw new IllegalStateException();
        }
    }

    @KeepForSdk
    public static void checkArgument(boolean z2, String str, Object... objArr) {
        if (!z2) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    @KeepForSdk
    public static void checkHandlerThread(Handler handler, String str) {
        if (Looper.myLooper() != handler.getLooper()) {
            throw new IllegalStateException(str);
        }
    }

    @KeepForSdk
    public static void checkNotMainThread(String str) {
        if (com.google.android.gms.common.util.zzc.isMainThread()) {
            throw new IllegalStateException(str);
        }
    }

    @NonNull
    @KeepForSdk
    public static <T> T checkNotNull(T t2, Object obj) {
        if (t2 != null) {
            return t2;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    @KeepForSdk
    public static int checkNotZero(int i2) {
        if (i2 != 0) {
            return i2;
        }
        throw new IllegalArgumentException("Given Integer is zero");
    }

    @KeepForSdk
    public static void checkState(boolean z2, Object obj) {
        if (!z2) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    @KeepForSdk
    public static void checkArgument(boolean z2) {
        if (!z2) {
            throw new IllegalArgumentException();
        }
    }

    @KeepForSdk
    public static String checkNotEmpty(String str, Object obj) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
        return str;
    }

    @KeepForSdk
    public static long checkNotZero(long j2, Object obj) {
        if (j2 != 0) {
            return j2;
        }
        throw new IllegalArgumentException(String.valueOf(obj));
    }

    @KeepForSdk
    public static void checkState(boolean z2, String str, Object... objArr) {
        if (!z2) {
            throw new IllegalStateException(String.format(str, objArr));
        }
    }

    @KeepForSdk
    public static long checkNotZero(long j2) {
        if (j2 != 0) {
            return j2;
        }
        throw new IllegalArgumentException("Given Long is zero");
    }
}
