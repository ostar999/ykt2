package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.exoplayer2.C;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CircleTextProgressbar extends AppCompatTextView {
    final Rect bounds;
    private int circleColor;
    private ColorStateList inCircleColors;
    private int listenerWhat;
    private RectF mArcRect;
    private OnCountdownProgressListener mCountdownProgressListener;
    private Paint mPaint;
    private ProgressType mProgressType;
    private int outLineColor;
    private int outLineWidth;
    private int progress;
    private Runnable progressChangeTask;
    private int progressLineColor;
    private int progressLineWidth;
    private long timeMillis;

    /* renamed from: com.psychiatrygarden.widget.CircleTextProgressbar$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$widget$CircleTextProgressbar$ProgressType;

        static {
            int[] iArr = new int[ProgressType.values().length];
            $SwitchMap$com$psychiatrygarden$widget$CircleTextProgressbar$ProgressType = iArr;
            try {
                iArr[ProgressType.COUNT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$widget$CircleTextProgressbar$ProgressType[ProgressType.COUNT_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public interface OnCountdownProgressListener {
        void onProgress(int what, int progress);
    }

    public enum ProgressType {
        COUNT,
        COUNT_BACK
    }

    public CircleTextProgressbar(Context context) {
        this(context, null);
    }

    public static /* synthetic */ int access$112(CircleTextProgressbar circleTextProgressbar, int i2) {
        int i3 = circleTextProgressbar.progress + i2;
        circleTextProgressbar.progress = i3;
        return i3;
    }

    public static /* synthetic */ int access$120(CircleTextProgressbar circleTextProgressbar, int i2) {
        int i3 = circleTextProgressbar.progress - i2;
        circleTextProgressbar.progress = i3;
        return i3;
    }

    private void initialize(Context context, AttributeSet attributeSet) {
        this.mPaint.setAntiAlias(true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextProgressbar);
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            this.inCircleColors = typedArrayObtainStyledAttributes.getColorStateList(0);
        } else {
            this.inCircleColors = ColorStateList.valueOf(0);
        }
        this.circleColor = this.inCircleColors.getColorForState(getDrawableState(), 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    private void resetProgress() {
        int i2 = AnonymousClass2.$SwitchMap$com$psychiatrygarden$widget$CircleTextProgressbar$ProgressType[this.mProgressType.ordinal()];
        if (i2 == 1) {
            this.progress = 0;
        } else {
            if (i2 != 2) {
                return;
            }
            this.progress = 100;
        }
    }

    private void validateCircleColor() {
        int colorForState = this.inCircleColors.getColorForState(getDrawableState(), 0);
        if (this.circleColor != colorForState) {
            this.circleColor = colorForState;
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int validateProgress(int progress) {
        if (progress > 100) {
            return 100;
        }
        if (progress < 0) {
            return 0;
        }
        return progress;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        validateCircleColor();
    }

    public int getProgress() {
        return this.progress;
    }

    public ProgressType getProgressType() {
        return this.mProgressType;
    }

    public long getTimeMillis() {
        return this.timeMillis;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        getDrawingRect(this.bounds);
        float fWidth = (this.bounds.height() > this.bounds.width() ? this.bounds.width() : this.bounds.height()) / 2;
        int colorForState = this.inCircleColors.getColorForState(getDrawableState(), 0);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(colorForState);
        canvas.drawCircle(this.bounds.centerX(), this.bounds.centerY(), fWidth - this.outLineWidth, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.outLineWidth);
        this.mPaint.setColor(this.outLineColor);
        canvas.drawCircle(this.bounds.centerX(), this.bounds.centerY(), fWidth - (this.outLineWidth / 2.0f), this.mPaint);
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(getText().toString(), this.bounds.centerX(), this.bounds.centerY() - ((paint.descent() + paint.ascent()) / 2.0f), paint);
        this.mPaint.setColor(this.progressLineColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.progressLineWidth);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        int i2 = this.progressLineWidth + this.outLineWidth;
        RectF rectF = this.mArcRect;
        Rect rect = this.bounds;
        float f2 = i2 / 2.0f;
        rectF.set(rect.left + f2, rect.top + f2, rect.right - f2, rect.bottom - f2);
        canvas.drawArc(this.mArcRect, 0.0f, (this.progress * 360) / 100.0f, false, this.mPaint);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iMax = Math.max(getMeasuredWidth(), getMeasuredHeight()) + ((this.outLineWidth + this.progressLineWidth) * 4);
        setMeasuredDimension(iMax, iMax);
    }

    public void reStart() {
        resetProgress();
        start();
    }

    public void setCountdownProgressListener(int what, OnCountdownProgressListener mCountdownProgressListener) {
        this.listenerWhat = what;
        this.mCountdownProgressListener = mCountdownProgressListener;
    }

    public void setInCircleColor(@ColorInt int inCircleColor) {
        this.inCircleColors = ColorStateList.valueOf(inCircleColor);
        invalidate();
    }

    public void setOutLineColor(@ColorInt int outLineColor) {
        this.outLineColor = outLineColor;
        invalidate();
    }

    public void setOutLineWidth(@ColorInt int outLineWidth) {
        this.outLineWidth = outLineWidth;
        invalidate();
    }

    public void setProgress(int progress) {
        this.progress = validateProgress(progress);
        invalidate();
    }

    public void setProgressColor(@ColorInt int progressLineColor) {
        this.progressLineColor = progressLineColor;
        invalidate();
    }

    public void setProgressLineWidth(int progressLineWidth) {
        this.progressLineWidth = progressLineWidth;
        invalidate();
    }

    public void setProgressType(ProgressType progressType) {
        this.mProgressType = progressType;
        resetProgress();
        invalidate();
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
        invalidate();
    }

    public void start() {
        stop();
        post(this.progressChangeTask);
    }

    public void stop() {
        removeCallbacks(this.progressChangeTask);
    }

    public CircleTextProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.outLineColor = -16777216;
        this.outLineWidth = 2;
        this.inCircleColors = ColorStateList.valueOf(0);
        this.progressLineColor = -16776961;
        this.progressLineWidth = 8;
        this.mPaint = new Paint();
        this.mArcRect = new RectF();
        this.progress = 100;
        this.mProgressType = ProgressType.COUNT_BACK;
        this.timeMillis = C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
        this.bounds = new Rect();
        this.listenerWhat = 0;
        this.progressChangeTask = new Runnable() { // from class: com.psychiatrygarden.widget.CircleTextProgressbar.1
            @Override // java.lang.Runnable
            public void run() {
                CircleTextProgressbar.this.removeCallbacks(this);
                int i2 = AnonymousClass2.$SwitchMap$com$psychiatrygarden$widget$CircleTextProgressbar$ProgressType[CircleTextProgressbar.this.mProgressType.ordinal()];
                if (i2 == 1) {
                    CircleTextProgressbar.access$112(CircleTextProgressbar.this, 1);
                } else if (i2 == 2) {
                    CircleTextProgressbar.access$120(CircleTextProgressbar.this, 1);
                }
                if (CircleTextProgressbar.this.progress < 0 || CircleTextProgressbar.this.progress > 100) {
                    CircleTextProgressbar circleTextProgressbar = CircleTextProgressbar.this;
                    circleTextProgressbar.progress = circleTextProgressbar.validateProgress(circleTextProgressbar.progress);
                    return;
                }
                if (CircleTextProgressbar.this.mCountdownProgressListener != null) {
                    CircleTextProgressbar.this.mCountdownProgressListener.onProgress(CircleTextProgressbar.this.listenerWhat, CircleTextProgressbar.this.progress);
                }
                CircleTextProgressbar.this.invalidate();
                CircleTextProgressbar circleTextProgressbar2 = CircleTextProgressbar.this;
                circleTextProgressbar2.postDelayed(circleTextProgressbar2.progressChangeTask, CircleTextProgressbar.this.timeMillis / 100);
            }
        };
        initialize(context, attrs);
    }
}
