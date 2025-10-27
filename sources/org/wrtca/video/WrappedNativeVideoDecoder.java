package org.wrtca.video;

import org.wrtca.api.EncodedImage;
import org.wrtca.api.VideoCodecStatus;
import org.wrtca.api.VideoDecoder;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public abstract class WrappedNativeVideoDecoder implements VideoDecoder {
    @CalledByNative
    public static boolean isInstanceOf(VideoDecoder videoDecoder) {
        return videoDecoder instanceof WrappedNativeVideoDecoder;
    }

    @CalledByNative
    abstract long createNativeDecoder();

    @Override // org.wrtca.api.VideoDecoder
    public VideoCodecStatus decode(EncodedImage encodedImage, VideoDecoder.DecodeInfo decodeInfo) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoDecoder
    public String getImplementationName() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoDecoder
    public boolean getPrefersLateDecoding() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoDecoder
    public VideoCodecStatus initDecode(VideoDecoder.Settings settings, VideoDecoder.Callback callback) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoDecoder
    public VideoCodecStatus release() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
