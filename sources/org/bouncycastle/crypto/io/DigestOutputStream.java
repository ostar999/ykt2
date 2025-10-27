package org.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes9.dex */
public class DigestOutputStream extends FilterOutputStream {
    protected Digest digest;

    public DigestOutputStream(OutputStream outputStream, Digest digest) {
        super(outputStream);
        this.digest = digest;
    }

    public Digest getDigest() {
        return this.digest;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        this.digest.update((byte) i2);
        ((FilterOutputStream) this).out.write(i2);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.digest.update(bArr, i2, i3);
        ((FilterOutputStream) this).out.write(bArr, i2, i3);
    }
}
