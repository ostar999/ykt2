package com.ykb.ebook.common_interface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0003H\u0017J\b\u0010\u0005\u001a\u00020\u0006H&J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0001\u0010\u0004\u001a\u00020\u0003H\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0001\u0010\u0004\u001a\u00020\u0003H\u0016J1\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0001\u0010\u0004\u001a\u00020\u00032\u0016\u0010\r\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000e\"\u0004\u0018\u00010\u0001H\u0016¢\u0006\u0002\u0010\u000fJ!\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0013H\u0016¢\u0006\u0002\u0010\u0014¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/common_interface/ResourcesAction;", "", "getColor", "", "id", "getContext", "Landroid/content/Context;", "getDrawable", "Landroid/graphics/drawable/Drawable;", "getResources", "Landroid/content/res/Resources;", "getString", "", "formatArgs", "", "(I[Ljava/lang/Object;)Ljava/lang/String;", "getSystemService", ExifInterface.LATITUDE_SOUTH, "serviceClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Ljava/lang/Object;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface ResourcesAction {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        @ColorInt
        public static int getColor(@NotNull ResourcesAction resourcesAction, @ColorRes int i2) {
            return ContextCompat.getColor(resourcesAction.getContext(), i2);
        }

        @Nullable
        public static Drawable getDrawable(@NotNull ResourcesAction resourcesAction, @DrawableRes int i2) {
            return ContextCompat.getDrawable(resourcesAction.getContext(), i2);
        }

        @NotNull
        public static Resources getResources(@NotNull ResourcesAction resourcesAction) {
            Resources resources = resourcesAction.getContext().getResources();
            Intrinsics.checkNotNullExpressionValue(resources, "getContext().resources");
            return resources;
        }

        @Nullable
        public static String getString(@NotNull ResourcesAction resourcesAction, @StringRes int i2) {
            return resourcesAction.getContext().getString(i2);
        }

        public static <S> S getSystemService(@NotNull ResourcesAction resourcesAction, @NotNull Class<S> serviceClass) {
            Intrinsics.checkNotNullParameter(serviceClass, "serviceClass");
            S s2 = (S) ContextCompat.getSystemService(resourcesAction.getContext(), serviceClass);
            Intrinsics.checkNotNull(s2);
            return s2;
        }

        @Nullable
        public static String getString(@NotNull ResourcesAction resourcesAction, @StringRes int i2, @NotNull Object... formatArgs) {
            Intrinsics.checkNotNullParameter(formatArgs, "formatArgs");
            return resourcesAction.getResources().getString(i2, Arrays.copyOf(formatArgs, formatArgs.length));
        }
    }

    @ColorInt
    int getColor(@ColorRes int id);

    @NotNull
    Context getContext();

    @Nullable
    Drawable getDrawable(@DrawableRes int id);

    @NotNull
    Resources getResources();

    @Nullable
    String getString(@StringRes int id);

    @Nullable
    String getString(@StringRes int id, @NotNull Object... formatArgs);

    <S> S getSystemService(@NotNull Class<S> serviceClass);
}
