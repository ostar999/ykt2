package com.ykb.ebook.util;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"toTenThousand", "", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class NumberUtilKt {
    @NotNull
    public static final String toTenThousand(int i2) {
        if (i2 <= 10000) {
            return String.valueOf(i2);
        }
        int i3 = i2 / 10000;
        int i4 = (i2 % 10000) / 1000;
        if (i3 <= 1 || i4 <= 1) {
            return i3 + "万+";
        }
        return i3 + '.' + i4 + "万+";
    }
}
