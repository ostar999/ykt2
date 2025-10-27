package org.bouncycastle.crypto.engines;

/* loaded from: classes9.dex */
public class VMPCKSA3Engine extends VMPCEngine {
    @Override // org.bouncycastle.crypto.engines.VMPCEngine, org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return "VMPC-KSA3";
    }

    @Override // org.bouncycastle.crypto.engines.VMPCEngine
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
        for (int i7 = 0; i7 < 768; i7++) {
            byte[] bArr5 = this.P;
            byte b9 = this.f27842s;
            int i8 = i7 & 255;
            byte b10 = bArr5[i8];
            byte b11 = bArr5[(b9 + b10 + bArr[i7 % bArr.length]) & 255];
            this.f27842s = b11;
            bArr5[i8] = bArr5[b11 & 255];
            bArr5[b11 & 255] = b10;
        }
        this.f27841n = (byte) 0;
    }
}
