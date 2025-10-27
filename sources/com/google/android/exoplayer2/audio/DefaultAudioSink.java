package com.google.android.exoplayer2.audio;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.PlaybackParams;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.AudioTrackPositionTracker;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class DefaultAudioSink implements AudioSink {
    private static final int AC3_BUFFER_MULTIPLICATION_FACTOR = 2;
    private static final int AUDIO_TRACK_RETRY_DURATION_MS = 100;
    private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
    public static final float DEFAULT_PLAYBACK_SPEED = 1.0f;
    private static final boolean DEFAULT_SKIP_SILENCE = false;
    private static final int ERROR_NATIVE_DEAD_OBJECT = -32;
    private static final long MAX_BUFFER_DURATION_US = 750000;
    public static final float MAX_PITCH = 8.0f;
    public static final float MAX_PLAYBACK_SPEED = 8.0f;
    private static final long MIN_BUFFER_DURATION_US = 250000;
    public static final float MIN_PITCH = 0.1f;
    public static final float MIN_PLAYBACK_SPEED = 0.1f;
    private static final long OFFLOAD_BUFFER_DURATION_US = 50000000;
    public static final int OFFLOAD_MODE_DISABLED = 0;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_DISABLED = 3;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_NOT_REQUIRED = 2;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_REQUIRED = 1;
    private static final int OUTPUT_MODE_OFFLOAD = 1;
    private static final int OUTPUT_MODE_PASSTHROUGH = 2;
    private static final int OUTPUT_MODE_PCM = 0;
    private static final long PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final String TAG = "DefaultAudioSink";
    public static boolean failOnSpuriousAudioTimestamp = false;
    private AudioProcessor[] activeAudioProcessors;

    @Nullable
    private MediaPositionParameters afterDrainParameters;
    private AudioAttributes audioAttributes;

    @Nullable
    private final AudioCapabilities audioCapabilities;
    private final AudioProcessorChain audioProcessorChain;
    private int audioSessionId;

    @Nullable
    private AudioTrack audioTrack;
    private PlaybackParameters audioTrackPlaybackParameters;
    private final AudioTrackPositionTracker audioTrackPositionTracker;
    private AuxEffectInfo auxEffectInfo;

    @Nullable
    private ByteBuffer avSyncHeader;
    private int bytesUntilNextAvSync;
    private final ChannelMappingAudioProcessor channelMappingAudioProcessor;
    private Configuration configuration;
    private int drainingAudioProcessorIndex;
    private final boolean enableAudioTrackPlaybackParams;
    private final boolean enableFloatOutput;
    private boolean externalAudioSessionIdProvided;
    private int framesPerEncodedSample;
    private boolean handledEndOfStream;
    private final PendingExceptionHolder<AudioSink.InitializationException> initializationExceptionPendingExceptionHolder;

    @Nullable
    private ByteBuffer inputBuffer;
    private int inputBufferAccessUnitCount;
    private boolean isWaitingForOffloadEndOfStreamHandled;
    private long lastFeedElapsedRealtimeMs;

    @Nullable
    private AudioSink.Listener listener;
    private MediaPositionParameters mediaPositionParameters;
    private final ArrayDeque<MediaPositionParameters> mediaPositionParametersCheckpoints;
    private boolean offloadDisabledUntilNextConfiguration;
    private final int offloadMode;
    private StreamEventCallbackV29 offloadStreamEventCallbackV29;

    @Nullable
    private ByteBuffer outputBuffer;
    private ByteBuffer[] outputBuffers;

    @Nullable
    private Configuration pendingConfiguration;
    private boolean playing;
    private byte[] preV21OutputBuffer;
    private int preV21OutputBufferOffset;
    private final ConditionVariable releasingConditionVariable;
    private long startMediaTimeUs;
    private boolean startMediaTimeUsNeedsInit;
    private boolean startMediaTimeUsNeedsSync;
    private boolean stoppedAudioTrack;
    private long submittedEncodedFrames;
    private long submittedPcmBytes;
    private final AudioProcessor[] toFloatPcmAvailableAudioProcessors;
    private final AudioProcessor[] toIntPcmAvailableAudioProcessors;
    private final TrimmingAudioProcessor trimmingAudioProcessor;
    private boolean tunneling;
    private float volume;
    private final PendingExceptionHolder<AudioSink.WriteException> writeExceptionPendingExceptionHolder;
    private long writtenEncodedFrames;
    private long writtenPcmBytes;

    public interface AudioProcessorChain {
        PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters);

        boolean applySkipSilenceEnabled(boolean z2);

        AudioProcessor[] getAudioProcessors();

        long getMediaDuration(long j2);

        long getSkippedOutputFrameCount();
    }

    public static final class Configuration {
        public final AudioProcessor[] availableAudioProcessors;
        public final int bufferSize;
        public final Format inputFormat;
        public final int inputPcmFrameSize;
        public final int outputChannelConfig;
        public final int outputEncoding;
        public final int outputMode;
        public final int outputPcmFrameSize;
        public final int outputSampleRate;

        public Configuration(Format format, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z2, AudioProcessor[] audioProcessorArr) {
            this.inputFormat = format;
            this.inputPcmFrameSize = i2;
            this.outputMode = i3;
            this.outputPcmFrameSize = i4;
            this.outputSampleRate = i5;
            this.outputChannelConfig = i6;
            this.outputEncoding = i7;
            this.availableAudioProcessors = audioProcessorArr;
            this.bufferSize = computeBufferSize(i8, z2);
        }

        private int computeBufferSize(int i2, boolean z2) {
            if (i2 != 0) {
                return i2;
            }
            int i3 = this.outputMode;
            if (i3 == 0) {
                return getPcmDefaultBufferSize(z2 ? 8.0f : 1.0f);
            }
            if (i3 == 1) {
                return getEncodedDefaultBufferSize(DefaultAudioSink.OFFLOAD_BUFFER_DURATION_US);
            }
            if (i3 == 2) {
                return getEncodedDefaultBufferSize(250000L);
            }
            throw new IllegalStateException();
        }

        private AudioTrack createAudioTrack(boolean z2, AudioAttributes audioAttributes, int i2) {
            int i3 = Util.SDK_INT;
            return i3 >= 29 ? createAudioTrackV29(z2, audioAttributes, i2) : i3 >= 21 ? createAudioTrackV21(z2, audioAttributes, i2) : createAudioTrackV9(audioAttributes, i2);
        }

        @RequiresApi(21)
        private AudioTrack createAudioTrackV21(boolean z2, AudioAttributes audioAttributes, int i2) {
            return new AudioTrack(getAudioTrackAttributesV21(audioAttributes, z2), DefaultAudioSink.getAudioFormat(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding), this.bufferSize, 1, i2);
        }

        @RequiresApi(29)
        private AudioTrack createAudioTrackV29(boolean z2, AudioAttributes audioAttributes, int i2) {
            return new AudioTrack.Builder().setAudioAttributes(getAudioTrackAttributesV21(audioAttributes, z2)).setAudioFormat(DefaultAudioSink.getAudioFormat(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding)).setTransferMode(1).setBufferSizeInBytes(this.bufferSize).setSessionId(i2).setOffloadedPlayback(this.outputMode == 1).build();
        }

        private AudioTrack createAudioTrackV9(AudioAttributes audioAttributes, int i2) {
            int streamTypeForAudioUsage = Util.getStreamTypeForAudioUsage(audioAttributes.usage);
            return i2 == 0 ? new AudioTrack(streamTypeForAudioUsage, this.outputSampleRate, this.outputChannelConfig, this.outputEncoding, this.bufferSize, 1) : new AudioTrack(streamTypeForAudioUsage, this.outputSampleRate, this.outputChannelConfig, this.outputEncoding, this.bufferSize, 1, i2);
        }

        @RequiresApi(21)
        private static android.media.AudioAttributes getAudioTrackAttributesV21(AudioAttributes audioAttributes, boolean z2) {
            return z2 ? getAudioTrackTunnelingAttributesV21() : audioAttributes.getAudioAttributesV21();
        }

        @RequiresApi(21)
        private static android.media.AudioAttributes getAudioTrackTunnelingAttributesV21() {
            return new AudioAttributes.Builder().setContentType(3).setFlags(16).setUsage(1).build();
        }

        private int getEncodedDefaultBufferSize(long j2) {
            int maximumEncodedRateBytesPerSecond = DefaultAudioSink.getMaximumEncodedRateBytesPerSecond(this.outputEncoding);
            if (this.outputEncoding == 5) {
                maximumEncodedRateBytesPerSecond *= 2;
            }
            return (int) ((j2 * maximumEncodedRateBytesPerSecond) / 1000000);
        }

        private int getPcmDefaultBufferSize(float f2) {
            int minBufferSize = AudioTrack.getMinBufferSize(this.outputSampleRate, this.outputChannelConfig, this.outputEncoding);
            Assertions.checkState(minBufferSize != -2);
            int iConstrainValue = Util.constrainValue(minBufferSize * 4, ((int) durationUsToFrames(250000L)) * this.outputPcmFrameSize, Math.max(minBufferSize, ((int) durationUsToFrames(DefaultAudioSink.MAX_BUFFER_DURATION_US)) * this.outputPcmFrameSize));
            return f2 != 1.0f ? Math.round(iConstrainValue * f2) : iConstrainValue;
        }

        public AudioTrack buildAudioTrack(boolean z2, AudioAttributes audioAttributes, int i2) throws AudioSink.InitializationException {
            try {
                AudioTrack audioTrackCreateAudioTrack = createAudioTrack(z2, audioAttributes, i2);
                int state = audioTrackCreateAudioTrack.getState();
                if (state == 1) {
                    return audioTrackCreateAudioTrack;
                }
                try {
                    audioTrackCreateAudioTrack.release();
                } catch (Exception unused) {
                }
                throw new AudioSink.InitializationException(state, this.outputSampleRate, this.outputChannelConfig, this.bufferSize, this.inputFormat, outputModeIsOffload(), null);
            } catch (IllegalArgumentException | UnsupportedOperationException e2) {
                throw new AudioSink.InitializationException(0, this.outputSampleRate, this.outputChannelConfig, this.bufferSize, this.inputFormat, outputModeIsOffload(), e2);
            }
        }

        public boolean canReuseAudioTrack(Configuration configuration) {
            return configuration.outputMode == this.outputMode && configuration.outputEncoding == this.outputEncoding && configuration.outputSampleRate == this.outputSampleRate && configuration.outputChannelConfig == this.outputChannelConfig && configuration.outputPcmFrameSize == this.outputPcmFrameSize;
        }

        public long durationUsToFrames(long j2) {
            return (j2 * this.outputSampleRate) / 1000000;
        }

        public long framesToDurationUs(long j2) {
            return (j2 * 1000000) / this.outputSampleRate;
        }

        public long inputFramesToDurationUs(long j2) {
            return (j2 * 1000000) / this.inputFormat.sampleRate;
        }

        public boolean outputModeIsOffload() {
            return this.outputMode == 1;
        }
    }

    public static class DefaultAudioProcessorChain implements AudioProcessorChain {
        private final AudioProcessor[] audioProcessors;
        private final SilenceSkippingAudioProcessor silenceSkippingAudioProcessor;
        private final SonicAudioProcessor sonicAudioProcessor;

        public DefaultAudioProcessorChain(AudioProcessor... audioProcessorArr) {
            this(audioProcessorArr, new SilenceSkippingAudioProcessor(), new SonicAudioProcessor());
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public PlaybackParameters applyPlaybackParameters(PlaybackParameters playbackParameters) {
            this.sonicAudioProcessor.setSpeed(playbackParameters.speed);
            this.sonicAudioProcessor.setPitch(playbackParameters.pitch);
            return playbackParameters;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public boolean applySkipSilenceEnabled(boolean z2) {
            this.silenceSkippingAudioProcessor.setEnabled(z2);
            return z2;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public AudioProcessor[] getAudioProcessors() {
            return this.audioProcessors;
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public long getMediaDuration(long j2) {
            return this.sonicAudioProcessor.getMediaDuration(j2);
        }

        @Override // com.google.android.exoplayer2.audio.DefaultAudioSink.AudioProcessorChain
        public long getSkippedOutputFrameCount() {
            return this.silenceSkippingAudioProcessor.getSkippedFrames();
        }

        public DefaultAudioProcessorChain(AudioProcessor[] audioProcessorArr, SilenceSkippingAudioProcessor silenceSkippingAudioProcessor, SonicAudioProcessor sonicAudioProcessor) {
            AudioProcessor[] audioProcessorArr2 = new AudioProcessor[audioProcessorArr.length + 2];
            this.audioProcessors = audioProcessorArr2;
            System.arraycopy(audioProcessorArr, 0, audioProcessorArr2, 0, audioProcessorArr.length);
            this.silenceSkippingAudioProcessor = silenceSkippingAudioProcessor;
            this.sonicAudioProcessor = sonicAudioProcessor;
            audioProcessorArr2[audioProcessorArr.length] = silenceSkippingAudioProcessor;
            audioProcessorArr2[audioProcessorArr.length + 1] = sonicAudioProcessor;
        }
    }

    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        private InvalidAudioTrackTimestampException(String str) {
            super(str);
        }
    }

    public static final class MediaPositionParameters {
        public final long audioTrackPositionUs;
        public final long mediaTimeUs;
        public final PlaybackParameters playbackParameters;
        public final boolean skipSilence;

        private MediaPositionParameters(PlaybackParameters playbackParameters, boolean z2, long j2, long j3) {
            this.playbackParameters = playbackParameters;
            this.skipSilence = z2;
            this.mediaTimeUs = j2;
            this.audioTrackPositionUs = j3;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface OffloadMode {
    }

    public static final class PendingExceptionHolder<T extends Exception> {

        @Nullable
        private T pendingException;
        private long throwDeadlineMs;
        private final long throwDelayMs;

        public PendingExceptionHolder(long j2) {
            this.throwDelayMs = j2;
        }

        public void clear() {
            this.pendingException = null;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: T extends java.lang.Exception */
        public void throwExceptionIfDeadlineIsReached(T t2) throws Exception {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (this.pendingException == null) {
                this.pendingException = t2;
                this.throwDeadlineMs = this.throwDelayMs + jElapsedRealtime;
            }
            if (jElapsedRealtime >= this.throwDeadlineMs) {
                T t3 = this.pendingException;
                if (t3 != t2) {
                    t3.addSuppressed(t2);
                }
                T t4 = this.pendingException;
                clear();
                throw t4;
            }
        }
    }

    public final class PositionTrackerListener implements AudioTrackPositionTracker.Listener {
        private PositionTrackerListener() {
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onInvalidLatency(long j2) {
            StringBuilder sb = new StringBuilder(61);
            sb.append("Ignoring impossibly large audio latency: ");
            sb.append(j2);
            Log.w(DefaultAudioSink.TAG, sb.toString());
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onPositionAdvancing(long j2) {
            if (DefaultAudioSink.this.listener != null) {
                DefaultAudioSink.this.listener.onPositionAdvancing(j2);
            }
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onPositionFramesMismatch(long j2, long j3, long j4, long j5) {
            long submittedFrames = DefaultAudioSink.this.getSubmittedFrames();
            long writtenFrames = DefaultAudioSink.this.getWrittenFrames();
            StringBuilder sb = new StringBuilder(182);
            sb.append("Spurious audio timestamp (frame position mismatch): ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j4);
            sb.append(", ");
            sb.append(j5);
            sb.append(", ");
            sb.append(submittedFrames);
            sb.append(", ");
            sb.append(writtenFrames);
            String string = sb.toString();
            if (DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                throw new InvalidAudioTrackTimestampException(string);
            }
            Log.w(DefaultAudioSink.TAG, string);
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onSystemTimeUsMismatch(long j2, long j3, long j4, long j5) {
            long submittedFrames = DefaultAudioSink.this.getSubmittedFrames();
            long writtenFrames = DefaultAudioSink.this.getWrittenFrames();
            StringBuilder sb = new StringBuilder(180);
            sb.append("Spurious audio timestamp (system clock mismatch): ");
            sb.append(j2);
            sb.append(", ");
            sb.append(j3);
            sb.append(", ");
            sb.append(j4);
            sb.append(", ");
            sb.append(j5);
            sb.append(", ");
            sb.append(submittedFrames);
            sb.append(", ");
            sb.append(writtenFrames);
            String string = sb.toString();
            if (DefaultAudioSink.failOnSpuriousAudioTimestamp) {
                throw new InvalidAudioTrackTimestampException(string);
            }
            Log.w(DefaultAudioSink.TAG, string);
        }

        @Override // com.google.android.exoplayer2.audio.AudioTrackPositionTracker.Listener
        public void onUnderrun(int i2, long j2) {
            if (DefaultAudioSink.this.listener != null) {
                DefaultAudioSink.this.listener.onUnderrun(i2, j2, SystemClock.elapsedRealtime() - DefaultAudioSink.this.lastFeedElapsedRealtimeMs);
            }
        }
    }

    @RequiresApi(29)
    public final class StreamEventCallbackV29 {
        private final AudioTrack.StreamEventCallback callback;
        private final Handler handler = new Handler();

        public StreamEventCallbackV29() {
            this.callback = new AudioTrack.StreamEventCallback() { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink.StreamEventCallbackV29.1
                @Override // android.media.AudioTrack.StreamEventCallback
                public void onDataRequest(AudioTrack audioTrack, int i2) {
                    Assertions.checkState(audioTrack == DefaultAudioSink.this.audioTrack);
                    if (DefaultAudioSink.this.listener == null || !DefaultAudioSink.this.playing) {
                        return;
                    }
                    DefaultAudioSink.this.listener.onOffloadBufferEmptying();
                }

                @Override // android.media.AudioTrack.StreamEventCallback
                public void onTearDown(AudioTrack audioTrack) {
                    Assertions.checkState(audioTrack == DefaultAudioSink.this.audioTrack);
                    if (DefaultAudioSink.this.listener == null || !DefaultAudioSink.this.playing) {
                        return;
                    }
                    DefaultAudioSink.this.listener.onOffloadBufferEmptying();
                }
            };
        }

        public void register(AudioTrack audioTrack) {
            Handler handler = this.handler;
            Objects.requireNonNull(handler);
            audioTrack.registerStreamEventCallback(new androidx.emoji2.text.b(handler), this.callback);
        }

        public void unregister(AudioTrack audioTrack) {
            audioTrack.unregisterStreamEventCallback(this.callback);
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr) {
        this(audioCapabilities, audioProcessorArr, false);
    }

    private void applyAudioProcessorPlaybackParametersAndSkipSilence(long j2) {
        PlaybackParameters playbackParametersApplyPlaybackParameters = shouldApplyAudioProcessorPlaybackParameters() ? this.audioProcessorChain.applyPlaybackParameters(getAudioProcessorPlaybackParameters()) : PlaybackParameters.DEFAULT;
        boolean zApplySkipSilenceEnabled = shouldApplyAudioProcessorPlaybackParameters() ? this.audioProcessorChain.applySkipSilenceEnabled(getSkipSilenceEnabled()) : false;
        this.mediaPositionParametersCheckpoints.add(new MediaPositionParameters(playbackParametersApplyPlaybackParameters, zApplySkipSilenceEnabled, Math.max(0L, j2), this.configuration.framesToDurationUs(getWrittenFrames())));
        setupAudioProcessors();
        AudioSink.Listener listener = this.listener;
        if (listener != null) {
            listener.onSkipSilenceEnabledChanged(zApplySkipSilenceEnabled);
        }
    }

    private long applyMediaPositionParameters(long j2) {
        while (!this.mediaPositionParametersCheckpoints.isEmpty() && j2 >= this.mediaPositionParametersCheckpoints.getFirst().audioTrackPositionUs) {
            this.mediaPositionParameters = this.mediaPositionParametersCheckpoints.remove();
        }
        MediaPositionParameters mediaPositionParameters = this.mediaPositionParameters;
        long j3 = j2 - mediaPositionParameters.audioTrackPositionUs;
        if (mediaPositionParameters.playbackParameters.equals(PlaybackParameters.DEFAULT)) {
            return this.mediaPositionParameters.mediaTimeUs + j3;
        }
        if (this.mediaPositionParametersCheckpoints.isEmpty()) {
            return this.mediaPositionParameters.mediaTimeUs + this.audioProcessorChain.getMediaDuration(j3);
        }
        MediaPositionParameters first = this.mediaPositionParametersCheckpoints.getFirst();
        return first.mediaTimeUs - Util.getMediaDurationForPlayoutDuration(first.audioTrackPositionUs - j2, this.mediaPositionParameters.playbackParameters.speed);
    }

    private long applySkipping(long j2) {
        return j2 + this.configuration.framesToDurationUs(this.audioProcessorChain.getSkippedOutputFrameCount());
    }

    private AudioTrack buildAudioTrack() throws AudioSink.InitializationException {
        try {
            return ((Configuration) Assertions.checkNotNull(this.configuration)).buildAudioTrack(this.tunneling, this.audioAttributes, this.audioSessionId);
        } catch (AudioSink.InitializationException e2) {
            maybeDisableOffload();
            AudioSink.Listener listener = this.listener;
            if (listener != null) {
                listener.onAudioSinkError(e2);
            }
            throw e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0029 -> B:5:0x0009). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean drainToEndOfStream() throws java.lang.Exception {
        /*
            r9 = this;
            int r0 = r9.drainingAudioProcessorIndex
            r1 = 1
            r2 = 0
            r3 = -1
            if (r0 != r3) goto Lb
            r9.drainingAudioProcessorIndex = r2
        L9:
            r0 = r1
            goto Lc
        Lb:
            r0 = r2
        Lc:
            int r4 = r9.drainingAudioProcessorIndex
            com.google.android.exoplayer2.audio.AudioProcessor[] r5 = r9.activeAudioProcessors
            int r6 = r5.length
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r4 >= r6) goto L2f
            r4 = r5[r4]
            if (r0 == 0) goto L1f
            r4.queueEndOfStream()
        L1f:
            r9.processBuffers(r7)
            boolean r0 = r4.isEnded()
            if (r0 != 0) goto L29
            return r2
        L29:
            int r0 = r9.drainingAudioProcessorIndex
            int r0 = r0 + r1
            r9.drainingAudioProcessorIndex = r0
            goto L9
        L2f:
            java.nio.ByteBuffer r0 = r9.outputBuffer
            if (r0 == 0) goto L3b
            r9.writeBuffer(r0, r7)
            java.nio.ByteBuffer r0 = r9.outputBuffer
            if (r0 == 0) goto L3b
            return r2
        L3b:
            r9.drainingAudioProcessorIndex = r3
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.DefaultAudioSink.drainToEndOfStream():boolean");
    }

    private void flushAudioProcessors() {
        int i2 = 0;
        while (true) {
            AudioProcessor[] audioProcessorArr = this.activeAudioProcessors;
            if (i2 >= audioProcessorArr.length) {
                return;
            }
            AudioProcessor audioProcessor = audioProcessorArr[i2];
            audioProcessor.flush();
            this.outputBuffers[i2] = audioProcessor.getOutput();
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    public static AudioFormat getAudioFormat(int i2, int i3, int i4) {
        return new AudioFormat.Builder().setSampleRate(i2).setChannelMask(i3).setEncoding(i4).build();
    }

    private PlaybackParameters getAudioProcessorPlaybackParameters() {
        return getMediaPositionParameters().playbackParameters;
    }

    private static int getChannelConfigForPassthrough(int i2) {
        int i3 = Util.SDK_INT;
        if (i3 <= 28) {
            if (i2 == 7) {
                i2 = 8;
            } else if (i2 == 3 || i2 == 4 || i2 == 5) {
                i2 = 6;
            }
        }
        if (i3 <= 26 && "fugu".equals(Util.DEVICE) && i2 == 1) {
            i2 = 2;
        }
        return Util.getAudioTrackChannelConfig(i2);
    }

    @Nullable
    private static Pair<Integer, Integer> getEncodingAndChannelConfigForPassthrough(Format format, @Nullable AudioCapabilities audioCapabilities) {
        if (audioCapabilities == null) {
            return null;
        }
        int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
        int maxSupportedChannelCountForPassthroughV29 = 6;
        if (!(encoding == 5 || encoding == 6 || encoding == 18 || encoding == 17 || encoding == 7 || encoding == 8 || encoding == 14)) {
            return null;
        }
        if (encoding == 18 && !audioCapabilities.supportsEncoding(18)) {
            encoding = 6;
        } else if (encoding == 8 && !audioCapabilities.supportsEncoding(8)) {
            encoding = 7;
        }
        if (!audioCapabilities.supportsEncoding(encoding)) {
            return null;
        }
        if (encoding != 18) {
            maxSupportedChannelCountForPassthroughV29 = format.channelCount;
            if (maxSupportedChannelCountForPassthroughV29 > audioCapabilities.getMaxChannelCount()) {
                return null;
            }
        } else if (Util.SDK_INT >= 29 && (maxSupportedChannelCountForPassthroughV29 = getMaxSupportedChannelCountForPassthroughV29(18, format.sampleRate)) == 0) {
            Log.w(TAG, "E-AC3 JOC encoding supported but no channel count supported");
            return null;
        }
        int channelConfigForPassthrough = getChannelConfigForPassthrough(maxSupportedChannelCountForPassthroughV29);
        if (channelConfigForPassthrough == 0) {
            return null;
        }
        return Pair.create(Integer.valueOf(encoding), Integer.valueOf(channelConfigForPassthrough));
    }

    private static int getFramesPerEncodedSample(int i2, ByteBuffer byteBuffer) {
        switch (i2) {
            case 5:
            case 6:
            case 18:
                return Ac3Util.parseAc3SyncframeAudioSampleCount(byteBuffer);
            case 7:
            case 8:
                return DtsUtil.parseDtsAudioSampleCount(byteBuffer);
            case 9:
                int mpegAudioFrameSampleCount = MpegAudioUtil.parseMpegAudioFrameSampleCount(Util.getBigEndianInt(byteBuffer, byteBuffer.position()));
                if (mpegAudioFrameSampleCount != -1) {
                    return mpegAudioFrameSampleCount;
                }
                throw new IllegalArgumentException();
            case 10:
                return 1024;
            case 11:
            case 12:
                return 2048;
            case 13:
            default:
                StringBuilder sb = new StringBuilder(38);
                sb.append("Unexpected audio encoding: ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
            case 14:
                int iFindTrueHdSyncframeOffset = Ac3Util.findTrueHdSyncframeOffset(byteBuffer);
                if (iFindTrueHdSyncframeOffset == -1) {
                    return 0;
                }
                return Ac3Util.parseTrueHdSyncframeAudioSampleCount(byteBuffer, iFindTrueHdSyncframeOffset) * 16;
            case 15:
                return 512;
            case 16:
                return 1024;
            case 17:
                return Ac4Util.parseAc4SyncframeAudioSampleCount(byteBuffer);
        }
    }

    @RequiresApi(29)
    private static int getMaxSupportedChannelCountForPassthroughV29(int i2, int i3) {
        android.media.AudioAttributes audioAttributesBuild = new AudioAttributes.Builder().setUsage(1).setContentType(3).build();
        for (int i4 = 8; i4 > 0; i4--) {
            if (AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setEncoding(i2).setSampleRate(i3).setChannelMask(Util.getAudioTrackChannelConfig(i4)).build(), audioAttributesBuild)) {
                return i4;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getMaximumEncodedRateBytesPerSecond(int i2) {
        switch (i2) {
            case 5:
                return Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND;
            case 6:
            case 18:
                return Ac3Util.E_AC3_MAX_RATE_BYTES_PER_SECOND;
            case 7:
                return DtsUtil.DTS_MAX_RATE_BYTES_PER_SECOND;
            case 8:
                return DtsUtil.DTS_HD_MAX_RATE_BYTES_PER_SECOND;
            case 9:
                return MpegAudioUtil.MAX_RATE_BYTES_PER_SECOND;
            case 10:
                return 100000;
            case 11:
                return 16000;
            case 12:
                return 7000;
            case 13:
            default:
                throw new IllegalArgumentException();
            case 14:
                return Ac3Util.TRUEHD_MAX_RATE_BYTES_PER_SECOND;
            case 15:
                return 8000;
            case 16:
                return AacUtil.AAC_XHE_MAX_RATE_BYTES_PER_SECOND;
            case 17:
                return Ac4Util.MAX_RATE_BYTES_PER_SECOND;
        }
    }

    private MediaPositionParameters getMediaPositionParameters() {
        MediaPositionParameters mediaPositionParameters = this.afterDrainParameters;
        return mediaPositionParameters != null ? mediaPositionParameters : !this.mediaPositionParametersCheckpoints.isEmpty() ? this.mediaPositionParametersCheckpoints.getLast() : this.mediaPositionParameters;
    }

    @RequiresApi(29)
    @SuppressLint({"WrongConstant"})
    private int getOffloadedPlaybackSupport(AudioFormat audioFormat, android.media.AudioAttributes audioAttributes) {
        int i2 = Util.SDK_INT;
        if (i2 >= 31) {
            return AudioManager.getPlaybackOffloadSupport(audioFormat, audioAttributes);
        }
        if (AudioManager.isOffloadedPlaybackSupported(audioFormat, audioAttributes)) {
            return (i2 == 30 && Util.MODEL.startsWith("Pixel")) ? 2 : 1;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getSubmittedFrames() {
        return this.configuration.outputMode == 0 ? this.submittedPcmBytes / r0.inputPcmFrameSize : this.submittedEncodedFrames;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getWrittenFrames() {
        return this.configuration.outputMode == 0 ? this.writtenPcmBytes / r0.outputPcmFrameSize : this.writtenEncodedFrames;
    }

    private void initializeAudioTrack() throws AudioSink.InitializationException {
        this.releasingConditionVariable.block();
        AudioTrack audioTrackBuildAudioTrack = buildAudioTrack();
        this.audioTrack = audioTrackBuildAudioTrack;
        if (isOffloadedPlayback(audioTrackBuildAudioTrack)) {
            registerStreamEventCallbackV29(this.audioTrack);
            if (this.offloadMode != 3) {
                AudioTrack audioTrack = this.audioTrack;
                Format format = this.configuration.inputFormat;
                audioTrack.setOffloadDelayPadding(format.encoderDelay, format.encoderPadding);
            }
        }
        this.audioSessionId = this.audioTrack.getAudioSessionId();
        AudioTrackPositionTracker audioTrackPositionTracker = this.audioTrackPositionTracker;
        AudioTrack audioTrack2 = this.audioTrack;
        Configuration configuration = this.configuration;
        audioTrackPositionTracker.setAudioTrack(audioTrack2, configuration.outputMode == 2, configuration.outputEncoding, configuration.outputPcmFrameSize, configuration.bufferSize);
        setVolumeInternal();
        int i2 = this.auxEffectInfo.effectId;
        if (i2 != 0) {
            this.audioTrack.attachAuxEffect(i2);
            this.audioTrack.setAuxEffectSendLevel(this.auxEffectInfo.sendLevel);
        }
        this.startMediaTimeUsNeedsInit = true;
    }

    private static boolean isAudioTrackDeadObject(int i2) {
        return (Util.SDK_INT >= 24 && i2 == -6) || i2 == ERROR_NATIVE_DEAD_OBJECT;
    }

    private boolean isAudioTrackInitialized() {
        return this.audioTrack != null;
    }

    private static boolean isOffloadedPlayback(AudioTrack audioTrack) {
        return Util.SDK_INT >= 29 && audioTrack.isOffloadedPlayback();
    }

    private static boolean isPassthroughPlaybackSupported(Format format, @Nullable AudioCapabilities audioCapabilities) {
        return getEncodingAndChannelConfigForPassthrough(format, audioCapabilities) != null;
    }

    private void maybeDisableOffload() {
        if (this.configuration.outputModeIsOffload()) {
            this.offloadDisabledUntilNextConfiguration = true;
        }
    }

    private void playPendingData() throws IllegalStateException {
        if (this.stoppedAudioTrack) {
            return;
        }
        this.stoppedAudioTrack = true;
        this.audioTrackPositionTracker.handleEndOfStream(getWrittenFrames());
        this.audioTrack.stop();
        this.bytesUntilNextAvSync = 0;
    }

    private void processBuffers(long j2) throws Exception {
        ByteBuffer byteBuffer;
        int length = this.activeAudioProcessors.length;
        int i2 = length;
        while (i2 >= 0) {
            if (i2 > 0) {
                byteBuffer = this.outputBuffers[i2 - 1];
            } else {
                byteBuffer = this.inputBuffer;
                if (byteBuffer == null) {
                    byteBuffer = AudioProcessor.EMPTY_BUFFER;
                }
            }
            if (i2 == length) {
                writeBuffer(byteBuffer, j2);
            } else {
                AudioProcessor audioProcessor = this.activeAudioProcessors[i2];
                if (i2 > this.drainingAudioProcessorIndex) {
                    audioProcessor.queueInput(byteBuffer);
                }
                ByteBuffer output = audioProcessor.getOutput();
                this.outputBuffers[i2] = output;
                if (output.hasRemaining()) {
                    i2++;
                }
            }
            if (byteBuffer.hasRemaining()) {
                return;
            } else {
                i2--;
            }
        }
    }

    @RequiresApi(29)
    private void registerStreamEventCallbackV29(AudioTrack audioTrack) {
        if (this.offloadStreamEventCallbackV29 == null) {
            this.offloadStreamEventCallbackV29 = new StreamEventCallbackV29();
        }
        this.offloadStreamEventCallbackV29.register(audioTrack);
    }

    private void resetSinkStateForFlush() {
        this.submittedPcmBytes = 0L;
        this.submittedEncodedFrames = 0L;
        this.writtenPcmBytes = 0L;
        this.writtenEncodedFrames = 0L;
        this.isWaitingForOffloadEndOfStreamHandled = false;
        this.framesPerEncodedSample = 0;
        this.mediaPositionParameters = new MediaPositionParameters(getAudioProcessorPlaybackParameters(), getSkipSilenceEnabled(), 0L, 0L);
        this.startMediaTimeUs = 0L;
        this.afterDrainParameters = null;
        this.mediaPositionParametersCheckpoints.clear();
        this.inputBuffer = null;
        this.inputBufferAccessUnitCount = 0;
        this.outputBuffer = null;
        this.stoppedAudioTrack = false;
        this.handledEndOfStream = false;
        this.drainingAudioProcessorIndex = -1;
        this.avSyncHeader = null;
        this.bytesUntilNextAvSync = 0;
        this.trimmingAudioProcessor.resetTrimmedFrameCount();
        flushAudioProcessors();
    }

    private void setAudioProcessorPlaybackParametersAndSkipSilence(PlaybackParameters playbackParameters, boolean z2) {
        MediaPositionParameters mediaPositionParameters = getMediaPositionParameters();
        if (playbackParameters.equals(mediaPositionParameters.playbackParameters) && z2 == mediaPositionParameters.skipSilence) {
            return;
        }
        MediaPositionParameters mediaPositionParameters2 = new MediaPositionParameters(playbackParameters, z2, C.TIME_UNSET, C.TIME_UNSET);
        if (isAudioTrackInitialized()) {
            this.afterDrainParameters = mediaPositionParameters2;
        } else {
            this.mediaPositionParameters = mediaPositionParameters2;
        }
    }

    @RequiresApi(23)
    private void setAudioTrackPlaybackParametersV23(PlaybackParameters playbackParameters) {
        if (isAudioTrackInitialized()) {
            try {
                this.audioTrack.setPlaybackParams(new PlaybackParams().allowDefaults().setSpeed(playbackParameters.speed).setPitch(playbackParameters.pitch).setAudioFallbackMode(2));
            } catch (IllegalArgumentException e2) {
                Log.w(TAG, "Failed to set playback params", e2);
            }
            playbackParameters = new PlaybackParameters(this.audioTrack.getPlaybackParams().getSpeed(), this.audioTrack.getPlaybackParams().getPitch());
            this.audioTrackPositionTracker.setAudioTrackPlaybackSpeed(playbackParameters.speed);
        }
        this.audioTrackPlaybackParameters = playbackParameters;
    }

    private void setVolumeInternal() {
        if (isAudioTrackInitialized()) {
            if (Util.SDK_INT >= 21) {
                setVolumeInternalV21(this.audioTrack, this.volume);
            } else {
                setVolumeInternalV3(this.audioTrack, this.volume);
            }
        }
    }

    @RequiresApi(21)
    private static void setVolumeInternalV21(AudioTrack audioTrack, float f2) {
        audioTrack.setVolume(f2);
    }

    private static void setVolumeInternalV3(AudioTrack audioTrack, float f2) {
        audioTrack.setStereoVolume(f2, f2);
    }

    private void setupAudioProcessors() {
        AudioProcessor[] audioProcessorArr = this.configuration.availableAudioProcessors;
        ArrayList arrayList = new ArrayList();
        for (AudioProcessor audioProcessor : audioProcessorArr) {
            if (audioProcessor.isActive()) {
                arrayList.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int size = arrayList.size();
        this.activeAudioProcessors = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[size]);
        this.outputBuffers = new ByteBuffer[size];
        flushAudioProcessors();
    }

    private boolean shouldApplyAudioProcessorPlaybackParameters() {
        return (this.tunneling || !MimeTypes.AUDIO_RAW.equals(this.configuration.inputFormat.sampleMimeType) || shouldUseFloatOutput(this.configuration.inputFormat.pcmEncoding)) ? false : true;
    }

    private boolean shouldUseFloatOutput(int i2) {
        return this.enableFloatOutput && Util.isEncodingHighResolutionPcm(i2);
    }

    private boolean useOffloadedPlayback(Format format, AudioAttributes audioAttributes) {
        int encoding;
        int audioTrackChannelConfig;
        int offloadedPlaybackSupport;
        if (Util.SDK_INT < 29 || this.offloadMode == 0 || (encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs)) == 0 || (audioTrackChannelConfig = Util.getAudioTrackChannelConfig(format.channelCount)) == 0 || (offloadedPlaybackSupport = getOffloadedPlaybackSupport(getAudioFormat(format.sampleRate, audioTrackChannelConfig, encoding), audioAttributes.getAudioAttributesV21())) == 0) {
            return false;
        }
        if (offloadedPlaybackSupport == 1) {
            return ((format.encoderDelay != 0 || format.encoderPadding != 0) && (this.offloadMode == 1)) ? false : true;
        }
        if (offloadedPlaybackSupport == 2) {
            return true;
        }
        throw new IllegalStateException();
    }

    private void writeBuffer(ByteBuffer byteBuffer, long j2) throws Exception {
        int iWriteNonBlockingV21;
        if (byteBuffer.hasRemaining()) {
            ByteBuffer byteBuffer2 = this.outputBuffer;
            if (byteBuffer2 != null) {
                Assertions.checkArgument(byteBuffer2 == byteBuffer);
            } else {
                this.outputBuffer = byteBuffer;
                if (Util.SDK_INT < 21) {
                    int iRemaining = byteBuffer.remaining();
                    byte[] bArr = this.preV21OutputBuffer;
                    if (bArr == null || bArr.length < iRemaining) {
                        this.preV21OutputBuffer = new byte[iRemaining];
                    }
                    int iPosition = byteBuffer.position();
                    byteBuffer.get(this.preV21OutputBuffer, 0, iRemaining);
                    byteBuffer.position(iPosition);
                    this.preV21OutputBufferOffset = 0;
                }
            }
            int iRemaining2 = byteBuffer.remaining();
            if (Util.SDK_INT < 21) {
                int availableBufferSize = this.audioTrackPositionTracker.getAvailableBufferSize(this.writtenPcmBytes);
                if (availableBufferSize > 0) {
                    iWriteNonBlockingV21 = this.audioTrack.write(this.preV21OutputBuffer, this.preV21OutputBufferOffset, Math.min(iRemaining2, availableBufferSize));
                    if (iWriteNonBlockingV21 > 0) {
                        this.preV21OutputBufferOffset += iWriteNonBlockingV21;
                        byteBuffer.position(byteBuffer.position() + iWriteNonBlockingV21);
                    }
                } else {
                    iWriteNonBlockingV21 = 0;
                }
            } else if (this.tunneling) {
                Assertions.checkState(j2 != C.TIME_UNSET);
                iWriteNonBlockingV21 = writeNonBlockingWithAvSyncV21(this.audioTrack, byteBuffer, iRemaining2, j2);
            } else {
                iWriteNonBlockingV21 = writeNonBlockingV21(this.audioTrack, byteBuffer, iRemaining2);
            }
            this.lastFeedElapsedRealtimeMs = SystemClock.elapsedRealtime();
            if (iWriteNonBlockingV21 < 0) {
                boolean zIsAudioTrackDeadObject = isAudioTrackDeadObject(iWriteNonBlockingV21);
                if (zIsAudioTrackDeadObject) {
                    maybeDisableOffload();
                }
                AudioSink.WriteException writeException = new AudioSink.WriteException(iWriteNonBlockingV21, this.configuration.inputFormat, zIsAudioTrackDeadObject);
                AudioSink.Listener listener = this.listener;
                if (listener != null) {
                    listener.onAudioSinkError(writeException);
                }
                if (writeException.isRecoverable) {
                    throw writeException;
                }
                this.writeExceptionPendingExceptionHolder.throwExceptionIfDeadlineIsReached(writeException);
                return;
            }
            this.writeExceptionPendingExceptionHolder.clear();
            if (isOffloadedPlayback(this.audioTrack)) {
                long j3 = this.writtenEncodedFrames;
                if (j3 > 0) {
                    this.isWaitingForOffloadEndOfStreamHandled = false;
                }
                if (this.playing && this.listener != null && iWriteNonBlockingV21 < iRemaining2 && !this.isWaitingForOffloadEndOfStreamHandled) {
                    this.listener.onOffloadBufferFull(this.audioTrackPositionTracker.getPendingBufferDurationMs(j3));
                }
            }
            int i2 = this.configuration.outputMode;
            if (i2 == 0) {
                this.writtenPcmBytes += iWriteNonBlockingV21;
            }
            if (iWriteNonBlockingV21 == iRemaining2) {
                if (i2 != 0) {
                    Assertions.checkState(byteBuffer == this.inputBuffer);
                    this.writtenEncodedFrames += this.framesPerEncodedSample * this.inputBufferAccessUnitCount;
                }
                this.outputBuffer = null;
            }
        }
    }

    @RequiresApi(21)
    private static int writeNonBlockingV21(AudioTrack audioTrack, ByteBuffer byteBuffer, int i2) {
        return audioTrack.write(byteBuffer, i2, 1);
    }

    @RequiresApi(21)
    private int writeNonBlockingWithAvSyncV21(AudioTrack audioTrack, ByteBuffer byteBuffer, int i2, long j2) {
        if (Util.SDK_INT >= 26) {
            return audioTrack.write(byteBuffer, i2, 1, j2 * 1000);
        }
        if (this.avSyncHeader == null) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(16);
            this.avSyncHeader = byteBufferAllocate;
            byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
            this.avSyncHeader.putInt(1431633921);
        }
        if (this.bytesUntilNextAvSync == 0) {
            this.avSyncHeader.putInt(4, i2);
            this.avSyncHeader.putLong(8, j2 * 1000);
            this.avSyncHeader.position(0);
            this.bytesUntilNextAvSync = i2;
        }
        int iRemaining = this.avSyncHeader.remaining();
        if (iRemaining > 0) {
            int iWrite = audioTrack.write(this.avSyncHeader, iRemaining, 1);
            if (iWrite < 0) {
                this.bytesUntilNextAvSync = 0;
                return iWrite;
            }
            if (iWrite < iRemaining) {
                return 0;
            }
        }
        int iWriteNonBlockingV21 = writeNonBlockingV21(audioTrack, byteBuffer, i2);
        if (iWriteNonBlockingV21 < 0) {
            this.bytesUntilNextAvSync = 0;
            return iWriteNonBlockingV21;
        }
        this.bytesUntilNextAvSync -= iWriteNonBlockingV21;
        return iWriteNonBlockingV21;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void configure(Format format, int i2, @Nullable int[] iArr) throws AudioSink.ConfigurationException {
        int pcmFrameSize;
        AudioProcessor[] audioProcessorArr;
        int iIntValue;
        int i3;
        int i4;
        int iIntValue2;
        int i5;
        int[] iArr2;
        if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            Assertions.checkArgument(Util.isEncodingLinearPcm(format.pcmEncoding));
            int pcmFrameSize2 = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
            AudioProcessor[] audioProcessorArr2 = shouldUseFloatOutput(format.pcmEncoding) ? this.toFloatPcmAvailableAudioProcessors : this.toIntPcmAvailableAudioProcessors;
            this.trimmingAudioProcessor.setTrimFrameCount(format.encoderDelay, format.encoderPadding);
            if (Util.SDK_INT < 21 && format.channelCount == 8 && iArr == null) {
                iArr2 = new int[6];
                for (int i6 = 0; i6 < 6; i6++) {
                    iArr2[i6] = i6;
                }
            } else {
                iArr2 = iArr;
            }
            this.channelMappingAudioProcessor.setChannelMap(iArr2);
            AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(format.sampleRate, format.channelCount, format.pcmEncoding);
            for (AudioProcessor audioProcessor : audioProcessorArr2) {
                try {
                    AudioProcessor.AudioFormat audioFormatConfigure = audioProcessor.configure(audioFormat);
                    if (audioProcessor.isActive()) {
                        audioFormat = audioFormatConfigure;
                    }
                } catch (AudioProcessor.UnhandledAudioFormatException e2) {
                    throw new AudioSink.ConfigurationException(e2, format);
                }
            }
            int i7 = audioFormat.encoding;
            i3 = audioFormat.sampleRate;
            iIntValue2 = Util.getAudioTrackChannelConfig(audioFormat.channelCount);
            audioProcessorArr = audioProcessorArr2;
            iIntValue = i7;
            i4 = pcmFrameSize2;
            pcmFrameSize = Util.getPcmFrameSize(i7, audioFormat.channelCount);
            i5 = 0;
        } else {
            AudioProcessor[] audioProcessorArr3 = new AudioProcessor[0];
            int i8 = format.sampleRate;
            pcmFrameSize = -1;
            if (useOffloadedPlayback(format, this.audioAttributes)) {
                audioProcessorArr = audioProcessorArr3;
                iIntValue = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
                i5 = 1;
                iIntValue2 = Util.getAudioTrackChannelConfig(format.channelCount);
                i3 = i8;
                i4 = -1;
            } else {
                Pair<Integer, Integer> encodingAndChannelConfigForPassthrough = getEncodingAndChannelConfigForPassthrough(format, this.audioCapabilities);
                if (encodingAndChannelConfigForPassthrough == null) {
                    String strValueOf = String.valueOf(format);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 37);
                    sb.append("Unable to configure passthrough for: ");
                    sb.append(strValueOf);
                    throw new AudioSink.ConfigurationException(sb.toString(), format);
                }
                audioProcessorArr = audioProcessorArr3;
                iIntValue = ((Integer) encodingAndChannelConfigForPassthrough.first).intValue();
                i3 = i8;
                i4 = -1;
                iIntValue2 = ((Integer) encodingAndChannelConfigForPassthrough.second).intValue();
                i5 = 2;
            }
        }
        if (iIntValue == 0) {
            String strValueOf2 = String.valueOf(format);
            StringBuilder sb2 = new StringBuilder(strValueOf2.length() + 48);
            sb2.append("Invalid output encoding (mode=");
            sb2.append(i5);
            sb2.append(") for: ");
            sb2.append(strValueOf2);
            throw new AudioSink.ConfigurationException(sb2.toString(), format);
        }
        if (iIntValue2 != 0) {
            this.offloadDisabledUntilNextConfiguration = false;
            Configuration configuration = new Configuration(format, i4, i5, pcmFrameSize, i3, iIntValue2, iIntValue, i2, this.enableAudioTrackPlaybackParams, audioProcessorArr);
            if (isAudioTrackInitialized()) {
                this.pendingConfiguration = configuration;
                return;
            } else {
                this.configuration = configuration;
                return;
            }
        }
        String strValueOf3 = String.valueOf(format);
        StringBuilder sb3 = new StringBuilder(strValueOf3.length() + 54);
        sb3.append("Invalid output channel config (mode=");
        sb3.append(i5);
        sb3.append(") for: ");
        sb3.append(strValueOf3);
        throw new AudioSink.ConfigurationException(sb3.toString(), format);
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void disableTunneling() throws IllegalStateException {
        if (this.tunneling) {
            this.tunneling = false;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void enableTunnelingV21() throws IllegalStateException {
        Assertions.checkState(Util.SDK_INT >= 21);
        Assertions.checkState(this.externalAudioSessionIdProvided);
        if (this.tunneling) {
            return;
        }
        this.tunneling = true;
        flush();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void experimentalFlushWithoutAudioTrackRelease() throws IllegalStateException {
        if (Util.SDK_INT < 25) {
            flush();
            return;
        }
        this.writeExceptionPendingExceptionHolder.clear();
        this.initializationExceptionPendingExceptionHolder.clear();
        if (isAudioTrackInitialized()) {
            resetSinkStateForFlush();
            if (this.audioTrackPositionTracker.isPlaying()) {
                this.audioTrack.pause();
            }
            this.audioTrack.flush();
            this.audioTrackPositionTracker.reset();
            AudioTrackPositionTracker audioTrackPositionTracker = this.audioTrackPositionTracker;
            AudioTrack audioTrack = this.audioTrack;
            Configuration configuration = this.configuration;
            audioTrackPositionTracker.setAudioTrack(audioTrack, configuration.outputMode == 2, configuration.outputEncoding, configuration.outputPcmFrameSize, configuration.bufferSize);
            this.startMediaTimeUsNeedsInit = true;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void flush() throws IllegalStateException {
        if (isAudioTrackInitialized()) {
            resetSinkStateForFlush();
            if (this.audioTrackPositionTracker.isPlaying()) {
                this.audioTrack.pause();
            }
            if (isOffloadedPlayback(this.audioTrack)) {
                ((StreamEventCallbackV29) Assertions.checkNotNull(this.offloadStreamEventCallbackV29)).unregister(this.audioTrack);
            }
            final AudioTrack audioTrack = this.audioTrack;
            this.audioTrack = null;
            if (Util.SDK_INT < 21 && !this.externalAudioSessionIdProvided) {
                this.audioSessionId = 0;
            }
            Configuration configuration = this.pendingConfiguration;
            if (configuration != null) {
                this.configuration = configuration;
                this.pendingConfiguration = null;
            }
            this.audioTrackPositionTracker.reset();
            this.releasingConditionVariable.close();
            new Thread("ExoPlayer:AudioTrackReleaseThread") { // from class: com.google.android.exoplayer2.audio.DefaultAudioSink.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        audioTrack.flush();
                        audioTrack.release();
                    } finally {
                        DefaultAudioSink.this.releasingConditionVariable.open();
                    }
                }
            }.start();
        }
        this.writeExceptionPendingExceptionHolder.clear();
        this.initializationExceptionPendingExceptionHolder.clear();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public long getCurrentPositionUs(boolean z2) {
        if (!isAudioTrackInitialized() || this.startMediaTimeUsNeedsInit) {
            return Long.MIN_VALUE;
        }
        return applySkipping(applyMediaPositionParameters(Math.min(this.audioTrackPositionTracker.getCurrentPositionUs(z2), this.configuration.framesToDurationUs(getWrittenFrames()))));
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public int getFormatSupport(Format format) {
        if (!MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
            return ((this.offloadDisabledUntilNextConfiguration || !useOffloadedPlayback(format, this.audioAttributes)) && !isPassthroughPlaybackSupported(format, this.audioCapabilities)) ? 0 : 2;
        }
        if (Util.isEncodingLinearPcm(format.pcmEncoding)) {
            int i2 = format.pcmEncoding;
            return (i2 == 2 || (this.enableFloatOutput && i2 == 4)) ? 2 : 1;
        }
        int i3 = format.pcmEncoding;
        StringBuilder sb = new StringBuilder(33);
        sb.append("Invalid PCM encoding: ");
        sb.append(i3);
        Log.w(TAG, sb.toString());
        return 0;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public PlaybackParameters getPlaybackParameters() {
        return this.enableAudioTrackPlaybackParams ? this.audioTrackPlaybackParameters : getAudioProcessorPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean getSkipSilenceEnabled() {
        return getMediaPositionParameters().skipSilence;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean handleBuffer(ByteBuffer byteBuffer, long j2, int i2) throws Exception {
        ByteBuffer byteBuffer2 = this.inputBuffer;
        Assertions.checkArgument(byteBuffer2 == null || byteBuffer == byteBuffer2);
        if (this.pendingConfiguration != null) {
            if (!drainToEndOfStream()) {
                return false;
            }
            if (this.pendingConfiguration.canReuseAudioTrack(this.configuration)) {
                this.configuration = this.pendingConfiguration;
                this.pendingConfiguration = null;
                if (isOffloadedPlayback(this.audioTrack) && this.offloadMode != 3) {
                    this.audioTrack.setOffloadEndOfStream();
                    AudioTrack audioTrack = this.audioTrack;
                    Format format = this.configuration.inputFormat;
                    audioTrack.setOffloadDelayPadding(format.encoderDelay, format.encoderPadding);
                    this.isWaitingForOffloadEndOfStreamHandled = true;
                }
            } else {
                playPendingData();
                if (hasPendingData()) {
                    return false;
                }
                flush();
            }
            applyAudioProcessorPlaybackParametersAndSkipSilence(j2);
        }
        if (!isAudioTrackInitialized()) {
            try {
                initializeAudioTrack();
            } catch (AudioSink.InitializationException e2) {
                if (e2.isRecoverable) {
                    throw e2;
                }
                this.initializationExceptionPendingExceptionHolder.throwExceptionIfDeadlineIsReached(e2);
                return false;
            }
        }
        this.initializationExceptionPendingExceptionHolder.clear();
        if (this.startMediaTimeUsNeedsInit) {
            this.startMediaTimeUs = Math.max(0L, j2);
            this.startMediaTimeUsNeedsSync = false;
            this.startMediaTimeUsNeedsInit = false;
            if (this.enableAudioTrackPlaybackParams && Util.SDK_INT >= 23) {
                setAudioTrackPlaybackParametersV23(this.audioTrackPlaybackParameters);
            }
            applyAudioProcessorPlaybackParametersAndSkipSilence(j2);
            if (this.playing) {
                play();
            }
        }
        if (!this.audioTrackPositionTracker.mayHandleBuffer(getWrittenFrames())) {
            return false;
        }
        if (this.inputBuffer == null) {
            Assertions.checkArgument(byteBuffer.order() == ByteOrder.LITTLE_ENDIAN);
            if (!byteBuffer.hasRemaining()) {
                return true;
            }
            Configuration configuration = this.configuration;
            if (configuration.outputMode != 0 && this.framesPerEncodedSample == 0) {
                int framesPerEncodedSample = getFramesPerEncodedSample(configuration.outputEncoding, byteBuffer);
                this.framesPerEncodedSample = framesPerEncodedSample;
                if (framesPerEncodedSample == 0) {
                    return true;
                }
            }
            if (this.afterDrainParameters != null) {
                if (!drainToEndOfStream()) {
                    return false;
                }
                applyAudioProcessorPlaybackParametersAndSkipSilence(j2);
                this.afterDrainParameters = null;
            }
            long jInputFramesToDurationUs = this.startMediaTimeUs + this.configuration.inputFramesToDurationUs(getSubmittedFrames() - this.trimmingAudioProcessor.getTrimmedFrameCount());
            if (!this.startMediaTimeUsNeedsSync && Math.abs(jInputFramesToDurationUs - j2) > 200000) {
                this.listener.onAudioSinkError(new AudioSink.UnexpectedDiscontinuityException(j2, jInputFramesToDurationUs));
                this.startMediaTimeUsNeedsSync = true;
            }
            if (this.startMediaTimeUsNeedsSync) {
                if (!drainToEndOfStream()) {
                    return false;
                }
                long j3 = j2 - jInputFramesToDurationUs;
                this.startMediaTimeUs += j3;
                this.startMediaTimeUsNeedsSync = false;
                applyAudioProcessorPlaybackParametersAndSkipSilence(j2);
                AudioSink.Listener listener = this.listener;
                if (listener != null && j3 != 0) {
                    listener.onPositionDiscontinuity();
                }
            }
            if (this.configuration.outputMode == 0) {
                this.submittedPcmBytes += byteBuffer.remaining();
            } else {
                this.submittedEncodedFrames += this.framesPerEncodedSample * i2;
            }
            this.inputBuffer = byteBuffer;
            this.inputBufferAccessUnitCount = i2;
        }
        processBuffers(j2);
        if (!this.inputBuffer.hasRemaining()) {
            this.inputBuffer = null;
            this.inputBufferAccessUnitCount = 0;
            return true;
        }
        if (!this.audioTrackPositionTracker.isStalled(getWrittenFrames())) {
            return false;
        }
        Log.w(TAG, "Resetting stalled audio track");
        flush();
        return true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void handleDiscontinuity() {
        this.startMediaTimeUsNeedsSync = true;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean hasPendingData() {
        return isAudioTrackInitialized() && this.audioTrackPositionTracker.hasPendingData(getWrittenFrames());
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean isEnded() {
        return !isAudioTrackInitialized() || (this.handledEndOfStream && !hasPendingData());
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void pause() throws IllegalStateException {
        this.playing = false;
        if (isAudioTrackInitialized() && this.audioTrackPositionTracker.pause()) {
            this.audioTrack.pause();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void play() throws IllegalStateException {
        this.playing = true;
        if (isAudioTrackInitialized()) {
            this.audioTrackPositionTracker.start();
            this.audioTrack.play();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void playToEndOfStream() throws IllegalStateException, AudioSink.WriteException {
        if (!this.handledEndOfStream && isAudioTrackInitialized() && drainToEndOfStream()) {
            playPendingData();
            this.handledEndOfStream = true;
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void reset() throws IllegalStateException {
        flush();
        for (AudioProcessor audioProcessor : this.toIntPcmAvailableAudioProcessors) {
            audioProcessor.reset();
        }
        for (AudioProcessor audioProcessor2 : this.toFloatPcmAvailableAudioProcessors) {
            audioProcessor2.reset();
        }
        this.playing = false;
        this.offloadDisabledUntilNextConfiguration = false;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalStateException {
        if (this.audioAttributes.equals(audioAttributes)) {
            return;
        }
        this.audioAttributes = audioAttributes;
        if (this.tunneling) {
            return;
        }
        flush();
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAudioSessionId(int i2) throws IllegalStateException {
        if (this.audioSessionId != i2) {
            this.audioSessionId = i2;
            this.externalAudioSessionIdProvided = i2 != 0;
            flush();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setAuxEffectInfo(AuxEffectInfo auxEffectInfo) {
        if (this.auxEffectInfo.equals(auxEffectInfo)) {
            return;
        }
        int i2 = auxEffectInfo.effectId;
        float f2 = auxEffectInfo.sendLevel;
        AudioTrack audioTrack = this.audioTrack;
        if (audioTrack != null) {
            if (this.auxEffectInfo.effectId != i2) {
                audioTrack.attachAuxEffect(i2);
            }
            if (i2 != 0) {
                this.audioTrack.setAuxEffectSendLevel(f2);
            }
        }
        this.auxEffectInfo = auxEffectInfo;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setListener(AudioSink.Listener listener) {
        this.listener = listener;
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        PlaybackParameters playbackParameters2 = new PlaybackParameters(Util.constrainValue(playbackParameters.speed, 0.1f, 8.0f), Util.constrainValue(playbackParameters.pitch, 0.1f, 8.0f));
        if (!this.enableAudioTrackPlaybackParams || Util.SDK_INT < 23) {
            setAudioProcessorPlaybackParametersAndSkipSilence(playbackParameters2, getSkipSilenceEnabled());
        } else {
            setAudioTrackPlaybackParametersV23(playbackParameters2);
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setSkipSilenceEnabled(boolean z2) {
        setAudioProcessorPlaybackParametersAndSkipSilence(getAudioProcessorPlaybackParameters(), z2);
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public void setVolume(float f2) {
        if (this.volume != f2) {
            this.volume = f2;
            setVolumeInternal();
        }
    }

    @Override // com.google.android.exoplayer2.audio.AudioSink
    public boolean supportsFormat(Format format) {
        return getFormatSupport(format) != 0;
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr, boolean z2) {
        this(audioCapabilities, new DefaultAudioProcessorChain(audioProcessorArr), z2, false, 0);
    }

    public DefaultAudioSink(@Nullable AudioCapabilities audioCapabilities, AudioProcessorChain audioProcessorChain, boolean z2, boolean z3, int i2) {
        this.audioCapabilities = audioCapabilities;
        this.audioProcessorChain = (AudioProcessorChain) Assertions.checkNotNull(audioProcessorChain);
        int i3 = Util.SDK_INT;
        this.enableFloatOutput = i3 >= 21 && z2;
        this.enableAudioTrackPlaybackParams = i3 >= 23 && z3;
        this.offloadMode = i3 >= 29 ? i2 : 0;
        this.releasingConditionVariable = new ConditionVariable(true);
        this.audioTrackPositionTracker = new AudioTrackPositionTracker(new PositionTrackerListener());
        ChannelMappingAudioProcessor channelMappingAudioProcessor = new ChannelMappingAudioProcessor();
        this.channelMappingAudioProcessor = channelMappingAudioProcessor;
        TrimmingAudioProcessor trimmingAudioProcessor = new TrimmingAudioProcessor();
        this.trimmingAudioProcessor = trimmingAudioProcessor;
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, new ResamplingAudioProcessor(), channelMappingAudioProcessor, trimmingAudioProcessor);
        Collections.addAll(arrayList, audioProcessorChain.getAudioProcessors());
        this.toIntPcmAvailableAudioProcessors = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[0]);
        this.toFloatPcmAvailableAudioProcessors = new AudioProcessor[]{new FloatResamplingAudioProcessor()};
        this.volume = 1.0f;
        this.audioAttributes = AudioAttributes.DEFAULT;
        this.audioSessionId = 0;
        this.auxEffectInfo = new AuxEffectInfo(0, 0.0f);
        PlaybackParameters playbackParameters = PlaybackParameters.DEFAULT;
        this.mediaPositionParameters = new MediaPositionParameters(playbackParameters, false, 0L, 0L);
        this.audioTrackPlaybackParameters = playbackParameters;
        this.drainingAudioProcessorIndex = -1;
        this.activeAudioProcessors = new AudioProcessor[0];
        this.outputBuffers = new ByteBuffer[0];
        this.mediaPositionParametersCheckpoints = new ArrayDeque<>();
        this.initializationExceptionPendingExceptionHolder = new PendingExceptionHolder<>(100L);
        this.writeExceptionPendingExceptionHolder = new PendingExceptionHolder<>(100L);
    }
}
