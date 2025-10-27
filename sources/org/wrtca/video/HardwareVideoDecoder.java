package org.wrtca.video;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.SystemClock;
import android.view.Surface;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.wrtca.api.EglBase;
import org.wrtca.api.EncodedImage;
import org.wrtca.api.JavaI420Buffer;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoCodecStatus;
import org.wrtca.api.VideoDecoder;
import org.wrtca.api.VideoFrame;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

@TargetApi(16)
/* loaded from: classes9.dex */
public class HardwareVideoDecoder implements VideoDecoder, SurfaceTextureHelper.OnTextureFrameAvailableListener {
    private static final int DEQUEUE_INPUT_TIMEOUT_US = 500000;
    private static final int DEQUEUE_OUTPUT_BUFFER_TIMEOUT_US = 100000;
    private static final int MEDIA_CODEC_RELEASE_TIMEOUT_MS = 5000;
    private static final String MEDIA_FORMAT_KEY_CROP_BOTTOM = "crop-bottom";
    private static final String MEDIA_FORMAT_KEY_CROP_LEFT = "crop-left";
    private static final String MEDIA_FORMAT_KEY_CROP_RIGHT = "crop-right";
    private static final String MEDIA_FORMAT_KEY_CROP_TOP = "crop-top";
    private static final String MEDIA_FORMAT_KEY_SLICE_HEIGHT = "slice-height";
    private static final String MEDIA_FORMAT_KEY_STRIDE = "stride";
    private static final String TAG = "HardwareVideoDecoder";
    private VideoDecoder.Callback callback;
    private final String codecName;
    private final VideoCodecType codecType;
    private int colorFormat;
    private ThreadUtils.ThreadChecker decoderThreadChecker;
    private final BlockingDeque<FrameInfo> frameInfos;
    private boolean hasDecodedFirstFrame;
    private int height;
    private boolean keyFrameRequired;
    private Thread outputThread;
    private ThreadUtils.ThreadChecker outputThreadChecker;
    private DecodedTextureMetadata renderedTextureMetadata;
    private final EglBase.Context sharedContext;
    private int sliceHeight;
    private int stride;
    private SurfaceTextureHelper surfaceTextureHelper;
    private int width;
    private volatile boolean running = false;
    private volatile Exception shutdownException = null;
    private final Object dimensionLock = new Object();
    private Surface surface = null;
    private final Object renderedTextureMetadataLock = new Object();
    private MediaCodec codec = null;

    public static class DecodedTextureMetadata {
        public final Integer decodeTimeMs;
        public final int height;
        public final long presentationTimestampUs;
        public final int rotation;
        public final int width;

        public DecodedTextureMetadata(int i2, int i3, int i4, long j2, Integer num) {
            this.width = i2;
            this.height = i3;
            this.rotation = i4;
            this.presentationTimestampUs = j2;
            this.decodeTimeMs = num;
        }
    }

    public static class FrameInfo {
        public final long decodeStartTimeMs;
        public final int rotation;

        public FrameInfo(long j2, int i2) {
            this.decodeStartTimeMs = j2;
            this.rotation = i2;
        }
    }

    public HardwareVideoDecoder(String str, VideoCodecType videoCodecType, int i2, EglBase.Context context) {
        if (!isSupportedColorFormat(i2)) {
            throw new IllegalArgumentException("Unsupported color format: " + i2);
        }
        this.codecName = str;
        this.codecType = videoCodecType;
        this.colorFormat = i2;
        this.sharedContext = context;
        this.frameInfos = new LinkedBlockingDeque();
    }

    private VideoFrame.Buffer copyI420Buffer(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5) {
        int i6 = i2 / 2;
        int i7 = (i2 * i3) + 0;
        int i8 = (i3 / 2) * i6;
        int i9 = i7 + i8;
        int i10 = ((i6 * i3) / 2) + i7;
        int i11 = i8 + i10;
        JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(i4, i5);
        ByteBuffer dataY = javaI420BufferAllocate.getDataY();
        byteBuffer.position(0);
        byteBuffer.limit(i7);
        dataY.put(byteBuffer);
        ByteBuffer dataU = javaI420BufferAllocate.getDataU();
        byteBuffer.position(i7);
        byteBuffer.limit(i9);
        dataU.put(byteBuffer);
        int i12 = i3 % 2;
        if (i12 != 0) {
            byteBuffer.position(i9 - i6);
            dataU.put(byteBuffer);
        }
        ByteBuffer dataV = javaI420BufferAllocate.getDataV();
        byteBuffer.position(i10);
        byteBuffer.limit(i11);
        dataV.put(byteBuffer);
        if (i12 != 0) {
            byteBuffer.position(i11 - i6);
            dataV.put(byteBuffer);
        }
        return javaI420BufferAllocate;
    }

    private VideoFrame.Buffer copyNV12ToI420Buffer(ByteBuffer byteBuffer, int i2, int i3, int i4, int i5) {
        return new NV12Buffer(i4, i5, i2, i3, byteBuffer, null).toI420();
    }

    private Thread createOutputThread() {
        return new Thread("HardwareVideoDecoder.outputThread") { // from class: org.wrtca.video.HardwareVideoDecoder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                HardwareVideoDecoder.this.outputThreadChecker = new ThreadUtils.ThreadChecker();
                while (HardwareVideoDecoder.this.running) {
                    HardwareVideoDecoder.this.deliverDecodedFrame();
                }
                HardwareVideoDecoder.this.releaseCodecOnOutputThread();
            }
        };
    }

    private void deliverByteFrame(int i2, MediaCodec.BufferInfo bufferInfo, int i3, Integer num) {
        int i4;
        int i5;
        int i6;
        int i7;
        synchronized (this.dimensionLock) {
            i4 = this.width;
            i5 = this.height;
            i6 = this.stride;
            i7 = this.sliceHeight;
        }
        int i8 = bufferInfo.size;
        if (i8 < ((i4 * i5) * 3) / 2) {
            Logging.e(TAG, "Insufficient output buffer size: " + bufferInfo.size);
            return;
        }
        int i9 = (i8 >= ((i6 * i5) * 3) / 2 || i7 != i5 || i6 <= i4) ? i6 : (i8 * 2) / (i5 * 3);
        ByteBuffer byteBuffer = this.codec.getOutputBuffers()[i2];
        byteBuffer.position(bufferInfo.offset);
        byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
        ByteBuffer byteBufferSlice = byteBuffer.slice();
        VideoFrame.Buffer bufferCopyI420Buffer = this.colorFormat == 19 ? copyI420Buffer(byteBufferSlice, i9, i7, i4, i5) : copyNV12ToI420Buffer(byteBufferSlice, i9, i7, i4, i5);
        this.codec.releaseOutputBuffer(i2, false);
        VideoFrame videoFrame = new VideoFrame(bufferCopyI420Buffer, i3, bufferInfo.presentationTimeUs * 1000);
        this.callback.onDecodedFrame(videoFrame, num, null);
        videoFrame.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliverDecodedFrame() {
        Integer numValueOf;
        int i2;
        this.outputThreadChecker.checkIsOnValidThread();
        try {
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.codec.dequeueOutputBuffer(bufferInfo, 100000L);
            if (iDequeueOutputBuffer == -2) {
                reformat(this.codec.getOutputFormat());
                return;
            }
            if (iDequeueOutputBuffer < 0) {
                Logging.v(TAG, "dequeueOutputBuffer returned " + iDequeueOutputBuffer);
                return;
            }
            FrameInfo frameInfoPoll = this.frameInfos.poll();
            if (frameInfoPoll != null) {
                numValueOf = Integer.valueOf((int) (SystemClock.elapsedRealtime() - frameInfoPoll.decodeStartTimeMs));
                i2 = frameInfoPoll.rotation;
            } else {
                numValueOf = null;
                i2 = 0;
            }
            this.hasDecodedFirstFrame = true;
            if (this.surfaceTextureHelper != null) {
                deliverTextureFrame(iDequeueOutputBuffer, bufferInfo, i2, numValueOf);
            } else {
                deliverByteFrame(iDequeueOutputBuffer, bufferInfo, i2, numValueOf);
            }
        } catch (IllegalStateException e2) {
            Logging.e(TAG, "deliverDecodedFrame failed", e2);
        }
    }

    private void deliverTextureFrame(int i2, MediaCodec.BufferInfo bufferInfo, int i3, Integer num) {
        int i4;
        int i5;
        synchronized (this.dimensionLock) {
            i4 = this.width;
            i5 = this.height;
        }
        synchronized (this.renderedTextureMetadataLock) {
            if (this.renderedTextureMetadata != null) {
                return;
            }
            this.renderedTextureMetadata = new DecodedTextureMetadata(i4, i5, i3, bufferInfo.presentationTimeUs, num);
            this.codec.releaseOutputBuffer(i2, true);
        }
    }

    private VideoCodecStatus initDecodeInternal(int i2, int i3) {
        this.decoderThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "initDecodeInternal");
        if (this.outputThread != null) {
            Logging.e(TAG, "initDecodeInternal called while the codec is already running");
            return VideoCodecStatus.FALLBACK_SOFTWARE;
        }
        this.width = i2;
        this.height = i3;
        this.stride = i2;
        this.sliceHeight = i3;
        this.hasDecodedFirstFrame = false;
        this.keyFrameRequired = true;
        try {
            this.codec = MediaCodec.createByCodecName(this.codecName);
            try {
                MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(this.codecType.mimeType(), i2, i3);
                if (this.sharedContext == null) {
                    mediaFormatCreateVideoFormat.setInteger("color-format", this.colorFormat);
                }
                this.codec.configure(mediaFormatCreateVideoFormat, this.surface, (MediaCrypto) null, 0);
                this.codec.start();
                this.running = true;
                Thread threadCreateOutputThread = createOutputThread();
                this.outputThread = threadCreateOutputThread;
                threadCreateOutputThread.start();
                Logging.d(TAG, "initDecodeInternal done");
                return VideoCodecStatus.OK;
            } catch (IllegalStateException e2) {
                Logging.e(TAG, "initDecode failed", e2);
                release();
                return VideoCodecStatus.FALLBACK_SOFTWARE;
            }
        } catch (IOException | IllegalArgumentException unused) {
            Logging.e(TAG, "Cannot create media decoder " + this.codecName);
            return VideoCodecStatus.FALLBACK_SOFTWARE;
        }
    }

    private boolean isSupportedColorFormat(int i2) {
        for (int i3 : MediaCodecUtils.DECODER_COLOR_FORMATS) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    private void reformat(MediaFormat mediaFormat) {
        int integer;
        int integer2;
        this.outputThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "Decoder format changed: " + mediaFormat.toString());
        if (mediaFormat.containsKey(MEDIA_FORMAT_KEY_CROP_LEFT) && mediaFormat.containsKey(MEDIA_FORMAT_KEY_CROP_RIGHT) && mediaFormat.containsKey(MEDIA_FORMAT_KEY_CROP_BOTTOM) && mediaFormat.containsKey(MEDIA_FORMAT_KEY_CROP_TOP)) {
            integer = (mediaFormat.getInteger(MEDIA_FORMAT_KEY_CROP_RIGHT) + 1) - mediaFormat.getInteger(MEDIA_FORMAT_KEY_CROP_LEFT);
            integer2 = (mediaFormat.getInteger(MEDIA_FORMAT_KEY_CROP_BOTTOM) + 1) - mediaFormat.getInteger(MEDIA_FORMAT_KEY_CROP_TOP);
        } else {
            integer = mediaFormat.getInteger("width");
            integer2 = mediaFormat.getInteger("height");
        }
        synchronized (this.dimensionLock) {
            if (this.hasDecodedFirstFrame && (this.width != integer || this.height != integer2)) {
                stopOnOutputThread(new RuntimeException("Unexpected size change. Configured " + this.width + "*" + this.height + ". New " + integer + "*" + integer2));
                return;
            }
            this.width = integer;
            this.height = integer2;
            if (this.surfaceTextureHelper == null && mediaFormat.containsKey("color-format")) {
                this.colorFormat = mediaFormat.getInteger("color-format");
                Logging.d(TAG, "Color: 0x" + Integer.toHexString(this.colorFormat));
                if (!isSupportedColorFormat(this.colorFormat)) {
                    stopOnOutputThread(new IllegalStateException("Unsupported color format: " + this.colorFormat));
                    return;
                }
            }
            synchronized (this.dimensionLock) {
                if (mediaFormat.containsKey(MEDIA_FORMAT_KEY_STRIDE)) {
                    this.stride = mediaFormat.getInteger(MEDIA_FORMAT_KEY_STRIDE);
                }
                if (mediaFormat.containsKey(MEDIA_FORMAT_KEY_SLICE_HEIGHT)) {
                    this.sliceHeight = mediaFormat.getInteger(MEDIA_FORMAT_KEY_SLICE_HEIGHT);
                }
                Logging.d(TAG, "Frame stride and slice height: " + this.stride + " x " + this.sliceHeight);
                this.stride = Math.max(this.width, this.stride);
                this.sliceHeight = Math.max(this.height, this.sliceHeight);
            }
        }
    }

    private VideoCodecStatus reinitDecode(int i2, int i3) {
        this.decoderThreadChecker.checkIsOnValidThread();
        VideoCodecStatus videoCodecStatusReleaseInternal = releaseInternal();
        return videoCodecStatusReleaseInternal != VideoCodecStatus.OK ? videoCodecStatusReleaseInternal : initDecodeInternal(i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseCodecOnOutputThread() {
        this.outputThreadChecker.checkIsOnValidThread();
        Logging.d(TAG, "Releasing MediaCodec on output thread");
        try {
            this.codec.stop();
        } catch (Exception e2) {
            Logging.e(TAG, "Media decoder stop failed", e2);
        }
        try {
            this.codec.release();
        } catch (Exception e3) {
            Logging.e(TAG, "Media decoder release failed", e3);
            this.shutdownException = e3;
        }
        Logging.d(TAG, "Release on output thread done");
    }

    private VideoCodecStatus releaseInternal() {
        if (!this.running) {
            Logging.d(TAG, "release: Decoder is not running.");
            return VideoCodecStatus.OK;
        }
        try {
            this.running = false;
            if (!ThreadUtils.joinUninterruptibly(this.outputThread, 5000L)) {
                Logging.e(TAG, "Media decoder release timeout", new RuntimeException());
                return VideoCodecStatus.TIMEOUT;
            }
            if (this.shutdownException != null) {
                Logging.e(TAG, "Media decoder release error", new RuntimeException(this.shutdownException));
                this.shutdownException = null;
                return VideoCodecStatus.ERROR;
            }
            this.codec = null;
            this.outputThread = null;
            return VideoCodecStatus.OK;
        } finally {
            this.codec = null;
            this.outputThread = null;
        }
    }

    private void stopOnOutputThread(Exception exc) {
        this.outputThreadChecker.checkIsOnValidThread();
        this.running = false;
        this.shutdownException = exc;
    }

    @Override // org.wrtca.api.VideoDecoder
    public VideoCodecStatus decode(EncodedImage encodedImage, VideoDecoder.DecodeInfo decodeInfo) throws MediaCodec.CryptoException {
        int i2;
        int i3;
        VideoCodecStatus videoCodecStatusReinitDecode;
        this.decoderThreadChecker.checkIsOnValidThread();
        if (this.codec == null || this.callback == null) {
            Logging.d(TAG, "decode uninitalized, codec: " + this.codec + ", callback: " + this.callback);
            return VideoCodecStatus.UNINITIALIZED;
        }
        ByteBuffer byteBuffer = encodedImage.buffer;
        if (byteBuffer == null) {
            Logging.e(TAG, "decode() - no input data");
            return VideoCodecStatus.ERR_PARAMETER;
        }
        int iRemaining = byteBuffer.remaining();
        if (iRemaining == 0) {
            Logging.e(TAG, "decode() - input buffer empty");
            return VideoCodecStatus.ERR_PARAMETER;
        }
        synchronized (this.dimensionLock) {
            i2 = this.width;
            i3 = this.height;
        }
        int i4 = encodedImage.encodedWidth;
        int i5 = encodedImage.encodedHeight;
        if (i4 * i5 > 0 && ((i4 != i2 || i5 != i3) && (videoCodecStatusReinitDecode = reinitDecode(i4, i5)) != VideoCodecStatus.OK)) {
            return videoCodecStatusReinitDecode;
        }
        if (this.keyFrameRequired) {
            if (encodedImage.frameType != EncodedImage.FrameType.VideoFrameKey) {
                Logging.e(TAG, "decode() - key frame required first");
                return VideoCodecStatus.NO_OUTPUT;
            }
            if (!encodedImage.completeFrame) {
                Logging.e(TAG, "decode() - complete frame required first");
                return VideoCodecStatus.NO_OUTPUT;
            }
        }
        try {
            int iDequeueInputBuffer = this.codec.dequeueInputBuffer(500000L);
            if (iDequeueInputBuffer < 0) {
                Logging.e(TAG, "decode() - no HW buffers available; decoder falling behind");
                return VideoCodecStatus.ERROR;
            }
            try {
                ByteBuffer byteBuffer2 = this.codec.getInputBuffers()[iDequeueInputBuffer];
                if (byteBuffer2.capacity() < iRemaining) {
                    Logging.e(TAG, "decode() - HW buffer too small");
                    return VideoCodecStatus.ERROR;
                }
                byteBuffer2.put(encodedImage.buffer);
                this.frameInfos.offer(new FrameInfo(SystemClock.elapsedRealtime(), encodedImage.rotation));
                try {
                    this.codec.queueInputBuffer(iDequeueInputBuffer, 0, iRemaining, TimeUnit.NANOSECONDS.toMicros(encodedImage.captureTimeNs), 0);
                    if (this.keyFrameRequired) {
                        this.keyFrameRequired = false;
                    }
                    return VideoCodecStatus.OK;
                } catch (IllegalStateException e2) {
                    Logging.e(TAG, "queueInputBuffer failed", e2);
                    this.frameInfos.pollLast();
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

    @Override // org.wrtca.api.VideoDecoder
    public String getImplementationName() {
        return "HWDecoder";
    }

    @Override // org.wrtca.api.VideoDecoder
    public boolean getPrefersLateDecoding() {
        return true;
    }

    @Override // org.wrtca.api.VideoDecoder
    public VideoCodecStatus initDecode(VideoDecoder.Settings settings, VideoDecoder.Callback callback) {
        this.decoderThreadChecker = new ThreadUtils.ThreadChecker();
        this.callback = callback;
        EglBase.Context context = this.sharedContext;
        if (context != null) {
            this.surfaceTextureHelper = SurfaceTextureHelper.create("decoder-texture-thread", context);
            this.surface = new Surface(this.surfaceTextureHelper.getSurfaceTexture());
            this.surfaceTextureHelper.startListening(this);
        }
        return initDecodeInternal(settings.width, settings.height);
    }

    @Override // org.wrtca.api.SurfaceTextureHelper.OnTextureFrameAvailableListener
    public void onTextureFrameAvailable(int i2, float[] fArr, long j2) {
        VideoFrame videoFrame;
        int iIntValue;
        synchronized (this.renderedTextureMetadataLock) {
            DecodedTextureMetadata decodedTextureMetadata = this.renderedTextureMetadata;
            if (decodedTextureMetadata == null) {
                throw new IllegalStateException("Rendered texture metadata was null in onTextureFrameAvailable.");
            }
            VideoFrame.TextureBuffer textureBufferCreateTextureBuffer = this.surfaceTextureHelper.createTextureBuffer(decodedTextureMetadata.width, decodedTextureMetadata.height, RendererCommon.convertMatrixToAndroidGraphicsMatrix(fArr));
            DecodedTextureMetadata decodedTextureMetadata2 = this.renderedTextureMetadata;
            videoFrame = new VideoFrame(textureBufferCreateTextureBuffer, decodedTextureMetadata2.rotation, decodedTextureMetadata2.presentationTimestampUs * 1000);
            iIntValue = this.renderedTextureMetadata.decodeTimeMs.intValue();
            this.renderedTextureMetadata = null;
        }
        this.callback.onDecodedFrame(videoFrame, Integer.valueOf(iIntValue), null);
        videoFrame.release();
    }

    @Override // org.wrtca.api.VideoDecoder
    public VideoCodecStatus release() {
        Logging.d(TAG, "release");
        VideoCodecStatus videoCodecStatusReleaseInternal = releaseInternal();
        Surface surface = this.surface;
        if (surface != null) {
            surface.release();
            this.surface = null;
            this.surfaceTextureHelper.stopListening();
            this.surfaceTextureHelper.dispose();
            this.surfaceTextureHelper = null;
        }
        synchronized (this.renderedTextureMetadataLock) {
            this.renderedTextureMetadata = null;
        }
        this.callback = null;
        this.frameInfos.clear();
        return videoCodecStatusReleaseInternal;
    }
}
