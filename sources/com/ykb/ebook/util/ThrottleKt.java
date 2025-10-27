package com.ykb.ebook.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a>\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\t¨\u0006\n"}, d2 = {"throttle", "Lcom/ykb/ebook/util/Throttle;", ExifInterface.GPS_DIRECTION_TRUE, "wait", "", "leading", "", "trailing", com.alipay.sdk.authjs.a.f3174g, "Lkotlin/Function0;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ThrottleKt {
    @NotNull
    public static final <T> Throttle<T> throttle(long j2, boolean z2, boolean z3, @NotNull Function0<? extends T> func) {
        Intrinsics.checkNotNullParameter(func, "func");
        return new Throttle<>(j2, z2, z3, func);
    }

    public static /* synthetic */ Throttle throttle$default(long j2, boolean z2, boolean z3, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            j2 = 0;
        }
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        if ((i2 & 4) != 0) {
            z3 = true;
        }
        return throttle(j2, z2, z3, function0);
    }
}
