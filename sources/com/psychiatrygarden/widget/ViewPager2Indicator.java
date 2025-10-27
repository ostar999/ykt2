package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ViewPager2Indicator extends View {
    private static final float DEFAULT_INDICATOR_HEIGHT_DP = 4.0f;
    private static final float DEFAULT_INDICATOR_WIDTH_DP = 20.0f;
    private static final float DEFAULT_PROGRESS_WIDTH_DP = 10.0f;
    private static final int INDICATOR_COLOR = -1118482;
    private static final int PROGRESS_COLOR = -436157;
    private float cornerRadiusPx;
    private float indicatorHeightPx;
    private Paint indicatorPaint;
    private RectF indicatorRect;
    private float indicatorWidthPx;
    private float progress;
    private Paint progressPaint;
    private RectF progressRect;
    private float progressWidthPx;

    public interface PageSelectListener {
        void onPageSelect(int position);
    }

    public ViewPager2Indicator(Context context) {
        super(context);
        this.indicatorRect = new RectF();
        this.progressRect = new RectF();
        this.progress = 0.0f;
        init();
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    private void init() {
        this.indicatorWidthPx = dpToPx(DEFAULT_INDICATOR_WIDTH_DP);
        this.indicatorHeightPx = dpToPx(DEFAULT_INDICATOR_HEIGHT_DP);
        this.progressWidthPx = dpToPx(DEFAULT_PROGRESS_WIDTH_DP);
        this.cornerRadiusPx = this.indicatorHeightPx / 2.0f;
        this.indicatorPaint = new Paint(1);
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_one_border_color, R.attr.main_theme_color});
        this.indicatorPaint.setColor(typedArrayObtainStyledAttributes.getColor(0, getContext().getColor(R.color.color_eeeeee)));
        this.indicatorPaint.setStyle(Paint.Style.FILL);
        Paint paint = new Paint(1);
        this.progressPaint = paint;
        paint.setColor(typedArrayObtainStyledAttributes.getColor(1, getContext().getColor(R.color.main_theme_color)));
        this.progressPaint.setStyle(Paint.Style.FILL);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgress(float progress) {
        this.progress = Math.max(0.0f, Math.min(1.0f, progress));
        invalidate();
    }

    public void bindViewPager2(final ViewPager2 viewPager2, final PageSelectListener listener) {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.widget.ViewPager2Indicator.1
            private void updateIndicatorProgress(int position, float offset) {
                if (viewPager2.getAdapter().getItemCount() <= 1) {
                    return;
                }
                ViewPager2Indicator.this.setProgress((position + offset) / (r0 - 1));
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                updateIndicatorProgress(position, positionOffset);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicatorProgress(position, 0.0f);
                listener.onPageSelect(position);
            }
        });
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float paddingLeft = getPaddingLeft() + ((((getWidth() - getPaddingLeft()) - getPaddingRight()) - this.indicatorWidthPx) / 2.0f);
        float paddingTop = getPaddingTop();
        float height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        float f2 = this.indicatorHeightPx;
        float f3 = paddingTop + ((height - f2) / 2.0f);
        this.indicatorRect.set(paddingLeft, f3, this.indicatorWidthPx + paddingLeft, f2 + f3);
        RectF rectF = this.indicatorRect;
        float f4 = this.cornerRadiusPx;
        canvas.drawRoundRect(rectF, f4, f4, this.indicatorPaint);
        float f5 = this.indicatorWidthPx;
        float f6 = this.progressWidthPx;
        float f7 = paddingLeft + (this.progress * (f5 - f6));
        this.progressRect.set(f7, f3, f6 + f7, this.indicatorHeightPx + f3);
        RectF rectF2 = this.progressRect;
        float f8 = this.cornerRadiusPx;
        canvas.drawRoundRect(rectF2, f8, f8, this.progressPaint);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.resolveSize(((int) Math.ceil(this.indicatorWidthPx)) + getPaddingLeft() + getPaddingRight(), widthMeasureSpec), View.resolveSize(((int) Math.ceil(this.indicatorHeightPx)) + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }

    public ViewPager2Indicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.indicatorRect = new RectF();
        this.progressRect = new RectF();
        this.progress = 0.0f;
        init();
    }

    public ViewPager2Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.indicatorRect = new RectF();
        this.progressRect = new RectF();
        this.progress = 0.0f;
        init();
    }
}
