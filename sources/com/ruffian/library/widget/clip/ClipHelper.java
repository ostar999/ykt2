package com.ruffian.library.widget.clip;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.ruffian.library.widget.clip.ClipPathManager;

/* loaded from: classes6.dex */
public class ClipHelper implements IClip {
    protected PorterDuffXfermode DST_IN_MODE;
    protected PorterDuffXfermode DST_OUT_MODE;
    private final Paint clipPaint;
    private final Path clipPath;
    private ClipPathManager clipPathManager;
    private boolean mClipLayout;
    private View mView;
    private final Path rectView;
    private boolean requestShapeUpdate;

    public ClipHelper() {
        Paint paint = new Paint(1);
        this.clipPaint = paint;
        this.DST_OUT_MODE = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.DST_IN_MODE = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.clipPath = new Path();
        this.rectView = new Path();
        this.clipPathManager = new ClipPathManager();
        this.requestShapeUpdate = true;
        paint.setAntiAlias(true);
        paint.setColor(-16776961);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1.0f);
    }

    private void calculateLayout(int i2, int i3) {
        this.rectView.reset();
        this.rectView.addRect(0.0f, 0.0f, getView().getWidth() * 1.0f, getView().getHeight() * 1.0f, Path.Direction.CW);
        if (i2 > 0 && i3 > 0) {
            this.clipPathManager.setupClipLayout(i2, i3);
            this.clipPath.reset();
            this.clipPath.set(this.clipPathManager.getClipPath());
            if (Build.VERSION.SDK_INT > 27) {
                this.rectView.op(this.clipPath, Path.Op.DIFFERENCE);
            }
            if (ViewCompat.getElevation(getView()) > 0.0f) {
                try {
                    getView().setOutlineProvider(getView().getOutlineProvider());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        getView().postInvalidate();
    }

    public boolean canClip() {
        return getView() != null && (getView() instanceof ViewGroup) && this.mClipLayout;
    }

    @Override // com.ruffian.library.widget.clip.IClip
    public void dispatchDraw(Canvas canvas) {
        if (canClip()) {
            if (this.requestShapeUpdate) {
                calculateLayout(canvas.getWidth(), canvas.getHeight());
                this.requestShapeUpdate = false;
            }
            int i2 = Build.VERSION.SDK_INT;
            if (i2 <= 27) {
                canvas.drawPath(this.clipPath, this.clipPaint);
            } else {
                canvas.drawPath(this.rectView, this.clipPaint);
            }
            if (i2 <= 27) {
                getView().setLayerType(2, null);
            }
        }
    }

    public View getView() {
        return this.mView;
    }

    public void initClip(View view, boolean z2, ClipPathManager.ClipPathCreator clipPathCreator) {
        this.mView = view;
        this.mClipLayout = z2;
        if (canClip()) {
            getView().setDrawingCacheEnabled(true);
            getView().setWillNotDraw(false);
            if (Build.VERSION.SDK_INT <= 27) {
                this.clipPaint.setXfermode(this.DST_IN_MODE);
                getView().setLayerType(1, this.clipPaint);
            } else {
                this.clipPaint.setXfermode(this.DST_OUT_MODE);
                getView().setLayerType(1, null);
            }
            this.clipPathManager.setClipPathCreator(clipPathCreator);
            requestShapeUpdate();
        }
    }

    @Override // com.ruffian.library.widget.clip.IClip
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (canClip() && z2) {
            requestShapeUpdate();
        }
    }

    public void requestShapeUpdate() {
        this.requestShapeUpdate = true;
        getView().postInvalidate();
    }
}
