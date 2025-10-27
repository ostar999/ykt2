package org.wrtca.video;

import org.wrtca.api.VideoDecoder;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.CalledByNative;
import org.wrtca.util.NativeClassQualifiedName;

/* loaded from: classes9.dex */
class VideoDecoderWrapper {
    @CalledByNative
    public static VideoDecoder.Callback createDecoderCallback(final long j2) {
        return new VideoDecoder.Callback() { // from class: org.wrtca.video.e
            @Override // org.wrtca.api.VideoDecoder.Callback
            public final void onDecodedFrame(VideoFrame videoFrame, Integer num, Integer num2) {
                VideoDecoderWrapper.nativeOnDecodedFrame(j2, videoFrame, num, num2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NativeClassQualifiedName("webrtc::jni::VideoDecoderWrapper")
    public static native void nativeOnDecodedFrame(long j2, VideoFrame videoFrame, Integer num, Integer num2);
}
