package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class OpenSSLPBEParametersGenerator extends PBEParametersGenerator {
    private Digest digest = new MD5Digest();

    private byte[] generateDerivedKey(int i2) {
        int digestSize = this.digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        byte[] bArr2 = new byte[i2];
        int i3 = 0;
        while (true) {
            Digest digest = this.digest;
            byte[] bArr3 = this.password;
            digest.update(bArr3, 0, bArr3.length);
            Digest digest2 = this.digest;
            byte[] bArr4 = this.salt;
            digest2.update(bArr4, 0, bArr4.length);
            this.digest.doFinal(bArr, 0);
            int i4 = i2 > digestSize ? digestSize : i2;
            System.arraycopy(bArr, 0, bArr2, i3, i4);
            i3 += i4;
            i2 -= i4;
            if (i2 == 0) {
                return bArr2;
            }
            this.digest.reset();
            this.digest.update(bArr, 0, digestSize);
        }
    }

    @Override // org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedMacParameters(int i2) {
        return generateDerivedParameters(i2);
    }

    @Override // org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int i2) {
        int i3 = i2 / 8;
        return new KeyParameter(generateDerivedKey(i3), 0, i3);
    }

    @Override // org.bouncycastle.crypto.PBEParametersGenerator
    public CipherParameters generateDerivedParameters(int i2, int i3) {
        int i4 = i2 / 8;
        int i5 = i3 / 8;
        byte[] bArrGenerateDerivedKey = generateDerivedKey(i4 + i5);
        return new ParametersWithIV(new KeyParameter(bArrGenerateDerivedKey, 0, i4), bArrGenerateDerivedKey, i4, i5);
    }

    public void init(byte[] bArr, byte[] bArr2) {
        super.init(bArr, bArr2, 1);
    }
}
