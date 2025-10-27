package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.core.internal.view.SupportMenu;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000eH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\fH\u0015J(\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\u0014R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/psychiatrygarden/widget/GaussianBlurRoundedView;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "blurBitmap", "Landroid/graphics/Bitmap;", "blurCanvas", "Landroid/graphics/Canvas;", "blurRadius", "", "cornerRadius", "paint", "Landroid/graphics/Paint;", "dpToPx", "dp", "onDraw", "", "canvas", "onSizeChanged", "w", "h", "oldw", "oldh", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class GaussianBlurRoundedView extends View {

    @Nullable
    private Bitmap blurBitmap;

    @Nullable
    private Canvas blurCanvas;
    private float blurRadius;
    private final float cornerRadius;

    @NotNull
    private final Paint paint;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public GaussianBlurRoundedView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public GaussianBlurRoundedView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ GaussianBlurRoundedView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final float dpToPx(float dp) {
        return TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    @Override // android.view.View
    @RequiresApi(31)
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        Canvas canvas2 = this.blurCanvas;
        if (canvas2 != null) {
            canvas2.drawColor(SupportMenu.CATEGORY_MASK);
        }
        Path path = new Path();
        float width = getWidth();
        float height = getHeight();
        float f2 = this.cornerRadius;
        path.addRoundRect(0.0f, 0.0f, width, height, f2, f2, Path.Direction.CW);
        float f3 = this.blurRadius;
        Intrinsics.checkNotNullExpressionValue(RenderEffect.createBlurEffect(f3, f3, Shader.TileMode.CLAMP), "createBlurEffect(\n      ….TileMode.CLAMP\n        )");
        Bitmap bitmap = this.blurBitmap;
        Intrinsics.checkNotNull(bitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.paint);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        this.blurBitmap = Bitmap.createBitmap(w2, h2, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = this.blurBitmap;
        Intrinsics.checkNotNull(bitmap);
        this.blurCanvas = new Canvas(bitmap);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public GaussianBlurRoundedView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        this.paint = new Paint(1);
        this.cornerRadius = dpToPx(16.0f);
        this.blurRadius = dpToPx(16.0f);
        setLayerType(2, null);
    }
}
