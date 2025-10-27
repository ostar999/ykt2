package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface VideoEncoderFactory {
    @CalledByNative
    VideoEncoder createEncoder(VideoCodecInfo videoCodecInfo);

    @CalledByNative
    VideoCodecInfo[] getSupportedCodecs();
}
