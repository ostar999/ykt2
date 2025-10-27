package com.psychiatrygarden.widget.glideUtil.progress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import java.io.File;

/* loaded from: classes6.dex */
public final class GlideApp {
    private GlideApp() {
    }

    @SuppressLint({"VisibleForTests"})
    @VisibleForTesting
    public static void enableHardwareBitmaps() {
        Glide.enableHardwareBitmaps();
    }

    @NonNull
    public static Glide get(@NonNull Context context) {
        return Glide.get(context);
    }

    @Nullable
    public static File getPhotoCacheDir(@NonNull Context context) {
        return Glide.getPhotoCacheDir(context);
    }

    @SuppressLint({"VisibleForTests"})
    @VisibleForTesting
    @Deprecated
    public static void init(Glide glide) {
        Glide.init(glide);
    }

    @SuppressLint({"VisibleForTests"})
    @VisibleForTesting
    public static void tearDown() {
        Glide.tearDown();
    }

    @NonNull
    public static GlideRequests with(@NonNull Context context) {
        return (GlideRequests) Glide.with(context);
    }

    @Nullable
    public static File getPhotoCacheDir(@NonNull Context context, @NonNull String string) {
        return Glide.getPhotoCacheDir(context, string);
    }

    @SuppressLint({"VisibleForTests"})
    @VisibleForTesting
    public static void init(@NonNull Context context, @NonNull GlideBuilder builder) {
        Glide.init(context, builder);
    }

    @NonNull
    public static GlideRequests with(@NonNull Activity activity) {
        return (GlideRequests) Glide.with(activity);
    }

    @NonNull
    public static GlideRequests with(@NonNull FragmentActivity activity) {
        return (GlideRequests) Glide.with(activity);
    }

    @NonNull
    public static GlideRequests with(@NonNull Fragment fragment) {
        return (GlideRequests) Glide.with(fragment);
    }

    @NonNull
    @Deprecated
    public static GlideRequests with(@NonNull android.app.Fragment fragment) {
        return (GlideRequests) Glide.with(fragment);
    }

    @NonNull
    public static GlideRequests with(@NonNull View view) {
        return (GlideRequests) Glide.with(view);
    }
}
