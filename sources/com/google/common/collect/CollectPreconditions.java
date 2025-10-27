package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
/* loaded from: classes4.dex */
final class CollectPreconditions {
    public static void checkEntryNotNull(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("null key in entry: null=" + obj2);
        }
        if (obj2 != null) {
            return;
        }
        throw new NullPointerException("null value in entry: " + obj + "=null");
    }

    @CanIgnoreReturnValue
    public static int checkNonnegative(int i2, String str) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " cannot be negative but was: " + i2);
    }

    public static void checkPositive(int i2, String str) {
        if (i2 > 0) {
            return;
        }
        throw new IllegalArgumentException(str + " must be positive but was: " + i2);
    }

    public static void checkRemove(boolean z2) {
        Preconditions.checkState(z2, "no calls to next() since the last call to remove()");
    }

    @CanIgnoreReturnValue
    public static long checkNonnegative(long j2, String str) {
        if (j2 >= 0) {
            return j2;
        }
        throw new IllegalArgumentException(str + " cannot be negative but was: " + j2);
    }
}
