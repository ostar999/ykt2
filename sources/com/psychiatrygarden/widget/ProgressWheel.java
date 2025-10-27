package com.psychiatrygarden.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ProgressWheel extends View {
    private static final String TAG = "ProgressWheel";
    private int barColor;
    private float barExtraLength;
    private boolean barGrowingFromFront;
    private final int barLength;
    private final int barMaxLength;
    private Paint barPaint;
    private double barSpinCycleTime;
    private int barWidth;
    private ProgressCallback callback;
    private RectF circleBounds;
    private int circleRadius;
    private boolean fillRadius;
    private boolean isSpinning;
    private long lastTimeAnimated;
    private boolean linearProgress;
    private float mProgress;
    private float mTargetProgress;
    private final long pauseGrowingTime;
    private long pausedTimeWithoutGrowing;
    private int rimColor;
    private Paint rimPaint;
    private int rimWidth;
    private boolean shouldAnimate;
    private float spinSpeed;
    private double timeStartGrowing;

    public interface ProgressCallback {
        void onProgressUpdate(float progress);
    }

    public static class WheelSavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<WheelSavedState> CREATOR = new Parcelable.Creator<WheelSavedState>() { // from class: com.psychiatrygarden.widget.ProgressWheel.WheelSavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WheelSavedState createFromParcel(Parcel in) {
                return new WheelSavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WheelSavedState[] newArray(int size) {
                return new WheelSavedState[size];
            }
        };
        int barColor;
        int barWidth;
        int circleRadius;
        boolean fillRadius;
        boolean isSpinning;
        boolean linearProgress;
        float mProgress;
        float mTargetProgress;
        int rimColor;
        int rimWidth;
        float spinSpeed;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeFloat(this.mProgress);
            parcel.writeFloat(this.mTargetProgress);
            parcel.writeByte(this.isSpinning ? (byte) 1 : (byte) 0);
            parcel.writeFloat(this.spinSpeed);
            parcel.writeInt(this.barWidth);
            parcel.writeInt(this.barColor);
            parcel.writeInt(this.rimWidth);
            parcel.writeInt(this.rimColor);
            parcel.writeInt(this.circleRadius);
            parcel.writeByte(this.linearProgress ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.fillRadius ? (byte) 1 : (byte) 0);
        }

        public WheelSavedState(Parcelable superState) {
            super(superState);
        }

        private WheelSavedState(Parcel in) {
            super(in);
            this.mProgress = in.readFloat();
            this.mTargetProgress = in.readFloat();
            this.isSpinning = in.readByte() != 0;
            this.spinSpeed = in.readFloat();
            this.barWidth = in.readInt();
            this.barColor = in.readInt();
            this.rimWidth = in.readInt();
            this.rimColor = in.readInt();
            this.circleRadius = in.readInt();
            this.linearProgress = in.readByte() != 0;
            this.fillRadius = in.readByte() != 0;
        }
    }

    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.barLength = 16;
        this.barMaxLength = 270;
        this.pauseGrowingTime = 200L;
        this.circleRadius = 28;
        this.barWidth = 4;
        this.rimWidth = 4;
        this.fillRadius = false;
        this.timeStartGrowing = 0.0d;
        this.barSpinCycleTime = 460.0d;
        this.barExtraLength = 0.0f;
        this.barGrowingFromFront = true;
        this.pausedTimeWithoutGrowing = 0L;
        this.barColor = -1442840576;
        this.rimColor = 16777215;
        this.barPaint = new Paint();
        this.rimPaint = new Paint();
        this.circleBounds = new RectF();
        this.spinSpeed = 230.0f;
        this.lastTimeAnimated = 0L;
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        this.isSpinning = false;
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel));
        setAnimationEnabled();
    }

    private void parseAttributes(TypedArray a3) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.barWidth = (int) TypedValue.applyDimension(1, this.barWidth, displayMetrics);
        this.rimWidth = (int) TypedValue.applyDimension(1, this.rimWidth, displayMetrics);
        int iApplyDimension = (int) TypedValue.applyDimension(1, this.circleRadius, displayMetrics);
        this.circleRadius = iApplyDimension;
        this.circleRadius = (int) a3.getDimension(3, iApplyDimension);
        this.fillRadius = a3.getBoolean(4, false);
        this.barWidth = (int) a3.getDimension(2, this.barWidth);
        this.rimWidth = (int) a3.getDimension(8, this.rimWidth);
        this.spinSpeed = a3.getFloat(9, this.spinSpeed / 360.0f) * 360.0f;
        this.barSpinCycleTime = a3.getInt(1, (int) this.barSpinCycleTime);
        this.barColor = a3.getColor(0, this.barColor);
        this.rimColor = a3.getColor(7, this.rimColor);
        this.linearProgress = a3.getBoolean(5, false);
        if (a3.getBoolean(6, false)) {
            spin();
        }
        a3.recycle();
    }

    private void runCallback(float value) {
        ProgressCallback progressCallback = this.callback;
        if (progressCallback != null) {
            progressCallback.onProgressUpdate(value);
        }
    }

    @TargetApi(17)
    private void setAnimationEnabled() {
        this.shouldAnimate = Settings.Global.getFloat(getContext().getContentResolver(), "animator_duration_scale", 1.0f) != 0.0f;
    }

    private void setupBounds(int layout_width, int layout_height) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (this.fillRadius) {
            int i2 = this.barWidth;
            this.circleBounds = new RectF(paddingLeft + i2, paddingTop + i2, (layout_width - paddingRight) - i2, (layout_height - paddingBottom) - i2);
            return;
        }
        int i3 = (layout_width - paddingLeft) - paddingRight;
        int iMin = Math.min(Math.min(i3, (layout_height - paddingBottom) - paddingTop), (this.circleRadius * 2) - (this.barWidth * 2));
        int i4 = ((i3 - iMin) / 2) + paddingLeft;
        int i5 = ((((layout_height - paddingTop) - paddingBottom) - iMin) / 2) + paddingTop;
        int i6 = this.barWidth;
        this.circleBounds = new RectF(i4 + i6, i5 + i6, (i4 + iMin) - i6, (i5 + iMin) - i6);
    }

    private void setupPaints() {
        this.barPaint.setColor(this.barColor);
        this.barPaint.setAntiAlias(true);
        this.barPaint.setStyle(Paint.Style.STROKE);
        this.barPaint.setStrokeWidth(this.barWidth);
        this.rimPaint.setColor(this.rimColor);
        this.rimPaint.setAntiAlias(true);
        this.rimPaint.setStyle(Paint.Style.STROKE);
        this.rimPaint.setStrokeWidth(this.rimWidth);
    }

    private void updateBarLength(long deltaTimeInMilliSeconds) {
        long j2 = this.pausedTimeWithoutGrowing;
        if (j2 < 200) {
            this.pausedTimeWithoutGrowing = j2 + deltaTimeInMilliSeconds;
            return;
        }
        double d3 = this.timeStartGrowing + deltaTimeInMilliSeconds;
        this.timeStartGrowing = d3;
        double d4 = this.barSpinCycleTime;
        if (d3 > d4) {
            this.timeStartGrowing = d3 - d4;
            this.pausedTimeWithoutGrowing = 0L;
            this.barGrowingFromFront = !this.barGrowingFromFront;
        }
        float fCos = (((float) Math.cos(((this.timeStartGrowing / d4) + 1.0d) * 3.141592653589793d)) / 2.0f) + 0.5f;
        if (this.barGrowingFromFront) {
            this.barExtraLength = fCos * 254.0f;
            return;
        }
        float f2 = (1.0f - fCos) * 254.0f;
        this.mProgress += this.barExtraLength - f2;
        this.barExtraLength = f2;
    }

    public int getBarColor() {
        return this.barColor;
    }

    public int getBarWidth() {
        return this.barWidth;
    }

    public int getCircleRadius() {
        return this.circleRadius;
    }

    public float getProgress() {
        if (this.isSpinning) {
            return -1.0f;
        }
        return this.mProgress / 360.0f;
    }

    public int getRimColor() {
        return this.rimColor;
    }

    public int getRimWidth() {
        return this.rimWidth;
    }

    public float getSpinSpeed() {
        return this.spinSpeed / 360.0f;
    }

    public boolean isSpinning() {
        return this.isSpinning;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f2;
        super.onDraw(canvas);
        canvas.drawArc(this.circleBounds, 360.0f, 360.0f, false, this.rimPaint);
        if (this.shouldAnimate) {
            float fPow = 0.0f;
            boolean z2 = true;
            if (this.isSpinning) {
                long jUptimeMillis = SystemClock.uptimeMillis() - this.lastTimeAnimated;
                float f3 = (jUptimeMillis * this.spinSpeed) / 1000.0f;
                updateBarLength(jUptimeMillis);
                float f4 = this.mProgress + f3;
                this.mProgress = f4;
                if (f4 > 360.0f) {
                    this.mProgress = f4 - 360.0f;
                    runCallback(-1.0f);
                }
                this.lastTimeAnimated = SystemClock.uptimeMillis();
                float f5 = this.mProgress - 90.0f;
                float f6 = this.barExtraLength + 16.0f;
                if (isInEditMode()) {
                    f6 = 135.0f;
                    f2 = 0.0f;
                } else {
                    f2 = f5;
                }
                canvas.drawArc(this.circleBounds, f2, f6, false, this.barPaint);
            } else {
                float f7 = this.mProgress;
                if (f7 != this.mTargetProgress) {
                    this.mProgress = Math.min(this.mProgress + (((SystemClock.uptimeMillis() - this.lastTimeAnimated) / 1000.0f) * this.spinSpeed), this.mTargetProgress);
                    this.lastTimeAnimated = SystemClock.uptimeMillis();
                } else {
                    z2 = false;
                }
                if (f7 != this.mProgress) {
                    runCallback();
                }
                float fPow2 = this.mProgress;
                if (!this.linearProgress) {
                    fPow = ((float) (1.0d - Math.pow(1.0f - (fPow2 / 360.0f), 4.0f))) * 360.0f;
                    fPow2 = ((float) (1.0d - Math.pow(1.0f - (this.mProgress / 360.0f), 2.0f))) * 360.0f;
                }
                canvas.drawArc(this.circleBounds, fPow - 90.0f, isInEditMode() ? 360.0f : fPow2, false, this.barPaint);
            }
            if (z2) {
                invalidate();
            }
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingLeft = this.circleRadius + getPaddingLeft() + getPaddingRight();
        int paddingTop = this.circleRadius + getPaddingTop() + getPaddingBottom();
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == 1073741824) {
            paddingLeft = size;
        } else if (mode == Integer.MIN_VALUE) {
            paddingLeft = Math.min(paddingLeft, size);
        }
        if (mode2 == 1073741824 || mode == 1073741824) {
            paddingTop = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            paddingTop = Math.min(paddingTop, size2);
        }
        setMeasuredDimension(paddingLeft, paddingTop);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof WheelSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        WheelSavedState wheelSavedState = (WheelSavedState) state;
        super.onRestoreInstanceState(wheelSavedState.getSuperState());
        this.mProgress = wheelSavedState.mProgress;
        this.mTargetProgress = wheelSavedState.mTargetProgress;
        this.isSpinning = wheelSavedState.isSpinning;
        this.spinSpeed = wheelSavedState.spinSpeed;
        this.barWidth = wheelSavedState.barWidth;
        this.barColor = wheelSavedState.barColor;
        this.rimWidth = wheelSavedState.rimWidth;
        this.rimColor = wheelSavedState.rimColor;
        this.circleRadius = wheelSavedState.circleRadius;
        this.linearProgress = wheelSavedState.linearProgress;
        this.fillRadius = wheelSavedState.fillRadius;
        this.lastTimeAnimated = SystemClock.uptimeMillis();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        WheelSavedState wheelSavedState = new WheelSavedState(super.onSaveInstanceState());
        wheelSavedState.mProgress = this.mProgress;
        wheelSavedState.mTargetProgress = this.mTargetProgress;
        wheelSavedState.isSpinning = this.isSpinning;
        wheelSavedState.spinSpeed = this.spinSpeed;
        wheelSavedState.barWidth = this.barWidth;
        wheelSavedState.barColor = this.barColor;
        wheelSavedState.rimWidth = this.rimWidth;
        wheelSavedState.rimColor = this.rimColor;
        wheelSavedState.circleRadius = this.circleRadius;
        wheelSavedState.linearProgress = this.linearProgress;
        wheelSavedState.fillRadius = this.fillRadius;
        return wheelSavedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        setupBounds(w2, h2);
        setupPaints();
        invalidate();
    }

    @Override // android.view.View
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0) {
            this.lastTimeAnimated = SystemClock.uptimeMillis();
        }
    }

    public void resetCount() {
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        invalidate();
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
        setupPaints();
        if (this.isSpinning) {
            return;
        }
        invalidate();
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        if (this.isSpinning) {
            return;
        }
        invalidate();
    }

    public void setCallback(ProgressCallback progressCallback) {
        this.callback = progressCallback;
        if (this.isSpinning) {
            return;
        }
        runCallback();
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        if (this.isSpinning) {
            return;
        }
        invalidate();
    }

    public void setInstantProgress(float progress) {
        if (this.isSpinning) {
            this.mProgress = 0.0f;
            this.isSpinning = false;
        }
        if (progress > 1.0f) {
            progress -= 1.0f;
        } else if (progress < 0.0f) {
            progress = 0.0f;
        }
        if (progress == this.mTargetProgress) {
            return;
        }
        float fMin = Math.min(progress * 360.0f, 360.0f);
        this.mTargetProgress = fMin;
        this.mProgress = fMin;
        this.lastTimeAnimated = SystemClock.uptimeMillis();
        invalidate();
    }

    public void setLinearProgress(boolean isLinear) {
        this.linearProgress = isLinear;
        if (this.isSpinning) {
            return;
        }
        invalidate();
    }

    public void setProgress(float progress) {
        if (this.isSpinning) {
            this.mProgress = 0.0f;
            this.isSpinning = false;
            runCallback();
        }
        if (progress > 1.0f) {
            progress -= 1.0f;
        } else if (progress < 0.0f) {
            progress = 0.0f;
        }
        float f2 = this.mTargetProgress;
        if (progress == f2) {
            return;
        }
        if (this.mProgress == f2) {
            this.lastTimeAnimated = SystemClock.uptimeMillis();
        }
        this.mTargetProgress = Math.min(progress * 360.0f, 360.0f);
        invalidate();
    }

    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;
        setupPaints();
        if (this.isSpinning) {
            return;
        }
        invalidate();
    }

    public void setRimWidth(int rimWidth) {
        this.rimWidth = rimWidth;
        if (this.isSpinning) {
            return;
        }
        invalidate();
    }

    public void setSpinSpeed(float spinSpeed) {
        this.spinSpeed = spinSpeed * 360.0f;
    }

    public void spin() {
        this.lastTimeAnimated = SystemClock.uptimeMillis();
        this.isSpinning = true;
        invalidate();
    }

    public void stopSpinning() {
        this.isSpinning = false;
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        invalidate();
    }

    private void runCallback() {
        if (this.callback != null) {
            this.callback.onProgressUpdate(Math.round((this.mProgress * 100.0f) / 360.0f) / 100.0f);
        }
    }

    public ProgressWheel(Context context) {
        super(context);
        this.barLength = 16;
        this.barMaxLength = 270;
        this.pauseGrowingTime = 200L;
        this.circleRadius = 28;
        this.barWidth = 4;
        this.rimWidth = 4;
        this.fillRadius = false;
        this.timeStartGrowing = 0.0d;
        this.barSpinCycleTime = 460.0d;
        this.barExtraLength = 0.0f;
        this.barGrowingFromFront = true;
        this.pausedTimeWithoutGrowing = 0L;
        this.barColor = -1442840576;
        this.rimColor = 16777215;
        this.barPaint = new Paint();
        this.rimPaint = new Paint();
        this.circleBounds = new RectF();
        this.spinSpeed = 230.0f;
        this.lastTimeAnimated = 0L;
        this.mProgress = 0.0f;
        this.mTargetProgress = 0.0f;
        this.isSpinning = false;
        setAnimationEnabled();
    }
}
