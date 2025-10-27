package cn.hutool.core.date;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import java.time.format.TextStyle;
import java.util.Locale;

/* loaded from: classes.dex */
public enum Month {
    JANUARY(0),
    FEBRUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11),
    UNDECIMBER(12);

    private final int value;
    private static final String[] ALIASES = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
    private static final Month[] ENUMS = values();

    /* renamed from: cn.hutool.core.date.Month$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$date$Month;

        static {
            int[] iArr = new int[Month.values().length];
            $SwitchMap$cn$hutool$core$date$Month = iArr;
            try {
                iArr[Month.FEBRUARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Month[Month.APRIL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Month[Month.JUNE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Month[Month.SEPTEMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$Month[Month.NOVEMBER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    Month(int i2) {
        this.value = i2;
    }

    public static Month of(int i2) {
        Month[] monthArr = ENUMS;
        if (i2 >= monthArr.length || i2 < 0) {
            return null;
        }
        return monthArr[i2];
    }

    public String getDisplayName(TextStyle textStyle) {
        return getDisplayName(textStyle, Locale.getDefault());
    }

    public int getLastDay(boolean z2) {
        int i2 = AnonymousClass1.$SwitchMap$cn$hutool$core$date$Month[ordinal()];
        return i2 != 1 ? (i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5) ? 30 : 31 : z2 ? 29 : 28;
    }

    public int getValue() {
        return this.value;
    }

    public int getValueBaseOne() throws Throwable {
        Assert.isFalse(this == UNDECIMBER, "Unsupported UNDECIMBER Field", new Object[0]);
        return getValue() + 1;
    }

    public java.time.Month toJdkMonth() {
        return java.time.Month.of(getValueBaseOne());
    }

    public static int getLastDay(int i2, boolean z2) throws IllegalArgumentException {
        Month monthOf = of(i2);
        Assert.notNull(monthOf, "Invalid Month base 0: " + i2, new Object[0]);
        return monthOf.getLastDay(z2);
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return toJdkMonth().getDisplayName(textStyle, locale);
    }

    public static Month of(String str) throws IllegalArgumentException {
        Assert.notBlank(str);
        Month monthOf = of(ArrayUtil.indexOfIgnoreCase(ALIASES, str));
        return monthOf == null ? valueOf(str.toUpperCase()) : monthOf;
    }

    public static Month of(java.time.Month month) {
        return of(month.ordinal());
    }
}
