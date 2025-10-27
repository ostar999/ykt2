package com.ykb.ebook.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.ykb.ebook.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001a\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0014J(\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\tH\u0014J\u001a\u0010\u001f\u001a\u00020\u00162\b\b\u0001\u0010 \u001a\u00020\t2\b\b\u0001\u0010\u0012\u001a\u00020\tJ\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\tJ\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\tR\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/ykb/ebook/weight/RoundCornerProgressBar;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", TtmlNode.ATTR_TTS_BACKGROUND_COLOR, "cornerRadius", "", "max", "paint", "Landroid/graphics/Paint;", "progress", "progressColor", "rectF", "Landroid/graphics/RectF;", "init", "", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onSizeChanged", "w", "h", "oldw", "oldh", "setColor", "bgColor", "setProgress", "setProgressMax", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class RoundCornerProgressBar extends View {
    private int backgroundColor;
    private float cornerRadius;
    private int max;
    private Paint paint;
    private int progress;
    private int progressColor;
    private RectF rectF;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RoundCornerProgressBar(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.progress = 10;
        this.progressColor = -7829368;
        this.backgroundColor = -16776961;
        this.cornerRadius = 20.0f;
        this.max = 100;
        init(context, null);
    }

    private final void init(Context context, AttributeSet attrs) {
        this.paint = new Paint();
        this.rectF = new RectF();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgressBar);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…e.RoundCornerProgressBar)");
        this.progress = typedArrayObtainStyledAttributes.getInt(R.styleable.RoundCornerProgressBar_progress_value, this.progress);
        this.progressColor = typedArrayObtainStyledAttributes.getColor(R.styleable.RoundCornerProgressBar_progress_color, this.progressColor);
        this.backgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.RoundCornerProgressBar_background_color, this.backgroundColor);
        this.cornerRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.RoundCornerProgressBar_progress_corner_radius, this.cornerRadius);
        this.max = typedArrayObtainStyledAttributes.getInt(R.styleable.RoundCornerProgressBar_max, this.max);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        RectF rectF = this.rectF;
        Paint paint = null;
        if (rectF == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF = null;
        }
        rectF.right = getWidth();
        Paint paint2 = this.paint;
        if (paint2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            paint2 = null;
        }
        paint2.setColor(this.backgroundColor);
        Paint paint3 = this.paint;
        if (paint3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            paint3 = null;
        }
        paint3.setStyle(Paint.Style.FILL);
        RectF rectF2 = this.rectF;
        if (rectF2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF2 = null;
        }
        float f2 = this.cornerRadius;
        Paint paint4 = this.paint;
        if (paint4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            paint4 = null;
        }
        canvas.drawRoundRect(rectF2, f2, f2, paint4);
        float width = (this.max <= 0 || this.progress <= 0) ? 0.0f : (getWidth() * this.progress) / this.max;
        RectF rectF3 = this.rectF;
        if (rectF3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF3 = null;
        }
        rectF3.right = width;
        Paint paint5 = this.paint;
        if (paint5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
            paint5 = null;
        }
        paint5.setColor(this.progressColor);
        RectF rectF4 = this.rectF;
        if (rectF4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF4 = null;
        }
        float f3 = this.cornerRadius;
        Paint paint6 = this.paint;
        if (paint6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("paint");
        } else {
            paint = paint6;
        }
        canvas.drawRoundRect(rectF4, f3, f3, paint);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        RectF rectF = this.rectF;
        RectF rectF2 = null;
        if (rectF == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF = null;
        }
        rectF.left = 0.0f;
        RectF rectF3 = this.rectF;
        if (rectF3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF3 = null;
        }
        rectF3.top = 0.0f;
        RectF rectF4 = this.rectF;
        if (rectF4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
            rectF4 = null;
        }
        rectF4.right = w2;
        RectF rectF5 = this.rectF;
        if (rectF5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rectF");
        } else {
            rectF2 = rectF5;
        }
        rectF2.bottom = h2;
    }

    public final void setColor(@ColorInt int bgColor, @ColorInt int progressColor) {
        this.progressColor = progressColor;
        this.backgroundColor = bgColor;
        invalidate();
    }

    public final void setProgress(int progress) {
        this.progress = progress;
    }

    public final void setProgressMax(int max) {
        this.max = max;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RoundCornerProgressBar(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.progress = 10;
        this.progressColor = -7829368;
        this.backgroundColor = -16776961;
        this.cornerRadius = 20.0f;
        this.max = 100;
        init(context, attrs);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RoundCornerProgressBar(@NotNull Context context, @NotNull AttributeSet attrs, int i2) {
        super(context, attrs, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        this.progress = 10;
        this.progressColor = -7829368;
        this.backgroundColor = -16776961;
        this.cornerRadius = 20.0f;
        this.max = 100;
        init(context, attrs);
    }
}
