package t0;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseUpFetchModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes2.dex */
public final /* synthetic */ class i {
    @NotNull
    public static BaseUpFetchModule a(UpFetchModule upFetchModule, @NotNull BaseQuickAdapter baseQuickAdapter) {
        Intrinsics.checkNotNullParameter(baseQuickAdapter, "baseQuickAdapter");
        return new BaseUpFetchModule(baseQuickAdapter);
    }
}
