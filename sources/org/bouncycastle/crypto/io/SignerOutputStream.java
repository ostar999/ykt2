package org.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.crypto.Signer;

/* loaded from: classes9.dex */
public class SignerOutputStream extends FilterOutputStream {
    protected Signer signer;

    public SignerOutputStream(OutputStream outputStream, Signer signer) {
        super(outputStream);
        this.signer = signer;
    }

    public Signer getSigner() {
        return this.signer;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) throws IOException {
        this.signer.update((byte) i2);
        ((FilterOutputStream) this).out.write(i2);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        this.signer.update(bArr, i2, i3);
        ((FilterOutputStream) this).out.write(bArr, i2, i3);
    }
}
