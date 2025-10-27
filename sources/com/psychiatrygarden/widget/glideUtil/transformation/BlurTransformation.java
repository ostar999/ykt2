package com.psychiatrygarden.widget.glideUtil.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;
import com.psychiatrygarden.widget.glideUtil.util.BlurUtils;
import com.yikaobang.yixue.R;
import java.security.MessageDigest;

/* loaded from: classes6.dex */
public class BlurTransformation extends BitmapTransformation {
    private static final int DEFAULT_SAMPLING = 1;
    private static final int MAX_RADIUS = 25;
    private final String ID;
    private Context context;
    private int radius;
    private int sampling;

    public BlurTransformation(Context context) {
        this(context, 25, 1);
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof BlurTransformation)) {
            return false;
        }
        BlurTransformation blurTransformation = (BlurTransformation) obj;
        return this.radius == blurTransformation.radius && this.sampling == blurTransformation.sampling;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode(this.ID.hashCode(), Util.hashCode(this.radius, Util.hashCode(this.sampling)));
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        int i2 = this.sampling;
        Bitmap bitmap = pool.get(width / i2, height / i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int i3 = this.sampling;
        canvas.scale(1.0f / i3, 1.0f / i3);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(toTransform, 0.0f, 0.0f, paint);
        canvas.drawColor(this.context.getResources().getColor(R.color.transimg));
        return BlurUtils.rsBlur(this.context, bitmap, this.radius);
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((this.ID + (this.radius * 10) + this.sampling).getBytes(Key.CHARSET));
    }

    public BlurTransformation(Context context, int radius) {
        this(context, radius, 1);
    }

    public BlurTransformation(Context context, int radius, int sampling) {
        this.ID = getClass().getName();
        this.context = context;
        this.radius = radius > 25 ? 25 : radius;
        this.sampling = sampling > 25 ? 25 : sampling;
    }
}
