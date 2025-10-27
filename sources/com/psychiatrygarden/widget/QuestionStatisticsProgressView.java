package com.psychiatrygarden.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import cn.hutool.core.text.StrPool;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010&\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u0018\u0010'\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010(\u001a\u00020\fH\u0002J\u0018\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u0007H\u0002J\u0010\u0010,\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0014J\u0018\u0010-\u001a\u00020#2\u0006\u0010.\u001a\u00020\u00072\u0006\u0010/\u001a\u00020\u0007H\u0014J\"\u00100\u001a\u00020#2\u0006\u00101\u001a\u00020\u00072\u0006\u00102\u001a\u00020\u00072\n\b\u0002\u00103\u001a\u0004\u0018\u000104R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/psychiatrygarden/widget/QuestionStatisticsProgressView;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "circlePaint", "Landroid/graphics/Paint;", "mCurrentPercent", "", "mDefaultHeight", "mDefaultWidth", "mEndColor", "mHeight", "mInnerPaint", "mMiddleColor", "mOutterPaint", "mPercentSymbolWidth", "mPercentText", "mPercentTextColor", "mPercentTextWidth", "mRectF", "Landroid/graphics/RectF;", "mRightPercentTextColor", "mShader", "Landroid/graphics/Shader;", "mStartColor", "mTextPaint", "Landroid/text/TextPaint;", "mWidth", "strokeWidth", "drawArc", "", "canvas", "Landroid/graphics/Canvas;", "drawEndCircle", "drawText", "radius", "getSize", "defaultSize", "measureSpec", "onDraw", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "setCurrentPercent", "rightCount", "totalCount", "rightPercent", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class QuestionStatisticsProgressView extends View {

    @NotNull
    private final Paint circlePaint;
    private float mCurrentPercent;
    private int mDefaultHeight;
    private int mDefaultWidth;
    private int mEndColor;
    private int mHeight;

    @NotNull
    private Paint mInnerPaint;
    private int mMiddleColor;

    @NotNull
    private Paint mOutterPaint;
    private float mPercentSymbolWidth;
    private float mPercentText;
    private int mPercentTextColor;
    private float mPercentTextWidth;

    @Nullable
    private RectF mRectF;
    private int mRightPercentTextColor;

    @Nullable
    private Shader mShader;
    private int mStartColor;

    @NotNull
    private TextPaint mTextPaint;
    private int mWidth;
    private int strokeWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public QuestionStatisticsProgressView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ QuestionStatisticsProgressView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final void drawArc(Canvas canvas) {
        RectF rectF = this.mRectF;
        if (rectF != null) {
            canvas.drawArc(rectF, 180.0f, 180.0f, false, this.mOutterPaint);
            canvas.drawArc(rectF, 180.0f, this.mCurrentPercent, false, this.mInnerPaint);
        }
    }

    private final void drawEndCircle(Canvas canvas) {
        float f2 = (this.mCurrentPercent + 180.0f) * 0.017453292f;
        RectF rectF = this.mRectF;
        Intrinsics.checkNotNull(rectF);
        float fCenterX = rectF.centerX();
        RectF rectF2 = this.mRectF;
        Intrinsics.checkNotNull(rectF2);
        float f3 = 2;
        double d3 = f2;
        float fWidth = fCenterX + ((rectF2.width() / f3) * ((float) Math.cos(d3)));
        RectF rectF3 = this.mRectF;
        Intrinsics.checkNotNull(rectF3);
        float fCenterY = rectF3.centerY();
        RectF rectF4 = this.mRectF;
        Intrinsics.checkNotNull(rectF4);
        float fHeight = fCenterY + ((rectF4.height() / f3) * ((float) Math.sin(d3)));
        this.circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.circlePaint.setColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? "#F95843" : "#B2575C"));
        this.circlePaint.setAntiAlias(true);
        this.circlePaint.setStrokeWidth(CommonUtil.dip2px(getContext(), 3.0f) * 1.0f);
        this.circlePaint.setShadowLayer(2.0f, 0.0f, 1.0f, Color.parseColor("#EF9A90"));
        canvas.drawCircle(fWidth, fHeight, CommonUtil.dip2px(getContext(), 5.0f) * 1.0f, this.circlePaint);
        this.circlePaint.setColor(-1);
        this.circlePaint.setShader(null);
        canvas.drawCircle(fWidth, fHeight, CommonUtil.dip2px(getContext(), 2.0f) * 1.0f, this.circlePaint);
    }

    private final void drawText(Canvas canvas, float radius) {
        this.mTextPaint.setTextSize(CommonUtil.sp2px(getContext(), 18.0f) * 1.0f);
        this.mTextPaint.setColor(this.mPercentTextColor);
        this.mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        float f2 = radius * 0.85f;
        String strValueOf = String.valueOf(this.mPercentText);
        if (StringsKt__StringsKt.contains$default((CharSequence) strValueOf, (CharSequence) StrPool.DOT, false, 2, (Object) null) && TextUtils.equals("0", (String) StringsKt__StringsKt.split$default((CharSequence) strValueOf, new String[]{StrPool.DOT}, false, 0, 6, (Object) null).get(1))) {
            strValueOf = (String) StringsKt__StringsKt.split$default((CharSequence) strValueOf, new String[]{StrPool.DOT}, false, 0, 6, (Object) null).get(0);
        }
        String str = this.mPercentText == 0.0f ? "0" : strValueOf;
        float width = getWidth() - this.mPercentTextWidth;
        float f3 = this.mPercentSymbolWidth;
        canvas.drawText(str, ((width - f3) / 2.0f) - (f3 / 4), f2, this.mTextPaint);
        this.mTextPaint.setTextSize(CommonUtil.sp2px(getContext(), 12.0f) * 1.0f);
        this.mTextPaint.setTypeface(Typeface.DEFAULT);
        float width2 = (getWidth() / 2.0f) + 5.0f + this.mPercentTextWidth;
        float f4 = this.mPercentSymbolWidth;
        canvas.drawText("%", (width2 - f4) + (f4 / (ScreenUtil.isTablet(getContext()) ? 3.0f : 4.0f)), f2, this.mTextPaint);
        this.mTextPaint.setColor(this.mRightPercentTextColor);
        canvas.drawText("正确率", (getWidth() - this.mTextPaint.measureText("正确率")) / 2.0f, f2 + CommonUtil.dip2px(getContext(), 20.0f), this.mTextPaint);
    }

    private final int getSize(int defaultSize, int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.getSize(measureSpec) : defaultSize;
    }

    public static /* synthetic */ void setCurrentPercent$default(QuestionStatisticsProgressView questionStatisticsProgressView, int i2, int i3, String str, int i4, Object obj) {
        if ((i4 & 4) != 0) {
            str = "0.0";
        }
        questionStatisticsProgressView.setCurrentPercent(i2, i3, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCurrentPercent$lambda$1(QuestionStatisticsProgressView this$0, DecimalFormat fm, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(fm, "$fm");
        Intrinsics.checkNotNullParameter(it, "it");
        String str = fm.format(it.getAnimatedValue());
        Intrinsics.checkNotNullExpressionValue(str, "fm.format(it.animatedValue)");
        this$0.mCurrentPercent = Float.parseFloat(str);
        this$0.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCurrentPercent$lambda$2(QuestionStatisticsProgressView this$0, DecimalFormat fm, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(fm, "$fm");
        Intrinsics.checkNotNullParameter(it, "it");
        String str = fm.format(it.getAnimatedValue());
        Intrinsics.checkNotNullExpressionValue(str, "fm.format(it.animatedValue)");
        this$0.mPercentText = Float.parseFloat(str);
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        float width = getWidth() * 1.0f;
        float height = getHeight() * 1.0f;
        float f2 = width / 2.0f;
        float f3 = height / 2.0f;
        if (this.mShader == null) {
            LinearGradient linearGradient = new LinearGradient(0.0f, f3, width * 1.0f, f3, new int[]{this.mStartColor, this.mMiddleColor, this.mEndColor}, (float[]) null, Shader.TileMode.CLAMP);
            this.mShader = linearGradient;
            this.mInnerPaint.setShader(linearGradient);
        }
        float fCoerceAtMost = RangesKt___RangesKt.coerceAtMost(width, height) / 2;
        if (this.mRectF == null) {
            this.mRectF = new RectF(f2 - fCoerceAtMost, CommonUtil.dip2px(getContext(), 10.0f) * 1.0f, f2 + fCoerceAtMost, f3 + fCoerceAtMost);
        }
        drawArc(canvas);
        drawEndCircle(canvas);
        drawText(canvas, fCoerceAtMost);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mWidth = getSize(this.mDefaultWidth, widthMeasureSpec);
        int size = getSize(this.mDefaultHeight, heightMeasureSpec);
        this.mHeight = size;
        int i2 = this.mWidth;
        if (i2 < size) {
            this.mHeight = i2;
        }
        setMeasuredDimension(i2, this.mHeight);
    }

    public final void setCurrentPercent(int rightCount, int totalCount, @Nullable String rightPercent) {
        if (rightCount > totalCount) {
            rightCount = totalCount;
        }
        if (rightCount < 0) {
            rightCount = 0;
        }
        if (rightCount == 0 || rightPercent == null) {
            this.mPercentText = 0.0f;
        }
        if (rightPercent != null) {
            if (new Regex("^-?[0-9]+(\\.[0-9]+)?([Ee][-+]?[0-9]+)?$").matches(rightPercent)) {
                this.mPercentText = Float.parseFloat(rightPercent);
            }
            this.mPercentTextWidth = (StringsKt__StringsKt.contains$default((CharSequence) rightPercent, (CharSequence) StrPool.DOT, false, 2, (Object) null) && TextUtils.equals("0", (CharSequence) StringsKt__StringsKt.split$default((CharSequence) rightPercent, new String[]{StrPool.DOT}, false, 0, 6, (Object) null).get(1))) ? this.mTextPaint.measureText((String) StringsKt__StringsKt.split$default((CharSequence) rightPercent, new String[]{StrPool.DOT}, false, 0, 6, (Object) null).get(0)) : this.mTextPaint.measureText(rightPercent);
        }
        float fFloatValue = new BigDecimal(String.valueOf(this.mPercentText)).multiply(new BigDecimal("180")).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).setScale(2).floatValue();
        this.mCurrentPercent = fFloatValue;
        if (fFloatValue > 180.0f) {
            this.mCurrentPercent = 180.0f;
        } else if (fFloatValue < 0.0f) {
            this.mCurrentPercent = 0.0f;
        }
        float f2 = this.mCurrentPercent;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, f2 / 2.0f, f2);
        float f3 = this.mPercentText;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, f3 / 2, f3);
        final DecimalFormat decimalFormat = new DecimalFormat("#.#");
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.jf
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                QuestionStatisticsProgressView.setCurrentPercent$lambda$1(this.f16620c, decimalFormat, valueAnimator);
            }
        });
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.kf
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                QuestionStatisticsProgressView.setCurrentPercent$lambda$2(this.f16652c, decimalFormat, valueAnimator);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500L);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        animatorSet.start();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public QuestionStatisticsProgressView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mDefaultHeight = CommonUtil.dip2px(context, 320.0f);
        this.mDefaultWidth = CommonUtil.dip2px(context, 160.0f);
        this.mStartColor = Color.parseColor("#FC8C7D");
        this.mMiddleColor = Color.parseColor("#F66C5B");
        this.mEndColor = Color.parseColor("#F8604D");
        this.mTextPaint = new TextPaint();
        this.circlePaint = new Paint();
        setBackground(new ColorDrawable(-1));
        this.strokeWidth = CommonUtil.dip2px(context, 10.0f);
        this.mInnerPaint = new Paint();
        Paint paint = new Paint();
        this.mOutterPaint = paint;
        paint.setStrokeWidth(this.strokeWidth * 1.0f);
        this.mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        boolean z2 = SkinManager.getCurrentSkinType(context) == 0;
        if (!z2) {
            this.mStartColor = Color.parseColor("#BA7C7F");
            this.mMiddleColor = Color.parseColor("#BD6B70");
            this.mEndColor = Color.parseColor("#B2575C");
        }
        this.mOutterPaint.setColor(Color.parseColor(z2 ? "#EFEFEF" : "#1C2134"));
        this.mInnerPaint.setStyle(Paint.Style.STROKE);
        this.mOutterPaint.setStyle(Paint.Style.STROKE);
        this.mInnerPaint.setShader(this.mShader);
        this.mInnerPaint.setAntiAlias(true);
        this.mOutterPaint.setAntiAlias(true);
        this.mInnerPaint.setStrokeWidth(this.strokeWidth * 1.0f);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize(CommonUtil.sp2px(context, 18.0f) * 1.0f);
        this.mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mPercentSymbolWidth = this.mTextPaint.measureText("%");
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.first_text_color, R.attr.tertiary_text_color});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.theme.obtainStyl…r\n            )\n        )");
        this.mPercentTextColor = typedArrayObtainStyledAttributes.getColor(0, Color.parseColor("#303030"));
        this.mRightPercentTextColor = typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#909090"));
        typedArrayObtainStyledAttributes.recycle();
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            setBackgroundColor(Color.rgb(46, 50, 65));
        }
    }
}
