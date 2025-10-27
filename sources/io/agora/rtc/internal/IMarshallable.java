package io.agora.rtc.internal;

import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
interface IMarshallable {
    void marshall(ByteBuffer buf);

    byte[] marshall();

    void unmarshall(ByteBuffer buf);

    void unmarshall(byte[] buf);
}
