package master.flame.danmaku.danmaku.model.android;

import android.annotation.SuppressLint;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.AlphaValue;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

/* loaded from: classes8.dex */
public class AndroidDisplayer extends AbsDisplayer<Canvas, Typeface> {
    public Canvas canvas;
    private int height;
    private float locationZ;
    private int width;
    private Camera camera = new Camera();
    private Matrix matrix = new Matrix();
    private final DisplayerConfig mDisplayConfig = new DisplayerConfig();
    private BaseCacheStuffer sStuffer = new SimpleTextCacheStuffer();
    private float density = 1.0f;
    private int densityDpi = 160;
    private float scaledDensity = 1.0f;
    private int mSlopPixel = 0;
    private boolean mIsHardwareAccelerated = true;
    private int mMaximumBitmapWidth = 2048;
    private int mMaximumBitmapHeight = 2048;

    public static class DisplayerConfig {
        public static final int BORDER_WIDTH = 4;
        private Paint ALPHA_PAINT;
        private Paint BORDER_PAINT;
        public final TextPaint PAINT;
        public final TextPaint PAINT_DUPLICATE;
        private Paint UNDERLINE_PAINT;
        private boolean isTranslucent;
        private float sLastScaleTextSize;
        private final Map<Float, Float> sCachedScaleSize = new HashMap(10);
        public int UNDERLINE_HEIGHT = 4;
        private float SHADOW_RADIUS = 4.0f;
        private float STROKE_WIDTH = 3.5f;
        public float sProjectionOffsetX = 1.0f;
        public float sProjectionOffsetY = 1.0f;
        private int sProjectionAlpha = 204;
        public boolean CONFIG_HAS_SHADOW = false;
        private boolean HAS_SHADOW = false;
        public boolean CONFIG_HAS_STROKE = true;
        private boolean HAS_STROKE = true;
        public boolean CONFIG_HAS_PROJECTION = false;
        public boolean HAS_PROJECTION = false;
        public boolean CONFIG_ANTI_ALIAS = true;
        private boolean ANTI_ALIAS = true;
        private int transparency = AlphaValue.MAX;
        private float scaleTextSize = 1.0f;
        private boolean isTextScaled = false;
        private int margin = 0;
        private int allMarginTop = 0;

        public DisplayerConfig() {
            TextPaint textPaint = new TextPaint();
            this.PAINT = textPaint;
            textPaint.setStrokeWidth(this.STROKE_WIDTH);
            this.PAINT_DUPLICATE = new TextPaint(textPaint);
            this.ALPHA_PAINT = new Paint();
            Paint paint = new Paint();
            this.UNDERLINE_PAINT = paint;
            paint.setStrokeWidth(this.UNDERLINE_HEIGHT);
            this.UNDERLINE_PAINT.setStyle(Paint.Style.STROKE);
            Paint paint2 = new Paint();
            this.BORDER_PAINT = paint2;
            paint2.setStyle(Paint.Style.STROKE);
            this.BORDER_PAINT.setStrokeWidth(4.0f);
        }

        private void applyTextScaleConfig(BaseDanmaku baseDanmaku, Paint paint) {
            if (this.isTextScaled) {
                Float fValueOf = this.sCachedScaleSize.get(Float.valueOf(baseDanmaku.textSize));
                if (fValueOf == null || this.sLastScaleTextSize != this.scaleTextSize) {
                    float f2 = this.scaleTextSize;
                    this.sLastScaleTextSize = f2;
                    fValueOf = Float.valueOf(baseDanmaku.textSize * f2);
                    this.sCachedScaleSize.put(Float.valueOf(baseDanmaku.textSize), fValueOf);
                }
                paint.setTextSize(fValueOf.floatValue());
            }
        }

        public void applyPaintConfig(BaseDanmaku baseDanmaku, Paint paint, boolean z2) {
            if (this.isTranslucent) {
                if (z2) {
                    paint.setStyle(this.HAS_PROJECTION ? Paint.Style.FILL : Paint.Style.FILL_AND_STROKE);
                    paint.setColor(baseDanmaku.textShadowColor & 16777215);
                    paint.setAlpha(this.HAS_PROJECTION ? (int) (this.sProjectionAlpha * (this.transparency / AlphaValue.MAX)) : this.transparency);
                } else {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(baseDanmaku.textColor & 16777215);
                    paint.setAlpha(this.transparency);
                }
            } else if (z2) {
                paint.setStyle(this.HAS_PROJECTION ? Paint.Style.FILL : Paint.Style.FILL_AND_STROKE);
                paint.setColor(baseDanmaku.textShadowColor & 16777215);
                paint.setAlpha(this.HAS_PROJECTION ? this.sProjectionAlpha : AlphaValue.MAX);
            } else {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(baseDanmaku.textColor & 16777215);
                paint.setAlpha(AlphaValue.MAX);
            }
            if (baseDanmaku.getType() == 7) {
                paint.setAlpha(baseDanmaku.getAlpha());
            }
        }

        public void clearTextHeightCache() {
            this.sCachedScaleSize.clear();
        }

        public void definePaintParams(boolean z2) {
            this.HAS_STROKE = this.CONFIG_HAS_STROKE;
            this.HAS_SHADOW = this.CONFIG_HAS_SHADOW;
            this.HAS_PROJECTION = this.CONFIG_HAS_PROJECTION;
            this.ANTI_ALIAS = this.CONFIG_ANTI_ALIAS;
        }

        public Paint getBorderPaint(BaseDanmaku baseDanmaku) {
            this.BORDER_PAINT.setColor(baseDanmaku.borderColor);
            return this.BORDER_PAINT;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.text.TextPaint getPaint(master.flame.danmaku.danmaku.model.BaseDanmaku r4, boolean r5) {
            /*
                r3 = this;
                if (r5 == 0) goto L5
                android.text.TextPaint r5 = r3.PAINT
                goto Lc
            L5:
                android.text.TextPaint r5 = r3.PAINT_DUPLICATE
                android.text.TextPaint r0 = r3.PAINT
                r5.set(r0)
            Lc:
                float r0 = r4.textSize
                r5.setTextSize(r0)
                r3.applyTextScaleConfig(r4, r5)
                boolean r0 = r3.HAS_SHADOW
                if (r0 == 0) goto L28
                float r0 = r3.SHADOW_RADIUS
                r1 = 0
                int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r2 <= 0) goto L28
                int r4 = r4.textShadowColor
                if (r4 != 0) goto L24
                goto L28
            L24:
                r5.setShadowLayer(r0, r1, r1, r4)
                goto L2b
            L28:
                r5.clearShadowLayer()
            L2b:
                boolean r4 = r3.ANTI_ALIAS
                r5.setAntiAlias(r4)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: master.flame.danmaku.danmaku.model.android.AndroidDisplayer.DisplayerConfig.getPaint(master.flame.danmaku.danmaku.model.BaseDanmaku, boolean):android.text.TextPaint");
        }

        public float getStrokeWidth() {
            boolean z2 = this.HAS_SHADOW;
            if (z2 && this.HAS_STROKE) {
                return Math.max(this.SHADOW_RADIUS, this.STROKE_WIDTH);
            }
            if (z2) {
                return this.SHADOW_RADIUS;
            }
            if (this.HAS_STROKE) {
                return this.STROKE_WIDTH;
            }
            return 0.0f;
        }

        public Paint getUnderlinePaint(BaseDanmaku baseDanmaku) {
            this.UNDERLINE_PAINT.setColor(baseDanmaku.underlineColor);
            return this.UNDERLINE_PAINT;
        }

        public boolean hasStroke(BaseDanmaku baseDanmaku) {
            return (this.HAS_STROKE || this.HAS_PROJECTION) && this.STROKE_WIDTH > 0.0f && baseDanmaku.textShadowColor != 0;
        }

        public void setFakeBoldText(boolean z2) {
            this.PAINT.setFakeBoldText(z2);
        }

        public void setProjectionConfig(float f2, float f3, int i2) {
            if (this.sProjectionOffsetX == f2 && this.sProjectionOffsetY == f3 && this.sProjectionAlpha == i2) {
                return;
            }
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            this.sProjectionOffsetX = f2;
            if (f3 <= 1.0f) {
                f3 = 1.0f;
            }
            this.sProjectionOffsetY = f3;
            if (i2 < 0) {
                i2 = 0;
            } else if (i2 > 255) {
                i2 = 255;
            }
            this.sProjectionAlpha = i2;
        }

        public void setScaleTextSizeFactor(float f2) {
            this.isTextScaled = f2 != 1.0f;
            this.scaleTextSize = f2;
        }

        public void setShadowRadius(float f2) {
            this.SHADOW_RADIUS = f2;
        }

        public void setStrokeWidth(float f2) {
            this.PAINT.setStrokeWidth(f2);
            this.STROKE_WIDTH = f2;
        }

        public void setTransparency(int i2) {
            this.isTranslucent = i2 != AlphaValue.MAX;
            this.transparency = i2;
        }

        public void setTypeface(Typeface typeface) {
            this.PAINT.setTypeface(typeface);
        }
    }

    private void calcPaintWH(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z2) {
        this.sStuffer.measure(baseDanmaku, textPaint, z2);
        setDanmakuPaintWidthAndHeight(baseDanmaku, baseDanmaku.paintWidth, baseDanmaku.paintHeight);
    }

    @SuppressLint({"NewApi"})
    private static final int getMaximumBitmapHeight(Canvas canvas) {
        return canvas.getMaximumBitmapHeight();
    }

    @SuppressLint({"NewApi"})
    private static final int getMaximumBitmapWidth(Canvas canvas) {
        return canvas.getMaximumBitmapWidth();
    }

    private synchronized TextPaint getPaint(BaseDanmaku baseDanmaku, boolean z2) {
        return this.mDisplayConfig.getPaint(baseDanmaku, z2);
    }

    private void resetPaintAlpha(Paint paint) {
        int alpha = paint.getAlpha();
        int i2 = AlphaValue.MAX;
        if (alpha != i2) {
            paint.setAlpha(i2);
        }
    }

    private void restoreCanvas(Canvas canvas) {
        canvas.restore();
    }

    private int saveCanvas(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3) {
        this.camera.save();
        float f4 = this.locationZ;
        if (f4 != 0.0f) {
            this.camera.setLocation(0.0f, 0.0f, f4);
        }
        this.camera.rotateY(-baseDanmaku.rotationY);
        this.camera.rotateZ(-baseDanmaku.rotationZ);
        this.camera.getMatrix(this.matrix);
        this.matrix.preTranslate(-f2, -f3);
        this.matrix.postTranslate(f2, f3);
        this.camera.restore();
        int iSave = canvas.save();
        canvas.concat(this.matrix);
        return iSave;
    }

    private void setDanmakuPaintWidthAndHeight(BaseDanmaku baseDanmaku, float f2, float f3) {
        int i2 = baseDanmaku.padding;
        float f4 = f2 + (i2 * 2);
        float f5 = f3 + (i2 * 2);
        if (baseDanmaku.borderColor != 0) {
            float f6 = 8;
            f4 += f6;
            f5 += f6;
        }
        baseDanmaku.paintWidth = f4 + getStrokeWidth();
        baseDanmaku.paintHeight = f5;
    }

    private void update(Canvas canvas) {
        this.canvas = canvas;
        if (canvas != null) {
            this.width = canvas.getWidth();
            this.height = canvas.getHeight();
            if (this.mIsHardwareAccelerated) {
                this.mMaximumBitmapWidth = getMaximumBitmapWidth(canvas);
                this.mMaximumBitmapHeight = getMaximumBitmapHeight(canvas);
            }
        }
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void clearTextHeightCache() {
        this.sStuffer.clearCaches();
        this.mDisplayConfig.clearTextHeightCache();
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int draw(BaseDanmaku baseDanmaku) {
        boolean z2;
        boolean z3;
        float top2 = baseDanmaku.getTop();
        float left = baseDanmaku.getLeft();
        if (this.canvas == null) {
            return 0;
        }
        int i2 = 1;
        Paint paint = null;
        if (baseDanmaku.getType() != 7) {
            z2 = false;
        } else {
            if (baseDanmaku.getAlpha() == AlphaValue.TRANSPARENT) {
                return 0;
            }
            if (baseDanmaku.rotationZ == 0.0f && baseDanmaku.rotationY == 0.0f) {
                z3 = false;
            } else {
                saveCanvas(baseDanmaku, this.canvas, left, top2);
                z3 = true;
            }
            if (baseDanmaku.getAlpha() != AlphaValue.MAX) {
                paint = this.mDisplayConfig.ALPHA_PAINT;
                paint.setAlpha(baseDanmaku.getAlpha());
            }
            z2 = z3;
        }
        Paint paint2 = paint;
        if (paint2 != null && paint2.getAlpha() == AlphaValue.TRANSPARENT) {
            return 0;
        }
        if (!this.sStuffer.drawCache(baseDanmaku, this.canvas, left, top2, paint2, this.mDisplayConfig.PAINT)) {
            if (paint2 != null) {
                this.mDisplayConfig.PAINT.setAlpha(paint2.getAlpha());
                this.mDisplayConfig.PAINT_DUPLICATE.setAlpha(paint2.getAlpha());
            } else {
                resetPaintAlpha(this.mDisplayConfig.PAINT);
            }
            drawDanmaku(baseDanmaku, this.canvas, left, top2, false);
            i2 = 2;
        }
        if (z2) {
            restoreCanvas(this.canvas);
        }
        return i2;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getAllMarginTop() {
        return this.mDisplayConfig.allMarginTop;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public BaseCacheStuffer getCacheStuffer() {
        return this.sStuffer;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public float getDensity() {
        return this.density;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getDensityDpi() {
        return this.densityDpi;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getHeight() {
        return this.height;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getMargin() {
        return this.mDisplayConfig.margin;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getMaximumCacheHeight() {
        return this.mMaximumBitmapHeight;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getMaximumCacheWidth() {
        return this.mMaximumBitmapWidth;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public float getScaledDensity() {
        return this.scaledDensity;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getSlopPixel() {
        return this.mSlopPixel;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public float getStrokeWidth() {
        return this.mDisplayConfig.getStrokeWidth();
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public int getWidth() {
        return this.width;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer, master.flame.danmaku.danmaku.model.IDisplayer
    public boolean isHardwareAccelerated() {
        return this.mIsHardwareAccelerated;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void measure(BaseDanmaku baseDanmaku, boolean z2) {
        TextPaint paint = getPaint(baseDanmaku, z2);
        if (this.mDisplayConfig.HAS_STROKE) {
            this.mDisplayConfig.applyPaintConfig(baseDanmaku, paint, true);
        }
        calcPaintWH(baseDanmaku, paint, z2);
        if (this.mDisplayConfig.HAS_STROKE) {
            this.mDisplayConfig.applyPaintConfig(baseDanmaku, paint, false);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void prepare(BaseDanmaku baseDanmaku, boolean z2) {
        BaseCacheStuffer baseCacheStuffer = this.sStuffer;
        if (baseCacheStuffer != null) {
            baseCacheStuffer.prepare(baseDanmaku, z2);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void recycle(BaseDanmaku baseDanmaku) {
        BaseCacheStuffer baseCacheStuffer = this.sStuffer;
        if (baseCacheStuffer != null) {
            baseCacheStuffer.releaseResource(baseDanmaku);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void resetSlopPixel(float f2) {
        float fMax = Math.max(f2, getWidth() / 682.0f) * 25.0f;
        this.mSlopPixel = (int) fMax;
        if (f2 > 1.0f) {
            this.mSlopPixel = (int) (fMax * f2);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setAllMarginTop(int i2) {
        this.mDisplayConfig.allMarginTop = i2;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setCacheStuffer(BaseCacheStuffer baseCacheStuffer) {
        if (baseCacheStuffer != this.sStuffer) {
            this.sStuffer = baseCacheStuffer;
        }
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setDanmakuStyle(int i2, float[] fArr) {
        if (i2 != -1) {
            if (i2 == 0) {
                DisplayerConfig displayerConfig = this.mDisplayConfig;
                displayerConfig.CONFIG_HAS_SHADOW = false;
                displayerConfig.CONFIG_HAS_STROKE = false;
                displayerConfig.CONFIG_HAS_PROJECTION = false;
                return;
            }
            if (i2 == 1) {
                DisplayerConfig displayerConfig2 = this.mDisplayConfig;
                displayerConfig2.CONFIG_HAS_SHADOW = true;
                displayerConfig2.CONFIG_HAS_STROKE = false;
                displayerConfig2.CONFIG_HAS_PROJECTION = false;
                setShadowRadius(fArr[0]);
                return;
            }
            if (i2 != 2) {
                if (i2 != 3) {
                    return;
                }
                DisplayerConfig displayerConfig3 = this.mDisplayConfig;
                displayerConfig3.CONFIG_HAS_SHADOW = false;
                displayerConfig3.CONFIG_HAS_STROKE = false;
                displayerConfig3.CONFIG_HAS_PROJECTION = true;
                setProjectionConfig(fArr[0], fArr[1], (int) fArr[2]);
                return;
            }
        }
        DisplayerConfig displayerConfig4 = this.mDisplayConfig;
        displayerConfig4.CONFIG_HAS_SHADOW = false;
        displayerConfig4.CONFIG_HAS_STROKE = true;
        displayerConfig4.CONFIG_HAS_PROJECTION = false;
        setPaintStorkeWidth(fArr[0]);
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setDensities(float f2, int i2, float f3) {
        this.density = f2;
        this.densityDpi = i2;
        this.scaledDensity = f3;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setFakeBoldText(boolean z2) {
        this.mDisplayConfig.setFakeBoldText(z2);
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setHardwareAccelerated(boolean z2) {
        this.mIsHardwareAccelerated = z2;
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setMargin(int i2) {
        this.mDisplayConfig.margin = i2;
    }

    public void setPaintStorkeWidth(float f2) {
        this.mDisplayConfig.setStrokeWidth(f2);
    }

    public void setProjectionConfig(float f2, float f3, int i2) {
        this.mDisplayConfig.setProjectionConfig(f2, f3, i2);
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setScaleTextSizeFactor(float f2) {
        this.mDisplayConfig.setScaleTextSizeFactor(f2);
    }

    public void setShadowRadius(float f2) {
        this.mDisplayConfig.setShadowRadius(f2);
    }

    @Override // master.flame.danmaku.danmaku.model.IDisplayer
    public void setSize(int i2, int i3) {
        this.width = i2;
        this.height = i3;
        this.locationZ = (float) ((i2 / 2.0f) / Math.tan(0.4799655442984406d));
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setTransparency(int i2) {
        this.mDisplayConfig.setTransparency(i2);
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public synchronized void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3, boolean z2) {
        BaseCacheStuffer baseCacheStuffer = this.sStuffer;
        if (baseCacheStuffer != null) {
            baseCacheStuffer.drawDanmaku(baseDanmaku, canvas, f2, f3, z2, this.mDisplayConfig);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public Canvas getExtraData() {
        return this.canvas;
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setExtraData(Canvas canvas) {
        update(canvas);
    }

    @Override // master.flame.danmaku.danmaku.model.AbsDisplayer
    public void setTypeFace(Typeface typeface) {
        this.mDisplayConfig.setTypeface(typeface);
    }
}
