package org.bouncycastle.crypto;

/* loaded from: classes9.dex */
public class StreamBlockCipher implements StreamCipher {
    private BlockCipher cipher;
    private byte[] oneByte = new byte[1];

    public StreamBlockCipher(BlockCipher blockCipher) {
        if (blockCipher.getBlockSize() != 1) {
            throw new IllegalArgumentException("block cipher block size != 1.");
        }
        this.cipher = blockCipher;
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName();
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.cipher.init(z2, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws IllegalStateException, DataLengthException {
        if (i4 + i3 > bArr2.length) {
            throw new DataLengthException("output buffer too small in processBytes()");
        }
        for (int i5 = 0; i5 != i3; i5++) {
            this.cipher.processBlock(bArr, i2 + i5, bArr2, i4 + i5);
        }
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public void reset() {
        this.cipher.reset();
    }

    @Override // org.bouncycastle.crypto.StreamCipher
    public byte returnByte(byte b3) throws IllegalStateException, DataLengthException {
        byte[] bArr = this.oneByte;
        bArr[0] = b3;
        this.cipher.processBlock(bArr, 0, bArr, 0);
        return this.oneByte[0];
    }
}
