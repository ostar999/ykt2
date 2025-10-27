package org.bouncycastle.crypto.tls;

import java.security.SecureRandom;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class TlsBlockCipher implements TlsCipher {
    protected TlsClientContext context;
    protected BlockCipher decryptCipher;
    protected BlockCipher encryptCipher;
    protected TlsMac readMac;
    protected TlsMac writeMac;

    public TlsBlockCipher(TlsClientContext tlsClientContext, BlockCipher blockCipher, BlockCipher blockCipher2, Digest digest, Digest digest2, int i2) throws IllegalArgumentException {
        this.context = tlsClientContext;
        this.encryptCipher = blockCipher;
        this.decryptCipher = blockCipher2;
        int i3 = i2 * 2;
        int digestSize = digest.getDigestSize() + i3 + digest2.getDigestSize() + blockCipher.getBlockSize() + blockCipher2.getBlockSize();
        SecurityParameters securityParameters = tlsClientContext.getSecurityParameters();
        byte[] bArrPRF = TlsUtils.PRF(securityParameters.masterSecret, "key expansion", TlsUtils.concat(securityParameters.serverRandom, securityParameters.clientRandom), digestSize);
        this.writeMac = new TlsMac(digest, bArrPRF, 0, digest.getDigestSize());
        int digestSize2 = digest.getDigestSize() + 0;
        this.readMac = new TlsMac(digest2, bArrPRF, digestSize2, digest2.getDigestSize());
        int digestSize3 = digestSize2 + digest2.getDigestSize();
        initCipher(true, blockCipher, bArrPRF, i2, digestSize3, digestSize3 + i3);
        int i4 = digestSize3 + i2;
        initCipher(false, blockCipher2, bArrPRF, i2, i4, i4 + i2 + blockCipher.getBlockSize());
    }

    public int chooseExtraPadBlocks(SecureRandom secureRandom, int i2) {
        return Math.min(lowestBitSet(secureRandom.nextInt()), i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002b  */
    @Override // org.bouncycastle.crypto.tls.TlsCipher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] decodeCiphertext(short r9, byte[] r10, int r11, int r12) throws java.lang.IllegalStateException, org.bouncycastle.crypto.DataLengthException, java.io.IOException {
        /*
            r8 = this;
            org.bouncycastle.crypto.tls.TlsMac r0 = r8.readMac
            int r0 = r0.getSize()
            r1 = 1
            int r0 = r0 + r1
            org.bouncycastle.crypto.BlockCipher r2 = r8.decryptCipher
            int r2 = r2.getBlockSize()
            if (r12 < r0) goto L6f
            int r3 = r12 % r2
            if (r3 != 0) goto L67
            r3 = 0
            r4 = r3
        L16:
            if (r4 >= r12) goto L21
            org.bouncycastle.crypto.BlockCipher r5 = r8.decryptCipher
            int r6 = r4 + r11
            r5.processBlock(r10, r6, r10, r6)
            int r4 = r4 + r2
            goto L16
        L21:
            int r2 = r11 + r12
            int r2 = r2 - r1
            r4 = r10[r2]
            r5 = r4 & 255(0xff, float:3.57E-43)
            int r12 = r12 - r0
            if (r5 <= r12) goto L2e
        L2b:
            r0 = r1
            r5 = r3
            goto L3f
        L2e:
            int r0 = r2 - r5
            r6 = r3
        L31:
            if (r0 >= r2) goto L3b
            r7 = r10[r0]
            r7 = r7 ^ r4
            r6 = r6 | r7
            byte r6 = (byte) r6
            int r0 = r0 + 1
            goto L31
        L3b:
            if (r6 == 0) goto L3e
            goto L2b
        L3e:
            r0 = r3
        L3f:
            int r12 = r12 - r5
            org.bouncycastle.crypto.tls.TlsMac r2 = r8.readMac
            byte[] r9 = r2.calculateMac(r9, r10, r11, r12)
            int r2 = r9.length
            byte[] r2 = new byte[r2]
            int r4 = r11 + r12
            int r5 = r9.length
            java.lang.System.arraycopy(r10, r4, r2, r3, r5)
            boolean r9 = org.bouncycastle.util.Arrays.constantTimeAreEqual(r9, r2)
            if (r9 != 0) goto L56
            goto L57
        L56:
            r1 = r0
        L57:
            if (r1 != 0) goto L5f
            byte[] r9 = new byte[r12]
            java.lang.System.arraycopy(r10, r11, r9, r3, r12)
            return r9
        L5f:
            org.bouncycastle.crypto.tls.TlsFatalAlert r9 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r10 = 20
            r9.<init>(r10)
            throw r9
        L67:
            org.bouncycastle.crypto.tls.TlsFatalAlert r9 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r10 = 21
            r9.<init>(r10)
            throw r9
        L6f:
            org.bouncycastle.crypto.tls.TlsFatalAlert r9 = new org.bouncycastle.crypto.tls.TlsFatalAlert
            r10 = 50
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.crypto.tls.TlsBlockCipher.decodeCiphertext(short, byte[], int, int):byte[]");
    }

    @Override // org.bouncycastle.crypto.tls.TlsCipher
    public byte[] encodePlaintext(short s2, byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException {
        int blockSize = this.encryptCipher.getBlockSize();
        int size = blockSize - (((this.writeMac.getSize() + i3) + 1) % blockSize);
        int iChooseExtraPadBlocks = size + (chooseExtraPadBlocks(this.context.getSecureRandom(), (255 - size) / blockSize) * blockSize);
        int size2 = this.writeMac.getSize() + i3 + iChooseExtraPadBlocks + 1;
        byte[] bArr2 = new byte[size2];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        byte[] bArrCalculateMac = this.writeMac.calculateMac(s2, bArr, i2, i3);
        System.arraycopy(bArrCalculateMac, 0, bArr2, i3, bArrCalculateMac.length);
        int length = i3 + bArrCalculateMac.length;
        for (int i4 = 0; i4 <= iChooseExtraPadBlocks; i4++) {
            bArr2[i4 + length] = (byte) iChooseExtraPadBlocks;
        }
        for (int i5 = 0; i5 < size2; i5 += blockSize) {
            this.encryptCipher.processBlock(bArr2, i5, bArr2, i5);
        }
        return bArr2;
    }

    public void initCipher(boolean z2, BlockCipher blockCipher, byte[] bArr, int i2, int i3, int i4) throws IllegalArgumentException {
        blockCipher.init(z2, new ParametersWithIV(new KeyParameter(bArr, i3, i2), bArr, i4, blockCipher.getBlockSize()));
    }

    public int lowestBitSet(int i2) {
        if (i2 == 0) {
            return 32;
        }
        int i3 = 0;
        while ((i2 & 1) == 0) {
            i3++;
            i2 >>= 1;
        }
        return i3;
    }
}
