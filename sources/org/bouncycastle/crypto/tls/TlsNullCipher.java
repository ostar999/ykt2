package org.bouncycastle.crypto.tls;

/* loaded from: classes9.dex */
public class TlsNullCipher implements TlsCipher {
    public byte[] copyData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.tls.TlsCipher
    public byte[] decodeCiphertext(short s2, byte[] bArr, int i2, int i3) {
        return copyData(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.tls.TlsCipher
    public byte[] encodePlaintext(short s2, byte[] bArr, int i2, int i3) {
        return copyData(bArr, i2, i3);
    }
}
