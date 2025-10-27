package com.necer.utils;

import com.heytap.mcssdk.constant.b;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u001e\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\u0012\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\u0014\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0010\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0006¨\u0006\u0017"}, d2 = {"Lcom/necer/utils/NDateUtil;", "", "()V", "getIntervalMonths", "", b.f7194s, "Ljava/time/LocalDate;", b.f7195t, "getIntervalWeek", "date1", "date2", "weekType", "getMonthDate", "", "localDate", "isAllMonthSixLine", "", "getWeekDate", "isEqualsMonth", "isLastMonth", "isNextMonth", "isToday", "date", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class NDateUtil {

    @NotNull
    public static final NDateUtil INSTANCE = new NDateUtil();

    private NDateUtil() {
    }

    public final int getIntervalMonths(@NotNull LocalDate startDate, @NotNull LocalDate endDate) {
        Intrinsics.checkNotNullParameter(startDate, "startDate");
        Intrinsics.checkNotNullParameter(endDate, "endDate");
        if (startDate.isAfter(endDate)) {
            return -getIntervalMonths(endDate, startDate);
        }
        return (int) ChronoUnit.MONTHS.between(LocalDate.of(startDate.getYear(), startDate.getMonth(), 1), LocalDate.of(endDate.getYear(), endDate.getMonth(), 1));
    }

    public final int getIntervalWeek(@NotNull LocalDate date1, @NotNull LocalDate date2, int weekType) {
        Intrinsics.checkNotNullParameter(date1, "date1");
        Intrinsics.checkNotNullParameter(date2, "date2");
        DayOfWeek dayOfWeek = weekType == 301 ? DayOfWeek.MONDAY : DayOfWeek.SUNDAY;
        return (int) (ChronoUnit.DAYS.between(date1.with(TemporalAdjusters.previousOrSame(dayOfWeek)), date2.with(TemporalAdjusters.previousOrSame(dayOfWeek))) / 7);
    }

    @NotNull
    public final List<LocalDate> getMonthDate(@NotNull LocalDate localDate, int weekType, boolean isAllMonthSixLine) {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        DayOfWeek dayOfWeek = weekType == 301 ? DayOfWeek.MONDAY : DayOfWeek.SUNDAY;
        LocalDate localDateWith = localDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate localDateWith2 = localDate.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate localDateMinusDays = localDateWith.minusDays((localDateWith.getDayOfWeek().getValue() - dayOfWeek.getValue()) + (dayOfWeek.getValue() > localDateWith.getDayOfWeek().getValue() ? 7 : 0));
        ArrayList arrayList = new ArrayList();
        while (arrayList.size() < 35) {
            arrayList.add(localDateMinusDays);
            localDateMinusDays = localDateMinusDays.plusDays(1L);
        }
        if (isAllMonthSixLine || ((LocalDate) arrayList.get(arrayList.size() - 1)).isBefore(localDateWith2)) {
            while (arrayList.size() < 42) {
                arrayList.add(localDateMinusDays);
                localDateMinusDays = localDateMinusDays.plusDays(1L);
            }
        }
        return arrayList;
    }

    @NotNull
    public final List<LocalDate> getWeekDate(@NotNull LocalDate localDate, int weekType) {
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        LocalDate localDateWith = localDate.with(TemporalAdjusters.previousOrSame(weekType == 301 ? DayOfWeek.MONDAY : DayOfWeek.SUNDAY));
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 7; i2++) {
            arrayList.add(localDateWith.plusDays(i2));
        }
        return arrayList;
    }

    public final boolean isEqualsMonth(@NotNull LocalDate date1, @NotNull LocalDate date2) {
        Intrinsics.checkNotNullParameter(date1, "date1");
        Intrinsics.checkNotNullParameter(date2, "date2");
        return date1.getYear() == date2.getYear() && date1.getMonthValue() == date2.getMonthValue();
    }

    public final boolean isLastMonth(@NotNull LocalDate date1, @NotNull LocalDate date2) {
        Intrinsics.checkNotNullParameter(date1, "date1");
        Intrinsics.checkNotNullParameter(date2, "date2");
        return date1.getMonthValue() == date2.plusMonths(-1L).getMonthValue();
    }

    public final boolean isNextMonth(@NotNull LocalDate date1, @NotNull LocalDate date2) {
        Intrinsics.checkNotNullParameter(date1, "date1");
        Intrinsics.checkNotNullParameter(date2, "date2");
        return date1.getMonthValue() == date2.plusMonths(1L).getMonthValue();
    }

    public final boolean isToday(@Nullable LocalDate date) {
        return LocalDate.now().isEqual(date);
    }
}
