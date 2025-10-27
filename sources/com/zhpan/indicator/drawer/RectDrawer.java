package com.zhpan.indicator.drawer;

import android.animation.ArgbEvaluator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.umeng.analytics.pro.am;
import com.zhpan.indicator.option.IndicatorOptions;
import com.zhpan.indicator.utils.IndicatorUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J \u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H&J\u0018\u0010\u0017\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0012H\u0002J\u0010\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u001c\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u001d"}, d2 = {"Lcom/zhpan/indicator/drawer/RectDrawer;", "Lcom/zhpan/indicator/drawer/BaseDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "mRectF", "Landroid/graphics/RectF;", "getMRectF$indicator_release", "()Landroid/graphics/RectF;", "setMRectF$indicator_release", "(Landroid/graphics/RectF;)V", "drawCheckedSlider", "", "canvas", "Landroid/graphics/Canvas;", "drawColorSlider", "drawInequalitySlider", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "", "drawRect", "rx", "", "ry", "drawScaleSlider", am.aC, "drawSmoothSlider", "drawUncheckedSlider", "drawWormSlider", "onDraw", "indicator_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes8.dex */
public abstract class RectDrawer extends BaseDrawer {

    @NotNull
    private RectF mRectF;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RectDrawer(@NotNull IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        this.mRectF = new RectF();
    }

    private final void drawCheckedSlider(Canvas canvas) {
        getMPaint().setColor(getMIndicatorOptions().getCheckedSliderColor());
        int slideMode = getMIndicatorOptions().getSlideMode();
        if (slideMode == 2) {
            drawSmoothSlider(canvas);
        } else if (slideMode == 3) {
            drawWormSlider(canvas);
        } else {
            if (slideMode != 5) {
                return;
            }
            drawColorSlider(canvas);
        }
    }

    private final void drawColorSlider(Canvas canvas) {
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        float f2 = currentPosition;
        float minWidth = (getMinWidth() * f2) + (f2 * getMIndicatorOptions().getSliderGap());
        if (getArgbEvaluator() == null) {
            setArgbEvaluator$indicator_release(new ArgbEvaluator());
        }
        if (slideProgress < 0.99d) {
            ArgbEvaluator argbEvaluator = getArgbEvaluator();
            if (argbEvaluator != null) {
                Object objEvaluate = argbEvaluator.evaluate(slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()));
                Paint mPaint = getMPaint();
                if (objEvaluate == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                mPaint.setColor(((Integer) objEvaluate).intValue());
            }
            this.mRectF.set(minWidth, 0.0f, getMinWidth() + minWidth, getMIndicatorOptions().getSliderHeight());
            drawRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
        }
        float sliderGap = minWidth + getMIndicatorOptions().getSliderGap() + getMIndicatorOptions().getNormalSliderWidth();
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            sliderGap = 0.0f;
        }
        ArgbEvaluator argbEvaluator2 = getArgbEvaluator();
        if (argbEvaluator2 != null) {
            Object objEvaluate2 = argbEvaluator2.evaluate(1 - slideProgress, Integer.valueOf(getMIndicatorOptions().getCheckedSliderColor()), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()));
            Paint mPaint2 = getMPaint();
            if (objEvaluate2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            mPaint2.setColor(((Integer) objEvaluate2).intValue());
        }
        this.mRectF.set(sliderGap, 0.0f, getMinWidth() + sliderGap, getMIndicatorOptions().getSliderHeight());
        drawRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
    }

    private final void drawInequalitySlider(Canvas canvas, int pageSize) {
        int i2 = 0;
        float sliderGap = 0.0f;
        while (i2 < pageSize) {
            float maxWidth = i2 == getMIndicatorOptions().getCurrentPosition() ? getMaxWidth() : getMinWidth();
            getMPaint().setColor(i2 == getMIndicatorOptions().getCurrentPosition() ? getMIndicatorOptions().getCheckedSliderColor() : getMIndicatorOptions().getNormalSliderColor());
            this.mRectF.set(sliderGap, 0.0f, sliderGap + maxWidth, getMIndicatorOptions().getSliderHeight());
            drawRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
            sliderGap += maxWidth + getMIndicatorOptions().getSliderGap();
            i2++;
        }
    }

    private final void drawScaleSlider(Canvas canvas, int i2) {
        float slideProgress;
        int checkedSliderColor = getMIndicatorOptions().getCheckedSliderColor();
        float sliderGap = getMIndicatorOptions().getSliderGap();
        float sliderHeight = getMIndicatorOptions().getSliderHeight();
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float normalSliderWidth = getMIndicatorOptions().getNormalSliderWidth();
        float checkedSliderWidth = getMIndicatorOptions().getCheckedSliderWidth();
        if (getArgbEvaluator() == null) {
            setArgbEvaluator$indicator_release(new ArgbEvaluator());
        }
        if (i2 < currentPosition) {
            getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
            if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
                float f2 = i2;
                slideProgress = (f2 * normalSliderWidth) + (f2 * sliderGap) + ((checkedSliderWidth - normalSliderWidth) * getMIndicatorOptions().getSlideProgress());
            } else {
                float f3 = i2;
                slideProgress = (f3 * normalSliderWidth) + (f3 * sliderGap);
            }
            this.mRectF.set(slideProgress, 0.0f, normalSliderWidth + slideProgress, sliderHeight);
            drawRect(canvas, sliderHeight, sliderHeight);
            return;
        }
        if (i2 != currentPosition) {
            if (currentPosition + 1 != i2 || getMIndicatorOptions().getSlideProgress() == 0.0f) {
                getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
                float f4 = i2;
                float minWidth = (getMinWidth() * f4) + (f4 * sliderGap) + (checkedSliderWidth - getMinWidth());
                this.mRectF.set(minWidth, 0.0f, getMinWidth() + minWidth, sliderHeight);
                drawRect(canvas, sliderHeight, sliderHeight);
                return;
            }
            return;
        }
        getMPaint().setColor(checkedSliderColor);
        float slideProgress2 = getMIndicatorOptions().getSlideProgress();
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            ArgbEvaluator argbEvaluator = getArgbEvaluator();
            if (argbEvaluator != null) {
                Object objEvaluate = argbEvaluator.evaluate(slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()));
                Paint mPaint = getMPaint();
                if (objEvaluate == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                mPaint.setColor(((Integer) objEvaluate).intValue());
            }
            float pageSize = ((getMIndicatorOptions().getPageSize() - 1) * (getMIndicatorOptions().getSliderGap() + normalSliderWidth)) + checkedSliderWidth;
            this.mRectF.set((pageSize - checkedSliderWidth) + ((checkedSliderWidth - normalSliderWidth) * slideProgress2), 0.0f, pageSize, sliderHeight);
            drawRect(canvas, sliderHeight, sliderHeight);
        } else {
            float f5 = 1;
            if (slideProgress2 < f5) {
                ArgbEvaluator argbEvaluator2 = getArgbEvaluator();
                if (argbEvaluator2 != null) {
                    Object objEvaluate2 = argbEvaluator2.evaluate(slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()));
                    Paint mPaint2 = getMPaint();
                    if (objEvaluate2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                    }
                    mPaint2.setColor(((Integer) objEvaluate2).intValue());
                }
                float f6 = i2;
                float f7 = (f6 * normalSliderWidth) + (f6 * sliderGap);
                this.mRectF.set(f7, 0.0f, f7 + normalSliderWidth + ((checkedSliderWidth - normalSliderWidth) * (f5 - slideProgress2)), sliderHeight);
                drawRect(canvas, sliderHeight, sliderHeight);
            }
        }
        if (currentPosition == getMIndicatorOptions().getPageSize() - 1) {
            if (slideProgress2 > 0) {
                ArgbEvaluator argbEvaluator3 = getArgbEvaluator();
                if (argbEvaluator3 != null) {
                    Object objEvaluate3 = argbEvaluator3.evaluate(1 - slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()));
                    Paint mPaint3 = getMPaint();
                    if (objEvaluate3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                    }
                    mPaint3.setColor(((Integer) objEvaluate3).intValue());
                }
                this.mRectF.set(0.0f, 0.0f, normalSliderWidth + 0.0f + ((checkedSliderWidth - normalSliderWidth) * slideProgress2), sliderHeight);
                drawRect(canvas, sliderHeight, sliderHeight);
                return;
            }
            return;
        }
        if (slideProgress2 > 0) {
            ArgbEvaluator argbEvaluator4 = getArgbEvaluator();
            if (argbEvaluator4 != null) {
                Object objEvaluate4 = argbEvaluator4.evaluate(1 - slideProgress2, Integer.valueOf(checkedSliderColor), Integer.valueOf(getMIndicatorOptions().getNormalSliderColor()));
                Paint mPaint4 = getMPaint();
                if (objEvaluate4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
                }
                mPaint4.setColor(((Integer) objEvaluate4).intValue());
            }
            float f8 = i2;
            float f9 = (f8 * normalSliderWidth) + (f8 * sliderGap) + normalSliderWidth + sliderGap + checkedSliderWidth;
            this.mRectF.set((f9 - normalSliderWidth) - ((checkedSliderWidth - normalSliderWidth) * slideProgress2), 0.0f, f9, sliderHeight);
            drawRect(canvas, sliderHeight, sliderHeight);
        }
    }

    private final void drawSmoothSlider(Canvas canvas) {
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float sliderGap = getMIndicatorOptions().getSliderGap();
        float sliderHeight = getMIndicatorOptions().getSliderHeight();
        float f2 = currentPosition;
        float maxWidth = (getMaxWidth() * f2) + (f2 * sliderGap) + ((getMaxWidth() + sliderGap) * getMIndicatorOptions().getSlideProgress());
        this.mRectF.set(maxWidth, 0.0f, getMaxWidth() + maxWidth, sliderHeight);
        drawRect(canvas, sliderHeight, sliderHeight);
    }

    private final void drawUncheckedSlider(Canvas canvas, int pageSize) {
        for (int i2 = 0; i2 < pageSize; i2++) {
            getMPaint().setColor(getMIndicatorOptions().getNormalSliderColor());
            float f2 = i2;
            float maxWidth = (getMaxWidth() * f2) + (f2 * getMIndicatorOptions().getSliderGap()) + (getMaxWidth() - getMinWidth());
            this.mRectF.set(maxWidth, 0.0f, getMinWidth() + maxWidth, getMIndicatorOptions().getSliderHeight());
            drawRect(canvas, getMIndicatorOptions().getSliderHeight(), getMIndicatorOptions().getSliderHeight());
        }
    }

    private final void drawWormSlider(Canvas canvas) {
        float sliderHeight = getMIndicatorOptions().getSliderHeight();
        float slideProgress = getMIndicatorOptions().getSlideProgress();
        int currentPosition = getMIndicatorOptions().getCurrentPosition();
        float sliderGap = getMIndicatorOptions().getSliderGap() + getMIndicatorOptions().getNormalSliderWidth();
        float coordinateX = IndicatorUtils.INSTANCE.getCoordinateX(getMIndicatorOptions(), getMaxWidth(), currentPosition);
        float f2 = 2;
        this.mRectF.set((RangesKt___RangesKt.coerceAtLeast(((slideProgress - 0.5f) * sliderGap) * 2.0f, 0.0f) + coordinateX) - (getMIndicatorOptions().getNormalSliderWidth() / f2), 0.0f, coordinateX + RangesKt___RangesKt.coerceAtMost(slideProgress * sliderGap * 2.0f, sliderGap) + (getMIndicatorOptions().getNormalSliderWidth() / f2), sliderHeight);
        drawRect(canvas, sliderHeight, sliderHeight);
    }

    public abstract void drawRect(@NotNull Canvas canvas, float rx, float ry);

    @NotNull
    /* renamed from: getMRectF$indicator_release, reason: from getter */
    public final RectF getMRectF() {
        return this.mRectF;
    }

    @Override // com.zhpan.indicator.drawer.IDrawer
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        int pageSize = getMIndicatorOptions().getPageSize();
        if (pageSize > 1 || (getMIndicatorOptions().getShowIndicatorOneItem() && pageSize == 1)) {
            if (isWidthEquals() && getMIndicatorOptions().getSlideMode() != 0) {
                drawUncheckedSlider(canvas, pageSize);
                drawCheckedSlider(canvas);
            } else {
                if (getMIndicatorOptions().getSlideMode() != 4) {
                    drawInequalitySlider(canvas, pageSize);
                    return;
                }
                for (int i2 = 0; i2 < pageSize; i2++) {
                    drawScaleSlider(canvas, i2);
                }
            }
        }
    }

    public final void setMRectF$indicator_release(@NotNull RectF rectF) {
        Intrinsics.checkParameterIsNotNull(rectF, "<set-?>");
        this.mRectF = rectF;
    }
}
