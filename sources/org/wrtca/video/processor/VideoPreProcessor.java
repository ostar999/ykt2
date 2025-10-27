package org.wrtca.video.processor;

import org.wrtca.api.VideoCapturer;

/* loaded from: classes9.dex */
public class VideoPreProcessor implements ProcessorModule<VideoCapturer> {
    private static final String TAG = "VideoPreProcessor";
    public VideoCapturer mVideoCapturer;

    public static final float[] backOriginMatrix() {
        return new float[]{0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    public static final float[] frontOriginMatrix() {
        return new float[]{0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    public static final float[] horizontalFlipMatrix() {
        return new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    }

    public static final float[] identityMatrix() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public static final float[] verticalFlipMatrix() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x012a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.wrtca.api.VideoFrame processWriteBack(org.wrtca.video.CameraSession.CameraParam r8, int r9, org.wrtca.api.SurfaceTextureHelper r10, core.data.WrappedVideoFrame r11, core.data.WrappedVideoFrame r12) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.video.processor.VideoPreProcessor.processWriteBack(org.wrtca.video.CameraSession$CameraParam, int, org.wrtca.api.SurfaceTextureHelper, core.data.WrappedVideoFrame, core.data.WrappedVideoFrame):org.wrtca.api.VideoFrame");
    }

    @Override // org.wrtca.video.processor.ProcessorModule
    public void release() {
        this.mVideoCapturer = null;
    }

    @Override // org.wrtca.video.processor.ProcessorModule
    public void load(VideoCapturer videoCapturer) {
        if (videoCapturer != null) {
            this.mVideoCapturer = videoCapturer;
        }
    }
}
