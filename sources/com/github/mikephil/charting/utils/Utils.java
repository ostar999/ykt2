package com.github.mikephil.charting.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import cn.hutool.core.text.CharPool;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class Utils {
    public static final double DEG2RAD = 0.017453292519943295d;
    public static final float FDEG2RAD = 0.017453292f;
    private static int mMaximumFlingVelocity = 8000;
    private static DisplayMetrics mMetrics = null;
    private static int mMinimumFlingVelocity = 50;
    public static final double DOUBLE_EPSILON = Double.longBitsToDouble(1);
    public static final float FLOAT_EPSILON = Float.intBitsToFloat(1);
    private static Rect mCalcTextHeightRect = new Rect();
    private static Paint.FontMetrics mFontMetrics = new Paint.FontMetrics();
    private static Rect mCalcTextSizeRect = new Rect();
    private static final int[] POW_10 = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    private static ValueFormatter mDefaultValueFormatter = generateDefaultValueFormatter();
    private static Rect mDrawableBoundsCache = new Rect();
    private static Rect mDrawTextRectBuffer = new Rect();
    private static Paint.FontMetrics mFontMetricsBuffer = new Paint.FontMetrics();

    public static int calcTextHeight(Paint paint, String str) {
        Rect rect = mCalcTextHeightRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    public static FSize calcTextSize(Paint paint, String str) {
        FSize fSize = FSize.getInstance(0.0f, 0.0f);
        calcTextSize(paint, str, fSize);
        return fSize;
    }

    public static int calcTextWidth(Paint paint, String str) {
        return (int) paint.measureText(str);
    }

    public static float convertDpToPixel(float f2) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) {
            return f2 * displayMetrics.density;
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertDpToPixel(...). Otherwise conversion does not take place.");
        return f2;
    }

    public static int[] convertIntegers(List<Integer> list) {
        int[] iArr = new int[list.size()];
        copyIntegers(list, iArr);
        return iArr;
    }

    public static float convertPixelsToDp(float f2) {
        DisplayMetrics displayMetrics = mMetrics;
        if (displayMetrics != null) {
            return f2 / displayMetrics.density;
        }
        Log.e("MPChartLib-Utils", "Utils NOT INITIALIZED. You need to call Utils.init(...) at least once before calling Utils.convertPixelsToDp(...). Otherwise conversion does not take place.");
        return f2;
    }

    public static String[] convertStrings(List<String> list) {
        int size = list.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            strArr[i2] = list.get(i2);
        }
        return strArr;
    }

    public static void copyIntegers(List<Integer> list, int[] iArr) {
        int length = iArr.length < list.size() ? iArr.length : list.size();
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = list.get(i2).intValue();
        }
    }

    public static void copyStrings(List<String> list, String[] strArr) {
        int length = strArr.length < list.size() ? strArr.length : list.size();
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = list.get(i2);
        }
    }

    public static void drawImage(Canvas canvas, Drawable drawable, int i2, int i3, int i4, int i5) {
        MPPointF mPPointF = MPPointF.getInstance();
        mPPointF.f6566x = i2 - (i4 / 2);
        mPPointF.f6567y = i3 - (i5 / 2);
        drawable.copyBounds(mDrawableBoundsCache);
        Rect rect = mDrawableBoundsCache;
        int i6 = rect.left;
        int i7 = rect.top;
        drawable.setBounds(i6, i7, i6 + i4, i4 + i7);
        int iSave = canvas.save();
        canvas.translate(mPPointF.f6566x, mPPointF.f6567y);
        drawable.draw(canvas);
        canvas.restoreToCount(iSave);
    }

    public static void drawMultilineText(Canvas canvas, StaticLayout staticLayout, float f2, float f3, TextPaint textPaint, MPPointF mPPointF, float f4) {
        float fontMetrics = textPaint.getFontMetrics(mFontMetricsBuffer);
        float width = staticLayout.getWidth();
        float lineCount = staticLayout.getLineCount() * fontMetrics;
        float f5 = 0.0f - mDrawTextRectBuffer.left;
        float f6 = lineCount + 0.0f;
        Paint.Align textAlign = textPaint.getTextAlign();
        textPaint.setTextAlign(Paint.Align.LEFT);
        if (f4 != 0.0f) {
            float f7 = f5 - (width * 0.5f);
            float f8 = f6 - (lineCount * 0.5f);
            if (mPPointF.f6566x != 0.5f || mPPointF.f6567y != 0.5f) {
                FSize sizeOfRotatedRectangleByDegrees = getSizeOfRotatedRectangleByDegrees(width, lineCount, f4);
                f2 -= sizeOfRotatedRectangleByDegrees.width * (mPPointF.f6566x - 0.5f);
                f3 -= sizeOfRotatedRectangleByDegrees.height * (mPPointF.f6567y - 0.5f);
                FSize.recycleInstance(sizeOfRotatedRectangleByDegrees);
            }
            canvas.save();
            canvas.translate(f2, f3);
            canvas.rotate(f4);
            canvas.translate(f7, f8);
            staticLayout.draw(canvas);
            canvas.restore();
        } else {
            float f9 = mPPointF.f6566x;
            if (f9 != 0.0f || mPPointF.f6567y != 0.0f) {
                f5 -= width * f9;
                f6 -= lineCount * mPPointF.f6567y;
            }
            canvas.save();
            canvas.translate(f5 + f2, f6 + f3);
            staticLayout.draw(canvas);
            canvas.restore();
        }
        textPaint.setTextAlign(textAlign);
    }

    public static void drawXAxisValue(Canvas canvas, String str, float f2, float f3, Paint paint, MPPointF mPPointF, float f4) {
        float fontMetrics = paint.getFontMetrics(mFontMetricsBuffer);
        paint.getTextBounds(str, 0, str.length(), mDrawTextRectBuffer);
        float fWidth = 0.0f - mDrawTextRectBuffer.left;
        float f5 = (-mFontMetricsBuffer.ascent) + 0.0f;
        Paint.Align textAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.LEFT);
        if (f4 != 0.0f) {
            float fWidth2 = fWidth - (mDrawTextRectBuffer.width() * 0.5f);
            float f6 = f5 - (fontMetrics * 0.5f);
            if (mPPointF.f6566x != 0.5f || mPPointF.f6567y != 0.5f) {
                FSize sizeOfRotatedRectangleByDegrees = getSizeOfRotatedRectangleByDegrees(mDrawTextRectBuffer.width(), fontMetrics, f4);
                f2 -= sizeOfRotatedRectangleByDegrees.width * (mPPointF.f6566x - 0.5f);
                f3 -= sizeOfRotatedRectangleByDegrees.height * (mPPointF.f6567y - 0.5f);
                FSize.recycleInstance(sizeOfRotatedRectangleByDegrees);
            }
            canvas.save();
            canvas.translate(f2, f3);
            canvas.rotate(f4);
            canvas.drawText(str, fWidth2, f6, paint);
            canvas.restore();
        } else {
            if (mPPointF.f6566x != 0.0f || mPPointF.f6567y != 0.0f) {
                fWidth -= mDrawTextRectBuffer.width() * mPPointF.f6566x;
                f5 -= fontMetrics * mPPointF.f6567y;
            }
            canvas.drawText(str, fWidth + f2, f5 + f3, paint);
        }
        paint.setTextAlign(textAlign);
    }

    public static String formatNumber(float f2, int i2, boolean z2) {
        return formatNumber(f2, i2, z2, '.');
    }

    private static ValueFormatter generateDefaultValueFormatter() {
        return new DefaultValueFormatter(1);
    }

    public static int getDecimals(float f2) {
        float fRoundToNextSignificant = roundToNextSignificant(f2);
        if (Float.isInfinite(fRoundToNextSignificant)) {
            return 0;
        }
        return ((int) Math.ceil(-Math.log10(fRoundToNextSignificant))) + 2;
    }

    public static ValueFormatter getDefaultValueFormatter() {
        return mDefaultValueFormatter;
    }

    public static float getLineHeight(Paint paint) {
        return getLineHeight(paint, mFontMetrics);
    }

    public static float getLineSpacing(Paint paint) {
        return getLineSpacing(paint, mFontMetrics);
    }

    public static int getMaximumFlingVelocity() {
        return mMaximumFlingVelocity;
    }

    public static int getMinimumFlingVelocity() {
        return mMinimumFlingVelocity;
    }

    public static float getNormalizedAngle(float f2) {
        while (f2 < 0.0f) {
            f2 += 360.0f;
        }
        return f2 % 360.0f;
    }

    public static MPPointF getPosition(MPPointF mPPointF, float f2, float f3) {
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        getPosition(mPPointF, f2, f3, mPPointF2);
        return mPPointF2;
    }

    public static int getSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(FSize fSize, float f2) {
        return getSizeOfRotatedRectangleByRadians(fSize.width, fSize.height, f2 * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(FSize fSize, float f2) {
        return getSizeOfRotatedRectangleByRadians(fSize.width, fSize.height, f2);
    }

    public static void init(Context context) {
        if (context == null) {
            mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
            Log.e("MPChartLib-Utils", "Utils.init(...) PROVIDED CONTEXT OBJECT IS NULL");
        } else {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            mMetrics = context.getResources().getDisplayMetrics();
        }
    }

    public static double nextUp(double d3) {
        if (d3 == Double.POSITIVE_INFINITY) {
            return d3;
        }
        double d4 = d3 + 0.0d;
        return Double.longBitsToDouble(Double.doubleToRawLongBits(d4) + (d4 >= 0.0d ? 1L : -1L));
    }

    @SuppressLint({"NewApi"})
    public static void postInvalidateOnAnimation(View view) {
        view.postInvalidateOnAnimation();
    }

    public static float roundToNextSignificant(double d3) {
        if (Double.isInfinite(d3) || Double.isNaN(d3) || d3 == 0.0d) {
            return 0.0f;
        }
        return Math.round(d3 * r0) / ((float) Math.pow(10.0d, 1 - ((int) Math.ceil((float) Math.log10(d3 < 0.0d ? -d3 : d3)))));
    }

    public static void velocityTrackerPointerUpCleanUpIfNecessary(MotionEvent motionEvent, VelocityTracker velocityTracker) {
        velocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        float xVelocity = velocityTracker.getXVelocity(pointerId);
        float yVelocity = velocityTracker.getYVelocity(pointerId);
        int pointerCount = motionEvent.getPointerCount();
        for (int i2 = 0; i2 < pointerCount; i2++) {
            if (i2 != actionIndex) {
                int pointerId2 = motionEvent.getPointerId(i2);
                if ((velocityTracker.getXVelocity(pointerId2) * xVelocity) + (velocityTracker.getYVelocity(pointerId2) * yVelocity) < 0.0f) {
                    velocityTracker.clear();
                    return;
                }
            }
        }
    }

    public static String formatNumber(float f2, int i2, boolean z2, char c3) {
        boolean z3;
        float f3 = f2;
        char[] cArr = new char[35];
        if (f3 == 0.0f) {
            return "0";
        }
        int i3 = 0;
        boolean z4 = f3 < 1.0f && f3 > -1.0f;
        if (f3 < 0.0f) {
            f3 = -f3;
            z3 = true;
        } else {
            z3 = false;
        }
        int[] iArr = POW_10;
        int length = i2 > iArr.length ? iArr.length - 1 : i2;
        long jRound = Math.round(f3 * iArr[length]);
        int i4 = 34;
        boolean z5 = false;
        while (true) {
            if (jRound == 0 && i3 >= length + 1) {
                break;
            }
            int i5 = (int) (jRound % 10);
            jRound /= 10;
            int i6 = i4 - 1;
            cArr[i4] = (char) (i5 + 48);
            i3++;
            if (i3 == length) {
                i4 = i6 - 1;
                cArr[i6] = ',';
                i3++;
                z5 = true;
            } else {
                if (z2 && jRound != 0 && i3 > length) {
                    if (z5) {
                        if ((i3 - length) % 4 == 0) {
                            i4 = i6 - 1;
                            cArr[i6] = c3;
                            i3++;
                        }
                    } else if ((i3 - length) % 4 == 3) {
                        i4 = i6 - 1;
                        cArr[i6] = c3;
                        i3++;
                    }
                }
                i4 = i6;
            }
        }
        if (z4) {
            cArr[i4] = '0';
            i3++;
            i4--;
        }
        if (z3) {
            cArr[i4] = CharPool.DASHED;
            i3++;
        }
        int i7 = 35 - i3;
        return String.valueOf(cArr, i7, 35 - i7);
    }

    public static float getLineHeight(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return fontMetrics.descent - fontMetrics.ascent;
    }

    public static float getLineSpacing(Paint paint, Paint.FontMetrics fontMetrics) {
        paint.getFontMetrics(fontMetrics);
        return (fontMetrics.ascent - fontMetrics.top) + fontMetrics.bottom;
    }

    public static FSize getSizeOfRotatedRectangleByDegrees(float f2, float f3, float f4) {
        return getSizeOfRotatedRectangleByRadians(f2, f3, f4 * 0.017453292f);
    }

    public static FSize getSizeOfRotatedRectangleByRadians(float f2, float f3, float f4) {
        double d3 = f4;
        return FSize.getInstance(Math.abs(((float) Math.cos(d3)) * f2) + Math.abs(((float) Math.sin(d3)) * f3), Math.abs(f2 * ((float) Math.sin(d3))) + Math.abs(f3 * ((float) Math.cos(d3))));
    }

    public static void calcTextSize(Paint paint, String str, FSize fSize) {
        Rect rect = mCalcTextSizeRect;
        rect.set(0, 0, 0, 0);
        paint.getTextBounds(str, 0, str.length(), rect);
        fSize.width = rect.width();
        fSize.height = rect.height();
    }

    public static void getPosition(MPPointF mPPointF, float f2, float f3, MPPointF mPPointF2) {
        double d3 = f2;
        double d4 = f3;
        mPPointF2.f6566x = (float) (mPPointF.f6566x + (Math.cos(Math.toRadians(d4)) * d3));
        mPPointF2.f6567y = (float) (mPPointF.f6567y + (d3 * Math.sin(Math.toRadians(d4))));
    }

    @Deprecated
    public static void init(Resources resources) {
        mMetrics = resources.getDisplayMetrics();
        mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
        mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
    }

    public static void drawMultilineText(Canvas canvas, String str, float f2, float f3, TextPaint textPaint, FSize fSize, MPPointF mPPointF, float f4) {
        drawMultilineText(canvas, new StaticLayout(str, 0, str.length(), textPaint, (int) Math.max(Math.ceil(fSize.width), 1.0d), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false), f2, f3, textPaint, mPPointF, f4);
    }
}
