package cn.hutool.core.date.chinese;

import java.time.LocalDate;

/* loaded from: classes.dex */
public class GanZhi {
    private static final String[] GAN = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    private static final String[] ZHI = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    public static String cyclicalm(int i2) {
        return GAN[i2 % 10] + ZHI[i2 % 12];
    }

    public static String getGanzhiOfDay(int i2, int i3, int i4) {
        return cyclicalm((int) (((LocalDate.of(i2, i3, i4).toEpochDay() - 1) - LunarInfo.BASE_DAY) + 41));
    }

    public static String getGanzhiOfMonth(int i2, int i3, int i4) {
        int term = SolarTerms.getTerm(i2, (i3 * 2) - 1);
        int i5 = ((i2 - 1900) * 12) + i3 + 11;
        if (i4 >= term) {
            i5++;
        }
        return cyclicalm(i5);
    }

    public static String getGanzhiOfYear(int i2) {
        return cyclicalm((i2 - 1900) + 36);
    }
}
