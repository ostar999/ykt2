package com.catchpig.utils.manager;

import android.content.Context;
import android.content.SharedPreferences;
import com.catchpig.utils.ext.LogExtKt;
import com.umeng.analytics.pro.d;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u000e2\u00020\u0001:\u0002\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/catchpig/utils/manager/ContextManager;", "", "()V", d.R, "Landroid/content/Context;", "getContext", "getSharedPreferences", "Landroid/content/SharedPreferences;", "name", "", "mode", "", "init", "", "Companion", "ContextManagerHolder", "utils_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ContextManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private Context context;

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/catchpig/utils/manager/ContextManager$Companion;", "", "()V", "getInstance", "Lcom/catchpig/utils/manager/ContextManager;", "utils_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final ContextManager getInstance() {
            return ContextManagerHolder.INSTANCE.getHolder();
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/utils/manager/ContextManager$ContextManagerHolder;", "", "()V", "holder", "Lcom/catchpig/utils/manager/ContextManager;", "getHolder", "()Lcom/catchpig/utils/manager/ContextManager;", "utils_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class ContextManagerHolder {

        @NotNull
        public static final ContextManagerHolder INSTANCE = new ContextManagerHolder();

        @NotNull
        private static final ContextManager holder = new ContextManager();

        private ContextManagerHolder() {
        }

        @NotNull
        public final ContextManager getHolder() {
            return holder;
        }
    }

    @NotNull
    public final Context getContext() {
        if (this.context == null) {
            LogExtKt.loge("ContextManager context未初始化", "ContextManager");
        }
        Context context = this.context;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException(d.R);
        return null;
    }

    @Deprecated(message = "请勿使用")
    @NotNull
    public final SharedPreferences getSharedPreferences(@NotNull String name, int mode) {
        Intrinsics.checkNotNullParameter(name, "name");
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(d.R);
            context = null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, mode);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "context.getSharedPreferences(name, mode)");
        return sharedPreferences;
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }
}
