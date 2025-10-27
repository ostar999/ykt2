package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;
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
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.exoplayer2.w1;
import com.google.android.exoplayer2.x1;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class PlayerNotificationManager {
    private static final String ACTION_DISMISS = "com.google.android.exoplayer.dismiss";
    public static final String ACTION_FAST_FORWARD = "com.google.android.exoplayer.ffwd";
    public static final String ACTION_NEXT = "com.google.android.exoplayer.next";
    public static final String ACTION_PAUSE = "com.google.android.exoplayer.pause";
    public static final String ACTION_PLAY = "com.google.android.exoplayer.play";
    public static final String ACTION_PREVIOUS = "com.google.android.exoplayer.prev";
    public static final String ACTION_REWIND = "com.google.android.exoplayer.rewind";
    public static final String ACTION_STOP = "com.google.android.exoplayer.stop";
    public static final String EXTRA_INSTANCE_ID = "INSTANCE_ID";
    private static final int MSG_START_OR_UPDATE_NOTIFICATION = 0;
    private static final int MSG_UPDATE_NOTIFICATION_BITMAP = 1;
    private static int instanceIdCounter;
    private int badgeIconType;

    @Nullable
    private NotificationCompat.Builder builder;

    @Nullable
    private List<NotificationCompat.Action> builderActions;
    private final String channelId;
    private int color;
    private boolean colorized;
    private final Context context;
    private int currentNotificationTag;

    @Nullable
    private final CustomActionReceiver customActionReceiver;
    private final Map<String, NotificationCompat.Action> customActions;
    private int defaults;
    private final PendingIntent dismissPendingIntent;

    @Nullable
    private String groupKey;
    private final int instanceId;
    private final IntentFilter intentFilter;
    private boolean isNotificationStarted;
    private final Handler mainHandler;
    private final MediaDescriptionAdapter mediaDescriptionAdapter;

    @Nullable
    private MediaSessionCompat.Token mediaSessionToken;
    private final NotificationBroadcastReceiver notificationBroadcastReceiver;
    private final int notificationId;

    @Nullable
    private final NotificationListener notificationListener;
    private final NotificationManagerCompat notificationManager;
    private final Map<String, NotificationCompat.Action> playbackActions;

    @Nullable
    private Player player;
    private final Player.Listener playerListener;
    private int priority;

    @DrawableRes
    private int smallIconResourceId;
    private boolean useChronometer;
    private boolean useFastForwardAction;
    private boolean useFastForwardActionInCompactView;
    private boolean useNextAction;
    private boolean useNextActionInCompactView;
    private boolean usePlayPauseActions;
    private boolean usePreviousAction;
    private boolean usePreviousActionInCompactView;
    private boolean useRewindAction;
    private boolean useRewindActionInCompactView;
    private boolean useStopAction;
    private int visibility;

    public final class BitmapCallback {
        private final int notificationTag;

        public void onBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                PlayerNotificationManager.this.postUpdateNotificationBitmap(bitmap, this.notificationTag);
            }
        }

        private BitmapCallback(int i2) {
            this.notificationTag = i2;
        }
    }

    public interface CustomActionReceiver {
        Map<String, NotificationCompat.Action> createCustomActions(Context context, int i2);

        List<String> getCustomActions(Player player);

        void onCustomAction(Player player, String str, Intent intent);
    }

    public interface MediaDescriptionAdapter {
        @Nullable
        PendingIntent createCurrentContentIntent(Player player);

        @Nullable
        CharSequence getCurrentContentText(Player player);

        CharSequence getCurrentContentTitle(Player player);

        @Nullable
        Bitmap getCurrentLargeIcon(Player player, BitmapCallback bitmapCallback);

        @Nullable
        CharSequence getCurrentSubText(Player player);
    }

    public class NotificationBroadcastReceiver extends BroadcastReceiver {
        private NotificationBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Player player = PlayerNotificationManager.this.player;
            if (player != null && PlayerNotificationManager.this.isNotificationStarted && intent.getIntExtra(PlayerNotificationManager.EXTRA_INSTANCE_ID, PlayerNotificationManager.this.instanceId) == PlayerNotificationManager.this.instanceId) {
                String action = intent.getAction();
                if (PlayerNotificationManager.ACTION_PLAY.equals(action)) {
                    if (player.getPlaybackState() == 1) {
                        player.prepare();
                    } else if (player.getPlaybackState() == 4) {
                        player.seekToDefaultPosition(player.getCurrentMediaItemIndex());
                    }
                    player.play();
                    return;
                }
                if (PlayerNotificationManager.ACTION_PAUSE.equals(action)) {
                    player.pause();
                    return;
                }
                if (PlayerNotificationManager.ACTION_PREVIOUS.equals(action)) {
                    player.seekToPrevious();
                    return;
                }
                if (PlayerNotificationManager.ACTION_REWIND.equals(action)) {
                    player.seekBack();
                    return;
                }
                if (PlayerNotificationManager.ACTION_FAST_FORWARD.equals(action)) {
                    player.seekForward();
                    return;
                }
                if (PlayerNotificationManager.ACTION_NEXT.equals(action)) {
                    player.seekToNext();
                    return;
                }
                if (PlayerNotificationManager.ACTION_STOP.equals(action)) {
                    player.stop(true);
                    return;
                }
                if (PlayerNotificationManager.ACTION_DISMISS.equals(action)) {
                    PlayerNotificationManager.this.stopNotification(true);
                } else {
                    if (action == null || PlayerNotificationManager.this.customActionReceiver == null || !PlayerNotificationManager.this.customActions.containsKey(action)) {
                        return;
                    }
                    PlayerNotificationManager.this.customActionReceiver.onCustomAction(player, action, intent);
                }
            }
        }
    }

    public interface NotificationListener {
        void onNotificationCancelled(int i2, boolean z2);

        void onNotificationPosted(int i2, Notification notification, boolean z2);
    }

    public class PlayerListener implements Player.Listener {
        private PlayerListener() {
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
            if (events.containsAny(4, 5, 7, 0, 12, 11, 8, 9, 14)) {
                PlayerNotificationManager.this.postStartOrUpdateNotification();
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

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public PlayerNotificationManager(Context context, String str, int i2, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener, @Nullable CustomActionReceiver customActionReceiver, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, @Nullable String str2) {
        Context applicationContext = context.getApplicationContext();
        this.context = applicationContext;
        this.channelId = str;
        this.notificationId = i2;
        this.mediaDescriptionAdapter = mediaDescriptionAdapter;
        this.notificationListener = notificationListener;
        this.customActionReceiver = customActionReceiver;
        this.smallIconResourceId = i3;
        this.groupKey = str2;
        int i11 = instanceIdCounter;
        instanceIdCounter = i11 + 1;
        this.instanceId = i11;
        this.mainHandler = Util.createHandler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.exoplayer2.ui.f
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f6939c.handleMessage(message);
            }
        });
        this.notificationManager = NotificationManagerCompat.from(applicationContext);
        this.playerListener = new PlayerListener();
        this.notificationBroadcastReceiver = new NotificationBroadcastReceiver();
        this.intentFilter = new IntentFilter();
        this.usePreviousAction = true;
        this.useNextAction = true;
        this.usePlayPauseActions = true;
        this.useRewindAction = true;
        this.useFastForwardAction = true;
        this.colorized = true;
        this.useChronometer = true;
        this.color = 0;
        this.defaults = 0;
        this.priority = -1;
        this.badgeIconType = 1;
        this.visibility = 1;
        Map<String, NotificationCompat.Action> mapCreatePlaybackActions = createPlaybackActions(applicationContext, i11, i4, i5, i6, i7, i8, i9, i10);
        this.playbackActions = mapCreatePlaybackActions;
        Iterator<String> it = mapCreatePlaybackActions.keySet().iterator();
        while (it.hasNext()) {
            this.intentFilter.addAction(it.next());
        }
        Map<String, NotificationCompat.Action> mapCreateCustomActions = customActionReceiver != null ? customActionReceiver.createCustomActions(applicationContext, this.instanceId) : Collections.emptyMap();
        this.customActions = mapCreateCustomActions;
        Iterator<String> it2 = mapCreateCustomActions.keySet().iterator();
        while (it2.hasNext()) {
            this.intentFilter.addAction(it2.next());
        }
        this.dismissPendingIntent = createBroadcastIntent(ACTION_DISMISS, applicationContext, this.instanceId);
        this.intentFilter.addAction(ACTION_DISMISS);
    }

    private static PendingIntent createBroadcastIntent(String str, Context context, int i2) {
        Intent intent = new Intent(str).setPackage(context.getPackageName());
        intent.putExtra(EXTRA_INSTANCE_ID, i2);
        return PendingIntent.getBroadcast(context, i2, intent, Util.SDK_INT >= 23 ? 201326592 : 134217728);
    }

    private static Map<String, NotificationCompat.Action> createPlaybackActions(Context context, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        HashMap map = new HashMap();
        map.put(ACTION_PLAY, new NotificationCompat.Action(i3, context.getString(R.string.exo_controls_play_description), createBroadcastIntent(ACTION_PLAY, context, i2)));
        map.put(ACTION_PAUSE, new NotificationCompat.Action(i4, context.getString(R.string.exo_controls_pause_description), createBroadcastIntent(ACTION_PAUSE, context, i2)));
        map.put(ACTION_STOP, new NotificationCompat.Action(i5, context.getString(R.string.exo_controls_stop_description), createBroadcastIntent(ACTION_STOP, context, i2)));
        map.put(ACTION_REWIND, new NotificationCompat.Action(i6, context.getString(R.string.exo_controls_rewind_description), createBroadcastIntent(ACTION_REWIND, context, i2)));
        map.put(ACTION_FAST_FORWARD, new NotificationCompat.Action(i7, context.getString(R.string.exo_controls_fastforward_description), createBroadcastIntent(ACTION_FAST_FORWARD, context, i2)));
        map.put(ACTION_PREVIOUS, new NotificationCompat.Action(i8, context.getString(R.string.exo_controls_previous_description), createBroadcastIntent(ACTION_PREVIOUS, context, i2)));
        map.put(ACTION_NEXT, new NotificationCompat.Action(i9, context.getString(R.string.exo_controls_next_description), createBroadcastIntent(ACTION_NEXT, context, i2)));
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 0) {
            Player player = this.player;
            if (player != null) {
                startOrUpdateNotification(player, null);
            }
        } else {
            if (i2 != 1) {
                return false;
            }
            Player player2 = this.player;
            if (player2 != null && this.isNotificationStarted && this.currentNotificationTag == message.arg1) {
                startOrUpdateNotification(player2, (Bitmap) message.obj);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postStartOrUpdateNotification() {
        if (this.mainHandler.hasMessages(0)) {
            return;
        }
        this.mainHandler.sendEmptyMessage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUpdateNotificationBitmap(Bitmap bitmap, int i2) {
        this.mainHandler.obtainMessage(1, i2, -1, bitmap).sendToTarget();
    }

    private static void setLargeIcon(NotificationCompat.Builder builder, @Nullable Bitmap bitmap) {
        builder.setLargeIcon(bitmap);
    }

    private boolean shouldShowPauseButton(Player player) {
        return (player.getPlaybackState() == 4 || player.getPlaybackState() == 1 || !player.getPlayWhenReady()) ? false : true;
    }

    private void startOrUpdateNotification(Player player, @Nullable Bitmap bitmap) {
        boolean ongoing = getOngoing(player);
        NotificationCompat.Builder builderCreateNotification = createNotification(player, this.builder, ongoing, bitmap);
        this.builder = builderCreateNotification;
        if (builderCreateNotification == null) {
            stopNotification(false);
            return;
        }
        Notification notificationBuild = builderCreateNotification.build();
        this.notificationManager.notify(this.notificationId, notificationBuild);
        if (!this.isNotificationStarted) {
            this.context.registerReceiver(this.notificationBroadcastReceiver, this.intentFilter);
        }
        NotificationListener notificationListener = this.notificationListener;
        if (notificationListener != null) {
            notificationListener.onNotificationPosted(this.notificationId, notificationBuild, ongoing || !this.isNotificationStarted);
        }
        this.isNotificationStarted = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopNotification(boolean z2) {
        if (this.isNotificationStarted) {
            this.isNotificationStarted = false;
            this.mainHandler.removeMessages(0);
            this.notificationManager.cancel(this.notificationId);
            this.context.unregisterReceiver(this.notificationBroadcastReceiver);
            NotificationListener notificationListener = this.notificationListener;
            if (notificationListener != null) {
                notificationListener.onNotificationCancelled(this.notificationId, z2);
            }
        }
    }

    @Nullable
    public NotificationCompat.Builder createNotification(Player player, @Nullable NotificationCompat.Builder builder, boolean z2, @Nullable Bitmap bitmap) {
        if (player.getPlaybackState() == 1 && player.getCurrentTimeline().isEmpty()) {
            this.builderActions = null;
            return null;
        }
        List<String> actions = getActions(player);
        ArrayList arrayList = new ArrayList(actions.size());
        for (int i2 = 0; i2 < actions.size(); i2++) {
            String str = actions.get(i2);
            NotificationCompat.Action action = this.playbackActions.containsKey(str) ? this.playbackActions.get(str) : this.customActions.get(str);
            if (action != null) {
                arrayList.add(action);
            }
        }
        if (builder == null || !arrayList.equals(this.builderActions)) {
            builder = new NotificationCompat.Builder(this.context, this.channelId);
            this.builderActions = arrayList;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                builder.addAction((NotificationCompat.Action) arrayList.get(i3));
            }
        }
        NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
        MediaSessionCompat.Token token = this.mediaSessionToken;
        if (token != null) {
            mediaStyle.setMediaSession(token);
        }
        mediaStyle.setShowActionsInCompactView(getActionIndicesForCompactView(actions, player));
        mediaStyle.setShowCancelButton(!z2);
        mediaStyle.setCancelButtonIntent(this.dismissPendingIntent);
        builder.setStyle(mediaStyle);
        builder.setDeleteIntent(this.dismissPendingIntent);
        builder.setBadgeIconType(this.badgeIconType).setOngoing(z2).setColor(this.color).setColorized(this.colorized).setSmallIcon(this.smallIconResourceId).setVisibility(this.visibility).setPriority(this.priority).setDefaults(this.defaults);
        if (Util.SDK_INT < 21 || !this.useChronometer || !player.isPlaying() || player.isPlayingAd() || player.isCurrentMediaItemDynamic() || player.getPlaybackParameters().speed != 1.0f) {
            builder.setShowWhen(false).setUsesChronometer(false);
        } else {
            builder.setWhen(System.currentTimeMillis() - player.getContentPosition()).setShowWhen(true).setUsesChronometer(true);
        }
        builder.setContentTitle(this.mediaDescriptionAdapter.getCurrentContentTitle(player));
        builder.setContentText(this.mediaDescriptionAdapter.getCurrentContentText(player));
        builder.setSubText(this.mediaDescriptionAdapter.getCurrentSubText(player));
        if (bitmap == null) {
            MediaDescriptionAdapter mediaDescriptionAdapter = this.mediaDescriptionAdapter;
            int i4 = this.currentNotificationTag + 1;
            this.currentNotificationTag = i4;
            bitmap = mediaDescriptionAdapter.getCurrentLargeIcon(player, new BitmapCallback(i4));
        }
        setLargeIcon(builder, bitmap);
        builder.setContentIntent(this.mediaDescriptionAdapter.createCurrentContentIntent(player));
        String str2 = this.groupKey;
        if (str2 != null) {
            builder.setGroup(str2);
        }
        builder.setOnlyAlertOnce(true);
        return builder;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int[] getActionIndicesForCompactView(java.util.List<java.lang.String> r7, com.google.android.exoplayer2.Player r8) {
        /*
            r6 = this;
            java.lang.String r0 = "com.google.android.exoplayer.pause"
            int r0 = r7.indexOf(r0)
            java.lang.String r1 = "com.google.android.exoplayer.play"
            int r1 = r7.indexOf(r1)
            boolean r2 = r6.usePreviousActionInCompactView
            r3 = -1
            if (r2 == 0) goto L18
            java.lang.String r2 = "com.google.android.exoplayer.prev"
            int r2 = r7.indexOf(r2)
            goto L24
        L18:
            boolean r2 = r6.useRewindActionInCompactView
            if (r2 == 0) goto L23
            java.lang.String r2 = "com.google.android.exoplayer.rewind"
            int r2 = r7.indexOf(r2)
            goto L24
        L23:
            r2 = r3
        L24:
            boolean r4 = r6.useNextActionInCompactView
            if (r4 == 0) goto L2f
            java.lang.String r4 = "com.google.android.exoplayer.next"
            int r7 = r7.indexOf(r4)
            goto L3b
        L2f:
            boolean r4 = r6.useFastForwardActionInCompactView
            if (r4 == 0) goto L3a
            java.lang.String r4 = "com.google.android.exoplayer.ffwd"
            int r7 = r7.indexOf(r4)
            goto L3b
        L3a:
            r7 = r3
        L3b:
            r4 = 3
            int[] r4 = new int[r4]
            r5 = 0
            if (r2 == r3) goto L44
            r4[r5] = r2
            r5 = 1
        L44:
            boolean r8 = r6.shouldShowPauseButton(r8)
            if (r0 == r3) goto L52
            if (r8 == 0) goto L52
            int r8 = r5 + 1
            r4[r5] = r0
        L50:
            r5 = r8
            goto L5b
        L52:
            if (r1 == r3) goto L5b
            if (r8 != 0) goto L5b
            int r8 = r5 + 1
            r4[r5] = r1
            goto L50
        L5b:
            if (r7 == r3) goto L62
            int r8 = r5 + 1
            r4[r5] = r7
            r5 = r8
        L62:
            int[] r7 = java.util.Arrays.copyOf(r4, r5)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.PlayerNotificationManager.getActionIndicesForCompactView(java.util.List, com.google.android.exoplayer2.Player):int[]");
    }

    public List<String> getActions(Player player) {
        boolean zIsCommandAvailable = player.isCommandAvailable(7);
        boolean zIsCommandAvailable2 = player.isCommandAvailable(11);
        boolean zIsCommandAvailable3 = player.isCommandAvailable(12);
        boolean zIsCommandAvailable4 = player.isCommandAvailable(9);
        ArrayList arrayList = new ArrayList();
        if (this.usePreviousAction && zIsCommandAvailable) {
            arrayList.add(ACTION_PREVIOUS);
        }
        if (this.useRewindAction && zIsCommandAvailable2) {
            arrayList.add(ACTION_REWIND);
        }
        if (this.usePlayPauseActions) {
            if (shouldShowPauseButton(player)) {
                arrayList.add(ACTION_PAUSE);
            } else {
                arrayList.add(ACTION_PLAY);
            }
        }
        if (this.useFastForwardAction && zIsCommandAvailable3) {
            arrayList.add(ACTION_FAST_FORWARD);
        }
        if (this.useNextAction && zIsCommandAvailable4) {
            arrayList.add(ACTION_NEXT);
        }
        CustomActionReceiver customActionReceiver = this.customActionReceiver;
        if (customActionReceiver != null) {
            arrayList.addAll(customActionReceiver.getCustomActions(player));
        }
        if (this.useStopAction) {
            arrayList.add(ACTION_STOP);
        }
        return arrayList;
    }

    public boolean getOngoing(Player player) {
        int playbackState = player.getPlaybackState();
        return (playbackState == 2 || playbackState == 3) && player.getPlayWhenReady();
    }

    public final void invalidate() {
        if (this.isNotificationStarted) {
            postStartOrUpdateNotification();
        }
    }

    public final void setBadgeIconType(int i2) {
        if (this.badgeIconType == i2) {
            return;
        }
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException();
        }
        this.badgeIconType = i2;
        invalidate();
    }

    public final void setColor(int i2) {
        if (this.color != i2) {
            this.color = i2;
            invalidate();
        }
    }

    public final void setColorized(boolean z2) {
        if (this.colorized != z2) {
            this.colorized = z2;
            invalidate();
        }
    }

    public final void setDefaults(int i2) {
        if (this.defaults != i2) {
            this.defaults = i2;
            invalidate();
        }
    }

    public final void setMediaSessionToken(MediaSessionCompat.Token token) {
        if (Util.areEqual(this.mediaSessionToken, token)) {
            return;
        }
        this.mediaSessionToken = token;
        invalidate();
    }

    public final void setPlayer(@Nullable Player player) {
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
            player2.removeListener(this.playerListener);
            if (player == null) {
                stopNotification(false);
            }
        }
        this.player = player;
        if (player != null) {
            player.addListener(this.playerListener);
            postStartOrUpdateNotification();
        }
    }

    public final void setPriority(int i2) {
        if (this.priority == i2) {
            return;
        }
        if (i2 != -2 && i2 != -1 && i2 != 0 && i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException();
        }
        this.priority = i2;
        invalidate();
    }

    public final void setSmallIcon(@DrawableRes int i2) {
        if (this.smallIconResourceId != i2) {
            this.smallIconResourceId = i2;
            invalidate();
        }
    }

    public final void setUseChronometer(boolean z2) {
        if (this.useChronometer != z2) {
            this.useChronometer = z2;
            invalidate();
        }
    }

    public final void setUseFastForwardAction(boolean z2) {
        if (this.useFastForwardAction != z2) {
            this.useFastForwardAction = z2;
            invalidate();
        }
    }

    public final void setUseFastForwardActionInCompactView(boolean z2) {
        if (this.useFastForwardActionInCompactView != z2) {
            this.useFastForwardActionInCompactView = z2;
            if (z2) {
                this.useNextActionInCompactView = false;
            }
            invalidate();
        }
    }

    public final void setUseNextAction(boolean z2) {
        if (this.useNextAction != z2) {
            this.useNextAction = z2;
            invalidate();
        }
    }

    public final void setUseNextActionInCompactView(boolean z2) {
        if (this.useNextActionInCompactView != z2) {
            this.useNextActionInCompactView = z2;
            if (z2) {
                this.useFastForwardActionInCompactView = false;
            }
            invalidate();
        }
    }

    public final void setUsePlayPauseActions(boolean z2) {
        if (this.usePlayPauseActions != z2) {
            this.usePlayPauseActions = z2;
            invalidate();
        }
    }

    public final void setUsePreviousAction(boolean z2) {
        if (this.usePreviousAction != z2) {
            this.usePreviousAction = z2;
            invalidate();
        }
    }

    public final void setUsePreviousActionInCompactView(boolean z2) {
        if (this.usePreviousActionInCompactView != z2) {
            this.usePreviousActionInCompactView = z2;
            if (z2) {
                this.useRewindActionInCompactView = false;
            }
            invalidate();
        }
    }

    public final void setUseRewindAction(boolean z2) {
        if (this.useRewindAction != z2) {
            this.useRewindAction = z2;
            invalidate();
        }
    }

    public final void setUseRewindActionInCompactView(boolean z2) {
        if (this.useRewindActionInCompactView != z2) {
            this.useRewindActionInCompactView = z2;
            if (z2) {
                this.usePreviousActionInCompactView = false;
            }
            invalidate();
        }
    }

    public final void setUseStopAction(boolean z2) {
        if (this.useStopAction == z2) {
            return;
        }
        this.useStopAction = z2;
        invalidate();
    }

    public final void setVisibility(int i2) {
        if (this.visibility == i2) {
            return;
        }
        if (i2 != -1 && i2 != 0 && i2 != 1) {
            throw new IllegalStateException();
        }
        this.visibility = i2;
        invalidate();
    }

    public static class Builder {
        protected int channelDescriptionResourceId;
        protected final String channelId;
        protected int channelImportance;
        protected int channelNameResourceId;
        protected final Context context;

        @Nullable
        protected CustomActionReceiver customActionReceiver;
        protected int fastForwardActionIconResourceId;

        @Nullable
        protected String groupKey;
        protected MediaDescriptionAdapter mediaDescriptionAdapter;
        protected int nextActionIconResourceId;
        protected final int notificationId;

        @Nullable
        protected NotificationListener notificationListener;
        protected int pauseActionIconResourceId;
        protected int playActionIconResourceId;
        protected int previousActionIconResourceId;
        protected int rewindActionIconResourceId;
        protected int smallIconResourceId;
        protected int stopActionIconResourceId;

        @Deprecated
        public Builder(Context context, int i2, String str, MediaDescriptionAdapter mediaDescriptionAdapter) {
            this(context, i2, str);
            this.mediaDescriptionAdapter = mediaDescriptionAdapter;
        }

        public PlayerNotificationManager build() {
            int i2 = this.channelNameResourceId;
            if (i2 != 0) {
                NotificationUtil.createNotificationChannel(this.context, this.channelId, i2, this.channelDescriptionResourceId, this.channelImportance);
            }
            return new PlayerNotificationManager(this.context, this.channelId, this.notificationId, this.mediaDescriptionAdapter, this.notificationListener, this.customActionReceiver, this.smallIconResourceId, this.playActionIconResourceId, this.pauseActionIconResourceId, this.stopActionIconResourceId, this.rewindActionIconResourceId, this.fastForwardActionIconResourceId, this.previousActionIconResourceId, this.nextActionIconResourceId, this.groupKey);
        }

        public Builder setChannelDescriptionResourceId(int i2) {
            this.channelDescriptionResourceId = i2;
            return this;
        }

        public Builder setChannelImportance(int i2) {
            this.channelImportance = i2;
            return this;
        }

        public Builder setChannelNameResourceId(int i2) {
            this.channelNameResourceId = i2;
            return this;
        }

        public Builder setCustomActionReceiver(CustomActionReceiver customActionReceiver) {
            this.customActionReceiver = customActionReceiver;
            return this;
        }

        public Builder setFastForwardActionIconResourceId(int i2) {
            this.fastForwardActionIconResourceId = i2;
            return this;
        }

        public Builder setGroup(String str) {
            this.groupKey = str;
            return this;
        }

        public Builder setMediaDescriptionAdapter(MediaDescriptionAdapter mediaDescriptionAdapter) {
            this.mediaDescriptionAdapter = mediaDescriptionAdapter;
            return this;
        }

        public Builder setNextActionIconResourceId(int i2) {
            this.nextActionIconResourceId = i2;
            return this;
        }

        public Builder setNotificationListener(NotificationListener notificationListener) {
            this.notificationListener = notificationListener;
            return this;
        }

        public Builder setPauseActionIconResourceId(int i2) {
            this.pauseActionIconResourceId = i2;
            return this;
        }

        public Builder setPlayActionIconResourceId(int i2) {
            this.playActionIconResourceId = i2;
            return this;
        }

        public Builder setPreviousActionIconResourceId(int i2) {
            this.previousActionIconResourceId = i2;
            return this;
        }

        public Builder setRewindActionIconResourceId(int i2) {
            this.rewindActionIconResourceId = i2;
            return this;
        }

        public Builder setSmallIconResourceId(int i2) {
            this.smallIconResourceId = i2;
            return this;
        }

        public Builder setStopActionIconResourceId(int i2) {
            this.stopActionIconResourceId = i2;
            return this;
        }

        public Builder(Context context, @IntRange(from = 1) int i2, String str) {
            Assertions.checkArgument(i2 > 0);
            this.context = context;
            this.notificationId = i2;
            this.channelId = str;
            this.channelImportance = 2;
            this.mediaDescriptionAdapter = new DefaultMediaDescriptionAdapter(null);
            this.smallIconResourceId = R.drawable.exo_notification_small_icon;
            this.playActionIconResourceId = R.drawable.exo_notification_play;
            this.pauseActionIconResourceId = R.drawable.exo_notification_pause;
            this.stopActionIconResourceId = R.drawable.exo_notification_stop;
            this.rewindActionIconResourceId = R.drawable.exo_notification_rewind;
            this.fastForwardActionIconResourceId = R.drawable.exo_notification_fastforward;
            this.previousActionIconResourceId = R.drawable.exo_notification_previous;
            this.nextActionIconResourceId = R.drawable.exo_notification_next;
        }
    }
}
