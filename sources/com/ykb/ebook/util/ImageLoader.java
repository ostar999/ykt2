package com.ykb.ebook.util;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.lifecycle.Lifecycle;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.ykb.ebook.extensions.RequestManagerExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import java.io.File;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u0005J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0001\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015¨\u0006\u001a"}, d2 = {"Lcom/ykb/ebook/util/ImageLoader;", "", "()V", "load", "Lcom/bumptech/glide/RequestBuilder;", "Landroid/graphics/drawable/Drawable;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bitmap", "Landroid/graphics/Bitmap;", "drawable", "uri", "Landroid/net/Uri;", "file", "Ljava/io/File;", HttpHeaderValues.BYTES, "", "resId", "", "(Landroid/content/Context;Ljava/lang/Integer;)Lcom/bumptech/glide/RequestBuilder;", "path", "", RequestParameters.SUBRESOURCE_LIFECYCLE, "Landroidx/lifecycle/Lifecycle;", "loadBitmap", "loadFile", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ImageLoader {

    @NotNull
    public static final ImageLoader INSTANCE = new ImageLoader();

    private ImageLoader() {
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @Nullable String path) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        if (path == null || path.length() == 0) {
            RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(path)");
            return requestBuilderLoad;
        }
        if (StringExtensionsKt.isDataUrl(path)) {
            RequestBuilder<Drawable> requestBuilderLoad2 = Glide.with(context).load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad2, "with(context).load(path)");
            return requestBuilderLoad2;
        }
        if (StringExtensionsKt.isAbsUrl(path)) {
            RequestBuilder<Drawable> requestBuilderLoad3 = Glide.with(context).load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad3, "with(context).load(path)");
            return requestBuilderLoad3;
        }
        if (StringExtensionsKt.isContentScheme(path)) {
            RequestBuilder<Drawable> requestBuilderLoad4 = Glide.with(context).load(Uri.parse(path));
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad4, "with(context).load(Uri.parse(path))");
            return requestBuilderLoad4;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Glide.with(context).load(new File(path)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m786exceptionOrNullimpl(objM783constructorimpl) != null) {
            objM783constructorimpl = Glide.with(context).load(path);
        }
        Intrinsics.checkNotNullExpressionValue(objM783constructorimpl, "runCatching {\n          ….load(path)\n            }");
        return (RequestBuilder) objM783constructorimpl;
    }

    @NotNull
    public final RequestBuilder<Bitmap> loadBitmap(@NotNull Context context, @Nullable String path) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        if (path == null || path.length() == 0) {
            RequestBuilder<Bitmap> requestBuilderLoad = Glide.with(context).asBitmap().load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).asBitmap().load(path)");
            return requestBuilderLoad;
        }
        if (StringExtensionsKt.isDataUrl(path)) {
            RequestBuilder<Bitmap> requestBuilderLoad2 = Glide.with(context).asBitmap().load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad2, "with(context).asBitmap().load(path)");
            return requestBuilderLoad2;
        }
        if (StringExtensionsKt.isAbsUrl(path)) {
            RequestBuilder<Bitmap> requestBuilderLoad3 = Glide.with(context).asBitmap().load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad3, "with(context).asBitmap().load(path)");
            return requestBuilderLoad3;
        }
        if (StringExtensionsKt.isContentScheme(path)) {
            RequestBuilder<Bitmap> requestBuilderLoad4 = Glide.with(context).asBitmap().load(Uri.parse(path));
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad4, "with(context).asBitmap().load(Uri.parse(path))");
            return requestBuilderLoad4;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Glide.with(context).asBitmap().load(new File(path)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m786exceptionOrNullimpl(objM783constructorimpl) != null) {
            objM783constructorimpl = Glide.with(context).asBitmap().load(path);
        }
        Intrinsics.checkNotNullExpressionValue(objM783constructorimpl, "runCatching {\n          ….load(path)\n            }");
        return (RequestBuilder) objM783constructorimpl;
    }

    @NotNull
    public final RequestBuilder<File> loadFile(@NotNull Context context, @Nullable String path) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        if (path == null || path.length() == 0) {
            RequestBuilder<File> requestBuilderLoad = Glide.with(context).asFile().load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).asFile().load(path)");
            return requestBuilderLoad;
        }
        if (StringExtensionsKt.isAbsUrl(path)) {
            RequestBuilder<File> requestBuilderLoad2 = Glide.with(context).asFile().load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad2, "with(context).asFile().load(path)");
            return requestBuilderLoad2;
        }
        if (StringExtensionsKt.isContentScheme(path)) {
            RequestBuilder<File> requestBuilderLoad3 = Glide.with(context).asFile().load(Uri.parse(path));
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad3, "with(context).asFile().load(Uri.parse(path))");
            return requestBuilderLoad3;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(Glide.with(context).asFile().load(new File(path)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m786exceptionOrNullimpl(objM783constructorimpl) != null) {
            objM783constructorimpl = Glide.with(context).asFile().load(path);
        }
        Intrinsics.checkNotNullExpressionValue(objM783constructorimpl, "runCatching {\n          ….load(path)\n            }");
        return (RequestBuilder) objM783constructorimpl;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Lifecycle lifecycle, @Nullable String path) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(lifecycle, "lifecycle");
        RequestManager requestManagerWith = Glide.with(AppCtxKt.getAppCtx());
        Intrinsics.checkNotNullExpressionValue(requestManagerWith, "with(appCtx)");
        RequestManager requestManagerLifecycle = RequestManagerExtensionsKt.lifecycle(requestManagerWith, lifecycle);
        if (path == null || path.length() == 0) {
            RequestBuilder<Drawable> requestBuilderLoad = requestManagerLifecycle.load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "requestManager.load(path)");
            return requestBuilderLoad;
        }
        if (StringExtensionsKt.isDataUrl(path)) {
            RequestBuilder<Drawable> requestBuilderLoad2 = requestManagerLifecycle.load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad2, "requestManager.load(path)");
            return requestBuilderLoad2;
        }
        if (StringExtensionsKt.isAbsUrl(path)) {
            RequestBuilder<Drawable> requestBuilderLoad3 = requestManagerLifecycle.load(path);
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad3, "requestManager.load(path)");
            return requestBuilderLoad3;
        }
        if (StringExtensionsKt.isContentScheme(path)) {
            RequestBuilder<Drawable> requestBuilderLoad4 = requestManagerLifecycle.load(Uri.parse(path));
            Intrinsics.checkNotNullExpressionValue(requestBuilderLoad4, "requestManager.load(Uri.parse(path))");
            return requestBuilderLoad4;
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(requestManagerLifecycle.load(new File(path)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m786exceptionOrNullimpl(objM783constructorimpl) != null) {
            objM783constructorimpl = requestManagerLifecycle.load(path);
        }
        Intrinsics.checkNotNullExpressionValue(objM783constructorimpl, "runCatching {\n          ….load(path)\n            }");
        return (RequestBuilder) objM783constructorimpl;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @DrawableRes @Nullable Integer resId) {
        Intrinsics.checkNotNullParameter(context, "context");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(resId);
        Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(resId)");
        return requestBuilderLoad;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @Nullable File file) {
        Intrinsics.checkNotNullParameter(context, "context");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(file);
        Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(file)");
        return requestBuilderLoad;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @Nullable Uri uri) {
        Intrinsics.checkNotNullParameter(context, "context");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(uri);
        Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(uri)");
        return requestBuilderLoad;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @Nullable Drawable drawable) {
        Intrinsics.checkNotNullParameter(context, "context");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(drawable);
        Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(drawable)");
        return requestBuilderLoad;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @Nullable Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(context, "context");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(bitmap);
        Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(bitmap)");
        return requestBuilderLoad;
    }

    @NotNull
    public final RequestBuilder<Drawable> load(@NotNull Context context, @Nullable byte[] bytes) {
        Intrinsics.checkNotNullParameter(context, "context");
        RequestBuilder<Drawable> requestBuilderLoad = Glide.with(context).load(bytes);
        Intrinsics.checkNotNullExpressionValue(requestBuilderLoad, "with(context).load(bytes)");
        return requestBuilderLoad;
    }
}
