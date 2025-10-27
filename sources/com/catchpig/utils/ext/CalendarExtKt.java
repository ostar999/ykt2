package com.catchpig.utils.ext;

import java.util.Calendar;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0001\u001a\u0014\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0005*\u00020\u0001\u001a\n\u0010\u0007\u001a\u00020\u0005*\u00020\u0001\u001a\n\u0010\b\u001a\u00020\u0005*\u00020\u0001\u001a\n\u0010\t\u001a\u00020\u0005*\u00020\u0001\u001a\n\u0010\n\u001a\u00020\u0005*\u00020\u0001¨\u0006\u000b"}, d2 = {"calendar", "Ljava/util/Calendar;", "", "format", "dayOfMonth", "", "hour", "minute", "month", "second", "year", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CalendarExtKt {
    @NotNull
    public static final Calendar calendar(@NotNull String str, @NotNull String format) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        return DateExtKt.calendar(DateExtKt.date(str, format));
    }

    public static /* synthetic */ Calendar calendar$default(String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str2 = "yyyy-MM-dd HH:mm:ss";
        }
        return calendar(str, str2);
    }

    public static final int dayOfMonth(@NotNull Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        return calendar.get(5);
    }

    @NotNull
    public static final String format(@NotNull Calendar calendar, @NotNull String format) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        Date time = calendar.getTime();
        Intrinsics.checkNotNullExpressionValue(time, "time");
        return DateExtKt.format(time, format);
    }

    public static /* synthetic */ String format$default(Calendar calendar, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        return format(calendar, str);
    }

    public static final int hour(@NotNull Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        return calendar.get(11);
    }

    public static final int minute(@NotNull Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        return calendar.get(12);
    }

    public static final int month(@NotNull Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        return calendar.get(2) + 1;
    }

    public static final int second(@NotNull Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        return calendar.get(13);
    }

    public static final int year(@NotNull Calendar calendar) {
        Intrinsics.checkNotNullParameter(calendar, "<this>");
        return calendar.get(1);
    }
}
