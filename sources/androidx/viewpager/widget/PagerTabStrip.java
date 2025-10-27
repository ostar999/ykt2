package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/* loaded from: classes.dex */
public class PagerTabStrip extends PagerTitleStrip {
    private static final int FULL_UNDERLINE_HEIGHT = 1;
    private static final int INDICATOR_HEIGHT = 3;
    private static final int MIN_PADDING_BOTTOM = 6;
    private static final int MIN_STRIP_HEIGHT = 32;
    private static final int MIN_TEXT_SPACING = 64;
    private static final int TAB_PADDING = 16;
    private static final int TAB_SPACING = 32;
    private static final String TAG = "PagerTabStrip";
    private boolean mDrawFullUnderline;
    private boolean mDrawFullUnderlineSet;
    private int mFullUnderlineHeight;
    private boolean mIgnoreTap;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mMinPaddingBottom;
    private int mMinStripHeight;
    private int mMinTextSpacing;
    private int mTabAlpha;
    private int mTabPadding;
    private final Paint mTabPaint;
    private final Rect mTempRect;
    private int mTouchSlop;

    public PagerTabStrip(@NonNull Context context) {
        this(context, null);
    }

    public boolean getDrawFullUnderline() {
        return this.mDrawFullUnderline;
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.mMinStripHeight);
    }

    @ColorInt
    public int getTabIndicatorColor() {
        return this.mIndicatorColor;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.mCurrText.getLeft() - this.mTabPadding;
        int right = this.mCurrText.getRight() + this.mTabPadding;
        int i2 = height - this.mIndicatorHeight;
        this.mTabPaint.setColor((this.mTabAlpha << 24) | (this.mIndicatorColor & 16777215));
        float f2 = height;
        canvas.drawRect(left, i2, right, f2, this.mTabPaint);
        if (this.mDrawFullUnderline) {
            this.mTabPaint.setColor((this.mIndicatorColor & 16777215) | (-16777216));
            canvas.drawRect(getPaddingLeft(), height - this.mFullUnderlineHeight, getWidth() - getPaddingRight(), f2, this.mTabPaint);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int action = motionEvent.getAction();
        if (action != 0 && this.mIgnoreTap) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        if (action == 0) {
            this.mInitialMotionX = x2;
            this.mInitialMotionY = y2;
            this.mIgnoreTap = false;
        } else if (action != 1) {
            if (action == 2 && (Math.abs(x2 - this.mInitialMotionX) > this.mTouchSlop || Math.abs(y2 - this.mInitialMotionY) > this.mTouchSlop)) {
                this.mIgnoreTap = true;
            }
        } else if (x2 < this.mCurrText.getLeft() - this.mTabPadding) {
            ViewPager viewPager = this.mPager;
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        } else if (x2 > this.mCurrText.getRight() + this.mTabPadding) {
            ViewPager viewPager2 = this.mPager;
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
        return true;
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i2) {
        super.setBackgroundColor(i2);
        if (this.mDrawFullUnderlineSet) {
            return;
        }
        this.mDrawFullUnderline = (i2 & (-16777216)) == 0;
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.mDrawFullUnderlineSet) {
            return;
        }
        this.mDrawFullUnderline = drawable == null;
    }

    @Override // android.view.View
    public void setBackgroundResource(@DrawableRes int i2) {
        super.setBackgroundResource(i2);
        if (this.mDrawFullUnderlineSet) {
            return;
        }
        this.mDrawFullUnderline = i2 == 0;
    }

    public void setDrawFullUnderline(boolean z2) {
        this.mDrawFullUnderline = z2;
        this.mDrawFullUnderlineSet = true;
        invalidate();
    }

    @Override // android.view.View
    public void setPadding(int i2, int i3, int i4, int i5) {
        int i6 = this.mMinPaddingBottom;
        if (i5 < i6) {
            i5 = i6;
        }
        super.setPadding(i2, i3, i4, i5);
    }

    public void setTabIndicatorColor(@ColorInt int i2) {
        this.mIndicatorColor = i2;
        this.mTabPaint.setColor(i2);
        invalidate();
    }

    public void setTabIndicatorColorResource(@ColorRes int i2) {
        setTabIndicatorColor(ContextCompat.getColor(getContext(), i2));
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void setTextSpacing(int i2) {
        int i3 = this.mMinTextSpacing;
        if (i2 < i3) {
            i2 = i3;
        }
        super.setTextSpacing(i2);
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void updateTextPositions(int i2, float f2, boolean z2) {
        Rect rect = this.mTempRect;
        int height = getHeight();
        int left = this.mCurrText.getLeft() - this.mTabPadding;
        int right = this.mCurrText.getRight() + this.mTabPadding;
        int i3 = height - this.mIndicatorHeight;
        rect.set(left, i3, right, height);
        super.updateTextPositions(i2, f2, z2);
        this.mTabAlpha = (int) (Math.abs(f2 - 0.5f) * 2.0f * 255.0f);
        rect.union(this.mCurrText.getLeft() - this.mTabPadding, i3, this.mCurrText.getRight() + this.mTabPadding, height);
        invalidate(rect);
    }

    public PagerTabStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mTabPaint = paint;
        this.mTempRect = new Rect();
        this.mTabAlpha = 255;
        this.mDrawFullUnderline = false;
        this.mDrawFullUnderlineSet = false;
        int i2 = this.mTextColor;
        this.mIndicatorColor = i2;
        paint.setColor(i2);
        float f2 = context.getResources().getDisplayMetrics().density;
        this.mIndicatorHeight = (int) ((3.0f * f2) + 0.5f);
        this.mMinPaddingBottom = (int) ((6.0f * f2) + 0.5f);
        this.mMinTextSpacing = (int) (64.0f * f2);
        this.mTabPadding = (int) ((16.0f * f2) + 0.5f);
        this.mFullUnderlineHeight = (int) ((1.0f * f2) + 0.5f);
        this.mMinStripHeight = (int) ((f2 * 32.0f) + 0.5f);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.mPrevText.setFocusable(true);
        this.mPrevText.setOnClickListener(new View.OnClickListener() { // from class: androidx.viewpager.widget.PagerTabStrip.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Resources.NotFoundException {
                PagerTabStrip.this.mPager.setCurrentItem(r2.getCurrentItem() - 1);
            }
        });
        this.mNextText.setFocusable(true);
        this.mNextText.setOnClickListener(new View.OnClickListener() { // from class: androidx.viewpager.widget.PagerTabStrip.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Resources.NotFoundException {
                ViewPager viewPager = PagerTabStrip.this.mPager;
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        if (getBackground() == null) {
            this.mDrawFullUnderline = true;
        }
    }
}
