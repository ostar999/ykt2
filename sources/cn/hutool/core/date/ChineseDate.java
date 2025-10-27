package cn.hutool.core.date;

import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.date.chinese.ChineseMonth;
import cn.hutool.core.date.chinese.GanZhi;
import cn.hutool.core.date.chinese.LunarFestival;
import cn.hutool.core.date.chinese.LunarInfo;
import cn.hutool.core.date.chinese.SolarTerms;
import cn.hutool.core.text.CharSequenceUtil;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class ChineseDate {
    private final int day;
    private final int gday;
    private final int gmonthBase1;
    private final int gyear;
    private final boolean isLeapMonth;
    private final int month;
    private final int year;

    public ChineseDate(Date date) {
        this(LocalDateTimeUtil.ofDate(date.toInstant()));
    }

    private String cyclicalm(int i2, int i3, int i4) {
        return CharSequenceUtil.format("{}年{}月{}日", GanZhi.getGanzhiOfYear(this.year), GanZhi.getGanzhiOfMonth(i2, i3, i4), GanZhi.getGanzhiOfDay(i2, i3, i4));
    }

    private DateTime lunar2solar(int i2, int i3, int i4, boolean z2) {
        if (i2 != 2100 || i3 != 12 || i4 <= 1) {
            if (i2 != 1900 || i3 != 1 || i4 >= 31) {
                int iMonthDays = LunarInfo.monthDays(i2, i3);
                int iLeapDays = z2 ? LunarInfo.leapDays(i2) : iMonthDays;
                if (i2 < 1900 || i2 > 2100 || i4 > iLeapDays) {
                    return null;
                }
                boolean z3 = false;
                int iMonthDays2 = 0;
                for (int i5 = 1900; i5 < i2; i5++) {
                    iMonthDays2 += LunarInfo.yearDays(i5);
                }
                for (int i6 = 1; i6 < i3; i6++) {
                    int iLeapMonth = LunarInfo.leapMonth(i2);
                    if (!z3 && iLeapMonth <= i6 && iLeapMonth > 0) {
                        iMonthDays2 += LunarInfo.leapDays(i2);
                        z3 = true;
                    }
                    iMonthDays2 += LunarInfo.monthDays(i2, i6);
                }
                if (z2) {
                    iMonthDays2 += iMonthDays;
                }
                return DateUtil.date((((iMonthDays2 + i4) - 31) * 86400000) - 2203804800000L);
            }
        }
        return null;
    }

    public String getChineseDay() {
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
        return strArr[this.day / 10] + NumberChineseFormatter.format(i3 + 1, false);
    }

    public String getChineseMonth() {
        return getChineseMonth(false);
    }

    public String getChineseMonthName() {
        return getChineseMonth(true);
    }

    public int getChineseYear() {
        return this.year;
    }

    public String getChineseZodiac() {
        return Zodiac.getChineseZodiac(this.year);
    }

    public String getCyclical() {
        return GanZhi.getGanzhiOfYear(this.year);
    }

    public String getCyclicalYMD() {
        int i2;
        int i3;
        int i4 = this.gyear;
        if (i4 < 1900 || (i2 = this.gmonthBase1) <= 0 || (i3 = this.gday) <= 0) {
            return null;
        }
        return cyclicalm(i4, i2, i3);
    }

    public int getDay() {
        return this.day;
    }

    public String getFestivals() {
        return CharSequenceUtil.join(",", LunarFestival.getFestivals(this.year, this.month, this.day));
    }

    public Calendar getGregorianCalendar() {
        Calendar calendar = CalendarUtil.calendar();
        calendar.set(this.gyear, getGregorianMonth(), this.gday, 0, 0, 0);
        return calendar;
    }

    public Date getGregorianDate() {
        return DateUtil.date(getGregorianCalendar());
    }

    public int getGregorianDay() {
        return this.gday;
    }

    public int getGregorianMonth() {
        return this.gmonthBase1 - 1;
    }

    public int getGregorianMonthBase1() {
        return this.gmonthBase1;
    }

    public int getGregorianYear() {
        return this.gyear;
    }

    public int getMonth() {
        return this.month;
    }

    public String getTerm() {
        return SolarTerms.getTerm(this.gyear, this.gmonthBase1, this.gday);
    }

    public boolean isLeapMonth() {
        return this.isLeapMonth;
    }

    public String toString() {
        return String.format("%s%s年 %s%s", getCyclical(), getChineseZodiac(), getChineseMonthName(), getChineseDay());
    }

    public String toStringNormal() {
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(this.year);
        objArr[1] = Integer.valueOf(isLeapMonth() ? this.month - 1 : this.month);
        objArr[2] = Integer.valueOf(this.day);
        return String.format("%04d-%02d-%02d", objArr);
    }

    public ChineseDate(LocalDate localDate) {
        boolean z2;
        int iMonthDays;
        int iYearDays;
        this.gyear = localDate.getYear();
        this.gmonthBase1 = localDate.getMonthValue();
        this.gday = localDate.getDayOfMonth();
        int epochDay = (int) (localDate.toEpochDay() - LunarInfo.BASE_DAY);
        int i2 = 1900;
        while (i2 <= LunarInfo.MAX_YEAR && epochDay >= (iYearDays = LunarInfo.yearDays(i2))) {
            epochDay -= iYearDays;
            i2++;
        }
        this.year = i2;
        int iLeapMonth = LunarInfo.leapMonth(i2);
        boolean z3 = false;
        boolean z4 = false;
        int i3 = 1;
        while (true) {
            if (i3 >= 13) {
                break;
            }
            if (iLeapMonth <= 0 || i3 != iLeapMonth + 1) {
                z2 = z4;
                iMonthDays = LunarInfo.monthDays(this.year, z4 ? i3 - 1 : i3);
            } else {
                iMonthDays = LunarInfo.leapDays(this.year);
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

    public String getChineseMonth(boolean z2) {
        return ChineseMonth.getChineseMonthName(isLeapMonth(), isLeapMonth() ? this.month - 1 : this.month, z2);
    }

    public ChineseDate(int i2, int i3, int i4) {
        this(i2, i3, i4, i3 == LunarInfo.leapMonth(i2));
    }

    public ChineseDate(int i2, int i3, int i4, boolean z2) {
        z2 = i3 != LunarInfo.leapMonth(i2) ? false : z2;
        this.day = i4;
        this.isLeapMonth = z2;
        this.month = z2 ? i3 + 1 : i3;
        this.year = i2;
        DateTime dateTimeLunar2solar = lunar2solar(i2, i3, i4, z2);
        if (dateTimeLunar2solar != null) {
            this.gday = dateTimeLunar2solar.dayOfMonth();
            this.gmonthBase1 = dateTimeLunar2solar.month() + 1;
            this.gyear = dateTimeLunar2solar.year();
        } else {
            this.gday = -1;
            this.gmonthBase1 = -1;
            this.gyear = -1;
        }
    }
}
