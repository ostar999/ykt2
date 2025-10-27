package com.easefun.polyv.livecommon.ui.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class PLVCoverDrawable extends Drawable {
    private Drawable drawable;
    private Paint paint;
    private Path path;

    public PLVCoverDrawable(@NonNull Drawable drawable) {
        this.path = new Path();
        this.drawable = drawable;
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(-1);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        this.drawable.setBounds(getBounds());
        Path path = this.path;
        if (path == null || path.isEmpty()) {
            this.drawable.draw(canvas);
            return;
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getBounds().width(), getBounds().height(), this.paint, 31);
        this.drawable.draw(canvas);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPath(this.path, this.paint);
        this.paint.setXfermode(null);
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.drawable.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.drawable.setAlpha(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.drawable.setColorFilter(colorFilter);
    }

    public PLVCoverDrawable(@NonNull Drawable drawable, int x2, int y2, int radius) {
        this(drawable);
        this.path.addCircle(x2, y2, radius, Path.Direction.CW);
    }

    public PLVCoverDrawable(@NonNull Drawable drawable, int left, int top2, int right, int bottom, int rx, int ry) {
        this(drawable);
        this.path.addRoundRect(new RectF(left, top2, right, bottom), rx, ry, Path.Direction.CW);
    }
}
