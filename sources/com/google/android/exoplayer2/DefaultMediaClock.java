package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.StandaloneMediaClock;

/* loaded from: classes3.dex */
final class DefaultMediaClock implements MediaClock {
    private boolean isUsingStandaloneClock = true;
    private final PlaybackParametersListener listener;

    @Nullable
    private MediaClock rendererClock;

    @Nullable
    private Renderer rendererClockSource;
    private final StandaloneMediaClock standaloneClock;
    private boolean standaloneClockIsStarted;

    public interface PlaybackParametersListener {
        void onPlaybackParametersChanged(PlaybackParameters playbackParameters);
    }

    public DefaultMediaClock(PlaybackParametersListener playbackParametersListener, Clock clock) {
        this.listener = playbackParametersListener;
        this.standaloneClock = new StandaloneMediaClock(clock);
    }

    private boolean shouldUseStandaloneClock(boolean z2) {
        Renderer renderer = this.rendererClockSource;
        return renderer == null || renderer.isEnded() || (!this.rendererClockSource.isReady() && (z2 || this.rendererClockSource.hasReadStreamToEnd()));
    }

    private void syncClocks(boolean z2) {
        if (shouldUseStandaloneClock(z2)) {
            this.isUsingStandaloneClock = true;
            if (this.standaloneClockIsStarted) {
                this.standaloneClock.start();
                return;
            }
            return;
        }
        MediaClock mediaClock = (MediaClock) Assertions.checkNotNull(this.rendererClock);
        long positionUs = mediaClock.getPositionUs();
        if (this.isUsingStandaloneClock) {
            if (positionUs < this.standaloneClock.getPositionUs()) {
                this.standaloneClock.stop();
                return;
            } else {
                this.isUsingStandaloneClock = false;
                if (this.standaloneClockIsStarted) {
                    this.standaloneClock.start();
                }
            }
        }
        this.standaloneClock.resetPosition(positionUs);
        PlaybackParameters playbackParameters = mediaClock.getPlaybackParameters();
        if (playbackParameters.equals(this.standaloneClock.getPlaybackParameters())) {
            return;
        }
        this.standaloneClock.setPlaybackParameters(playbackParameters);
        this.listener.onPlaybackParametersChanged(playbackParameters);
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public PlaybackParameters getPlaybackParameters() {
        MediaClock mediaClock = this.rendererClock;
        return mediaClock != null ? mediaClock.getPlaybackParameters() : this.standaloneClock.getPlaybackParameters();
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public long getPositionUs() {
        return this.isUsingStandaloneClock ? this.standaloneClock.getPositionUs() : ((MediaClock) Assertions.checkNotNull(this.rendererClock)).getPositionUs();
    }

    public void onRendererDisabled(Renderer renderer) {
        if (renderer == this.rendererClockSource) {
            this.rendererClock = null;
            this.rendererClockSource = null;
            this.isUsingStandaloneClock = true;
        }
    }

    public void onRendererEnabled(Renderer renderer) throws ExoPlaybackException {
        MediaClock mediaClock;
        MediaClock mediaClock2 = renderer.getMediaClock();
        if (mediaClock2 == null || mediaClock2 == (mediaClock = this.rendererClock)) {
            return;
        }
        if (mediaClock != null) {
            throw ExoPlaybackException.createForUnexpected(new IllegalStateException("Multiple renderer media clocks enabled."));
        }
        this.rendererClock = mediaClock2;
        this.rendererClockSource = renderer;
        mediaClock2.setPlaybackParameters(this.standaloneClock.getPlaybackParameters());
    }

    public void resetPosition(long j2) {
        this.standaloneClock.resetPosition(j2);
    }

    @Override // com.google.android.exoplayer2.util.MediaClock
    public void setPlaybackParameters(PlaybackParameters playbackParameters) {
        MediaClock mediaClock = this.rendererClock;
        if (mediaClock != null) {
            mediaClock.setPlaybackParameters(playbackParameters);
            playbackParameters = this.rendererClock.getPlaybackParameters();
        }
        this.standaloneClock.setPlaybackParameters(playbackParameters);
    }

    public void start() {
        this.standaloneClockIsStarted = true;
        this.standaloneClock.start();
    }

    public void stop() {
        this.standaloneClockIsStarted = false;
        this.standaloneClock.stop();
    }

    public long syncAndGetPositionUs(boolean z2) {
        syncClocks(z2);
        return getPositionUs();
    }
}
