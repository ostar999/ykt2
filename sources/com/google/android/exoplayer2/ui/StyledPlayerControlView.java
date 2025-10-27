package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.ForwardingPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public class StyledPlayerControlView extends FrameLayout {
    public static final int DEFAULT_REPEAT_TOGGLE_MODES = 0;
    public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;
    public static final int DEFAULT_TIME_BAR_MIN_UPDATE_INTERVAL_MS = 200;
    private static final int MAX_UPDATE_INTERVAL_MS = 1000;
    public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR = 100;
    private static final int SETTINGS_AUDIO_TRACK_SELECTION_POSITION = 1;
    private static final int SETTINGS_PLAYBACK_SPEED_POSITION = 0;
    private long[] adGroupTimesMs;

    @Nullable
    private View audioTrackButton;
    private AudioTrackSelectionAdapter audioTrackSelectionAdapter;
    private final float buttonAlphaDisabled;
    private final float buttonAlphaEnabled;
    private final ComponentListener componentListener;
    private StyledPlayerControlViewLayoutManager controlViewLayoutManager;
    private long currentWindowOffset;

    @Nullable
    private final TextView durationView;
    private long[] extraAdGroupTimesMs;
    private boolean[] extraPlayedAdGroups;

    @Nullable
    private final View fastForwardButton;

    @Nullable
    private final TextView fastForwardButtonTextView;
    private final StringBuilder formatBuilder;
    private final Formatter formatter;

    @Nullable
    private ImageView fullScreenButton;
    private final String fullScreenEnterContentDescription;
    private final Drawable fullScreenEnterDrawable;
    private final String fullScreenExitContentDescription;
    private final Drawable fullScreenExitDrawable;
    private boolean isAttachedToWindow;
    private boolean isFullScreen;

    @Nullable
    private ImageView minimalFullScreenButton;
    private boolean multiWindowTimeBar;
    private boolean needToHideBars;

    @Nullable
    private final View nextButton;

    @Nullable
    private OnFullScreenModeChangedListener onFullScreenModeChangedListener;
    private final Timeline.Period period;

    @Nullable
    private final View playPauseButton;
    private PlaybackSpeedAdapter playbackSpeedAdapter;

    @Nullable
    private View playbackSpeedButton;
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
    private Resources resources;

    @Nullable
    private final View rewindButton;

    @Nullable
    private final TextView rewindButtonTextView;
    private boolean scrubbing;
    private SettingsAdapter settingsAdapter;

    @Nullable
    private View settingsButton;
    private RecyclerView settingsView;
    private PopupWindow settingsWindow;
    private int settingsWindowMargin;
    private boolean showMultiWindowTimeBar;
    private int showTimeoutMs;

    @Nullable
    private final ImageView shuffleButton;
    private final Drawable shuffleOffButtonDrawable;
    private final String shuffleOffContentDescription;
    private final Drawable shuffleOnButtonDrawable;
    private final String shuffleOnContentDescription;

    @Nullable
    private ImageView subtitleButton;
    private final Drawable subtitleOffButtonDrawable;
    private final String subtitleOffContentDescription;
    private final Drawable subtitleOnButtonDrawable;
    private final String subtitleOnContentDescription;
    private TextTrackSelectionAdapter textTrackSelectionAdapter;

    @Nullable
    private final TimeBar timeBar;
    private int timeBarMinUpdateIntervalMs;
    private TrackNameProvider trackNameProvider;
    private final Runnable updateProgressAction;
    private final CopyOnWriteArrayList<VisibilityListener> visibilityListeners;

    @Nullable
    private final View vrButton;
    private final Timeline.Window window;

    public final class AudioTrackSelectionAdapter extends TrackSelectionAdapter {
        private AudioTrackSelectionAdapter() {
            super();
        }

        private boolean hasSelectionOverride(TrackSelectionOverrides trackSelectionOverrides) {
            for (int i2 = 0; i2 < this.tracks.size(); i2++) {
                if (trackSelectionOverrides.getOverride(this.tracks.get(i2).trackGroupInfo.getTrackGroup()) != null) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolderAtZeroPosition$0(View view) {
            if (StyledPlayerControlView.this.player == null) {
                return;
            }
            TrackSelectionParameters trackSelectionParameters = StyledPlayerControlView.this.player.getTrackSelectionParameters();
            TrackSelectionOverrides trackSelectionOverridesBuild = trackSelectionParameters.trackSelectionOverrides.buildUpon().clearOverridesOfType(1).build();
            HashSet hashSet = new HashSet(trackSelectionParameters.disabledTrackTypes);
            hashSet.remove(1);
            ((Player) Util.castNonNull(StyledPlayerControlView.this.player)).setTrackSelectionParameters(trackSelectionParameters.buildUpon().setTrackSelectionOverrides(trackSelectionOverridesBuild).setDisabledTrackTypes(hashSet).build());
            StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_auto));
            StyledPlayerControlView.this.settingsWindow.dismiss();
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void init(List<TrackInformation> list) {
            this.tracks = list;
            TrackSelectionParameters trackSelectionParameters = ((Player) Assertions.checkNotNull(StyledPlayerControlView.this.player)).getTrackSelectionParameters();
            if (list.isEmpty()) {
                StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_none));
                return;
            }
            if (!hasSelectionOverride(trackSelectionParameters.trackSelectionOverrides)) {
                StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, StyledPlayerControlView.this.getResources().getString(R.string.exo_track_selection_auto));
                return;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                TrackInformation trackInformation = list.get(i2);
                if (trackInformation.isSelected()) {
                    StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, trackInformation.trackName);
                    return;
                }
            }
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onBindViewHolderAtZeroPosition(SubSettingViewHolder subSettingViewHolder) {
            subSettingViewHolder.textView.setText(R.string.exo_track_selection_auto);
            subSettingViewHolder.checkView.setVisibility(hasSelectionOverride(((Player) Assertions.checkNotNull(StyledPlayerControlView.this.player)).getTrackSelectionParameters().trackSelectionOverrides) ? 4 : 0);
            subSettingViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.n
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f6951c.lambda$onBindViewHolderAtZeroPosition$0(view);
                }
            });
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onTrackSelection(String str) {
            StyledPlayerControlView.this.settingsAdapter.setSubTextAtPosition(1, str);
        }
    }

    public final class ComponentListener implements Player.Listener, TimeBar.OnScrubListener, View.OnClickListener, PopupWindow.OnDismissListener {
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
            Player player = StyledPlayerControlView.this.player;
            if (player == null) {
                return;
            }
            StyledPlayerControlView.this.controlViewLayoutManager.resetHideCallbacks();
            if (StyledPlayerControlView.this.nextButton == view) {
                player.seekToNext();
                return;
            }
            if (StyledPlayerControlView.this.previousButton == view) {
                player.seekToPrevious();
                return;
            }
            if (StyledPlayerControlView.this.fastForwardButton == view) {
                if (player.getPlaybackState() != 4) {
                    player.seekForward();
                    return;
                }
                return;
            }
            if (StyledPlayerControlView.this.rewindButton == view) {
                player.seekBack();
                return;
            }
            if (StyledPlayerControlView.this.playPauseButton == view) {
                StyledPlayerControlView.this.dispatchPlayPause(player);
                return;
            }
            if (StyledPlayerControlView.this.repeatToggleButton == view) {
                player.setRepeatMode(RepeatModeUtil.getNextRepeatMode(player.getRepeatMode(), StyledPlayerControlView.this.repeatToggleModes));
                return;
            }
            if (StyledPlayerControlView.this.shuffleButton == view) {
                player.setShuffleModeEnabled(!player.getShuffleModeEnabled());
                return;
            }
            if (StyledPlayerControlView.this.settingsButton == view) {
                StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                styledPlayerControlView.displaySettingsWindow(styledPlayerControlView.settingsAdapter);
                return;
            }
            if (StyledPlayerControlView.this.playbackSpeedButton == view) {
                StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                StyledPlayerControlView styledPlayerControlView2 = StyledPlayerControlView.this;
                styledPlayerControlView2.displaySettingsWindow(styledPlayerControlView2.playbackSpeedAdapter);
            } else if (StyledPlayerControlView.this.audioTrackButton == view) {
                StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                StyledPlayerControlView styledPlayerControlView3 = StyledPlayerControlView.this;
                styledPlayerControlView3.displaySettingsWindow(styledPlayerControlView3.audioTrackSelectionAdapter);
            } else if (StyledPlayerControlView.this.subtitleButton == view) {
                StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
                StyledPlayerControlView styledPlayerControlView4 = StyledPlayerControlView.this;
                styledPlayerControlView4.displaySettingsWindow(styledPlayerControlView4.textTrackSelectionAdapter);
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

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            if (StyledPlayerControlView.this.needToHideBars) {
                StyledPlayerControlView.this.controlViewLayoutManager.resetHideCallbacks();
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(4, 5)) {
                StyledPlayerControlView.this.updatePlayPauseButton();
            }
            if (events.containsAny(4, 5, 7)) {
                StyledPlayerControlView.this.updateProgress();
            }
            if (events.contains(8)) {
                StyledPlayerControlView.this.updateRepeatModeButton();
            }
            if (events.contains(9)) {
                StyledPlayerControlView.this.updateShuffleButton();
            }
            if (events.containsAny(8, 9, 11, 0, 16, 17, 13)) {
                StyledPlayerControlView.this.updateNavigation();
            }
            if (events.containsAny(11, 0)) {
                StyledPlayerControlView.this.updateTimeline();
            }
            if (events.contains(12)) {
                StyledPlayerControlView.this.updatePlaybackSpeedList();
            }
            if (events.contains(2)) {
                StyledPlayerControlView.this.updateTrackLists();
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
            if (StyledPlayerControlView.this.positionView != null) {
                StyledPlayerControlView.this.positionView.setText(Util.getStringForTime(StyledPlayerControlView.this.formatBuilder, StyledPlayerControlView.this.formatter, j2));
            }
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStart(TimeBar timeBar, long j2) {
            StyledPlayerControlView.this.scrubbing = true;
            if (StyledPlayerControlView.this.positionView != null) {
                StyledPlayerControlView.this.positionView.setText(Util.getStringForTime(StyledPlayerControlView.this.formatBuilder, StyledPlayerControlView.this.formatter, j2));
            }
            StyledPlayerControlView.this.controlViewLayoutManager.removeHideCallbacks();
        }

        @Override // com.google.android.exoplayer2.ui.TimeBar.OnScrubListener
        public void onScrubStop(TimeBar timeBar, long j2, boolean z2) {
            StyledPlayerControlView.this.scrubbing = false;
            if (!z2 && StyledPlayerControlView.this.player != null) {
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                styledPlayerControlView.seekToTimeBarPosition(styledPlayerControlView.player, j2);
            }
            StyledPlayerControlView.this.controlViewLayoutManager.resetHideCallbacks();
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

    public interface OnFullScreenModeChangedListener {
        void onFullScreenModeChanged(boolean z2);
    }

    public final class PlaybackSpeedAdapter extends RecyclerView.Adapter<SubSettingViewHolder> {
        private final String[] playbackSpeedTexts;
        private final int[] playbackSpeedsMultBy100;
        private int selectedIndex;

        public PlaybackSpeedAdapter(String[] strArr, int[] iArr) {
            this.playbackSpeedTexts = strArr;
            this.playbackSpeedsMultBy100 = iArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolder$0(int i2, View view) {
            if (i2 != this.selectedIndex) {
                StyledPlayerControlView.this.setPlaybackSpeed(this.playbackSpeedsMultBy100[i2] / 100.0f);
            }
            StyledPlayerControlView.this.settingsWindow.dismiss();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.playbackSpeedTexts.length;
        }

        public String getSelectedText() {
            return this.playbackSpeedTexts[this.selectedIndex];
        }

        public void updateSelectedIndex(float f2) {
            int iRound = Math.round(f2 * 100.0f);
            int i2 = 0;
            int i3 = Integer.MAX_VALUE;
            int i4 = 0;
            while (true) {
                int[] iArr = this.playbackSpeedsMultBy100;
                if (i2 >= iArr.length) {
                    this.selectedIndex = i4;
                    return;
                }
                int iAbs = Math.abs(iRound - iArr[i2]);
                if (iAbs < i3) {
                    i4 = i2;
                    i3 = iAbs;
                }
                i2++;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SubSettingViewHolder subSettingViewHolder, final int i2) {
            String[] strArr = this.playbackSpeedTexts;
            if (i2 < strArr.length) {
                subSettingViewHolder.textView.setText(strArr[i2]);
            }
            subSettingViewHolder.checkView.setVisibility(i2 == this.selectedIndex ? 0 : 4);
            subSettingViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.o
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f6952c.lambda$onBindViewHolder$0(i2, view);
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SubSettingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
            return new SubSettingViewHolder(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, viewGroup, false));
        }
    }

    public interface ProgressUpdateListener {
        void onProgressUpdate(long j2, long j3);
    }

    public final class SettingViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconView;
        private final TextView mainTextView;
        private final TextView subTextView;

        public SettingViewHolder(View view) {
            super(view);
            if (Util.SDK_INT < 26) {
                view.setFocusable(true);
            }
            this.mainTextView = (TextView) view.findViewById(R.id.exo_main_text);
            this.subTextView = (TextView) view.findViewById(R.id.exo_sub_text);
            this.iconView = (ImageView) view.findViewById(R.id.exo_icon);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.p
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f6954c.lambda$new$0(view2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            StyledPlayerControlView.this.onSettingViewClicked(getAdapterPosition());
        }
    }

    public class SettingsAdapter extends RecyclerView.Adapter<SettingViewHolder> {
        private final Drawable[] iconIds;
        private final String[] mainTexts;
        private final String[] subTexts;

        public SettingsAdapter(String[] strArr, Drawable[] drawableArr) {
            this.mainTexts = strArr;
            this.subTexts = new String[strArr.length];
            this.iconIds = drawableArr;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mainTexts.length;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        public void setSubTextAtPosition(int i2, String str) {
            this.subTexts[i2] = str;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SettingViewHolder settingViewHolder, int i2) {
            settingViewHolder.mainTextView.setText(this.mainTexts[i2]);
            if (this.subTexts[i2] == null) {
                settingViewHolder.subTextView.setVisibility(8);
            } else {
                settingViewHolder.subTextView.setText(this.subTexts[i2]);
            }
            if (this.iconIds[i2] == null) {
                settingViewHolder.iconView.setVisibility(8);
            } else {
                settingViewHolder.iconView.setImageDrawable(this.iconIds[i2]);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SettingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
            return StyledPlayerControlView.this.new SettingViewHolder(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_settings_list_item, viewGroup, false));
        }
    }

    public static class SubSettingViewHolder extends RecyclerView.ViewHolder {
        public final View checkView;
        public final TextView textView;

        public SubSettingViewHolder(View view) {
            super(view);
            if (Util.SDK_INT < 26) {
                view.setFocusable(true);
            }
            this.textView = (TextView) view.findViewById(R.id.exo_text);
            this.checkView = view.findViewById(R.id.exo_check);
        }
    }

    public final class TextTrackSelectionAdapter extends TrackSelectionAdapter {
        private TextTrackSelectionAdapter() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolderAtZeroPosition$0(View view) {
            if (StyledPlayerControlView.this.player != null) {
                TrackSelectionParameters trackSelectionParameters = StyledPlayerControlView.this.player.getTrackSelectionParameters();
                StyledPlayerControlView.this.player.setTrackSelectionParameters(trackSelectionParameters.buildUpon().setDisabledTrackTypes(new ImmutableSet.Builder().addAll((Iterable) trackSelectionParameters.disabledTrackTypes).add((ImmutableSet.Builder) 3).build()).build());
                StyledPlayerControlView.this.settingsWindow.dismiss();
            }
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void init(List<TrackInformation> list) {
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    break;
                }
                if (list.get(i2).isSelected()) {
                    z2 = true;
                    break;
                }
                i2++;
            }
            if (StyledPlayerControlView.this.subtitleButton != null) {
                ImageView imageView = StyledPlayerControlView.this.subtitleButton;
                StyledPlayerControlView styledPlayerControlView = StyledPlayerControlView.this;
                imageView.setImageDrawable(z2 ? styledPlayerControlView.subtitleOnButtonDrawable : styledPlayerControlView.subtitleOffButtonDrawable);
                StyledPlayerControlView.this.subtitleButton.setContentDescription(z2 ? StyledPlayerControlView.this.subtitleOnContentDescription : StyledPlayerControlView.this.subtitleOffContentDescription);
            }
            this.tracks = list;
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onBindViewHolderAtZeroPosition(SubSettingViewHolder subSettingViewHolder) {
            boolean z2;
            subSettingViewHolder.textView.setText(R.string.exo_track_selection_none);
            int i2 = 0;
            while (true) {
                if (i2 >= this.tracks.size()) {
                    z2 = true;
                    break;
                } else {
                    if (this.tracks.get(i2).isSelected()) {
                        z2 = false;
                        break;
                    }
                    i2++;
                }
            }
            subSettingViewHolder.checkView.setVisibility(z2 ? 0 : 4);
            subSettingViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.q
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f6955c.lambda$onBindViewHolderAtZeroPosition$0(view);
                }
            });
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter
        public void onTrackSelection(String str) {
        }

        @Override // com.google.android.exoplayer2.ui.StyledPlayerControlView.TrackSelectionAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SubSettingViewHolder subSettingViewHolder, int i2) {
            super.onBindViewHolder(subSettingViewHolder, i2);
            if (i2 > 0) {
                subSettingViewHolder.checkView.setVisibility(this.tracks.get(i2 + (-1)).isSelected() ? 0 : 4);
            }
        }
    }

    public static final class TrackInformation {
        public final TracksInfo.TrackGroupInfo trackGroupInfo;
        public final int trackIndex;
        public final String trackName;

        public TrackInformation(TracksInfo tracksInfo, int i2, int i3, String str) {
            this.trackGroupInfo = tracksInfo.getTrackGroupInfos().get(i2);
            this.trackIndex = i3;
            this.trackName = str;
        }

        public boolean isSelected() {
            return this.trackGroupInfo.isTrackSelected(this.trackIndex);
        }
    }

    public abstract class TrackSelectionAdapter extends RecyclerView.Adapter<SubSettingViewHolder> {
        protected List<TrackInformation> tracks = new ArrayList();

        public TrackSelectionAdapter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolder$0(TrackGroup trackGroup, TrackInformation trackInformation, View view) {
            if (StyledPlayerControlView.this.player == null) {
                return;
            }
            TrackSelectionParameters trackSelectionParameters = StyledPlayerControlView.this.player.getTrackSelectionParameters();
            TrackSelectionOverrides trackSelectionOverridesBuild = trackSelectionParameters.trackSelectionOverrides.buildUpon().setOverrideForType(new TrackSelectionOverrides.TrackSelectionOverride(trackGroup, ImmutableList.of(Integer.valueOf(trackInformation.trackIndex)))).build();
            HashSet hashSet = new HashSet(trackSelectionParameters.disabledTrackTypes);
            hashSet.remove(Integer.valueOf(trackInformation.trackGroupInfo.getTrackType()));
            ((Player) Assertions.checkNotNull(StyledPlayerControlView.this.player)).setTrackSelectionParameters(trackSelectionParameters.buildUpon().setTrackSelectionOverrides(trackSelectionOverridesBuild).setDisabledTrackTypes(hashSet).build());
            onTrackSelection(trackInformation.trackName);
            StyledPlayerControlView.this.settingsWindow.dismiss();
        }

        public void clear() {
            this.tracks = Collections.emptyList();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (this.tracks.isEmpty()) {
                return 0;
            }
            return this.tracks.size() + 1;
        }

        public abstract void init(List<TrackInformation> list);

        public abstract void onBindViewHolderAtZeroPosition(SubSettingViewHolder subSettingViewHolder);

        public abstract void onTrackSelection(String str);

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(SubSettingViewHolder subSettingViewHolder, int i2) {
            if (StyledPlayerControlView.this.player == null) {
                return;
            }
            if (i2 == 0) {
                onBindViewHolderAtZeroPosition(subSettingViewHolder);
                return;
            }
            final TrackInformation trackInformation = this.tracks.get(i2 - 1);
            final TrackGroup trackGroup = trackInformation.trackGroupInfo.getTrackGroup();
            boolean z2 = ((Player) Assertions.checkNotNull(StyledPlayerControlView.this.player)).getTrackSelectionParameters().trackSelectionOverrides.getOverride(trackGroup) != null && trackInformation.isSelected();
            subSettingViewHolder.textView.setText(trackInformation.trackName);
            subSettingViewHolder.checkView.setVisibility(z2 ? 0 : 4);
            subSettingViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f6956c.lambda$onBindViewHolder$0(trackGroup, trackInformation, view);
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public SubSettingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
            return new SubSettingViewHolder(LayoutInflater.from(StyledPlayerControlView.this.getContext()).inflate(R.layout.exo_styled_sub_settings_list_item, viewGroup, false));
        }
    }

    public interface VisibilityListener {
        void onVisibilityChange(int i2);
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.ui");
    }

    public StyledPlayerControlView(Context context) {
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

    private void dispatchPause(Player player) {
        player.pause();
    }

    private void dispatchPlay(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1) {
            player.prepare();
        } else if (playbackState == 4) {
            seekTo(player, player.getCurrentMediaItemIndex(), C.TIME_UNSET);
        }
        player.play();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPlayPause(Player player) {
        int playbackState = player.getPlaybackState();
        if (playbackState == 1 || playbackState == 4 || !player.getPlayWhenReady()) {
            dispatchPlay(player);
        } else {
            dispatchPause(player);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displaySettingsWindow(RecyclerView.Adapter<?> adapter) {
        this.settingsView.setAdapter(adapter);
        updateSettingsWindowSize();
        this.needToHideBars = false;
        this.settingsWindow.dismiss();
        this.needToHideBars = true;
        this.settingsWindow.showAsDropDown(this, (getWidth() - this.settingsWindow.getWidth()) - this.settingsWindowMargin, (-this.settingsWindow.getHeight()) - this.settingsWindowMargin);
    }

    private ImmutableList<TrackInformation> gatherSupportedTrackInfosOfType(TracksInfo tracksInfo, int i2) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        ImmutableList<TracksInfo.TrackGroupInfo> trackGroupInfos = tracksInfo.getTrackGroupInfos();
        for (int i3 = 0; i3 < trackGroupInfos.size(); i3++) {
            TracksInfo.TrackGroupInfo trackGroupInfo = trackGroupInfos.get(i3);
            if (trackGroupInfo.getTrackType() == i2) {
                TrackGroup trackGroup = trackGroupInfo.getTrackGroup();
                for (int i4 = 0; i4 < trackGroup.length; i4++) {
                    if (trackGroupInfo.isTrackSupported(i4)) {
                        builder.add((ImmutableList.Builder) new TrackInformation(tracksInfo, i3, i4, this.trackNameProvider.getTrackName(trackGroup.getFormat(i4))));
                    }
                }
            }
        }
        return builder.build();
    }

    private void initTrackSelectionAdapter() {
        this.textTrackSelectionAdapter.clear();
        this.audioTrackSelectionAdapter.clear();
        Player player = this.player;
        if (player != null && player.isCommandAvailable(30) && this.player.isCommandAvailable(29)) {
            TracksInfo currentTracksInfo = this.player.getCurrentTracksInfo();
            this.audioTrackSelectionAdapter.init(gatherSupportedTrackInfosOfType(currentTracksInfo, 1));
            if (this.controlViewLayoutManager.getShowButton(this.subtitleButton)) {
                this.textTrackSelectionAdapter.init(gatherSupportedTrackInfosOfType(currentTracksInfo, 3));
            } else {
                this.textTrackSelectionAdapter.init(ImmutableList.of());
            }
        }
    }

    private static void initializeFullScreenButton(View view, View.OnClickListener onClickListener) {
        if (view == null) {
            return;
        }
        view.setVisibility(8);
        view.setOnClickListener(onClickListener);
    }

    @SuppressLint({"InlinedApi"})
    private static boolean isHandledMediaKey(int i2) {
        return i2 == 90 || i2 == 89 || i2 == 85 || i2 == 79 || i2 == 126 || i2 == 127 || i2 == 87 || i2 == 88;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFullScreenButtonClicked(View view) {
        if (this.onFullScreenModeChangedListener == null) {
            return;
        }
        boolean z2 = !this.isFullScreen;
        this.isFullScreen = z2;
        updateFullScreenButtonForState(this.fullScreenButton, z2);
        updateFullScreenButtonForState(this.minimalFullScreenButton, this.isFullScreen);
        OnFullScreenModeChangedListener onFullScreenModeChangedListener = this.onFullScreenModeChangedListener;
        if (onFullScreenModeChangedListener != null) {
            onFullScreenModeChangedListener.onFullScreenModeChanged(this.isFullScreen);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        int i10 = i5 - i3;
        int i11 = i9 - i7;
        if (!(i4 - i2 == i8 - i6 && i10 == i11) && this.settingsWindow.isShowing()) {
            updateSettingsWindowSize();
            this.settingsWindow.update(view, (getWidth() - this.settingsWindow.getWidth()) - this.settingsWindowMargin, (-this.settingsWindow.getHeight()) - this.settingsWindowMargin, -1, -1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSettingViewClicked(int i2) {
        if (i2 == 0) {
            displaySettingsWindow(this.playbackSpeedAdapter);
        } else if (i2 == 1) {
            displaySettingsWindow(this.audioTrackSelectionAdapter);
        } else {
            this.settingsWindow.dismiss();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlaybackSpeed(float f2) {
        Player player = this.player;
        if (player == null) {
            return;
        }
        player.setPlaybackParameters(player.getPlaybackParameters().withSpeed(f2));
    }

    private boolean shouldShowPauseButton() {
        Player player = this.player;
        return (player == null || player.getPlaybackState() == 4 || this.player.getPlaybackState() == 1 || !this.player.getPlayWhenReady()) ? false : true;
    }

    private void updateButton(boolean z2, @Nullable View view) {
        if (view == null) {
            return;
        }
        view.setEnabled(z2);
        view.setAlpha(z2 ? this.buttonAlphaEnabled : this.buttonAlphaDisabled);
    }

    private void updateFastForwardButton() {
        Player player = this.player;
        int seekForwardIncrement = (int) ((player != null ? player.getSeekForwardIncrement() : C.DEFAULT_SEEK_FORWARD_INCREMENT_MS) / 1000);
        TextView textView = this.fastForwardButtonTextView;
        if (textView != null) {
            textView.setText(String.valueOf(seekForwardIncrement));
        }
        View view = this.fastForwardButton;
        if (view != null) {
            view.setContentDescription(this.resources.getQuantityString(R.plurals.exo_controls_fastforward_by_amount_description, seekForwardIncrement, Integer.valueOf(seekForwardIncrement)));
        }
    }

    private void updateFullScreenButtonForState(@Nullable ImageView imageView, boolean z2) {
        if (imageView == null) {
            return;
        }
        if (z2) {
            imageView.setImageDrawable(this.fullScreenExitDrawable);
            imageView.setContentDescription(this.fullScreenExitContentDescription);
        } else {
            imageView.setImageDrawable(this.fullScreenEnterDrawable);
            imageView.setContentDescription(this.fullScreenEnterContentDescription);
        }
    }

    private static void updateFullScreenButtonVisibility(@Nullable View view, boolean z2) {
        if (view == null) {
            return;
        }
        if (z2) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
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
            if (zIsCommandAvailable4) {
                updateRewindButton();
            }
            if (zIsCommandAvailable5) {
                updateFastForwardButton();
            }
            updateButton(zIsCommandAvailable3, this.previousButton);
            updateButton(zIsCommandAvailable4, this.rewindButton);
            updateButton(zIsCommandAvailable5, this.fastForwardButton);
            updateButton(zIsCommandAvailable2, this.nextButton);
            TimeBar timeBar = this.timeBar;
            if (timeBar != null) {
                timeBar.setEnabled(zIsCommandAvailable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlayPauseButton() {
        if (isVisible() && this.isAttachedToWindow && this.playPauseButton != null) {
            if (shouldShowPauseButton()) {
                ((ImageView) this.playPauseButton).setImageDrawable(this.resources.getDrawable(R.drawable.exo_styled_controls_pause));
                this.playPauseButton.setContentDescription(this.resources.getString(R.string.exo_controls_pause_description));
            } else {
                ((ImageView) this.playPauseButton).setImageDrawable(this.resources.getDrawable(R.drawable.exo_styled_controls_play));
                this.playPauseButton.setContentDescription(this.resources.getString(R.string.exo_controls_play_description));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePlaybackSpeedList() {
        Player player = this.player;
        if (player == null) {
            return;
        }
        this.playbackSpeedAdapter.updateSelectedIndex(player.getPlaybackParameters().speed);
        this.settingsAdapter.setSubTextAtPosition(0, this.playbackSpeedAdapter.getSelectedText());
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
            TextView textView = this.positionView;
            if (textView != null && !this.scrubbing) {
                textView.setText(Util.getStringForTime(this.formatBuilder, this.formatter, contentPosition));
            }
            TimeBar timeBar = this.timeBar;
            if (timeBar != null) {
                timeBar.setPosition(contentPosition);
                this.timeBar.setBufferedPosition(contentBufferedPosition);
            }
            ProgressUpdateListener progressUpdateListener = this.progressUpdateListener;
            if (progressUpdateListener != null) {
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
                updateButton(false, imageView);
                return;
            }
            Player player = this.player;
            if (player == null) {
                updateButton(false, imageView);
                this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
                return;
            }
            updateButton(true, imageView);
            int repeatMode = player.getRepeatMode();
            if (repeatMode == 0) {
                this.repeatToggleButton.setImageDrawable(this.repeatOffButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOffButtonContentDescription);
            } else if (repeatMode == 1) {
                this.repeatToggleButton.setImageDrawable(this.repeatOneButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatOneButtonContentDescription);
            } else {
                if (repeatMode != 2) {
                    return;
                }
                this.repeatToggleButton.setImageDrawable(this.repeatAllButtonDrawable);
                this.repeatToggleButton.setContentDescription(this.repeatAllButtonContentDescription);
            }
        }
    }

    private void updateRewindButton() {
        Player player = this.player;
        int seekBackIncrement = (int) ((player != null ? player.getSeekBackIncrement() : 5000L) / 1000);
        TextView textView = this.rewindButtonTextView;
        if (textView != null) {
            textView.setText(String.valueOf(seekBackIncrement));
        }
        View view = this.rewindButton;
        if (view != null) {
            view.setContentDescription(this.resources.getQuantityString(R.plurals.exo_controls_rewind_by_amount_description, seekBackIncrement, Integer.valueOf(seekBackIncrement)));
        }
    }

    private void updateSettingsWindowSize() {
        this.settingsView.measure(0, 0);
        this.settingsWindow.setWidth(Math.min(this.settingsView.getMeasuredWidth(), getWidth() - (this.settingsWindowMargin * 2)));
        this.settingsWindow.setHeight(Math.min(getHeight() - (this.settingsWindowMargin * 2), this.settingsView.getMeasuredHeight()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShuffleButton() {
        ImageView imageView;
        if (isVisible() && this.isAttachedToWindow && (imageView = this.shuffleButton) != null) {
            Player player = this.player;
            if (!this.controlViewLayoutManager.getShowButton(imageView)) {
                updateButton(false, this.shuffleButton);
                return;
            }
            if (player == null) {
                updateButton(false, this.shuffleButton);
                this.shuffleButton.setImageDrawable(this.shuffleOffButtonDrawable);
                this.shuffleButton.setContentDescription(this.shuffleOffContentDescription);
            } else {
                updateButton(true, this.shuffleButton);
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.StyledPlayerControlView.updateTimeline():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTrackLists() {
        initTrackSelectionAdapter();
        updateButton(this.textTrackSelectionAdapter.getItemCount() > 0, this.subtitleButton);
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

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    public int getRepeatToggleModes() {
        return this.repeatToggleModes;
    }

    public boolean getShowShuffleButton() {
        return this.controlViewLayoutManager.getShowButton(this.shuffleButton);
    }

    public boolean getShowSubtitleButton() {
        return this.controlViewLayoutManager.getShowButton(this.subtitleButton);
    }

    public int getShowTimeoutMs() {
        return this.showTimeoutMs;
    }

    public boolean getShowVrButton() {
        return this.controlViewLayoutManager.getShowButton(this.vrButton);
    }

    public void hide() {
        this.controlViewLayoutManager.hide();
    }

    public void hideImmediately() {
        this.controlViewLayoutManager.hideImmediately();
    }

    public boolean isAnimationEnabled() {
        return this.controlViewLayoutManager.isAnimationEnabled();
    }

    public boolean isFullyVisible() {
        return this.controlViewLayoutManager.isFullyVisible();
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public void notifyOnVisibilityChange() {
        Iterator<VisibilityListener> it = this.visibilityListeners.iterator();
        while (it.hasNext()) {
            it.next().onVisibilityChange(getVisibility());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.controlViewLayoutManager.onAttachedToWindow();
        this.isAttachedToWindow = true;
        if (isFullyVisible()) {
            this.controlViewLayoutManager.resetHideCallbacks();
        }
        updateAll();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.controlViewLayoutManager.onDetachedFromWindow();
        this.isAttachedToWindow = false;
        removeCallbacks(this.updateProgressAction);
        this.controlViewLayoutManager.removeHideCallbacks();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        this.controlViewLayoutManager.onLayout(z2, i2, i3, i4, i5);
    }

    public void removeVisibilityListener(VisibilityListener visibilityListener) {
        this.visibilityListeners.remove(visibilityListener);
    }

    public void requestPlayPauseFocus() {
        View view = this.playPauseButton;
        if (view != null) {
            view.requestFocus();
        }
    }

    public void setAnimationEnabled(boolean z2) {
        this.controlViewLayoutManager.setAnimationEnabled(z2);
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

    public void setOnFullScreenModeChangedListener(@Nullable OnFullScreenModeChangedListener onFullScreenModeChangedListener) {
        this.onFullScreenModeChangedListener = onFullScreenModeChangedListener;
        updateFullScreenButtonVisibility(this.fullScreenButton, onFullScreenModeChangedListener != null);
        updateFullScreenButtonVisibility(this.minimalFullScreenButton, onFullScreenModeChangedListener != null);
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
        if (player instanceof ForwardingPlayer) {
            ((ForwardingPlayer) player).getWrappedPlayer();
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
        this.controlViewLayoutManager.setShowButton(this.repeatToggleButton, i2 != 0);
        updateRepeatModeButton();
    }

    public void setShowFastForwardButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.fastForwardButton, z2);
        updateNavigation();
    }

    public void setShowMultiWindowTimeBar(boolean z2) {
        this.showMultiWindowTimeBar = z2;
        updateTimeline();
    }

    public void setShowNextButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.nextButton, z2);
        updateNavigation();
    }

    public void setShowPreviousButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.previousButton, z2);
        updateNavigation();
    }

    public void setShowRewindButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.rewindButton, z2);
        updateNavigation();
    }

    public void setShowShuffleButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.shuffleButton, z2);
        updateShuffleButton();
    }

    public void setShowSubtitleButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.subtitleButton, z2);
    }

    public void setShowTimeoutMs(int i2) {
        this.showTimeoutMs = i2;
        if (isFullyVisible()) {
            this.controlViewLayoutManager.resetHideCallbacks();
        }
    }

    public void setShowVrButton(boolean z2) {
        this.controlViewLayoutManager.setShowButton(this.vrButton, z2);
    }

    public void setTimeBarMinUpdateInterval(int i2) {
        this.timeBarMinUpdateIntervalMs = Util.constrainValue(i2, 16, 1000);
    }

    public void setVrButtonListener(@Nullable View.OnClickListener onClickListener) {
        View view = this.vrButton;
        if (view != null) {
            view.setOnClickListener(onClickListener);
            updateButton(onClickListener != null, this.vrButton);
        }
    }

    public void show() {
        this.controlViewLayoutManager.show();
    }

    public void updateAll() {
        updatePlayPauseButton();
        updateNavigation();
        updateRepeatModeButton();
        updateShuffleButton();
        updateTrackLists();
        updatePlaybackSpeedList();
        updateTimeline();
    }

    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private static int getRepeatToggleModes(TypedArray typedArray, int i2) {
        return typedArray.getInt(R.styleable.StyledPlayerControlView_repeat_toggle_modes, i2);
    }

    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        this(context, attributeSet, i2, attributeSet);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.view.ViewGroup, com.google.android.exoplayer2.ui.StyledPlayerControlView$1] */
    /* JADX WARN: Type inference failed for: r9v4 */
    public StyledPlayerControlView(Context context, @Nullable AttributeSet attributeSet, int i2, @Nullable AttributeSet attributeSet2) throws Resources.NotFoundException {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        ComponentListener componentListener;
        boolean z10;
        boolean z11;
        ?? r9;
        boolean z12;
        super(context, attributeSet, i2);
        int resourceId = R.layout.exo_styled_player_control_view;
        this.showTimeoutMs = 5000;
        this.repeatToggleModes = 0;
        this.timeBarMinUpdateIntervalMs = 200;
        if (attributeSet2 != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet2, R.styleable.StyledPlayerControlView, i2, 0);
            try {
                resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.StyledPlayerControlView_controller_layout_id, resourceId);
                this.showTimeoutMs = typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerControlView_show_timeout, this.showTimeoutMs);
                this.repeatToggleModes = getRepeatToggleModes(typedArrayObtainStyledAttributes, this.repeatToggleModes);
                boolean z13 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_rewind_button, true);
                boolean z14 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_fastforward_button, true);
                boolean z15 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_previous_button, true);
                boolean z16 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_next_button, true);
                boolean z17 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_shuffle_button, false);
                boolean z18 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_subtitle_button, false);
                boolean z19 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_show_vr_button, false);
                setTimeBarMinUpdateInterval(typedArrayObtainStyledAttributes.getInt(R.styleable.StyledPlayerControlView_time_bar_min_update_interval, this.timeBarMinUpdateIntervalMs));
                boolean z20 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyledPlayerControlView_animation_enabled, true);
                typedArrayObtainStyledAttributes.recycle();
                z3 = z17;
                z4 = z18;
                z6 = z13;
                z7 = z14;
                z8 = z15;
                z5 = z20;
                z9 = z16;
                z2 = z19;
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            z2 = false;
            z3 = false;
            z4 = false;
            z5 = true;
            z6 = true;
            z7 = true;
            z8 = true;
            z9 = true;
        }
        LayoutInflater.from(context).inflate(resourceId, this);
        setDescendantFocusability(262144);
        ComponentListener componentListener2 = new ComponentListener();
        this.componentListener = componentListener2;
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
        this.updateProgressAction = new Runnable() { // from class: com.google.android.exoplayer2.ui.k
            @Override // java.lang.Runnable
            public final void run() {
                this.f6947c.updateProgress();
            }
        };
        this.durationView = (TextView) findViewById(R.id.exo_duration);
        this.positionView = (TextView) findViewById(R.id.exo_position);
        ImageView imageView = (ImageView) findViewById(R.id.exo_subtitle);
        this.subtitleButton = imageView;
        if (imageView != null) {
            imageView.setOnClickListener(componentListener2);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_fullscreen);
        this.fullScreenButton = imageView2;
        initializeFullScreenButton(imageView2, new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f6949c.onFullScreenButtonClicked(view);
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.exo_minimal_fullscreen);
        this.minimalFullScreenButton = imageView3;
        initializeFullScreenButton(imageView3, new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f6949c.onFullScreenButtonClicked(view);
            }
        });
        View viewFindViewById = findViewById(R.id.exo_settings);
        this.settingsButton = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(componentListener2);
        }
        View viewFindViewById2 = findViewById(R.id.exo_playback_speed);
        this.playbackSpeedButton = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setOnClickListener(componentListener2);
        }
        View viewFindViewById3 = findViewById(R.id.exo_audio_track);
        this.audioTrackButton = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setOnClickListener(componentListener2);
        }
        int i3 = R.id.exo_progress;
        TimeBar timeBar = (TimeBar) findViewById(i3);
        View viewFindViewById4 = findViewById(R.id.exo_progress_placeholder);
        if (timeBar != null) {
            this.timeBar = timeBar;
            componentListener = componentListener2;
            z10 = z5;
            z11 = z2;
            r9 = 0;
        } else if (viewFindViewById4 != null) {
            r9 = 0;
            componentListener = componentListener2;
            z10 = z5;
            z11 = z2;
            DefaultTimeBar defaultTimeBar = new DefaultTimeBar(context, null, 0, attributeSet2, R.style.ExoStyledControls_TimeBar);
            defaultTimeBar.setId(i3);
            defaultTimeBar.setLayoutParams(viewFindViewById4.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) viewFindViewById4.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(viewFindViewById4);
            viewGroup.removeView(viewFindViewById4);
            viewGroup.addView(defaultTimeBar, iIndexOfChild);
            this.timeBar = defaultTimeBar;
        } else {
            componentListener = componentListener2;
            z10 = z5;
            z11 = z2;
            r9 = 0;
            this.timeBar = null;
        }
        TimeBar timeBar2 = this.timeBar;
        ComponentListener componentListener3 = componentListener;
        if (timeBar2 != null) {
            timeBar2.addListener(componentListener3);
        }
        View viewFindViewById5 = findViewById(R.id.exo_play_pause);
        this.playPauseButton = viewFindViewById5;
        if (viewFindViewById5 != null) {
            viewFindViewById5.setOnClickListener(componentListener3);
        }
        View viewFindViewById6 = findViewById(R.id.exo_prev);
        this.previousButton = viewFindViewById6;
        if (viewFindViewById6 != null) {
            viewFindViewById6.setOnClickListener(componentListener3);
        }
        View viewFindViewById7 = findViewById(R.id.exo_next);
        this.nextButton = viewFindViewById7;
        if (viewFindViewById7 != null) {
            viewFindViewById7.setOnClickListener(componentListener3);
        }
        Typeface font = ResourcesCompat.getFont(context, R.font.roboto_medium_numbers);
        View viewFindViewById8 = findViewById(R.id.exo_rew);
        TextView textView = viewFindViewById8 == null ? (TextView) findViewById(R.id.exo_rew_with_amount) : r9;
        this.rewindButtonTextView = textView;
        if (textView != null) {
            textView.setTypeface(font);
        }
        viewFindViewById8 = viewFindViewById8 == null ? textView : viewFindViewById8;
        this.rewindButton = viewFindViewById8;
        if (viewFindViewById8 != null) {
            viewFindViewById8.setOnClickListener(componentListener3);
        }
        View viewFindViewById9 = findViewById(R.id.exo_ffwd);
        TextView textView2 = viewFindViewById9 == null ? (TextView) findViewById(R.id.exo_ffwd_with_amount) : r9;
        this.fastForwardButtonTextView = textView2;
        if (textView2 != null) {
            textView2.setTypeface(font);
        }
        viewFindViewById9 = viewFindViewById9 == null ? textView2 : viewFindViewById9;
        this.fastForwardButton = viewFindViewById9;
        if (viewFindViewById9 != null) {
            viewFindViewById9.setOnClickListener(componentListener3);
        }
        ImageView imageView4 = (ImageView) findViewById(R.id.exo_repeat_toggle);
        this.repeatToggleButton = imageView4;
        if (imageView4 != null) {
            imageView4.setOnClickListener(componentListener3);
        }
        ImageView imageView5 = (ImageView) findViewById(R.id.exo_shuffle);
        this.shuffleButton = imageView5;
        if (imageView5 != null) {
            imageView5.setOnClickListener(componentListener3);
        }
        this.resources = context.getResources();
        this.buttonAlphaEnabled = r2.getInteger(R.integer.exo_media_button_opacity_percentage_enabled) / 100.0f;
        this.buttonAlphaDisabled = this.resources.getInteger(R.integer.exo_media_button_opacity_percentage_disabled) / 100.0f;
        View viewFindViewById10 = findViewById(R.id.exo_vr);
        this.vrButton = viewFindViewById10;
        if (viewFindViewById10 != null) {
            updateButton(false, viewFindViewById10);
        }
        StyledPlayerControlViewLayoutManager styledPlayerControlViewLayoutManager = new StyledPlayerControlViewLayoutManager(this);
        this.controlViewLayoutManager = styledPlayerControlViewLayoutManager;
        styledPlayerControlViewLayoutManager.setAnimationEnabled(z10);
        this.settingsAdapter = new SettingsAdapter(new String[]{this.resources.getString(R.string.exo_controls_playback_speed), this.resources.getString(R.string.exo_track_selection_title_audio)}, new Drawable[]{this.resources.getDrawable(R.drawable.exo_styled_controls_speed), this.resources.getDrawable(R.drawable.exo_styled_controls_audiotrack)});
        this.settingsWindowMargin = this.resources.getDimensionPixelSize(R.dimen.exo_settings_offset);
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(context).inflate(R.layout.exo_styled_settings_list, (ViewGroup) r9);
        this.settingsView = recyclerView;
        recyclerView.setAdapter(this.settingsAdapter);
        this.settingsView.setLayoutManager(new LinearLayoutManager(getContext()));
        PopupWindow popupWindow = new PopupWindow((View) this.settingsView, -2, -2, true);
        this.settingsWindow = popupWindow;
        if (Util.SDK_INT < 23) {
            z12 = false;
            popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        } else {
            z12 = false;
        }
        this.settingsWindow.setOnDismissListener(componentListener3);
        this.needToHideBars = true;
        this.trackNameProvider = new DefaultTrackNameProvider(getResources());
        this.subtitleOnButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_subtitle_on);
        this.subtitleOffButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_subtitle_off);
        this.subtitleOnContentDescription = this.resources.getString(R.string.exo_controls_cc_enabled_description);
        this.subtitleOffContentDescription = this.resources.getString(R.string.exo_controls_cc_disabled_description);
        this.textTrackSelectionAdapter = new TextTrackSelectionAdapter();
        this.audioTrackSelectionAdapter = new AudioTrackSelectionAdapter();
        this.playbackSpeedAdapter = new PlaybackSpeedAdapter(this.resources.getStringArray(R.array.exo_playback_speeds), this.resources.getIntArray(R.array.exo_speed_multiplied_by_100));
        this.fullScreenExitDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_fullscreen_exit);
        this.fullScreenEnterDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_fullscreen_enter);
        this.repeatOffButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_repeat_off);
        this.repeatOneButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_repeat_one);
        this.repeatAllButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_repeat_all);
        this.shuffleOnButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_shuffle_on);
        this.shuffleOffButtonDrawable = this.resources.getDrawable(R.drawable.exo_styled_controls_shuffle_off);
        this.fullScreenExitContentDescription = this.resources.getString(R.string.exo_controls_fullscreen_exit_description);
        this.fullScreenEnterContentDescription = this.resources.getString(R.string.exo_controls_fullscreen_enter_description);
        this.repeatOffButtonContentDescription = this.resources.getString(R.string.exo_controls_repeat_off_description);
        this.repeatOneButtonContentDescription = this.resources.getString(R.string.exo_controls_repeat_one_description);
        this.repeatAllButtonContentDescription = this.resources.getString(R.string.exo_controls_repeat_all_description);
        this.shuffleOnContentDescription = this.resources.getString(R.string.exo_controls_shuffle_on_description);
        this.shuffleOffContentDescription = this.resources.getString(R.string.exo_controls_shuffle_off_description);
        this.controlViewLayoutManager.setShowButton((ViewGroup) findViewById(R.id.exo_bottom_bar), true);
        this.controlViewLayoutManager.setShowButton(this.fastForwardButton, z7);
        this.controlViewLayoutManager.setShowButton(this.rewindButton, z6);
        this.controlViewLayoutManager.setShowButton(this.previousButton, z8);
        this.controlViewLayoutManager.setShowButton(this.nextButton, z9);
        this.controlViewLayoutManager.setShowButton(this.shuffleButton, z3);
        this.controlViewLayoutManager.setShowButton(this.subtitleButton, z4);
        this.controlViewLayoutManager.setShowButton(this.vrButton, z11);
        this.controlViewLayoutManager.setShowButton(this.repeatToggleButton, this.repeatToggleModes != 0 ? true : z12);
        addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.exoplayer2.ui.m
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                this.f6950c.onLayoutChange(view, i4, i5, i6, i7, i8, i9, i10, i11);
            }
        });
    }
}
