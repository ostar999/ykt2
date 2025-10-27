package com.azhon.appupdate.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.azhon.appupdate.config.Constant;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.service.DownloadService;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.plv.socket.user.PLVSocketUserConstant;
import com.umeng.analytics.pro.d;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/azhon/appupdate/util/NotificationUtil;", "", "()V", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class NotificationUtil {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0003J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u0011\u001a\u00020\u000eH\u0003J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\nJ6\u0010\u0014\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0017J&\u0010\u0018\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eJ&\u0010\u0019\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eJ6\u0010\u001a\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\f¨\u0006\u001d"}, d2 = {"Lcom/azhon/appupdate/util/NotificationUtil$Companion;", "", "()V", "afterO", "", PLVSocketUserConstant.USERTYPE_MANAGER, "Landroid/app/NotificationManager;", "builderNotification", "Landroidx/core/app/NotificationCompat$Builder;", d.R, "Landroid/content/Context;", RemoteMessageConst.Notification.ICON, "", "title", "", "content", "cancelNotification", "getNotificationChannelId", "notificationEnable", "", "showDoneNotification", "authorities", "apk", "Ljava/io/File;", "showErrorNotification", "showNotification", "showProgressNotification", "max", "progress", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @RequiresApi(api = 26)
        private final void afterO(NotificationManager manager) {
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
            NotificationChannel notificationChannel = instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotificationChannel() : null;
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(Constant.DEFAULT_CHANNEL_ID, Constant.DEFAULT_CHANNEL_NAME, 2);
                notificationChannel.enableLights(true);
                notificationChannel.setShowBadge(true);
            }
            manager.createNotificationChannel(notificationChannel);
        }

        private final NotificationCompat.Builder builderNotification(Context context, int icon, String title, String content) {
            NotificationCompat.Builder ongoing = new NotificationCompat.Builder(context, Build.VERSION.SDK_INT >= 26 ? getNotificationChannelId() : "").setSmallIcon(icon).setContentTitle(title).setWhen(System.currentTimeMillis()).setContentText(content).setAutoCancel(false).setOngoing(true);
            Intrinsics.checkNotNullExpressionValue(ongoing, "Builder(context, channel…        .setOngoing(true)");
            return ongoing;
        }

        @RequiresApi(api = 26)
        private final String getNotificationChannelId() {
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
            NotificationChannel notificationChannel = instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotificationChannel() : null;
            if (notificationChannel == null) {
                return Constant.DEFAULT_CHANNEL_ID;
            }
            String id = notificationChannel.getId();
            Intrinsics.checkNotNullExpressionValue(id, "{\n                channel.id\n            }");
            return id;
        }

        public final void cancelNotification(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            Object systemService = context.getSystemService(RemoteMessageConst.NOTIFICATION);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
            notificationManager.cancel(instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotifyId() : 1011);
        }

        public final boolean notificationEnable(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }

        public final void showDoneNotification(@NotNull Context context, int icon, @NotNull String title, @NotNull String content, @NotNull String authorities, @NotNull File apk) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(content, "content");
            Intrinsics.checkNotNullParameter(authorities, "authorities");
            Intrinsics.checkNotNullParameter(apk, "apk");
            Object systemService = context.getSystemService(RemoteMessageConst.NOTIFICATION);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            DownloadManager.Companion companion = DownloadManager.INSTANCE;
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(companion, null, 1, null);
            notificationManager.cancel(instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotifyId() : 1011);
            Notification notificationBuild = builderNotification(context, icon, title, content).setContentIntent(PendingIntent.getActivity(context, 0, ApkUtil.INSTANCE.createInstallIntent(context, authorities, apk), 67108864)).build();
            Intrinsics.checkNotNullExpressionValue(notificationBuild, "builderNotification(cont…\n                .build()");
            notificationBuild.flags |= 16;
            DownloadManager instance$mvvm_release$default2 = DownloadManager.Companion.getInstance$mvvm_release$default(companion, null, 1, null);
            notificationManager.notify(instance$mvvm_release$default2 != null ? instance$mvvm_release$default2.getNotifyId() : 1011, notificationBuild);
        }

        public final void showErrorNotification(@NotNull Context context, int icon, @NotNull String title, @NotNull String content) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(content, "content");
            Object systemService = context.getSystemService(RemoteMessageConst.NOTIFICATION);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            if (Build.VERSION.SDK_INT >= 26) {
                afterO(notificationManager);
            }
            Notification notificationBuild = builderNotification(context, icon, title, content).setAutoCancel(true).setOngoing(false).setContentIntent(PendingIntent.getService(context, 0, new Intent(context, (Class<?>) DownloadService.class), 67108864)).setDefaults(1).build();
            Intrinsics.checkNotNullExpressionValue(notificationBuild, "builderNotification(cont…\n                .build()");
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
            notificationManager.notify(instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotifyId() : 1011, notificationBuild);
        }

        public final void showNotification(@NotNull Context context, int icon, @NotNull String title, @NotNull String content) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(content, "content");
            Object systemService = context.getSystemService(RemoteMessageConst.NOTIFICATION);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            if (Build.VERSION.SDK_INT >= 26) {
                afterO(notificationManager);
            }
            Notification notificationBuild = builderNotification(context, icon, title, content).setDefaults(1).build();
            Intrinsics.checkNotNullExpressionValue(notificationBuild, "builderNotification(cont…\n                .build()");
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
            notificationManager.notify(instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotifyId() : 1011, notificationBuild);
        }

        public final void showProgressNotification(@NotNull Context context, int icon, @NotNull String title, @NotNull String content, int max, int progress) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(content, "content");
            Object systemService = context.getSystemService(RemoteMessageConst.NOTIFICATION);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            Notification notificationBuild = builderNotification(context, icon, title, content).setProgress(max, progress, max == -1).build();
            Intrinsics.checkNotNullExpressionValue(notificationBuild, "builderNotification(cont…gress, max == -1).build()");
            DownloadManager instance$mvvm_release$default = DownloadManager.Companion.getInstance$mvvm_release$default(DownloadManager.INSTANCE, null, 1, null);
            notificationManager.notify(instance$mvvm_release$default != null ? instance$mvvm_release$default.getNotifyId() : 1011, notificationBuild);
        }
    }
}
