package com.psychiatrygarden.widget.glideUtil.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
import java.security.MessageDigest;

/* loaded from: classes6.dex */
public class RadiusTransformation extends BitmapTransformation {
    private final String ID = getClass().getName();
    private int radius;

    public RadiusTransformation(Context context, int radius) {
        this.radius = Utils.dp2px(context, radius);
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof RadiusTransformation) && this.radius == ((RadiusTransformation) obj).radius;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode(this.ID.hashCode(), Util.hashCode(this.radius));
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(toTransform, tileMode, tileMode));
        RectF rectF = new RectF(0.0f, 0.0f, width, height);
        int i2 = this.radius;
        canvas.drawRoundRect(rectF, i2, i2, paint);
        return bitmap;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((this.ID + this.radius).getBytes(Key.CHARSET));
    }
}
