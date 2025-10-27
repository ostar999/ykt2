package org.bouncycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;

/* loaded from: classes9.dex */
public class MacOutputStream extends FilterOutputStream {
    protected Mac mac;

    public MacOutputStream(OutputStream outputStream, Mac mac) {
        super(outputStream);
        this.mac = mac;
    }

    public Mac getMac() {
        return this.mac;
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) throws IllegalStateException, IOException {
        this.mac.update((byte) i2);
        ((FilterOutputStream) this).out.write(i2);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException, IOException {
        this.mac.update(bArr, i2, i3);
        ((FilterOutputStream) this).out.write(bArr, i2, i3);
    }
}
