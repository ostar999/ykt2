package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;

/* loaded from: classes9.dex */
public class NullEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 1;
    private boolean initialised;

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Null";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 1;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.initialised = true;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) throws IllegalStateException, DataLengthException {
        if (!this.initialised) {
            throw new IllegalStateException("Null engine not initialised");
        }
        if (i2 + 1 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 1 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        bArr2[i3 + 0] = bArr[i2 + 0];
        return 1;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
