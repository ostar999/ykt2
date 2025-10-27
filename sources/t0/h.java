package t0;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes2.dex */
public final /* synthetic */ class h {
    @NotNull
    public static BaseLoadMoreModule a(LoadMoreModule loadMoreModule, @NotNull BaseQuickAdapter baseQuickAdapter) {
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "baseQuickAdapter");
        return new BaseLoadMoreModule(baseQuickAdapter);
    }
}
