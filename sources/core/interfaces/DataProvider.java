package core.interfaces;

import java.nio.ByteBuffer;
import java.util.List;

/* loaded from: classes8.dex */
public interface DataProvider {
    public static final int ABGR_TO_I420 = 16781377;
    public static final int ARGB_TO_I420 = 16781379;
    public static final int BGRA_TO_I420 = 16781378;
    public static final int I420 = 16781465;
    public static final int NV12 = 16781457;
    public static final int NV21 = 16781456;
    public static final int RGB24_TO_I420 = 16781364;
    public static final int RGB565_TO_I420 = 16781349;
    public static final int RGBA_TO_I420 = 16781376;

    ByteBuffer provideRGBData(List<Integer> list);

    void releaseBuffer();
}
