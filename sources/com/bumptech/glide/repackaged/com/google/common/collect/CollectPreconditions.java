package com.bumptech.glide.repackaged.com.google.common.collect;

import com.bumptech.glide.repackaged.com.google.common.base.Preconditions;

/* loaded from: classes2.dex */
final class CollectPreconditions {
    public static int checkNonnegative(int i2, String str) {
        if (i2 >= 0) {
            return i2;
        }
        throw new IllegalArgumentException(str + " cannot be negative but was: " + i2);
    }

    public static void checkRemove(boolean z2) {
        Preconditions.checkState(z2, "no calls to next() since the last call to remove()");
    }
}
