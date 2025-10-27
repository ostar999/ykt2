package com.scwang.smart.refresh.header.material;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class MaterialProgressDrawable extends Drawable implements Animatable {
    private static final int ANIMATION_DURATION = 1332;
    private static final byte ARROW_HEIGHT = 5;
    private static final byte ARROW_HEIGHT_LARGE = 6;
    private static final float ARROW_OFFSET_ANGLE = 5.0f;
    private static final byte ARROW_WIDTH = 10;
    private static final byte ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 8.75f;
    private static final float CENTER_RADIUS_LARGE = 12.5f;
    private static final byte CIRCLE_DIAMETER = 40;
    private static final byte CIRCLE_DIAMETER_LARGE = 56;
    private static final float COLOR_START_DELAY_OFFSET = 0.75f;
    public static final byte DEFAULT = 1;
    private static final float END_TRIM_START_DELAY_OFFSET = 0.5f;
    private static final float FULL_ROTATION = 1080.0f;
    public static final byte LARGE = 0;
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final byte NUM_POINTS = 5;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;
    private Animation mAnimation;
    boolean mFinishing;
    private float mHeight;
    private View mParent;
    private float mRotation;
    float mRotationCount;
    private float mWidth;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int[] COLORS = {-16777216};
    private final List<Animation> mAnimators = new ArrayList();
    private final Ring mRing = new Ring();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressDrawableSize {
    }

    public class Ring {
        Path mArrow;
        int mArrowHeight;
        final Paint mArrowPaint;
        float mArrowScale;
        int mArrowWidth;
        int mColorIndex;
        int[] mColors;
        int mCurrentColor;
        float mEndTrim;
        final Paint mPaint;
        double mRingCenterRadius;
        float mRotation;
        boolean mShowArrow;
        float mStartTrim;
        float mStartingEndTrim;
        float mStartingRotation;
        float mStartingStartTrim;
        float mStrokeInset;
        float mStrokeWidth;
        final RectF mTempBounds = new RectF();

        public Ring() {
            Paint paint = new Paint();
            this.mPaint = paint;
            Paint paint2 = new Paint();
            this.mArrowPaint = paint2;
            this.mStartTrim = 0.0f;
            this.mEndTrim = 0.0f;
            this.mRotation = 0.0f;
            this.mStrokeWidth = MaterialProgressDrawable.ARROW_OFFSET_ANGLE;
            this.mStrokeInset = MaterialProgressDrawable.STROKE_WIDTH;
            paint.setStrokeCap(Paint.Cap.SQUARE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint2.setStyle(Paint.Style.FILL);
            paint2.setAntiAlias(true);
        }

        private void drawTriangle(Canvas canvas, float f2, float f3, Rect rect) {
            if (this.mShowArrow) {
                Path path = this.mArrow;
                if (path == null) {
                    Path path2 = new Path();
                    this.mArrow = path2;
                    path2.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                float f4 = (((int) this.mStrokeInset) / 2) * this.mArrowScale;
                float fCos = (float) ((this.mRingCenterRadius * Math.cos(0.0d)) + rect.exactCenterX());
                float fSin = (float) ((this.mRingCenterRadius * Math.sin(0.0d)) + rect.exactCenterY());
                this.mArrow.moveTo(0.0f, 0.0f);
                this.mArrow.lineTo(this.mArrowWidth * this.mArrowScale, 0.0f);
                Path path3 = this.mArrow;
                float f5 = this.mArrowWidth;
                float f6 = this.mArrowScale;
                path3.lineTo((f5 * f6) / 2.0f, this.mArrowHeight * f6);
                this.mArrow.offset(fCos - f4, fSin);
                this.mArrow.close();
                this.mArrowPaint.setColor(this.mCurrentColor);
                canvas.rotate((f2 + f3) - MaterialProgressDrawable.ARROW_OFFSET_ANGLE, rect.exactCenterX(), rect.exactCenterY());
                canvas.drawPath(this.mArrow, this.mArrowPaint);
            }
        }

        private int getNextColorIndex() {
            return (this.mColorIndex + 1) % this.mColors.length;
        }

        public void draw(Canvas canvas, Rect rect) {
            RectF rectF = this.mTempBounds;
            rectF.set(rect);
            float f2 = this.mStrokeInset;
            rectF.inset(f2, f2);
            float f3 = this.mStartTrim;
            float f4 = this.mRotation;
            float f5 = (f3 + f4) * 360.0f;
            float f6 = ((this.mEndTrim + f4) * 360.0f) - f5;
            if (f6 != 0.0f) {
                this.mPaint.setColor(this.mCurrentColor);
                canvas.drawArc(rectF, f5, f6, false, this.mPaint);
            }
            drawTriangle(canvas, f5, f6, rect);
        }

        public int getNextColor() {
            return this.mColors[getNextColorIndex()];
        }

        public int getStartingColor() {
            return this.mColors[this.mColorIndex];
        }

        public void goToNextColor() {
            setColorIndex(getNextColorIndex());
        }

        public void resetOriginals() {
            this.mStartingStartTrim = 0.0f;
            this.mStartingEndTrim = 0.0f;
            this.mStartingRotation = 0.0f;
            this.mStartTrim = 0.0f;
            this.mEndTrim = 0.0f;
            this.mRotation = 0.0f;
        }

        public void setColorIndex(int i2) {
            this.mColorIndex = i2;
            this.mCurrentColor = this.mColors[i2];
        }

        public void setInsets(int i2, int i3) {
            float fMin = Math.min(i2, i3);
            double d3 = this.mRingCenterRadius;
            this.mStrokeInset = (float) ((d3 <= 0.0d || fMin < 0.0f) ? Math.ceil(this.mStrokeWidth / 2.0f) : (fMin / 2.0f) - d3);
        }

        public void storeOriginals() {
            this.mStartingStartTrim = this.mStartTrim;
            this.mStartingEndTrim = this.mEndTrim;
            this.mStartingRotation = this.mRotation;
        }
    }

    public MaterialProgressDrawable(View view) {
        this.mParent = view;
        setColorSchemeColors(COLORS);
        updateSizes(1);
        setupAnimators();
    }

    private int evaluateColorChange(float f2, int i2, int i3) {
        return ((((i2 >> 24) & 255) + ((int) ((((i3 >> 24) & 255) - r0) * f2))) << 24) | ((((i2 >> 16) & 255) + ((int) ((((i3 >> 16) & 255) - r1) * f2))) << 16) | ((((i2 >> 8) & 255) + ((int) ((((i3 >> 8) & 255) - r2) * f2))) << 8) | ((i2 & 255) + ((int) (f2 * ((i3 & 255) - r8))));
    }

    private void setSizeParameters(int i2, int i3, float f2, float f3, float f4, float f5) {
        float f6 = Resources.getSystem().getDisplayMetrics().density;
        this.mWidth = i2 * f6;
        this.mHeight = i3 * f6;
        this.mRing.setColorIndex(0);
        float f7 = f3 * f6;
        this.mRing.mPaint.setStrokeWidth(f7);
        Ring ring = this.mRing;
        ring.mStrokeWidth = f7;
        ring.mRingCenterRadius = f2 * f6;
        ring.mArrowWidth = (int) (f4 * f6);
        ring.mArrowHeight = (int) (f5 * f6);
        ring.setInsets((int) this.mWidth, (int) this.mHeight);
        invalidateSelf();
    }

    private void setupAnimators() {
        final Ring ring = this.mRing;
        Animation animation = new Animation() { // from class: com.scwang.smart.refresh.header.material.MaterialProgressDrawable.1
            @Override // android.view.animation.Animation
            public void applyTransformation(float f2, Transformation transformation) {
                MaterialProgressDrawable materialProgressDrawable = MaterialProgressDrawable.this;
                if (materialProgressDrawable.mFinishing) {
                    materialProgressDrawable.applyFinishTranslation(f2, ring);
                    return;
                }
                float minProgressArc = materialProgressDrawable.getMinProgressArc(ring);
                Ring ring2 = ring;
                float f3 = ring2.mStartingEndTrim;
                float f4 = ring2.mStartingStartTrim;
                float f5 = ring2.mStartingRotation;
                MaterialProgressDrawable.this.updateRingColor(f2, ring2);
                if (f2 <= 0.5f) {
                    ring.mStartTrim = f4 + ((MaterialProgressDrawable.MAX_PROGRESS_ARC - minProgressArc) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(f2 / 0.5f));
                }
                if (f2 > 0.5f) {
                    float f6 = MaterialProgressDrawable.MAX_PROGRESS_ARC - minProgressArc;
                    ring.mEndTrim = f3 + (f6 * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation((f2 - 0.5f) / 0.5f));
                }
                MaterialProgressDrawable.this.setProgressRotation(f5 + (0.25f * f2));
                MaterialProgressDrawable materialProgressDrawable2 = MaterialProgressDrawable.this;
                materialProgressDrawable2.setRotation((f2 * 216.0f) + ((materialProgressDrawable2.mRotationCount / MaterialProgressDrawable.ARROW_OFFSET_ANGLE) * MaterialProgressDrawable.FULL_ROTATION));
            }
        };
        animation.setRepeatCount(-1);
        animation.setRepeatMode(1);
        animation.setInterpolator(LINEAR_INTERPOLATOR);
        animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.scwang.smart.refresh.header.material.MaterialProgressDrawable.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation2) {
                ring.storeOriginals();
                ring.goToNextColor();
                Ring ring2 = ring;
                ring2.mStartTrim = ring2.mEndTrim;
                MaterialProgressDrawable materialProgressDrawable = MaterialProgressDrawable.this;
                if (!materialProgressDrawable.mFinishing) {
                    materialProgressDrawable.mRotationCount = (materialProgressDrawable.mRotationCount + 1.0f) % MaterialProgressDrawable.ARROW_OFFSET_ANGLE;
                    return;
                }
                materialProgressDrawable.mFinishing = false;
                animation2.setDuration(1332L);
                MaterialProgressDrawable.this.showArrow(false);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation2) {
                MaterialProgressDrawable.this.mRotationCount = 0.0f;
            }
        });
        this.mAnimation = animation;
    }

    public void applyFinishTranslation(float f2, Ring ring) {
        updateRingColor(f2, ring);
        float fFloor = (float) (Math.floor(ring.mStartingRotation / MAX_PROGRESS_ARC) + 1.0d);
        float minProgressArc = getMinProgressArc(ring);
        float f3 = ring.mStartingStartTrim;
        float f4 = ring.mStartingEndTrim;
        setStartEndTrim(f3 + (((f4 - minProgressArc) - f3) * f2), f4);
        float f5 = ring.mStartingRotation;
        setProgressRotation(f5 + ((fFloor - f5) * f2));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int iSave = canvas.save();
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        this.mRing.draw(canvas, bounds);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) this.mWidth;
    }

    public float getMinProgressArc(Ring ring) {
        return (float) Math.toRadians(ring.mStrokeWidth / (ring.mRingCenterRadius * 6.283185307179586d));
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        List<Animation> list = this.mAnimators;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Animation animation = list.get(i2);
            if (animation.hasStarted() && !animation.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
    }

    public void setArrowScale(float f2) {
        Ring ring = this.mRing;
        if (ring.mArrowScale != f2) {
            ring.mArrowScale = f2;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mRing.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setColorSchemeColors(@ColorInt int... iArr) {
        Ring ring = this.mRing;
        ring.mColors = iArr;
        ring.setColorIndex(0);
    }

    public void setProgressRotation(float f2) {
        this.mRing.mRotation = f2;
        invalidateSelf();
    }

    public void setRotation(float f2) {
        this.mRotation = f2;
        invalidateSelf();
    }

    public void setStartEndTrim(float f2, float f3) {
        Ring ring = this.mRing;
        ring.mStartTrim = f2;
        ring.mEndTrim = f3;
        invalidateSelf();
    }

    public void showArrow(boolean z2) {
        Ring ring = this.mRing;
        if (ring.mShowArrow != z2) {
            ring.mShowArrow = z2;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.mAnimation.reset();
        this.mRing.storeOriginals();
        Ring ring = this.mRing;
        if (ring.mEndTrim != ring.mStartTrim) {
            this.mFinishing = true;
            this.mAnimation.setDuration(666L);
            this.mParent.startAnimation(this.mAnimation);
        } else {
            ring.setColorIndex(0);
            this.mRing.resetOriginals();
            this.mAnimation.setDuration(1332L);
            this.mParent.startAnimation(this.mAnimation);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mParent.clearAnimation();
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
        showArrow(false);
        setRotation(0.0f);
    }

    public void updateRingColor(float f2, Ring ring) {
        if (f2 > 0.75f) {
            ring.mCurrentColor = evaluateColorChange((f2 - 0.75f) / 0.25f, ring.getStartingColor(), ring.getNextColor());
        }
    }

    public void updateSizes(int i2) {
        if (i2 == 0) {
            setSizeParameters(56, 56, CENTER_RADIUS_LARGE, 3.0f, 12.0f, 6.0f);
        } else {
            setSizeParameters(40, 40, CENTER_RADIUS, STROKE_WIDTH, 10.0f, ARROW_OFFSET_ANGLE);
        }
    }
}
