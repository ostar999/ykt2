package me.dkzwm.widget.srl.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import me.dkzwm.widget.srl.utils.PixelUtl;

/* loaded from: classes9.dex */
public class MaterialProgressDrawable extends Drawable implements Animatable {
    private static final int ANIMATION_DURATION = 1332;
    private static final int ARROW_HEIGHT = 5;
    private static final int ARROW_HEIGHT_LARGE = 6;
    private static final float ARROW_OFFSET_ANGLE = 5.0f;
    private static final int ARROW_WIDTH = 10;
    private static final int ARROW_WIDTH_LARGE = 12;
    private static final float CENTER_RADIUS = 8.75f;
    private static final float CENTER_RADIUS_LARGE = 12.5f;
    private static final int CIRCLE_DIAMETER = 40;
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float COLOR_START_DELAY_OFFSET = 0.75f;
    public static final int DEFAULT = 1;
    private static final float END_TRIM_START_DELAY_OFFSET = 0.5f;
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final float FULL_ROTATION = 1080.0f;
    private static final int KEY_SHADOW_COLOR = 503316480;
    public static final int LARGE = 0;
    private static final float MAX_PROGRESS_ARC = 0.8f;
    private static final float NUM_POINTS = 5.0f;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    private static final float STROKE_WIDTH = 2.5f;
    private static final float STROKE_WIDTH_LARGE = 3.0f;
    private static final float X_OFFSET = 0.0f;
    private static final float Y_OFFSET = 1.75f;
    private Animation mAnimation;
    private final ArrayList<Animation> mAnimators = new ArrayList<>();
    private boolean mFinishing;
    private double mHeight;
    private View mParent;
    private Resources mResources;
    private final Ring mRing;
    private float mRotation;
    private float mRotationCount;
    private ShapeDrawable mShadow;
    private double mWidth;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final int[] COLORS = {-16777216};

    public class OvalShadow extends OvalShape {
        private int mCircleDiameter;
        private RadialGradient mRadialGradient;
        private Paint mShadowPaint = new Paint();
        private int mShadowRadius;

        public OvalShadow(int i2, int i3) {
            this.mShadowRadius = i2;
            this.mCircleDiameter = i3;
            int i4 = this.mCircleDiameter;
            RadialGradient radialGradient = new RadialGradient(i4 / 2, i4 / 2, this.mShadowRadius, new int[]{MaterialProgressDrawable.FILL_SHADOW_COLOR, 0}, (float[]) null, Shader.TileMode.CLAMP);
            this.mRadialGradient = radialGradient;
            this.mShadowPaint.setShader(radialGradient);
        }

        @Override // android.graphics.drawable.shapes.OvalShape, android.graphics.drawable.shapes.RectShape, android.graphics.drawable.shapes.Shape
        public void draw(Canvas canvas, Paint paint) {
            float fWidth = MaterialProgressDrawable.this.getBounds().width() / 2;
            float fHeight = MaterialProgressDrawable.this.getBounds().height() / 2;
            canvas.drawCircle(fWidth, fHeight, (this.mCircleDiameter / 2) + this.mShadowRadius, this.mShadowPaint);
            canvas.drawCircle(fWidth, fHeight, this.mCircleDiameter / 2, paint);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressDrawableSize {
    }

    public static class Ring {
        private int mAlpha;
        private Path mArrow;
        private int mArrowHeight;
        private final Paint mArrowPaint;
        private float mArrowScale;
        private int mArrowWidth;
        private int mBackgroundColor;
        private final Drawable.Callback mCallback;
        private final Paint mCirclePaint;
        private int mColorIndex;
        private int[] mColors;
        private int mCurrentColor;
        private float mEndTrim;
        private final Paint mPaint;
        private double mRingCenterRadius;
        private float mRotation;
        private boolean mShowArrow;
        private float mStartTrim;
        private float mStartingEndTrim;
        private float mStartingRotation;
        private float mStartingStartTrim;
        private float mStrokeInset;
        private float mStrokeWidth;
        private final RectF mTempBounds = new RectF();

        public Ring(Drawable.Callback callback) {
            Paint paint = new Paint();
            this.mPaint = paint;
            Paint paint2 = new Paint();
            this.mArrowPaint = paint2;
            this.mCirclePaint = new Paint(1);
            this.mStartTrim = 0.0f;
            this.mEndTrim = 0.0f;
            this.mRotation = 0.0f;
            this.mStrokeWidth = 5.0f;
            this.mStrokeInset = MaterialProgressDrawable.STROKE_WIDTH;
            this.mCallback = callback;
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
                canvas.rotate((f2 + f3) - 5.0f, rect.exactCenterX(), rect.exactCenterY());
                canvas.drawPath(this.mArrow, this.mArrowPaint);
            }
        }

        private int getNextColorIndex() {
            return (this.mColorIndex + 1) % this.mColors.length;
        }

        private void invalidateSelf() {
            this.mCallback.invalidateDrawable(null);
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
            this.mPaint.setColor(this.mCurrentColor);
            canvas.drawArc(rectF, f5, f6, false, this.mPaint);
            drawTriangle(canvas, f5, f6, rect);
            if (this.mAlpha < 255) {
                this.mCirclePaint.setColor(this.mBackgroundColor);
                this.mCirclePaint.setAlpha(255 - this.mAlpha);
                canvas.drawCircle(rect.exactCenterX(), rect.exactCenterY(), rect.width() / 2, this.mCirclePaint);
            }
        }

        public int getAlpha() {
            return this.mAlpha;
        }

        public double getCenterRadius() {
            return this.mRingCenterRadius;
        }

        public float getEndTrim() {
            return this.mEndTrim;
        }

        public int getNextColor() {
            return this.mColors[getNextColorIndex()];
        }

        public float getStartTrim() {
            return this.mStartTrim;
        }

        public int getStartingColor() {
            return this.mColors[this.mColorIndex];
        }

        public float getStartingEndTrim() {
            return this.mStartingEndTrim;
        }

        public float getStartingRotation() {
            return this.mStartingRotation;
        }

        public float getStartingStartTrim() {
            return this.mStartingStartTrim;
        }

        public float getStrokeWidth() {
            return this.mStrokeWidth;
        }

        public void goToNextColor() {
            setColorIndex(getNextColorIndex());
        }

        public void resetOriginals() {
            this.mStartingStartTrim = 0.0f;
            this.mStartingEndTrim = 0.0f;
            this.mStartingRotation = 0.0f;
            setStartTrim(0.0f);
            setEndTrim(0.0f);
            setRotation(0.0f);
        }

        public void setAlpha(int i2) {
            this.mAlpha = i2;
        }

        public void setArrowDimensions(float f2, float f3) {
            this.mArrowWidth = (int) f2;
            this.mArrowHeight = (int) f3;
        }

        public void setArrowScale(float f2) {
            if (f2 != this.mArrowScale) {
                this.mArrowScale = f2;
                invalidateSelf();
            }
        }

        public void setBackgroundColor(int i2) {
            this.mBackgroundColor = i2;
        }

        public void setCenterRadius(double d3) {
            this.mRingCenterRadius = d3;
        }

        public void setColor(int i2) {
            this.mCurrentColor = i2;
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        public void setColorIndex(int i2) {
            this.mColorIndex = i2;
            this.mCurrentColor = this.mColors[i2];
        }

        public void setColors(@NonNull int[] iArr) {
            this.mColors = iArr;
            setColorIndex(0);
        }

        public void setEndTrim(float f2) {
            this.mEndTrim = f2;
            invalidateSelf();
        }

        public void setInsets(int i2, int i3) {
            float fMin = Math.min(i2, i3);
            double d3 = this.mRingCenterRadius;
            this.mStrokeInset = (float) ((d3 <= 0.0d || fMin < 0.0f) ? Math.ceil(this.mStrokeWidth / 2.0f) : (fMin / 2.0f) - d3);
        }

        public void setRotation(float f2) {
            this.mRotation = f2;
            invalidateSelf();
        }

        public void setShowArrow(boolean z2) {
            if (this.mShowArrow != z2) {
                this.mShowArrow = z2;
                invalidateSelf();
            }
        }

        public void setStartTrim(float f2) {
            this.mStartTrim = f2;
            invalidateSelf();
        }

        public void setStrokeWidth(float f2) {
            this.mStrokeWidth = f2;
            this.mPaint.setStrokeWidth(f2);
            invalidateSelf();
        }

        public void storeOriginals() {
            this.mStartingStartTrim = this.mStartTrim;
            this.mStartingEndTrim = this.mEndTrim;
            this.mStartingRotation = this.mRotation;
        }
    }

    public MaterialProgressDrawable(Context context, View view) {
        this.mParent = view;
        this.mResources = context.getResources();
        Ring ring = new Ring(new Drawable.Callback() { // from class: me.dkzwm.widget.srl.drawable.MaterialProgressDrawable.1
            @Override // android.graphics.drawable.Drawable.Callback
            public void invalidateDrawable(Drawable drawable) {
                MaterialProgressDrawable.this.invalidateSelf();
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
                MaterialProgressDrawable.this.scheduleSelf(runnable, j2);
            }

            @Override // android.graphics.drawable.Drawable.Callback
            public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                MaterialProgressDrawable.this.unscheduleSelf(runnable);
            }
        });
        this.mRing = ring;
        ring.setColors(COLORS);
        updateSizes(1);
        setupAnimators();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyFinishTranslation(float f2, Ring ring) {
        updateRingColor(f2, ring);
        float fFloor = (float) (Math.floor(ring.getStartingRotation() / MAX_PROGRESS_ARC) + 1.0d);
        ring.setStartTrim(ring.getStartingStartTrim() + (((ring.getStartingEndTrim() - ((float) Math.toRadians(ring.getStrokeWidth() / (ring.getCenterRadius() * 6.283185307179586d)))) - ring.getStartingStartTrim()) * f2));
        ring.setEndTrim(ring.getStartingEndTrim());
        ring.setRotation(ring.getStartingRotation() + ((fFloor - ring.getStartingRotation()) * f2));
    }

    private void setSizeParameters(double d3, double d4, double d5, double d6, float f2, float f3) {
        Ring ring = this.mRing;
        float f4 = this.mResources.getDisplayMetrics().density;
        double d7 = f4;
        this.mWidth = d3 * d7;
        this.mHeight = d4 * d7;
        ring.setStrokeWidth(((float) d6) * f4);
        ring.setCenterRadius(d5 * d7);
        ring.setColorIndex(0);
        ring.setArrowDimensions(f2 * f4, f3 * f4);
        ring.setInsets((int) this.mWidth, (int) this.mHeight);
        setUp(this.mWidth);
    }

    private void setUp(double d3) {
        int iDp2px = PixelUtl.dp2px(this.mParent.getContext(), 1.75f);
        int iDp2px2 = PixelUtl.dp2px(this.mParent.getContext(), 0.0f);
        int iDp2px3 = PixelUtl.dp2px(this.mParent.getContext(), SHADOW_RADIUS);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShadow(iDp2px3, (int) d3));
        this.mShadow = shapeDrawable;
        this.mParent.setLayerType(1, shapeDrawable.getPaint());
        this.mShadow.getPaint().setShadowLayer(iDp2px3, iDp2px2, iDp2px, KEY_SHADOW_COLOR);
    }

    private void setupAnimators() {
        final Ring ring = this.mRing;
        Animation animation = new Animation() { // from class: me.dkzwm.widget.srl.drawable.MaterialProgressDrawable.2
            @Override // android.view.animation.Animation
            public void applyTransformation(float f2, Transformation transformation) {
                if (MaterialProgressDrawable.this.mFinishing) {
                    MaterialProgressDrawable.this.applyFinishTranslation(f2, ring);
                    return;
                }
                float radians = (float) Math.toRadians(ring.getStrokeWidth() / (ring.getCenterRadius() * 6.283185307179586d));
                float startingEndTrim = ring.getStartingEndTrim();
                float startingStartTrim = ring.getStartingStartTrim();
                float startingRotation = ring.getStartingRotation();
                MaterialProgressDrawable.this.updateRingColor(f2, ring);
                if (f2 <= 0.5f) {
                    ring.setStartTrim(startingStartTrim + ((MaterialProgressDrawable.MAX_PROGRESS_ARC - radians) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation(f2 / 0.5f)));
                }
                if (f2 > 0.5f) {
                    ring.setEndTrim(startingEndTrim + ((MaterialProgressDrawable.MAX_PROGRESS_ARC - radians) * MaterialProgressDrawable.MATERIAL_INTERPOLATOR.getInterpolation((f2 - 0.5f) / 0.5f)));
                }
                ring.setRotation(startingRotation + (0.25f * f2));
                MaterialProgressDrawable materialProgressDrawable = MaterialProgressDrawable.this;
                materialProgressDrawable.mRotation = (f2 * 216.0f) + ((materialProgressDrawable.mRotationCount / 5.0f) * MaterialProgressDrawable.FULL_ROTATION);
                MaterialProgressDrawable.this.invalidateSelf();
            }
        };
        animation.setRepeatCount(-1);
        animation.setRepeatMode(1);
        animation.setInterpolator(LINEAR_INTERPOLATOR);
        animation.setAnimationListener(new Animation.AnimationListener() { // from class: me.dkzwm.widget.srl.drawable.MaterialProgressDrawable.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation2) {
                ring.storeOriginals();
                ring.goToNextColor();
                Ring ring2 = ring;
                ring2.setStartTrim(ring2.getEndTrim());
                if (!MaterialProgressDrawable.this.mFinishing) {
                    MaterialProgressDrawable materialProgressDrawable = MaterialProgressDrawable.this;
                    materialProgressDrawable.mRotationCount = (materialProgressDrawable.mRotationCount + 1.0f) % 5.0f;
                } else {
                    MaterialProgressDrawable.this.mFinishing = false;
                    animation2.setDuration(1332L);
                    ring.setShowArrow(false);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation2) {
                MaterialProgressDrawable.this.mRotationCount = 0.0f;
            }
        });
        this.mAnimation = animation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRingColor(float f2, Ring ring) {
        if (f2 > 0.75f) {
            float f3 = (f2 - 0.75f) / 0.25f;
            int startingColor = ring.getStartingColor();
            int nextColor = ring.getNextColor();
            ring.setColor(((((startingColor >> 24) & 255) + ((int) ((((nextColor >> 24) & 255) - r2) * f3))) << 24) | ((((startingColor >> 16) & 255) + ((int) ((((nextColor >> 16) & 255) - r3) * f3))) << 16) | ((((startingColor >> 8) & 255) + ((int) ((((nextColor >> 8) & 255) - r4) * f3))) << 8) | ((startingColor & 255) + ((int) (f3 * ((nextColor & 255) - r0)))));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        ShapeDrawable shapeDrawable = this.mShadow;
        if (shapeDrawable != null) {
            shapeDrawable.getPaint().setColor(this.mRing.mBackgroundColor);
            this.mShadow.draw(canvas);
        }
        Rect bounds = getBounds();
        int iSave = canvas.save();
        canvas.rotate(this.mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        this.mRing.draw(canvas, bounds);
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mRing.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return (int) this.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return (int) this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public float getRotation() {
        return this.mRotation;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        ArrayList<Animation> arrayList = this.mAnimators;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            Animation animation = arrayList.get(i2);
            if (animation.hasStarted() && !animation.hasEnded()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.mRing.setAlpha(i2);
    }

    public void setArrowScale(float f2) {
        this.mRing.setArrowScale(f2);
    }

    public void setBackgroundColor(int i2) {
        this.mRing.setBackgroundColor(i2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mRing.setColorFilter(colorFilter);
    }

    public void setColorSchemeColors(int... iArr) {
        this.mRing.setColors(iArr);
        this.mRing.setColorIndex(0);
    }

    public void setProgressRotation(float f2) {
        this.mRing.setRotation(f2);
    }

    public void setStartEndTrim(float f2, float f3) {
        this.mRing.setStartTrim(f2);
        this.mRing.setEndTrim(f3);
    }

    public void showArrow(boolean z2) {
        this.mRing.setShowArrow(z2);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.mAnimation.reset();
        this.mRing.storeOriginals();
        if (this.mRing.getEndTrim() != this.mRing.getStartTrim()) {
            this.mFinishing = true;
            this.mAnimation.setDuration(666L);
            this.mParent.startAnimation(this.mAnimation);
        } else {
            this.mRing.setColorIndex(0);
            this.mRing.resetOriginals();
            this.mAnimation.setDuration(1332L);
            this.mParent.startAnimation(this.mAnimation);
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.mParent.clearAnimation();
        this.mRotation = 0.0f;
        this.mRing.setShowArrow(false);
        this.mRing.setColorIndex(0);
        this.mRing.resetOriginals();
        invalidateSelf();
    }

    public void updateSizes(int i2) {
        if (i2 == 0) {
            setSizeParameters(56.0d, 56.0d, 12.5d, 3.0d, 12.0f, 6.0f);
        } else {
            setSizeParameters(40.0d, 40.0d, 8.75d, 2.5d, 10.0f, 5.0f);
        }
    }
}
