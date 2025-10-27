package org.bouncycastle.openpgp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import org.bouncycastle.bcpg.InputStreamPacket;
import org.bouncycastle.bcpg.SymmetricEncIntegrityPacket;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public abstract class PGPEncryptedData implements SymmetricKeyAlgorithmTags {
    InputStreamPacket encData;
    InputStream encStream;
    TruncatedStream truncStream;

    public class TruncatedStream extends InputStream {
        int bufPtr;
        InputStream in;
        int[] lookAhead = new int[22];

        public TruncatedStream(InputStream inputStream) throws IOException {
            int i2 = 0;
            while (true) {
                int[] iArr = this.lookAhead;
                if (i2 == iArr.length) {
                    this.bufPtr = 0;
                    this.in = inputStream;
                    return;
                } else {
                    int i3 = inputStream.read();
                    iArr[i2] = i3;
                    if (i3 < 0) {
                        throw new EOFException();
                    }
                    i2++;
                }
            }
        }

        public int[] getLookAhead() {
            int[] iArr = new int[this.lookAhead.length];
            int i2 = this.bufPtr;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int[] iArr2 = this.lookAhead;
                if (i2 == iArr2.length) {
                    break;
                }
                iArr[i4] = iArr2[i2];
                i2++;
                i4++;
            }
            while (i3 != this.bufPtr) {
                iArr[i4] = this.lookAhead[i3];
                i3++;
                i4++;
            }
            return iArr;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int i2 = this.in.read();
            if (i2 < 0) {
                return -1;
            }
            int[] iArr = this.lookAhead;
            int i3 = this.bufPtr;
            int i4 = iArr[i3];
            iArr[i3] = i2;
            this.bufPtr = (i3 + 1) % iArr.length;
            return i4;
        }
    }

    public PGPEncryptedData(InputStreamPacket inputStreamPacket) {
        this.encData = inputStreamPacket;
    }

    public InputStream getInputStream() {
        return this.encData.getInputStream();
    }

    public boolean isIntegrityProtected() {
        return this.encData instanceof SymmetricEncIntegrityPacket;
    }

    public boolean verify() throws IOException, PGPException {
        if (!isIntegrityProtected()) {
            throw new PGPException("data not integrity protected.");
        }
        DigestInputStream digestInputStream = (DigestInputStream) this.encStream;
        while (this.encStream.read() >= 0) {
        }
        MessageDigest messageDigest = digestInputStream.getMessageDigest();
        int[] lookAhead = this.truncStream.getLookAhead();
        messageDigest.update((byte) lookAhead[0]);
        messageDigest.update((byte) lookAhead[1]);
        byte[] bArrDigest = messageDigest.digest();
        int length = bArrDigest.length;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr[i2] = (byte) lookAhead[i2 + 2];
        }
        return Arrays.constantTimeAreEqual(bArrDigest, bArr);
    }
}
