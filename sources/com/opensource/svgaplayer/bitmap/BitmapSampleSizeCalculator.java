package com.opensource.svgaplayer.bitmap;

import android.graphics.BitmapFactory;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"}, d2 = {"Lcom/opensource/svgaplayer/bitmap/BitmapSampleSizeCalculator;", "", "()V", "calculate", "", "options", "Landroid/graphics/BitmapFactory$Options;", "reqWidth", "reqHeight", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class BitmapSampleSizeCalculator {
    public static final BitmapSampleSizeCalculator INSTANCE = new BitmapSampleSizeCalculator();

    private BitmapSampleSizeCalculator() {
    }

    public final int calculate(@NotNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        Pair pair = TuplesKt.to(Integer.valueOf(options.outHeight), Integer.valueOf(options.outWidth));
        int iIntValue = ((Number) pair.component1()).intValue();
        int iIntValue2 = ((Number) pair.component2()).intValue();
        int i2 = 1;
        if (reqHeight > 0 && reqWidth > 0 && (iIntValue > reqHeight || iIntValue2 > reqWidth)) {
            int i3 = iIntValue / 2;
            int i4 = iIntValue2 / 2;
            while (i3 / i2 >= reqHeight && i4 / i2 >= reqWidth) {
                i2 *= 2;
            }
        }
        return i2;
    }
}
