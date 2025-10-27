package com.ykb.ebook.weight.slider;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010(\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0002J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020\u0019H\u0017J\b\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020+2\u0006\u00102\u001a\u00020\u0015H\u0014J\u0010\u00103\u001a\u0002002\u0006\u00104\u001a\u000205H\u0014J\u0010\u00106\u001a\u00020+2\u0006\u00107\u001a\u00020\u0019H\u0016J\u0012\u00108\u001a\u00020+2\b\u00107\u001a\u0004\u0018\u000109H\u0016J\u0010\u0010:\u001a\u0002002\u0006\u00104\u001a\u000205H\u0002R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR(\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010 \u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0011\"\u0004\b\"\u0010\u0013R\u000e\u0010#\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010$\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0007\"\u0004\b&\u0010\tR\u000e\u0010'\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lcom/ykb/ebook/weight/slider/DefaultThumbDrawable;", "Landroid/graphics/drawable/Drawable;", "Lcom/ykb/ebook/weight/slider/IBaseThumbDrawable;", "()V", "cornerSize", "", "getCornerSize", "()F", "setCornerSize", "(F)V", "elevation", "getElevation", "setElevation", "value", "Landroid/content/res/ColorStateList;", "fillColor", "getFillColor", "()Landroid/content/res/ColorStateList;", "setFillColor", "(Landroid/content/res/ColorStateList;)V", "rect", "Landroid/graphics/Rect;", "rectF", "Landroid/graphics/RectF;", "shadowColor", "", "getShadowColor", "()I", "setShadowColor", "(I)V", "shadowPaint", "Landroid/graphics/Paint;", "strokeColor", "getStrokeColor", "setStrokeColor", "strokePaint", "strokeWidth", "getStrokeWidth", "setStrokeWidth", "thumbPaint", "calculateElevation", "radius", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getOpacity", "isStateful", "", "onBoundsChange", "bounds", "onStateChange", "state", "", "setAlpha", "p0", "setColorFilter", "Landroid/graphics/ColorFilter;", "updateColorsForState", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class DefaultThumbDrawable extends Drawable implements IBaseThumbDrawable {
    private float cornerSize;
    private float elevation;

    @Nullable
    private ColorStateList fillColor;

    @NotNull
    private final Rect rect;

    @NotNull
    private final RectF rectF;
    private int shadowColor;

    @NotNull
    private final Paint shadowPaint;

    @Nullable
    private ColorStateList strokeColor;

    @NotNull
    private final Paint strokePaint;
    private float strokeWidth;

    @NotNull
    private final Paint thumbPaint;

    public DefaultThumbDrawable() {
        Paint paint = new Paint(5);
        this.thumbPaint = paint;
        Paint paint2 = new Paint(5);
        this.strokePaint = paint2;
        Paint paint3 = new Paint(5);
        this.shadowPaint = paint3;
        this.rect = new Rect();
        this.rectF = new RectF();
        this.cornerSize = -1.0f;
        paint.setStyle(Paint.Style.FILL);
        paint2.setStyle(Paint.Style.STROKE);
        paint3.setAntiAlias(true);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        int[] state = getState();
        Intrinsics.checkNotNullExpressionValue(state, "state");
        updateColorsForState(state);
    }

    private final float calculateElevation(float elevation, float radius) {
        return Math.min(elevation, radius);
    }

    private final boolean updateColorsForState(int[] state) {
        int color;
        int colorForState;
        ColorStateList colorStateList = this.fillColor;
        boolean z2 = true;
        boolean z3 = false;
        if (colorStateList != null && color != (colorForState = colorStateList.getColorForState(state, (color = this.thumbPaint.getColor())))) {
            this.thumbPaint.setColor(colorForState);
            z3 = true;
        }
        ColorStateList colorStateList2 = this.strokeColor;
        if (colorStateList2 == null) {
            return z3;
        }
        int color2 = this.strokePaint.getColor();
        int colorForState2 = colorStateList2.getColorForState(state, color2);
        if (color2 != colorForState2) {
            this.strokePaint.setColor(colorForState2);
        } else {
            z2 = z3;
        }
        return z2;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        int i2;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        boolean z2 = false;
        if (this.cornerSize == -1.0f) {
            this.cornerSize = this.rectF.width() / 2.0f;
        }
        float fMin = Math.min(this.cornerSize, this.rectF.width() / 2.0f);
        float fDpToPx = ConvertExtensionsKt.dpToPx(1);
        if (this.elevation > 0.0f && (i2 = this.shadowColor) != 0) {
            this.shadowPaint.setColor(i2);
            this.shadowPaint.setShadowLayer(calculateElevation(this.elevation + fDpToPx, fMin), 0.0f, 0.0f, this.shadowColor);
            this.rectF.inset(fDpToPx, fDpToPx);
            canvas.drawRoundRect(this.rectF, fMin, fMin, this.shadowPaint);
            z2 = true;
        }
        if (z2) {
            float f2 = -fDpToPx;
            this.rectF.inset(f2, f2);
        }
        canvas.drawRoundRect(this.rectF, fMin, fMin, this.thumbPaint);
        if (this.strokeWidth > 0.0f) {
            canvas.drawRoundRect(this.rectF, fMin, fMin, this.strokePaint);
        }
    }

    public final float getCornerSize() {
        return this.cornerSize;
    }

    public final float getElevation() {
        return this.elevation;
    }

    @Nullable
    public final ColorStateList getFillColor() {
        return this.fillColor;
    }

    @Override // android.graphics.drawable.Drawable
    @Deprecated(message = "Deprecated in Java", replaceWith = @ReplaceWith(expression = "PixelFormat.TRANSLUCENT", imports = {"android.graphics.PixelFormat"}))
    public int getOpacity() {
        return -3;
    }

    public final int getShadowColor() {
        return this.shadowColor;
    }

    @Nullable
    public final ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    public final float getStrokeWidth() {
        return this.strokeWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        ColorStateList colorStateList = this.fillColor;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        ColorStateList colorStateList2 = this.strokeColor;
        return colorStateList2 != null && colorStateList2.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(@NotNull Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        super.onBoundsChange(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(@NotNull int[] state) {
        Intrinsics.checkNotNullParameter(state, "state");
        return updateColorsForState(state);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int p02) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter p02) {
    }

    public final void setCornerSize(float f2) {
        this.cornerSize = f2;
    }

    public final void setElevation(float f2) {
        this.elevation = f2;
    }

    public final void setFillColor(@Nullable ColorStateList colorStateList) {
        if (Intrinsics.areEqual(colorStateList, this.fillColor)) {
            return;
        }
        this.fillColor = colorStateList;
        int[] state = getState();
        Intrinsics.checkNotNullExpressionValue(state, "state");
        onStateChange(state);
    }

    public final void setShadowColor(int i2) {
        this.shadowColor = i2;
    }

    public final void setStrokeColor(@Nullable ColorStateList colorStateList) {
        if (Intrinsics.areEqual(colorStateList, this.strokeColor)) {
            return;
        }
        this.strokeColor = colorStateList;
        int[] state = getState();
        Intrinsics.checkNotNullExpressionValue(state, "state");
        onStateChange(state);
    }

    public final void setStrokeWidth(float f2) {
        this.strokeWidth = f2;
        this.strokePaint.setStrokeWidth(f2);
    }
}
