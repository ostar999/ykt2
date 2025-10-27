package com.catchpig.utils.ext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\u001a\u0014\u0010\u0005\u001a\u00020\u0004*\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0001\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\u0004\u001a\u0014\u0010\u0006\u001a\u00020\u0001*\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0001\u001a\n\u0010\t\u001a\u00020\b*\u00020\u0004\u001a\n\u0010\n\u001a\u00020\b*\u00020\u0004\u001a\n\u0010\u000b\u001a\u00020\b*\u00020\u0004\u001a\n\u0010\f\u001a\u00020\b*\u00020\u0004\u001a\n\u0010\r\u001a\u00020\b*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"DATE_FORMAT", "", "calendar", "Ljava/util/Calendar;", "Ljava/util/Date;", "date", "format", "dayOfMonth", "", "hour", "minute", "month", "second", "year", "utils_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class DateExtKt {

    @NotNull
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @NotNull
    public static final Calendar calendar(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Intrinsics.checkNotNullExpressionValue(calendar, "calendar");
        return calendar;
    }

    @NotNull
    public static final Date date(@NotNull String str, @NotNull String format) throws ParseException {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        Date date = new SimpleDateFormat(format).parse(str);
        Intrinsics.checkNotNull(date);
        return date;
    }

    public static /* synthetic */ Date date$default(String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str2 = "yyyy-MM-dd HH:mm:ss";
        }
        return date(str, str2);
    }

    public static final int dayOfMonth(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        return CalendarExtKt.dayOfMonth(calendar(date));
    }

    @NotNull
    public static final String format(@NotNull Date date, @NotNull String format) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        Intrinsics.checkNotNullParameter(format, "format");
        String str = new SimpleDateFormat(format).format(date);
        Intrinsics.checkNotNullExpressionValue(str, "dateFormat.format(this)");
        return str;
    }

    public static /* synthetic */ String format$default(Date date, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = "yyyy-MM-dd HH:mm:ss";
        }
        return format(date, str);
    }

    public static final int hour(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        return CalendarExtKt.hour(calendar(date));
    }

    public static final int minute(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        return CalendarExtKt.minute(calendar(date));
    }

    public static final int month(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        return CalendarExtKt.month(calendar(date));
    }

    public static final int second(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        return CalendarExtKt.second(calendar(date));
    }

    public static final int year(@NotNull Date date) {
        Intrinsics.checkNotNullParameter(date, "<this>");
        return CalendarExtKt.year(calendar(date));
    }
}
