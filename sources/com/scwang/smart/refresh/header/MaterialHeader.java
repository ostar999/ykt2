package com.scwang.smart.refresh.header;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import com.scwang.smart.refresh.header.material.CircleImageView;
import com.scwang.smart.refresh.header.material.MaterialProgressDrawable;
import com.scwang.smart.refresh.header.material.R;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;
import com.scwang.smart.refresh.layout.util.SmartUtil;

/* loaded from: classes6.dex */
public class MaterialHeader extends SimpleComponent implements RefreshHeader {
    protected static final int CIRCLE_BG_LIGHT = -328966;

    @VisibleForTesting
    protected static final int CIRCLE_DIAMETER = 40;

    @VisibleForTesting
    protected static final int CIRCLE_DIAMETER_LARGE = 56;
    protected static final float MAX_PROGRESS_ANGLE = 0.8f;
    public static final int SIZE_DEFAULT = 1;
    public static final int SIZE_LARGE = 0;
    protected Paint mBezierPaint;
    protected Path mBezierPath;
    protected int mCircleDiameter;
    protected ImageView mCircleView;
    protected boolean mFinished;
    protected int mHeadHeight;
    protected MaterialProgressDrawable mProgress;
    protected boolean mScrollableWhenRefreshing;
    protected boolean mShowBezierWave;
    protected RefreshState mState;
    protected int mWaveHeight;

    /* renamed from: com.scwang.smart.refresh.header.MaterialHeader$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public MaterialHeader(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (this.mShowBezierWave) {
            this.mBezierPath.reset();
            this.mBezierPath.lineTo(0.0f, this.mHeadHeight);
            this.mBezierPath.quadTo(getMeasuredWidth() / 2.0f, this.mHeadHeight + (this.mWaveHeight * 1.9f), getMeasuredWidth(), this.mHeadHeight);
            this.mBezierPath.lineTo(getMeasuredWidth(), 0.0f);
            canvas.drawPath(this.mBezierPath, this.mBezierPaint);
        }
        super.dispatchDraw(canvas);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        ImageView imageView = this.mCircleView;
        this.mProgress.stop();
        imageView.animate().scaleX(0.0f).scaleY(0.0f);
        this.mFinished = true;
        return 0;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i2, int i3) {
        if (!this.mShowBezierWave) {
            refreshKernel.requestDefaultTranslationContentFor(this, false);
        }
        if (isInEditMode()) {
            int i4 = i2 / 2;
            this.mHeadHeight = i4;
            this.mWaveHeight = i4;
        }
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        if (getChildCount() == 0) {
            return;
        }
        ImageView imageView = this.mCircleView;
        int measuredWidth = getMeasuredWidth();
        int measuredWidth2 = imageView.getMeasuredWidth();
        int measuredHeight = imageView.getMeasuredHeight();
        if (!isInEditMode() || (i6 = this.mHeadHeight) <= 0) {
            int i7 = measuredWidth / 2;
            int i8 = measuredWidth2 / 2;
            imageView.layout(i7 - i8, -measuredHeight, i7 + i8, 0);
            return;
        }
        int i9 = i6 - (measuredHeight / 2);
        int i10 = measuredWidth / 2;
        int i11 = measuredWidth2 / 2;
        imageView.layout(i10 - i11, i9, i10 + i11, measuredHeight + i9);
        this.mProgress.showArrow(true);
        this.mProgress.setStartEndTrim(0.0f, MAX_PROGRESS_ANGLE);
        this.mProgress.setArrowScale(1.0f);
        imageView.setAlpha(1.0f);
        imageView.setVisibility(0);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.setMeasuredDimension(View.MeasureSpec.getSize(i2), View.MeasureSpec.getSize(i3));
        this.mCircleView.measure(View.MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCircleDiameter, 1073741824));
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onMoving(boolean z2, float f2, int i2, int i3, int i4) {
        RefreshState refreshState = this.mState;
        RefreshState refreshState2 = RefreshState.Refreshing;
        if (refreshState == refreshState2) {
            return;
        }
        if (this.mShowBezierWave) {
            this.mHeadHeight = Math.min(i2, i3);
            this.mWaveHeight = Math.max(0, i2 - i3);
            postInvalidate();
        }
        if (z2 || !(this.mProgress.isRunning() || this.mFinished)) {
            if (this.mState != refreshState2) {
                float f3 = i3;
                float fMax = (((float) Math.max(Math.min(1.0f, Math.abs((i2 * 1.0f) / f3)) - 0.4d, 0.0d)) * 5.0f) / 3.0f;
                double dMax = Math.max(0.0f, Math.min(Math.abs(i2) - i3, f3 * 2.0f) / f3) / 4.0f;
                float fPow = ((float) (dMax - Math.pow(dMax, 2.0d))) * 2.0f;
                float f4 = fMax * MAX_PROGRESS_ANGLE;
                this.mProgress.showArrow(true);
                this.mProgress.setStartEndTrim(0.0f, Math.min(MAX_PROGRESS_ANGLE, f4));
                this.mProgress.setArrowScale(Math.min(1.0f, fMax));
                this.mProgress.setProgressRotation((((fMax * 0.4f) - 0.25f) + (fPow * 2.0f)) * 0.5f);
            }
            ImageView imageView = this.mCircleView;
            float f5 = i2;
            imageView.setTranslationY(Math.min(f5, (f5 / 2.0f) + (this.mCircleDiameter / 2.0f)));
            imageView.setAlpha(Math.min(1.0f, (f5 * 4.0f) / this.mCircleDiameter));
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        this.mProgress.start();
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        ImageView imageView = this.mCircleView;
        this.mState = refreshState2;
        if (AnonymousClass1.$SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[refreshState2.ordinal()] != 4) {
            return;
        }
        this.mFinished = false;
        imageView.setVisibility(0);
        imageView.setTranslationY(0.0f);
        imageView.setScaleX(1.0f);
        imageView.setScaleY(1.0f);
    }

    public MaterialHeader setColorSchemeColors(@ColorInt int... iArr) {
        this.mProgress.setColorSchemeColors(iArr);
        return this;
    }

    public MaterialHeader setColorSchemeResources(@ColorRes int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = ContextCompat.getColor(context, iArr[i2]);
        }
        return setColorSchemeColors(iArr2);
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    @Deprecated
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (iArr.length > 0) {
            this.mBezierPaint.setColor(iArr[0]);
        }
    }

    public MaterialHeader setProgressBackgroundColorSchemeColor(@ColorInt int i2) {
        this.mCircleView.setBackgroundColor(i2);
        return this;
    }

    public MaterialHeader setProgressBackgroundColorSchemeResource(@ColorRes int i2) {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(getContext(), i2));
        return this;
    }

    public MaterialHeader setScrollableWhenRefreshing(boolean z2) {
        this.mScrollableWhenRefreshing = z2;
        return this;
    }

    public MaterialHeader setShowBezierWave(boolean z2) {
        this.mShowBezierWave = z2;
        return this;
    }

    public MaterialHeader setSize(int i2) {
        if (i2 != 0 && i2 != 1) {
            return this;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (i2 == 0) {
            this.mCircleDiameter = (int) (displayMetrics.density * 56.0f);
        } else {
            this.mCircleDiameter = (int) (displayMetrics.density * 40.0f);
        }
        this.mCircleView.setImageDrawable(null);
        this.mProgress.updateSizes(i2);
        this.mCircleView.setImageDrawable(this.mProgress);
        return this;
    }

    public MaterialHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mShowBezierWave = false;
        this.mScrollableWhenRefreshing = true;
        this.mSpinnerStyle = SpinnerStyle.MatchLayout;
        setMinimumHeight(SmartUtil.dp2px(100.0f));
        MaterialProgressDrawable materialProgressDrawable = new MaterialProgressDrawable(this);
        this.mProgress = materialProgressDrawable;
        materialProgressDrawable.setColorSchemeColors(-16737844, -48060, -10053376, -5609780, -30720);
        CircleImageView circleImageView = new CircleImageView(context, CIRCLE_BG_LIGHT);
        this.mCircleView = circleImageView;
        circleImageView.setImageDrawable(this.mProgress);
        this.mCircleView.setAlpha(0.0f);
        addView(this.mCircleView);
        this.mCircleDiameter = (int) (getResources().getDisplayMetrics().density * 40.0f);
        this.mBezierPath = new Path();
        Paint paint = new Paint();
        this.mBezierPaint = paint;
        paint.setAntiAlias(true);
        this.mBezierPaint.setStyle(Paint.Style.FILL);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialHeader);
        this.mShowBezierWave = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_srlShowBezierWave, this.mShowBezierWave);
        this.mScrollableWhenRefreshing = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_srlScrollableWhenRefreshing, this.mScrollableWhenRefreshing);
        this.mBezierPaint.setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.MaterialHeader_srlPrimaryColor, -15614977));
        int i2 = R.styleable.MaterialHeader_srlShadowRadius;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            this.mBezierPaint.setShadowLayer(typedArrayObtainStyledAttributes.getDimensionPixelOffset(i2, 0), 0.0f, 0.0f, typedArrayObtainStyledAttributes.getColor(R.styleable.MaterialHeader_mhShadowColor, -16777216));
            setLayerType(1, null);
        }
        this.mShowBezierWave = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_mhShowBezierWave, this.mShowBezierWave);
        this.mScrollableWhenRefreshing = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MaterialHeader_mhScrollableWhenRefreshing, this.mScrollableWhenRefreshing);
        int i3 = R.styleable.MaterialHeader_mhPrimaryColor;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            this.mBezierPaint.setColor(typedArrayObtainStyledAttributes.getColor(i3, -15614977));
        }
        int i4 = R.styleable.MaterialHeader_mhShadowRadius;
        if (typedArrayObtainStyledAttributes.hasValue(i4)) {
            this.mBezierPaint.setShadowLayer(typedArrayObtainStyledAttributes.getDimensionPixelOffset(i4, 0), 0.0f, 0.0f, typedArrayObtainStyledAttributes.getColor(R.styleable.MaterialHeader_mhShadowColor, -16777216));
            setLayerType(1, null);
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
