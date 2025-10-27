package com.ykb.ebook.weight.slider;

import android.content.AppCtxKt;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.ColorResourcesKt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010>\u001a\u00020\u001d2\u0006\u0010?\u001a\u00020\u001dH\u0002J \u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010?\u001a\u00020\u001dH\u0002J \u0010F\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010?\u001a\u00020\u001dH\u0002J \u0010G\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010?\u001a\u00020\u001dH\u0002J \u0010H\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010?\u001a\u00020\u001dH\u0002J(\u0010I\u001a\u00020A2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010?\u001a\u00020\u001dH\u0016J\u0012\u0010J\u001a\u0004\u0018\u00010\u00062\u0006\u0010K\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020A2\b\b\u0001\u0010L\u001a\u00020\fJ\u0010\u0010+\u001a\u00020A2\b\b\u0001\u0010L\u001a\u00020\fR(\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R(\u0010\u0016\u001a\u0004\u0018\u00010\u00152\b\u0010\u0005\u001a\u0004\u0018\u00010\u0015@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0005\u001a\u00020\u001d@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R(\u0010$\u001a\u0004\u0018\u00010#2\b\u0010\u0005\u001a\u0004\u0018\u00010#@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010)\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\t\"\u0004\b+\u0010\u000bR$\u0010,\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u000f\"\u0004\b.\u0010\u0011R$\u0010/\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u000f\"\u0004\b1\u0010\u0011R(\u00102\u001a\u0004\u0018\u00010\u00152\b\u0010\u0005\u001a\u0004\u0018\u00010\u0015@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0018\"\u0004\b4\u0010\u001aR\u000e\u00105\u001a\u00020\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R$\u00106\u001a\u00020\u001d2\u0006\u0010\u0005\u001a\u00020\u001d@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010 \"\u0004\b8\u0010\"R(\u00109\u001a\u0004\u0018\u00010#2\b\u0010\u0005\u001a\u0004\u0018\u00010#@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010&\"\u0004\b;\u0010(R\u000e\u0010<\u001a\u00020=X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006M"}, d2 = {"Lcom/ykb/ebook/weight/slider/ITEffect;", "Lcom/ykb/ebook/weight/slider/BaseEffect;", "slider", "Lcom/ykb/ebook/weight/slider/NiftySlider;", "(Lcom/ykb/ebook/weight/slider/NiftySlider;)V", "value", "Landroid/graphics/drawable/Drawable;", "endIcon", "getEndIcon", "()Landroid/graphics/drawable/Drawable;", "setEndIcon", "(Landroid/graphics/drawable/Drawable;)V", "", "endIconSize", "getEndIconSize", "()I", "setEndIconSize", "(I)V", "endPadding", "getEndPadding", "setEndPadding", "", "endText", "getEndText", "()Ljava/lang/String;", "setEndText", "(Ljava/lang/String;)V", "endTextBounds", "Landroid/graphics/Rect;", "", "endTextSize", "getEndTextSize", "()F", "setEndTextSize", "(F)V", "Landroid/content/res/ColorStateList;", "endTintList", "getEndTintList", "()Landroid/content/res/ColorStateList;", "setEndTintList", "(Landroid/content/res/ColorStateList;)V", "startIcon", "getStartIcon", "setStartIcon", "startIconSize", "getStartIconSize", "setStartIconSize", "startPadding", "getStartPadding", "setStartPadding", "startText", "getStartText", "setStartText", "startTextBounds", "startTextSize", "getStartTextSize", "setStartTextSize", "startTintList", "getStartTintList", "setStartTintList", "textPaint", "Landroid/graphics/Paint;", "baseline", "yCenter", "drawEndIcon", "", "canvas", "Landroid/graphics/Canvas;", "trackRect", "Landroid/graphics/RectF;", "drawEndText", "drawStartIcon", "drawStartText", "drawTrackAfter", "initializeCustomIconDrawable", "originalDrawable", "drawableResId", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nITEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ITEffect.kt\ncom/ykb/ebook/weight/slider/ITEffect\n+ 2 Canvas.kt\nandroidx/core/graphics/CanvasKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,263:1\n47#2,8:264\n47#2,8:272\n42#3:280\n42#3:281\n*S KotlinDebug\n*F\n+ 1 ITEffect.kt\ncom/ykb/ebook/weight/slider/ITEffect\n*L\n47#1:264,8\n61#1:272,8\n76#1:280\n96#1:281\n*E\n"})
/* loaded from: classes8.dex */
public final class ITEffect extends BaseEffect {

    @Nullable
    private Drawable endIcon;
    private int endIconSize;
    private int endPadding;

    @Nullable
    private String endText;

    @NotNull
    private Rect endTextBounds;
    private float endTextSize;

    @Nullable
    private ColorStateList endTintList;

    @NotNull
    private final NiftySlider slider;

    @Nullable
    private Drawable startIcon;
    private int startIconSize;
    private int startPadding;

    @Nullable
    private String startText;

    @NotNull
    private Rect startTextBounds;
    private float startTextSize;

    @Nullable
    private ColorStateList startTintList;

    @NotNull
    private Paint textPaint;

    public ITEffect(@NotNull NiftySlider slider) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        this.slider = slider;
        this.startTextBounds = new Rect();
        this.endTextBounds = new Rect();
        Paint paint = new Paint(5);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        this.textPaint = paint;
        this.startTextSize = 20.0f;
        this.endTextSize = 20.0f;
    }

    private final float baseline(float yCenter) {
        return yCenter - ((this.textPaint.getFontMetricsInt().bottom + this.textPaint.getFontMetricsInt().top) / 2);
    }

    private final void drawEndIcon(Canvas canvas, RectF trackRect, float yCenter) {
        Drawable drawable = this.endIcon;
        if (drawable != null) {
            int iSave = canvas.save();
            canvas.translate(((this.slider.getTrackWidth() + trackRect.left) - this.endPadding) - drawable.getBounds().width(), yCenter - (drawable.getBounds().height() / 2.0f));
            try {
                drawable.draw(canvas);
            } finally {
                canvas.restoreToCount(iSave);
            }
        }
    }

    private final void drawEndText(Canvas canvas, RectF trackRect, float yCenter) {
        String str = this.endText;
        if (str != null) {
            if (this.endTintList != null) {
                this.textPaint.setColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
            }
            this.textPaint.setTextSize(this.endTextSize);
            this.textPaint.getTextBounds(str, 0, str.length(), this.endTextBounds);
            canvas.drawText(str, ((this.slider.getTrackWidth() + trackRect.left) - this.endPadding) - (this.endTextBounds.width() / 2), baseline(yCenter), this.textPaint);
        }
    }

    private final void drawStartIcon(Canvas canvas, RectF trackRect, float yCenter) {
        Drawable drawable = this.startIcon;
        if (drawable != null) {
            int iSave = canvas.save();
            canvas.translate(trackRect.left + this.startPadding, yCenter - (drawable.getBounds().height() / 2.0f));
            try {
                drawable.draw(canvas);
            } finally {
                canvas.restoreToCount(iSave);
            }
        }
    }

    private final void drawStartText(Canvas canvas, RectF trackRect, float yCenter) {
        String str = this.startText;
        if (str != null) {
            if (this.startTintList != null) {
                this.textPaint.setColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
            }
            this.textPaint.setTextSize(this.startTextSize);
            this.textPaint.getTextBounds(str, 0, str.length(), this.startTextBounds);
            canvas.drawText(str, trackRect.left + this.startPadding + (this.startTextBounds.width() / 2), baseline(yCenter), this.textPaint);
        }
    }

    private final Drawable initializeCustomIconDrawable(Drawable originalDrawable) {
        Drawable.ConstantState constantState = originalDrawable.mutate().getConstantState();
        if (constantState != null) {
            return constantState.newDrawable();
        }
        return null;
    }

    @Nullable
    public final Drawable getEndIcon() {
        return this.endIcon;
    }

    public final int getEndIconSize() {
        return this.endIconSize;
    }

    public final int getEndPadding() {
        return this.endPadding;
    }

    @Nullable
    public final String getEndText() {
        return this.endText;
    }

    public final float getEndTextSize() {
        return this.endTextSize;
    }

    @Nullable
    public final ColorStateList getEndTintList() {
        return this.endTintList;
    }

    @Nullable
    public final Drawable getStartIcon() {
        return this.startIcon;
    }

    public final int getStartIconSize() {
        return this.startIconSize;
    }

    public final int getStartPadding() {
        return this.startPadding;
    }

    @Nullable
    public final String getStartText() {
        return this.startText;
    }

    public final float getStartTextSize() {
        return this.startTextSize;
    }

    @Nullable
    public final ColorStateList getStartTintList() {
        return this.startTintList;
    }

    public final void setEndIcon(@DrawableRes int drawableResId) {
        setEndIcon(ContextCompat.getDrawable(this.slider.getContext(), drawableResId));
    }

    public final void setEndIconSize(int i2) {
        this.endIconSize = i2;
        Drawable drawable = this.endIcon;
        if (drawable != null) {
            drawable.setBounds(0, 0, i2, i2);
        }
        this.slider.postInvalidate();
    }

    public final void setEndPadding(int i2) {
        this.endPadding = i2;
        this.slider.postInvalidate();
    }

    public final void setEndText(@Nullable String str) {
        if (str != null) {
            this.endText = str;
            this.slider.postInvalidate();
        }
    }

    public final void setEndTextSize(float f2) {
        this.endTextSize = f2;
        this.slider.postInvalidate();
    }

    public final void setEndTintList(@Nullable ColorStateList colorStateList) {
        this.endTintList = colorStateList;
        Drawable drawable = this.endIcon;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, colorStateList);
        }
    }

    public final void setStartIcon(@DrawableRes int drawableResId) {
        setStartIcon(ContextCompat.getDrawable(this.slider.getContext(), drawableResId));
    }

    public final void setStartIconSize(int i2) {
        this.startIconSize = i2;
        Drawable drawable = this.startIcon;
        if (drawable != null) {
            drawable.setBounds(0, 0, i2, i2);
        }
        this.slider.postInvalidate();
    }

    public final void setStartPadding(int i2) {
        this.startPadding = i2;
        this.slider.postInvalidate();
    }

    public final void setStartText(@Nullable String str) {
        if (str != null) {
            this.startText = str;
            this.slider.postInvalidate();
        }
    }

    public final void setStartTextSize(float f2) {
        this.startTextSize = f2;
        this.slider.postInvalidate();
    }

    public final void setStartTintList(@Nullable ColorStateList colorStateList) {
        this.startTintList = colorStateList;
        Drawable drawable = this.startIcon;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, colorStateList);
        }
    }

    @Override // com.ykb.ebook.weight.slider.BaseEffect, com.ykb.ebook.weight.slider.SliderEffect
    public void drawTrackAfter(@NotNull NiftySlider slider, @NotNull Canvas canvas, @NotNull RectF trackRect, float yCenter) {
        Intrinsics.checkNotNullParameter(slider, "slider");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(trackRect, "trackRect");
        drawStartIcon(canvas, trackRect, yCenter);
        drawEndIcon(canvas, trackRect, yCenter);
        drawStartText(canvas, trackRect, yCenter);
        drawEndText(canvas, trackRect, yCenter);
    }

    public final void setEndIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            Drawable drawableInitializeCustomIconDrawable = initializeCustomIconDrawable(drawable);
            if (drawableInitializeCustomIconDrawable != null) {
                DrawableCompat.setTintList(drawableInitializeCustomIconDrawable, this.endTintList);
                int i2 = this.endIconSize;
                if (i2 > 0) {
                    drawableInitializeCustomIconDrawable.setBounds(0, 0, i2, i2);
                }
            } else {
                drawableInitializeCustomIconDrawable = null;
            }
            this.endIcon = drawableInitializeCustomIconDrawable;
            this.slider.postInvalidate();
        }
    }

    public final void setStartIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            Drawable drawableInitializeCustomIconDrawable = initializeCustomIconDrawable(drawable);
            if (drawableInitializeCustomIconDrawable != null) {
                DrawableCompat.setTintList(drawableInitializeCustomIconDrawable, this.startTintList);
                int i2 = this.startIconSize;
                if (i2 > 0) {
                    drawableInitializeCustomIconDrawable.setBounds(0, 0, i2, i2);
                }
            } else {
                drawableInitializeCustomIconDrawable = null;
            }
            this.startIcon = drawableInitializeCustomIconDrawable;
            this.slider.postInvalidate();
        }
    }
}
