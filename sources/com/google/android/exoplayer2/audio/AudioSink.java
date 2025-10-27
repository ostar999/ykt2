package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public interface AudioSink {
    public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
    public static final int SINK_FORMAT_SUPPORTED_DIRECTLY = 2;
    public static final int SINK_FORMAT_SUPPORTED_WITH_TRANSCODING = 1;
    public static final int SINK_FORMAT_UNSUPPORTED = 0;

    public static final class InitializationException extends Exception {
        public final int audioTrackState;
        public final Format format;
        public final boolean isRecoverable;

        public InitializationException(int i2, int i3, int i4, int i5, Format format, boolean z2, @Nullable Exception exc) {
            String str = z2 ? " (recoverable)" : "";
            StringBuilder sb = new StringBuilder(str.length() + 80);
            sb.append("AudioTrack init failed ");
            sb.append(i2);
            sb.append(" ");
            sb.append("Config(");
            sb.append(i3);
            sb.append(", ");
            sb.append(i4);
            sb.append(", ");
            sb.append(i5);
            sb.append(")");
            sb.append(str);
            super(sb.toString(), exc);
            this.audioTrackState = i2;
            this.isRecoverable = z2;
            this.format = format;
        }
    }

    public interface Listener {
        void onAudioSinkError(Exception exc);

        void onOffloadBufferEmptying();

        void onOffloadBufferFull(long j2);

        void onPositionAdvancing(long j2);

        void onPositionDiscontinuity();

        void onSkipSilenceEnabledChanged(boolean z2);

        void onUnderrun(int i2, long j2, long j3);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SinkFormatSupport {
    }

    public static final class UnexpectedDiscontinuityException extends Exception {
        public final long actualPresentationTimeUs;
        public final long expectedPresentationTimeUs;

        public UnexpectedDiscontinuityException(long j2, long j3) {
            StringBuilder sb = new StringBuilder(103);
            sb.append("Unexpected audio track timestamp discontinuity: expected ");
            sb.append(j3);
            sb.append(", got ");
            sb.append(j2);
            super(sb.toString());
            this.actualPresentationTimeUs = j2;
            this.expectedPresentationTimeUs = j3;
        }
    }

    public static final class WriteException extends Exception {
        public final int errorCode;
        public final Format format;
        public final boolean isRecoverable;

        public WriteException(int i2, Format format, boolean z2) {
            StringBuilder sb = new StringBuilder(36);
            sb.append("AudioTrack write failed: ");
            sb.append(i2);
            super(sb.toString());
            this.isRecoverable = z2;
            this.errorCode = i2;
            this.format = format;
        }
    }

    void configure(Format format, int i2, @Nullable int[] iArr) throws ConfigurationException;

    void disableTunneling();

    void enableTunnelingV21();

    void experimentalFlushWithoutAudioTrackRelease();

    void flush();

    long getCurrentPositionUs(boolean z2);

    int getFormatSupport(Format format);

    PlaybackParameters getPlaybackParameters();

    boolean getSkipSilenceEnabled();

    boolean handleBuffer(ByteBuffer byteBuffer, long j2, int i2) throws WriteException, InitializationException;

    void handleDiscontinuity();

    boolean hasPendingData();

    boolean isEnded();

    void pause();

    void play();

    void playToEndOfStream() throws WriteException;

    void reset();

    void setAudioAttributes(AudioAttributes audioAttributes);

    void setAudioSessionId(int i2);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setListener(Listener listener);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setSkipSilenceEnabled(boolean z2);

    void setVolume(float f2);

    boolean supportsFormat(Format format);

    public static final class ConfigurationException extends Exception {
        public final Format format;

        public ConfigurationException(Throwable th, Format format) {
            super(th);
            this.format = format;
        }

        public ConfigurationException(String str, Format format) {
            super(str);
            this.format = format;
        }
    }
}
