package com.xiaomi.push;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes6.dex */
public class ki extends kl {

    /* renamed from: a, reason: collision with root package name */
    protected InputStream f25519a;

    /* renamed from: a, reason: collision with other field name */
    protected OutputStream f934a;

    public ki() {
        this.f25519a = null;
        this.f934a = null;
    }

    public ki(OutputStream outputStream) {
        this.f25519a = null;
        this.f934a = outputStream;
    }

    @Override // com.xiaomi.push.kl
    public int a(byte[] bArr, int i2, int i3) throws km, IOException {
        InputStream inputStream = this.f25519a;
        if (inputStream == null) {
            throw new km(1, "Cannot read from null inputStream");
        }
        try {
            int i4 = inputStream.read(bArr, i2, i3);
            if (i4 >= 0) {
                return i4;
            }
            throw new km(4);
        } catch (IOException e2) {
            throw new km(0, e2);
        }
    }

    @Override // com.xiaomi.push.kl
    /* renamed from: a, reason: collision with other method in class */
    public void mo673a(byte[] bArr, int i2, int i3) throws km, IOException {
        OutputStream outputStream = this.f934a;
        if (outputStream == null) {
            throw new km(1, "Cannot write to null outputStream");
        }
        try {
            outputStream.write(bArr, i2, i3);
        } catch (IOException e2) {
            throw new km(0, e2);
        }
    }
}
