package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class PendingIntentCompat {

    @RequiresApi(16)
    public static class Api16Impl {
        private Api16Impl() {
        }

        @NonNull
        @DoNotInline
        public static PendingIntent getActivities(@NonNull Context context, int i2, @NonNull @SuppressLint({"ArrayReturn"}) Intent[] intentArr, int i3, @NonNull Bundle bundle) {
            return PendingIntent.getActivities(context, i2, intentArr, i3, bundle);
        }

        @NonNull
        @DoNotInline
        public static PendingIntent getActivity(@NonNull Context context, int i2, @NonNull Intent intent, int i3, @NonNull Bundle bundle) {
            return PendingIntent.getActivity(context, i2, intent, i3, bundle);
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static PendingIntent getForegroundService(Context context, int i2, Intent intent, int i3) {
            return PendingIntent.getForegroundService(context, i2, intent, i3);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private PendingIntentCompat() {
    }

    private static int addMutabilityFlags(boolean z2, int i2) {
        int i3;
        if (!z2) {
            i3 = 67108864;
        } else {
            if (Build.VERSION.SDK_INT < 31) {
                return i2;
            }
            i3 = 33554432;
        }
        return i2 | i3;
    }

    @NonNull
    public static PendingIntent getActivities(@NonNull Context context, int i2, @NonNull @SuppressLint({"ArrayReturn"}) Intent[] intentArr, int i3, @NonNull Bundle bundle, boolean z2) {
        return Api16Impl.getActivities(context, i2, intentArr, addMutabilityFlags(z2, i3), bundle);
    }

    @NonNull
    public static PendingIntent getActivity(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return PendingIntent.getActivity(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @NonNull
    public static PendingIntent getBroadcast(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return PendingIntent.getBroadcast(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @NonNull
    @RequiresApi(26)
    public static PendingIntent getForegroundService(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return Api26Impl.getForegroundService(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @NonNull
    public static PendingIntent getService(@NonNull Context context, int i2, @NonNull Intent intent, int i3, boolean z2) {
        return PendingIntent.getService(context, i2, intent, addMutabilityFlags(z2, i3));
    }

    @NonNull
    public static PendingIntent getActivities(@NonNull Context context, int i2, @NonNull @SuppressLint({"ArrayReturn"}) Intent[] intentArr, int i3, boolean z2) {
        return PendingIntent.getActivities(context, i2, intentArr, addMutabilityFlags(z2, i3));
    }

    @NonNull
    public static PendingIntent getActivity(@NonNull Context context, int i2, @NonNull Intent intent, int i3, @NonNull Bundle bundle, boolean z2) {
        return Api16Impl.getActivity(context, i2, intent, addMutabilityFlags(z2, i3), bundle);
    }
}
