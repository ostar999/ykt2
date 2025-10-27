package com.zhpan.indicator.drawer;

import android.graphics.Canvas;
import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lcom/zhpan/indicator/drawer/DashDrawer;", "Lcom/zhpan/indicator/drawer/RectDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "(Lcom/zhpan/indicator/option/IndicatorOptions;)V", "drawRect", "", "canvas", "Landroid/graphics/Canvas;", "rx", "", "ry", "indicator_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes8.dex */
public final class DashDrawer extends RectDrawer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DashDrawer(@NotNull IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
    }

    @Override // com.zhpan.indicator.drawer.RectDrawer
    public void drawRect(@NotNull Canvas canvas, float rx, float ry) {
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        canvas.drawRect(getMRectF(), getMPaint());
    }
}
