package com.psychiatrygarden.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.extensions.StringExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0011\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ(\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u000fH\u0002J0\u0010;\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\u000fH\u0002J0\u0010=\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\u000fH\u0002J\u0018\u0010>\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u0010?\u001a\u00020\u000fH\u0002J(\u0010@\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000f2\u0006\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u000fH\u0002J\u0018\u0010A\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u000fH\u0002J\u0010\u0010B\u001a\u0002052\u0006\u00106\u001a\u000207H\u0014J\u0018\u0010C\u001a\u0002052\u0006\u0010D\u001a\u00020\u00072\u0006\u0010E\u001a\u00020\u0007H\u0014J.\u0010F\u001a\u0002052\u0006\u0010G\u001a\u00020\u000f2\u0006\u0010H\u001a\u00020\u000f2\u0006\u0010I\u001a\u00020\u000f2\u0006\u0010J\u001a\u0002002\u0006\u0010/\u001a\u000200R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0004\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006K"}, d2 = {"Lcom/psychiatrygarden/widget/EstimateScoreView;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "arcPaint", "Landroid/graphics/Paint;", "arcRect", "Landroid/graphics/RectF;", "containerColor", "containerCorner", "", "containerHeight", "containerPaint", "containerRect", "containerWidth", "currentScore", "iconHeight", "iconTextSpacing", "iconWidth", "innerArcColor", "labelColor", "labelPadding", "markInnerColor", "markPaint", "markRadiusInner", "markRadiusOuter", "maxScore", "notPassGradientColors", "", "passGradientColors", "passIconAngleOffset", "passPointerDrawable", "Landroid/graphics/drawable/Drawable;", "passScore", "scoreTextColor", "scoreUp", "", "startAngle", "strokeWidth", "sweepAngle", "textOffsetTop", "textPaint", "upDownScores", "", "updownIcon", "warningColor", "warningTextColor", "drawBottomContainer", "", "canvas", "Landroid/graphics/Canvas;", "cx", "cy", "radius", "drawEndMarker", "currentAngle", "drawGradientArc", "drawPassIcon", "passAngle", "drawStartEndLabels", "drawTopText", "onDraw", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "setScores", "current", "max", "pass", "scoreDown", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class EstimateScoreView extends View {

    @NotNull
    private final Paint arcPaint;

    @NotNull
    private final RectF arcRect;
    private int containerColor;
    private final float containerCorner;
    private final float containerHeight;

    @NotNull
    private final Paint containerPaint;

    @NotNull
    private final RectF containerRect;
    private final float containerWidth;
    private float currentScore;
    private final float iconHeight;
    private final float iconTextSpacing;
    private final float iconWidth;
    private int innerArcColor;
    private int labelColor;
    private final float labelPadding;
    private int markInnerColor;

    @NotNull
    private final Paint markPaint;
    private final float markRadiusInner;
    private final float markRadiusOuter;
    private float maxScore;

    @NotNull
    private final int[] notPassGradientColors;

    @NotNull
    private final int[] passGradientColors;
    private final float passIconAngleOffset;
    private Drawable passPointerDrawable;
    private float passScore;
    private int scoreTextColor;
    private boolean scoreUp;
    private final float startAngle;
    private final float strokeWidth;
    private final float sweepAngle;
    private final float textOffsetTop;

    @NotNull
    private final Paint textPaint;

    @Nullable
    private String upDownScores;

    @Nullable
    private Drawable updownIcon;
    private int warningColor;
    private int warningTextColor;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public EstimateScoreView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public EstimateScoreView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public EstimateScoreView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.maxScore = 600.0f;
        float fDp = StringExtensionsKt.dp(10.0f, context);
        this.strokeWidth = fDp;
        this.markRadiusOuter = StringExtensionsKt.dp(8.0f, context);
        this.markRadiusInner = StringExtensionsKt.dp(5.0f, context);
        this.textOffsetTop = StringExtensionsKt.dp(20.0f, context);
        this.labelPadding = StringExtensionsKt.dp(12.0f, context);
        this.containerWidth = StringExtensionsKt.dp(48.0f, context);
        this.containerHeight = StringExtensionsKt.dp(20.0f, context);
        this.containerCorner = StringExtensionsKt.dp(100.0f, context);
        this.iconWidth = StringExtensionsKt.dp(8.0f, context);
        this.iconHeight = StringExtensionsKt.dp(8.0f, context);
        this.iconTextSpacing = StringExtensionsKt.dp(5.0f, context);
        this.passIconAngleOffset = StringExtensionsKt.dp(6.0f, context);
        this.innerArcColor = Color.parseColor("#F9FAFB");
        this.warningColor = Color.parseColor("#F95843");
        this.warningTextColor = Color.parseColor("#909499");
        this.scoreTextColor = Color.parseColor("#141516");
        this.containerColor = Color.parseColor("#FFF1F0");
        this.markInnerColor = Color.parseColor("#96F95C");
        this.labelColor = Color.parseColor("#C2C6CB");
        this.startAngle = 170.0f;
        this.sweepAngle = 200.0f;
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.new_bg_two_color, R.attr.main_theme_color, R.attr.third_txt_color, R.attr.first_txt_color, R.attr.main_theme_five_deep_color, R.attr.forth_txt_color});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.theme.obtainStyl…r\n            )\n        )");
        this.innerArcColor = typedArrayObtainStyledAttributes.getColor(0, context.getColor(R.color.new_bg_two_color));
        this.warningColor = typedArrayObtainStyledAttributes.getColor(1, context.getColor(R.color.main_theme_color));
        this.warningTextColor = typedArrayObtainStyledAttributes.getColor(2, context.getColor(R.color.third_txt_color));
        this.scoreTextColor = typedArrayObtainStyledAttributes.getColor(3, context.getColor(R.color.first_txt_color));
        this.containerColor = typedArrayObtainStyledAttributes.getColor(4, context.getColor(R.color.main_theme_five_deep_color));
        this.labelColor = typedArrayObtainStyledAttributes.getColor(5, context.getColor(R.color.forth_txt_color));
        typedArrayObtainStyledAttributes.recycle();
        Drawable drawable = context.getDrawable(R.drawable.ic_estimate_score_pointer_vertical);
        Intrinsics.checkNotNull(drawable);
        this.passPointerDrawable = drawable;
        if (SkinManager.getCurrentSkinType(context) == 1) {
            this.updownIcon = ContextCompat.getDrawable(context, R.drawable.ic_score_down_day);
        }
        this.upDownScores = "0";
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(fDp);
        this.arcPaint = paint;
        this.textPaint = new Paint(1);
        Paint paint2 = new Paint(1);
        paint2.setColor(this.containerColor);
        paint2.setStyle(Paint.Style.FILL);
        this.containerPaint = paint2;
        Paint paint3 = new Paint(1);
        paint3.setStyle(Paint.Style.FILL);
        this.markPaint = paint3;
        this.arcRect = new RectF();
        this.containerRect = new RectF();
        this.notPassGradientColors = new int[]{Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F")};
        this.passGradientColors = new int[]{Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FFFB72"), Color.parseColor("#8DEE96"), Color.parseColor("#8DEE96"), Color.parseColor("#1CE2BA")};
    }

    private final void drawBottomContainer(Canvas canvas, float cx, float cy, float radius) {
        if (TextUtils.isEmpty(this.upDownScores) || this.updownIcon == null) {
            RectF rectF = this.containerRect;
            float f2 = 2;
            float f3 = cx - (this.containerWidth / f2);
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "context");
            float fDp = StringExtensionsKt.dp(75.0f, context);
            float f4 = (this.containerWidth / f2) + cx;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "context");
            rectF.set(f3, fDp, f4, StringExtensionsKt.dp(75.0f, context2) + this.containerHeight);
        } else {
            RectF rectF2 = this.containerRect;
            float f5 = 2;
            float f6 = cx - (this.containerWidth / f5);
            Context context3 = getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "context");
            float fDp2 = StringExtensionsKt.dp(62.0f, context3);
            float f7 = (this.containerWidth / f5) + cx;
            Context context4 = getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "context");
            rectF2.set(f6, fDp2, f7, StringExtensionsKt.dp(62.0f, context4) + this.containerHeight);
        }
        float f8 = 2;
        canvas.drawRoundRect(this.containerRect, Math.min(this.containerCorner, this.containerHeight / f8), Math.min(this.containerCorner, this.containerHeight / f8), this.containerPaint);
        this.textPaint.setColor(getContext().getColor(this.scoreUp ? R.color.main_theme_color : R.color.new_success_color));
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.textPaint.setColor(getContext().getColor(this.scoreUp ? R.color.main_theme_color_night : R.color.new_success_color_night));
        }
        Paint paint = this.textPaint;
        Context context5 = getContext();
        Intrinsics.checkNotNullExpressionValue(context5, "context");
        paint.setTextSize(StringExtensionsKt.sp(12.0f, context5));
        String str = this.upDownScores;
        Intrinsics.checkNotNull(str);
        int iDp2px = str.length() > 2 ? SizeUtil.dp2px(getContext(), 2) : 0;
        int iDp2px2 = ((int) this.containerRect.left) + SizeUtil.dp2px(getContext(), 13);
        Drawable drawable = this.updownIcon;
        if (drawable != null) {
            float f9 = this.containerRect.top;
            Context context6 = getContext();
            Intrinsics.checkNotNullExpressionValue(context6, "context");
            int iDp = (int) (f9 + StringExtensionsKt.dp(6.0f, context6));
            int i2 = (int) (iDp2px2 + this.iconWidth);
            float f10 = this.containerRect.top;
            Context context7 = getContext();
            Intrinsics.checkNotNullExpressionValue(context7, "context");
            drawable.setBounds(iDp2px2, iDp, i2, (int) (f10 + StringExtensionsKt.dp(7.0f, context7) + this.iconHeight));
        }
        Drawable drawable2 = this.updownIcon;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Drawable drawable3 = this.updownIcon;
        int intrinsicWidth = drawable3 != null ? drawable3.getIntrinsicWidth() : (int) this.iconWidth;
        String str2 = this.upDownScores;
        if (str2 == null) {
            str2 = "0";
        }
        if (this.updownIcon != null) {
            cx = iDp2px2 + intrinsicWidth + iDp2px;
        }
        RectF rectF3 = this.containerRect;
        canvas.drawText(str2, cx, (rectF3.top + (rectF3.height() / f8)) - ((this.textPaint.descent() + this.textPaint.ascent()) / f8), this.textPaint);
    }

    private final void drawEndMarker(Canvas canvas, float cx, float cy, float radius, float currentAngle) {
        double radians = Math.toRadians(currentAngle);
        float fCos = cx + (((float) Math.cos(radians)) * radius);
        float fSin = cy + (radius * ((float) Math.sin(radians)));
        this.markPaint.setColor(-1);
        canvas.drawCircle(fCos, fSin, this.markRadiusOuter, this.markPaint);
        Paint paint = this.markPaint;
        float f2 = this.currentScore;
        paint.setColor((f2 < this.passScore || f2 <= 0.0f) ? this.notPassGradientColors[0] : this.markInnerColor);
        canvas.drawCircle(fCos, fSin, this.markRadiusInner, this.markPaint);
    }

    private final void drawGradientArc(Canvas canvas, float cx, float cy, float radius, float currentAngle) {
        Path path = new Path();
        RectF rectF = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        float f2 = this.startAngle;
        path.addArc(rectF, f2, currentAngle - f2);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        Paint paint = new Paint(this.arcPaint);
        Path path2 = new Path();
        pathMeasure.getSegment(0.0f, length, path2, true);
        paint.setShader(new LinearGradient(0.0f, 0.0f, pathMeasure.getLength(), 0.0f, this.currentScore < this.passScore ? this.notPassGradientColors : this.passGradientColors, (float[]) null, Shader.TileMode.CLAMP));
        canvas.drawPath(path2, paint);
        this.arcPaint.setShader(null);
    }

    private final void drawPassIcon(Canvas canvas, float passAngle) {
        Drawable drawable = this.passPointerDrawable;
        if (drawable == null) {
            Intrinsics.throwUninitializedPropertyAccessException("passPointerDrawable");
            drawable = null;
        }
        float f2 = 2;
        float fWidth = ((this.arcRect.width() / f2) - (this.arcPaint.getStrokeWidth() / f2)) - this.passIconAngleOffset;
        float fCenterX = this.arcRect.centerX();
        float fCenterY = this.arcRect.centerY();
        double d3 = passAngle;
        float fCos = fCenterX + (((float) Math.cos(Math.toRadians(d3))) * fWidth);
        float fSin = fCenterY + (fWidth * ((float) Math.sin(Math.toRadians(d3))));
        canvas.save();
        canvas.translate(fCos, fSin);
        canvas.rotate(passAngle + 90.0f);
        drawable.setBounds((-drawable.getIntrinsicWidth()) / 2, (-drawable.getIntrinsicHeight()) / 2, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
        drawable.draw(canvas);
        canvas.restore();
    }

    private final void drawStartEndLabels(Canvas canvas, float cx, float cy, float radius) {
        double radians = Math.toRadians(this.startAngle);
        float fCos = (((float) Math.cos(radians)) * radius) + cx;
        float fSin = (((float) Math.sin(radians)) * radius) + cy;
        this.textPaint.setTypeface(Typeface.DEFAULT);
        this.textPaint.setColor(this.labelColor);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.textPaint.setColor(getContext().getColor(R.color.forth_txt_color_night));
        }
        Paint paint = this.textPaint;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        paint.setTextSize(StringExtensionsKt.sp(8.0f, context));
        this.textPaint.setTextAlign(Paint.Align.LEFT);
        float f2 = 2;
        float f3 = fCos + (this.labelPadding / f2);
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        canvas.drawText("0", f3, fSin + StringExtensionsKt.dp(5.0f, context2), this.textPaint);
        double radians2 = Math.toRadians(this.startAngle + this.sweepAngle);
        float fCos2 = cx + (((float) Math.cos(radians2)) * radius);
        float fSin2 = cy + (radius * ((float) Math.sin(radians2)));
        this.textPaint.setTextAlign(Paint.Align.RIGHT);
        String strValueOf = String.valueOf((int) this.maxScore);
        float f4 = fCos2 - (this.labelPadding / f2);
        Context context3 = getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "context");
        canvas.drawText(strValueOf, f4, fSin2 + StringExtensionsKt.dp(5.0f, context3), this.textPaint);
    }

    private final void drawTopText(Canvas canvas, float cx) {
        float f2 = 2;
        float height = (getHeight() / 2.0f) + ((Math.min(getWidth(), getHeight()) - (this.strokeWidth * f2)) * ((float) Math.sin(Math.toRadians(this.startAngle))));
        Rect rect = new Rect();
        Paint paint = this.textPaint;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        paint.setTextSize(StringExtensionsKt.sp(22.0f, context));
        this.textPaint.getTextBounds(String.valueOf(this.currentScore), 0, String.valueOf(this.currentScore).length(), rect);
        int iHeight = rect.height();
        this.textPaint.setColor(this.warningTextColor);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.textPaint.setColor(getContext().getColor(R.color.third_txt_color_night));
        }
        Paint paint2 = this.textPaint;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        paint2.setTextSize(StringExtensionsKt.sp(12.0f, context2));
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        float f3 = this.textOffsetTop - (this.textPaint.getFontMetrics().top / f2);
        this.textPaint.setTypeface(Typeface.DEFAULT);
        if (TextUtils.isEmpty(this.upDownScores) || this.updownIcon == null) {
            canvas.drawText("预估分", cx, (height - iHeight) - SizeUtil.dp2px(getContext(), 14), this.textPaint);
        } else {
            Context context3 = getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "context");
            canvas.drawText("预估分", cx, StringExtensionsKt.dp(10.0f, context3) + f3, this.textPaint);
        }
        this.textPaint.setColor(this.scoreTextColor);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            this.textPaint.setColor(getContext().getColor(R.color.first_txt_color_night));
        }
        Paint paint3 = this.textPaint;
        Context context4 = getContext();
        Intrinsics.checkNotNullExpressionValue(context4, "context");
        paint3.setTextSize(StringExtensionsKt.sp(22.0f, context4));
        this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        if (TextUtils.isEmpty(this.upDownScores) || this.updownIcon == null) {
            canvas.drawText(String.valueOf((int) this.currentScore), cx, height - SizeUtil.dp2px(getContext(), 8), this.textPaint);
        } else {
            canvas.drawText(String.valueOf((int) this.currentScore), cx, f3 + SizeUtil.dp2px(getContext(), 33), this.textPaint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setScores$lambda$3(EstimateScoreView this$0, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        this$0.currentScore = ((Float) animatedValue).floatValue();
        this$0.invalidate();
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        canvas.drawColor(0);
        if (this.maxScore == 0.0f) {
            return;
        }
        float width = getWidth() / 2.0f;
        float height = getHeight() / 2.0f;
        float fMin = (Math.min(getWidth(), getHeight()) - (this.strokeWidth * 2)) / 2.0f;
        this.arcRect.set(width - fMin, height - fMin, width + fMin, height + fMin);
        this.arcPaint.setColor(Color.parseColor("#F2F4F6"));
        this.arcPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(this.arcRect, this.startAngle, this.sweepAngle, false, this.arcPaint);
        float f2 = this.startAngle + ((this.currentScore / this.maxScore) * this.sweepAngle);
        drawGradientArc(canvas, width, height, fMin, f2);
        drawEndMarker(canvas, width, height, fMin, f2);
        drawTopText(canvas, width);
        if (!TextUtils.isEmpty(this.upDownScores) && this.updownIcon != null) {
            drawBottomContainer(canvas, width, height, fMin);
        }
        drawStartEndLabels(canvas, width, height, fMin);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(SizeUtil.dp2px(getContext(), R2.array.ease_excel_file_suffix), SizeUtil.dp2px(getContext(), 132));
    }

    public final void setScores(float current, float max, float pass, @NotNull String scoreDown, @NotNull String upDownScores) {
        Intrinsics.checkNotNullParameter(scoreDown, "scoreDown");
        Intrinsics.checkNotNullParameter(upDownScores, "upDownScores");
        this.maxScore = max;
        this.passScore = pass;
        this.scoreUp = !Intrinsics.areEqual(scoreDown, "2");
        this.upDownScores = upDownScores;
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.ic_score_up, R.attr.ic_score_down, R.attr.main_theme_five_deep_color});
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.theme.obtainStyl…n_theme_five_deep_color))");
        this.updownIcon = Intrinsics.areEqual("0", scoreDown) ? null : Intrinsics.areEqual("1", scoreDown) ? typedArrayObtainStyledAttributes.getDrawable(0) : typedArrayObtainStyledAttributes.getDrawable(1);
        this.containerColor = typedArrayObtainStyledAttributes.getColor(2, getContext().getColor(R.color.main_theme_five_deep_color));
        if (Intrinsics.areEqual("2", scoreDown)) {
            this.containerColor = Color.parseColor("#ECF7E0");
            this.warningColor = Color.parseColor("#81CB30");
            if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                this.containerColor = Color.parseColor("#E9F1E8");
                this.warningColor = Color.parseColor("#6AA064");
                this.warningTextColor = Color.parseColor("#575F79");
            }
        }
        this.containerPaint.setColor(this.containerColor);
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, current).setDuration(500L);
        duration.setInterpolator(new LinearInterpolator());
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.b9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                EstimateScoreView.setScores$lambda$3(this.f16339c, valueAnimator);
            }
        });
        duration.start();
        typedArrayObtainStyledAttributes.recycle();
    }

    public /* synthetic */ EstimateScoreView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
