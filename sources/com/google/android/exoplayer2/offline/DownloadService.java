package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.HashMap;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes3.dex */
public abstract class DownloadService extends Service {
    public static final String ACTION_ADD_DOWNLOAD = "com.google.android.exoplayer.downloadService.action.ADD_DOWNLOAD";
    public static final String ACTION_INIT = "com.google.android.exoplayer.downloadService.action.INIT";
    public static final String ACTION_PAUSE_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.PAUSE_DOWNLOADS";
    public static final String ACTION_REMOVE_ALL_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.REMOVE_ALL_DOWNLOADS";
    public static final String ACTION_REMOVE_DOWNLOAD = "com.google.android.exoplayer.downloadService.action.REMOVE_DOWNLOAD";
    private static final String ACTION_RESTART = "com.google.android.exoplayer.downloadService.action.RESTART";
    public static final String ACTION_RESUME_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.RESUME_DOWNLOADS";
    public static final String ACTION_SET_REQUIREMENTS = "com.google.android.exoplayer.downloadService.action.SET_REQUIREMENTS";
    public static final String ACTION_SET_STOP_REASON = "com.google.android.exoplayer.downloadService.action.SET_STOP_REASON";
    public static final long DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000;
    public static final int FOREGROUND_NOTIFICATION_ID_NONE = 0;
    public static final String KEY_CONTENT_ID = "content_id";
    public static final String KEY_DOWNLOAD_REQUEST = "download_request";
    public static final String KEY_FOREGROUND = "foreground";
    public static final String KEY_REQUIREMENTS = "requirements";
    public static final String KEY_STOP_REASON = "stop_reason";
    private static final String TAG = "DownloadService";
    private static final HashMap<Class<? extends DownloadService>, DownloadManagerHelper> downloadManagerHelpers = new HashMap<>();

    @StringRes
    private final int channelDescriptionResourceId;

    @Nullable
    private final String channelId;

    @StringRes
    private final int channelNameResourceId;
    private DownloadManagerHelper downloadManagerHelper;

    @Nullable
    private final ForegroundNotificationUpdater foregroundNotificationUpdater;
    private boolean isDestroyed;
    private boolean isStopped;
    private int lastStartId;
    private boolean startedInForeground;
    private boolean taskRemoved;

    public static final class DownloadManagerHelper implements DownloadManager.Listener {
        private final Context context;
        private final DownloadManager downloadManager;

        @Nullable
        private DownloadService downloadService;
        private final boolean foregroundAllowed;
        private Requirements scheduledRequirements;

        @Nullable
        private final Scheduler scheduler;
        private final Class<? extends DownloadService> serviceClass;

        @RequiresNonNull({"scheduler"})
        private void cancelScheduler() {
            Requirements requirements = new Requirements(0);
            if (schedulerNeedsUpdate(requirements)) {
                this.scheduler.cancel();
                this.scheduledRequirements = requirements;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$attachService$0(DownloadService downloadService) {
            downloadService.notifyDownloads(this.downloadManager.getCurrentDownloads());
        }

        private void restartService() {
            if (this.foregroundAllowed) {
                try {
                    Util.startForegroundService(this.context, DownloadService.getIntent(this.context, this.serviceClass, DownloadService.ACTION_RESTART));
                    return;
                } catch (IllegalStateException unused) {
                    Log.w(DownloadService.TAG, "Failed to restart (foreground launch restriction)");
                    return;
                }
            }
            try {
                this.context.startService(DownloadService.getIntent(this.context, this.serviceClass, DownloadService.ACTION_INIT));
            } catch (IllegalStateException unused2) {
                Log.w(DownloadService.TAG, "Failed to restart (process is idle)");
            }
        }

        private boolean schedulerNeedsUpdate(Requirements requirements) {
            return !Util.areEqual(this.scheduledRequirements, requirements);
        }

        private boolean serviceMayNeedRestart() {
            DownloadService downloadService = this.downloadService;
            return downloadService == null || downloadService.isStopped();
        }

        public void attachService(final DownloadService downloadService) {
            Assertions.checkState(this.downloadService == null);
            this.downloadService = downloadService;
            if (this.downloadManager.isInitialized()) {
                Util.createHandlerForCurrentOrMainLooper().postAtFrontOfQueue(new Runnable() { // from class: com.google.android.exoplayer2.offline.n
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6826c.lambda$attachService$0(downloadService);
                    }
                });
            }
        }

        public void detachService(DownloadService downloadService) {
            Assertions.checkState(this.downloadService == downloadService);
            this.downloadService = null;
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onDownloadChanged(DownloadManager downloadManager, Download download, @Nullable Exception exc) {
            DownloadService downloadService = this.downloadService;
            if (downloadService != null) {
                downloadService.notifyDownloadChanged(download);
            }
            if (serviceMayNeedRestart() && DownloadService.needsStartedService(download.state)) {
                Log.w(DownloadService.TAG, "DownloadService wasn't running. Restarting.");
                restartService();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onDownloadRemoved(DownloadManager downloadManager, Download download) {
            DownloadService downloadService = this.downloadService;
            if (downloadService != null) {
                downloadService.notifyDownloadRemoved();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public /* synthetic */ void onDownloadsPausedChanged(DownloadManager downloadManager, boolean z2) {
            m.c(this, downloadManager, z2);
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public final void onIdle(DownloadManager downloadManager) {
            DownloadService downloadService = this.downloadService;
            if (downloadService != null) {
                downloadService.onIdle();
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onInitialized(DownloadManager downloadManager) {
            DownloadService downloadService = this.downloadService;
            if (downloadService != null) {
                downloadService.notifyDownloads(downloadManager.getCurrentDownloads());
            }
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onRequirementsStateChanged(DownloadManager downloadManager, Requirements requirements, int i2) {
            updateScheduler();
        }

        @Override // com.google.android.exoplayer2.offline.DownloadManager.Listener
        public void onWaitingForRequirementsChanged(DownloadManager downloadManager, boolean z2) {
            if (z2 || downloadManager.getDownloadsPaused() || !serviceMayNeedRestart()) {
                return;
            }
            List<Download> currentDownloads = downloadManager.getCurrentDownloads();
            for (int i2 = 0; i2 < currentDownloads.size(); i2++) {
                if (currentDownloads.get(i2).state == 0) {
                    restartService();
                    return;
                }
            }
        }

        public boolean updateScheduler() {
            boolean zIsWaitingForRequirements = this.downloadManager.isWaitingForRequirements();
            if (this.scheduler == null) {
                return !zIsWaitingForRequirements;
            }
            if (!zIsWaitingForRequirements) {
                cancelScheduler();
                return true;
            }
            Requirements requirements = this.downloadManager.getRequirements();
            if (!this.scheduler.getSupportedRequirements(requirements).equals(requirements)) {
                cancelScheduler();
                return false;
            }
            if (!schedulerNeedsUpdate(requirements)) {
                return true;
            }
            if (this.scheduler.schedule(requirements, this.context.getPackageName(), DownloadService.ACTION_RESTART)) {
                this.scheduledRequirements = requirements;
                return true;
            }
            Log.w(DownloadService.TAG, "Failed to schedule restart");
            cancelScheduler();
            return false;
        }

        private DownloadManagerHelper(Context context, DownloadManager downloadManager, boolean z2, @Nullable Scheduler scheduler, Class<? extends DownloadService> cls) {
            this.context = context;
            this.downloadManager = downloadManager;
            this.foregroundAllowed = z2;
            this.scheduler = scheduler;
            this.serviceClass = cls;
            downloadManager.addListener(this);
            updateScheduler();
        }
    }

    public final class ForegroundNotificationUpdater {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private boolean notificationDisplayed;
        private final int notificationId;
        private boolean periodicUpdatesStarted;
        private final long updateInterval;

        public ForegroundNotificationUpdater(int i2, long j2) {
            this.notificationId = i2;
            this.updateInterval = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update() {
            DownloadManager downloadManager = ((DownloadManagerHelper) Assertions.checkNotNull(DownloadService.this.downloadManagerHelper)).downloadManager;
            Notification foregroundNotification = DownloadService.this.getForegroundNotification(downloadManager.getCurrentDownloads(), downloadManager.getNotMetRequirements());
            if (this.notificationDisplayed) {
                ((NotificationManager) DownloadService.this.getSystemService(RemoteMessageConst.NOTIFICATION)).notify(this.notificationId, foregroundNotification);
            } else {
                DownloadService.this.startForeground(this.notificationId, foregroundNotification);
                this.notificationDisplayed = true;
            }
            if (this.periodicUpdatesStarted) {
                this.handler.removeCallbacksAndMessages(null);
                this.handler.postDelayed(new Runnable() { // from class: com.google.android.exoplayer2.offline.o
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f6828c.update();
                    }
                }, this.updateInterval);
            }
        }

        public void invalidate() {
            if (this.notificationDisplayed) {
                update();
            }
        }

        public void showNotificationIfNotAlready() {
            if (this.notificationDisplayed) {
                return;
            }
            update();
        }

        public void startPeriodicUpdates() {
            this.periodicUpdatesStarted = true;
            update();
        }

        public void stopPeriodicUpdates() {
            this.periodicUpdatesStarted = false;
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    public DownloadService(int i2) {
        this(i2, 1000L);
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, boolean z2) {
        return buildAddDownloadIntent(context, cls, downloadRequest, 0, z2);
    }

    public static Intent buildPauseDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z2) {
        return getIntent(context, cls, ACTION_PAUSE_DOWNLOADS, z2);
    }

    public static Intent buildRemoveAllDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z2) {
        return getIntent(context, cls, ACTION_REMOVE_ALL_DOWNLOADS, z2);
    }

    public static Intent buildRemoveDownloadIntent(Context context, Class<? extends DownloadService> cls, String str, boolean z2) {
        return getIntent(context, cls, ACTION_REMOVE_DOWNLOAD, z2).putExtra(KEY_CONTENT_ID, str);
    }

    public static Intent buildResumeDownloadsIntent(Context context, Class<? extends DownloadService> cls, boolean z2) {
        return getIntent(context, cls, ACTION_RESUME_DOWNLOADS, z2);
    }

    public static Intent buildSetRequirementsIntent(Context context, Class<? extends DownloadService> cls, Requirements requirements, boolean z2) {
        return getIntent(context, cls, ACTION_SET_REQUIREMENTS, z2).putExtra(KEY_REQUIREMENTS, requirements);
    }

    public static Intent buildSetStopReasonIntent(Context context, Class<? extends DownloadService> cls, @Nullable String str, int i2, boolean z2) {
        return getIntent(context, cls, ACTION_SET_STOP_REASON, z2).putExtra(KEY_CONTENT_ID, str).putExtra(KEY_STOP_REASON, i2);
    }

    private static Intent getIntent(Context context, Class<? extends DownloadService> cls, String str, boolean z2) {
        return getIntent(context, cls, str).putExtra(KEY_FOREGROUND, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isStopped() {
        return this.isStopped;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean needsStartedService(int i2) {
        return i2 == 2 || i2 == 5 || i2 == 7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadChanged(Download download) {
        if (this.foregroundNotificationUpdater != null) {
            if (needsStartedService(download.state)) {
                this.foregroundNotificationUpdater.startPeriodicUpdates();
            } else {
                this.foregroundNotificationUpdater.invalidate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloadRemoved() {
        ForegroundNotificationUpdater foregroundNotificationUpdater = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater != null) {
            foregroundNotificationUpdater.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDownloads(List<Download> list) {
        if (this.foregroundNotificationUpdater != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (needsStartedService(list.get(i2).state)) {
                    this.foregroundNotificationUpdater.startPeriodicUpdates();
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onIdle() {
        ForegroundNotificationUpdater foregroundNotificationUpdater = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater != null) {
            foregroundNotificationUpdater.stopPeriodicUpdates();
        }
        if (((DownloadManagerHelper) Assertions.checkNotNull(this.downloadManagerHelper)).updateScheduler()) {
            if (Util.SDK_INT >= 28 || !this.taskRemoved) {
                this.isStopped |= stopSelfResult(this.lastStartId);
            } else {
                stopSelf();
                this.isStopped = true;
            }
        }
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, boolean z2) {
        startService(context, buildAddDownloadIntent(context, cls, downloadRequest, z2), z2);
    }

    public static void sendPauseDownloads(Context context, Class<? extends DownloadService> cls, boolean z2) {
        startService(context, buildPauseDownloadsIntent(context, cls, z2), z2);
    }

    public static void sendRemoveAllDownloads(Context context, Class<? extends DownloadService> cls, boolean z2) {
        startService(context, buildRemoveAllDownloadsIntent(context, cls, z2), z2);
    }

    public static void sendRemoveDownload(Context context, Class<? extends DownloadService> cls, String str, boolean z2) {
        startService(context, buildRemoveDownloadIntent(context, cls, str, z2), z2);
    }

    public static void sendResumeDownloads(Context context, Class<? extends DownloadService> cls, boolean z2) {
        startService(context, buildResumeDownloadsIntent(context, cls, z2), z2);
    }

    public static void sendSetRequirements(Context context, Class<? extends DownloadService> cls, Requirements requirements, boolean z2) {
        startService(context, buildSetRequirementsIntent(context, cls, requirements, z2), z2);
    }

    public static void sendSetStopReason(Context context, Class<? extends DownloadService> cls, @Nullable String str, int i2, boolean z2) {
        startService(context, buildSetStopReasonIntent(context, cls, str, i2, z2), z2);
    }

    public static void start(Context context, Class<? extends DownloadService> cls) {
        context.startService(getIntent(context, cls, ACTION_INIT));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> cls) {
        Util.startForegroundService(context, getIntent(context, cls, ACTION_INIT, true));
    }

    private static void startService(Context context, Intent intent, boolean z2) {
        if (z2) {
            Util.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    public abstract DownloadManager getDownloadManager();

    public abstract Notification getForegroundNotification(List<Download> list, int i2);

    @Nullable
    public abstract Scheduler getScheduler();

    public final void invalidateForegroundNotification() {
        ForegroundNotificationUpdater foregroundNotificationUpdater = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater == null || this.isDestroyed) {
            return;
        }
        foregroundNotificationUpdater.invalidate();
    }

    @Override // android.app.Service
    @Nullable
    public final IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.Service
    public void onCreate() {
        String str = this.channelId;
        if (str != null) {
            NotificationUtil.createNotificationChannel(this, str, this.channelNameResourceId, this.channelDescriptionResourceId, 2);
        }
        Class<?> cls = getClass();
        HashMap<Class<? extends DownloadService>, DownloadManagerHelper> map = downloadManagerHelpers;
        DownloadManagerHelper downloadManagerHelper = (DownloadManagerHelper) map.get(cls);
        if (downloadManagerHelper == null) {
            boolean z2 = this.foregroundNotificationUpdater != null;
            Scheduler scheduler = (z2 && (Util.SDK_INT < 31)) ? getScheduler() : null;
            DownloadManager downloadManager = getDownloadManager();
            downloadManager.resumeDownloads();
            downloadManagerHelper = new DownloadManagerHelper(getApplicationContext(), downloadManager, z2, scheduler, cls);
            map.put(cls, downloadManagerHelper);
        }
        this.downloadManagerHelper = downloadManagerHelper;
        downloadManagerHelper.attachService(this);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.isDestroyed = true;
        ((DownloadManagerHelper) Assertions.checkNotNull(this.downloadManagerHelper)).detachService(this);
        ForegroundNotificationUpdater foregroundNotificationUpdater = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater != null) {
            foregroundNotificationUpdater.stopPeriodicUpdates();
        }
    }

    @Override // android.app.Service
    public int onStartCommand(@Nullable Intent intent, int i2, int i3) {
        String action;
        String stringExtra;
        DownloadManager downloadManager;
        ForegroundNotificationUpdater foregroundNotificationUpdater;
        this.lastStartId = i3;
        this.taskRemoved = false;
        if (intent != null) {
            action = intent.getAction();
            stringExtra = intent.getStringExtra(KEY_CONTENT_ID);
            this.startedInForeground |= intent.getBooleanExtra(KEY_FOREGROUND, false) || ACTION_RESTART.equals(action);
        } else {
            action = null;
            stringExtra = null;
        }
        if (action == null) {
            action = ACTION_INIT;
        }
        downloadManager = ((DownloadManagerHelper) Assertions.checkNotNull(this.downloadManagerHelper)).downloadManager;
        switch (action) {
            case "com.google.android.exoplayer.downloadService.action.ADD_DOWNLOAD":
                DownloadRequest downloadRequest = (DownloadRequest) ((Intent) Assertions.checkNotNull(intent)).getParcelableExtra(KEY_DOWNLOAD_REQUEST);
                if (downloadRequest != null) {
                    downloadManager.addDownload(downloadRequest, intent.getIntExtra(KEY_STOP_REASON, 0));
                    break;
                } else {
                    Log.e(TAG, "Ignored ADD_DOWNLOAD: Missing download_request extra");
                    break;
                }
            case "com.google.android.exoplayer.downloadService.action.RESUME_DOWNLOADS":
                downloadManager.resumeDownloads();
                break;
            case "com.google.android.exoplayer.downloadService.action.RESTART":
            case "com.google.android.exoplayer.downloadService.action.INIT":
                break;
            case "com.google.android.exoplayer.downloadService.action.REMOVE_ALL_DOWNLOADS":
                downloadManager.removeAllDownloads();
                break;
            case "com.google.android.exoplayer.downloadService.action.SET_REQUIREMENTS":
                Requirements requirements = (Requirements) ((Intent) Assertions.checkNotNull(intent)).getParcelableExtra(KEY_REQUIREMENTS);
                if (requirements != null) {
                    downloadManager.setRequirements(requirements);
                    break;
                } else {
                    Log.e(TAG, "Ignored SET_REQUIREMENTS: Missing requirements extra");
                    break;
                }
            case "com.google.android.exoplayer.downloadService.action.PAUSE_DOWNLOADS":
                downloadManager.pauseDownloads();
                break;
            case "com.google.android.exoplayer.downloadService.action.SET_STOP_REASON":
                if (!((Intent) Assertions.checkNotNull(intent)).hasExtra(KEY_STOP_REASON)) {
                    Log.e(TAG, "Ignored SET_STOP_REASON: Missing stop_reason extra");
                    break;
                } else {
                    downloadManager.setStopReason(stringExtra, intent.getIntExtra(KEY_STOP_REASON, 0));
                    break;
                }
            case "com.google.android.exoplayer.downloadService.action.REMOVE_DOWNLOAD":
                if (stringExtra != null) {
                    downloadManager.removeDownload(stringExtra);
                    break;
                } else {
                    Log.e(TAG, "Ignored REMOVE_DOWNLOAD: Missing content_id extra");
                    break;
                }
            default:
                Log.e(TAG, action.length() != 0 ? "Ignored unrecognized action: ".concat(action) : new String("Ignored unrecognized action: "));
                break;
        }
        if (Util.SDK_INT >= 26 && this.startedInForeground && (foregroundNotificationUpdater = this.foregroundNotificationUpdater) != null) {
            foregroundNotificationUpdater.showNotificationIfNotAlready();
        }
        this.isStopped = false;
        if (downloadManager.isIdle()) {
            onIdle();
        }
        return 1;
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent intent) {
        this.taskRemoved = true;
    }

    public DownloadService(int i2, long j2) {
        this(i2, j2, null, 0, 0);
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, int i2, boolean z2) {
        return getIntent(context, cls, ACTION_ADD_DOWNLOAD, z2).putExtra(KEY_DOWNLOAD_REQUEST, downloadRequest).putExtra(KEY_STOP_REASON, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Intent getIntent(Context context, Class<? extends DownloadService> cls, String str) {
        return new Intent(context, cls).setAction(str);
    }

    @Deprecated
    public DownloadService(int i2, long j2, @Nullable String str, @StringRes int i3) {
        this(i2, j2, str, i3, 0);
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> cls, DownloadRequest downloadRequest, int i2, boolean z2) {
        startService(context, buildAddDownloadIntent(context, cls, downloadRequest, i2, z2), z2);
    }

    public DownloadService(int i2, long j2, @Nullable String str, @StringRes int i3, @StringRes int i4) {
        if (i2 == 0) {
            this.foregroundNotificationUpdater = null;
            this.channelId = null;
            this.channelNameResourceId = 0;
            this.channelDescriptionResourceId = 0;
            return;
        }
        this.foregroundNotificationUpdater = new ForegroundNotificationUpdater(i2, j2);
        this.channelId = str;
        this.channelNameResourceId = i3;
        this.channelDescriptionResourceId = i4;
    }
}
