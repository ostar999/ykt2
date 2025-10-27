package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import java.util.HashMap;
import java.util.Map;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.SpecialDanmaku;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;

/* loaded from: classes8.dex */
public class SimpleTextCacheStuffer extends BaseCacheStuffer {
    private static final Map<Float, Float> sTextHeightCache = new HashMap();

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void clearCaches() {
        sTextHeightCache.clear();
    }

    public void drawBackground(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3) {
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f2, float f3, boolean z2, AndroidDisplayer.DisplayerConfig displayerConfig) {
        float f4;
        float f5;
        int i2;
        boolean z3;
        float f6;
        float f7;
        float f8;
        float f9;
        int i3 = baseDanmaku.padding;
        float f10 = f2 + i3;
        float f11 = f3 + i3;
        if (baseDanmaku.borderColor != 0) {
            f10 += 4.0f;
            f11 += 4.0f;
        }
        float f12 = f11;
        float f13 = f10;
        displayerConfig.definePaintParams(z2);
        TextPaint paint = displayerConfig.getPaint(baseDanmaku, z2);
        drawBackground(baseDanmaku, canvas, f2, f3);
        String[] strArr = baseDanmaku.lines;
        boolean z4 = true;
        boolean z5 = false;
        if (strArr == null) {
            if (displayerConfig.hasStroke(baseDanmaku)) {
                displayerConfig.applyPaintConfig(baseDanmaku, paint, true);
                float fAscent = f12 - paint.ascent();
                if (displayerConfig.HAS_PROJECTION) {
                    float f14 = displayerConfig.sProjectionOffsetX + f13;
                    f4 = fAscent + displayerConfig.sProjectionOffsetY;
                    f5 = f14;
                } else {
                    f4 = fAscent;
                    f5 = f13;
                }
                drawStroke(baseDanmaku, null, canvas, f5, f4, paint);
            }
            displayerConfig.applyPaintConfig(baseDanmaku, paint, false);
            drawText(baseDanmaku, null, canvas, f13, f12 - paint.ascent(), paint, z2);
        } else if (strArr.length == 1) {
            if (displayerConfig.hasStroke(baseDanmaku)) {
                displayerConfig.applyPaintConfig(baseDanmaku, paint, true);
                float fAscent2 = f12 - paint.ascent();
                if (displayerConfig.HAS_PROJECTION) {
                    float f15 = displayerConfig.sProjectionOffsetX + f13;
                    f8 = fAscent2 + displayerConfig.sProjectionOffsetY;
                    f9 = f15;
                } else {
                    f8 = fAscent2;
                    f9 = f13;
                }
                drawStroke(baseDanmaku, strArr[0], canvas, f9, f8, paint);
            }
            displayerConfig.applyPaintConfig(baseDanmaku, paint, false);
            drawText(baseDanmaku, strArr[0], canvas, f13, f12 - paint.ascent(), paint, z2);
        } else {
            float length = (baseDanmaku.paintHeight - (baseDanmaku.padding * 2)) / strArr.length;
            int i4 = 0;
            while (i4 < strArr.length) {
                String str = strArr[i4];
                if (str == null || str.length() == 0) {
                    i2 = i4;
                    z3 = z5;
                } else {
                    if (displayerConfig.hasStroke(baseDanmaku)) {
                        displayerConfig.applyPaintConfig(baseDanmaku, paint, z4);
                        float fAscent3 = ((i4 * length) + f12) - paint.ascent();
                        if (displayerConfig.HAS_PROJECTION) {
                            float f16 = displayerConfig.sProjectionOffsetX + f13;
                            f6 = fAscent3 + displayerConfig.sProjectionOffsetY;
                            f7 = f16;
                        } else {
                            f6 = fAscent3;
                            f7 = f13;
                        }
                        i2 = i4;
                        drawStroke(baseDanmaku, strArr[i4], canvas, f7, f6, paint);
                    } else {
                        i2 = i4;
                    }
                    displayerConfig.applyPaintConfig(baseDanmaku, paint, z5);
                    z3 = z5;
                    drawText(baseDanmaku, strArr[i2], canvas, f13, ((i2 * length) + f12) - paint.ascent(), paint, z2);
                }
                i4 = i2 + 1;
                z5 = z3;
                z4 = true;
            }
        }
        if (baseDanmaku.underlineColor != 0) {
            Paint underlinePaint = displayerConfig.getUnderlinePaint(baseDanmaku);
            float f17 = (f3 + baseDanmaku.paintHeight) - displayerConfig.UNDERLINE_HEIGHT;
            canvas.drawLine(f2, f17, f2 + baseDanmaku.paintWidth, f17, underlinePaint);
        }
        if (baseDanmaku.borderColor != 0) {
            canvas.drawRect(f2, f3, f2 + baseDanmaku.paintWidth, f3 + baseDanmaku.paintHeight, displayerConfig.getBorderPaint(baseDanmaku));
        }
    }

    public void drawStroke(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f2, float f3, Paint paint) {
        if (str != null) {
            canvas.drawText(str, f2, f3, paint);
        } else {
            canvas.drawText(baseDanmaku.text.toString(), f2, f3, paint);
        }
    }

    public void drawText(BaseDanmaku baseDanmaku, String str, Canvas canvas, float f2, float f3, TextPaint textPaint, boolean z2) {
        if (z2 && (baseDanmaku instanceof SpecialDanmaku)) {
            textPaint.setAlpha(255);
        }
        if (str != null) {
            canvas.drawText(str, f2, f3, textPaint);
        } else {
            canvas.drawText(baseDanmaku.text.toString(), f2, f3, textPaint);
        }
    }

    public Float getCacheHeight(BaseDanmaku baseDanmaku, Paint paint) {
        Float fValueOf = Float.valueOf(paint.getTextSize());
        Map<Float, Float> map = sTextHeightCache;
        Float f2 = map.get(fValueOf);
        if (f2 != null) {
            return f2;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Float fValueOf2 = Float.valueOf((fontMetrics.descent - fontMetrics.ascent) + fontMetrics.leading);
        map.put(fValueOf, fValueOf2);
        return fValueOf2;
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z2) {
        float fMax = 0.0f;
        Float fValueOf = Float.valueOf(0.0f);
        if (baseDanmaku.lines == null) {
            CharSequence charSequence = baseDanmaku.text;
            if (charSequence != null) {
                fMax = textPaint.measureText(charSequence.toString());
                fValueOf = getCacheHeight(baseDanmaku, textPaint);
            }
            baseDanmaku.paintWidth = fMax;
            baseDanmaku.paintHeight = fValueOf.floatValue();
            return;
        }
        Float cacheHeight = getCacheHeight(baseDanmaku, textPaint);
        for (String str : baseDanmaku.lines) {
            if (str.length() > 0) {
                fMax = Math.max(textPaint.measureText(str), fMax);
            }
        }
        baseDanmaku.paintWidth = fMax;
        baseDanmaku.paintHeight = baseDanmaku.lines.length * cacheHeight.floatValue();
    }
}
