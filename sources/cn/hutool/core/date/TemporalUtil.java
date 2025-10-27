package cn.hutool.core.date;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class TemporalUtil {

    /* renamed from: cn.hutool.core.date.TemporalUtil$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoUnit;
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit;

        static {
            int[] iArr = new int[ChronoUnit.values().length];
            $SwitchMap$java$time$temporal$ChronoUnit = iArr;
            try {
                iArr[ChronoUnit.NANOS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MICROS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MILLIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoUnit[ChronoUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[TimeUnit.values().length];
            $SwitchMap$java$util$concurrent$TimeUnit = iArr2;
            try {
                iArr2[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    public static Duration between(Temporal temporal, Temporal temporal2) {
        return Duration.between(temporal, temporal2);
    }

    public static <T extends Temporal> T offset(T t2, long j2, TemporalUnit temporalUnit) {
        if (t2 == null) {
            return null;
        }
        return (T) t2.plus(j2, temporalUnit);
    }

    public static ChronoUnit toChronoUnit(TimeUnit timeUnit) throws IllegalArgumentException {
        if (timeUnit == null) {
            return null;
        }
        switch (AnonymousClass1.$SwitchMap$java$util$concurrent$TimeUnit[timeUnit.ordinal()]) {
            case 1:
                return ChronoUnit.NANOS;
            case 2:
                return ChronoUnit.MICROS;
            case 3:
                return ChronoUnit.MILLIS;
            case 4:
                return ChronoUnit.SECONDS;
            case 5:
                return ChronoUnit.MINUTES;
            case 6:
                return ChronoUnit.HOURS;
            case 7:
                return ChronoUnit.DAYS;
            default:
                throw new IllegalArgumentException("Unknown TimeUnit constant");
        }
    }

    public static TimeUnit toTimeUnit(ChronoUnit chronoUnit) throws IllegalArgumentException {
        if (chronoUnit == null) {
            return null;
        }
        switch (AnonymousClass1.$SwitchMap$java$time$temporal$ChronoUnit[chronoUnit.ordinal()]) {
            case 1:
                return TimeUnit.NANOSECONDS;
            case 2:
                return TimeUnit.MICROSECONDS;
            case 3:
                return TimeUnit.MILLISECONDS;
            case 4:
                return TimeUnit.SECONDS;
            case 5:
                return TimeUnit.MINUTES;
            case 6:
                return TimeUnit.HOURS;
            case 7:
                return TimeUnit.DAYS;
            default:
                throw new IllegalArgumentException("ChronoUnit cannot be converted to TimeUnit: " + chronoUnit);
        }
    }

    public static long between(Temporal temporal, Temporal temporal2, ChronoUnit chronoUnit) {
        return chronoUnit.between(temporal, temporal2);
    }

    public <T extends Temporal> T offset(T t2, DayOfWeek dayOfWeek, boolean z2) {
        return (T) t2.with(z2 ? TemporalAdjusters.previous(dayOfWeek) : TemporalAdjusters.next(dayOfWeek));
    }
}
