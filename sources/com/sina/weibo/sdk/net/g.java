package com.sina.weibo.sdk.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public final class g implements f {
    private int code;

    /* renamed from: q, reason: collision with root package name */
    private InputStream f17192q;

    public g(int i2, InputStream inputStream) {
        this.code = i2;
        this.f17192q = inputStream;
    }

    @Override // com.sina.weibo.sdk.net.f
    public final String h() throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = this.f17192q.read(bArr);
                if (i2 == -1) {
                    String string = byteArrayOutputStream.toString();
                    byteArrayOutputStream.close();
                    return string;
                }
                byteArrayOutputStream.write(bArr, 0, i2);
            }
        } catch (IOException e2) {
            throw e2;
        }
    }
}
