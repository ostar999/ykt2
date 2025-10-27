package com.angcyo.tablayout;

import android.graphics.Canvas;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lcom/angcyo/tablayout/ITabIndicatorDraw;", "", "onDrawTabIndicator", "", "tabIndicator", "Lcom/angcyo/tablayout/DslTabIndicator;", "canvas", "Landroid/graphics/Canvas;", "positionOffset", "", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public interface ITabIndicatorDraw {
    void onDrawTabIndicator(@NotNull DslTabIndicator tabIndicator, @NotNull Canvas canvas, float positionOffset);
}
