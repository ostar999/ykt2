package com.meizu.cloud.pushsdk.b.c;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public abstract class j {
    public static j a(final g gVar, final File file) {
        if (file != null) {
            return new j() { // from class: com.meizu.cloud.pushsdk.b.c.j.2
                @Override // com.meizu.cloud.pushsdk.b.c.j
                public g a() {
                    return gVar;
                }

                @Override // com.meizu.cloud.pushsdk.b.c.j
                public void a(com.meizu.cloud.pushsdk.b.g.b bVar) throws IOException {
                    com.meizu.cloud.pushsdk.b.g.l lVarA = null;
                    try {
                        lVarA = com.meizu.cloud.pushsdk.b.g.f.a(file);
                        bVar.a(lVarA);
                    } finally {
                        m.a(lVarA);
                    }
                }

                @Override // com.meizu.cloud.pushsdk.b.c.j
                public long b() {
                    return file.length();
                }
            };
        }
        throw new NullPointerException("content == null");
    }

    public static j a(g gVar, String str) {
        Charset charset = m.f9164c;
        if (gVar != null) {
            Charset charsetB = gVar.b();
            if (charsetB == null) {
                gVar = g.a(gVar + "; charset=utf-8");
            } else {
                charset = charsetB;
            }
        }
        return a(gVar, str.getBytes(charset));
    }

    public static j a(g gVar, byte[] bArr) {
        return a(gVar, bArr, 0, bArr.length);
    }

    public static j a(final g gVar, final byte[] bArr, final int i2, final int i3) {
        if (bArr == null) {
            throw new NullPointerException("content == null");
        }
        m.a(bArr.length, i2, i3);
        return new j() { // from class: com.meizu.cloud.pushsdk.b.c.j.1
            @Override // com.meizu.cloud.pushsdk.b.c.j
            public g a() {
                return gVar;
            }

            @Override // com.meizu.cloud.pushsdk.b.c.j
            public void a(com.meizu.cloud.pushsdk.b.g.b bVar) throws IOException {
                bVar.c(bArr, i2, i3);
            }

            @Override // com.meizu.cloud.pushsdk.b.c.j
            public long b() {
                return i3;
            }
        };
    }

    public abstract g a();

    public abstract void a(com.meizu.cloud.pushsdk.b.g.b bVar) throws IOException;

    public long b() throws IOException {
        return -1L;
    }
}
