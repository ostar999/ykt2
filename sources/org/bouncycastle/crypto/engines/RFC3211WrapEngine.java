package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;

/* loaded from: classes9.dex */
public class RFC3211WrapEngine implements Wrapper {
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private ParametersWithIV param;
    private SecureRandom rand;

    public RFC3211WrapEngine(BlockCipher blockCipher) {
        this.engine = new CBCBlockCipher(blockCipher);
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public String getAlgorithmName() {
        return this.engine.getUnderlyingCipher().getAlgorithmName() + "/RFC3211Wrap";
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forWrapping = z2;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.rand = parametersWithRandom.getRandom();
            this.param = (ParametersWithIV) parametersWithRandom.getParameters();
        } else {
            if (z2) {
                this.rand = new SecureRandom();
            }
            this.param = (ParametersWithIV) cipherParameters;
        }
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i2, int i3) throws InvalidCipherTextException, IllegalStateException, DataLengthException, IllegalArgumentException {
        if (this.forWrapping) {
            throw new IllegalStateException("not set for unwrapping");
        }
        int blockSize = this.engine.getBlockSize();
        if (i3 < blockSize * 2) {
            throw new InvalidCipherTextException("input too short");
        }
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[blockSize];
        int i4 = 0;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        System.arraycopy(bArr, i2, bArr3, 0, blockSize);
        this.engine.init(false, new ParametersWithIV(this.param.getParameters(), bArr3));
        for (int i5 = blockSize; i5 < i3; i5 += blockSize) {
            this.engine.processBlock(bArr2, i5, bArr2, i5);
        }
        System.arraycopy(bArr2, i3 - blockSize, bArr3, 0, blockSize);
        this.engine.init(false, new ParametersWithIV(this.param.getParameters(), bArr3));
        this.engine.processBlock(bArr2, 0, bArr2, 0);
        this.engine.init(false, this.param);
        for (int i6 = 0; i6 < i3; i6 += blockSize) {
            this.engine.processBlock(bArr2, i6, bArr2, i6);
        }
        int i7 = bArr2[0];
        if ((i7 & 255) > i3 - 4) {
            throw new InvalidCipherTextException("wrapped key corrupted");
        }
        byte[] bArr4 = new byte[i7 & 255];
        System.arraycopy(bArr2, 4, bArr4, 0, i7);
        int i8 = 0;
        while (i4 != 3) {
            int i9 = i4 + 1;
            i8 |= ((byte) (~bArr2[i9])) ^ bArr4[i4];
            i4 = i9;
        }
        if (i8 == 0) {
            return bArr4;
        }
        throw new InvalidCipherTextException("wrapped key fails checksum");
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        if (!this.forWrapping) {
            throw new IllegalStateException("not set for wrapping");
        }
        this.engine.init(true, this.param);
        int blockSize = this.engine.getBlockSize();
        int i4 = i3 + 4;
        int i5 = blockSize * 2;
        if (i4 >= i5) {
            i5 = i4 % blockSize == 0 ? i4 : ((i4 / blockSize) + 1) * blockSize;
        }
        byte[] bArr2 = new byte[i5];
        bArr2[0] = (byte) i3;
        bArr2[1] = (byte) (~bArr[i2]);
        bArr2[2] = (byte) (~bArr[i2 + 1]);
        bArr2[3] = (byte) (~bArr[i2 + 2]);
        System.arraycopy(bArr, i2, bArr2, 4, i3);
        while (i4 < bArr2.length) {
            bArr2[i4] = (byte) this.rand.nextInt();
            i4++;
        }
        for (int i6 = 0; i6 < bArr2.length; i6 += blockSize) {
            this.engine.processBlock(bArr2, i6, bArr2, i6);
        }
        for (int i7 = 0; i7 < bArr2.length; i7 += blockSize) {
            this.engine.processBlock(bArr2, i7, bArr2, i7);
        }
        return bArr2;
    }
}
