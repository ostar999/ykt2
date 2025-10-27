package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SpringProgressView extends View {
    private int color_all;
    private float errorCount;
    private Paint mGreenPaint;
    private int mHeight;
    private Paint mPaint;
    private Paint mRedPaint;
    private int mWidth;
    private float maxCount;
    protected Path path;
    protected float[] rids;
    private float rightCount;
    private int type;

    public SpringProgressView(Context context) {
        this(context, null);
    }

    private int dipToPx(int dip) {
        return (int) ((dip * getContext().getResources().getDisplayMetrics().density) + ((dip >= 0 ? 1 : -1) * 0.5f));
    }

    private void initView(Context context) {
        int color;
        int color2;
        this.mPaint = new Paint();
        this.mRedPaint = new Paint();
        this.mGreenPaint = new Paint();
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mRedPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mGreenPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaint.setAntiAlias(true);
        this.mRedPaint.setAntiAlias(false);
        this.mGreenPaint.setAntiAlias(false);
        if (SkinManager.getCurrentSkinType(context) == 0) {
            this.color_all = ContextCompat.getColor(getContext(), R.color.processredbgcolor);
            color = ContextCompat.getColor(getContext(), R.color.processredredcolor);
            color2 = ContextCompat.getColor(getContext(), R.color.processredgreencolor);
        } else {
            this.color_all = ContextCompat.getColor(getContext(), R.color.processredbgcolor_night);
            color = ContextCompat.getColor(getContext(), R.color.processredredcolor_night);
            color2 = ContextCompat.getColor(getContext(), R.color.processredgreencolor_night);
        }
        this.mPaint.setColor(this.color_all);
        this.mRedPaint.setColor(color);
        this.mGreenPaint.setColor(color2);
        this.path = new Path();
    }

    public float getErrorCount() {
        return this.errorCount;
    }

    public float getMaxCount() {
        return this.maxCount;
    }

    public float getRightCount() {
        return this.rightCount;
    }

    @Override // android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (this.type == 1) {
            this.path.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), this.mWidth, this.mHeight), this.rids, Path.Direction.CW);
            canvas.clipPath(this.path);
        }
        float f2 = 0;
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.mWidth, this.mHeight), f2, f2, this.mPaint);
        float f3 = this.errorCount;
        float f4 = this.rightCount;
        if (f3 + f4 > 0.0f) {
            float f5 = (f3 + f4) / this.maxCount;
            canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.mWidth * f5, this.mHeight), f2, f2, this.mGreenPaint);
            float f6 = this.errorCount;
            if (f6 > 0.0f) {
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, this.mWidth * f5 * (f6 / (this.rightCount + f6)), this.mHeight), f2, f2, this.mRedPaint);
            }
        }
        canvas.restore();
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == 1073741824 || mode == Integer.MIN_VALUE) {
            this.mWidth = size;
        } else {
            this.mWidth = 0;
        }
        if (mode2 == Integer.MIN_VALUE || mode2 == 0) {
            this.mHeight = dipToPx(15);
        } else {
            this.mHeight = size2;
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    public void setColorAll(int color) {
        this.color_all = color;
    }

    public void setMaxErrRightCount(float maxCount, float errorCount, float rightCount) {
        this.maxCount = maxCount;
        this.errorCount = errorCount;
        this.rightCount = rightCount;
        invalidate();
    }

    public SpringProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rids = new float[]{15.0f, 15.0f, 15.0f, 15.0f, 15.0f, 15.0f, 15.0f, 15.0f};
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.SpringProgressView);
        this.type = typedArrayObtainStyledAttributes.getInteger(0, 1);
        typedArrayObtainStyledAttributes.recycle();
        initView(context);
    }
}
