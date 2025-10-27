package org.bouncycastle.crypto.macs;

import com.google.common.base.Ascii;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class VMPCMac implements Mac {
    private byte[] T;

    /* renamed from: g, reason: collision with root package name */
    private byte f27845g;
    private byte[] workingIV;
    private byte[] workingKey;

    /* renamed from: x1, reason: collision with root package name */
    private byte f27848x1;
    private byte x2;
    private byte x3;
    private byte x4;

    /* renamed from: n, reason: collision with root package name */
    private byte f27846n = 0;
    private byte[] P = null;

    /* renamed from: s, reason: collision with root package name */
    private byte f27847s = 0;

    private void initKey(byte[] bArr, byte[] bArr2) {
        this.f27847s = (byte) 0;
        this.P = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            this.P[i2] = (byte) i2;
        }
        for (int i3 = 0; i3 < 768; i3++) {
            byte[] bArr3 = this.P;
            byte b3 = this.f27847s;
            int i4 = i3 & 255;
            byte b4 = bArr3[i4];
            byte b5 = bArr3[(b3 + b4 + bArr[i3 % bArr.length]) & 255];
            this.f27847s = b5;
            bArr3[i4] = bArr3[b5 & 255];
            bArr3[b5 & 255] = b4;
        }
        for (int i5 = 0; i5 < 768; i5++) {
            byte[] bArr4 = this.P;
            byte b6 = this.f27847s;
            int i6 = i5 & 255;
            byte b7 = bArr4[i6];
            byte b8 = bArr4[(b6 + b7 + bArr2[i5 % bArr2.length]) & 255];
            this.f27847s = b8;
            bArr4[i6] = bArr4[b8 & 255];
            bArr4[b8 & 255] = b7;
        }
        this.f27846n = (byte) 0;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) throws IllegalStateException, DataLengthException {
        for (int i3 = 1; i3 < 25; i3++) {
            byte[] bArr2 = this.P;
            byte b3 = this.f27847s;
            byte b4 = this.f27846n;
            byte b5 = bArr2[(b3 + bArr2[b4 & 255]) & 255];
            this.f27847s = b5;
            byte b6 = this.x4;
            byte b7 = this.x3;
            byte b8 = bArr2[(b6 + b7 + i3) & 255];
            this.x4 = b8;
            byte b9 = this.x2;
            byte b10 = bArr2[(b7 + b9 + i3) & 255];
            this.x3 = b10;
            byte b11 = this.f27848x1;
            byte b12 = bArr2[(b9 + b11 + i3) & 255];
            this.x2 = b12;
            byte b13 = bArr2[(b11 + b5 + i3) & 255];
            this.f27848x1 = b13;
            byte[] bArr3 = this.T;
            byte b14 = this.f27845g;
            bArr3[b14 & Ascii.US] = (byte) (b13 ^ bArr3[b14 & Ascii.US]);
            bArr3[(b14 + 1) & 31] = (byte) (b12 ^ bArr3[(b14 + 1) & 31]);
            bArr3[(b14 + 2) & 31] = (byte) (b10 ^ bArr3[(b14 + 2) & 31]);
            bArr3[(b14 + 3) & 31] = (byte) (b8 ^ bArr3[(b14 + 3) & 31]);
            this.f27845g = (byte) ((b14 + 4) & 31);
            byte b15 = bArr2[b4 & 255];
            bArr2[b4 & 255] = bArr2[b5 & 255];
            bArr2[b5 & 255] = b15;
            this.f27846n = (byte) ((b4 + 1) & 255);
        }
        for (int i4 = 0; i4 < 768; i4++) {
            byte[] bArr4 = this.P;
            byte b16 = this.f27847s;
            int i5 = i4 & 255;
            byte b17 = bArr4[i5];
            byte b18 = bArr4[(b16 + b17 + this.T[i4 & 31]) & 255];
            this.f27847s = b18;
            bArr4[i5] = bArr4[b18 & 255];
            bArr4[b18 & 255] = b17;
        }
        byte[] bArr5 = new byte[20];
        for (int i6 = 0; i6 < 20; i6++) {
            byte[] bArr6 = this.P;
            int i7 = i6 & 255;
            byte b19 = bArr6[(this.f27847s + bArr6[i7]) & 255];
            this.f27847s = b19;
            bArr5[i6] = bArr6[(bArr6[bArr6[b19 & 255] & 255] + 1) & 255];
            byte b20 = bArr6[i7];
            bArr6[i7] = bArr6[b19 & 255];
            bArr6[b19 & 255] = b20;
        }
        System.arraycopy(bArr5, 0, bArr, i2, 20);
        reset();
        return 20;
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return "VMPC-MAC";
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return 20;
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) throws IllegalArgumentException {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include an IV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        KeyParameter keyParameter = (KeyParameter) parametersWithIV.getParameters();
        if (!(parametersWithIV.getParameters() instanceof KeyParameter)) {
            throw new IllegalArgumentException("VMPC-MAC Init parameters must include a key");
        }
        byte[] iv = parametersWithIV.getIV();
        this.workingIV = iv;
        if (iv == null || iv.length < 1 || iv.length > 768) {
            throw new IllegalArgumentException("VMPC-MAC requires 1 to 768 bytes of IV");
        }
        this.workingKey = keyParameter.getKey();
        reset();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        initKey(this.workingKey, this.workingIV);
        this.f27846n = (byte) 0;
        this.x4 = (byte) 0;
        this.x3 = (byte) 0;
        this.x2 = (byte) 0;
        this.f27848x1 = (byte) 0;
        this.f27845g = (byte) 0;
        this.T = new byte[32];
        for (int i2 = 0; i2 < 32; i2++) {
            this.T[i2] = 0;
        }
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b3) throws IllegalStateException {
        byte[] bArr = this.P;
        byte b4 = this.f27847s;
        byte b5 = this.f27846n;
        byte b6 = bArr[(b4 + bArr[b5 & 255]) & 255];
        this.f27847s = b6;
        byte b7 = (byte) (b3 ^ bArr[(bArr[bArr[b6 & 255] & 255] + 1) & 255]);
        byte b8 = this.x4;
        byte b9 = this.x3;
        byte b10 = bArr[(b8 + b9) & 255];
        this.x4 = b10;
        byte b11 = this.x2;
        byte b12 = bArr[(b9 + b11) & 255];
        this.x3 = b12;
        byte b13 = this.f27848x1;
        byte b14 = bArr[(b11 + b13) & 255];
        this.x2 = b14;
        byte b15 = bArr[(b13 + b6 + b7) & 255];
        this.f27848x1 = b15;
        byte[] bArr2 = this.T;
        byte b16 = this.f27845g;
        bArr2[b16 & Ascii.US] = (byte) (b15 ^ bArr2[b16 & Ascii.US]);
        bArr2[(b16 + 1) & 31] = (byte) (b14 ^ bArr2[(b16 + 1) & 31]);
        bArr2[(b16 + 2) & 31] = (byte) (b12 ^ bArr2[(b16 + 2) & 31]);
        bArr2[(b16 + 3) & 31] = (byte) (b10 ^ bArr2[(b16 + 3) & 31]);
        this.f27845g = (byte) ((b16 + 4) & 31);
        byte b17 = bArr[b5 & 255];
        bArr[b5 & 255] = bArr[b6 & 255];
        bArr[b6 & 255] = b17;
        this.f27846n = (byte) ((b5 + 1) & 255);
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException {
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        for (int i4 = 0; i4 < i3; i4++) {
            update(bArr[i4]);
        }
    }
}
