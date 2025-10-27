package com.google.android.exoplayer2.video;

import android.os.Handler;
import android.os.SystemClock;
import android.view.Surface;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.CryptoConfig;
import com.google.android.exoplayer2.decoder.Decoder;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.decoder.VideoDecoderOutputBuffer;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* loaded from: classes3.dex */
public abstract class DecoderVideoRenderer extends BaseRenderer {
    private static final int REINITIALIZATION_STATE_NONE = 0;
    private static final int REINITIALIZATION_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REINITIALIZATION_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "DecoderVideoRenderer";
    private final long allowedJoiningTimeMs;
    private int buffersInCodecCount;
    private int consecutiveDroppedFrameCount;

    @Nullable
    private Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder;
    protected DecoderCounters decoderCounters;

    @Nullable
    private DrmSession decoderDrmSession;
    private boolean decoderReceivedBuffers;
    private int decoderReinitializationState;
    private long droppedFrameAccumulationStartTimeMs;
    private int droppedFrames;
    private final VideoRendererEventListener.EventDispatcher eventDispatcher;
    private final DecoderInputBuffer flagsOnlyBuffer;
    private final TimedValueQueue<Format> formatQueue;

    @Nullable
    private VideoFrameMetadataListener frameMetadataListener;
    private long initialPositionUs;
    private DecoderInputBuffer inputBuffer;
    private Format inputFormat;
    private boolean inputStreamEnded;
    private long joiningDeadlineMs;
    private long lastRenderTimeUs;
    private final int maxDroppedFramesToNotify;
    private boolean mayRenderFirstFrameAfterEnableIfNotStarted;

    @Nullable
    private Object output;
    private VideoDecoderOutputBuffer outputBuffer;

    @Nullable
    private VideoDecoderOutputBufferRenderer outputBufferRenderer;
    private Format outputFormat;
    private int outputMode;
    private boolean outputStreamEnded;
    private long outputStreamOffsetUs;

    @Nullable
    private Surface outputSurface;
    private boolean renderedFirstFrameAfterEnable;
    private boolean renderedFirstFrameAfterReset;

    @Nullable
    private VideoSize reportedVideoSize;

    @Nullable
    private DrmSession sourceDrmSession;
    private boolean waitingForFirstSampleInFormat;

    public DecoderVideoRenderer(long j2, @Nullable Handler handler, @Nullable VideoRendererEventListener videoRendererEventListener, int i2) {
        super(2);
        this.allowedJoiningTimeMs = j2;
        this.maxDroppedFramesToNotify = i2;
        this.joiningDeadlineMs = C.TIME_UNSET;
        clearReportedVideoSize();
        this.formatQueue = new TimedValueQueue<>();
        this.flagsOnlyBuffer = DecoderInputBuffer.newNoDataInstance();
        this.eventDispatcher = new VideoRendererEventListener.EventDispatcher(handler, videoRendererEventListener);
        this.decoderReinitializationState = 0;
        this.outputMode = -1;
    }

    private void clearRenderedFirstFrame() {
        this.renderedFirstFrameAfterReset = false;
    }

    private void clearReportedVideoSize() {
        this.reportedVideoSize = null;
    }

    private boolean drainOutputBuffer(long j2, long j3) throws ExoPlaybackException, DecoderException {
        if (this.outputBuffer == null) {
            VideoDecoderOutputBuffer videoDecoderOutputBufferDequeueOutputBuffer = this.decoder.dequeueOutputBuffer();
            this.outputBuffer = videoDecoderOutputBufferDequeueOutputBuffer;
            if (videoDecoderOutputBufferDequeueOutputBuffer == null) {
                return false;
            }
            DecoderCounters decoderCounters = this.decoderCounters;
            int i2 = decoderCounters.skippedOutputBufferCount;
            int i3 = videoDecoderOutputBufferDequeueOutputBuffer.skippedOutputBufferCount;
            decoderCounters.skippedOutputBufferCount = i2 + i3;
            this.buffersInCodecCount -= i3;
        }
        if (!this.outputBuffer.isEndOfStream()) {
            boolean zProcessOutputBuffer = processOutputBuffer(j2, j3);
            if (zProcessOutputBuffer) {
                onProcessedOutputBuffer(this.outputBuffer.timeUs);
                this.outputBuffer = null;
            }
            return zProcessOutputBuffer;
        }
        if (this.decoderReinitializationState == 2) {
            releaseDecoder();
            maybeInitDecoder();
        } else {
            this.outputBuffer.release();
            this.outputBuffer = null;
            this.outputStreamEnded = true;
        }
        return false;
    }

    private boolean feedInputBuffer() throws ExoPlaybackException, DecoderException {
        Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.decoder;
        if (decoder == null || this.decoderReinitializationState == 2 || this.inputStreamEnded) {
            return false;
        }
        if (this.inputBuffer == null) {
            DecoderInputBuffer decoderInputBufferDequeueInputBuffer = decoder.dequeueInputBuffer();
            this.inputBuffer = decoderInputBufferDequeueInputBuffer;
            if (decoderInputBufferDequeueInputBuffer == null) {
                return false;
            }
        }
        if (this.decoderReinitializationState == 1) {
            this.inputBuffer.setFlags(4);
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.inputBuffer = null;
            this.decoderReinitializationState = 2;
            return false;
        }
        FormatHolder formatHolder = getFormatHolder();
        int source = readSource(formatHolder, this.inputBuffer, 0);
        if (source == -5) {
            onInputFormatChanged(formatHolder);
            return true;
        }
        if (source != -4) {
            if (source == -3) {
                return false;
            }
            throw new IllegalStateException();
        }
        if (this.inputBuffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            this.decoder.queueInputBuffer(this.inputBuffer);
            this.inputBuffer = null;
            return false;
        }
        if (this.waitingForFirstSampleInFormat) {
            this.formatQueue.add(this.inputBuffer.timeUs, this.inputFormat);
            this.waitingForFirstSampleInFormat = false;
        }
        this.inputBuffer.flip();
        DecoderInputBuffer decoderInputBuffer = this.inputBuffer;
        decoderInputBuffer.format = this.inputFormat;
        onQueueInputBuffer(decoderInputBuffer);
        this.decoder.queueInputBuffer(this.inputBuffer);
        this.buffersInCodecCount++;
        this.decoderReceivedBuffers = true;
        this.decoderCounters.inputBufferCount++;
        this.inputBuffer = null;
        return true;
    }

    private boolean hasOutput() {
        return this.outputMode != -1;
    }

    private static boolean isBufferLate(long j2) {
        return j2 < -30000;
    }

    private static boolean isBufferVeryLate(long j2) {
        return j2 < -500000;
    }

    private void maybeInitDecoder() throws ExoPlaybackException {
        CryptoConfig cryptoConfig;
        if (this.decoder != null) {
            return;
        }
        setDecoderDrmSession(this.sourceDrmSession);
        DrmSession drmSession = this.decoderDrmSession;
        if (drmSession != null) {
            cryptoConfig = drmSession.getCryptoConfig();
            if (cryptoConfig == null && this.decoderDrmSession.getError() == null) {
                return;
            }
        } else {
            cryptoConfig = null;
        }
        try {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.decoder = createDecoder(this.inputFormat, cryptoConfig);
            setDecoderOutputMode(this.outputMode);
            long jElapsedRealtime2 = SystemClock.elapsedRealtime();
            this.eventDispatcher.decoderInitialized(this.decoder.getName(), jElapsedRealtime2, jElapsedRealtime2 - jElapsedRealtime);
            this.decoderCounters.decoderInitCount++;
        } catch (DecoderException e2) {
            Log.e(TAG, "Video codec error", e2);
            this.eventDispatcher.videoCodecError(e2);
            throw createRendererException(e2, this.inputFormat, 4001);
        } catch (OutOfMemoryError e3) {
            throw createRendererException(e3, this.inputFormat, 4001);
        }
    }

    private void maybeNotifyDroppedFrames() {
        if (this.droppedFrames > 0) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.eventDispatcher.droppedFrames(this.droppedFrames, jElapsedRealtime - this.droppedFrameAccumulationStartTimeMs);
            this.droppedFrames = 0;
            this.droppedFrameAccumulationStartTimeMs = jElapsedRealtime;
        }
    }

    private void maybeNotifyRenderedFirstFrame() {
        this.renderedFirstFrameAfterEnable = true;
        if (this.renderedFirstFrameAfterReset) {
            return;
        }
        this.renderedFirstFrameAfterReset = true;
        this.eventDispatcher.renderedFirstFrame(this.output);
    }

    private void maybeNotifyVideoSizeChanged(int i2, int i3) {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null && videoSize.width == i2 && videoSize.height == i3) {
            return;
        }
        VideoSize videoSize2 = new VideoSize(i2, i3);
        this.reportedVideoSize = videoSize2;
        this.eventDispatcher.videoSizeChanged(videoSize2);
    }

    private void maybeRenotifyRenderedFirstFrame() {
        if (this.renderedFirstFrameAfterReset) {
            this.eventDispatcher.renderedFirstFrame(this.output);
        }
    }

    private void maybeRenotifyVideoSizeChanged() {
        VideoSize videoSize = this.reportedVideoSize;
        if (videoSize != null) {
            this.eventDispatcher.videoSizeChanged(videoSize);
        }
    }

    private void onOutputChanged() {
        maybeRenotifyVideoSizeChanged();
        clearRenderedFirstFrame();
        if (getState() == 2) {
            setJoiningDeadlineMs();
        }
    }

    private void onOutputRemoved() {
        clearReportedVideoSize();
        clearRenderedFirstFrame();
    }

    private void onOutputReset() {
        maybeRenotifyVideoSizeChanged();
        maybeRenotifyRenderedFirstFrame();
    }

    private boolean processOutputBuffer(long j2, long j3) throws ExoPlaybackException, DecoderException {
        if (this.initialPositionUs == C.TIME_UNSET) {
            this.initialPositionUs = j2;
        }
        long j4 = this.outputBuffer.timeUs - j2;
        if (!hasOutput()) {
            if (!isBufferLate(j4)) {
                return false;
            }
            skipOutputBuffer(this.outputBuffer);
            return true;
        }
        long j5 = this.outputBuffer.timeUs - this.outputStreamOffsetUs;
        Format formatPollFloor = this.formatQueue.pollFloor(j5);
        if (formatPollFloor != null) {
            this.outputFormat = formatPollFloor;
        }
        long jElapsedRealtime = (SystemClock.elapsedRealtime() * 1000) - this.lastRenderTimeUs;
        boolean z2 = getState() == 2;
        if ((this.renderedFirstFrameAfterEnable ? !this.renderedFirstFrameAfterReset : z2 || this.mayRenderFirstFrameAfterEnableIfNotStarted) || (z2 && shouldForceRenderOutputBuffer(j4, jElapsedRealtime))) {
            renderOutputBuffer(this.outputBuffer, j5, this.outputFormat);
            return true;
        }
        if (!z2 || j2 == this.initialPositionUs || (shouldDropBuffersToKeyframe(j4, j3) && maybeDropBuffersToKeyframe(j2))) {
            return false;
        }
        if (shouldDropOutputBuffer(j4, j3)) {
            dropOutputBuffer(this.outputBuffer);
            return true;
        }
        if (j4 < 30000) {
            renderOutputBuffer(this.outputBuffer, j5, this.outputFormat);
            return true;
        }
        return false;
    }

    private void setDecoderDrmSession(@Nullable DrmSession drmSession) {
        com.google.android.exoplayer2.drm.i.b(this.decoderDrmSession, drmSession);
        this.decoderDrmSession = drmSession;
    }

    private void setJoiningDeadlineMs() {
        this.joiningDeadlineMs = this.allowedJoiningTimeMs > 0 ? SystemClock.elapsedRealtime() + this.allowedJoiningTimeMs : C.TIME_UNSET;
    }

    private void setSourceDrmSession(@Nullable DrmSession drmSession) {
        com.google.android.exoplayer2.drm.i.b(this.sourceDrmSession, drmSession);
        this.sourceDrmSession = drmSession;
    }

    public DecoderReuseEvaluation canReuseDecoder(String str, Format format, Format format2) {
        return new DecoderReuseEvaluation(str, format, format2, 0, 1);
    }

    public abstract Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> createDecoder(Format format, @Nullable CryptoConfig cryptoConfig) throws DecoderException;

    public void dropOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        updateDroppedBufferCounters(1);
        videoDecoderOutputBuffer.release();
    }

    @CallSuper
    public void flushDecoder() throws ExoPlaybackException {
        this.buffersInCodecCount = 0;
        if (this.decoderReinitializationState != 0) {
            releaseDecoder();
            maybeInitDecoder();
            return;
        }
        this.inputBuffer = null;
        VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.outputBuffer;
        if (videoDecoderOutputBuffer != null) {
            videoDecoderOutputBuffer.release();
            this.outputBuffer = null;
        }
        this.decoder.flush();
        this.decoderReceivedBuffers = false;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException {
        if (i2 == 1) {
            setOutput(obj);
        } else if (i2 == 7) {
            this.frameMetadataListener = (VideoFrameMetadataListener) obj;
        } else {
            super.handleMessage(i2, obj);
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        if (this.inputFormat != null && ((isSourceReady() || this.outputBuffer != null) && (this.renderedFirstFrameAfterReset || !hasOutput()))) {
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

    public boolean maybeDropBuffersToKeyframe(long j2) throws ExoPlaybackException {
        int iSkipSource = skipSource(j2);
        if (iSkipSource == 0) {
            return false;
        }
        this.decoderCounters.droppedToKeyframeCount++;
        updateDroppedBufferCounters(this.buffersInCodecCount + iSkipSource);
        flushDecoder();
        return true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onDisabled() {
        this.inputFormat = null;
        clearReportedVideoSize();
        clearRenderedFirstFrame();
        try {
            setSourceDrmSession(null);
            releaseDecoder();
        } finally {
            this.eventDispatcher.disabled(this.decoderCounters);
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onEnabled(boolean z2, boolean z3) throws ExoPlaybackException {
        DecoderCounters decoderCounters = new DecoderCounters();
        this.decoderCounters = decoderCounters;
        this.eventDispatcher.enabled(decoderCounters);
        this.mayRenderFirstFrameAfterEnableIfNotStarted = z3;
        this.renderedFirstFrameAfterEnable = false;
    }

    @CallSuper
    public void onInputFormatChanged(FormatHolder formatHolder) throws ExoPlaybackException {
        this.waitingForFirstSampleInFormat = true;
        Format format = (Format) Assertions.checkNotNull(formatHolder.format);
        setSourceDrmSession(formatHolder.drmSession);
        Format format2 = this.inputFormat;
        this.inputFormat = format;
        Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.decoder;
        if (decoder == null) {
            maybeInitDecoder();
            this.eventDispatcher.inputFormatChanged(this.inputFormat, null);
            return;
        }
        DecoderReuseEvaluation decoderReuseEvaluation = this.sourceDrmSession != this.decoderDrmSession ? new DecoderReuseEvaluation(decoder.getName(), format2, format, 0, 128) : canReuseDecoder(decoder.getName(), format2, format);
        if (decoderReuseEvaluation.result == 0) {
            if (this.decoderReceivedBuffers) {
                this.decoderReinitializationState = 1;
            } else {
                releaseDecoder();
                maybeInitDecoder();
            }
        }
        this.eventDispatcher.inputFormatChanged(this.inputFormat, decoderReuseEvaluation);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long j2, boolean z2) throws ExoPlaybackException {
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        clearRenderedFirstFrame();
        this.initialPositionUs = C.TIME_UNSET;
        this.consecutiveDroppedFrameCount = 0;
        if (this.decoder != null) {
            flushDecoder();
        }
        if (z2) {
            setJoiningDeadlineMs();
        } else {
            this.joiningDeadlineMs = C.TIME_UNSET;
        }
        this.formatQueue.clear();
    }

    @CallSuper
    public void onProcessedOutputBuffer(long j2) {
        this.buffersInCodecCount--;
    }

    public void onQueueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStarted() {
        this.droppedFrames = 0;
        this.droppedFrameAccumulationStartTimeMs = SystemClock.elapsedRealtime();
        this.lastRenderTimeUs = SystemClock.elapsedRealtime() * 1000;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStopped() {
        this.joiningDeadlineMs = C.TIME_UNSET;
        maybeNotifyDroppedFrames();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j2, long j3) throws ExoPlaybackException {
        this.outputStreamOffsetUs = j3;
        super.onStreamChanged(formatArr, j2, j3);
    }

    @CallSuper
    public void releaseDecoder() {
        this.inputBuffer = null;
        this.outputBuffer = null;
        this.decoderReinitializationState = 0;
        this.decoderReceivedBuffers = false;
        this.buffersInCodecCount = 0;
        Decoder<DecoderInputBuffer, ? extends VideoDecoderOutputBuffer, ? extends DecoderException> decoder = this.decoder;
        if (decoder != null) {
            this.decoderCounters.decoderReleaseCount++;
            decoder.release();
            this.eventDispatcher.decoderReleased(this.decoder.getName());
            this.decoder = null;
        }
        setDecoderDrmSession(null);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j2, long j3) throws ExoPlaybackException {
        if (this.outputStreamEnded) {
            return;
        }
        if (this.inputFormat == null) {
            FormatHolder formatHolder = getFormatHolder();
            this.flagsOnlyBuffer.clear();
            int source = readSource(formatHolder, this.flagsOnlyBuffer, 2);
            if (source != -5) {
                if (source == -4) {
                    Assertions.checkState(this.flagsOnlyBuffer.isEndOfStream());
                    this.inputStreamEnded = true;
                    this.outputStreamEnded = true;
                    return;
                }
                return;
            }
            onInputFormatChanged(formatHolder);
        }
        maybeInitDecoder();
        if (this.decoder != null) {
            try {
                TraceUtil.beginSection("drainAndFeed");
                while (drainOutputBuffer(j2, j3)) {
                }
                while (feedInputBuffer()) {
                }
                TraceUtil.endSection();
                this.decoderCounters.ensureUpdated();
            } catch (DecoderException e2) {
                Log.e(TAG, "Video codec error", e2);
                this.eventDispatcher.videoCodecError(e2);
                throw createRendererException(e2, this.inputFormat, 4003);
            }
        }
    }

    public void renderOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer, long j2, Format format) throws DecoderException {
        VideoFrameMetadataListener videoFrameMetadataListener = this.frameMetadataListener;
        if (videoFrameMetadataListener != null) {
            videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j2, System.nanoTime(), format, null);
        }
        this.lastRenderTimeUs = Util.msToUs(SystemClock.elapsedRealtime() * 1000);
        int i2 = videoDecoderOutputBuffer.mode;
        boolean z2 = i2 == 1 && this.outputSurface != null;
        boolean z3 = i2 == 0 && this.outputBufferRenderer != null;
        if (!z3 && !z2) {
            dropOutputBuffer(videoDecoderOutputBuffer);
            return;
        }
        maybeNotifyVideoSizeChanged(videoDecoderOutputBuffer.width, videoDecoderOutputBuffer.height);
        if (z3) {
            this.outputBufferRenderer.setOutputBuffer(videoDecoderOutputBuffer);
        } else {
            renderOutputBufferToSurface(videoDecoderOutputBuffer, this.outputSurface);
        }
        this.consecutiveDroppedFrameCount = 0;
        this.decoderCounters.renderedOutputBufferCount++;
        maybeNotifyRenderedFirstFrame();
    }

    public abstract void renderOutputBufferToSurface(VideoDecoderOutputBuffer videoDecoderOutputBuffer, Surface surface) throws DecoderException;

    public abstract void setDecoderOutputMode(int i2);

    public final void setOutput(@Nullable Object obj) {
        if (obj instanceof Surface) {
            this.outputSurface = (Surface) obj;
            this.outputBufferRenderer = null;
            this.outputMode = 1;
        } else if (obj instanceof VideoDecoderOutputBufferRenderer) {
            this.outputSurface = null;
            this.outputBufferRenderer = (VideoDecoderOutputBufferRenderer) obj;
            this.outputMode = 0;
        } else {
            this.outputSurface = null;
            this.outputBufferRenderer = null;
            this.outputMode = -1;
            obj = null;
        }
        if (this.output == obj) {
            if (obj != null) {
                onOutputReset();
                return;
            }
            return;
        }
        this.output = obj;
        if (obj == null) {
            onOutputRemoved();
            return;
        }
        if (this.decoder != null) {
            setDecoderOutputMode(this.outputMode);
        }
        onOutputChanged();
    }

    public boolean shouldDropBuffersToKeyframe(long j2, long j3) {
        return isBufferVeryLate(j2);
    }

    public boolean shouldDropOutputBuffer(long j2, long j3) {
        return isBufferLate(j2);
    }

    public boolean shouldForceRenderOutputBuffer(long j2, long j3) {
        return isBufferLate(j2) && j3 > 100000;
    }

    public void skipOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        this.decoderCounters.skippedOutputBufferCount++;
        videoDecoderOutputBuffer.release();
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
}
