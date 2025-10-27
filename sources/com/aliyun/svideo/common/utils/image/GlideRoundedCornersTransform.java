package com.aliyun.svideo.common.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import com.aliyun.svideo.common.utils.DensityUtils;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import java.security.MessageDigest;

/* loaded from: classes2.dex */
public class GlideRoundedCornersTransform extends CenterCrop {
    private static final String ID = "com.aliyun.svideo.commonGlideRoundedCornersTransform.1";
    private static final byte[] ID_BYTES = ID.getBytes(Key.CHARSET);
    private static final int VERSION = 1;
    private CornerType mCornerType;
    private float mRadius;

    /* renamed from: com.aliyun.svideo.common.utils.image.GlideRoundedCornersTransform$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType;

        static {
            int[] iArr = new int[CornerType.values().length];
            $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType = iArr;
            try {
                iArr[CornerType.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP_LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP_RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.BOTTOM_RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.BOTTOM_LEFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.BOTTOM.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.LEFT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.RIGHT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP_LEFT_BOTTOM_RIGHT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP_RIGHT_BOTTOM_LEFT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[CornerType.TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public enum CornerType {
        ALL,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        TOP_LEFT_BOTTOM_RIGHT,
        TOP_RIGHT_BOTTOM_LEFT,
        TOP_LEFT_TOP_RIGHT_BOTTOM_RIGHT,
        TOP_RIGHT_BOTTOM_RIGHT_BOTTOM_LEFT
    }

    public GlideRoundedCornersTransform(Context context, float f2, CornerType cornerType) {
        this.mRadius = DensityUtils.dip2px(context, f2);
        this.mCornerType = cornerType;
    }

    private void drawPath(float[] fArr, Canvas canvas, Paint paint, Path path, int i2, int i3) {
        path.addRoundRect(new RectF(0.0f, 0.0f, i2, i3), fArr, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    private void drawRoundRect(Canvas canvas, Paint paint, Path path, int i2, int i3) {
        switch (AnonymousClass1.$SwitchMap$com$aliyun$svideo$common$utils$image$GlideRoundedCornersTransform$CornerType[this.mCornerType.ordinal()]) {
            case 1:
                float f2 = this.mRadius;
                drawPath(new float[]{f2, f2, f2, f2, f2, f2, f2, f2}, canvas, paint, path, i2, i3);
                return;
            case 2:
                float f3 = this.mRadius;
                drawPath(new float[]{f3, f3, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 3:
                float f4 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f4, f4, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 4:
                float f5 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f5, f5, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 5:
                float f6 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, f6, f6}, canvas, paint, path, i2, i3);
                return;
            case 6:
                float f7 = this.mRadius;
                drawPath(new float[]{f7, f7, f7, f7, 0.0f, 0.0f, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 7:
                float f8 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, 0.0f, 0.0f, f8, f8, f8, f8}, canvas, paint, path, i2, i3);
                return;
            case 8:
                float f9 = this.mRadius;
                drawPath(new float[]{f9, f9, 0.0f, 0.0f, 0.0f, 0.0f, f9, f9}, canvas, paint, path, i2, i3);
                return;
            case 9:
                float f10 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f10, f10, f10, f10, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 10:
                float f11 = this.mRadius;
                drawPath(new float[]{f11, f11, 0.0f, 0.0f, f11, f11, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 11:
                float f12 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f12, f12, 0.0f, 0.0f, f12, f12}, canvas, paint, path, i2, i3);
                return;
            case 12:
                float f13 = this.mRadius;
                drawPath(new float[]{f13, f13, f13, f13, f13, f13, 0.0f, 0.0f}, canvas, paint, path, i2, i3);
                return;
            case 13:
                float f14 = this.mRadius;
                drawPath(new float[]{0.0f, 0.0f, f14, f14, f14, f14, f14, f14}, canvas, paint, path, i2, i3);
                return;
            default:
                throw new RuntimeException("RoundedCorners type not belong to CornerType");
        }
    }

    private Bitmap roundCrop(BitmapPool bitmapPool, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap bitmapCreateBitmap = bitmapPool.get(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmapCreateBitmap == null) {
            bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        paint.setAntiAlias(true);
        drawRoundRect(canvas, paint, new Path(), width, height);
        return bitmapCreateBitmap;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return obj instanceof GlideRoundedCornersTransform;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.Key
    public int hashCode() {
        return -833112035;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i2, int i3) {
        return roundCrop(bitmapPool, super.transform(bitmapPool, bitmap, i2, i3));
    }

    @Override // com.bumptech.glide.load.resource.bitmap.CenterCrop, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
