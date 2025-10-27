package org.wrtca.video;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.Surface;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.wrtca.api.EncodedImage;
import org.wrtca.api.GlRectDrawer;
import org.wrtca.api.VideoCodecStatus;
import org.wrtca.api.VideoEncoder;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.VideoFrameDrawer;
import org.wrtca.api.YuvHelper;
import org.wrtca.gl.EglBase14;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

@TargetApi(19)
/* loaded from: classes9.dex */
public class HardwareVideoEncoder implements VideoEncoder {
    private static final int DEQUEUE_OUTPUT_BUFFER_TIMEOUT_US = 100000;
    private static final String KEY_BITRATE_MODE = "bitrate-mode";
    private static final int MAX_ENCODER_Q_SIZE = 2;
    private static final int MAX_VIDEO_FRAMERATE = 30;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final String TAG = "HardwareVideoEncoder";
    private static final int VIDEO_AVC_LEVEL_3 = 256;
    private static final int VIDEO_AVC_PROFILE_HIGH = 8;
    private static final int VIDEO_ControlRateConstant = 2;
    private int adjustedBitrate;
    private boolean automaticResizeOn;
    private final BitrateAdjuster bitrateAdjuster;
    private VideoEncoder.Callback callback;
    private MediaCodec codec;
    private final String codecName;
    private final VideoCodecType codecType;
    private ByteBuffer configBuffer;
    private final ThreadUtils.ThreadChecker encodeThreadChecker;
    private final long forcedKeyFrameNs;
    private int height;
    private final int keyFrameIntervalSec;
    private long lastKeyFrameNs;
    private Thread outputThread;
    private final ThreadUtils.ThreadChecker outputThreadChecker;
    private final Map<String, String> params;
    private volatile boolean running;
    private final EglBase14.Context sharedContext;
    private volatile Exception shutdownException;
    private final Integer surfaceColorFormat;
    private EglBase14 textureEglBase;
    private Surface textureInputSurface;
    private boolean useSurfaceMode;
    private int width;
    private final Integer yuvColorFormat;
    private final YuvFormat yuvFormat;
    private final GlRectDrawer textureDrawer = new GlRectDrawer();
    private final VideoFrameDrawer videoFrameDrawer = new VideoFrameDrawer();
    private final BlockingDeque<EncodedImage.Builder> outputBuilders = new LinkedBlockingDeque();

    public enum YuvFormat {
        I420 { // from class: org.wrtca.video.HardwareVideoEncoder.YuvFormat.1
            @Override // org.wrtca.video.HardwareVideoEncoder.YuvFormat
            public void fillBuffer(ByteBuffer byteBuffer, VideoFrame.Buffer buffer) {
                VideoFrame.I420Buffer i420 = buffer.toI420();
                YuvHelper.I420Copy(i420.getDataY(), i420.getStrideY(), i420.getDataU(), i420.getStrideU(), i420.getDataV(), i420.getStrideV(), byteBuffer, i420.getWidth(), i420.getHeight());
                i420.release();
            }
        },
        NV12 { // from class: org.wrtca.video.HardwareVideoEncoder.YuvFormat.2
            @Override // org.wrtca.video.HardwareVideoEncoder.YuvFormat
            public void fillBuffer(ByteBuffer byteBuffer, VideoFrame.Buffer buffer) {
                VideoFrame.I420Buffer i420 = buffer.toI420();
                YuvHelper.I420ToNV12(i420.getDataY(), i420.getStrideY(), i420.getDataU(), i420.getStrideU(), i420.getDataV(), i420.getStrideV(), byteBuffer, i420.getWidth(), i420.getHeight());
                i420.release();
            }
        };

        public abstract void fillBuffer(ByteBuffer byteBuffer, VideoFrame.Buffer buffer);

        public static YuvFormat valueOf(int i2) {
            if (i2 == 19) {
                return I420;
            }
            if (i2 == 21 || i2 == 2141391872 || i2 == 2141391876) {
                return NV12;
            }
            throw new IllegalArgumentException("Unsupported colorFormat: " + i2);
        }
    }

    public HardwareVideoEncoder(String str, VideoCodecType videoCodecType, Integer num, Integer num2, Map<String, String> map, int i2, int i3, BitrateAdjuster bitrateAdjuster, EglBase14.Context context) {
        ThreadUtils.ThreadChecker threadChecker = new ThreadUtils.ThreadChecker();
        this.encodeThreadChecker = threadChecker;
        this.outputThreadChecker = new ThreadUtils.ThreadChecker();
        this.configBuffer = null;
        this.running = false;
        this.shutdownException = null;
        this.codecName = str;
        this.codecType = videoCodecType;
        this.surfaceColorFormat = num;
        this.yuvColorFormat = num2;
        this.yuvFormat = YuvFormat.valueOf(num2.intValue());
        this.params = map;
        this.keyFrameIntervalSec = i2;
        this.forcedKeyFrameNs = TimeUnit.MILLISECONDS.toNanos(i3);
        this.bitrateAdjuster = bitrateAdjuster;
        this.sharedContext = context;
        threadChecker.detachThread();
    }

    private boolean canUseSurface() {
        return (this.sharedContext == null || this.surfaceColorFormat == null) ? false : true;
    }

    private Thread createOutputThread() {
        return new Thread() { // from class: org.wrtca.video.HardwareVideoEncoder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (HardwareVideoEncoder.this.running) {
                    HardwareVideoEncoder.this.deliverEncodedImage();
                }
                HardwareVideoEncoder.this.releaseCodecOnOutputThread();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliverEncodedImage() {
        ByteBuffer byteBufferSlice;
        this.outputThreadChecker.checkIsOnValidThread();
        try {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, 100000L);
            if (iDequeueOutputBuffer < 0) {
                return;
            }
            ByteBuffer byteBuffer = this.codec.getOutputBuffers()[iDequeueOutputBuffer];
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            if ((bufferInfo.flags & 2) != 0) {
                Logging.d(TAG, "Config frame generated. Offset: " + bufferInfo.offset + ". Size: " + bufferInfo.size);
                ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bufferInfo.size);
                this.configBuffer = byteBufferAllocateDirect;
                byteBufferAllocateDirect.put(byteBuffer);
            } else {
                this.bitrateAdjuster.reportEncodedFrame(bufferInfo.size);
                if (this.adjustedBitrate != this.bitrateAdjuster.getAdjustedBitrateBps()) {
                    updateBitrate();
                }
                boolean z2 = true;
                if ((bufferInfo.flags & 1) == 0) {
                    z2 = false;
                }
                if (z2) {
                    Logging.d(TAG, "Sync frame generated");
                }
                if (z2 && this.codecType == VideoCodecType.H264) {
                    Logging.d(TAG, "Prepending config frame of size " + this.configBuffer.capacity() + " to output buffer with offset " + bufferInfo.offset + ", size " + bufferInfo.size);
                    byteBufferSlice = ByteBuffer.allocateDirect(bufferInfo.size + this.configBuffer.capacity());
                    this.configBuffer.rewind();
                    byteBufferSlice.put(this.configBuffer);
                    byteBufferSlice.put(byteBuffer);
                    byteBufferSlice.rewind();
                } else {
                    byteBufferSlice = byteBuffer.slice();
                }
                EncodedImage.FrameType frameType = z2 ? EncodedImage.FrameType.VideoFrameKey : EncodedImage.FrameType.VideoFrameDelta;
                EncodedImage.Builder builderPoll = this.outputBuilders.poll();
                builderPoll.setBuffer(byteBufferSlice).setFrameType(frameType);
                this.callback.onEncodedFrame(builderPoll.createEncodedImage(), new VideoEncoder.CodecSpecificInfo());
            }
            this.codec.releaseOutputBuffer(iDequeueOutputBuffer, false);
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "deliverOutput failed", e2);
        }
    }

    private VideoCodecStatus encodeByteBuffer(VideoFrame videoFrame, VideoFrame.Buffer buffer, int i2) throws MediaCodec.CryptoException {
        this.encodeThreadChecker.checkIsOnValidThread();
        long timestampNs = (videoFrame.getTimestampNs() + 500) / 1000;
        try {
            int iDequeueInputBuffer = this.codec.dequeueInputBuffer(0L);
            if (iDequeueInputBuffer == -1) {
                Logging.d(TAG, "Dropped frame, no input buffers available");
                return VideoCodecStatus.NO_OUTPUT;
            }
            try {
                this.yuvFormat.fillBuffer(this.codec.getInputBuffers()[iDequeueInputBuffer], buffer);
                try {
                    this.codec.queueInputBuffer(iDequeueInputBuffer, 0, i2, timestampNs, 0);
                    return VideoCodecStatus.OK;
                } catch (IllegalStateException e2) {
                    Logging.e(TAG, "queueInputBuffer failed", e2);
                    return VideoCodecStatus.ERROR;
                }
            } catch (IllegalStateException e3) {
                Logging.e(TAG, "getInputBuffers failed", e3);
                return VideoCodecStatus.ERROR;
            }
        } catch (IllegalStateException e4) {
            Logging.e(TAG, "dequeueInputBuffer failed", e4);
            return VideoCodecStatus.ERROR;
        }
    }

    private VideoCodecStatus encodeTextureBuffer(VideoFrame videoFrame) {
        this.encodeThreadChecker.checkIsOnValidThread();
        try {
            GLES20.glClear(16384);
            this.videoFrameDrawer.drawFrame(new VideoFrame(videoFrame.getBuffer(), 0, videoFrame.getTimestampNs()), this.textureDrawer, null);
            this.textureEglBase.swapBuffers(videoFrame.getTimestampNs());
            return VideoCodecStatus.OK;
        } catch (RuntimeException e2) {
            Logging.e(TAG, "encodeTexture failed", e2);
            return VideoCodecStatus.ERROR;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a3 A[Catch: IllegalStateException -> 0x0104, TryCatch #1 {IllegalStateException -> 0x0104, blocks: (B:9:0x0020, B:11:0x0059, B:15:0x0068, B:29:0x008e, B:30:0x00a3, B:20:0x0077, B:23:0x0081, B:31:0x00b1, B:33:0x00cf, B:34:0x00ec), top: B:44:0x0020 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.wrtca.api.VideoCodecStatus initEncodeInternal() {
        /*
            Method dump skipped, instructions count: 297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.wrtca.video.HardwareVideoEncoder.initEncodeInternal():org.wrtca.api.VideoCodecStatus");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseCodecOnOutputThread() {
        this.outputThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "Releasing MediaCodec on output thread");
        try {
            this.codec.stop();
        } catch (Exception e2) {
            Logging.e(TAG, "Media encoder stop failed", e2);
        }
        try {
            this.codec.release();
        } catch (Exception e3) {
            Logging.e(TAG, "Media encoder release failed", e3);
            this.shutdownException = e3;
        }
        this.configBuffer = null;
        Logging.d(TAG, "Release on output thread done");
    }

    private void requestKeyFrame(long j2) {
        this.encodeThreadChecker.checkIsOnValidThread();
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("request-sync", 0);
            this.codec.setParameters(bundle);
            this.lastKeyFrameNs = j2;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "requestKeyFrame failed", e2);
        }
    }

    private VideoCodecStatus resetCodec(int i2, int i3, boolean z2) {
        this.encodeThreadChecker.checkIsOnValidThread();
        VideoCodecStatus videoCodecStatusRelease = release();
        if (videoCodecStatusRelease != VideoCodecStatus.OK) {
            return videoCodecStatusRelease;
        }
        this.width = i2;
        this.height = i3;
        this.useSurfaceMode = z2;
        return initEncodeInternal();
    }

    private boolean shouldForceKeyFrame(long j2) {
        this.encodeThreadChecker.checkIsOnValidThread();
        long j3 = this.forcedKeyFrameNs;
        return j3 > 0 && j2 > this.lastKeyFrameNs + j3;
    }

    private VideoCodecStatus updateBitrate() {
        this.outputThreadChecker.checkIsOnValidThread();
        this.adjustedBitrate = this.bitrateAdjuster.getAdjustedBitrateBps();
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("video-bitrate", this.adjustedBitrate);
            this.codec.setParameters(bundle);
            return VideoCodecStatus.OK;
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "updateBitrate failed", e2);
            return VideoCodecStatus.ERROR;
        }
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus encode(VideoFrame videoFrame, VideoEncoder.EncodeInfo encodeInfo) {
        VideoCodecStatus videoCodecStatusResetCodec;
        this.encodeThreadChecker.checkIsOnValidThread();
        if (this.codec == null) {
            return VideoCodecStatus.UNINITIALIZED;
        }
        VideoFrame.Buffer buffer = videoFrame.getBuffer();
        boolean z2 = buffer instanceof VideoFrame.TextureBuffer;
        int width = videoFrame.getBuffer().getWidth();
        int height = videoFrame.getBuffer().getHeight();
        boolean z3 = canUseSurface() && z2;
        if ((width != this.width || height != this.height || z3 != this.useSurfaceMode) && (videoCodecStatusResetCodec = resetCodec(width, height, z3)) != VideoCodecStatus.OK) {
            return videoCodecStatusResetCodec;
        }
        if (this.outputBuilders.size() > 2) {
            Logging.e(TAG, "Dropped frame, encoder queue full");
            return VideoCodecStatus.NO_OUTPUT;
        }
        boolean z4 = false;
        for (EncodedImage.FrameType frameType : encodeInfo.frameTypes) {
            if (frameType == EncodedImage.FrameType.VideoFrameKey) {
                z4 = true;
            }
        }
        if (z4 || shouldForceKeyFrame(videoFrame.getTimestampNs())) {
            requestKeyFrame(videoFrame.getTimestampNs());
        }
        int height2 = ((buffer.getHeight() * buffer.getWidth()) * 3) / 2;
        this.outputBuilders.offer(EncodedImage.builder().setCaptureTimeNs(videoFrame.getTimestampNs()).setCompleteFrame(true).setEncodedWidth(videoFrame.getBuffer().getWidth()).setEncodedHeight(videoFrame.getBuffer().getHeight()).setRotation(videoFrame.getRotation()));
        VideoCodecStatus videoCodecStatusEncodeTextureBuffer = this.useSurfaceMode ? encodeTextureBuffer(videoFrame) : encodeByteBuffer(videoFrame, buffer, height2);
        if (videoCodecStatusEncodeTextureBuffer != VideoCodecStatus.OK) {
            this.outputBuilders.pollLast();
        }
        return videoCodecStatusEncodeTextureBuffer;
    }

    @Override // org.wrtca.api.VideoEncoder
    public String getImplementationName() {
        return "HWEncoder";
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoEncoder.ScalingSettings getScalingSettings() {
        this.encodeThreadChecker.checkIsOnValidThread();
        if (this.automaticResizeOn) {
            VideoCodecType videoCodecType = this.codecType;
            if (videoCodecType == VideoCodecType.VP8) {
                return new VideoEncoder.ScalingSettings(29, 95);
            }
            if (videoCodecType == VideoCodecType.H264) {
                return new VideoEncoder.ScalingSettings(24, 37);
            }
        }
        return VideoEncoder.ScalingSettings.OFF;
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus initEncode(VideoEncoder.Settings settings, VideoEncoder.Callback callback) {
        int i2;
        this.encodeThreadChecker.checkIsOnValidThread();
        this.callback = callback;
        this.automaticResizeOn = settings.automaticResizeOn;
        this.width = settings.width;
        this.height = settings.height;
        this.useSurfaceMode = canUseSurface();
        int i3 = settings.startBitrate;
        if (i3 != 0 && (i2 = settings.maxFramerate) != 0) {
            this.bitrateAdjuster.setTargets(i3 * 1000, i2);
        }
        this.adjustedBitrate = this.bitrateAdjuster.getAdjustedBitrateBps();
        Logging.d(TAG, "initEncode: " + this.width + " x " + this.height + ". @ " + settings.startBitrate + "kbps. Fps: " + settings.maxFramerate + " Use surface mode: " + this.useSurfaceMode);
        return initEncodeInternal();
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus release() {
        VideoCodecStatus videoCodecStatus;
        this.encodeThreadChecker.checkIsOnValidThread();
        if (this.outputThread == null) {
            videoCodecStatus = VideoCodecStatus.OK;
        } else {
            this.running = false;
            if (!ThreadUtils.joinUninterruptibly(this.outputThread, 5000L)) {
                Logging.e(TAG, "Media encoder release timeout");
                videoCodecStatus = VideoCodecStatus.TIMEOUT;
            } else if (this.shutdownException != null) {
                Logging.e(TAG, "Media encoder release exception", this.shutdownException);
                videoCodecStatus = VideoCodecStatus.ERROR;
            } else {
                videoCodecStatus = VideoCodecStatus.OK;
            }
        }
        this.textureDrawer.release();
        this.videoFrameDrawer.release();
        EglBase14 eglBase14 = this.textureEglBase;
        if (eglBase14 != null) {
            eglBase14.release();
            this.textureEglBase = null;
        }
        Surface surface = this.textureInputSurface;
        if (surface != null) {
            surface.release();
            this.textureInputSurface = null;
        }
        this.outputBuilders.clear();
        this.codec = null;
        this.outputThread = null;
        this.encodeThreadChecker.detachThread();
        return videoCodecStatus;
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus setChannelParameters(short s2, long j2) {
        this.encodeThreadChecker.checkIsOnValidThread();
        return VideoCodecStatus.OK;
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus setRateAllocation(VideoEncoder.BitrateAllocation bitrateAllocation, int i2) {
        this.encodeThreadChecker.checkIsOnValidThread();
        if (i2 > 30) {
            i2 = 30;
        }
        this.bitrateAdjuster.setTargets(bitrateAllocation.getSum(), i2);
        return VideoCodecStatus.OK;
    }
}
