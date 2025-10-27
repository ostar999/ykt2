package com.necer.utils.hutool;

import java.time.LocalDate;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nJ\u001e\u0010\u000f\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\nR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/necer/utils/hutool/GanZhi;", "", "()V", "GAN", "", "", "[Ljava/lang/String;", "ZHI", "cyclicalm", "num", "", "getGanzhiOfDay", "year", "month", "day", "getGanzhiOfMonth", "getGanzhiOfYear", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class GanZhi {

    @NotNull
    public static final GanZhi INSTANCE = new GanZhi();

    @NotNull
    private static final String[] GAN = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};

    @NotNull
    private static final String[] ZHI = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    private GanZhi() {
    }

    @NotNull
    public final String cyclicalm(int num) {
        return GAN[num % 10] + ZHI[num % 12];
    }

    @NotNull
    public final String getGanzhiOfDay(int year, int month, int day) {
        return cyclicalm((int) (((LocalDate.of(year, month, day).toEpochDay() - 1) - LunarInfo.INSTANCE.getBASE_DAY()) + 41));
    }

    @NotNull
    public final String getGanzhiOfMonth(int year, int month, int day) {
        int term = SolarTerms.INSTANCE.getTerm(year, (month * 2) - 1);
        int i2 = ((year - 1900) * 12) + month + 11;
        if (day >= term) {
            i2++;
        }
        return cyclicalm(i2);
    }

    @NotNull
    public final String getGanzhiOfYear(int year) {
        return cyclicalm((year - 1900) + 36);
    }
}
