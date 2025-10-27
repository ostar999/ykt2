package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.recyclerview.widget.RecyclerView;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010 \u001a\u00020\u001d2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J(\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u00072\u0006\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u00072\u0006\u0010'\u001a\u00020\u0007H\u0014J\u0010\u0010(\u001a\u00020\u001d2\b\b\u0001\u0010)\u001a\u00020\u0007J\u0010\u0010*\u001a\u00020\u001d2\b\b\u0001\u0010)\u001a\u00020\u0007R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\u001a\u0010\u0018R\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/psychiatrygarden/widget/TreeIndicator;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "mBgColor", "mBgPaint", "Landroid/graphics/Paint;", "mBgRect", "Landroid/graphics/RectF;", "mIndicatorColor", "mPaint", "mRadius", "", "mRect", "value", "progress", "getProgress", "()F", "setProgress", "(F)V", "ratio", "setRatio", "viewWidth", "bindRecyclerView", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onSizeChanged", "w", "h", "oldw", "oldh", "setBgColor", "color", "setIndicatorColor", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class TreeIndicator extends View {
    private int mBgColor;

    @NotNull
    private final Paint mBgPaint;

    @NotNull
    private final RectF mBgRect;
    private int mIndicatorColor;

    @NotNull
    private final Paint mPaint;
    private float mRadius;

    @NotNull
    private RectF mRect;
    private float progress;
    private float ratio;
    private int viewWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TreeIndicator(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TreeIndicator(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public TreeIndicator(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Paint paint = new Paint(1);
        this.mBgPaint = paint;
        this.mBgRect = new RectF();
        Paint paint2 = new Paint(1);
        this.mPaint = paint2;
        this.mRect = new RectF();
        this.mBgColor = Color.parseColor("#F5F6FA");
        this.mIndicatorColor = Color.parseColor("#ff4646");
        this.ratio = 0.5f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HIndicator);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…, R.styleable.HIndicator)");
        this.mBgColor = typedArrayObtainStyledAttributes.getColor(0, this.mBgColor);
        this.mIndicatorColor = typedArrayObtainStyledAttributes.getColor(1, this.mIndicatorColor);
        typedArrayObtainStyledAttributes.recycle();
        paint.setColor(this.mBgColor);
        paint.setStyle(Paint.Style.FILL);
        paint2.setColor(this.mIndicatorColor);
        paint2.setStyle(Paint.Style.FILL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindRecyclerView$lambda$0(RecyclerView recyclerView, TreeIndicator this$0, View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        Intrinsics.checkNotNullParameter(recyclerView, "$recyclerView");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setRatio((recyclerView.computeHorizontalScrollExtent() * 1.0f) / recyclerView.computeHorizontalScrollRange());
    }

    private final void setRatio(float f2) {
        this.ratio = f2;
        invalidate();
    }

    public final void bindRecyclerView(@NotNull final RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.widget.TreeIndicator.bindRecyclerView.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NotNull RecyclerView recyclerView2, int dx, int dy) {
                Intrinsics.checkNotNullParameter(recyclerView2, "recyclerView");
                super.onScrolled(recyclerView2, dx, dy);
                TreeIndicator.this.setProgress((recyclerView2.computeHorizontalScrollOffset() * 1.0f) / (recyclerView2.computeHorizontalScrollRange() - recyclerView2.computeHorizontalScrollExtent()));
            }
        });
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.psychiatrygarden.widget.ti
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                TreeIndicator.bindRecyclerView$lambda$0(recyclerView, this, view, i2, i3, i4, i5, i6, i7, i8, i9);
            }
        });
    }

    public final float getProgress() {
        return this.progress;
    }

    @Override // android.view.View
    public void onDraw(@Nullable Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            RectF rectF = this.mBgRect;
            float f2 = this.mRadius;
            canvas.drawRoundRect(rectF, f2, f2, this.mBgPaint);
        }
        int i2 = this.viewWidth;
        float f3 = this.ratio;
        float f4 = i2 * (1.0f - f3) * this.progress;
        RectF rectF2 = this.mBgRect;
        float f5 = rectF2.left + f4;
        this.mRect.set(f5, rectF2.top, (i2 * f3) + f5, rectF2.bottom);
        if (canvas != null) {
            RectF rectF3 = this.mRect;
            float f6 = this.mRadius;
            canvas.drawRoundRect(rectF3, f6, f6, this.mPaint);
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.viewWidth = w2;
        float f2 = h2;
        this.mBgRect.set(0.0f, 0.0f, w2 * 1.0f, 1.0f * f2);
        this.mRadius = f2 / 2.0f;
    }

    public final void setBgColor(@ColorInt int color) {
        this.mBgPaint.setColor(color);
        invalidate();
    }

    public final void setIndicatorColor(@ColorInt int color) {
        this.mPaint.setColor(color);
        invalidate();
    }

    public final void setProgress(float f2) {
        this.progress = f2;
        invalidate();
    }

    public /* synthetic */ TreeIndicator(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
