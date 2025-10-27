package cn.hutool.core.date;

import cn.hutool.core.lang.Assert;
import java.util.Calendar;
import java.util.Date;

/* loaded from: classes.dex */
public class Zodiac {
    private static final int[] DAY_ARR = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIACS = {"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
    private static final String[] CHINESE_ZODIACS = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    public static String getChineseZodiac(Date date) {
        return getChineseZodiac(CalendarUtil.calendar(date));
    }

    public static String getZodiac(Date date) {
        return getZodiac(CalendarUtil.calendar(date));
    }

    public static String getChineseZodiac(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return getChineseZodiac(calendar.get(1));
    }

    public static String getZodiac(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return getZodiac(calendar.get(2), calendar.get(5));
    }

    public static String getChineseZodiac(int i2) {
        if (i2 < 1900) {
            return null;
        }
        String[] strArr = CHINESE_ZODIACS;
        return strArr[(i2 - 1900) % strArr.length];
    }

    public static String getZodiac(Month month, int i2) {
        return getZodiac(month.getValue(), i2);
    }

    public static String getZodiac(int i2, int i3) {
        Assert.checkBetween(i2, Month.JANUARY.getValue(), Month.DECEMBER.getValue(), "Unsupported month value, must be [0,12]", new Object[0]);
        return i3 < DAY_ARR[i2] ? ZODIACS[i2] : ZODIACS[i2 + 1];
    }
}
