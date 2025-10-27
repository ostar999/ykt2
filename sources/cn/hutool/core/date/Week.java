package cn.hutool.core.date;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import java.time.DayOfWeek;

/* loaded from: classes.dex */
public enum Week {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);

    private static final String[] ALIASES = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
    private static final Week[] ENUMS = values();
    private final int value;

    /* renamed from: cn.hutool.core.date.Week$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$date$Week;

        static {
            int[] iArr = new int[Week.values().length];
            $SwitchMap$cn$hutool$core$date$Week = iArr;
            try {
                iArr[Week.SUNDAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Week[Week.MONDAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Week[Week.TUESDAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Week[Week.WEDNESDAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Week[Week.THURSDAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Week[Week.FRIDAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Week[Week.SATURDAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    Week(int i2) {
        this.value = i2;
    }

    public static Week of(int i2) {
        Week[] weekArr = ENUMS;
        if (i2 > weekArr.length || i2 < 1) {
            return null;
        }
        return weekArr[i2 - 1];
    }

    public int getIso8601Value() {
        int value = getValue() - 1;
        if (value == 0) {
            return 7;
        }
        return value;
    }

    public int getValue() {
        return this.value;
    }

    public String toChinese() {
        return toChinese("星期");
    }

    public DayOfWeek toJdkDayOfWeek() {
        return DayOfWeek.of(getIso8601Value());
    }

    public String toChinese(String str) {
        switch (AnonymousClass1.$SwitchMap$cn$hutool$core$date$Week[ordinal()]) {
            case 1:
                return str + "日";
            case 2:
                return str + "一";
            case 3:
                return str + "二";
            case 4:
                return str + "三";
            case 5:
                return str + "四";
            case 6:
                return str + "五";
            case 7:
                return str + "六";
            default:
                return null;
        }
    }

    public static Week of(String str) throws IllegalArgumentException {
        Assert.notBlank(str);
        Week weekOf = of(ArrayUtil.indexOfIgnoreCase(ALIASES, str) + 1);
        return weekOf == null ? valueOf(str.toUpperCase()) : weekOf;
    }

    public static Week of(DayOfWeek dayOfWeek) throws IllegalArgumentException {
        Assert.notNull(dayOfWeek);
        int value = dayOfWeek.getValue() + 1;
        return of(8 != value ? value : 1);
    }
}
