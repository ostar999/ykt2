package com.tencent.liteav.videoengine.decoder;

/* loaded from: classes6.dex */
public interface n {

    public enum a {
        HARDWARE,
        SOFTWARE
    }

    void decode(com.tencent.liteav.videobase.e.b bVar);

    a getDecoderType();

    void start(Object obj, o oVar);

    void stop();
}
