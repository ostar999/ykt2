package com.ykb.ebook.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004H\u0002¢\u0006\u0002\u0010\tJ \u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u001e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J(\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0004J(\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u0013J)\u0010\u0014\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000f¨\u0006\u001e"}, d2 = {"Lcom/ykb/ebook/util/BitmapUtils;", "", "()V", "calculateInSampleSize", "", "options", "Landroid/graphics/BitmapFactory$Options;", "width", "height", "(Landroid/graphics/BitmapFactory$Options;Ljava/lang/Integer;Ljava/lang/Integer;)I", "computeInitialSampleSize", "minSideLength", "maxNumOfPixels", "computeSampleSize", "decodeAssetsBitmap", "Landroid/graphics/Bitmap;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "fileNameInAssets", "", "decodeBitmap", "resId", "path", "(Ljava/lang/String;ILjava/lang/Integer;)Landroid/graphics/Bitmap;", "getTopRadiusDrawabl", "Landroid/graphics/drawable/Drawable;", "radius", "toInputStream", "Ljava/io/InputStream;", "bitmap", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBitmapUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BitmapUtils.kt\ncom/ykb/ebook/util/BitmapUtils\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,338:1\n1#2:339\n*E\n"})
/* loaded from: classes7.dex */
public final class BitmapUtils {

    @NotNull
    public static final BitmapUtils INSTANCE = new BitmapUtils();

    private BitmapUtils() {
    }

    private final int calculateInSampleSize(BitmapFactory.Options options, Integer width, Integer height) {
        int iIntValue;
        int iIntValue2 = -1;
        if (width != null) {
            iIntValue = options.outWidth / width.intValue();
        } else {
            iIntValue = -1;
        }
        if (height != null) {
            iIntValue2 = options.outHeight / height.intValue();
        }
        if (iIntValue > 1 && iIntValue2 > 1) {
            return Math.max(iIntValue, iIntValue2);
        }
        if (iIntValue > 1) {
            return iIntValue;
        }
        if (iIntValue2 > 1) {
            return iIntValue2;
        }
        return 1;
    }

    public static /* synthetic */ int calculateInSampleSize$default(BitmapUtils bitmapUtils, BitmapFactory.Options options, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            num = null;
        }
        if ((i2 & 4) != 0) {
            num2 = null;
        }
        return bitmapUtils.calculateInSampleSize(options, num, num2);
    }

    private final int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int iMin;
        double d3 = options.outWidth;
        double d4 = options.outHeight;
        int iCeil = maxNumOfPixels == -1 ? 1 : (int) Math.ceil(Math.sqrt((d3 * d4) / maxNumOfPixels));
        if (minSideLength == -1) {
            iMin = 128;
        } else {
            double d5 = minSideLength;
            iMin = (int) Math.min(Math.floor(d3 / d5), Math.floor(d4 / d5));
        }
        if (iMin < iCeil) {
            return iCeil;
        }
        if (maxNumOfPixels == -1 && minSideLength == -1) {
            return 1;
        }
        return minSideLength == -1 ? iCeil : iMin;
    }

    public static /* synthetic */ Bitmap decodeBitmap$default(BitmapUtils bitmapUtils, String str, int i2, Integer num, int i3, Object obj) throws IOException {
        if ((i3 & 4) != 0) {
            num = null;
        }
        return bitmapUtils.decodeBitmap(str, i2, num);
    }

    public final int computeSampleSize(@NotNull BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        Intrinsics.checkNotNullParameter(options, "options");
        int iComputeInitialSampleSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        if (iComputeInitialSampleSize > 8) {
            return 8 * ((iComputeInitialSampleSize + 7) / 8);
        }
        int i2 = 1;
        while (i2 < iComputeInitialSampleSize) {
            i2 <<= 1;
        }
        return i2;
    }

    @Nullable
    public final Bitmap decodeAssetsBitmap(@NotNull Context context, @NotNull String fileNameInAssets, int width, int height) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fileNameInAssets, "fileNameInAssets");
        InputStream inputStreamOpen = context.getAssets().open(fileNameInAssets);
        Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "context.assets.open(fileNameInAssets)");
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStreamOpen, null, options);
            options.inSampleSize = INSTANCE.calculateInSampleSize(options, Integer.valueOf(width), Integer.valueOf(height));
            InputStream inputStreamOpen2 = context.getAssets().open(fileNameInAssets);
            Intrinsics.checkNotNullExpressionValue(inputStreamOpen2, "context.assets.open(fileNameInAssets)");
            options.inJustDecodeBounds = false;
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen2, null, options);
            CloseableKt.closeFinally(inputStreamOpen, null);
            return bitmapDecodeStream;
        } finally {
        }
    }

    @Nullable
    public final Bitmap decodeBitmap(@NotNull String path, int width, @Nullable Integer height) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        FileInputStream fileInputStream = new FileInputStream(path);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileInputStream.getFD(), null, options);
            options.inSampleSize = INSTANCE.calculateInSampleSize(options, Integer.valueOf(width), height);
            options.inJustDecodeBounds = false;
            Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileInputStream.getFD(), null, options);
            CloseableKt.closeFinally(fileInputStream, null);
            return bitmapDecodeFileDescriptor;
        } finally {
        }
    }

    @Nullable
    public final Drawable getTopRadiusDrawabl(@NotNull Context context, int radius) throws Resources.NotFoundException {
        GradientDrawable gradientDrawable;
        Intrinsics.checkNotNullParameter(context, "context");
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.shape_white_top_16_bg);
            Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            gradientDrawable = (GradientDrawable) drawable;
        } else if (colorMode == 1) {
            Drawable drawable2 = context.getResources().getDrawable(R.drawable.shape_yellow_top_16_bg);
            Intrinsics.checkNotNull(drawable2, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            gradientDrawable = (GradientDrawable) drawable2;
        } else {
            if (colorMode != 2) {
                throw new IllegalArgumentException("Invalid color mode");
            }
            Drawable drawable3 = context.getResources().getDrawable(R.drawable.shape_blue_top_16_bg);
            Intrinsics.checkNotNull(drawable3, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            gradientDrawable = (GradientDrawable) drawable3;
        }
        gradientDrawable.setCornerRadii(new float[]{ConvertExtensionsKt.dpToPx(radius), ConvertExtensionsKt.dpToPx(radius), ConvertExtensionsKt.dpToPx(radius), ConvertExtensionsKt.dpToPx(radius), 0.0f, 0.0f, 0.0f, 0.0f});
        return gradientDrawable;
    }

    @NotNull
    public final InputStream toInputStream(@NotNull Bitmap bitmap) throws IOException {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close();
        return byteArrayInputStream;
    }

    @Nullable
    public final Bitmap decodeBitmap(@NotNull String path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        FileInputStream fileInputStream = new FileInputStream(path);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileInputStream.getFD(), null, options);
            options.inSampleSize = INSTANCE.computeSampleSize(options, -1, 16384);
            options.inJustDecodeBounds = false;
            Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileInputStream.getFD(), null, options);
            CloseableKt.closeFinally(fileInputStream, null);
            return bitmapDecodeFileDescriptor;
        } finally {
        }
    }

    @Nullable
    public final Bitmap decodeBitmap(@NotNull Context context, int resId) {
        Intrinsics.checkNotNullParameter(context, "context");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    @Nullable
    public final Bitmap decodeBitmap(@NotNull Context context, int resId, int width, int height) {
        Intrinsics.checkNotNullParameter(context, "context");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inSampleSize = calculateInSampleSize(options, Integer.valueOf(width), Integer.valueOf(height));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }
}
