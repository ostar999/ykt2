package com.psychiatrygarden.widget.glideUtil.progress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;
import java.net.URL;

/* loaded from: classes6.dex */
public class GlideRequests extends RequestManager {
    public GlideRequests(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode treeNode, @NonNull Context context) {
        super(glide, lifecycle, treeNode, context);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public /* bridge */ /* synthetic */ RequestManager addDefaultRequestListener(RequestListener listener) {
        return addDefaultRequestListener((RequestListener<Object>) listener);
    }

    @Override // com.bumptech.glide.RequestManager
    public void setRequestOptions(@NonNull RequestOptions toSet) {
        if (toSet instanceof GlideOptions) {
            super.setRequestOptions(toSet);
        } else {
            super.setRequestOptions(new GlideOptions().apply((BaseRequestOptions<?>) toSet));
        }
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public GlideRequests addDefaultRequestListener(RequestListener<Object> listener) {
        return (GlideRequests) super.addDefaultRequestListener(listener);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public synchronized GlideRequests applyDefaultRequestOptions(@NonNull RequestOptions options) {
        return (GlideRequests) super.applyDefaultRequestOptions(options);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public <ResourceType> GlideRequest<ResourceType> as(@NonNull Class<ResourceType> resourceClass) {
        return new GlideRequest<>(this.glide, this, resourceClass, this.context);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Bitmap> asBitmap() {
        return (GlideRequest) super.asBitmap();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> asDrawable() {
        return (GlideRequest) super.asDrawable();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<File> asFile() {
        return (GlideRequest) super.asFile();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<GifDrawable> asGif() {
        return (GlideRequest) super.asGif();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<File> download(@Nullable Object o2) {
        return (GlideRequest) super.download(o2);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<File> downloadOnly() {
        return (GlideRequest) super.downloadOnly();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public synchronized GlideRequests setDefaultRequestOptions(@NonNull RequestOptions options) {
        return (GlideRequests) super.setDefaultRequestOptions(options);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Bitmap bitmap) {
        return (GlideRequest) super.load(bitmap);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Drawable drawable) {
        return (GlideRequest) super.load(drawable);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable String string) {
        return (GlideRequest) super.load(string);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Uri uri) {
        return (GlideRequest) super.load(uri);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable File file) {
        return (GlideRequest) super.load(file);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable @DrawableRes @RawRes Integer id) {
        return (GlideRequest) super.load(id);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @Deprecated
    public RequestBuilder<Drawable> load(@Nullable URL url) {
        return (GlideRequest) super.load(url);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable byte[] bytes) {
        return (GlideRequest) super.load(bytes);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Object o2) {
        return (GlideRequest) super.load(o2);
    }
}
