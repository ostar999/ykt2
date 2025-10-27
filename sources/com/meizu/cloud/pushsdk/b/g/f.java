package com.meizu.cloud.pushsdk.b.g;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/* loaded from: classes4.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    private static final Logger f9187a = Logger.getLogger(f.class.getName());

    private f() {
    }

    public static b a(k kVar) {
        if (kVar != null) {
            return new g(kVar);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static c a(l lVar) {
        if (lVar != null) {
            return new h(lVar);
        }
        throw new IllegalArgumentException("source == null");
    }

    public static k a(OutputStream outputStream) {
        return a(outputStream, new m());
    }

    private static k a(final OutputStream outputStream, final m mVar) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (mVar != null) {
            return new k() { // from class: com.meizu.cloud.pushsdk.b.g.f.1
                @Override // com.meizu.cloud.pushsdk.b.g.k
                public void a(a aVar, long j2) throws IOException {
                    n.a(aVar.f9179b, 0L, j2);
                    while (j2 > 0) {
                        mVar.a();
                        i iVar = aVar.f9178a;
                        int iMin = (int) Math.min(j2, iVar.f9201c - iVar.f9200b);
                        outputStream.write(iVar.f9199a, iVar.f9200b, iMin);
                        int i2 = iVar.f9200b + iMin;
                        iVar.f9200b = i2;
                        long j3 = iMin;
                        j2 -= j3;
                        aVar.f9179b -= j3;
                        if (i2 == iVar.f9201c) {
                            aVar.f9178a = iVar.a();
                            j.a(iVar);
                        }
                    }
                }

                @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.b.g.l
                public void close() throws IOException {
                    outputStream.close();
                }

                @Override // com.meizu.cloud.pushsdk.b.g.k, java.io.Flushable
                public void flush() throws IOException {
                    outputStream.flush();
                }

                public String toString() {
                    return "sink(" + outputStream + ")";
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }

    public static l a(File file) throws FileNotFoundException {
        if (file != null) {
            return a(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static l a(InputStream inputStream) {
        return a(inputStream, new m());
    }

    private static l a(final InputStream inputStream, final m mVar) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (mVar != null) {
            return new l() { // from class: com.meizu.cloud.pushsdk.b.g.f.2
                @Override // com.meizu.cloud.pushsdk.b.g.l
                public long b(a aVar, long j2) throws IOException {
                    if (j2 < 0) {
                        throw new IllegalArgumentException("byteCount < 0: " + j2);
                    }
                    if (j2 == 0) {
                        return 0L;
                    }
                    mVar.a();
                    i iVarC = aVar.c(1);
                    int i2 = inputStream.read(iVarC.f9199a, iVarC.f9201c, (int) Math.min(j2, 2048 - iVarC.f9201c));
                    if (i2 == -1) {
                        return -1L;
                    }
                    iVarC.f9201c += i2;
                    long j3 = i2;
                    aVar.f9179b += j3;
                    return j3;
                }

                @Override // com.meizu.cloud.pushsdk.b.g.l, java.lang.AutoCloseable
                public void close() throws IOException {
                    inputStream.close();
                }

                public String toString() {
                    return "source(" + inputStream + ")";
                }
            };
        }
        throw new IllegalArgumentException("timeout == null");
    }
}
