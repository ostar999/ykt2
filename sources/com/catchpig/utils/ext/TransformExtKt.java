package com.catchpig.utils.ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.umeng.analytics.pro.d;
import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0003\u001a\u0014\u0010\u0004\u001a\u00020\u0003*\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u001a\u0014\u0010\u0004\u001a\u00020\u0003*\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\u0007\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\b\u001a\u00020\t\u001a\u0012\u0010\u0007\u001a\u00020\u0002*\u00020\u00032\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"toBitmap", "Landroid/graphics/Bitmap;", "Landroid/graphics/drawable/Drawable;", "", "toByteArray", "format", "Landroid/graphics/Bitmap$CompressFormat;", "toDrawable", d.R, "Landroid/content/Context;", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class TransformExtKt {
    @NotNull
    public static final Bitmap toBitmap(@NotNull byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        Intrinsics.checkNotNullExpressionValue(bitmapDecodeByteArray, "decodeByteArray(this, 0, size)");
        return bitmapDecodeByteArray;
    }

    @NotNull
    public static final byte[] toByteArray(@NotNull Bitmap bitmap, @NotNull Bitmap.CompressFormat format) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "baos.toByteArray()");
        return byteArray;
    }

    public static /* synthetic */ byte[] toByteArray$default(Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            compressFormat = Bitmap.CompressFormat.PNG;
        }
        return toByteArray(bitmap, compressFormat);
    }

    @NotNull
    public static final Drawable toDrawable(@NotNull Bitmap bitmap, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    @NotNull
    public static final Bitmap toBitmap(@NotNull Drawable drawable) {
        Bitmap bitmap;
        Intrinsics.checkNotNullParameter(drawable, "<this>");
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
            Intrinsics.checkNotNullExpressionValue(bitmap2, "bitmap");
            return bitmap2;
        }
        if (drawable.getIntrinsicHeight() <= 0 || drawable.getIntrinsicWidth() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
        return bitmap;
    }

    public static /* synthetic */ byte[] toByteArray$default(Drawable drawable, Bitmap.CompressFormat compressFormat, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            compressFormat = Bitmap.CompressFormat.PNG;
        }
        return toByteArray(drawable, compressFormat);
    }

    @NotNull
    public static final Drawable toDrawable(@NotNull byte[] bArr, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return toDrawable(toBitmap(bArr), context);
    }

    @NotNull
    public static final byte[] toByteArray(@NotNull Drawable drawable, @NotNull Bitmap.CompressFormat format) {
        Intrinsics.checkNotNullParameter(drawable, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        return toByteArray(toBitmap(drawable), format);
    }
}
