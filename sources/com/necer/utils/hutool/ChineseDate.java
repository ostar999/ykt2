package com.necer.utils.hutool;

import java.time.LocalDate;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0013\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\rH\u0002J\u0006\u0010\u001f\u001a\u00020\u0007J\u0006\u0010 \u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u0019J\u0006\u0010\"\u001a\u00020\u0007J \u0010\"\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010!\u001a\u00020\u0019H\u0002J\b\u0010#\u001a\u0004\u0018\u00010\u0007J\u0012\u0010#\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001e\u001a\u00020\rH\u0002J\u0006\u0010$\u001a\u00020\u0007J\b\u0010%\u001a\u0004\u0018\u00010\u0007J\u0006\u0010&\u001a\u00020\rJ\b\u0010'\u001a\u0004\u0018\u00010\u0007J\b\u0010(\u001a\u0004\u0018\u00010\u0007J\u0006\u0010)\u001a\u00020\u0007J\b\u0010*\u001a\u00020\u0007H\u0016J\u0006\u0010+\u001a\u00020\u0007R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\u0014\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\u0016\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000f¨\u0006,"}, d2 = {"Lcom/necer/utils/hutool/ChineseDate;", "", "localDate", "Ljava/time/LocalDate;", "(Ljava/time/LocalDate;)V", "CHINESE_ZODIACS", "", "", "[Ljava/lang/String;", "DAY_NAME", "MONTH_NAME", "MONTH_NAME_TRADITIONAL", "chineseYear", "", "getChineseYear", "()I", "day", "getDay", "gregorianDay", "getGregorianDay", "gregorianMonthBase1", "getGregorianMonthBase1", "gregorianYear", "getGregorianYear", "isLeapMonth", "", "()Z", "month", "getMonth", "cyclicalm", "year", "getChineseDay", "getChineseMonth", "isTraditional", "getChineseMonthName", "getChineseZodiac", "getCyclical", "getCyclicalYMD", "getGregorianMonth", "getLunarFestivals", "getSolarFestivals", "getTerm", "toString", "toStringNormal", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class ChineseDate {

    @NotNull
    private final String[] CHINESE_ZODIACS;

    @NotNull
    private final String[] DAY_NAME;

    @NotNull
    private final String[] MONTH_NAME;

    @NotNull
    private final String[] MONTH_NAME_TRADITIONAL;
    private final int chineseYear;
    private final int day;
    private final int gregorianDay;
    private final int gregorianMonthBase1;
    private final int gregorianYear;
    private final boolean isLeapMonth;
    private final int month;

    public ChineseDate(@NotNull LocalDate localDate) {
        LunarInfo lunarInfo;
        boolean z2;
        int iMonthDays;
        int iYearDays;
        Intrinsics.checkNotNullParameter(localDate, "localDate");
        this.DAY_NAME = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        this.MONTH_NAME = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        this.MONTH_NAME_TRADITIONAL = new String[]{"正", "二", "三", "四", "五", "六", "七", "八", "九", "寒", "冬", "腊"};
        this.CHINESE_ZODIACS = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        this.gregorianYear = localDate.getYear();
        this.gregorianMonthBase1 = localDate.getMonthValue();
        this.gregorianDay = localDate.getDayOfMonth();
        int epochDay = (int) (localDate.toEpochDay() - LunarInfo.INSTANCE.getBASE_DAY());
        int i2 = 1900;
        while (true) {
            lunarInfo = LunarInfo.INSTANCE;
            if (i2 > lunarInfo.getMAX_YEAR() || epochDay < (iYearDays = lunarInfo.yearDays(i2))) {
                break;
            }
            epochDay -= iYearDays;
            i2++;
        }
        this.chineseYear = i2;
        int iLeapMonth = lunarInfo.leapMonth(i2);
        boolean z3 = false;
        boolean z4 = false;
        int i3 = 1;
        while (true) {
            if (i3 >= 13) {
                break;
            }
            if (iLeapMonth <= 0 || i3 != iLeapMonth + 1) {
                z2 = z4;
                iMonthDays = LunarInfo.INSTANCE.monthDays(this.chineseYear, z4 ? i3 - 1 : i3);
            } else {
                iMonthDays = LunarInfo.INSTANCE.leapDays(this.chineseYear);
                z2 = true;
            }
            if (epochDay < iMonthDays) {
                z4 = z2;
                break;
            } else {
                epochDay -= iMonthDays;
                i3++;
                z4 = z2;
            }
        }
        if (iLeapMonth > 0 && i3 == iLeapMonth + 1) {
            z3 = true;
        }
        this.isLeapMonth = z3;
        if (z4 && !z3) {
            i3--;
        }
        this.month = i3;
        this.day = epochDay + 1;
    }

    private final String cyclicalm(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        GanZhi ganZhi = GanZhi.INSTANCE;
        sb.append(ganZhi.getGanzhiOfYear(this.chineseYear));
        sb.append((char) 24180);
        sb.append(ganZhi.getGanzhiOfMonth(year, month, day));
        sb.append((char) 26376);
        sb.append(ganZhi.getGanzhiOfDay(year, month, day));
        sb.append((char) 26085);
        return sb.toString();
    }

    @NotNull
    public final String getChineseDay() {
        String[] strArr = {"初", "十", "廿", "卅"};
        int i2 = this.day;
        int i3 = i2 % 10 == 0 ? 9 : (i2 % 10) - 1;
        if (i2 > 30) {
            return "";
        }
        if (i2 == 10) {
            return "初十";
        }
        if (i2 == 20) {
            return "二十";
        }
        if (i2 == 30) {
            return "三十";
        }
        return strArr[this.day / 10] + this.DAY_NAME[i3];
    }

    @NotNull
    public final String getChineseMonth() {
        return getChineseMonth(false);
    }

    @NotNull
    public final String getChineseMonthName() {
        return getChineseMonth(true);
    }

    public final int getChineseYear() {
        return this.chineseYear;
    }

    @Nullable
    public final String getChineseZodiac() {
        return getChineseZodiac(this.chineseYear);
    }

    @NotNull
    public final String getCyclical() {
        return GanZhi.INSTANCE.getGanzhiOfYear(this.chineseYear);
    }

    @Nullable
    public final String getCyclicalYMD() {
        int i2;
        int i3;
        int i4 = this.gregorianYear;
        if (i4 < 1900 || (i2 = this.gregorianMonthBase1) <= 0 || (i3 = this.gregorianDay) <= 0) {
            return null;
        }
        return cyclicalm(i4, i2, i3);
    }

    public final int getDay() {
        return this.day;
    }

    public final int getGregorianDay() {
        return this.gregorianDay;
    }

    public final int getGregorianMonth() {
        return this.gregorianMonthBase1 - 1;
    }

    public final int getGregorianMonthBase1() {
        return this.gregorianMonthBase1;
    }

    public final int getGregorianYear() {
        return this.gregorianYear;
    }

    @Nullable
    public final String getLunarFestivals() {
        return LunarFestival.INSTANCE.getFestivals(this.chineseYear, this.month, this.day);
    }

    public final int getMonth() {
        return this.month;
    }

    @Nullable
    public final String getSolarFestivals() {
        return SolarFestival.INSTANCE.getFestivals(this.gregorianMonthBase1, this.gregorianDay);
    }

    @NotNull
    public final String getTerm() {
        return SolarTerms.INSTANCE.getTerm(this.gregorianYear, this.gregorianMonthBase1, this.gregorianDay);
    }

    /* renamed from: isLeapMonth, reason: from getter */
    public final boolean getIsLeapMonth() {
        return this.isLeapMonth;
    }

    @NotNull
    public String toString() {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%s%s年 %s%s", Arrays.copyOf(new Object[]{getCyclical(), getChineseZodiac(), getChineseMonthName(), getChineseDay()}, 4));
        Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
        return str;
    }

    @NotNull
    public final String toStringNormal() {
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(this.chineseYear);
        objArr[1] = Integer.valueOf(this.isLeapMonth ? this.month - 1 : this.month);
        objArr[2] = Integer.valueOf(this.day);
        String str = String.format("%04d-%02d-%02d", Arrays.copyOf(objArr, 3));
        Intrinsics.checkNotNullExpressionValue(str, "format(format, *args)");
        return str;
    }

    private final String getChineseMonthName(boolean isLeapMonth, int month, boolean isTraditional) {
        StringBuilder sb = new StringBuilder();
        sb.append(isLeapMonth ? "闰" : "");
        sb.append((isTraditional ? this.MONTH_NAME_TRADITIONAL : this.MONTH_NAME)[month - 1]);
        sb.append((char) 26376);
        return sb.toString();
    }

    private final String getChineseZodiac(int year) {
        if (year < 1900) {
            return null;
        }
        String[] strArr = this.CHINESE_ZODIACS;
        return strArr[(year - 1900) % strArr.length];
    }

    @NotNull
    public final String getChineseMonth(boolean isTraditional) {
        boolean z2 = this.isLeapMonth;
        int i2 = this.month;
        if (z2) {
            i2--;
        }
        return getChineseMonthName(z2, i2, isTraditional);
    }
}
