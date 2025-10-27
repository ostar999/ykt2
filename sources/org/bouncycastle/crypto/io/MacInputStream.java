package org.bouncycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;

/* loaded from: classes9.dex */
public class MacInputStream extends FilterInputStream {
    protected Mac mac;

    public MacInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        this.mac = mac;
    }

    public Mac getMac() {
        return this.mac;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IllegalStateException, IOException {
        int i2 = ((FilterInputStream) this).in.read();
        if (i2 >= 0) {
            this.mac.update((byte) i2);
        }
        return i2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException, IOException {
        int i4 = ((FilterInputStream) this).in.read(bArr, i2, i3);
        if (i4 >= 0) {
            this.mac.update(bArr, i2, i4);
        }
        return i4;
    }
}
