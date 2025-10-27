package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
abstract class CoderBase {
    private static final byte[] NONE = new byte[0];
    private final Class<?>[] acceptableOptions;

    public CoderBase(Class<?>... clsArr) {
        this.acceptableOptions = clsArr;
    }

    public static int numberOptionOrDefault(Object obj, int i2) {
        return obj instanceof Number ? ((Number) obj).intValue() : i2;
    }

    public boolean canAcceptOptions(Object obj) {
        for (Class<?> cls : this.acceptableOptions) {
            if (cls.isInstance(obj)) {
                return true;
            }
        }
        return false;
    }

    public abstract InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException;

    public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
        throw new UnsupportedOperationException("method doesn't support writing");
    }

    public byte[] getOptionsAsProperties(Object obj) {
        return NONE;
    }

    public Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return null;
    }
}
