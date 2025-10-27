package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class FrequencyView extends View implements View.OnClickListener {
    private static final int ANIM_DURTION = 120;
    private static final int LINE_ANIM_COUNT = 4;
    private static final String TAG = "FrequencyView";
    private Handler handler;
    private int mEndY;
    private int mFrameNum;
    private int mLeveNum;
    private List<Line> mLines;
    private int mLintToLineW;
    public OnFrequencyViewClickListener mListener;
    private float mMaxLineStartY;
    private Paint mPaint;
    private int mShortLineHgt;
    private int mStartX;
    private int mStartY;
    private Runnable runnable;

    public final class Line {
        public float startY;
        public float[] startYs;
        public float stopY;

        /* renamed from: x, reason: collision with root package name */
        public float f16262x;

        public Line() {
        }

        public Line(float x2, float startY, float stopY) {
            this.f16262x = x2;
            this.startY = startY;
            this.stopY = stopY;
        }
    }

    public interface OnFrequencyViewClickListener {
        void onFreViewClick();
    }

    public FrequencyView(Context context) {
        this(context, null);
    }

    private int getSize(boolean isWidth, int measureSpec) {
        int paddingTop;
        int i2;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            if (isWidth) {
                paddingTop = getPaddingLeft() + getPaddingRight();
                i2 = this.mLintToLineW * 3;
            } else {
                paddingTop = getPaddingTop() + getPaddingBottom();
                i2 = this.mEndY - ((int) this.mMaxLineStartY);
            }
            int i3 = paddingTop + i2;
            logT("计算得出的尺寸 = " + i3);
            if (mode == Integer.MIN_VALUE) {
                return Math.min(i3, size);
            }
        } else if (mode == 1073741824) {
            return size;
        }
        return 0;
    }

    private void init() {
        this.mPaint.setColor(Color.parseColor("#FFF44336"));
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setDither(true);
        this.mPaint.setStrokeWidth(dp2px(2.0f));
        this.mLintToLineW = dp2px(10.0f);
        this.mFrameNum = dp2px(9.0f);
        this.mShortLineHgt = dp2px(20.0f);
        setOnClickListener(this);
        initData();
    }

    private void initData() {
        logT("view的宽 = " + getWidth() + "-->view的高 = " + getHeight() + "-->leftPadding = " + getPaddingLeft() + "-->topPadding = " + getPaddingTop() + "-->rightPadding = " + getPaddingRight() + "-->bottomPadding = " + getPaddingBottom());
        this.mStartX = getPaddingLeft();
        this.mStartY = getPaddingTop();
        ArrayList arrayList = new ArrayList();
        this.mEndY = this.mStartY + this.mShortLineHgt;
        int i2 = 0;
        while (i2 < 4) {
            int i3 = this.mEndY;
            int i4 = i2 + 1;
            float f2 = i3 - (((this.mShortLineHgt * i4) * 1) / 2);
            arrayList.add(new Line(this.mStartX + (i2 * this.mLintToLineW), f2, i3));
            logT("测试y = " + f2);
            i2 = i4;
        }
        this.mMaxLineStartY = ((Line) arrayList.get(3)).startY;
        Line line = new Line();
        line.f16262x = ((Line) arrayList.get(0)).f16262x;
        float f3 = ((Line) arrayList.get(2)).startY;
        line.startY = f3;
        line.startYs = new float[]{(r9 * 2) + f3, this.mFrameNum + f3, f3};
        line.stopY = ((Line) arrayList.get(0)).stopY;
        this.mLines.add(line);
        Line line2 = new Line();
        line2.f16262x = ((Line) arrayList.get(1)).f16262x;
        float f4 = ((Line) arrayList.get(1)).startY;
        line2.startY = f4;
        line2.startYs = new float[]{f4 - (r9 * 2), f4 - this.mFrameNum, f4};
        line2.stopY = ((Line) arrayList.get(1)).stopY;
        this.mLines.add(line2);
        Line line3 = new Line();
        line3.f16262x = ((Line) arrayList.get(2)).f16262x;
        float f5 = ((Line) arrayList.get(3)).startY;
        line3.startY = f5;
        int i5 = this.mFrameNum;
        line3.startYs = new float[]{i5 + f5 + ((i5 * 1) / 2), i5 + f5 + ((i5 * 1) / 4), f5};
        line3.stopY = ((Line) arrayList.get(2)).stopY;
        this.mLines.add(line3);
        Line line4 = new Line();
        line4.f16262x = ((Line) arrayList.get(3)).f16262x;
        float f6 = ((Line) arrayList.get(0)).startY;
        line4.startY = f6;
        line4.startYs = new float[]{f6 - (r8 * 2), f6 - this.mFrameNum, f6};
        line4.stopY = ((Line) arrayList.get(3)).stopY;
        this.mLines.add(line4);
    }

    private void logT(String log) {
        Log.d(TAG, "震动频率-->" + log);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float[] reverseArray(float[] array) {
        float[] fArr = new float[array.length];
        for (int i2 = 0; i2 < array.length; i2++) {
            fArr[i2] = array[(array.length - i2) - 1];
        }
        return fArr;
    }

    public int dp2px(float dpValue) {
        return (int) ((dpValue * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        start(600);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        logT("点击了view");
        OnFrequencyViewClickListener onFrequencyViewClickListener = this.mListener;
        if (onFrequencyViewClickListener == null) {
            return;
        }
        onFrequencyViewClickListener.onFreViewClick();
    }

    public void onClickSetFreViewListener(OnFrequencyViewClickListener listener) {
        this.mListener = listener;
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0.0f, getPaddingTop());
        for (int i2 = 0; i2 < this.mLines.size(); i2++) {
            Line line = this.mLines.get(i2);
            float f2 = line.f16262x;
            canvas.drawLine(f2, line.startYs[this.mLeveNum], f2, line.stopY, this.mPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getSize(true, widthMeasureSpec), getSize(false, heightMeasureSpec));
    }

    public void start(int delay) {
        stop();
        this.handler.postDelayed(this.runnable, delay);
    }

    public void stop() {
        this.handler.removeCallbacks(this.runnable);
    }

    public FrequencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaint = new Paint();
        this.mLeveNum = 0;
        this.mLines = new ArrayList();
        this.handler = new Handler();
        this.runnable = new Runnable() { // from class: com.psychiatrygarden.widget.FrequencyView.1
            @Override // java.lang.Runnable
            public void run() {
                int i2 = FrequencyView.this.mLeveNum;
                if (i2 == 0) {
                    FrequencyView.this.mLeveNum = 1;
                } else if (i2 == 1) {
                    FrequencyView.this.mLeveNum = 2;
                } else if (i2 == 2) {
                    FrequencyView.this.mLeveNum = 0;
                    for (int i3 = 0; i3 < 4; i3++) {
                        Line line = (Line) FrequencyView.this.mLines.get(i3);
                        line.startYs = FrequencyView.this.reverseArray(line.startYs);
                    }
                }
                FrequencyView.this.postInvalidate();
                FrequencyView.this.handler.postDelayed(FrequencyView.this.runnable, 120L);
            }
        };
        init();
    }
}
