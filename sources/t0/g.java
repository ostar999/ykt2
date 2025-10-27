package t0;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseDraggableModule;
import com.chad.library.adapter.base.module.DraggableModule;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes2.dex */
public final /* synthetic */ class g {
    @NotNull
    public static BaseDraggableModule a(DraggableModule draggableModule, @NotNull BaseQuickAdapter baseQuickAdapter) {
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "baseQuickAdapter");
        return new BaseDraggableModule(baseQuickAdapter);
    }
}
