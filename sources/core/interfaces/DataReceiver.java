package core.interfaces;

import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public interface DataReceiver {
    public static final int I420_TO_ABGR = 16781376;
    public static final int I420_TO_RGBA = 16781377;

    ByteBuffer getCacheBuffer();

    int getType();

    void onReceiveRGBAData(ByteBuffer byteBuffer, int i2, int i3);

    void releaseBuffer();
}
