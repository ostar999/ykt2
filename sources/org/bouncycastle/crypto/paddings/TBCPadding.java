package org.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.InvalidCipherTextException;

/* loaded from: classes9.dex */
public class TBCPadding implements BlockCipherPadding {
    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i2) {
        int length = bArr.length - i2;
        int i3 = 255;
        if (i2 <= 0 ? (bArr[bArr.length - 1] & 1) != 0 : (bArr[i2 - 1] & 1) != 0) {
            i3 = 0;
        }
        byte b3 = (byte) i3;
        while (i2 < bArr.length) {
            bArr[i2] = b3;
            i2++;
        }
        return length;
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "TBC";
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) throws InvalidCipherTextException {
        byte b3 = bArr[bArr.length - 1];
        int length = bArr.length - 1;
        while (length > 0 && bArr[length - 1] == b3) {
            length--;
        }
        return bArr.length - length;
    }
}
