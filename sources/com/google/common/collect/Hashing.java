package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
final class Hashing {
    private static final long C1 = -862048943;
    private static final long C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 1073741824;

    private Hashing() {
    }

    public static int closedTableSize(int i2, double d3) {
        int iMax = Math.max(i2, 2);
        int iHighestOneBit = Integer.highestOneBit(iMax);
        if (iMax <= ((int) (d3 * iHighestOneBit))) {
            return iHighestOneBit;
        }
        int i3 = iHighestOneBit << 1;
        if (i3 > 0) {
            return i3;
        }
        return 1073741824;
    }

    public static boolean needsResizing(int i2, int i3, double d3) {
        return ((double) i2) > d3 * ((double) i3) && i3 < 1073741824;
    }

    public static int smear(int i2) {
        return (int) (Integer.rotateLeft((int) (i2 * C1), 15) * C2);
    }

    public static int smearedHash(@NullableDecl Object obj) {
        return smear(obj == null ? 0 : obj.hashCode());
    }
}
