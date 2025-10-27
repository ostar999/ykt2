package org.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/* loaded from: classes9.dex */
public class TlsMac {
    protected HMac mac;
    protected long seqNo;

    public TlsMac(Digest digest, byte[] bArr, int i2, int i3) {
        this.mac = new HMac(digest);
        this.mac.init(new KeyParameter(bArr, i2, i3));
        this.seqNo = 0L;
    }

    public byte[] calculateMac(short s2, byte[] bArr, int i2, int i3) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(13);
        try {
            long j2 = this.seqNo;
            this.seqNo = 1 + j2;
            TlsUtils.writeUint64(j2, byteArrayOutputStream);
            TlsUtils.writeUint8(s2, byteArrayOutputStream);
            TlsUtils.writeVersion(byteArrayOutputStream);
            TlsUtils.writeUint16(i3, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.mac.update(byteArray, 0, byteArray.length);
            this.mac.update(bArr, i2, i3);
            byte[] bArr2 = new byte[this.mac.getMacSize()];
            this.mac.doFinal(bArr2, 0);
            return bArr2;
        } catch (IOException unused) {
            throw new IllegalStateException("Internal error during mac calculation");
        }
    }

    public int getSize() {
        return this.mac.getMacSize();
    }
}
