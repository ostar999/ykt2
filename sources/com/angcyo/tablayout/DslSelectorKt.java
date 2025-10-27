package com.angcyo.tablayout;

import android.view.ViewGroup;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a)\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0019\b\u0002\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\b\b¨\u0006\t"}, d2 = {"dslSelector", "Lcom/angcyo/tablayout/DslSelector;", "viewGroup", "Landroid/view/ViewGroup;", "config", "Lkotlin/Function1;", "Lcom/angcyo/tablayout/DslSelectorConfig;", "", "Lkotlin/ExtensionFunctionType;", "TabLayout_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final class DslSelectorKt {
    @NotNull
    public static final DslSelector dslSelector(@NotNull ViewGroup viewGroup, @NotNull Function1<? super DslSelectorConfig, Unit> config) {
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        Intrinsics.checkNotNullParameter(config, "config");
        DslSelector dslSelector = new DslSelector();
        dslSelector.install(viewGroup, config);
        return dslSelector;
    }

    public static /* synthetic */ DslSelector dslSelector$default(ViewGroup viewGroup, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            function1 = new Function1<DslSelectorConfig, Unit>() { // from class: com.angcyo.tablayout.DslSelectorKt.dslSelector.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslSelectorConfig dslSelectorConfig) {
                    invoke2(dslSelectorConfig);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslSelectorConfig dslSelectorConfig) {
                    Intrinsics.checkNotNullParameter(dslSelectorConfig, "$this$null");
                }
            };
        }
        return dslSelector(viewGroup, function1);
    }
}
