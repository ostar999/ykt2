package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

/* loaded from: classes9.dex */
public interface AEADBlockCipher {
    int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException;

    String getAlgorithmName();

    byte[] getMac();

    int getOutputSize(int i2);

    BlockCipher getUnderlyingCipher();

    int getUpdateOutputSize(int i2);

    void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException;

    int processByte(byte b3, byte[] bArr, int i2) throws DataLengthException;

    int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException;

    void reset();
}
