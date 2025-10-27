package com.ykb.common_share_lib;

import android.content.SharedPreferences;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\t"}, d2 = {"Lcom/ykb/common_share_lib/GlobalApplication;", "", "getSharedPreferences", "Landroid/content/SharedPreferences;", "name", "", "mode", "", "Companion", "common_share_lib_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public interface GlobalApplication {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/ykb/common_share_lib/GlobalApplication$Companion;", "", "()V", "instance", "Lcom/ykb/common_share_lib/GlobalApplication;", "getInstance", "()Lcom/ykb/common_share_lib/GlobalApplication;", "setInstance", "(Lcom/ykb/common_share_lib/GlobalApplication;)V", "common_share_lib_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static GlobalApplication instance;

        private Companion() {
        }

        @NotNull
        public final GlobalApplication getInstance() {
            GlobalApplication globalApplication = instance;
            if (globalApplication != null) {
                return globalApplication;
            }
            Intrinsics.throwUninitializedPropertyAccessException("instance");
            return null;
        }

        public final void setInstance(@NotNull GlobalApplication globalApplication) {
            Intrinsics.checkNotNullParameter(globalApplication, "<set-?>");
            instance = globalApplication;
        }
    }

    @NotNull
    SharedPreferences getSharedPreferences(@NotNull String name, int mode);
}
