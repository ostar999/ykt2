package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* loaded from: classes4.dex */
abstract class AI01weightDecoder extends AI01decoder {
    public AI01weightDecoder(BitArray bitArray) {
        super(bitArray);
    }

    public abstract void addWeightCode(StringBuilder sb, int i2);

    public abstract int checkWeight(int i2);

    public final void encodeCompressedWeight(StringBuilder sb, int i2, int i3) {
        int iExtractNumericValueFromBitArray = getGeneralDecoder().extractNumericValueFromBitArray(i2, i3);
        addWeightCode(sb, iExtractNumericValueFromBitArray);
        int iCheckWeight = checkWeight(iExtractNumericValueFromBitArray);
        int i4 = 100000;
        for (int i5 = 0; i5 < 5; i5++) {
            if (iCheckWeight / i4 == 0) {
                sb.append('0');
            }
            i4 /= 10;
        }
        sb.append(iCheckWeight);
    }
}
