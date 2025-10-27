package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class VMPCEngine implements StreamCipher {
    protected byte[] workingIV;
    protected byte[] workingKey;

    /* renamed from: n, reason: collision with root package name */
    protected byte f27841n = 0;
    protected byte[] P = null;

    /* renamed from: s, reason: collision with root package name */
    protected byte f27842s = 0;

    @Override // org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "VMPC";
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("VMPC init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC init parameters must include a key");
        }
        byte[] iv = parametersWithIV.getIV();
        this.workingIV = iv;
        if (iv == null || iv.length < 1 || iv.length > 768) {
            throw new IllegalArgumentException("VMPC requires 1 to 768 bytes of IV");
        }
        byte[] key = keyParameter.getKey();
        this.workingKey = key;
        initKey(key, this.workingIV);
    }

    public void initKey(byte[] bArr, byte[] bArr2) {
        this.f27842s = (byte) 0;
        this.P = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            this.P[i2] = (byte) i2;
        }
        for (int i3 = 0; i3 < 768; i3++) {
            byte[] bArr3 = this.P;
            byte b3 = this.f27842s;
            int i4 = i3 & 255;
            byte b4 = bArr3[i4];
            byte b5 = bArr3[(b3 + b4 + bArr[i3 % bArr.length]) & 255];
            this.f27842s = b5;
            bArr3[i4] = bArr3[b5 & 255];
            bArr3[b5 & 255] = b4;
        }
        for (int i5 = 0; i5 < 768; i5++) {
            byte[] bArr4 = this.P;
            byte b6 = this.f27842s;
            int i6 = i5 & 255;
            byte b7 = bArr4[i6];
            byte b8 = bArr4[(b6 + b7 + bArr2[i5 % bArr2.length]) & 255];
            this.f27842s = b8;
            bArr4[i6] = bArr4[b8 & 255];
            bArr4[b8 & 255] = b7;
        }
        this.f27841n = (byte) 0;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i4 + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        }
        for (int i5 = 0; i5 < i3; i5++) {
            byte[] bArr3 = this.P;
            byte b3 = this.f27842s;
            byte b4 = this.f27841n;
            byte b5 = bArr3[(b3 + bArr3[b4 & 255]) & 255];
            this.f27842s = b5;
            byte b6 = bArr3[(bArr3[bArr3[b5 & 255] & 255] + 1) & 255];
            byte b7 = bArr3[b4 & 255];
            bArr3[b4 & 255] = bArr3[b5 & 255];
            bArr3[b5 & 255] = b7;
            this.f27841n = (byte) ((b4 + 1) & 255);
            bArr2[i5 + i4] = (byte) (bArr[i5 + i2] ^ b6);
        }
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void reset() {
        initKey(this.workingKey, this.workingIV);
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b3) {
        byte[] bArr = this.P;
        byte b4 = this.f27842s;
        byte b5 = this.f27841n;
        byte b6 = bArr[(b4 + bArr[b5 & 255]) & 255];
        this.f27842s = b6;
        byte b7 = bArr[(bArr[bArr[b6 & 255] & 255] + 1) & 255];
        byte b8 = bArr[b5 & 255];
        bArr[b5 & 255] = bArr[b6 & 255];
        bArr[b6 & 255] = b8;
        this.f27841n = (byte) ((b5 + 1) & 255);
        return (byte) (b3 ^ b7);
    }
}
