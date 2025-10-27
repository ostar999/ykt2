package org.eclipse.jetty.io;

import org.eclipse.jetty.io.Buffers;

/* loaded from: classes9.dex */
public class BuffersFactory {
    public static Buffers newBuffers(Buffers.Type type, int i2, Buffers.Type type2, int i3, Buffers.Type type3, int i4) {
        return i4 >= 0 ? new PooledBuffers(type, i2, type2, i3, type3, i4) : new ThreadLocalBuffers(type, i2, type2, i3, type3);
    }
}
