package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class SimpleLineChart extends View {
    private int mHeight;
    private int mLineColor;
    private String mNoDataMsg;
    private HashMap<Integer, Integer> mPointMap;
    private float mPointRadius;
    private float mStrokeWidth;
    private int mWidth;
    private String[] mXAxis;
    private String[] mYAxis;
    private float mYAxisFontSize;

    public SimpleLineChart(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mXAxis.length == 0 || this.mYAxis.length == 0) {
            throw new IllegalArgumentException("X or Y items is null");
        }
        Paint paint = new Paint();
        paint.setTextSize(this.mYAxisFontSize);
        paint.setColor(Color.parseColor("#3F51B5"));
        HashMap<Integer, Integer> map = this.mPointMap;
        if (map == null || map.size() == 0) {
            canvas.drawText(this.mNoDataMsg, (this.mWidth / 2) - (((int) paint.measureText(this.mNoDataMsg)) / 2), this.mHeight / 2, paint);
            return;
        }
        int[] iArr = new int[this.mYAxis.length];
        int length = (int) (((this.mHeight - this.mYAxisFontSize) - 2.0f) / r2.length);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Math.ceil(fontMetrics.descent - fontMetrics.ascent);
        Log.e("wing", this.mHeight + "");
        int i2 = 0;
        while (true) {
            String[] strArr = this.mYAxis;
            if (i2 >= strArr.length) {
                break;
            }
            float f2 = i2 * length;
            canvas.drawText(strArr[i2], 0.0f, this.mYAxisFontSize + f2, paint);
            iArr[i2] = (int) (this.mYAxisFontSize + f2);
            i2++;
        }
        int length2 = this.mXAxis.length;
        int[] iArr2 = new int[length2];
        Log.e("wing", length2 + "");
        int iMeasureText = (int) paint.measureText(this.mYAxis[1]);
        int length3 = (this.mWidth - 50) / this.mXAxis.length;
        int length4 = (int) (this.mYAxisFontSize + ((float) (this.mYAxis.length * length)));
        int i3 = 0;
        while (true) {
            String[] strArr2 = this.mXAxis;
            if (i3 >= strArr2.length) {
                break;
            }
            canvas.drawText(strArr2[i3], r14 + 50, length4, paint);
            iArr2[i3] = (int) ((i3 * length3) + iMeasureText + (paint.measureText(this.mXAxis[i3]) / 2.0f) + 50 + 10.0f);
            i3++;
        }
        Paint paint2 = new Paint();
        paint2.setColor(this.mLineColor);
        Paint paint3 = new Paint();
        paint3.setColor(this.mLineColor);
        paint3.setAntiAlias(true);
        paint3.setStrokeWidth(this.mStrokeWidth);
        paint2.setStyle(Paint.Style.FILL);
        for (int i4 = 0; i4 < this.mXAxis.length; i4++) {
            if (this.mPointMap.get(Integer.valueOf(i4)) == null) {
                throw new IllegalArgumentException("PointMap has incomplete data!");
            }
            canvas.drawCircle(iArr2[i4], iArr[this.mPointMap.get(Integer.valueOf(i4)).intValue()], this.mPointRadius, paint2);
            if (i4 > 0) {
                int i5 = i4 - 1;
                canvas.drawLine(iArr2[i5], iArr[this.mPointMap.get(Integer.valueOf(i5)).intValue()], iArr2[i4], iArr[this.mPointMap.get(Integer.valueOf(i4)).intValue()], paint3);
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == 1073741824) {
            this.mWidth = size;
        } else if (mode == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("width must be EXACTLY,you should set like android:width=\"200dp\"");
        }
        if (mode2 == 1073741824) {
            this.mHeight = size2;
        } else if (widthMeasureSpec == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("height must be EXACTLY,you should set like android:height=\"200dp\"");
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    public void setData(HashMap<Integer, Integer> data) {
        this.mPointMap = data;
        invalidate();
    }

    public void setLineColor(int color) {
        this.mLineColor = color;
        invalidate();
    }

    public void setXItem(String[] xItem) {
        this.mXAxis = xItem;
    }

    public void setYItem(String[] yItem) {
        this.mYAxis = yItem;
    }

    public SimpleLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mYAxisFontSize = 24.0f;
        this.mLineColor = Color.parseColor("#00BCD4");
        this.mStrokeWidth = 8.0f;
        this.mPointRadius = 10.0f;
        this.mNoDataMsg = "no data";
        this.mXAxis = new String[0];
        this.mYAxis = new String[0];
    }
}
