package cn.lightsky.infiniteindicator.indicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.core.view.ViewConfigurationCompat;
import androidx.viewpager.widget.ViewPager;
import cn.lightsky.infiniteindicator.R;
import cn.lightsky.infiniteindicator.recycle.RecyleAdapter;

/* loaded from: classes.dex */
public class CircleIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private boolean mCentered;
    private int mCurrentPage;
    private boolean mIsDragging;
    private float mLastMotionX;
    private int mOrientation;
    private float mPageOffset;
    private final Paint mPaintFill;
    private final Paint mPaintPageFill;
    private final Paint mPaintStroke;
    private float mRadius;
    private int mRealCount;
    private RecyleAdapter mRecyleAdapter;
    private int mScrollState;
    private boolean mSnap;
    private int mSnapPage;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public CircleIndicator(Context context) {
        this(context, null);
    }

    private int measureLong(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824 || this.mViewPager == null) {
            return size;
        }
        int realCount = this.mRecyleAdapter.getRealCount();
        float paddingLeft = getPaddingLeft() + getPaddingRight();
        float f2 = this.mRadius;
        int i3 = (int) (paddingLeft + (realCount * 2 * f2) + ((realCount - 1) * f2) + 1.0f);
        return mode == Integer.MIN_VALUE ? Math.min(i3, size) : i3;
    }

    private int measureShort(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = (int) ((this.mRadius * 2.0f) + getPaddingTop() + getPaddingBottom() + 1.0f);
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    public int getFillColor() {
        return this.mPaintFill.getColor();
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getPageColor() {
        return this.mPaintPageFill.getColor();
    }

    public float getRadius() {
        return this.mRadius;
    }

    public int getStrokeColor() {
        return this.mPaintStroke.getColor();
    }

    public float getStrokeWidth() {
        return this.mPaintStroke.getStrokeWidth();
    }

    public boolean isCentered() {
        return this.mCentered;
    }

    public boolean isSnap() {
        return this.mSnap;
    }

    @Override // cn.lightsky.infiniteindicator.indicator.PageIndicator
    public void notifyDataSetChanged() {
        this.mCurrentPage = 0;
        invalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int realCount;
        int height;
        int paddingTop;
        int paddingBottom;
        int paddingLeft;
        float f2;
        float f3;
        super.onDraw(canvas);
        RecyleAdapter recyleAdapter = this.mRecyleAdapter;
        if (recyleAdapter != null && (realCount = recyleAdapter.getRealCount()) > 1) {
            if (this.mOrientation == 0) {
                height = getWidth();
                paddingTop = getPaddingLeft();
                paddingBottom = getPaddingRight();
                paddingLeft = getPaddingTop();
            } else {
                height = getHeight();
                paddingTop = getPaddingTop();
                paddingBottom = getPaddingBottom();
                paddingLeft = getPaddingLeft();
            }
            float strokeWidth = this.mRadius;
            float f4 = 3.0f * strokeWidth;
            float f5 = paddingLeft + strokeWidth;
            float f6 = paddingTop + strokeWidth;
            if (this.mCentered) {
                f6 += (((height - paddingTop) - paddingBottom) / 2.0f) - ((realCount * f4) / 2.0f);
            }
            if (this.mPaintStroke.getStrokeWidth() > 0.0f) {
                strokeWidth -= this.mPaintStroke.getStrokeWidth() / 2.0f;
            }
            for (int i2 = 0; i2 < realCount; i2++) {
                float f7 = (i2 * f4) + f6;
                if (this.mOrientation == 0) {
                    f3 = f5;
                } else {
                    f3 = f7;
                    f7 = f5;
                }
                if (this.mPaintPageFill.getAlpha() > 0) {
                    canvas.drawCircle(f7, f3, strokeWidth, this.mPaintPageFill);
                }
                float f8 = this.mRadius;
                if (strokeWidth != f8) {
                    canvas.drawCircle(f7, f3, f8, this.mPaintStroke);
                }
            }
            float f9 = (this.mSnapPage % realCount) * f4;
            if (this.mOrientation == 0) {
                float f10 = f6 + f9;
                f2 = f5;
                f5 = f10;
            } else {
                f2 = f6 + f9;
            }
            canvas.drawCircle(f5, f2, this.mRadius, this.mPaintFill);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        if (this.mOrientation == 0) {
            setMeasuredDimension(measureLong(i2), measureShort(i3));
        } else {
            setMeasuredDimension(measureShort(i2), measureLong(i3));
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i2) {
        this.mScrollState = i2;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i2, float f2, int i3) {
        this.mCurrentPage = i2;
        this.mPageOffset = f2;
        invalidate();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i2) {
        if (this.mSnap || this.mScrollState == 0) {
            this.mCurrentPage = i2;
            this.mSnapPage = i2;
            invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0098  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r10) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.lightsky.infiniteindicator.indicator.CircleIndicator.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setCentered(boolean z2) {
        this.mCentered = z2;
        invalidate();
    }

    @Override // cn.lightsky.infiniteindicator.indicator.PageIndicator
    public void setCurrentItem(int i2) {
        this.mCurrentPage = i2;
        invalidate();
    }

    public void setFillColor(int i2) {
        this.mPaintFill.setColor(i2);
        invalidate();
    }

    public void setOrientation(int i2) {
        if (i2 != 0 && i2 != 1) {
            throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
        }
        this.mOrientation = i2;
        requestLayout();
    }

    public void setPageColor(int i2) {
        this.mPaintPageFill.setColor(i2);
        invalidate();
    }

    public void setRadius(float f2) {
        this.mRadius = f2;
        invalidate();
    }

    public void setRealViewCount(int i2) {
        this.mRealCount = i2;
    }

    public void setSnap(boolean z2) {
        this.mSnap = z2;
        invalidate();
    }

    public void setStrokeColor(int i2) {
        this.mPaintStroke.setColor(i2);
        invalidate();
    }

    public void setStrokeWidth(float f2) {
        this.mPaintStroke.setStrokeWidth(f2);
        invalidate();
    }

    @Override // cn.lightsky.infiniteindicator.indicator.PageIndicator
    public void setViewPager(ViewPager viewPager, int i2) {
        if (this.mViewPager == viewPager) {
            return;
        }
        this.mViewPager = viewPager;
        this.mRecyleAdapter = (RecyleAdapter) viewPager.getAdapter();
    }

    public CircleIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiCirclePageIndicatorStyle);
    }

    public CircleIndicator(Context context, AttributeSet attributeSet, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i2);
        Paint paint = new Paint(1);
        this.mPaintPageFill = paint;
        Paint paint2 = new Paint(1);
        this.mPaintStroke = paint2;
        Paint paint3 = new Paint(1);
        this.mPaintFill = paint3;
        this.mSnap = true;
        this.mLastMotionX = -1.0f;
        this.mActivePointerId = -1;
        if (isInEditMode()) {
            return;
        }
        Resources resources = getResources();
        int color = resources.getColor(R.color.default_circle_indicator_page_color);
        int color2 = resources.getColor(R.color.default_circle_indicator_fill_color);
        int integer = resources.getInteger(R.integer.default_circle_indicator_orientation);
        int color3 = resources.getColor(R.color.default_circle_indicator_stroke_color);
        float dimension = resources.getDimension(R.dimen.default_circle_indicator_stroke_width);
        float dimension2 = resources.getDimension(R.dimen.default_circle_indicator_radius);
        boolean z2 = resources.getBoolean(R.bool.default_circle_indicator_centered);
        resources.getBoolean(R.bool.default_circle_indicator_snap);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CirclePageIndicator, i2, 0);
        this.mCentered = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CirclePageIndicator_centered, z2);
        this.mOrientation = typedArrayObtainStyledAttributes.getInt(R.styleable.CirclePageIndicator_android_orientation, integer);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_pageColor, color));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_strokeColor, color3));
        paint2.setStrokeWidth(typedArrayObtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_strokeWidth, dimension));
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(typedArrayObtainStyledAttributes.getColor(R.styleable.CirclePageIndicator_fillColor, color2));
        this.mRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.CirclePageIndicator_radius, dimension2);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.CirclePageIndicator_android_background);
        if (drawable != null) {
            setBackgroundDrawable(drawable);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
    }
}
