package org.eclipse.jetty.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes9.dex */
public interface Buffer extends Cloneable {
    public static final int IMMUTABLE = 0;
    public static final boolean NON_VOLATILE = false;
    public static final int READONLY = 1;
    public static final int READWRITE = 2;
    public static final boolean VOLATILE = true;

    public interface CaseInsensitve {
    }

    byte[] array();

    byte[] asArray();

    Buffer asImmutableBuffer();

    Buffer asMutableBuffer();

    Buffer asNonVolatileBuffer();

    Buffer asReadOnlyBuffer();

    Buffer buffer();

    int capacity();

    void clear();

    void compact();

    boolean equalsIgnoreCase(Buffer buffer);

    byte get();

    int get(byte[] bArr, int i2, int i3);

    Buffer get(int i2);

    int getIndex();

    boolean hasContent();

    boolean isImmutable();

    boolean isReadOnly();

    boolean isVolatile();

    int length();

    void mark();

    void mark(int i2);

    int markIndex();

    byte peek();

    byte peek(int i2);

    int peek(int i2, byte[] bArr, int i3, int i4);

    Buffer peek(int i2, int i3);

    int poke(int i2, Buffer buffer);

    int poke(int i2, byte[] bArr, int i3, int i4);

    void poke(int i2, byte b3);

    int put(Buffer buffer);

    int put(byte[] bArr);

    int put(byte[] bArr, int i2, int i3);

    void put(byte b3);

    int putIndex();

    int readFrom(InputStream inputStream, int i2) throws IOException;

    void reset();

    void setGetIndex(int i2);

    void setMarkIndex(int i2);

    void setPutIndex(int i2);

    int skip(int i2);

    Buffer slice();

    Buffer sliceFromMark();

    Buffer sliceFromMark(int i2);

    int space();

    String toDetailString();

    String toString(String str);

    String toString(Charset charset);

    void writeTo(OutputStream outputStream) throws IOException;
}
