package com.squareup.picasso;

import android.app.Notification;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.squareup.picasso.Picasso;

/* loaded from: classes6.dex */
abstract class RemoteViewsAction extends Action<RemoteViewsTarget> {
    final RemoteViews remoteViews;
    private RemoteViewsTarget target;
    final int viewId;

    public static class AppWidgetAction extends RemoteViewsAction {
        private final int[] appWidgetIds;

        public AppWidgetAction(Picasso picasso, Request request, RemoteViews remoteViews, int i2, int[] iArr, boolean z2, int i3, String str, Object obj) {
            super(picasso, request, remoteViews, i2, i3, z2, str, obj);
            this.appWidgetIds = iArr;
        }

        @Override // com.squareup.picasso.RemoteViewsAction, com.squareup.picasso.Action
        public /* bridge */ /* synthetic */ RemoteViewsTarget getTarget() {
            return super.getTarget();
        }

        @Override // com.squareup.picasso.RemoteViewsAction
        public void update() {
            AppWidgetManager.getInstance(this.picasso.context).updateAppWidget(this.appWidgetIds, this.remoteViews);
        }
    }

    public static class NotificationAction extends RemoteViewsAction {
        private final Notification notification;
        private final int notificationId;

        public NotificationAction(Picasso picasso, Request request, RemoteViews remoteViews, int i2, int i3, Notification notification, boolean z2, int i4, String str, Object obj) {
            super(picasso, request, remoteViews, i2, i4, z2, str, obj);
            this.notificationId = i3;
            this.notification = notification;
        }

        @Override // com.squareup.picasso.RemoteViewsAction, com.squareup.picasso.Action
        public /* bridge */ /* synthetic */ RemoteViewsTarget getTarget() {
            return super.getTarget();
        }

        @Override // com.squareup.picasso.RemoteViewsAction
        public void update() {
            ((NotificationManager) Utils.getService(this.picasso.context, RemoteMessageConst.NOTIFICATION)).notify(this.notificationId, this.notification);
        }
    }

    public static class RemoteViewsTarget {
        final RemoteViews remoteViews;
        final int viewId;

        public RemoteViewsTarget(RemoteViews remoteViews, int i2) {
            this.remoteViews = remoteViews;
            this.viewId = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RemoteViewsTarget remoteViewsTarget = (RemoteViewsTarget) obj;
            return this.viewId == remoteViewsTarget.viewId && this.remoteViews.equals(remoteViewsTarget.remoteViews);
        }

        public int hashCode() {
            return (this.remoteViews.hashCode() * 31) + this.viewId;
        }
    }

    public RemoteViewsAction(Picasso picasso, Request request, RemoteViews remoteViews, int i2, int i3, boolean z2, String str, Object obj) {
        super(picasso, null, request, z2, false, i3, null, str, obj);
        this.remoteViews = remoteViews;
        this.viewId = i2;
    }

    @Override // com.squareup.picasso.Action
    public void complete(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        this.remoteViews.setImageViewBitmap(this.viewId, bitmap);
        update();
    }

    @Override // com.squareup.picasso.Action
    public void error() {
        int i2 = this.errorResId;
        if (i2 != 0) {
            setImageResource(i2);
        }
    }

    public void setImageResource(int i2) {
        this.remoteViews.setImageViewResource(this.viewId, i2);
        update();
    }

    public abstract void update();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.squareup.picasso.Action
    public RemoteViewsTarget getTarget() {
        if (this.target == null) {
            this.target = new RemoteViewsTarget(this.remoteViews, this.viewId);
        }
        return this.target;
    }
}
