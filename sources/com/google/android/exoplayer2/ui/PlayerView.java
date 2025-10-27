package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.GLSurfaceView;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public class PlayerView extends FrameLayout implements AdViewProvider {
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int PICTURE_TYPE_NOT_SET = -1;
    public static final int SHOW_BUFFERING_ALWAYS = 2;
    public static final int SHOW_BUFFERING_NEVER = 0;
    public static final int SHOW_BUFFERING_WHEN_PLAYING = 1;
    private static final int SURFACE_TYPE_NONE = 0;
    private static final int SURFACE_TYPE_SPHERICAL_GL_SURFACE_VIEW = 3;
    private static final int SURFACE_TYPE_SURFACE_VIEW = 1;
    private static final int SURFACE_TYPE_TEXTURE_VIEW = 2;
    private static final int SURFACE_TYPE_VIDEO_DECODER_GL_SURFACE_VIEW = 4;

    @Nullable
    private final FrameLayout adOverlayFrameLayout;

    @Nullable
    private final ImageView artworkView;

    @Nullable
    private final View bufferingView;
    private final ComponentListener componentListener;

    @Nullable
    private final AspectRatioFrameLayout contentFrame;

    @Nullable
    private final PlayerControlView controller;
    private boolean controllerAutoShow;
    private boolean controllerHideDuringAds;
    private boolean controllerHideOnTouch;
    private int controllerShowTimeoutMs;

    @Nullable
    private PlayerControlView.VisibilityListener controllerVisibilityListener;

    @Nullable
    private CharSequence customErrorMessage;

    @Nullable
    private Drawable defaultArtwork;

    @Nullable
    private ErrorMessageProvider<? super PlaybackException> errorMessageProvider;

    @Nullable
    private final TextView errorMessageView;
    private boolean isTouching;
    private boolean keepContentOnPlayerReset;

    @Nullable
    private final FrameLayout overlayFrameLayout;

    @Nullable
    private Player player;
    private int showBuffering;

    @Nullable
    private final View shutterView;

    @Nullable
    private final SubtitleView subtitleView;

    @Nullable
    private final View surfaceView;
    private final boolean surfaceViewIgnoresVideoAspectRatio;
    private int textureViewRotation;
    private boolean useArtwork;
    private boolean useController;

    public final class ComponentListener implements Player.Listener, View.OnLayoutChangeListener, View.OnClickListener, PlayerControlView.VisibilityListener {

        @Nullable
        private Object lastPeriodUidWithTracks;
        private final Timeline.Period period = new Timeline.Period();

        public ComponentListener() {
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
            PlayerView.this.toggleControllerVisibility();
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onCues(List<Cue> list) {
            if (PlayerView.this.subtitleView != null) {
                PlayerView.this.subtitleView.setCues(list);
            }
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

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            PlayerView.applyTextureViewRotation((TextureView) view, PlayerView.this.textureViewRotation);
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
        public void onPlayWhenReadyChanged(boolean z2, int i2) {
            PlayerView.this.updateBuffering();
            PlayerView.this.updateControllerVisibility();
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            x1.n(this, playbackParameters);
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onPlaybackStateChanged(int i2) {
            PlayerView.this.updateBuffering();
            PlayerView.this.updateErrorMessage();
            PlayerView.this.updateControllerVisibility();
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
        public void onPositionDiscontinuity(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
            if (PlayerView.this.isPlayingAd() && PlayerView.this.controllerHideDuringAds) {
                PlayerView.this.hideController();
            }
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onRenderedFirstFrame() {
            if (PlayerView.this.shutterView != null) {
                PlayerView.this.shutterView.setVisibility(4);
            }
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
        public void onTracksInfoChanged(TracksInfo tracksInfo) {
            Player player = (Player) Assertions.checkNotNull(PlayerView.this.player);
            Timeline currentTimeline = player.getCurrentTimeline();
            if (currentTimeline.isEmpty()) {
                this.lastPeriodUidWithTracks = null;
            } else if (player.getCurrentTracksInfo().getTrackGroupInfos().isEmpty()) {
                Object obj = this.lastPeriodUidWithTracks;
                if (obj != null) {
                    int indexOfPeriod = currentTimeline.getIndexOfPeriod(obj);
                    if (indexOfPeriod != -1) {
                        if (player.getCurrentMediaItemIndex() == currentTimeline.getPeriod(indexOfPeriod, this.period).windowIndex) {
                            return;
                        }
                    }
                    this.lastPeriodUidWithTracks = null;
                }
            } else {
                this.lastPeriodUidWithTracks = currentTimeline.getPeriod(player.getCurrentPeriodIndex(), this.period, true).uid;
            }
            PlayerView.this.updateForCurrentTrackSelections(false);
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public void onVideoSizeChanged(VideoSize videoSize) {
            PlayerView.this.updateAspectRatio();
        }

        @Override // com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener
        public void onVisibilityChange(int i2) {
            PlayerView.this.updateContentDescription();
        }

        @Override // com.google.android.exoplayer2.Player.Listener
        public /* synthetic */ void onVolumeChanged(float f2) {
            x1.E(this, f2);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowBuffering {
    }

    public PlayerView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void applyTextureViewRotation(TextureView textureView, int i2) {
        Matrix matrix = new Matrix();
        float width = textureView.getWidth();
        float height = textureView.getHeight();
        if (width != 0.0f && height != 0.0f && i2 != 0) {
            float f2 = width / 2.0f;
            float f3 = height / 2.0f;
            matrix.postRotate(i2, f2, f3);
            RectF rectF = new RectF(0.0f, 0.0f, width, height);
            RectF rectF2 = new RectF();
            matrix.mapRect(rectF2, rectF);
            matrix.postScale(width / rectF2.width(), height / rectF2.height(), f2, f3);
        }
        textureView.setTransform(matrix);
    }

    private void closeShutter() {
        View view = this.shutterView;
        if (view != null) {
            view.setVisibility(0);
        }
    }

    private static void configureEditModeLogo(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo));
        imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color));
    }

    @RequiresApi(23)
    private static void configureEditModeLogoV23(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, null));
        imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color, null));
    }

    private void hideArtwork() {
        ImageView imageView = this.artworkView;
        if (imageView != null) {
            imageView.setImageResource(android.R.color.transparent);
            this.artworkView.setVisibility(4);
        }
    }

    @SuppressLint({"InlinedApi"})
    private boolean isDpadKey(int i2) {
        return i2 == 19 || i2 == 270 || i2 == 22 || i2 == 271 || i2 == 20 || i2 == 269 || i2 == 21 || i2 == 268 || i2 == 23;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPlayingAd() {
        Player player = this.player;
        return player != null && player.isPlayingAd() && this.player.getPlayWhenReady();
    }

    private void maybeShowController(boolean z2) {
        if (!(isPlayingAd() && this.controllerHideDuringAds) && useController()) {
            boolean z3 = this.controller.isVisible() && this.controller.getShowTimeoutMs() <= 0;
            boolean zShouldShowControllerIndefinitely = shouldShowControllerIndefinitely();
            if (z2 || z3 || zShouldShowControllerIndefinitely) {
                showController(zShouldShowControllerIndefinitely);
            }
        }
    }

    @RequiresNonNull({"artworkView"})
    private boolean setArtworkFromMediaMetadata(MediaMetadata mediaMetadata) {
        byte[] bArr = mediaMetadata.artworkData;
        if (bArr == null) {
            return false;
        }
        return setDrawableArtwork(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length)));
    }

    @RequiresNonNull({"artworkView"})
    private boolean setDrawableArtwork(@Nullable Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                onContentAspectRatioChanged(this.contentFrame, intrinsicWidth / intrinsicHeight);
                this.artworkView.setImageDrawable(drawable);
                this.artworkView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    private static void setResizeModeRaw(AspectRatioFrameLayout aspectRatioFrameLayout, int i2) {
        aspectRatioFrameLayout.setResizeMode(i2);
    }

    private boolean shouldShowControllerIndefinitely() {
        Player player = this.player;
        if (player == null) {
            return true;
        }
        int playbackState = player.getPlaybackState();
        return this.controllerAutoShow && (playbackState == 1 || playbackState == 4 || !this.player.getPlayWhenReady());
    }

    public static void switchTargetView(Player player, @Nullable PlayerView playerView, @Nullable PlayerView playerView2) {
        if (playerView == playerView2) {
            return;
        }
        if (playerView2 != null) {
            playerView2.setPlayer(player);
        }
        if (playerView != null) {
            playerView.setPlayer(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean toggleControllerVisibility() {
        if (!useController() || this.player == null) {
            return false;
        }
        if (!this.controller.isVisible()) {
            maybeShowController(true);
        } else if (this.controllerHideOnTouch) {
            this.controller.hide();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAspectRatio() {
        Player player = this.player;
        VideoSize videoSize = player != null ? player.getVideoSize() : VideoSize.UNKNOWN;
        int i2 = videoSize.width;
        int i3 = videoSize.height;
        int i4 = videoSize.unappliedRotationDegrees;
        float f2 = (i3 == 0 || i2 == 0) ? 0.0f : (i2 * videoSize.pixelWidthHeightRatio) / i3;
        View view = this.surfaceView;
        if (view instanceof TextureView) {
            if (f2 > 0.0f && (i4 == 90 || i4 == 270)) {
                f2 = 1.0f / f2;
            }
            if (this.textureViewRotation != 0) {
                view.removeOnLayoutChangeListener(this.componentListener);
            }
            this.textureViewRotation = i4;
            if (i4 != 0) {
                this.surfaceView.addOnLayoutChangeListener(this.componentListener);
            }
            applyTextureViewRotation((TextureView) this.surfaceView, this.textureViewRotation);
        }
        onContentAspectRatioChanged(this.contentFrame, this.surfaceViewIgnoresVideoAspectRatio ? 0.0f : f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateBuffering() {
        /*
            r4 = this;
            android.view.View r0 = r4.bufferingView
            if (r0 == 0) goto L2b
            com.google.android.exoplayer2.Player r0 = r4.player
            r1 = 0
            if (r0 == 0) goto L20
            int r0 = r0.getPlaybackState()
            r2 = 2
            if (r0 != r2) goto L20
            int r0 = r4.showBuffering
            r3 = 1
            if (r0 == r2) goto L21
            if (r0 != r3) goto L20
            com.google.android.exoplayer2.Player r0 = r4.player
            boolean r0 = r0.getPlayWhenReady()
            if (r0 == 0) goto L20
            goto L21
        L20:
            r3 = r1
        L21:
            android.view.View r0 = r4.bufferingView
            if (r3 == 0) goto L26
            goto L28
        L26:
            r1 = 8
        L28:
            r0.setVisibility(r1)
        L2b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerView.updateBuffering():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateContentDescription() {
        PlayerControlView playerControlView = this.controller;
        if (playerControlView == null || !this.useController) {
            setContentDescription(null);
        } else if (playerControlView.getVisibility() == 0) {
            setContentDescription(this.controllerHideOnTouch ? getResources().getString(R.string.exo_controls_hide) : null);
        } else {
            setContentDescription(getResources().getString(R.string.exo_controls_show));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateControllerVisibility() {
        if (isPlayingAd() && this.controllerHideDuringAds) {
            hideController();
        } else {
            maybeShowController(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateErrorMessage() {
        ErrorMessageProvider<? super PlaybackException> errorMessageProvider;
        TextView textView = this.errorMessageView;
        if (textView != null) {
            CharSequence charSequence = this.customErrorMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                this.errorMessageView.setVisibility(0);
                return;
            }
            Player player = this.player;
            PlaybackException playerError = player != null ? player.getPlayerError() : null;
            if (playerError == null || (errorMessageProvider = this.errorMessageProvider) == null) {
                this.errorMessageView.setVisibility(8);
            } else {
                this.errorMessageView.setText((CharSequence) errorMessageProvider.getErrorMessage(playerError).second);
                this.errorMessageView.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForCurrentTrackSelections(boolean z2) {
        Player player = this.player;
        if (player == null || !player.isCommandAvailable(30) || player.getCurrentTracksInfo().getTrackGroupInfos().isEmpty()) {
            if (this.keepContentOnPlayerReset) {
                return;
            }
            hideArtwork();
            closeShutter();
            return;
        }
        if (z2 && !this.keepContentOnPlayerReset) {
            closeShutter();
        }
        if (player.getCurrentTracksInfo().isTypeSelected(2)) {
            hideArtwork();
            return;
        }
        closeShutter();
        if (useArtwork() && (setArtworkFromMediaMetadata(player.getMediaMetadata()) || setDrawableArtwork(this.defaultArtwork))) {
            return;
        }
        hideArtwork();
    }

    @EnsuresNonNullIf(expression = {"artworkView"}, result = true)
    private boolean useArtwork() {
        if (!this.useArtwork) {
            return false;
        }
        Assertions.checkStateNotNull(this.artworkView);
        return true;
    }

    @EnsuresNonNullIf(expression = {"controller"}, result = true)
    private boolean useController() {
        if (!this.useController) {
            return false;
        }
        Assertions.checkStateNotNull(this.controller);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Player player = this.player;
        if (player != null && player.isPlayingAd()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        boolean zIsDpadKey = isDpadKey(keyEvent.getKeyCode());
        if (zIsDpadKey && useController() && !this.controller.isVisible()) {
            maybeShowController(true);
            return true;
        }
        if (dispatchMediaKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
            maybeShowController(true);
            return true;
        }
        if (zIsDpadKey && useController()) {
            maybeShowController(true);
        }
        return false;
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        return useController() && this.controller.dispatchMediaKeyEvent(keyEvent);
    }

    @Override // com.google.android.exoplayer2.ui.AdViewProvider
    public List<AdOverlayInfo> getAdOverlayInfos() {
        ArrayList arrayList = new ArrayList();
        FrameLayout frameLayout = this.overlayFrameLayout;
        if (frameLayout != null) {
            arrayList.add(new AdOverlayInfo(frameLayout, 3, "Transparent overlay does not impact viewability"));
        }
        PlayerControlView playerControlView = this.controller;
        if (playerControlView != null) {
            arrayList.add(new AdOverlayInfo(playerControlView, 0));
        }
        return ImmutableList.copyOf((Collection) arrayList);
    }

    @Override // com.google.android.exoplayer2.ui.AdViewProvider
    public ViewGroup getAdViewGroup() {
        return (ViewGroup) Assertions.checkStateNotNull(this.adOverlayFrameLayout, "exo_ad_overlay must be present for ad playback");
    }

    public boolean getControllerAutoShow() {
        return this.controllerAutoShow;
    }

    public boolean getControllerHideOnTouch() {
        return this.controllerHideOnTouch;
    }

    public int getControllerShowTimeoutMs() {
        return this.controllerShowTimeoutMs;
    }

    @Nullable
    public Drawable getDefaultArtwork() {
        return this.defaultArtwork;
    }

    @Nullable
    public FrameLayout getOverlayFrameLayout() {
        return this.overlayFrameLayout;
    }

    @Nullable
    public Player getPlayer() {
        return this.player;
    }

    public int getResizeMode() {
        Assertions.checkStateNotNull(this.contentFrame);
        return this.contentFrame.getResizeMode();
    }

    @Nullable
    public SubtitleView getSubtitleView() {
        return this.subtitleView;
    }

    public boolean getUseArtwork() {
        return this.useArtwork;
    }

    public boolean getUseController() {
        return this.useController;
    }

    @Nullable
    public View getVideoSurfaceView() {
        return this.surfaceView;
    }

    public void hideController() {
        PlayerControlView playerControlView = this.controller;
        if (playerControlView != null) {
            playerControlView.hide();
        }
    }

    public boolean isControllerVisible() {
        PlayerControlView playerControlView = this.controller;
        return playerControlView != null && playerControlView.isVisible();
    }

    public void onContentAspectRatioChanged(@Nullable AspectRatioFrameLayout aspectRatioFrameLayout, float f2) {
        if (aspectRatioFrameLayout != null) {
            aspectRatioFrameLayout.setAspectRatio(f2);
        }
    }

    public void onPause() {
        View view = this.surfaceView;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onPause();
        }
    }

    public void onResume() {
        View view = this.surfaceView;
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).onResume();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!useController() || this.player == null) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isTouching = true;
            return true;
        }
        if (action != 1 || !this.isTouching) {
            return false;
        }
        this.isTouching = false;
        performClick();
        return true;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (!useController() || this.player == null) {
            return false;
        }
        maybeShowController(true);
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        super.performClick();
        return toggleControllerVisibility();
    }

    public void setAspectRatioListener(@Nullable AspectRatioFrameLayout.AspectRatioListener aspectRatioListener) {
        Assertions.checkStateNotNull(this.contentFrame);
        this.contentFrame.setAspectRatioListener(aspectRatioListener);
    }

    public void setControllerAutoShow(boolean z2) {
        this.controllerAutoShow = z2;
    }

    public void setControllerHideDuringAds(boolean z2) {
        this.controllerHideDuringAds = z2;
    }

    public void setControllerHideOnTouch(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controllerHideOnTouch = z2;
        updateContentDescription();
    }

    public void setControllerShowTimeoutMs(int i2) {
        Assertions.checkStateNotNull(this.controller);
        this.controllerShowTimeoutMs = i2;
        if (this.controller.isVisible()) {
            showController();
        }
    }

    public void setControllerVisibilityListener(@Nullable PlayerControlView.VisibilityListener visibilityListener) {
        Assertions.checkStateNotNull(this.controller);
        PlayerControlView.VisibilityListener visibilityListener2 = this.controllerVisibilityListener;
        if (visibilityListener2 == visibilityListener) {
            return;
        }
        if (visibilityListener2 != null) {
            this.controller.removeVisibilityListener(visibilityListener2);
        }
        this.controllerVisibilityListener = visibilityListener;
        if (visibilityListener != null) {
            this.controller.addVisibilityListener(visibilityListener);
        }
    }

    public void setCustomErrorMessage(@Nullable CharSequence charSequence) {
        Assertions.checkState(this.errorMessageView != null);
        this.customErrorMessage = charSequence;
        updateErrorMessage();
    }

    public void setDefaultArtwork(@Nullable Drawable drawable) {
        if (this.defaultArtwork != drawable) {
            this.defaultArtwork = drawable;
            updateForCurrentTrackSelections(false);
        }
    }

    public void setErrorMessageProvider(@Nullable ErrorMessageProvider<? super PlaybackException> errorMessageProvider) {
        if (this.errorMessageProvider != errorMessageProvider) {
            this.errorMessageProvider = errorMessageProvider;
            updateErrorMessage();
        }
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setExtraAdGroupMarkers(jArr, zArr);
    }

    public void setKeepContentOnPlayerReset(boolean z2) {
        if (this.keepContentOnPlayerReset != z2) {
            this.keepContentOnPlayerReset = z2;
            updateForCurrentTrackSelections(false);
        }
    }

    public void setPlayer(@Nullable Player player) {
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        Assertions.checkArgument(player == null || player.getApplicationLooper() == Looper.getMainLooper());
        Player player2 = this.player;
        if (player2 == player) {
            return;
        }
        if (player2 != null) {
            player2.removeListener(this.componentListener);
            if (player2.isCommandAvailable(27)) {
                View view = this.surfaceView;
                if (view instanceof TextureView) {
                    player2.clearVideoTextureView((TextureView) view);
                } else if (view instanceof SurfaceView) {
                    player2.clearVideoSurfaceView((SurfaceView) view);
                }
            }
        }
        SubtitleView subtitleView = this.subtitleView;
        if (subtitleView != null) {
            subtitleView.setCues(null);
        }
        this.player = player;
        if (useController()) {
            this.controller.setPlayer(player);
        }
        updateBuffering();
        updateErrorMessage();
        updateForCurrentTrackSelections(true);
        if (player == null) {
            hideController();
            return;
        }
        if (player.isCommandAvailable(27)) {
            View view2 = this.surfaceView;
            if (view2 instanceof TextureView) {
                player.setVideoTextureView((TextureView) view2);
            } else if (view2 instanceof SurfaceView) {
                player.setVideoSurfaceView((SurfaceView) view2);
            }
            updateAspectRatio();
        }
        if (this.subtitleView != null && player.isCommandAvailable(28)) {
            this.subtitleView.setCues(player.getCurrentCues());
        }
        player.addListener(this.componentListener);
        maybeShowController(false);
    }

    public void setRepeatToggleModes(int i2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setRepeatToggleModes(i2);
    }

    public void setResizeMode(int i2) {
        Assertions.checkStateNotNull(this.contentFrame);
        this.contentFrame.setResizeMode(i2);
    }

    public void setShowBuffering(int i2) {
        if (this.showBuffering != i2) {
            this.showBuffering = i2;
            updateBuffering();
        }
    }

    public void setShowFastForwardButton(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowFastForwardButton(z2);
    }

    public void setShowMultiWindowTimeBar(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowMultiWindowTimeBar(z2);
    }

    public void setShowNextButton(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowNextButton(z2);
    }

    public void setShowPreviousButton(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowPreviousButton(z2);
    }

    public void setShowRewindButton(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowRewindButton(z2);
    }

    public void setShowShuffleButton(boolean z2) {
        Assertions.checkStateNotNull(this.controller);
        this.controller.setShowShuffleButton(z2);
    }

    public void setShutterBackgroundColor(int i2) {
        View view = this.shutterView;
        if (view != null) {
            view.setBackgroundColor(i2);
        }
    }

    public void setUseArtwork(boolean z2) {
        Assertions.checkState((z2 && this.artworkView == null) ? false : true);
        if (this.useArtwork != z2) {
            this.useArtwork = z2;
            updateForCurrentTrackSelections(false);
        }
    }

    public void setUseController(boolean z2) {
        Assertions.checkState((z2 && this.controller == null) ? false : true);
        if (this.useController == z2) {
            return;
        }
        this.useController = z2;
        if (useController()) {
            this.controller.setPlayer(this.player);
        } else {
            PlayerControlView playerControlView = this.controller;
            if (playerControlView != null) {
                playerControlView.hide();
                this.controller.setPlayer(null);
            }
        }
        updateContentDescription();
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        View view = this.surfaceView;
        if (view instanceof SurfaceView) {
            view.setVisibility(i2);
        }
    }

    public void showController() {
        showController(shouldShowControllerIndefinitely());
    }

    public PlayerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void showController(boolean z2) {
        if (useController()) {
            this.controller.setShowTimeoutMs(z2 ? 0 : this.controllerShowTimeoutMs);
            this.controller.show();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PlayerView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        int i3;
        boolean z2;
        int i4;
        int integer;
        boolean z3;
        boolean z4;
        int i5;
        int i6;
        boolean z5;
        boolean z6;
        int i7;
        boolean z7;
        boolean z8;
        boolean z9;
        int i8;
        boolean z10;
        super(context, attributeSet, i2);
        ComponentListener componentListener = new ComponentListener();
        this.componentListener = componentListener;
        if (isInEditMode()) {
            this.contentFrame = null;
            this.shutterView = null;
            this.surfaceView = null;
            this.surfaceViewIgnoresVideoAspectRatio = false;
            this.artworkView = null;
            this.subtitleView = null;
            this.bufferingView = null;
            this.errorMessageView = null;
            this.controller = null;
            this.adOverlayFrameLayout = null;
            this.overlayFrameLayout = null;
            ImageView imageView = new ImageView(context);
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(getResources(), imageView);
            } else {
                configureEditModeLogo(getResources(), imageView);
            }
            addView(imageView);
            return;
        }
        int i9 = R.layout.exo_player_view;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.PlayerView, i2, 0);
            try {
                int i10 = R.styleable.PlayerView_shutter_background_color;
                boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(i10);
                int color = typedArrayObtainStyledAttributes.getColor(i10, 0);
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.PlayerView_player_layout_id, i9);
                boolean z11 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerView_use_artwork, true);
                int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.PlayerView_default_artwork, 0);
                boolean z12 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerView_use_controller, true);
                int i11 = typedArrayObtainStyledAttributes.getInt(R.styleable.PlayerView_surface_type, 1);
                int i12 = typedArrayObtainStyledAttributes.getInt(R.styleable.PlayerView_resize_mode, 0);
                int i13 = typedArrayObtainStyledAttributes.getInt(R.styleable.PlayerView_show_timeout, 5000);
                boolean z13 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerView_hide_on_touch, true);
                boolean z14 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerView_auto_show, true);
                integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.PlayerView_show_buffering, 0);
                this.keepContentOnPlayerReset = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerView_keep_content_on_player_reset, this.keepContentOnPlayerReset);
                boolean z15 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PlayerView_hide_during_ads, true);
                typedArrayObtainStyledAttributes.recycle();
                z4 = z13;
                z2 = z14;
                i4 = i12;
                z7 = z12;
                i7 = resourceId2;
                z6 = z11;
                z5 = zHasValue;
                i6 = color;
                i5 = i11;
                i9 = resourceId;
                i3 = i13;
                z3 = z15;
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        } else {
            i3 = 5000;
            z2 = true;
            i4 = 0;
            integer = 0;
            z3 = true;
            z4 = true;
            i5 = 1;
            i6 = 0;
            z5 = false;
            z6 = true;
            i7 = 0;
            z7 = true;
        }
        LayoutInflater.from(context).inflate(i9, this);
        setDescendantFocusability(262144);
        AspectRatioFrameLayout aspectRatioFrameLayout = (AspectRatioFrameLayout) findViewById(R.id.exo_content_frame);
        this.contentFrame = aspectRatioFrameLayout;
        if (aspectRatioFrameLayout != null) {
            setResizeModeRaw(aspectRatioFrameLayout, i4);
        }
        View viewFindViewById = findViewById(R.id.exo_shutter);
        this.shutterView = viewFindViewById;
        if (viewFindViewById != null && z5) {
            viewFindViewById.setBackgroundColor(i6);
        }
        if (aspectRatioFrameLayout != null && i5 != 0) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            if (i5 == 2) {
                z8 = true;
                this.surfaceView = new TextureView(context);
            } else if (i5 != 3) {
                if (i5 != 4) {
                    this.surfaceView = new SurfaceView(context);
                } else {
                    try {
                        this.surfaceView = (View) Class.forName("com.google.android.exoplayer2.video.VideoDecoderGLSurfaceView").getConstructor(Context.class).newInstance(context);
                    } catch (Exception e2) {
                        throw new IllegalStateException("video_decoder_gl_surface_view requires an ExoPlayer dependency", e2);
                    }
                }
                z8 = true;
            } else {
                try {
                    z8 = true;
                    this.surfaceView = (View) Class.forName("com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView").getConstructor(Context.class).newInstance(context);
                    z10 = true;
                    this.surfaceView.setLayoutParams(layoutParams);
                    this.surfaceView.setOnClickListener(componentListener);
                    this.surfaceView.setClickable(false);
                    aspectRatioFrameLayout.addView(this.surfaceView, 0);
                    z9 = z10;
                } catch (Exception e3) {
                    throw new IllegalStateException("spherical_gl_surface_view requires an ExoPlayer dependency", e3);
                }
            }
            z10 = false;
            this.surfaceView.setLayoutParams(layoutParams);
            this.surfaceView.setOnClickListener(componentListener);
            this.surfaceView.setClickable(false);
            aspectRatioFrameLayout.addView(this.surfaceView, 0);
            z9 = z10;
        } else {
            z8 = true;
            this.surfaceView = null;
            z9 = false;
        }
        this.surfaceViewIgnoresVideoAspectRatio = z9;
        this.adOverlayFrameLayout = (FrameLayout) findViewById(R.id.exo_ad_overlay);
        this.overlayFrameLayout = (FrameLayout) findViewById(R.id.exo_overlay);
        ImageView imageView2 = (ImageView) findViewById(R.id.exo_artwork);
        this.artworkView = imageView2;
        this.useArtwork = (!z6 || imageView2 == null) ? false : z8;
        if (i7 != 0) {
            this.defaultArtwork = ContextCompat.getDrawable(getContext(), i7);
        }
        SubtitleView subtitleView = (SubtitleView) findViewById(R.id.exo_subtitles);
        this.subtitleView = subtitleView;
        if (subtitleView != null) {
            subtitleView.setUserDefaultStyle();
            subtitleView.setUserDefaultTextSize();
        }
        View viewFindViewById2 = findViewById(R.id.exo_buffering);
        this.bufferingView = viewFindViewById2;
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(8);
        }
        this.showBuffering = integer;
        TextView textView = (TextView) findViewById(R.id.exo_error_message);
        this.errorMessageView = textView;
        if (textView != null) {
            textView.setVisibility(8);
        }
        int i14 = R.id.exo_controller;
        PlayerControlView playerControlView = (PlayerControlView) findViewById(i14);
        View viewFindViewById3 = findViewById(R.id.exo_controller_placeholder);
        if (playerControlView != null) {
            this.controller = playerControlView;
            i8 = 0;
        } else if (viewFindViewById3 != null) {
            i8 = 0;
            PlayerControlView playerControlView2 = new PlayerControlView(context, null, 0, attributeSet);
            this.controller = playerControlView2;
            playerControlView2.setId(i14);
            playerControlView2.setLayoutParams(viewFindViewById3.getLayoutParams());
            ViewGroup viewGroup = (ViewGroup) viewFindViewById3.getParent();
            int iIndexOfChild = viewGroup.indexOfChild(viewFindViewById3);
            viewGroup.removeView(viewFindViewById3);
            viewGroup.addView(playerControlView2, iIndexOfChild);
        } else {
            i8 = 0;
            this.controller = null;
        }
        PlayerControlView playerControlView3 = this.controller;
        this.controllerShowTimeoutMs = playerControlView3 != null ? i3 : i8;
        this.controllerHideOnTouch = z4;
        this.controllerAutoShow = z2;
        this.controllerHideDuringAds = z3;
        this.useController = (!z7 || playerControlView3 == null) ? i8 : z8;
        hideController();
        updateContentDescription();
        PlayerControlView playerControlView4 = this.controller;
        if (playerControlView4 != null) {
            playerControlView4.addVisibilityListener(componentListener);
        }
    }
}
