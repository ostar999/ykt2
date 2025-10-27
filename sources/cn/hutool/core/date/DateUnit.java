package cn.hutool.core.date;

import java.time.temporal.ChronoUnit;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'MINUTE' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes.dex */
public final class DateUnit {
    private static final /* synthetic */ DateUnit[] $VALUES;
    public static final DateUnit DAY;
    public static final DateUnit HOUR;
    public static final DateUnit MINUTE;
    public static final DateUnit MS;
    public static final DateUnit SECOND;
    public static final DateUnit WEEK;
    private final long millis;

    /* renamed from: cn.hutool.core.date.DateUnit$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$date$DateUnit;
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoUnit;

        static {
            int[] iArr = new int[DateUnit.values().length];
            $SwitchMap$cn$hutool$core$date$DateUnit = iArr;
            try {
                iArr[DateUnit.MS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateUnit[DateUnit.SECOND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateUnit[DateUnit.MINUTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateUnit[DateUnit.HOUR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateUnit[DateUnit.DAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$cn$hutool$core$date$DateUnit[DateUnit.WEEK.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[ChronoUnit.values().length];
            $SwitchMap$java$time$temporal$ChronoUnit = iArr2;
            try {
                iArr2[ChronoUnit.MICROS.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.SECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MINUTES.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HOURS.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.DAYS.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.WEEKS.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    static {
        DateUnit dateUnit = new DateUnit("MS", 0, 1L);
        MS = dateUnit;
        DateUnit dateUnit2 = new DateUnit("SECOND", 1, 1000L);
        SECOND = dateUnit2;
        DateUnit dateUnit3 = new DateUnit("MINUTE", 2, dateUnit2.getMillis() * 60);
        MINUTE = dateUnit3;
        DateUnit dateUnit4 = new DateUnit("HOUR", 3, dateUnit3.getMillis() * 60);
        HOUR = dateUnit4;
        DateUnit dateUnit5 = new DateUnit("DAY", 4, dateUnit4.getMillis() * 24);
        DAY = dateUnit5;
        DateUnit dateUnit6 = new DateUnit("WEEK", 5, dateUnit5.getMillis() * 7);
        WEEK = dateUnit6;
        $VALUES = new DateUnit[]{dateUnit, dateUnit2, dateUnit3, dateUnit4, dateUnit5, dateUnit6};
    }

    private DateUnit(String str, int i2, long j2) {
        this.millis = j2;
    }

    public static DateUnit of(ChronoUnit chronoUnit) {
        switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoUnit[chronoUnit.ordinal()]) {
            case 1:
                return MS;
            case 2:
                return SECOND;
            case 3:
                return MINUTE;
            case 4:
                return HOUR;
            case 5:
                return DAY;
            case 6:
                return WEEK;
            default:
                return null;
        }
    }

    public static DateUnit valueOf(String str) {
        return (DateUnit) Enum.valueOf(DateUnit.class, str);
    }

    public static DateUnit[] values() {
        return (DateUnit[]) $VALUES.clone();
    }

    public long getMillis() {
        return this.millis;
    }

    public ChronoUnit toChronoUnit() {
        return toChronoUnit(this);
    }

    public static ChronoUnit toChronoUnit(DateUnit dateUnit) {
        switch (AnonymousClass1.$SwitchMap$cn$hutool$core$date$DateUnit[dateUnit.ordinal()]) {
            case 1:
                return ChronoUnit.MICROS;
            case 2:
                return ChronoUnit.SECONDS;
            case 3:
                return ChronoUnit.MINUTES;
            case 4:
                return ChronoUnit.HOURS;
            case 5:
                return ChronoUnit.DAYS;
            case 6:
                return ChronoUnit.WEEKS;
            default:
                return null;
        }
    }
}
