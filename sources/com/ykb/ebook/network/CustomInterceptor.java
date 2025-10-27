package com.ykb.ebook.network;

import cn.hutool.core.text.StrPool;
import com.mobile.auth.BuildConfig;
import com.ykb.ebook.util.JsonUtilKt;
import com.ykb.ebook.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/network/CustomInterceptor;", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "()V", BuildConfig.FLAVOR_type, "", "message", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class CustomInterceptor implements HttpLoggingInterceptor.Logger {
    @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
    public void log(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        boolean z2 = false;
        boolean z3 = StringsKt__StringsJVMKt.startsWith$default(message, StrPool.DELIM_START, false, 2, null) && StringsKt__StringsJVMKt.endsWith$default(message, "}", false, 2, null);
        if (StringsKt__StringsJVMKt.startsWith$default(message, StrPool.BRACKET_START, false, 2, null) && StringsKt__StringsJVMKt.endsWith$default(message, StrPool.BRACKET_END, false, 2, null)) {
            z2 = true;
        }
        if (z3 || z2) {
            message = JsonUtilKt.formatJson(JsonUtilKt.decodeUnicode(message));
        }
        Log.INSTANCE.logD(RetrofitClientKt.tag, message);
    }
}
