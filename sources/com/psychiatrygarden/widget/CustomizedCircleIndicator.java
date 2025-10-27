package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.psychiatrygarden.utils.LogUtils;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.BaseIndicator;
import com.youth.banner.util.BannerUtils;

/* loaded from: classes6.dex */
public class CustomizedCircleIndicator extends BaseIndicator {
    public static final int INDICATOR_SMALL_WIDTH = BannerUtils.dp2px(4.0f);
    private long DxanimTime;
    private boolean begainScrool_anim;
    private float curX;
    int indicatorSize;
    private boolean isLeft;
    private int lastValue;
    private ValueAnimator mDxscrollAnimator;
    private int mNormalRadius;
    int mPagetate;
    private int mSelectedRadius;
    private int mSlideDx;
    private int mSmallRadius;
    private int maxRadius;
    boolean startAnim;

    public enum Duration {
        LEFT,
        RIGHT
    }

    public interface ScrollEndListener {
        void scrollEnd();
    }

    public CustomizedCircleIndicator(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        if (Math.abs((int) ((Float) valueAnimator.getAnimatedValue()).floatValue()) <= this.mSlideDx * 3) {
            this.curX = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPageSelected$1() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPageSelected$2() {
    }

    private void resetScroolDx(int position) {
        if (position == 0) {
            this.curX = 0.0f;
        }
        if (position == 1) {
            this.curX = 0.0f;
        }
        if (this.isLeft) {
            if (position == 2) {
                this.curX = 0.0f;
            }
            int i2 = this.indicatorSize;
            if (position == i2 - 2) {
                this.curX = -(this.mSlideDx * (i2 - 5));
            }
            if (position == i2 - 1) {
                this.curX = -(this.mSlideDx * (i2 - 5));
            }
        } else {
            int i3 = this.indicatorSize;
            if (position == i3 - 3) {
                this.curX = -(this.mSlideDx * (i3 - 5));
            }
            if (position == i3 - 2) {
                this.curX = -(this.mSlideDx * (i3 - 5));
            }
            if (position == i3 - 1) {
                this.curX = -(this.mSlideDx * (i3 - 5));
            }
        }
        invalidate();
    }

    private void startDxScrollAnim(Duration duration, final ScrollEndListener listener) {
        if (this.mDxscrollAnimator.isRunning()) {
            this.mDxscrollAnimator.end();
            if (duration == Duration.LEFT) {
                if ((((int) Math.abs(this.curX)) / this.mSlideDx) + 1 < this.indicatorSize - 4) {
                    this.curX = (-r0) * r2;
                    invalidate();
                }
            } else {
                if (((int) Math.abs(this.curX)) / this.mSlideDx < this.indicatorSize - 4) {
                    this.curX = (-r0) * r2;
                    invalidate();
                }
            }
        }
        this.mDxscrollAnimator.setDuration(this.DxanimTime);
        float normalWidth = duration == Duration.LEFT ? (this.curX - this.config.getNormalWidth()) - this.config.getIndicatorSpace() : this.curX + this.config.getNormalWidth() + this.config.getIndicatorSpace();
        this.mSlideDx = this.config.getNormalWidth() + this.config.getIndicatorSpace();
        this.mDxscrollAnimator.setFloatValues(this.curX, normalWidth);
        this.mDxscrollAnimator.removeAllListeners();
        this.mDxscrollAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.widget.CustomizedCircleIndicator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ScrollEndListener scrollEndListener = listener;
                if (scrollEndListener != null) {
                    scrollEndListener.scrollEnd();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }
        });
        this.mDxscrollAnimator.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int indicatorSize = this.config.getIndicatorSize();
        if (indicatorSize <= 1) {
            return;
        }
        float indicatorSpace = this.curX;
        int i2 = 0;
        if (this.indicatorSize <= 5) {
            while (i2 < indicatorSize) {
                this.mPaint.setColor(this.config.getCurrentPosition() == i2 ? this.config.getSelectedColor() : this.config.getNormalColor());
                int i3 = this.mSelectedRadius;
                int normalWidth = this.config.getNormalWidth();
                float f2 = i3;
                canvas.drawCircle(indicatorSpace + f2, this.maxRadius, f2, this.mPaint);
                indicatorSpace += normalWidth + this.config.getIndicatorSpace();
                i2++;
            }
            return;
        }
        int currentPosition = this.config.getCurrentPosition();
        LogUtils.e("img_pos_rad", "currentPosition=====》" + currentPosition);
        if (currentPosition > 2 && currentPosition < this.indicatorSize - 2) {
            LogUtils.e("img_pos", "中间选中" + this.isLeft);
            if (!this.isLeft) {
                while (i2 < indicatorSize) {
                    Paint paint = this.mPaint;
                    IndicatorConfig indicatorConfig = this.config;
                    paint.setColor(currentPosition == i2 ? indicatorConfig.getSelectedColor() : indicatorConfig.getNormalColor());
                    int i4 = this.mNormalRadius;
                    int normalWidth2 = this.config.getNormalWidth();
                    if (i2 == currentPosition) {
                        i4 = this.mSelectedRadius;
                    }
                    if (i2 == currentPosition - 2) {
                        i4 = this.mSmallRadius;
                    }
                    float f3 = i4;
                    canvas.drawCircle(indicatorSpace + f3, this.maxRadius, f3, this.mPaint);
                    indicatorSpace += normalWidth2 + this.config.getIndicatorSpace();
                    i2++;
                }
                return;
            }
            int i5 = this.startAnim;
            while (i5 < indicatorSize) {
                Paint paint2 = this.mPaint;
                IndicatorConfig indicatorConfig2 = this.config;
                paint2.setColor(currentPosition == i5 ? indicatorConfig2.getSelectedColor() : indicatorConfig2.getNormalColor());
                int i6 = this.mNormalRadius;
                int normalWidth3 = this.config.getNormalWidth();
                if (i5 == currentPosition) {
                    i6 = this.mSelectedRadius;
                }
                if (i5 == currentPosition + 1) {
                    LogUtils.e("img_pos_rad", "mNormalRadius");
                    i6 = this.mNormalRadius;
                }
                if (i5 == currentPosition + 2) {
                    LogUtils.e("img_pos_rad", "mSmallRadius+2");
                    i6 = this.mSmallRadius;
                }
                if (i5 == currentPosition - 3) {
                    LogUtils.e("img_pos_rad", "mSmallRadius-2");
                    i6 = this.mSmallRadius;
                }
                float f4 = i6;
                canvas.drawCircle(indicatorSpace + f4, this.maxRadius, f4, this.mPaint);
                indicatorSpace += normalWidth3 + this.config.getIndicatorSpace();
                i5++;
            }
            return;
        }
        if (currentPosition >= 2 && currentPosition >= this.indicatorSize - 2) {
            LogUtils.e("img_pos", "后面两个选中");
            while (i2 < indicatorSize) {
                Paint paint3 = this.mPaint;
                IndicatorConfig indicatorConfig3 = this.config;
                paint3.setColor(currentPosition == i2 ? indicatorConfig3.getSelectedColor() : indicatorConfig3.getNormalColor());
                int i7 = this.mNormalRadius;
                int normalWidth4 = this.config.getNormalWidth();
                int i8 = this.indicatorSize;
                if (i2 >= i8 - 3) {
                    i7 = this.mSelectedRadius;
                }
                if (i2 == i8 - 4) {
                    i7 = this.mNormalRadius;
                }
                if (i2 == i8 - 5) {
                    i7 = this.mSmallRadius;
                }
                float f5 = i7;
                canvas.drawCircle(indicatorSpace + f5, this.maxRadius, f5, this.mPaint);
                indicatorSpace += normalWidth4 + this.config.getIndicatorSpace();
                i2++;
            }
            return;
        }
        LogUtils.e("img_pos", "前面两个选中" + this.isLeft);
        if (this.isLeft) {
            while (i2 < indicatorSize) {
                Paint paint4 = this.mPaint;
                IndicatorConfig indicatorConfig4 = this.config;
                paint4.setColor(currentPosition == i2 ? indicatorConfig4.getSelectedColor() : indicatorConfig4.getNormalColor());
                int i9 = this.mNormalRadius;
                int normalWidth5 = this.config.getNormalWidth();
                if (i2 < 3) {
                    i9 = this.mSelectedRadius;
                }
                if (i2 == 3) {
                    i9 = this.mNormalRadius;
                }
                if (i2 == 4) {
                    i9 = this.mSmallRadius;
                }
                float f6 = i9;
                canvas.drawCircle(indicatorSpace + f6, this.maxRadius, f6, this.mPaint);
                indicatorSpace += normalWidth5 + this.config.getIndicatorSpace();
                i2++;
            }
            return;
        }
        while (i2 < indicatorSize) {
            Paint paint5 = this.mPaint;
            IndicatorConfig indicatorConfig5 = this.config;
            paint5.setColor(currentPosition == i2 ? indicatorConfig5.getSelectedColor() : indicatorConfig5.getNormalColor());
            int i10 = this.mNormalRadius;
            int normalWidth6 = this.config.getNormalWidth();
            if (i2 < 3) {
                i10 = this.mSelectedRadius;
            }
            if (i2 == 3) {
                i10 = this.mNormalRadius;
            }
            if (i2 == 4) {
                i10 = this.mSmallRadius;
            }
            if (currentPosition == 1 && this.begainScrool_anim) {
                if (i2 == 0) {
                    i10 = this.mNormalRadius;
                }
                if (i2 > 0 && i2 < 4) {
                    i10 = this.mSelectedRadius;
                }
                if (i2 == 4) {
                    i10 = this.mNormalRadius;
                }
            }
            float f7 = i10;
            canvas.drawCircle(indicatorSpace + f7, this.maxRadius, f7, this.mPaint);
            indicatorSpace += normalWidth6 + this.config.getIndicatorSpace();
            i2++;
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.config.getIndicatorSize() > 5) {
            this.indicatorSize = this.config.getIndicatorSize();
        }
        this.mNormalRadius = this.config.getNormalWidth() / 2;
        int selectedWidth = this.config.getSelectedWidth() / 2;
        this.mSelectedRadius = selectedWidth;
        this.maxRadius = Math.max(selectedWidth, this.mNormalRadius);
        setMeasuredDimension((this.config.getIndicatorSpace() * 4) + this.config.getSelectedWidth() + (this.config.getNormalWidth() * 4), Math.max(this.config.getNormalWidth(), this.config.getSelectedWidth()));
    }

    @Override // com.youth.banner.indicator.BaseIndicator, com.youth.banner.listener.OnPageChangeListener
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
        this.mPagetate = state;
    }

    @Override // com.youth.banner.indicator.BaseIndicator, com.youth.banner.listener.OnPageChangeListener
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.offset = positionOffset;
        if (positionOffset != 0.0f) {
            int i2 = this.lastValue;
            if (i2 >= positionOffsetPixels) {
                this.isLeft = false;
            } else if (i2 < positionOffsetPixels) {
                this.isLeft = true;
            }
        }
        this.lastValue = positionOffsetPixels;
    }

    @Override // com.youth.banner.indicator.BaseIndicator, com.youth.banner.listener.OnPageChangeListener
    public void onPageSelected(int position) {
        this.config.setCurrentPosition(position);
        if (position > 2 && position < this.indicatorSize - 2 && this.isLeft) {
            startDxScrollAnim(Duration.LEFT, new ScrollEndListener() { // from class: com.psychiatrygarden.widget.f5
                @Override // com.psychiatrygarden.widget.CustomizedCircleIndicator.ScrollEndListener
                public final void scrollEnd() {
                    CustomizedCircleIndicator.lambda$onPageSelected$1();
                }
            });
        } else if (position < 2 || position >= this.indicatorSize - 3 || this.isLeft) {
            resetScroolDx(position);
        } else {
            startDxScrollAnim(Duration.RIGHT, new ScrollEndListener() { // from class: com.psychiatrygarden.widget.g5
                @Override // com.psychiatrygarden.widget.CustomizedCircleIndicator.ScrollEndListener
                public final void scrollEnd() {
                    CustomizedCircleIndicator.lambda$onPageSelected$2();
                }
            });
        }
    }

    public CustomizedCircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizedCircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.startAnim = false;
        this.mPagetate = 0;
        this.lastValue = -1;
        this.isLeft = true;
        this.begainScrool_anim = false;
        this.mSmallRadius = INDICATOR_SMALL_WIDTH / 2;
        this.mNormalRadius = this.config.getNormalWidth() / 2;
        this.mSelectedRadius = this.config.getSelectedWidth() / 2;
        this.DxanimTime = 500L;
        ValueAnimator valueAnimator = new ValueAnimator();
        this.mDxscrollAnimator = valueAnimator;
        valueAnimator.setDuration(500L);
        this.mDxscrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.h5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f16541c.lambda$new$0(valueAnimator2);
            }
        });
    }
}
