package com.google.android.exoplayer2.video;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.b2;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.gms.common.Scopes;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class MediaCodecVideoRenderer extends MediaCodecRenderer {
    private static final float INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR = 1.5f;
    private static final String KEY_CROP_BOTTOM = "crop-bottom";
    private static final String KEY_CROP_LEFT = "crop-left";
    private static final String KEY_CROP_RIGHT = "crop-right";
    private static final String KEY_CROP_TOP = "crop-top";
    private static final int[] STANDARD_LONG_EDGE_VIDEO_PX = {R2.attr.iconTint, 1600, R2.attr.ease_round_radius, 1280, 960, R2.attr.buttonTint, 640, R2.attr.bl_checked_gradient_centerY, 480};
    private static final String TAG = "MediaCodecVideoRenderer";
    private static final long TUNNELING_EOS_PRESENTATION_TIME_US = Long.MAX_VALUE;
    private static boolean deviceNeedsSetOutputSurfaceWorkaround;
    private static boolean evaluatedDeviceNeedsSetOutputSurfaceWorkaround;
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private boolean codecHandlesHdr10PlusOutOfBandMetadata;
    private CodecMaxValues codecMaxValues;
    private boolean codecNeedsSetOutputSurfaceWorkaround;
    private int consecutiveDroppedFrameCount;
    private final Context context;
    private int currentHeight;
    private float currentPixelWidthHeightRatio;
    private int currentUnappliedRotationDegrees;
    private int currentWidth;
    private final boolean deviceNeedsNoPostProcessWorkaround;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;

    @Nullable
    private DummySurface dummySurface;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;

    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private final VideoFrameReleaseHelper frameReleaseHelper;
    private boolean haveReportedFirstFrameRenderedForCurrentSurface;
    private long initialPositionUs;
    private long joiningDeadlineMs;
    private long lastBufferPresentationTimeUs;
    private long lastRenderRealtimeUs;
    private final int maxDroppedFramesToNotify;
    private boolean mayRenderFirstFrameAfterEnableIfNotStarted;
    private boolean renderedFirstFrameAfterEnable;
    private boolean renderedFirstFrameAfterReset;

    @Nullable
    private VideoSize reportedVideoSize;
    private int scalingMode;

    @Nullable
    private Surface surface;
    private long totalVideoFrameProcessingOffsetUs;
    private boolean tunneling;
    private int tunnelingAudioSessionId;

    @Nullable
    OnFrameRenderedListenerV23 tunnelingOnFrameRenderedListener;
    private int videoFrameProcessingOffsetCount;

    public static final class CodecMaxValues {
        public final int height;
        public final int inputSize;
        public final int width;

        public CodecMaxValues(int i2, int i3, int i4) {
            this.width = i2;
            this.height = i3;
            this.inputSize = i4;
        }
    }

    @RequiresApi(23)
    public final class OnFrameRenderedListenerV23 implements MediaCodecAdapter.OnFrameRenderedListener, Handler.Callback {
        private static final int HANDLE_FRAME_RENDERED = 0;
        private final Handler handler;

        public OnFrameRenderedListenerV23(MediaCodecAdapter mediaCodecAdapter) {
            Handler handlerCreateHandlerForCurrentLooper = Util.createHandlerForCurrentLooper(this);
            this.handler = handlerCreateHandlerForCurrentLooper;
            mediaCodecAdapter.setOnFrameRenderedListener(this, handlerCreateHandlerForCurrentLooper);
        }

        private void handleFrameRendered(long j2) {
            MediaCodecVideoRenderer mediaCodecVideoRenderer = MediaCodecVideoRenderer.this;
            if (this != mediaCodecVideoRenderer.tunnelingOnFrameRenderedListener) {
                return;
            }
            if (j2 == Long.MAX_VALUE) {
                mediaCodecVideoRenderer.onProcessedTunneledEndOfStream();
                return;
            }
            try {
                mediaCodecVideoRenderer.onProcessedTunneledBuffer(j2);
            } catch (ExoPlaybackException e2) {
                MediaCodecVideoRenderer.this.setPendingPlaybackException(e2);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            handleFrameRendered(Util.toLong(message.arg1, message.arg2));
            return true;
        }

        @Override // com.google.android.exoplayer2.mediacodec.MediaCodecAdapter.OnFrameRenderedListener
        public void onFrameRendered(MediaCodecAdapter mediaCodecAdapter, long j2, long j3) {
            if (Util.SDK_INT >= 30) {
                handleFrameRendered(j2);
            } else {
                this.handler.sendMessageAtFrontOfQueue(Message.obtain(this.handler, 0, (int) (j2 >> 32), (int) j2));
            }
        }
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector) {
        this(context, mediaCodecSelector, 0L);
    }

    private void clearRenderedFirstFrame() {
        MediaCodecAdapter codec;
        this.renderedFirstFrameAfterReset = false;
        if (Util.SDK_INT < 23 || !this.tunneling || (codec = getCodec()) == null) {
            return;
        }
        this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23(codec);
    }

    private void clearReportedVideoSize() {
        this.reportedVideoSize = null;
    }

    @RequiresApi(21)
    private static void configureTunnelingV21(MediaFormat mediaFormat, int i2) {
        mediaFormat.setFeatureEnabled("tunneled-playback", true);
        mediaFormat.setInteger("audio-session-id", i2);
    }

    private static boolean deviceNeedsNoPostProcessWorkaround() {
        return "NVIDIA".equals(Util.MANUFACTURER);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:610:0x0827  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean evaluateDeviceNeedsSetOutputSurfaceWorkaround() {
        /*
            Method dump skipped, instructions count: 3044
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.evaluateDeviceNeedsSetOutputSurfaceWorkaround():boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo r10, com.google.android.exoplayer2.Format r11) {
        /*
            int r0 = r11.width
            int r1 = r11.height
            r2 = -1
            if (r0 == r2) goto Lc2
            if (r1 != r2) goto Lb
            goto Lc2
        Lb:
            java.lang.String r3 = r11.sampleMimeType
            java.lang.String r4 = "video/dolby-vision"
            boolean r4 = r4.equals(r3)
            java.lang.String r5 = "video/avc"
            r6 = 1
            java.lang.String r7 = "video/hevc"
            r8 = 2
            if (r4 == 0) goto L34
            android.util.Pair r11 = com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getCodecProfileAndLevel(r11)
            if (r11 == 0) goto L33
            java.lang.Object r11 = r11.first
            java.lang.Integer r11 = (java.lang.Integer) r11
            int r11 = r11.intValue()
            r3 = 512(0x200, float:7.17E-43)
            if (r11 == r3) goto L31
            if (r11 == r6) goto L31
            if (r11 != r8) goto L33
        L31:
            r3 = r5
            goto L34
        L33:
            r3 = r7
        L34:
            r3.hashCode()
            int r11 = r3.hashCode()
            r4 = 4
            r9 = 3
            switch(r11) {
                case -1664118616: goto L73;
                case -1662541442: goto L6c;
                case 1187890754: goto L61;
                case 1331836730: goto L58;
                case 1599127256: goto L4d;
                case 1599127257: goto L42;
                default: goto L40;
            }
        L40:
            r6 = r2
            goto L7d
        L42:
            java.lang.String r11 = "video/x-vnd.on2.vp9"
            boolean r11 = r3.equals(r11)
            if (r11 != 0) goto L4b
            goto L40
        L4b:
            r6 = 5
            goto L7d
        L4d:
            java.lang.String r11 = "video/x-vnd.on2.vp8"
            boolean r11 = r3.equals(r11)
            if (r11 != 0) goto L56
            goto L40
        L56:
            r6 = r4
            goto L7d
        L58:
            boolean r11 = r3.equals(r5)
            if (r11 != 0) goto L5f
            goto L40
        L5f:
            r6 = r9
            goto L7d
        L61:
            java.lang.String r11 = "video/mp4v-es"
            boolean r11 = r3.equals(r11)
            if (r11 != 0) goto L6a
            goto L40
        L6a:
            r6 = r8
            goto L7d
        L6c:
            boolean r11 = r3.equals(r7)
            if (r11 != 0) goto L7d
            goto L40
        L73:
            java.lang.String r11 = "video/3gpp"
            boolean r11 = r3.equals(r11)
            if (r11 != 0) goto L7c
            goto L40
        L7c:
            r6 = 0
        L7d:
            switch(r6) {
                case 0: goto Lbc;
                case 1: goto Lba;
                case 2: goto Lbc;
                case 3: goto L81;
                case 4: goto Lbc;
                case 5: goto Lba;
                default: goto L80;
            }
        L80:
            return r2
        L81:
            java.lang.String r11 = com.google.android.exoplayer2.util.Util.MODEL
            java.lang.String r3 = "BRAVIA 4K 2015"
            boolean r3 = r3.equals(r11)
            if (r3 != 0) goto Lb9
            java.lang.String r3 = "Amazon"
            java.lang.String r4 = com.google.android.exoplayer2.util.Util.MANUFACTURER
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto Laa
            java.lang.String r3 = "KFSOWI"
            boolean r3 = r3.equals(r11)
            if (r3 != 0) goto Lb9
            java.lang.String r3 = "AFTS"
            boolean r11 = r3.equals(r11)
            if (r11 == 0) goto Laa
            boolean r10 = r10.secure
            if (r10 == 0) goto Laa
            goto Lb9
        Laa:
            r10 = 16
            int r11 = com.google.android.exoplayer2.util.Util.ceilDivide(r0, r10)
            int r0 = com.google.android.exoplayer2.util.Util.ceilDivide(r1, r10)
            int r11 = r11 * r0
            int r11 = r11 * r10
            int r0 = r11 * 16
            goto Lbd
        Lb9:
            return r2
        Lba:
            int r0 = r0 * r1
            goto Lbe
        Lbc:
            int r0 = r0 * r1
        Lbd:
            r4 = r8
        Lbe:
            int r0 = r0 * r9
            int r4 = r4 * r8
            int r0 = r0 / r4
            return r0
        Lc2:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.MediaCodecVideoRenderer.getCodecMaxInputSize(com.google.android.exoplayer2.mediacodec.MediaCodecInfo, com.google.android.exoplayer2.Format):int");
    }

    private static Point getCodecMaxSize(MediaCodecInfo mediaCodecInfo, Format format) {
        int i2 = format.height;
        int i3 = format.width;
        boolean z2 = i2 > i3;
        int i4 = z2 ? i2 : i3;
        if (z2) {
            i2 = i3;
        }
        float f2 = i2 / i4;
        for (int i5 : STANDARD_LONG_EDGE_VIDEO_PX) {
            int i6 = (int) (i5 * f2);
            if (i5 <= i4 || i6 <= i2) {
                break;
            }
            if (Util.SDK_INT >= 21) {
                int i7 = z2 ? i6 : i5;
                if (!z2) {
                    i5 = i6;
                }
                Point pointAlignVideoSizeV21 = mediaCodecInfo.alignVideoSizeV21(i7, i5);
                if (mediaCodecInfo.isVideoSizeAndRateSupportedV21(pointAlignVideoSizeV21.x, pointAlignVideoSizeV21.y, format.frameRate)) {
                    return pointAlignVideoSizeV21;
                }
            } else {
                try {
                    int iCeilDivide = Util.ceilDivide(i5, 16) * 16;
                    int iCeilDivide2 = Util.ceilDivide(i6, 16) * 16;
                    if (iCeilDivide * iCeilDivide2 <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        int i8 = z2 ? iCeilDivide2 : iCeilDivide;
                        if (!z2) {
                            iCeilDivide = iCeilDivide2;
                        }
                        return new Point(i8, iCeilDivide);
                    }
                } catch (MediaCodecUtil.DecoderQueryException unused) {
                }
            }
        }
        return null;
    }

    public static int getMaxInputSize(MediaCodecInfo mediaCodecInfo, Format format) {
        if (format.maxInputSize == -1) {
            return getCodecMaxInputSize(mediaCodecInfo, format);
        }
        int size = format.initializationData.size();
        int length = 0;
        for (int i2 = 0; i2 < size; i2++) {
            length += format.initializationData.get(i2).length;
        }
        return format.maxInputSize + length;
    }

    private static boolean isBufferLate(long j2) {
        return j2 < -30000;
    }

    private static boolean isBufferVeryLate(long j2) {
        return j2 < -500000;
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, jElapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = jElapsedRealtime;
        }
    }

    private void maybeNotifyVideoFrameProcessingOffset() {
        int i2 = this.videoFrameProcessingOffsetCount;
        if (i2 != 0) {
            this.eventDispatcher.reportVideoFrameProcessingOffset(this.totalVideoFrameProcessingOffsetUs, i2);
            this.totalVideoFrameProcessingOffsetUs = 0L;
            this.videoFrameProcessingOffsetCount = 0;
        }
    }

    private void maybeNotifyVideoSizeChanged() {
        int i2 = this.currentWidth;
        if (i2 == -1 && this.currentHeight == -1) {
            return;
        }
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null && videoSize.width == i2 && videoSize.height == this.currentHeight && videoSize.unappliedRotationDegrees == this.currentUnappliedRotationDegrees && videoSize.pixelWidthHeightRatio == this.currentPixelWidthHeightRatio) {
            return;
        }
        VideoSize videoSize2 = new VideoSize(this.currentWidth, this.currentHeight, this.currentUnappliedRotationDegrees, this.currentPixelWidthHeightRatio);
        this.reportedVideoSize = videoSize2;
        this.eventDispatcher.videoSizeChanged(videoSize2);
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.haveReportedFirstFrameRenderedForCurrentSurface) {
            this.eventDispatcher.renderedFirstFrame(this.surface);
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null) {
            this.eventDispatcher.videoSizeChanged(videoSize);
        }
    }

    private void notifyFrameMetadataListener(long j2, long j3, Format format) {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j2, j3, format, getCodecOutputMediaFormat());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProcessedTunneledEndOfStream() {
        setPendingOutputEndOfStream();
    }

    @RequiresApi(17)
    private void releaseDummySurface() {
        Surface surface = this.surface;
        DummySurface dummySurface = this.dummySurface;
        if (surface == dummySurface) {
            this.surface = null;
        }
        dummySurface.release();
        this.dummySurface = null;
    }

    @RequiresApi(29)
    private static void setHdr10PlusInfoV29(MediaCodecAdapter mediaCodecAdapter, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putByteArray("hdr10-plus-info", bArr);
        mediaCodecAdapter.setParameters(bundle);
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.video.MediaCodecVideoRenderer] */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.view.Surface] */
    private void setOutput(@Nullable Object obj) throws ExoPlaybackException {
        DummySurface dummySurfaceNewInstanceV17 = obj instanceof Surface ? (Surface) obj : null;
        if (dummySurfaceNewInstanceV17 == null) {
            DummySurface dummySurface = this.dummySurface;
            if (dummySurface != null) {
                dummySurfaceNewInstanceV17 = dummySurface;
            } else {
                MediaCodecInfo codecInfo = getCodecInfo();
                if (codecInfo != null && shouldUseDummySurface(codecInfo)) {
                    dummySurfaceNewInstanceV17 = DummySurface.newInstanceV17(this.context, codecInfo.secure);
                    this.dummySurface = dummySurfaceNewInstanceV17;
                }
            }
        }
        if (this.surface == dummySurfaceNewInstanceV17) {
            if (dummySurfaceNewInstanceV17 == null || dummySurfaceNewInstanceV17 == this.dummySurface) {
                return;
            }
            maybeRenotifyVideoSizeChanged();
            maybeRenotifyRenderedFirstFrame();
            return;
        }
        this.surface = dummySurfaceNewInstanceV17;
        this.frameReleaseHelper.onSurfaceChanged(dummySurfaceNewInstanceV17);
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        int state = getState();
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            if (Util.SDK_INT < 23 || dummySurfaceNewInstanceV17 == null || this.codecNeedsSetOutputSurfaceWorkaround) {
                releaseCodec();
                maybeInitCodecOrBypass();
            } else {
                setOutputSurfaceV23(codec, dummySurfaceNewInstanceV17);
            }
        }
        if (dummySurfaceNewInstanceV17 == null || dummySurfaceNewInstanceV17 == this.dummySurface) {
            clearReportedVideoSize();
            clearRenderedFirstFrame();
            return;
        }
        maybeRenotifyVideoSizeChanged();
        clearRenderedFirstFrame();
        if (state == 2) {
            setJoiningDeadlineMs();
        }
    }

    private boolean shouldUseDummySurface(MediaCodecInfo mediaCodecInfo) {
        return Util.SDK_INT >= 23 && !this.tunneling && !codecNeedsSetOutputSurfaceWorkaround(mediaCodecInfo.name) && (!mediaCodecInfo.secure || DummySurface.isSecureSupported(this.context));
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public DecoderReuseEvaluation canReuseCodec(MediaCodecInfo mediaCodecInfo, Format format, Format format2) {
        DecoderReuseEvaluation decoderReuseEvaluationCanReuseCodec = mediaCodecInfo.canReuseCodec(format, format2);
        int i2 = decoderReuseEvaluationCanReuseCodec.discardReasons;
        int i3 = format2.width;
        CodecMaxValues codecMaxValues = this.codecMaxValues;
        if (i3 > codecMaxValues.width || format2.height > codecMaxValues.height) {
            i2 |= 256;
        }
        if (getMaxInputSize(mediaCodecInfo, format2) > this.codecMaxValues.inputSize) {
            i2 |= 64;
        }
        int i4 = i2;
        return new DecoderReuseEvaluation(mediaCodecInfo.name, format, format2, i4 != 0 ? 0 : decoderReuseEvaluationCanReuseCodec.result, i4);
    }

    public boolean codecNeedsSetOutputSurfaceWorkaround(String str) {
        if (str.startsWith("OMX.google")) {
            return false;
        }
        synchronized (MediaCodecVideoRenderer.class) {
            if (!evaluatedDeviceNeedsSetOutputSurfaceWorkaround) {
                deviceNeedsSetOutputSurfaceWorkaround = evaluateDeviceNeedsSetOutputSurfaceWorkaround();
                evaluatedDeviceNeedsSetOutputSurfaceWorkaround = true;
            }
        }
        return deviceNeedsSetOutputSurfaceWorkaround;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public MediaCodecDecoderException createDecoderException(Throwable th, @Nullable MediaCodecInfo mediaCodecInfo) {
        return new MediaCodecVideoDecoderException(th, mediaCodecInfo, this.surface);
    }

    public void dropOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i2, long j2) {
        TraceUtil.beginSection("dropVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, false);
        TraceUtil.endSection();
        updateDroppedBufferCounters(1);
    }

    public CodecMaxValues getCodecMaxValues(MediaCodecInfo mediaCodecInfo, Format format, Format[] formatArr) {
        int codecMaxInputSize;
        int iMax = format.width;
        int iMax2 = format.height;
        int maxInputSize = getMaxInputSize(mediaCodecInfo, format);
        if (formatArr.length == 1) {
            if (maxInputSize != -1 && (codecMaxInputSize = getCodecMaxInputSize(mediaCodecInfo, format)) != -1) {
                maxInputSize = Math.min((int) (maxInputSize * INITIAL_FORMAT_MAX_INPUT_SIZE_SCALE_FACTOR), codecMaxInputSize);
            }
            return new CodecMaxValues(iMax, iMax2, maxInputSize);
        }
        int length = formatArr.length;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            Format formatBuild = formatArr[i2];
            if (format.colorInfo != null && formatBuild.colorInfo == null) {
                formatBuild = formatBuild.buildUpon().setColorInfo(format.colorInfo).build();
            }
            if (mediaCodecInfo.canReuseCodec(format, formatBuild).result != 0) {
                int i3 = formatBuild.width;
                z2 |= i3 == -1 || formatBuild.height == -1;
                iMax = Math.max(iMax, i3);
                iMax2 = Math.max(iMax2, formatBuild.height);
                maxInputSize = Math.max(maxInputSize, getMaxInputSize(mediaCodecInfo, formatBuild));
            }
        }
        if (z2) {
            StringBuilder sb = new StringBuilder(66);
            sb.append("Resolutions unknown. Codec max resolution: ");
            sb.append(iMax);
            sb.append("x");
            sb.append(iMax2);
            Log.w(TAG, sb.toString());
            Point codecMaxSize = getCodecMaxSize(mediaCodecInfo, format);
            if (codecMaxSize != null) {
                iMax = Math.max(iMax, codecMaxSize.x);
                iMax2 = Math.max(iMax2, codecMaxSize.y);
                maxInputSize = Math.max(maxInputSize, getCodecMaxInputSize(mediaCodecInfo, format.buildUpon().setWidth(iMax).setHeight(iMax2).build()));
                StringBuilder sb2 = new StringBuilder(57);
                sb2.append("Codec max resolution adjusted to: ");
                sb2.append(iMax);
                sb2.append("x");
                sb2.append(iMax2);
                Log.w(TAG, sb2.toString());
            }
        }
        return new CodecMaxValues(iMax, iMax2, maxInputSize);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public boolean getCodecNeedsEosPropagation() {
        return this.tunneling && Util.SDK_INT < 23;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public float getCodecOperatingRateV23(float f2, Format format, Format[] formatArr) {
        float fMax = -1.0f;
        for (Format format2 : formatArr) {
            float f3 = format2.frameRate;
            if (f3 != -1.0f) {
                fMax = Math.max(fMax, f3);
            }
        }
        if (fMax == -1.0f) {
            return -1.0f;
        }
        return fMax * f2;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z2) throws MediaCodecUtil.DecoderQueryException {
        return getDecoderInfos(mediaCodecSelector, format, z2, this.tunneling);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @TargetApi(17)
    public MediaCodecAdapter.Configuration getMediaCodecConfiguration(MediaCodecInfo mediaCodecInfo, Format format, @Nullable MediaCrypto mediaCrypto, float f2) {
        DummySurface dummySurface = this.dummySurface;
        if (dummySurface != null && dummySurface.secure != mediaCodecInfo.secure) {
            releaseDummySurface();
        }
        String str = mediaCodecInfo.codecMimeType;
        CodecMaxValues codecMaxValues = getCodecMaxValues(mediaCodecInfo, format, getStreamFormats());
        this.codecMaxValues = codecMaxValues;
        MediaFormat mediaFormat = getMediaFormat(format, str, codecMaxValues, f2, this.deviceNeedsNoPostProcessWorkaround, this.tunneling ? this.tunnelingAudioSessionId : 0);
        if (this.surface == null) {
            if (!shouldUseDummySurface(mediaCodecInfo)) {
                throw new IllegalStateException();
            }
            if (this.dummySurface == null) {
                this.dummySurface = DummySurface.newInstanceV17(this.context, mediaCodecInfo.secure);
            }
            this.surface = this.dummySurface;
        }
        return MediaCodecAdapter.Configuration.createForVideoDecoding(mediaCodecInfo, mediaFormat, format, this.surface, mediaCrypto);
    }

    @SuppressLint({"InlinedApi"})
    @TargetApi(21)
    public MediaFormat getMediaFormat(Format format, String str, CodecMaxValues codecMaxValues, float f2, boolean z2, int i2) {
        Pair<Integer, Integer> codecProfileAndLevel;
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString(IMediaFormat.KEY_MIME, str);
        mediaFormat.setInteger("width", format.width);
        mediaFormat.setInteger("height", format.height);
        MediaFormatUtil.setCsdBuffers(mediaFormat, format.initializationData);
        MediaFormatUtil.maybeSetFloat(mediaFormat, "frame-rate", format.frameRate);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "rotation-degrees", format.rotationDegrees);
        MediaFormatUtil.maybeSetColorInfo(mediaFormat, format.colorInfo);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(format.sampleMimeType) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            MediaFormatUtil.maybeSetInteger(mediaFormat, Scopes.PROFILE, ((Integer) codecProfileAndLevel.first).intValue());
        }
        mediaFormat.setInteger("max-width", codecMaxValues.width);
        mediaFormat.setInteger("max-height", codecMaxValues.height);
        MediaFormatUtil.maybeSetInteger(mediaFormat, "max-input-size", codecMaxValues.inputSize);
        if (Util.SDK_INT >= 23) {
            mediaFormat.setInteger(RemoteMessageConst.Notification.PRIORITY, 0);
            if (f2 != -1.0f) {
                mediaFormat.setFloat("operating-rate", f2);
            }
        }
        if (z2) {
            mediaFormat.setInteger("no-post-process", 1);
            mediaFormat.setInteger("auto-frc", 0);
        }
        if (i2 != 0) {
            configureTunnelingV21(mediaFormat, i2);
        }
        return mediaFormat;
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    public Surface getSurface() {
        return this.surface;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @TargetApi(29)
    public void handleInputBufferSupplementalData(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        if (this.codecHandlesHdr10PlusOutOfBandMetadata) {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(decoderInputBuffer.supplementalData);
            if (byteBuffer.remaining() >= 7) {
                byte b3 = byteBuffer.get();
                short s2 = byteBuffer.getShort();
                short s3 = byteBuffer.getShort();
                byte b4 = byteBuffer.get();
                byte b5 = byteBuffer.get();
                byteBuffer.position(0);
                if (b3 == -75 && s2 == 60 && s3 == 1 && b4 == 4 && b5 == 0) {
                    byte[] bArr = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bArr);
                    byteBuffer.position(0);
                    setHdr10PlusInfoV29(getCodec(), bArr);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException {
        if (i2 == 1) {
            setOutput(obj);
            return;
        }
        if (i2 == 7) {
            this.frameMetadataListener = (VideoFrameMetadataListener) obj;
            return;
        }
        if (i2 == 10) {
            int iIntValue = ((Integer) obj).intValue();
            if (this.tunnelingAudioSessionId != iIntValue) {
                this.tunnelingAudioSessionId = iIntValue;
                if (this.tunneling) {
                    releaseCodec();
                    return;
                }
                return;
            }
            return;
        }
        if (i2 != 4) {
            if (i2 != 5) {
                super.handleMessage(i2, obj);
                return;
            } else {
                this.frameReleaseHelper.setChangeFrameRateStrategy(((Integer) obj).intValue());
                return;
            }
        }
        this.scalingMode = ((Integer) obj).intValue();
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            codec.setVideoScalingMode(this.scalingMode);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        DummySurface dummySurface;
        if (super.isReady() && (this.renderedFirstFrameAfterReset || (((dummySurface = this.dummySurface) != null && this.surface == dummySurface) || getCodec() == null || this.tunneling))) {
            this.joiningDeadlineMs = C.TIME_UNSET;
            return true;
        }
        if (this.joiningDeadlineMs == C.TIME_UNSET) {
            return false;
        }
        if (SystemClock.elapsedRealtime() < this.joiningDeadlineMs) {
            return true;
        }
        this.joiningDeadlineMs = C.TIME_UNSET;
        return false;
    }

    public boolean maybeDropBuffersToKeyframe(long j2, boolean z2) throws ExoPlaybackException {
        int iSkipSource = skipSource(j2);
        if (iSkipSource == 0) {
            return false;
        }
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.droppedToKeyframeCount++;
        int i2 = this.buffersInCodecCount + iSkipSource;
        if (z2) {
            decoderCounters.skippedOutputBufferCount += i2;
        } else {
            updateDroppedBufferCounters(i2);
        }
        flushOrReinitializeCodec();
        return true;
    }

    public void maybeNotifyRenderedFirstFrame() {
        this.renderedFirstFrameAfterEnable = true;
        if (this.renderedFirstFrameAfterReset) {
            return;
        }
        this.renderedFirstFrameAfterReset = true;
        this.eventDispatcher.renderedFirstFrame(this.surface);
        this.haveReportedFirstFrameRenderedForCurrentSurface = true;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onCodecError(Exception exc) {
        Log.e(TAG, "Video codec error", exc);
        this.eventDispatcher.videoCodecError(exc);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onCodecInitialized(String str, long j2, long j3) {
        this.eventDispatcher.decoderInitialized(str, j2, j3);
        this.codecNeedsSetOutputSurfaceWorkaround = codecNeedsSetOutputSurfaceWorkaround(str);
        this.codecHandlesHdr10PlusOutOfBandMetadata = ((MediaCodecInfo) Assertions.checkNotNull(getCodecInfo())).isHdr10PlusOutOfBandMetadataSupported();
        if (Util.SDK_INT < 23 || !this.tunneling) {
            return;
        }
        this.tunnelingOnFrameRenderedListener = new OnFrameRenderedListenerV23((MediaCodecAdapter) Assertions.checkNotNull(getCodec()));
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onCodecReleased(String str) {
        this.eventDispatcher.decoderReleased(str);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onDisabled() {
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        this.haveReportedFirstFrameRenderedForCurrentSurface = false;
        this.frameReleaseHelper.onDisabled();
        this.tunnelingOnFrameRenderedListener = null;
        try {
            super.onDisabled();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onEnabled(boolean z2, boolean z3) throws ExoPlaybackException {
        super.onEnabled(z2, z3);
        boolean z4 = getConfiguration().tunneling;
        Assertions.checkState((z4 && this.tunnelingAudioSessionId == 0) ? false : true);
        if (this.tunneling != z4) {
            this.tunneling = z4;
            releaseCodec();
        }
        this.eventDispatcher.enabled(this.decoderCounters);
        this.frameReleaseHelper.onEnabled();
        this.mayRenderFirstFrameAfterEnableIfNotStarted = z3;
        this.renderedFirstFrameAfterEnable = false;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @Nullable
    public DecoderReuseEvaluation onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        DecoderReuseEvaluation decoderReuseEvaluationOnInputFormatChanged = super.onInputFormatChanged(formatHolder);
        this.eventDispatcher.inputFormatChanged(formatHolder.format, decoderReuseEvaluationOnInputFormatChanged);
        return decoderReuseEvaluationOnInputFormatChanged;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onOutputFormatChanged(Format format, @Nullable MediaFormat mediaFormat) {
        MediaCodecAdapter codec = getCodec();
        if (codec != null) {
            codec.setVideoScalingMode(this.scalingMode);
        }
        if (this.tunneling) {
            this.currentWidth = format.width;
            this.currentHeight = format.height;
        } else {
            Assertions.checkNotNull(mediaFormat);
            boolean z2 = mediaFormat.containsKey(KEY_CROP_RIGHT) && mediaFormat.containsKey(KEY_CROP_LEFT) && mediaFormat.containsKey(KEY_CROP_BOTTOM) && mediaFormat.containsKey(KEY_CROP_TOP);
            this.currentWidth = z2 ? (mediaFormat.getInteger(KEY_CROP_RIGHT) - mediaFormat.getInteger(KEY_CROP_LEFT)) + 1 : mediaFormat.getInteger("width");
            this.currentHeight = z2 ? (mediaFormat.getInteger(KEY_CROP_BOTTOM) - mediaFormat.getInteger(KEY_CROP_TOP)) + 1 : mediaFormat.getInteger("height");
        }
        float f2 = format.pixelWidthHeightRatio;
        this.currentPixelWidthHeightRatio = f2;
        if (Util.SDK_INT >= 21) {
            int i2 = format.rotationDegrees;
            if (i2 == 90 || i2 == 270) {
                int i3 = this.currentWidth;
                this.currentWidth = this.currentHeight;
                this.currentHeight = i3;
                this.currentPixelWidthHeightRatio = 1.0f / f2;
            }
        } else {
            this.currentUnappliedRotationDegrees = format.rotationDegrees;
        }
        this.frameReleaseHelper.onFormatChanged(format.frameRate);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long j2, boolean z2) throws ExoPlaybackException {
        super.onPositionReset(j2, z2);
        clearRenderedFirstFrame();
        this.frameReleaseHelper.onPositionReset();
        this.lastBufferPresentationTimeUs = C.TIME_UNSET;
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        if (z2) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void onProcessedOutputBuffer(long j2) {
        super.onProcessedOutputBuffer(j2);
        if (this.tunneling) {
            return;
        }
        this.buffersInCodecCount--;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public void onProcessedStreamChange() {
        super.onProcessedStreamChange();
        clearRenderedFirstFrame();
    }

    public void onProcessedTunneledBuffer(long j2) throws ExoPlaybackException {
        updateOutputFormatForTime(j2);
        maybeNotifyVideoSizeChanged();
        this.decoderCounters.renderedOutputBufferCount++;
        maybeNotifyRenderedFirstFrame();
        onProcessedOutputBuffer(j2);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) throws ExoPlaybackException {
        boolean z2 = this.tunneling;
        if (!z2) {
            this.buffersInCodecCount++;
        }
        if (Util.SDK_INT >= 23 || !z2) {
            return;
        }
        onProcessedTunneledBuffer(decoderInputBuffer.timeUs);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    @TargetApi(17)
    public void onReset() {
        try {
            super.onReset();
        } finally {
            if (this.dummySurface != null) {
                releaseDummySurface();
            }
        }
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onStarted() {
        super.onStarted();
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.totalVideoFrameProcessingOffsetUs = 0L;
        this.videoFrameProcessingOffsetCount = 0;
        this.frameReleaseHelper.onStarted();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer
    public void onStopped() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
        maybeNotifyVideoFrameProcessingOffset();
        this.frameReleaseHelper.onStopped();
        super.onStopped();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public boolean processOutputBuffer(long j2, long j3, @Nullable MediaCodecAdapter mediaCodecAdapter, @Nullable ByteBuffer byteBuffer, int i2, int i3, int i4, long j4, boolean z2, boolean z3, Format format) throws ExoPlaybackException, InterruptedException {
        boolean z4;
        long j5;
        Assertions.checkNotNull(mediaCodecAdapter);
        if (this.initialPositionUs == C.TIME_UNSET) {
            this.initialPositionUs = j2;
        }
        if (j4 != this.lastBufferPresentationTimeUs) {
            this.frameReleaseHelper.onNextFrame(j4);
            this.lastBufferPresentationTimeUs = j4;
        }
        long outputStreamOffsetUs = getOutputStreamOffsetUs();
        long j6 = j4 - outputStreamOffsetUs;
        if (z2 && !z3) {
            skipOutputBuffer(mediaCodecAdapter, i2, j6);
            return true;
        }
        double playbackSpeed = getPlaybackSpeed();
        boolean z5 = getState() == 2;
        long jElapsedRealtime = SystemClock.elapsedRealtime() * 1000;
        long j7 = (long) ((j4 - j2) / playbackSpeed);
        if (z5) {
            j7 -= jElapsedRealtime - j3;
        }
        if (this.surface == this.dummySurface) {
            if (!isBufferLate(j7)) {
                return false;
            }
            skipOutputBuffer(mediaCodecAdapter, i2, j6);
            updateVideoFrameProcessingOffsetCounters(j7);
            return true;
        }
        long j8 = jElapsedRealtime - this.lastRenderRealtimeUs;
        if (this.renderedFirstFrameAfterEnable ? this.renderedFirstFrameAfterReset : !(z5 || this.mayRenderFirstFrameAfterEnableIfNotStarted)) {
            j5 = j8;
            z4 = false;
        } else {
            z4 = true;
            j5 = j8;
        }
        if (this.joiningDeadlineMs == C.TIME_UNSET && j2 >= outputStreamOffsetUs && (z4 || (z5 && shouldForceRenderOutputBuffer(j7, j5)))) {
            long jNanoTime = System.nanoTime();
            notifyFrameMetadataListener(j6, jNanoTime, format);
            if (Util.SDK_INT >= 21) {
                renderOutputBufferV21(mediaCodecAdapter, i2, j6, jNanoTime);
            } else {
                renderOutputBuffer(mediaCodecAdapter, i2, j6);
            }
            updateVideoFrameProcessingOffsetCounters(j7);
            return true;
        }
        if (z5 && j2 != this.initialPositionUs) {
            long jNanoTime2 = System.nanoTime();
            long jAdjustReleaseTime = this.frameReleaseHelper.adjustReleaseTime((j7 * 1000) + jNanoTime2);
            long j9 = (jAdjustReleaseTime - jNanoTime2) / 1000;
            boolean z6 = this.joiningDeadlineMs != C.TIME_UNSET;
            if (shouldDropBuffersToKeyframe(j9, j3, z3) && maybeDropBuffersToKeyframe(j2, z6)) {
                return false;
            }
            if (shouldDropOutputBuffer(j9, j3, z3)) {
                if (z6) {
                    skipOutputBuffer(mediaCodecAdapter, i2, j6);
                } else {
                    dropOutputBuffer(mediaCodecAdapter, i2, j6);
                }
                updateVideoFrameProcessingOffsetCounters(j9);
                return true;
            }
            if (Util.SDK_INT >= 21) {
                if (j9 < 50000) {
                    notifyFrameMetadataListener(j6, jAdjustReleaseTime, format);
                    renderOutputBufferV21(mediaCodecAdapter, i2, j6, jAdjustReleaseTime);
                    updateVideoFrameProcessingOffsetCounters(j9);
                    return true;
                }
            } else if (j9 < 30000) {
                if (j9 > 11000) {
                    try {
                        Thread.sleep((j9 - com.heytap.mcssdk.constant.a.f7153q) / 1000);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
                notifyFrameMetadataListener(j6, jAdjustReleaseTime, format);
                renderOutputBuffer(mediaCodecAdapter, i2, j6);
                updateVideoFrameProcessingOffsetCounters(j9);
                return true;
            }
        }
        return false;
    }

    public void renderOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i2, long j2) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, true);
        TraceUtil.endSection();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    @RequiresApi(21)
    public void renderOutputBufferV21(MediaCodecAdapter mediaCodecAdapter, int i2, long j2, long j3) {
        maybeNotifyVideoSizeChanged();
        TraceUtil.beginSection("releaseOutputBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, j3);
        TraceUtil.endSection();
        this.lastRenderRealtimeUs = SystemClock.elapsedRealtime() * 1000;
        this.decoderCounters.renderedOutputBufferCount++;
        this.consecutiveDroppedFrameCount = 0;
        maybeNotifyRenderedFirstFrame();
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    @CallSuper
    public void resetCodecStateForFlush() {
        super.resetCodecStateForFlush();
        this.buffersInCodecCount = 0;
    }

    @RequiresApi(23)
    public void setOutputSurfaceV23(MediaCodecAdapter mediaCodecAdapter, Surface surface) {
        mediaCodecAdapter.setOutputSurface(surface);
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer, com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public void setPlaybackSpeed(float f2, float f3) throws ExoPlaybackException {
        super.setPlaybackSpeed(f2, f3);
        this.frameReleaseHelper.onPlaybackSpeed(f2);
    }

    public boolean shouldDropBuffersToKeyframe(long j2, long j3, boolean z2) {
        return isBufferVeryLate(j2) && !z2;
    }

    public boolean shouldDropOutputBuffer(long j2, long j3, boolean z2) {
        return isBufferLate(j2) && !z2;
    }

    public boolean shouldForceRenderOutputBuffer(long j2, long j3) {
        return isBufferLate(j2) && j3 > 100000;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public boolean shouldInitCodec(MediaCodecInfo mediaCodecInfo) {
        return this.surface != null || shouldUseDummySurface(mediaCodecInfo);
    }

    public void skipOutputBuffer(MediaCodecAdapter mediaCodecAdapter, int i2, long j2) {
        TraceUtil.beginSection("skipVideoBuffer");
        mediaCodecAdapter.releaseOutputBuffer(i2, false);
        TraceUtil.endSection();
        this.decoderCounters.skippedOutputBufferCount++;
    }

    @Override // com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
    public int supportsFormat(MediaCodecSelector mediaCodecSelector, Format format) throws MediaCodecUtil.DecoderQueryException {
        int i2 = 0;
        if (!MimeTypes.isVideo(format.sampleMimeType)) {
            return b2.a(0);
        }
        boolean z2 = format.drmInitData != null;
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mediaCodecSelector, format, z2, false);
        if (z2 && decoderInfos.isEmpty()) {
            decoderInfos = getDecoderInfos(mediaCodecSelector, format, false, false);
        }
        if (decoderInfos.isEmpty()) {
            return b2.a(1);
        }
        if (!MediaCodecRenderer.supportsFormatDrm(format)) {
            return b2.a(2);
        }
        MediaCodecInfo mediaCodecInfo = decoderInfos.get(0);
        boolean zIsFormatSupported = mediaCodecInfo.isFormatSupported(format);
        int i3 = mediaCodecInfo.isSeamlessAdaptationSupported(format) ? 16 : 8;
        if (zIsFormatSupported) {
            List<MediaCodecInfo> decoderInfos2 = getDecoderInfos(mediaCodecSelector, format, z2, true);
            if (!decoderInfos2.isEmpty()) {
                MediaCodecInfo mediaCodecInfo2 = decoderInfos2.get(0);
                if (mediaCodecInfo2.isFormatSupported(format) && mediaCodecInfo2.isSeamlessAdaptationSupported(format)) {
                    i2 = 32;
                }
            }
        }
        return b2.b(zIsFormatSupported ? 4 : 3, i3, i2);
    }

    public void updateDroppedBufferCounters(int i2) {
        DecoderCounters decoderCounters = this.decoderCounters;
        decoderCounters.droppedBufferCount += i2;
        this.droppedFrames += i2;
        int i3 = this.consecutiveDroppedFrameCount + i2;
        this.consecutiveDroppedFrameCount = i3;
        decoderCounters.maxConsecutiveDroppedBufferCount = Math.max(i3, decoderCounters.maxConsecutiveDroppedBufferCount);
        int i4 = this.maxDroppedFramesToNotify;
        if (i4 <= 0 || this.droppedFrames < i4) {
            return;
        }
        maybeNotifyDroppedFrames();
    }

    public void updateVideoFrameProcessingOffsetCounters(long j2) {
        this.decoderCounters.addVideoFrameProcessingOffset(j2);
        this.totalVideoFrameProcessingOffsetUs += j2;
        this.videoFrameProcessingOffsetCount++;
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j2) {
        this(context, mediaCodecSelector, j2, null, null, 0);
    }

    private static List<MediaCodecInfo> getDecoderInfos(MediaCodecSelector mediaCodecSelector, Format format, boolean z2, boolean z3) throws MediaCodecUtil.DecoderQueryException {
        Pair<Integer, Integer> codecProfileAndLevel;
        String str = format.sampleMimeType;
        if (str == null) {
            return Collections.emptyList();
        }
        List<MediaCodecInfo> decoderInfosSortedByFormatSupport = MediaCodecUtil.getDecoderInfosSortedByFormatSupport(mediaCodecSelector.getDecoderInfos(str, z2, z3), format);
        if (MimeTypes.VIDEO_DOLBY_VISION.equals(str) && (codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format)) != null) {
            int iIntValue = ((Integer) codecProfileAndLevel.first).intValue();
            if (iIntValue == 16 || iIntValue == 256) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H265, z2, z3));
            } else if (iIntValue == 512) {
                decoderInfosSortedByFormatSupport.addAll(mediaCodecSelector.getDecoderInfos(MimeTypes.VIDEO_H264, z2, z3));
            }
        }
        return Collections.unmodifiableList(decoderInfosSortedByFormatSupport);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, j2, false, handler, videoRendererEventListener, i2, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        this(context, MediaCodecAdapter.Factory.DEFAULT, mediaCodecSelector, j2, z2, handler, videoRendererEventListener, i2, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        this(context, factory, mediaCodecSelector, j2, z2, handler, videoRendererEventListener, i2, 30.0f);
    }

    public MediaCodecVideoRenderer(Context context, MediaCodecAdapter.Factory factory, MediaCodecSelector mediaCodecSelector, long j2, boolean z2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2, float f2) {
        super(2, factory, mediaCodecSelector, z2, f2);
        this.allowedJoiningTimeMs = j2;
        this.maxDroppedFramesToNotify = i2;
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.frameReleaseHelper = new VideoFrameReleaseHelper(applicationContext);
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
        this.deviceNeedsNoPostProcessWorkaround = deviceNeedsNoPostProcessWorkaround();
        this.joiningDeadlineMs = C.TIME_UNSET;
        this.currentWidth = -1;
        this.currentHeight = -1;
        this.currentPixelWidthHeightRatio = -1.0f;
        this.scalingMode = 1;
        this.tunnelingAudioSessionId = 0;
        clearReportedVideoSize();
    }
}
