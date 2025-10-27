package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;

/* loaded from: classes4.dex */
final class UPCEANExtensionSupport {
    private static final int[] EXTENSION_START_PATTERN = {1, 1, 2};
    private final UPCEANExtension2Support twoSupport = new UPCEANExtension2Support();
    private final UPCEANExtension5Support fiveSupport = new UPCEANExtension5Support();

    public Result decodeRow(int i2, BitArray bitArray, int i3) throws NotFoundException {
        int[] iArrFindGuardPattern = UPCEANReader.findGuardPattern(bitArray, i3, false, EXTENSION_START_PATTERN);
        try {
            return this.fiveSupport.decodeRow(i2, bitArray, iArrFindGuardPattern);
        } catch (ReaderException unused) {
            return this.twoSupport.decodeRow(i2, bitArray, iArrFindGuardPattern);
        }
    }
}
