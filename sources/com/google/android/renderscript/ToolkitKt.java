package com.google.android.renderscript;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u0006\u0010\u0005\u001a\u00020\u0003H\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0000\u001a\"\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\rH\u0000\u001a\u001a\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\b\u001a\u00020\u0007H\u0000\u001a$\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0000\u001a,\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0000\u001a\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0003H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"externalName", "", "createCompatibleBitmap", "Landroid/graphics/Bitmap;", "kotlin.jvm.PlatformType", "inputBitmap", "paddedSize", "", "vectorSize", "validateBitmap", "", "function", "alphaAllowed", "", "validateHistogramDotCoefficients", "coefficients", "", "validateRestriction", "tag", "bitmap", "restriction", "Lcom/google/android/renderscript/Range2d;", "sizeX", "sizeY", "renderscript-toolkit_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class ToolkitKt {
    private static final String externalName = "RenderScript Toolkit";

    @Metadata(bv = {1, 0, 3}, k = 3, mv = {1, 4, 2})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Bitmap.Config.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            iArr[Bitmap.Config.ALPHA_8.ordinal()] = 2;
        }
    }

    public static final Bitmap createCompatibleBitmap(@NotNull Bitmap inputBitmap) {
        Intrinsics.checkNotNullParameter(inputBitmap, "inputBitmap");
        return Bitmap.createBitmap(inputBitmap.getWidth(), inputBitmap.getHeight(), inputBitmap.getConfig());
    }

    public static final int paddedSize(int i2) {
        if (i2 == 3) {
            return 4;
        }
        return i2;
    }

    public static final void validateBitmap(@NotNull String function, @NotNull Bitmap inputBitmap, boolean z2) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(inputBitmap, "inputBitmap");
        if (z2) {
            if (!(inputBitmap.getConfig() == Bitmap.Config.ARGB_8888 || inputBitmap.getConfig() == Bitmap.Config.ALPHA_8)) {
                throw new IllegalArgumentException(("RenderScript Toolkit. " + function + " supports only ARGB_8888 and ALPHA_8 bitmaps. " + inputBitmap.getConfig() + " provided.").toString());
            }
        } else {
            if (!(inputBitmap.getConfig() == Bitmap.Config.ARGB_8888)) {
                throw new IllegalArgumentException(("RenderScript Toolkit. " + function + " supports only ARGB_8888. " + inputBitmap.getConfig() + " provided.").toString());
            }
        }
        if (inputBitmap.getWidth() * vectorSize(inputBitmap) == inputBitmap.getRowBytes()) {
            return;
        }
        throw new IllegalArgumentException(("RenderScript Toolkit " + function + ". Only bitmaps with rowSize equal to the width * vectorSize are currently supported. Provided were rowBytes=" + inputBitmap.getRowBytes() + ", width={" + inputBitmap.getWidth() + ", and vectorSize=" + vectorSize(inputBitmap) + '.').toString());
    }

    public static /* synthetic */ void validateBitmap$default(String str, Bitmap bitmap, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        validateBitmap(str, bitmap, z2);
    }

    public static final void validateHistogramDotCoefficients(@Nullable float[] fArr, int i2) {
        if (!(fArr == null || fArr.length == i2)) {
            throw new IllegalArgumentException(("RenderScript Toolkit histogramDot. The coefficients should be null or have " + i2 + " values.").toString());
        }
        if (fArr != null) {
            float f2 = 0.0f;
            for (int i3 = 0; i3 < i2; i3++) {
                float f3 = fArr[i3];
                if (!(f3 >= 0.0f)) {
                    throw new IllegalArgumentException(("RenderScript Toolkit histogramDot. Coefficients should not be negative. Coefficient " + i3 + " was " + fArr[i3] + '.').toString());
                }
                f2 += f3;
            }
            if (f2 <= 1.0f) {
                return;
            }
            throw new IllegalArgumentException(("RenderScript Toolkit histogramDot. Coefficients should add to 1 or less. Their sum is " + f2 + '.').toString());
        }
    }

    public static final void validateRestriction(@NotNull String tag, @NotNull Bitmap bitmap, @Nullable Range2d range2d) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        validateRestriction(tag, bitmap.getWidth(), bitmap.getHeight(), range2d);
    }

    public static /* synthetic */ void validateRestriction$default(String str, Bitmap bitmap, Range2d range2d, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            range2d = null;
        }
        validateRestriction(str, bitmap, range2d);
    }

    public static final int vectorSize(@NotNull Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Bitmap.Config config = bitmap.getConfig();
        if (config != null) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[config.ordinal()];
            if (i2 == 1) {
                return 4;
            }
            if (i2 == 2) {
                return 1;
            }
        }
        throw new IllegalArgumentException("RenderScript Toolkit. Only ARGB_8888 and ALPHA_8 Bitmap are supported.");
    }

    public static final void validateRestriction(@NotNull String tag, int i2, int i3, @Nullable Range2d range2d) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        if (range2d == null) {
            return;
        }
        if (!(range2d.getStartX() < i2 && range2d.getEndX() <= i2)) {
            throw new IllegalArgumentException(("RenderScript Toolkit " + tag + ". sizeX should be greater than restriction.startX and greater or equal to restriction.endX. " + i2 + ", " + range2d.getStartX() + ", and " + range2d.getEndX() + " were provided respectively.").toString());
        }
        if (!(range2d.getStartY() < i3 && range2d.getEndY() <= i3)) {
            throw new IllegalArgumentException(("RenderScript Toolkit " + tag + ". sizeY should be greater than restriction.startY and greater or equal to restriction.endY. " + i3 + ", " + range2d.getStartY() + ", and " + range2d.getEndY() + " were provided respectively.").toString());
        }
        if (!(range2d.getStartX() < range2d.getEndX())) {
            throw new IllegalArgumentException(("RenderScript Toolkit " + tag + ". Restriction startX should be less than endX. " + range2d.getStartX() + " and " + range2d.getEndX() + " were provided respectively.").toString());
        }
        if (range2d.getStartY() < range2d.getEndY()) {
            return;
        }
        throw new IllegalArgumentException(("RenderScript Toolkit " + tag + ". Restriction startY should be less than endY. " + range2d.getStartY() + " and " + range2d.getEndY() + " were provided respectively.").toString());
    }

    public static /* synthetic */ void validateRestriction$default(String str, int i2, int i3, Range2d range2d, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            range2d = null;
        }
        validateRestriction(str, i2, i3, range2d);
    }
}
