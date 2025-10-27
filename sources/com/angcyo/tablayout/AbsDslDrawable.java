package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.plv.livescenes.document.PLVDocumentWebProcessor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0015\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 f2\u00020\u0001:\u0001fB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202H\u0016J\b\u00103\u001a\u00020\u0010H\u0016J\n\u00104\u001a\u0004\u0018\u000105H\u0016J\u0010\u00106\u001a\u0002002\u0006\u00107\u001a\u00020\bH\u0016J\b\u00108\u001a\u00020\u0010H\u0016J\b\u00109\u001a\u00020\u0010H\u0016J\b\u0010:\u001a\u00020\u0010H\u0016J\b\u0010;\u001a\u00020\u0010H\u0016J\b\u0010<\u001a\u00020\u0010H\u0016J\u001c\u0010=\u001a\u0002002\u0006\u0010>\u001a\u00020?2\n\b\u0002\u0010@\u001a\u0004\u0018\u00010AH\u0016J\b\u0010B\u001a\u00020\u0016H\u0016J\b\u0010C\u001a\u00020\u0001H\u0016J\u0010\u0010D\u001a\u0002002\u0006\u0010E\u001a\u00020\bH\u0014J\u0010\u0010F\u001a\u00020\u00162\u0006\u0010G\u001a\u00020\u0010H\u0014J\u0010\u0010H\u001a\u00020\u00162\u0006\u0010I\u001a\u00020JH\u0014J\u0010\u0010K\u001a\u0002002\u0006\u0010L\u001a\u00020\u0010H\u0016J\u0010\u0010M\u001a\u0002002\u0006\u0010E\u001a\u00020\bH\u0016J(\u0010M\u001a\u0002002\u0006\u0010N\u001a\u00020\u00102\u0006\u0010O\u001a\u00020\u00102\u0006\u0010P\u001a\u00020\u00102\u0006\u0010Q\u001a\u00020\u0010H\u0016J\u0012\u0010R\u001a\u0002002\b\u0010S\u001a\u0004\u0018\u000105H\u0016J\u0010\u0010T\u001a\u0002002\u0006\u0010U\u001a\u00020\u0016H\u0016J\u0010\u0010V\u001a\u0002002\u0006\u0010W\u001a\u00020\u0016H\u0016J\u0018\u0010X\u001a\u0002002\u0006\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020ZH\u0016J(\u0010\\\u001a\u0002002\u0006\u0010N\u001a\u00020\u00102\u0006\u0010O\u001a\u00020\u00102\u0006\u0010P\u001a\u00020\u00102\u0006\u0010Q\u001a\u00020\u0010H\u0016J\u0012\u0010]\u001a\u0002002\b\u0010^\u001a\u0004\u0018\u00010_H\u0016J\u0012\u0010`\u001a\u0002002\b\u0010a\u001a\u0004\u0018\u00010bH\u0016J\u0012\u0010c\u001a\u0002002\b\u0010d\u001a\u0004\u0018\u00010eH\u0016R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0019\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0012R\u0011\u0010\u001b\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012R\u0011\u0010\u001d\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0012R\u0011\u0010\u001f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b \u0010\u0012R\u001b\u0010!\u001a\u00020\"8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b#\u0010$R\u0011\u0010'\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b(\u0010\u0012R\u0011\u0010)\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b*\u0010\u0012R\u0011\u0010+\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b,\u0010\u0012R\u0011\u0010-\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b.\u0010\u0012¨\u0006g"}, d2 = {"Lcom/angcyo/tablayout/AbsDslDrawable;", "Landroid/graphics/drawable/Drawable;", "()V", "attachView", "Landroid/view/View;", "getAttachView", "()Landroid/view/View;", "drawRect", "Landroid/graphics/Rect;", "getDrawRect", "()Landroid/graphics/Rect;", "drawRectF", "Landroid/graphics/RectF;", "getDrawRectF", "()Landroid/graphics/RectF;", "drawType", "", "getDrawType", "()I", PLVDocumentWebProcessor.SETDRAWTYPE, "(I)V", "isInEditMode", "", "()Z", "isViewRtl", "paddingBottom", "getPaddingBottom", "paddingLeft", "getPaddingLeft", "paddingRight", "getPaddingRight", "paddingTop", "getPaddingTop", "textPaint", "Landroid/text/TextPaint;", "getTextPaint", "()Landroid/text/TextPaint;", "textPaint$delegate", "Lkotlin/Lazy;", "viewDrawHeight", "getViewDrawHeight", "viewDrawWidth", "getViewDrawWidth", "viewHeight", "getViewHeight", "viewWidth", "getViewWidth", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getAlpha", "getColorFilter", "Landroid/graphics/ColorFilter;", "getHotspotBounds", "outRect", "getIntrinsicHeight", "getIntrinsicWidth", "getMinimumHeight", "getMinimumWidth", "getOpacity", "initAttribute", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "isFilterBitmap", "mutate", "onBoundsChange", "bounds", "onLevelChange", DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "onStateChange", "state", "", "setAlpha", "alpha", "setBounds", "left", PLVDanmakuInfo.FONTMODE_TOP, "right", PLVDanmakuInfo.FONTMODE_BOTTOM, "setColorFilter", "colorFilter", "setDither", "dither", "setFilterBitmap", "filter", "setHotspot", "x", "", "y", "setHotspotBounds", "setTintBlendMode", "blendMode", "Landroid/graphics/BlendMode;", "setTintList", "tint", "Landroid/content/res/ColorStateList;", "setTintMode", "tintMode", "Landroid/graphics/PorterDuff$Mode;", "Companion", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public abstract class AbsDslDrawable extends Drawable {
    public static final int DRAW_TYPE_DRAW_AFTER = 1;
    public static final int DRAW_TYPE_DRAW_BEFORE = 2;
    public static final int DRAW_TYPE_DRAW_NONE = 0;
    public static final int DRAW_TYPE_ON_DRAW_AFTER = 4;
    public static final int DRAW_TYPE_ON_DRAW_BEFORE = 8;

    /* renamed from: textPaint$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy textPaint = LazyKt__LazyJVMKt.lazy(new Function0<TextPaint>() { // from class: com.angcyo.tablayout.AbsDslDrawable$textPaint$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final TextPaint invoke() {
            TextPaint textPaint = new TextPaint(1);
            textPaint.setFilterBitmap(true);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextSize(12 * LibExKt.getDp());
            return textPaint;
        }
    });

    @NotNull
    private final Rect drawRect = new Rect();

    @NotNull
    private final RectF drawRectF = new RectF();
    private int drawType = 4;

    public static /* synthetic */ void initAttribute$default(AbsDslDrawable absDslDrawable, Context context, AttributeSet attributeSet, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: initAttribute");
        }
        if ((i2 & 2) != 0) {
            attributeSet = null;
        }
        absDslDrawable.initAttribute(context, attributeSet);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return getTextPaint().getAlpha();
    }

    @Nullable
    public final View getAttachView() {
        if (!(getCallback() instanceof View)) {
            return null;
        }
        Drawable.Callback callback = getCallback();
        if (callback instanceof View) {
            return (View) callback;
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    @Nullable
    public ColorFilter getColorFilter() {
        return getTextPaint().getColorFilter();
    }

    @NotNull
    public final Rect getDrawRect() {
        return this.drawRect;
    }

    @NotNull
    public final RectF getDrawRectF() {
        return this.drawRectF;
    }

    public final int getDrawType() {
        return this.drawType;
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(@NotNull Rect outRect) {
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        super.getHotspotBounds(outRect);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return getAlpha() < 255 ? -3 : -1;
    }

    public final int getPaddingBottom() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.getPaddingBottom();
        }
        return 0;
    }

    public final int getPaddingLeft() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.getPaddingLeft();
        }
        return 0;
    }

    public final int getPaddingRight() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.getPaddingRight();
        }
        return 0;
    }

    public final int getPaddingTop() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.getPaddingTop();
        }
        return 0;
    }

    @NotNull
    public final TextPaint getTextPaint() {
        return (TextPaint) this.textPaint.getValue();
    }

    public final int getViewDrawHeight() {
        return (getViewHeight() - getPaddingTop()) - getPaddingBottom();
    }

    public final int getViewDrawWidth() {
        return (getViewWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public final int getViewHeight() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.getMeasuredHeight();
        }
        return 0;
    }

    public final int getViewWidth() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.getMeasuredWidth();
        }
        return 0;
    }

    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isFilterBitmap() {
        return getTextPaint().isFilterBitmap();
    }

    public final boolean isInEditMode() {
        View attachView = getAttachView();
        if (attachView != null) {
            return attachView.isInEditMode();
        }
        return false;
    }

    public final boolean isViewRtl() {
        if (getAttachView() != null) {
            View attachView = getAttachView();
            Intrinsics.checkNotNull(attachView);
            if (ViewCompat.getLayoutDirection(attachView) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    @NotNull
    public Drawable mutate() {
        Drawable drawableMutate = super.mutate();
        Intrinsics.checkNotNullExpressionValue(drawableMutate, "super.mutate()");
        return drawableMutate;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(@NotNull Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        super.onBoundsChange(bounds);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLevelChange(int level) {
        return super.onLevelChange(level);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(@NotNull int[] state) {
        Intrinsics.checkNotNullParameter(state, "state");
        return super.onStateChange(state);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        if (getTextPaint().getAlpha() != alpha) {
            getTextPaint().setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int left, int top2, int right, int bottom) {
        super.setBounds(left, top2, right, bottom);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        getTextPaint().setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean dither) {
        getTextPaint().setDither(dither);
        invalidateSelf();
    }

    public final void setDrawType(int i2) {
        this.drawType = i2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean filter) {
        getTextPaint().setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float x2, float y2) {
        super.setHotspot(x2, y2);
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int left, int top2, int right, int bottom) {
        super.setHotspotBounds(left, top2, right, bottom);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintBlendMode(@Nullable BlendMode blendMode) {
        super.setTintBlendMode(blendMode);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(@Nullable ColorStateList tint) {
        super.setTintList(tint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(@Nullable PorterDuff.Mode tintMode) {
        super.setTintMode(tintMode);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(@NotNull Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        super.setBounds(bounds);
    }
}
