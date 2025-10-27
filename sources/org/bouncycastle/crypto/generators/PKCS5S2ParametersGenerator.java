package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class PKCS5S2ParametersGenerator extends PBEParametersGenerator {
    private Mac hMac = new HMac(new SHA1Digest());

    private void F(byte[] bArr, byte[] bArr2, int i2, byte[] bArr3, byte[] bArr4, int i3) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        int macSize = this.hMac.getMacSize();
        byte[] bArr5 = new byte[macSize];
        KeyParameter keyParameter = new KeyParameter(bArr);
        this.hMac.init(keyParameter);
        if (bArr2 != null) {
            this.hMac.update(bArr2, 0, bArr2.length);
        }
        this.hMac.update(bArr3, 0, bArr3.length);
        this.hMac.doFinal(bArr5, 0);
        System.arraycopy(bArr5, 0, bArr4, i3, macSize);
        if (i2 == 0) {
            throw new IllegalArgumentException("iteration count must be at least 1.");
        }
        for (int i4 = 1; i4 < i2; i4++) {
            this.hMac.init(keyParameter);
            this.hMac.update(bArr5, 0, macSize);
            this.hMac.doFinal(bArr5, 0);
            for (int i5 = 0; i5 != macSize; i5++) {
                int i6 = i3 + i5;
                bArr4[i6] = (byte) (bArr4[i6] ^ bArr5[i5]);
            }
        }
    }

    private byte[] generateDerivedKey(int i2) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        int macSize = this.hMac.getMacSize();
        int i3 = ((i2 + macSize) - 1) / macSize;
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[i3 * macSize];
        for (int i4 = 1; i4 <= i3; i4++) {
            intToOctet(bArr, i4);
            F(this.password, this.salt, this.iterationCount, bArr, bArr2, (i4 - 1) * macSize);
        }
        return bArr2;
    }

    private void intToOctet(byte[] bArr, int i2) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) i2;
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
    public CipherParameters generateDerivedParameters(int i2, int i3) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        int i4 = i2 / 8;
        int i5 = i3 / 8;
        byte[] bArrGenerateDerivedKey = generateDerivedKey(i4 + i5);
        return new ParametersWithIV(new KeyParameter(bArrGenerateDerivedKey, 0, i4), bArrGenerateDerivedKey, i4, i5);
    }
}
