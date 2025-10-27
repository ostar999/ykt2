package com.psychiatrygarden.widget.glideUtil.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;
import java.security.MessageDigest;

/* loaded from: classes6.dex */
public class CircleTransformation extends BitmapTransformation {
    private final String ID = getClass().getName();

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof BlurTransformation) && this == obj;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode(this.ID.hashCode());
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int iMin = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(toTransform, (toTransform.getWidth() - iMin) / 2, (toTransform.getHeight() - iMin) / 2, iMin, iMin);
        Bitmap bitmap = pool.get(iMin, iMin, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(bitmapCreateBitmap, tileMode, tileMode));
        paint.setAntiAlias(true);
        float f2 = iMin / 2.0f;
        canvas.drawCircle(f2, f2, f2, paint);
        return bitmap;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(this.ID.getBytes(Key.CHARSET));
    }
}
