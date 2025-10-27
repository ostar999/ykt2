package com.scwang.smart.refresh.classics;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.scwang.smart.drawable.PaintDrawable;

/* loaded from: classes6.dex */
public class ArrowDrawable extends PaintDrawable {
    private int mWidth = 0;
    private int mHeight = 0;
    private final Path mPath = new Path();

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        if (this.mWidth != iWidth || this.mHeight != iHeight) {
            this.mPath.reset();
            float f2 = (iWidth * 30) / 225;
            float f3 = f2 * 0.70710677f;
            float f4 = f2 / 0.70710677f;
            float f5 = iWidth;
            float f6 = f5 / 2.0f;
            float f7 = iHeight;
            this.mPath.moveTo(f6, f7);
            float f8 = f7 / 2.0f;
            this.mPath.lineTo(0.0f, f8);
            float f9 = f8 - f3;
            this.mPath.lineTo(f3, f9);
            float f10 = f2 / 2.0f;
            float f11 = f6 - f10;
            float f12 = (f7 - f4) - f10;
            this.mPath.lineTo(f11, f12);
            this.mPath.lineTo(f11, 0.0f);
            float f13 = f6 + f10;
            this.mPath.lineTo(f13, 0.0f);
            this.mPath.lineTo(f13, f12);
            this.mPath.lineTo(f5 - f3, f9);
            this.mPath.lineTo(f5, f8);
            this.mPath.close();
            this.mWidth = iWidth;
            this.mHeight = iHeight;
        }
        canvas.drawPath(this.mPath, this.mPaint);
    }
}
