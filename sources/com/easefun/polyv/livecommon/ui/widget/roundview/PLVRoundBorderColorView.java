package com.easefun.polyv.livecommon.ui.widget.roundview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;
import com.easefun.polyv.livecommon.R;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;

/* loaded from: classes3.dex */
public class PLVRoundBorderColorView extends View {
    private static final float BG_RADIUS = 10.0f;
    private static final int CONTENT_SIZE = 20;
    private static final float SHADOW_WIDTH = 6.0f;
    private static final int SIZE = 36;
    private static final float STROKE_RADIUS = 11.5f;
    private static final float STROKE_WIDTH = 4.0f;
    public static final int UNCHECKED = Integer.MIN_VALUE;
    private Paint mBackgroundPaint;
    private Rect mCheckRect;
    private boolean mChecked;
    private boolean mCountable;
    private float mDensity;
    private boolean mEnabled;
    private Paint mInnerStrokePaint;
    private Paint mOuterStrokePaint;

    public PLVRoundBorderColorView(Context context) throws Resources.NotFoundException {
        super(context);
        this.mEnabled = true;
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) throws Resources.NotFoundException {
        this.mDensity = context.getResources().getDisplayMetrics().density;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.PLVRoundBorderColorView);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVRoundBorderColorView_innerBorderColor, -1);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVRoundBorderColorView_innerBorderWidth, 0.0f);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.PLVRoundBorderColorView_outerBorderColor, -1);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(R.styleable.PLVRoundBorderColorView_outerBorderWidth, 0.0f);
        Paint paint = new Paint();
        this.mInnerStrokePaint = paint;
        paint.setAntiAlias(true);
        this.mInnerStrokePaint.setStyle(Paint.Style.STROKE);
        this.mInnerStrokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.mInnerStrokePaint.setStrokeWidth(dimension);
        this.mInnerStrokePaint.setColor(color);
        Paint paint2 = new Paint();
        this.mOuterStrokePaint = paint2;
        paint2.setAntiAlias(true);
        this.mOuterStrokePaint.setStyle(Paint.Style.STROKE);
        this.mOuterStrokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.mOuterStrokePaint.setStrokeWidth(dimension2);
        this.mOuterStrokePaint.setColor(color2);
        if (this.mBackgroundPaint == null) {
            Paint paint3 = new Paint();
            this.mBackgroundPaint = paint3;
            paint3.setAntiAlias(true);
            this.mBackgroundPaint.setStyle(Paint.Style.FILL);
            this.mBackgroundPaint.setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.PLVRoundBorderColorView_mainColor, ResourcesCompat.getColor(getResources(), R.color.item_checkCircle_backgroundColor, getContext().getTheme())));
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public String getBackgroundColorString() {
        Paint paint = this.mBackgroundPaint;
        if (paint == null) {
            return "";
        }
        return DictionaryFactory.SHARP + Integer.toHexString(paint.getColor()).substring(2);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mChecked) {
            float strokeWidth = (this.mDensity * BG_RADIUS) + (this.mOuterStrokePaint.getStrokeWidth() / 2.0f);
            float f2 = this.mDensity;
            canvas.drawCircle((f2 * 36.0f) / 2.0f, (f2 * 36.0f) / 2.0f, strokeWidth, this.mOuterStrokePaint);
            float strokeWidth2 = (this.mDensity * BG_RADIUS) + (this.mInnerStrokePaint.getStrokeWidth() / 2.0f);
            float f3 = this.mDensity;
            canvas.drawCircle((f3 * 36.0f) / 2.0f, (f3 * 36.0f) / 2.0f, strokeWidth2, this.mInnerStrokePaint);
        }
        float f4 = this.mDensity;
        canvas.drawCircle((f4 * 36.0f) / 2.0f, (36.0f * f4) / 2.0f, f4 * BG_RADIUS, this.mBackgroundPaint);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) (this.mDensity * 36.0f), 1073741824);
        super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec);
    }

    public void setChecked(boolean checked) {
        if (this.mCountable) {
            throw new IllegalStateException("CheckView is countable, call setCheckedNum() instead.");
        }
        this.mChecked = checked;
        invalidate();
    }

    public void setCheckedNum(int checkedNum) {
        if (!this.mCountable) {
            throw new IllegalStateException("CheckView is not countable, call setChecked() instead.");
        }
        if (checkedNum != Integer.MIN_VALUE && checkedNum <= 0) {
            throw new IllegalArgumentException("checked num can't be negative.");
        }
        invalidate();
    }

    public void setCountable(boolean countable) {
        this.mCountable = countable;
    }

    @Override // android.view.View
    public void setEnabled(boolean enabled) {
        if (this.mEnabled != enabled) {
            this.mEnabled = enabled;
            invalidate();
        }
    }

    public PLVRoundBorderColorView(Context context, AttributeSet attrs) throws Resources.NotFoundException {
        super(context, attrs);
        this.mEnabled = true;
        init(context, attrs);
    }

    public PLVRoundBorderColorView(Context context, AttributeSet attrs, int defStyleAttr) throws Resources.NotFoundException {
        super(context, attrs, defStyleAttr);
        this.mEnabled = true;
        init(context, attrs);
    }
}
