package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class PlayerControlView extends FrameLayout {
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    public static final int DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200;
    private static final int MAX_UPDATE_INTERVAL_MS = 1000;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private long[] adGroupTimesMs;
    private final float buttonAlphaDisabled;
    private final float buttonAlphaEnabled;
    private final ComponentListener componentListener;
    private long currentBufferedPosition;
    private long currentPosition;
    private long currentWindowOffset;

    @Nullable
    private final TextView durationView;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;

    @Nullable
    private final View fastForwardButton;
    private final StringBuilder formatBuilder;
    private final Formatter formatter;
    private final Runnable hideAction;
    private long hideAtMs;
    private boolean isAttachedToWindow;
    private boolean multiWindowTimeBar;

    @Nullable
    private final View nextButton;

    @Nullable
    private final View pauseButton;
    private final Timeline.Period period;

    @Nullable
    private final View playButton;
    private boolean[] playedAdGroups;

    @Nullable
    private Player player;

    @Nullable
    private final TextView positionView;

    @Nullable
    private final View previousButton;

    @Nullable
    private ProgressUpdateListener progressUpdateListener;
    private final String repeatAllButtonContentDescription;
    private final Drawable repeatAllButtonDrawable;
    private final String repeatOffButtonContentDescription;
    private final Drawable repeatOffButtonDrawable;
    private final String repeatOneButtonContentDescription;
    private final Drawable repeatOneButtonDrawable;

    @Nullable
    private final ImageView repeatToggleButton;
    private int repeatToggleModes;

    @Nullable
    private final View rewindButton;
    private boolean scrubbing;
    private boolean showFastForwardButton;
    private boolean showMultiWindowTimeBar;
    private boolean showNextButton;
    private boolean showPreviousButton;
    private boolean showRewindButton;
    private boolean showShuffleButton;
    private int showTimeoutMs;

    @Nullable
    private final ImageView shuffleButton;
    private final Drawable shuffleOffButtonDrawable;
    private final String shuffleOffContentDescription;
    private final Drawable shuffleOnButtonDrawable;
    private final String shuffleOnContentDescription;

    @Nullable
    private final TimeBar timeBar;
    private int timeBarMinUpdateIntervalMs;
    private final Runnable updateProgressAction;
    private final CopyOnWriteArrayList<VisibilityListener> visibilityListeners;

    @Nullable
    private final View vrButton;
    private final Timeline.Window window;

    @RequiresApi(21)
    public static final class Api21 {
        private Api21() {
        }

        @DoNotInline
        public static boolean isAccessibilityFocused(View view) {
            return view.isAccessibilityFocused();
        }
    }

    public final class ComponentListener implements Player.Listener, TimeBar.OnScrubListener, View.OnClickListener {
        private ComponentListener() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onAudioAttributesChanged(AudioAttributes audioAttributes) {
            x1.a(this, audioAttributes);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onAudioSessionIdChanged(int i2) {
            x1.b(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onAvailableCommandsChanged(Player.Commands commands) {
            x1.c(this, commands);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Player player = PlayerControlView.this.player;
            if (player == null) {
                return;
            }
            if (PlayerControlView.this.nextButton == view) {
                player.seekToNext();
                return;
            }
            if (PlayerControlView.this.previousButton == view) {
                player.seekToPrevious();
                return;
            }
            if (PlayerControlView.this.fastForwardButton == view) {
                if (player.getPlaybackState() != 4) {
                    player.seekForward();
                    return;
                }
                return;
            }
            if (PlayerControlView.this.rewindButton == view) {
                player.seekBack();
                return;
            }
            if (PlayerControlView.this.playButton == view) {
                PlayerControlView.this.dispatchPlay(player);
                return;
            }
            if (PlayerControlView.this.pauseButton == view) {
                PlayerControlView.this.dispatchPause(player);
            } else if (PlayerControlView.this.repeatToggleButton == view) {
                player.setRepeatMode(RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), PlayerControlView.this.repeatToggleModes));
            } else if (PlayerControlView.this.shuffleButton == view) {
                player.setShuffleModeEnabled(!player.getShuffleModeEnabled());
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onCues(List list) {
            x1.d(this, list);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            x1.e(this, deviceInfo);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onDeviceVolumeChanged(int i2, boolean z2) {
            x1.f(this, i2, z2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(4, 5)) {
                PlayerControlView.this.updatePlayPauseButton();
            }
            if (events.containsAny(4, 5, 7)) {
                PlayerControlView.this.updateProgress();
            }
            if (events.contains(8)) {
                PlayerControlView.this.updateRepeatModeButton();
            }
            if (events.contains(9)) {
                PlayerControlView.this.updateShuffleButton();
            }
            if (events.containsAny(8, 9, 11, 0, 13)) {
                PlayerControlView.this.updateNavigation();
            }
            if (events.containsAny(11, 0)) {
                PlayerControlView.this.updateTimeline();
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onIsLoadingChanged(boolean z2) {
            x1.h(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onIsPlayingChanged(boolean z2) {
            x1.i(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onLoadingChanged(boolean z2) {
            w1.e(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onMaxSeekToPreviousPositionChanged(long j2) {
            w1.f(this, j2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onMediaItemTransition(MediaItem mediaItem, int i2) {
            x1.j(this, mediaItem, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
            x1.k(this, mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onMetadata(Metadata metadata) {
            x1.l(this, metadata);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayWhenReadyChanged(boolean z2, int i2) {
            x1.m(this, z2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            x1.n(this, playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackStateChanged(int i2) {
            x1.o(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackSuppressionReasonChanged(int i2) {
            x1.p(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayerError(PlaybackException playbackException) {
            x1.q(this, playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayerErrorChanged(PlaybackException playbackException) {
            x1.r(this, playbackException);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlayerStateChanged(boolean z2, int i2) {
            w1.o(this, z2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaylistMetadataChanged(MediaMetadata mediaMetadata) {
            x1.s(this, mediaMetadata);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPositionDiscontinuity(int i2) {
            w1.q(this, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
            x1.t(this, positionInfo, positionInfo2, i2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onRenderedFirstFrame() {
            x1.u(this);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onRepeatModeChanged(int i2) {
            x1.v(this, i2);
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubMove(TimeBar timeBar, long j2) {
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, j2));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStart(TimeBar timeBar, long j2) {
            PlayerControlView.this.scrubbing = true;
            if (PlayerControlView.this.positionView != null) {
                PlayerControlView.this.positionView.setText(Util.getStringForTime(PlayerControlView.this.formatBuilder, PlayerControlView.this.formatter, j2));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStop(TimeBar timeBar, long j2, boolean z2) {
            PlayerControlView.this.scrubbing = false;
            if (z2 || PlayerControlView.this.player == null) {
                return;
            }
            PlayerControlView playerControlView = PlayerControlView.this;
            playerControlView.seekToTimeBarPosition(playerControlView.player, j2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onSeekBackIncrementChanged(long j2) {
            x1.w(this, j2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onSeekForwardIncrementChanged(long j2) {
            x1.x(this, j2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onSeekProcessed() {
            w1.v(this);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onShuffleModeEnabledChanged(boolean z2) {
            x1.y(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onSkipSilenceEnabledChanged(boolean z2) {
            x1.z(this, z2);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onSurfaceSizeChanged(int i2, int i3) {
            x1.A(this, i2, i3);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTimelineChanged(Timeline timeline, int i2) {
            x1.B(this, timeline, i2);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTrackSelectionParametersChanged(TrackSelectionParameters trackSelectionParameters) {
            w1.y(this, trackSelectionParameters);
        }

        @Override // com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            w1.z(this, trackGroupArray, trackSelectionArray);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onTracksInfoChanged(TracksInfo tracksInfo) {
            x1.C(this, tracksInfo);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
            x1.D(this, videoSize);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onVolumeChanged(float f2) {
            x1.E(this, f2);
        }
    }

    public interface ProgressUpdateListener {
        void onProgressUpdate(long j2, long j3);
    }

    public interface VisibilityListener {
        void onVisibilityChange(int i2);
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public PlayerControlView(Context context) {
        this(context, null);
    }

    private static boolean canShowMultiWindowTimeBar(Timeline timeline, Timeline.Window window) {
        if (timeline.getWindowCount() > 100) {
            return false;
        }
        int windowCount = timeline.getWindowCount();
        for (int i2 = 0; i2 < windowCount; i2++) {
            if (timeline.getWindow(i2, window).durationUs == C.TIME_UNSET) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPause(Player player) {
        player.pause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPlay(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            player.prepare();
        } else if (playbackState == 4) {
            seekTo(player, player.getCurrentMediaItemIndex(), C.TIME_UNSET);
        }
        player.play();
    }

    private void dispatchPlayPause(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
            dispatchPlay(player);
        } else {
            dispatchPause(player);
        }
    }

    private void hideAfterTimeout() {
        removeCallbacks(this.hideAction);
        if (this.showTimeoutMs <= 0) {
            this.hideAtMs = C.TIME_UNSET;
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        int i2 = this.showTimeoutMs;
        this.hideAtMs = jUptimeMillis + i2;
        if (this.isAttachedToWindow) {
            postDelayed(this.hideAction, i2);
        }
    }

    @SuppressLint({"InlinedApi"})
    private static boolean isHandledMediaKey(int i2) {
        return i2 == 90 || i2 == 89 || i2 == 85 || i2 == 79 || i2 == 126 || i2 == 127 || i2 == 87 || i2 == 88;
    }

    private void requestPlayPauseAccessibilityFocus() {
        View view;
        View view2;
        boolean zShouldShowPauseButton = shouldShowPauseButton();
        if (!zShouldShowPauseButton && (view2 = this.playButton) != null) {
            view2.sendAccessibilityEvent(8);
        } else {
            if (!zShouldShowPauseButton || (view = this.pauseButton) == null) {
                return;
            }
            view.sendAccessibilityEvent(8);
        }
    }

    private void requestPlayPauseFocus() {
        View view;
        View view2;
        boolean zShouldShowPauseButton = shouldShowPauseButton();
        if (!zShouldShowPauseButton && (view2 = this.playButton) != null) {
            view2.requestFocus();
        } else {
            if (!zShouldShowPauseButton || (view = this.pauseButton) == null) {
                return;
            }
            view.requestFocus();
        }
    }

    private void seekTo(Player player, int i2, long j2) {
        player.seekTo(i2, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekToTimeBarPosition(Player player, long j2) {
        int currentMediaItemIndex;
        Timeline currentTimeline = player.getCurrentTimeline();
        if (this.multiWindowTimeBar && !currentTimeline.isEmpty()) {
            int windowCount = currentTimeline.getWindowCount();
            currentMediaItemIndex = 0;
            while (true) {
                long durationMs = currentTimeline.getWindow(currentMediaItemIndex, this.window).getDurationMs();
                if (j2 < durationMs) {
                    break;
                }
                if (currentMediaItemIndex == windowCount - 1) {
                    j2 = durationMs;
                    break;
                } else {
                    j2 -= durationMs;
                    currentMediaItemIndex++;
                }
            }
        } else {
            currentMediaItemIndex = player.getCurrentMediaItemIndex();
        }
        seekTo(player, currentMediaItemIndex, j2);
        updateProgress();
    }

    private boolean shouldShowPauseButton() {
        Player player = this.player;
        return (player == null || player.getPlaybackState() == 4 || this.player.getPlaybackState() == 1 || !this.player.getPlayWhenReady()) ? false : true;
    }

    private void updateAll() {
        updatePlayPauseButton();
        updateNavigation();
        updateRepeatModeButton();
        updateShuffleButton();
        updateTimeline();
    }

    private void updateButton(boolean z2, boolean z3, @Nullable View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(z3);
        view.setAlpha(z3 ? this.buttonAlphaEnabled : this.buttonAlphaDisabled);
        view.setVisibility(z2 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNavigation() {
        boolean zIsCommandAvailable;
        boolean zIsCommandAvailable2;
        boolean zIsCommandAvailable3;
        boolean zIsCommandAvailable4;
        boolean zIsCommandAvailable5;
        if (isVisible() && this.isAttachedToWindow) {
            Player player = this.player;
            if (player != null) {
                zIsCommandAvailable = player.isCommandAvailable(5);
                zIsCommandAvailable3 = player.isCommandAvailable(7);
                zIsCommandAvailable4 = player.isCommandAvailable(11);
                zIsCommandAvailable5 = player.isCommandAvailable(12);
                zIsCommandAvailable2 = player.isCommandAvailable(9);
            } else {
                zIsCommandAvailable = false;
                zIsCommandAvailable2 = false;
                zIsCommandAvailable3 = false;
                zIsCommandAvailable4 = false;
                zIsCommandAvailable5 = false;
            }
            updateButton(this.showPreviousButton, zIsCommandAvailable3, this.previousButton);
            updateButton(this.showRewindButton, zIsCommandAvailable4, this.rewindButton);
            updateButton(this.showFastForwardButton, zIsCommandAvailable5, this.fastForwardButton);
            updateButton(this.showNextButton, zIsCommandAvailable2, this.nextButton);
            TimeBar timeBar = this.timeBar;
            if (timeBar != null) {
                timeBar.setEnabled(zIsCommandAvailable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayPauseButton() {
        boolean z2;
        boolean z3;
        if (isVisible() && this.isAttachedToWindow) {
            boolean zShouldShowPauseButton = shouldShowPauseButton();
            View view = this.playButton;
            boolean z4 = true;
            if (view != null) {
                z2 = (zShouldShowPauseButton && view.isFocused()) | false;
                z3 = (Util.SDK_INT < 21 ? z2 : zShouldShowPauseButton && Api21.isAccessibilityFocused(this.playButton)) | false;
                this.playButton.setVisibility(zShouldShowPauseButton ? 8 : 0);
            } else {
                z2 = false;
                z3 = false;
            }
            View view2 = this.pauseButton;
            if (view2 != null) {
                z2 |= !zShouldShowPauseButton && view2.isFocused();
                if (Util.SDK_INT < 21) {
                    z4 = z2;
                } else if (zShouldShowPauseButton || !Api21.isAccessibilityFocused(this.pauseButton)) {
                    z4 = false;
                }
                z3 |= z4;
                this.pauseButton.setVisibility(zShouldShowPauseButton ? 0 : 8);
            }
            if (z2) {
                requestPlayPauseFocus();
            }
            if (z3) {
                requestPlayPauseAccessibilityFocus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress() {
        long contentPosition;
        long contentBufferedPosition;
        if (isVisible() && this.isAttachedToWindow) {
            Player player = this.player;
            if (player != null) {
                contentPosition = this.currentWindowOffset + player.getContentPosition();
                contentBufferedPosition = this.currentWindowOffset + player.getContentBufferedPosition();
            } else {
                contentPosition = 0;
                contentBufferedPosition = 0;
            }
            boolean z2 = contentPosition != this.currentPosition;
            boolean z3 = contentBufferedPosition != this.currentBufferedPosition;
            this.currentPosition = contentPosition;
            this.currentBufferedPosition = contentBufferedPosition;
            TextView textView = this.positionView;
            if (textView != null && !this.scrubbing && z2) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, contentPosition));
            }
            TimeBar timeBar = this.timeBar;
            if (timeBar != null) {
                timeBar.setPosition(contentPosition);
                this.timeBar.setBufferedPosition(contentBufferedPosition);
            }
            ProgressUpdateListener progressUpdateListener = this.progressUpdateListener;
            if (progressUpdateListener != null && (z2 || z3)) {
                progressUpdateListener.onProgressUpdate(contentPosition, contentBufferedPosition);
            }
            removeCallbacks(this.updateProgressAction);
            int playbackState = player == null ? 1 : player.getPlaybackState();
            if (player == null || !player.isPlaying()) {
                if (playbackState == 4 || playbackState == 1) {
                    return;
                }
                postDelayed(this.updateProgressAction, 1000L);
                return;
            }
            TimeBar timeBar2 = this.timeBar;
            long jMin = Math.min(timeBar2 != null ? timeBar2.getPreferredUpdateDelay() : 1000L, 1000 - (contentPosition % 1000));
            float f2 = player.getPlaybackParameters().speed;
            postDelayed(this.updateProgressAction, Util.constrainValue(f2 > 0.0f ? (long) (jMin / f2) : 1000L, this.timeBarMinUpdateIntervalMs, 1000L));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRepeatModeButton() {
        ImageView imageView;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.repeatToggleButton) != null) {
            if (this.repeatToggleModes == 0) {
                updateButton(false, false, imageView);
                return;
            }
            Player player = this.player;
            if (player == null) {
                updateButton(true, false, imageView);
                this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                return;
            }
            updateButton(true, true, imageView);
            int repeatMode = player.getRepeatMode();
            if (repeatMode == 0) {
                this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
            } else if (repeatMode == 1) {
                this.repeatToggleButton.setImageDrawable(this.repeatOneButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOneButtonContentDescription);
            } else if (repeatMode == 2) {
                this.repeatToggleButton.setImageDrawable(this.repeatAllButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatAllButtonContentDescription);
            }
            this.repeatToggleButton.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShuffleButton() {
        ImageView imageView;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.shuffleButton) != null) {
            Player player = this.player;
            if (!this.showShuffleButton) {
                updateButton(false, false, imageView);
                return;
            }
            if (player == null) {
                updateButton(true, false, imageView);
                this.shuffleButton.setImageDrawable(this.shuffleOffButtonDrawable);
                this.shuffleButton.setContentDescription(this.shuffleOffContentDescription);
            } else {
                updateButton(true, true, imageView);
                this.shuffleButton.setImageDrawable(player.getShuffleModeEnabled() ? this.shuffleOnButtonDrawable : this.shuffleOffButtonDrawable);
                this.shuffleButton.setContentDescription(player.getShuffleModeEnabled() ? this.shuffleOnContentDescription : this.shuffleOffContentDescription);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateTimeline() {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerControlView.updateTimeline():void");
    }

    public void addVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkNotNull(visibilityListener);
        this.visibilityListeners.add(visibilityListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Player player = this.player;
        if (player == null || !isHandledMediaKey(keyCode)) {
            return false;
        }
        if (keyEvent.getAction() != 0) {
            return true;
        }
        if (keyCode == 90) {
            if (player.getPlaybackState() == 4) {
                return true;
            }
            player.seekForward();
            return true;
        }
        if (keyCode == 89) {
            player.seekBack();
            return true;
        }
        if (keyEvent.getRepeatCount() != 0) {
            return true;
        }
        if (keyCode == 79 || keyCode == 85) {
            dispatchPlayPause(player);
            return true;
        }
        if (keyCode == 87) {
            player.seekToNext();
            return true;
        }
        if (keyCode == 88) {
            player.seekToPrevious();
            return true;
        }
        if (keyCode == 126) {
            dispatchPlay(player);
            return true;
        }
        if (keyCode != 127) {
            return true;
        }
        dispatchPause(player);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            removeCallbacks(this.hideAction);
        } else if (motionEvent.getAction() == 1) {
            hideAfterTimeout();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    public int getRepeatToggleModes() {
        return this.repeatToggleModes;
    }

    public boolean getShowShuffleButton() {
        return this.showShuffleButton;
    }

    public int getShowTimeoutMs() {
        return this.showTimeoutMs;
    }

    public boolean getShowVrButton() {
        View view = this.vrButton;
        return view != null && view.getVisibility() == 0;
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(8);
            Iterator<VisibilityListener> it = this.visibilityListeners.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(getVisibility());
            }
            removeCallbacks(this.updateProgressAction);
            removeCallbacks(this.hideAction);
            this.hideAtMs = C.TIME_UNSET;
        }
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.isAttachedToWindow = true;
        long j2 = this.hideAtMs;
        if (j2 != C.TIME_UNSET) {
            long jUptimeMillis = j2 - SystemClock.uptimeMillis();
            if (jUptimeMillis <= 0) {
                hide();
            } else {
                postDelayed(this.hideAction, jUptimeMillis);
            }
        } else if (isVisible()) {
            hideAfterTimeout();
        }
        updateAll();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isAttachedToWindow = false;
        removeCallbacks(this.updateProgressAction);
        removeCallbacks(this.hideAction);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.visibilityListeners.remove(visibilityListener);
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        if (jArr == null) {
            this.extraAdGroupTimesMs = new long[0];
            this.extraPlayedAdGroups = new boolean[0];
        } else {
            boolean[] zArr2 = (boolean[]) Assertions.checkNotNull(zArr);
            Assertions.checkArgument(jArr.length == zArr2.length);
            this.extraAdGroupTimesMs = jArr;
            this.extraPlayedAdGroups = zArr2;
        }
        updateTimeline();
    }

    public void setPlayer(@Nullable Player player) {
        boolean z2 = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (player != null && player.getApplicationLooper() != Looper.getMainLooper()) {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        Player player2 = this.player;
        if (player2 == player) {
            return;
        }
        if (player2 != null) {
            player2.removeListener(this.componentListener);
        }
        this.player = player;
        if (player != null) {
            player.addListener(this.componentListener);
        }
        updateAll();
    }

    public void setProgressUpdateListener(@Nullable ProgressUpdateListener progressUpdateListener) {
        this.progressUpdateListener = progressUpdateListener;
    }

    public void setRepeatToggleModes(int i2) {
        this.repeatToggleModes = i2;
        Player player = this.player;
        if (player != null) {
            int repeatMode = player.getRepeatMode();
            if (i2 == 0 && repeatMode != 0) {
                this.player.setRepeatMode(0);
            } else if (i2 == 1 && repeatMode == 2) {
                this.player.setRepeatMode(1);
            } else if (i2 == 2 && repeatMode == 1) {
                this.player.setRepeatMode(2);
            }
        }
        updateRepeatModeButton();
    }

    public void setShowFastForwardButton(boolean z2) {
        this.showFastForwardButton = z2;
        updateNavigation();
    }

    public void setShowMultiWindowTimeBar(boolean z2) {
        this.showMultiWindowTimeBar = z2;
        updateTimeline();
    }

    public void setShowNextButton(boolean z2) {
        this.showNextButton = z2;
        updateNavigation();
    }

    public void setShowPreviousButton(boolean z2) {
        this.showPreviousButton = z2;
        updateNavigation();
    }

    public void setShowRewindButton(boolean z2) {
        this.showRewindButton = z2;
        updateNavigation();
    }

    public void setShowShuffleButton(boolean z2) {
        this.showShuffleButton = z2;
        updateShuffleButton();
    }

    public void setShowTimeoutMs(int i2) {
        this.showTimeoutMs = i2;
        if (isVisible()) {
            hideAfterTimeout();
        }
    }

    public void setShowVrButton(boolean z2) {
        View view = this.vrButton;
        if (view != null) {
            view.setVisibility(z2 ? 0 : 8);
        }
    }

    public void setTimeBarMinUpdateInterval(int i2) {
        this.timeBarMinUpdateIntervalMs = Util.constrainValue(i2, 16, 1000);
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        View view = this.vrButton;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            updateButton(getShowVrButton(), onClickListener != null, this.vrButton);
        }
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(0);
            Iterator<VisibilityListener> it = this.visibilityListeners.iterator();
            while (it.hasNext()) {
                it.next().onVisibilityChange(getVisibility());
            }
            updateAll();
            requestPlayPauseFocus();
            requestPlayPauseAccessibilityFocus();
        }
        hideAfterTimeout();
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private static int getRepeatToggleModes(TypedArray typedArray, int i2) {
        return typedArray.getInt(R.styleable.PlayerControlView_repeat_toggle_modes, i2);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    public PlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2) {
        super(context, attributeSet, i2);
        int resourceId = R.layout.exo_player_control_view;
        this.showTimeoutMs = 5000;
        this.repeatToggleModes = 0;
        this.timeBarMinUpdateIntervalMs = 200;
        this.hideAtMs = C.TIME_UNSET;
        this.showRewindButton = true;
        this.showFastForwardButton = true;
        this.showPreviousButton = true;
        this.showNextButton = true;
        this.showShuffleButton = false;
        if (attributeSet2 != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.PlayerControlView, i2, 0);
            try {
                this.showTimeoutMs = typedArrayObtainStyledAttributes.getInt(R.styleable.PlayerControlView_show_timeout, this.showTimeoutMs);
                resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.PlayerControlView_controller_layout_id, resourceId);
                this.repeatToggleModes = getRepeatToggleModes(typedArrayObtainStyledAttributes, this.repeatToggleModes);
                this.showRewindButton = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_rewind_button, this.showRewindButton);
                this.showFastForwardButton = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_fastforward_button, this.showFastForwardButton);
                this.showPreviousButton = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_previous_button, this.showPreviousButton);
                this.showNextButton = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_next_button, this.showNextButton);
                this.showShuffleButton = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerControlView_show_shuffle_button, this.showShuffleButton);
                setTimeBarMinUpdateInterval(typedArrayObtainStyledAttributes.getInt(R.styleable.PlayerControlView_time_bar_min_update_interval, this.timeBarMinUpdateIntervalMs));
            } finally {
                typedArrayObtainStyledAttributes.recycle();
            }
        }
        this.visibilityListeners = new CopyOnWriteArrayList<>();
        this.period = new Timeline.Period();
        this.window = new Timeline.Window();
        StringBuilder sb = new StringBuilder();
        this.formatBuilder = sb;
        this.formatter = new Formatter(sb, Locale.getDefault());
        this.adGroupTimesMs = new long[0];
        this.playedAdGroups = new boolean[0];
        this.extraAdGroupTimesMs = new long[0];
        this.extraPlayedAdGroups = new boolean[0];
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        this.updateProgressAction = new Runnable() { // from class: com.google.android.exoplayer2.ui.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f6935c.updateProgress();
            }
        };
        this.hideAction = new Runnable() { // from class: com.google.android.exoplayer2.ui.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f6937c.hide();
            }
        };
        LayoutInflater.from(context).inflate(resourceId, this);
        setDescendantFocusability(262144);
        int i3 = R.id.exo_progress;
        TimeBar timeBar = (TimeBar) findViewById(i3);
        View viewFindViewById = findViewById(R.id.exo_progress_placeholder);
        if (timeBar != null) {
            this.timeBar = timeBar;
        } else if (viewFindViewById != null) {
            DefaultTimeBar defaultTimeBar = new DefaultTimeBar(context, null, 0, attributeSet2);
            defaultTimeBar.setId(i3);
            defaultTimeBar.setLayoutParams(viewFindViewById.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(viewFindViewById);
            viewGroup.removeView(viewFindViewById);
            viewGroup.addView(defaultTimeBar, iIndexOfChild);
            this.timeBar = defaultTimeBar;
        } else {
            this.timeBar = null;
        }
        this.durationView = (TextView) findViewById(R.id.exo_duration);
        this.positionView = (TextView) findViewById(R.id.exo_position);
        TimeBar timeBar2 = this.timeBar;
        if (timeBar2 != null) {
            timeBar2.addListener(componentListener);
        }
        View viewFindViewById2 = findViewById(R.id.exo_play);
        this.playButton = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(componentListener);
        }
        View viewFindViewById3 = findViewById(R.id.exo_pause);
        this.pauseButton = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setOnClickListener(componentListener);
        }
        View viewFindViewById4 = findViewById(R.id.exo_prev);
        this.previousButton = viewFindViewById4;
        if (viewFindViewById4 != null) {
            viewFindViewById4.setOnClickListener(componentListener);
        }
        View viewFindViewById5 = findViewById(R.id.exo_next);
        this.nextButton = viewFindViewById5;
        if (viewFindViewById5 != null) {
            viewFindViewById5.setOnClickListener(componentListener);
        }
        View viewFindViewById6 = findViewById(R.id.exo_rew);
        this.rewindButton = viewFindViewById6;
        if (viewFindViewById6 != null) {
            viewFindViewById6.setOnClickListener(componentListener);
        }
        View viewFindViewById7 = findViewById(R.id.exo_ffwd);
        this.fastForwardButton = viewFindViewById7;
        if (viewFindViewById7 != null) {
            viewFindViewById7.setOnClickListener(componentListener);
        }
        ImageView imageView = (ImageView) findViewById(R.id.exo_repeat_toggle);
        this.repeatToggleButton = imageView;
        if (imageView != null) {
            imageView.setOnClickListener(componentListener);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_shuffle);
        this.shuffleButton = imageView2;
        if (imageView2 != null) {
            imageView2.setOnClickListener(componentListener);
        }
        View viewFindViewById8 = findViewById(R.id.exo_vr);
        this.vrButton = viewFindViewById8;
        setShowVrButton(false);
        updateButton(false, false, viewFindViewById8);
        Resources resources = context.getResources();
        this.buttonAlphaEnabled = resources.getInteger(R.integer.exo_media_button_opacity_percentage_enabled) / 100.0f;
        this.buttonAlphaDisabled = resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled) / 100.0f;
        this.repeatOffButtonDrawable = resources.getDrawable(R.drawable.exo_controls_repeat_off);
        this.repeatOneButtonDrawable = resources.getDrawable(R.drawable.exo_controls_repeat_one);
        this.repeatAllButtonDrawable = resources.getDrawable(R.drawable.exo_controls_repeat_all);
        this.shuffleOnButtonDrawable = resources.getDrawable(R.drawable.exo_controls_shuffle_on);
        this.shuffleOffButtonDrawable = resources.getDrawable(R.drawable.exo_controls_shuffle_off);
        this.repeatOffButtonContentDescription = resources.getString(R.string.exo_controls_repeat_off_description);
        this.repeatOneButtonContentDescription = resources.getString(R.string.exo_controls_repeat_one_description);
        this.repeatAllButtonContentDescription = resources.getString(R.string.exo_controls_repeat_all_description);
        this.shuffleOnContentDescription = resources.getString(R.string.exo_controls_shuffle_on_description);
        this.shuffleOffContentDescription = resources.getString(R.string.exo_controls_shuffle_off_description);
        this.currentPosition = C.TIME_UNSET;
        this.currentBufferedPosition = C.TIME_UNSET;
    }
}
