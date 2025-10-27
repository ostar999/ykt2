package org.bouncycastle.bcpg;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.bcpg.sig.Exportable;
import org.bouncycastle.bcpg.sig.IssuerKeyID;
import org.bouncycastle.bcpg.sig.KeyExpirationTime;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.bcpg.sig.NotationData;
import org.bouncycastle.bcpg.sig.PreferredAlgorithms;
import org.bouncycastle.bcpg.sig.PrimaryUserID;
import org.bouncycastle.bcpg.sig.Revocable;
import org.bouncycastle.bcpg.sig.SignatureCreationTime;
import org.bouncycastle.bcpg.sig.SignatureExpirationTime;
import org.bouncycastle.bcpg.sig.SignerUserID;
import org.bouncycastle.bcpg.sig.TrustSignature;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes9.dex */
public class SignatureSubpacketInputStream extends InputStream implements SignatureSubpacketTags {
    InputStream in;

    public SignatureSubpacketInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.in.read();
    }

    public SignatureSubpacket readPacket() throws IOException {
        int i2 = read();
        if (i2 < 0) {
            return null;
        }
        if (i2 >= 192) {
            i2 = i2 <= 223 ? ((i2 - 192) << 8) + this.in.read() + 192 : i2 == 255 ? (this.in.read() << 24) | (this.in.read() << 16) | (this.in.read() << 8) | this.in.read() : 0;
        }
        int i3 = this.in.read();
        if (i3 < 0) {
            throw new EOFException("unexpected EOF reading signature sub packet");
        }
        int i4 = i2 - 1;
        byte[] bArr = new byte[i4];
        if (Streams.readFully(this.in, bArr) < i4) {
            throw new EOFException();
        }
        boolean z2 = (i3 & 128) != 0;
        int i5 = i3 & 127;
        if (i5 == 2) {
            return new SignatureCreationTime(z2, bArr);
        }
        if (i5 == 3) {
            return new SignatureExpirationTime(z2, bArr);
        }
        if (i5 == 4) {
            return new Exportable(z2, bArr);
        }
        if (i5 == 5) {
            return new TrustSignature(z2, bArr);
        }
        if (i5 == 7) {
            return new Revocable(z2, bArr);
        }
        if (i5 == 9) {
            return new KeyExpirationTime(z2, bArr);
        }
        if (i5 != 11) {
            if (i5 == 16) {
                return new IssuerKeyID(z2, bArr);
            }
            if (i5 == 25) {
                return new PrimaryUserID(z2, bArr);
            }
            if (i5 == 27) {
                return new KeyFlags(z2, bArr);
            }
            if (i5 == 28) {
                return new SignerUserID(z2, bArr);
            }
            switch (i5) {
                case 20:
                    return new NotationData(z2, bArr);
                case 21:
                case 22:
                    break;
                default:
                    return new SignatureSubpacket(i5, z2, bArr);
            }
        }
        return new PreferredAlgorithms(i5, z2, bArr);
    }
}
