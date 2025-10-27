package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface VideoDecoderFactory {
    @CalledByNative
    VideoDecoder createDecoder(String str);
}
