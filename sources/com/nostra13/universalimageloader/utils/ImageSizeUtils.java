package com.nostra13.universalimageloader.utils;

import android.opengl.GLES10;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public final class ImageSizeUtils {
    private static final int DEFAULT_MAX_BITMAP_DIMENSION = 2048;
    private static ImageSize maxBitmapSize;

    /* renamed from: com.nostra13.universalimageloader.utils.ImageSizeUtils$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$nostra13$universalimageloader$core$assist$ViewScaleType;

        static {
            int[] iArr = new int[ViewScaleType.values().length];
            $SwitchMap$com$nostra13$universalimageloader$core$assist$ViewScaleType = iArr;
            try {
                iArr[ViewScaleType.FIT_INSIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$nostra13$universalimageloader$core$assist$ViewScaleType[ViewScaleType.CROP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static {
        int[] iArr = new int[1];
        GLES10.glGetIntegerv(R2.attr.stroke_Width, iArr, 0);
        int iMax = Math.max(iArr[0], 2048);
        maxBitmapSize = new ImageSize(iMax, iMax);
    }

    private ImageSizeUtils() {
    }

    public static int computeImageSampleSize(ImageSize imageSize, ImageSize imageSize2, ViewScaleType viewScaleType, boolean z2) {
        int iMax;
        int width = imageSize.getWidth();
        int height = imageSize.getHeight();
        int width2 = imageSize2.getWidth();
        int height2 = imageSize2.getHeight();
        int i2 = AnonymousClass1.$SwitchMap$com$nostra13$universalimageloader$core$assist$ViewScaleType[viewScaleType.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                iMax = 1;
            } else if (z2) {
                int i3 = width / 2;
                int i4 = height / 2;
                iMax = 1;
                while (i3 / iMax > width2 && i4 / iMax > height2) {
                    iMax *= 2;
                }
            } else {
                iMax = Math.min(width / width2, height / height2);
            }
        } else if (z2) {
            int i5 = width / 2;
            int i6 = height / 2;
            iMax = 1;
            while (true) {
                if (i5 / iMax <= width2 && i6 / iMax <= height2) {
                    break;
                }
                iMax *= 2;
            }
        } else {
            iMax = Math.max(width / width2, height / height2);
        }
        return considerMaxTextureSize(width, height, iMax >= 1 ? iMax : 1, z2);
    }

    public static float computeImageScale(ImageSize imageSize, ImageSize imageSize2, ViewScaleType viewScaleType, boolean z2) {
        int width = imageSize.getWidth();
        int height = imageSize.getHeight();
        int width2 = imageSize2.getWidth();
        int height2 = imageSize2.getHeight();
        float f2 = width;
        float f3 = f2 / width2;
        float f4 = height;
        float f5 = f4 / height2;
        if ((viewScaleType != ViewScaleType.FIT_INSIDE || f3 < f5) && (viewScaleType != ViewScaleType.CROP || f3 >= f5)) {
            width2 = (int) (f2 / f5);
        } else {
            height2 = (int) (f4 / f3);
        }
        if ((z2 || width2 >= width || height2 >= height) && (!z2 || width2 == width || height2 == height)) {
            return 1.0f;
        }
        return width2 / f2;
    }

    public static int computeMinImageSampleSize(ImageSize imageSize) {
        int width = imageSize.getWidth();
        int height = imageSize.getHeight();
        return Math.max((int) Math.ceil(width / maxBitmapSize.getWidth()), (int) Math.ceil(height / maxBitmapSize.getHeight()));
    }

    private static int considerMaxTextureSize(int i2, int i3, int i4, boolean z2) {
        int width = maxBitmapSize.getWidth();
        int height = maxBitmapSize.getHeight();
        while (true) {
            if (i2 / i4 <= width && i3 / i4 <= height) {
                return i4;
            }
            i4 = z2 ? i4 * 2 : i4 + 1;
        }
    }

    public static ImageSize defineTargetSizeForView(ImageAware imageAware, ImageSize imageSize) {
        int width = imageAware.getWidth();
        if (width <= 0) {
            width = imageSize.getWidth();
        }
        int height = imageAware.getHeight();
        if (height <= 0) {
            height = imageSize.getHeight();
        }
        return new ImageSize(width, height);
    }
}
