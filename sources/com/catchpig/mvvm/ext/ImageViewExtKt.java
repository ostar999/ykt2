package com.catchpig.mvvm.ext;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.catchpig.mvvm.utils.SuperGlideTransformation;
import com.catchpig.utils.manager.ContextManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u001aç\u0001\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\b\u0002\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\t2\b\b\u0002\u0010\u0015\u001a\u00020\u00062\b\b\u0002\u0010\u0016\u001a\u00020\u00062\b\b\u0002\u0010\u0017\u001a\u00020\t2'\b\u0002\u0010\u0018\u001a!\u0012\u0015\u0012\u0013\u0018\u00010\u001a¢\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00192\u0010\b\u0002\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u001f\u001a6\u0010 \u001a\u00020\u0001*\u00020!2\u0018\b\u0002\u0010\"\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00192\u0010\b\u0002\u0010#\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u001f¨\u0006$"}, d2 = {"load", "", "Landroid/widget/ImageView;", "url", "", "placeholder", "", "error", "isCircle", "", "isCenterCrop", "borderSize", "borderColor", "blurScale", "", "blurRadius", "roundRadius", "roundArray", "", "isCrossFade", "isForceOriginalSize", "targetWidth", "targetHeight", "diskNoCache", "onImageLoad", "Lkotlin/Function1;", "Landroid/graphics/drawable/Drawable;", "Lkotlin/ParameterName;", "name", "resource", "onImageFail", "Lkotlin/Function0;", "preloadImage", "", "onSuccess", "onFail", "mvvm_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class ImageViewExtKt {
    public static final void load(@NotNull ImageView imageView, @Nullable Object obj, int i2, int i3, boolean z2, boolean z3, int i4, int i5, float f2, float f3, int i6, @Nullable float[] fArr, boolean z4, boolean z5, int i7, int i8, boolean z6, @Nullable final Function1<? super Drawable, Unit> function1, @Nullable final Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(imageView, "<this>");
        if (imageView.getContext() == null) {
            return;
        }
        if (imageView.getContext() instanceof Activity) {
            Context context = imageView.getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.app.Activity");
            if (((Activity) context).isDestroyed()) {
                return;
            }
            Context context2 = imageView.getContext();
            Intrinsics.checkNotNull(context2, "null cannot be cast to non-null type android.app.Activity");
            if (((Activity) context2).isFinishing()) {
                return;
            }
        }
        if (z3) {
            ImageView.ScaleType scaleType = imageView.getScaleType();
            ImageView.ScaleType scaleType2 = ImageView.ScaleType.CENTER_CROP;
            if (scaleType != scaleType2) {
                imageView.setScaleType(scaleType2);
            }
        }
        int iMax = (z2 && i6 == 0) ? Math.max(imageView.getMeasuredWidth(), imageView.getLayoutParams().width) / 2 : i6;
        RequestOptions requestOptionsError = new RequestOptions().placeholder(i2).error(i3);
        RequestOptions requestOptions = requestOptionsError;
        if (z5) {
            requestOptions.override(Integer.MIN_VALUE);
        }
        if (i7 != 0 && i8 != 0) {
            requestOptions.override(i7, i8);
        }
        Intrinsics.checkNotNullExpressionValue(requestOptionsError, "RequestOptions().placeho…etHeight)\n        }\n    }");
        requestOptions.transform(new SuperGlideTransformation(z3 || imageView.getScaleType() == ImageView.ScaleType.CENTER_CROP, f2, f3, iMax, i5, i4, fArr));
        RequestBuilder requestBuilderDiskCacheStrategy = Glide.with(imageView.getContext()).load(obj).apply((BaseRequestOptions<?>) requestOptions).diskCacheStrategy(z6 ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC);
        RequestBuilder requestBuilder = requestBuilderDiskCacheStrategy;
        if (z4) {
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        }
        if (function1 != null || function0 != null) {
            requestBuilder.listener(new RequestListener<Drawable>() { // from class: com.catchpig.mvvm.ext.ImageViewExtKt$load$glide$1$1
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException e2, @Nullable Object model, @Nullable Target<Drawable> target, boolean isFirstResource) {
                    Function0<Unit> function02 = function0;
                    if (function02 != null) {
                        function02.invoke();
                    }
                    return function0 != null;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(@Nullable Drawable resource, @Nullable Object model, @Nullable Target<Drawable> target, @Nullable DataSource dataSource, boolean isFirstResource) {
                    Function1<Drawable, Unit> function12 = function1;
                    if (function12 == null) {
                        return false;
                    }
                    function12.invoke(resource);
                    return false;
                }
            });
        }
        Intrinsics.checkNotNullExpressionValue(requestBuilderDiskCacheStrategy, "with(context).load(url).…)\n            }\n        }");
        requestBuilder.into(imageView);
    }

    public static final void preloadImage(@NotNull String str, @Nullable final Function1<? super Drawable, Unit> function1, @Nullable final Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Glide.with(ContextManager.INSTANCE.getInstance().getContext()).load(str).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<Drawable>() { // from class: com.catchpig.mvvm.ext.ImageViewExtKt.preloadImage.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException e2, @Nullable Object model, @Nullable Target<Drawable> target, boolean isFirstResource) {
                Function0<Unit> function02 = function0;
                if (function02 == null) {
                    return false;
                }
                function02.invoke();
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(@Nullable Drawable resource, @Nullable Object model, @Nullable Target<Drawable> target, @Nullable DataSource dataSource, boolean isFirstResource) {
                Function1<Drawable, Unit> function12 = function1;
                if (function12 == null) {
                    return false;
                }
                function12.invoke(resource);
                return false;
            }
        }).preload();
    }

    public static /* synthetic */ void preloadImage$default(String str, Function1 function1, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            function1 = null;
        }
        if ((i2 & 2) != 0) {
            function0 = null;
        }
        preloadImage(str, function1, function0);
    }
}
