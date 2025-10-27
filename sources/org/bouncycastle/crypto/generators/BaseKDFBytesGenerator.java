package org.bouncycastle.crypto.generators;

import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.ISO18033KDFParameters;
import org.bouncycastle.crypto.params.KDFParameters;

/* loaded from: classes9.dex */
public class BaseKDFBytesGenerator implements DerivationFunction {
    private int counterStart;
    private Digest digest;
    private byte[] iv;
    private byte[] shared;

    public BaseKDFBytesGenerator(int i2, Digest digest) {
        this.counterStart = i2;
        this.digest = digest;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) throws DataLengthException, IllegalArgumentException {
        if (bArr.length - i3 < i2) {
            throw new DataLengthException("output buffer too small");
        }
        long j2 = i3;
        int digestSize = this.digest.getDigestSize();
        if (j2 > TarConstants.MAXSIZE) {
            throw new IllegalArgumentException("Output length too large");
        }
        long j3 = digestSize;
        int i4 = (int) (((j2 + j3) - 1) / j3);
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        int i5 = this.counterStart;
        for (int i6 = 0; i6 < i4; i6++) {
            Digest digest = this.digest;
            byte[] bArr3 = this.shared;
            digest.update(bArr3, 0, bArr3.length);
            this.digest.update((byte) (i5 >> 24));
            this.digest.update((byte) (i5 >> 16));
            this.digest.update((byte) (i5 >> 8));
            this.digest.update((byte) i5);
            byte[] bArr4 = this.iv;
            if (bArr4 != null) {
                this.digest.update(bArr4, 0, bArr4.length);
            }
            this.digest.doFinal(bArr2, 0);
            if (i3 > digestSize) {
                System.arraycopy(bArr2, 0, bArr, i2, digestSize);
                i2 += digestSize;
                i3 -= digestSize;
            } else {
                System.arraycopy(bArr2, 0, bArr, i2, i3);
            }
            i5++;
        }
        this.digest.reset();
        return i3;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public Digest getDigest() {
        return this.digest;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public void init(DerivationParameters derivationParameters) {
        if (derivationParameters instanceof KDFParameters) {
            KDFParameters kDFParameters = (KDFParameters) derivationParameters;
            this.shared = kDFParameters.getSharedSecret();
            this.iv = kDFParameters.getIV();
        } else {
            if (!(derivationParameters instanceof ISO18033KDFParameters)) {
                throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
            }
            this.shared = ((ISO18033KDFParameters) derivationParameters).getSeed();
            this.iv = null;
        }
    }
}
