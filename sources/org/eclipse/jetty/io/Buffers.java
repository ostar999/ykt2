package org.eclipse.jetty.io;

/* loaded from: classes9.dex */
public interface Buffers {

    public enum Type {
        BYTE_ARRAY,
        DIRECT,
        INDIRECT
    }

    Buffer getBuffer();

    Buffer getBuffer(int i2);

    Buffer getHeader();

    void returnBuffer(Buffer buffer);
}
