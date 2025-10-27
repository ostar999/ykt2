package com.ykb.ebook.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001aH\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n¨\u0006\u000b"}, d2 = {"debounce", "Lcom/ykb/ebook/util/Debounce;", ExifInterface.GPS_DIRECTION_TRUE, "wait", "", "maxWait", "leading", "", "trailing", com.alipay.sdk.authjs.a.f3174g, "Lkotlin/Function0;", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class DebounceKt {
    @NotNull
    public static final <T> Debounce<T> debounce(long j2, long j3, boolean z2, boolean z3, @NotNull Function0<? extends T> func) {
        Intrinsics.checkNotNullParameter(func, "func");
        return new Debounce<>(j2, j3, z2, z3, func);
    }

    public static /* synthetic */ Debounce debounce$default(long j2, long j3, boolean z2, boolean z3, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            j2 = 0;
        }
        long j4 = j2;
        if ((i2 & 2) != 0) {
            j3 = -1;
        }
        long j5 = j3;
        if ((i2 & 4) != 0) {
            z2 = false;
        }
        boolean z4 = z2;
        if ((i2 & 8) != 0) {
            z3 = true;
        }
        return debounce(j4, j5, z4, z3, function0);
    }
}
