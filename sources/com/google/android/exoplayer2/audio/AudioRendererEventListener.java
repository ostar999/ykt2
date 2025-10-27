package com.google.android.exoplayer2.audio;

import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes3.dex */
public interface AudioRendererEventListener {

    public static final class EventDispatcher {

        @Nullable
        private final Handler handler;

        @Nullable
        private final AudioRendererEventListener listener;

        public EventDispatcher(@Nullable Handler handler, @Nullable AudioRendererEventListener audioRendererEventListener) {
            this.handler = audioRendererEventListener != null ? (Handler) Assertions.checkNotNull(handler) : null;
            this.listener = audioRendererEventListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$audioCodecError$9(Exception exc) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioCodecError(exc);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$audioSinkError$8(Exception exc) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioSinkError(exc);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$decoderInitialized$1(String str, long j2, long j3) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioDecoderInitialized(str, j2, j3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$decoderReleased$5(String str) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioDecoderReleased(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$disabled$6(DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioDisabled(decoderCounters);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$enabled$0(DecoderCounters decoderCounters) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioEnabled(decoderCounters);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$inputFormatChanged$2(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioInputFormatChanged(format);
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioInputFormatChanged(format, decoderReuseEvaluation);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionAdvancing$3(long j2) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioPositionAdvancing(j2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$skipSilenceEnabledChanged$7(boolean z2) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onSkipSilenceEnabledChanged(z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$underrun$4(int i2, long j2, long j3) {
            ((AudioRendererEventListener) Util.castNonNull(this.listener)).onAudioUnderrun(i2, j2, j3);
        }

        public void audioCodecError(final Exception exc) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.m
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6736c.lambda$audioCodecError$9(exc);
                    }
                });
            }
        }

        public void audioSinkError(final Exception exc) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.l
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6734c.lambda$audioSinkError$8(exc);
                    }
                });
            }
        }

        public void decoderInitialized(final String str, final long j2, final long j3) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.n
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6738c.lambda$decoderInitialized$1(str, j2, j3);
                    }
                });
            }
        }

        public void decoderReleased(final String str) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.e
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6717c.lambda$decoderReleased$5(str);
                    }
                });
            }
        }

        public void disabled(final DecoderCounters decoderCounters) {
            decoderCounters.ensureUpdated();
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.i
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6726c.lambda$disabled$6(decoderCounters);
                    }
                });
            }
        }

        public void enabled(final DecoderCounters decoderCounters) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.j
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6728c.lambda$enabled$0(decoderCounters);
                    }
                });
            }
        }

        public void inputFormatChanged(final Format format, @Nullable final DecoderReuseEvaluation decoderReuseEvaluation) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.f
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6719c.lambda$inputFormatChanged$2(format, decoderReuseEvaluation);
                    }
                });
            }
        }

        public void positionAdvancing(final long j2) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.h
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6724c.lambda$positionAdvancing$3(j2);
                    }
                });
            }
        }

        public void skipSilenceEnabledChanged(final boolean z2) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.g
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6722c.lambda$skipSilenceEnabledChanged$7(z2);
                    }
                });
            }
        }

        public void underrun(final int i2, final long j2, final long j3) {
            Handler handler = this.handler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.google.android.exoplayer2.audio.k
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6730c.lambda$underrun$4(i2, j2, j3);
                    }
                });
            }
        }
    }

    void onAudioCodecError(Exception exc);

    void onAudioDecoderInitialized(String str, long j2, long j3);

    void onAudioDecoderReleased(String str);

    void onAudioDisabled(DecoderCounters decoderCounters);

    void onAudioEnabled(DecoderCounters decoderCounters);

    @Deprecated
    void onAudioInputFormatChanged(Format format);

    void onAudioInputFormatChanged(Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation);

    void onAudioPositionAdvancing(long j2);

    void onAudioSinkError(Exception exc);

    void onAudioUnderrun(int i2, long j2, long j3);

    void onSkipSilenceEnabledChanged(boolean z2);
}
