package android.content;

import android.content.Context;
import androidx.annotation.Keep;
import androidx.startup.Initializer;
import com.umeng.analytics.pro.d;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import splitties.experimental.ExperimentalSplittiesApi;

@ExperimentalSplittiesApi
@Keep
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0001\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016¨\u0006\t"}, d2 = {"Lsplitties/init/AppCtxInitializer;", "Landroidx/startup/Initializer;", "()V", "create", d.R, "Landroid/content/Context;", "dependencies", "", "", "splitties-appctx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class AppCtxInitializer implements Initializer<AppCtxInitializer> {
    @Override // androidx.startup.Initializer
    @NotNull
    public List dependencies() {
        return CollectionsKt__CollectionsKt.emptyList();
    }

    @Override // androidx.startup.Initializer
    @NotNull
    public AppCtxInitializer create(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        AppCtxKt.injectAsAppCtx(context);
        return this;
    }
}
