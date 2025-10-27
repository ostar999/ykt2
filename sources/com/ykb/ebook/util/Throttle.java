package com.ykb.ebook.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/util/Throttle;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/ykb/ebook/util/Debounce;", "wait", "", "leading", "", "trailing", com.alipay.sdk.authjs.a.f3174g, "Lkotlin/Function0;", "(JZZLkotlin/jvm/functions/Function0;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class Throttle<T> extends Debounce<T> {
    public /* synthetic */ Throttle(long j2, boolean z2, boolean z3, Function0 function0, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j2, (i2 & 2) != 0 ? true : z2, (i2 & 4) != 0 ? true : z3, function0);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Throttle(long j2, boolean z2, boolean z3, @NotNull Function0<? extends T> func) {
        super(j2, j2, z2, z3, func);
        Intrinsics.checkNotNullParameter(func, "func");
    }
}
