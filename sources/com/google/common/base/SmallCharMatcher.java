package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import java.util.BitSet;

@GwtIncompatible
/* loaded from: classes3.dex */
final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5d;
    static final int MAX_SIZE = 1023;
    private final boolean containsZero;
    private final long filter;
    private final char[] table;

    private SmallCharMatcher(char[] cArr, long j2, boolean z2, String str) {
        super(str);
        this.table = cArr;
        this.filter = j2;
        this.containsZero = z2;
    }

    private boolean checkFilter(int i2) {
        return 1 == ((this.filter >> i2) & 1);
    }

    @VisibleForTesting
    public static int chooseTableSize(int i2) {
        if (i2 == 1) {
            return 2;
        }
        int iHighestOneBit = Integer.highestOneBit(i2 - 1) << 1;
        while (iHighestOneBit * DESIRED_LOAD_FACTOR < i2) {
            iHighestOneBit <<= 1;
        }
        return iHighestOneBit;
    }

    public static CharMatcher from(BitSet bitSet, String str) {
        int i2;
        int iCardinality = bitSet.cardinality();
        boolean z2 = bitSet.get(0);
        int iChooseTableSize = chooseTableSize(iCardinality);
        char[] cArr = new char[iChooseTableSize];
        int i3 = iChooseTableSize - 1;
        int iNextSetBit = bitSet.nextSetBit(0);
        long j2 = 0;
        while (iNextSetBit != -1) {
            long j3 = (1 << iNextSetBit) | j2;
            int iSmear = smear(iNextSetBit);
            while (true) {
                i2 = iSmear & i3;
                if (cArr[i2] == 0) {
                    break;
                }
                iSmear = i2 + 1;
            }
            cArr[i2] = (char) iNextSetBit;
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            j2 = j3;
        }
        return new SmallCharMatcher(cArr, j2, z2, str);
    }

    public static int smear(int i2) {
        return Integer.rotateLeft(i2 * C1, 15) * C2;
    }

    @Override // com.google.common.base.CharMatcher
    public boolean matches(char c3) {
        if (c3 == 0) {
            return this.containsZero;
        }
        if (!checkFilter(c3)) {
            return false;
        }
        int length = this.table.length - 1;
        int iSmear = smear(c3) & length;
        int i2 = iSmear;
        do {
            char c4 = this.table[i2];
            if (c4 == 0) {
                return false;
            }
            if (c4 == c3) {
                return true;
            }
            i2 = (i2 + 1) & length;
        } while (i2 != iSmear);
        return false;
    }

    @Override // com.google.common.base.CharMatcher
    public void setBits(BitSet bitSet) {
        if (this.containsZero) {
            bitSet.set(0);
        }
        for (char c3 : this.table) {
            if (c3 != 0) {
                bitSet.set(c3);
            }
        }
    }
}
