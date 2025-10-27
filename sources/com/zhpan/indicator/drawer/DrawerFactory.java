package com.zhpan.indicator.drawer;

import com.zhpan.indicator.option.IndicatorOptions;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/zhpan/indicator/drawer/DrawerFactory;", "", "()V", "createDrawer", "Lcom/zhpan/indicator/drawer/IDrawer;", "indicatorOptions", "Lcom/zhpan/indicator/option/IndicatorOptions;", "indicator_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes8.dex */
public final class DrawerFactory {
    public static final DrawerFactory INSTANCE = new DrawerFactory();

    private DrawerFactory() {
    }

    @NotNull
    public final IDrawer createDrawer(@NotNull IndicatorOptions indicatorOptions) {
        Intrinsics.checkParameterIsNotNull(indicatorOptions, "indicatorOptions");
        int indicatorStyle = indicatorOptions.getIndicatorStyle();
        return indicatorStyle != 2 ? indicatorStyle != 4 ? new CircleDrawer(indicatorOptions) : new RoundRectDrawer(indicatorOptions) : new DashDrawer(indicatorOptions);
    }
}
