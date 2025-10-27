package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.FrameLayout;
import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.DeviceInfo;
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
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class SubtitleView extends FrameLayout implements Player.Listener {
    public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08f;
    public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533f;
    public static final int VIEW_TYPE_CANVAS = 1;
    public static final int VIEW_TYPE_WEB = 2;
    private boolean applyEmbeddedFontSizes;
    private boolean applyEmbeddedStyles;
    private float bottomPaddingFraction;
    private List<Cue> cues;
    private float defaultTextSize;
    private int defaultTextSizeType;
    private View innerSubtitleView;
    private Output output;
    private CaptionStyleCompat style;
    private int viewType;

    public interface Output {
        void update(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f2, int i2, float f3);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    public SubtitleView(Context context) {
        this(context, null);
    }

    private List<Cue> getCuesWithStylingPreferencesApplied() {
        if (this.applyEmbeddedStyles && this.applyEmbeddedFontSizes) {
            return this.cues;
        }
        ArrayList arrayList = new ArrayList(this.cues.size());
        for (int i2 = 0; i2 < this.cues.size(); i2++) {
            arrayList.add(removeEmbeddedStyling(this.cues.get(i2)));
        }
        return arrayList;
    }

    private float getUserCaptionFontScale() {
        CaptioningManager captioningManager;
        if (Util.SDK_INT < 19 || isInEditMode() || (captioningManager = (CaptioningManager) getContext().getSystemService("captioning")) == null || !captioningManager.isEnabled()) {
            return 1.0f;
        }
        return captioningManager.getFontScale();
    }

    private CaptionStyleCompat getUserCaptionStyle() {
        if (Util.SDK_INT < 19 || isInEditMode()) {
            return CaptionStyleCompat.DEFAULT;
        }
        CaptioningManager captioningManager = (CaptioningManager) getContext().getSystemService("captioning");
        return (captioningManager == null || !captioningManager.isEnabled()) ? CaptionStyleCompat.DEFAULT : CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    private Cue removeEmbeddedStyling(Cue cue) {
        Cue.Builder builderBuildUpon = cue.buildUpon();
        if (!this.applyEmbeddedStyles) {
            SubtitleViewUtils.removeAllEmbeddedStyling(builderBuildUpon);
        } else if (!this.applyEmbeddedFontSizes) {
            SubtitleViewUtils.removeEmbeddedFontSizes(builderBuildUpon);
        }
        return builderBuildUpon.build();
    }

    private void setTextSize(int i2, float f2) {
        this.defaultTextSizeType = i2;
        this.defaultTextSize = f2;
        updateOutput();
    }

    private <T extends View & Output> void setView(T t2) {
        removeView(this.innerSubtitleView);
        View view = this.innerSubtitleView;
        if (view instanceof WebViewSubtitleOutput) {
            ((WebViewSubtitleOutput) view).destroy();
        }
        this.innerSubtitleView = t2;
        this.output = t2;
        addView(t2);
    }

    private void updateOutput() {
        this.output.update(getCuesWithStylingPreferencesApplied(), this.style, this.defaultTextSize, this.defaultTextSizeType, this.bottomPaddingFraction);
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

    @Override // com.google.android.exoplayer2.Player.Listener
    public void onCues(List<Cue> list) {
        setCues(list);
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
    public /* synthetic */ void onEvents(Player player, Player.Events events) {
        x1.g(this, player, events);
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

    public void setApplyEmbeddedFontSizes(boolean z2) {
        this.applyEmbeddedFontSizes = z2;
        updateOutput();
    }

    public void setApplyEmbeddedStyles(boolean z2) {
        this.applyEmbeddedStyles = z2;
        updateOutput();
    }

    public void setBottomPaddingFraction(float f2) {
        this.bottomPaddingFraction = f2;
        updateOutput();
    }

    public void setCues(@Nullable List<Cue> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        this.cues = list;
        updateOutput();
    }

    public void setFixedTextSize(@Dimension int i2, float f2) {
        Context context = getContext();
        setTextSize(2, TypedValue.applyDimension(i2, f2, (context == null ? Resources.getSystem() : context.getResources()).getDisplayMetrics()));
    }

    public void setFractionalTextSize(float f2) {
        setFractionalTextSize(f2, false);
    }

    public void setStyle(CaptionStyleCompat captionStyleCompat) {
        this.style = captionStyleCompat;
        updateOutput();
    }

    public void setUserDefaultStyle() {
        setStyle(getUserCaptionStyle());
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(getUserCaptionFontScale() * 0.0533f);
    }

    public void setViewType(int i2) {
        if (this.viewType == i2) {
            return;
        }
        if (i2 == 1) {
            setView(new CanvasSubtitleOutput(getContext()));
        } else {
            if (i2 != 2) {
                throw new IllegalArgumentException();
            }
            setView(new WebViewSubtitleOutput(getContext()));
        }
        this.viewType = i2;
    }

    public SubtitleView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.cues = Collections.emptyList();
        this.style = CaptionStyleCompat.DEFAULT;
        this.defaultTextSizeType = 0;
        this.defaultTextSize = 0.0533f;
        this.bottomPaddingFraction = 0.08f;
        this.applyEmbeddedStyles = true;
        this.applyEmbeddedFontSizes = true;
        CanvasSubtitleOutput canvasSubtitleOutput = new CanvasSubtitleOutput(context);
        this.output = canvasSubtitleOutput;
        this.innerSubtitleView = canvasSubtitleOutput;
        addView(canvasSubtitleOutput);
        this.viewType = 1;
    }

    public void setFractionalTextSize(float f2, boolean z2) {
        setTextSize(z2 ? 1 : 0, f2);
    }
}
