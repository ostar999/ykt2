package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.Duration;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\"\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\"\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\n\u001a \u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a \u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a \u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a\r\u0010\u0016\u001a\u00020\u0017*\u00020\u0001H\u0082\b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, d2 = {"checkInfiniteSumDefined", "", "longNs", "duration", "Lkotlin/time/Duration;", "durationNs", "checkInfiniteSumDefined-PjuGub4", "(JJJ)J", "saturatingAdd", "saturatingAdd-pTJri5U", "(JJ)J", "saturatingAddInHalves", "saturatingAddInHalves-pTJri5U", "saturatingDiff", "valueNs", "originNs", "saturatingFiniteDiff", "value1Ns", "value2Ns", "saturatingOriginsDiff", "origin1Ns", "origin2Ns", "isSaturated", "", "kotlin-stdlib"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nlongSaturatedMath.kt\nKotlin\n*S Kotlin\n*F\n+ 1 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n1#1,75:1\n74#1:76\n74#1:77\n74#1:78\n74#1:79\n74#1:80\n74#1:81\n*S KotlinDebug\n*F\n+ 1 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n15#1:76\n18#1:77\n36#1:78\n45#1:79\n52#1:80\n56#1:81\n*E\n"})
/* loaded from: classes8.dex */
public final class LongSaturatedMathKt {
    /* renamed from: checkInfiniteSumDefined-PjuGub4, reason: not valid java name */
    private static final long m2263checkInfiniteSumDefinedPjuGub4(long j2, long j3, long j4) {
        if (!Duration.m2168isInfiniteimpl(j3) || (j2 ^ j4) >= 0) {
            return j2;
        }
        throw new IllegalArgumentException("Summing infinities of different signs");
    }

    private static final boolean isSaturated(long j2) {
        return ((j2 - 1) | 1) == Long.MAX_VALUE;
    }

    /* renamed from: saturatingAdd-pTJri5U, reason: not valid java name */
    public static final long m2264saturatingAddpTJri5U(long j2, long j3) {
        long jM2156getInWholeNanosecondsimpl = Duration.m2156getInWholeNanosecondsimpl(j3);
        if (((j2 - 1) | 1) == Long.MAX_VALUE) {
            return m2263checkInfiniteSumDefinedPjuGub4(j2, j3, jM2156getInWholeNanosecondsimpl);
        }
        if ((1 | (jM2156getInWholeNanosecondsimpl - 1)) == Long.MAX_VALUE) {
            return m2265saturatingAddInHalvespTJri5U(j2, j3);
        }
        long j4 = j2 + jM2156getInWholeNanosecondsimpl;
        return ((j2 ^ j4) & (jM2156getInWholeNanosecondsimpl ^ j4)) < 0 ? j2 < 0 ? Long.MIN_VALUE : Long.MAX_VALUE : j4;
    }

    /* renamed from: saturatingAddInHalves-pTJri5U, reason: not valid java name */
    private static final long m2265saturatingAddInHalvespTJri5U(long j2, long j3) {
        long jM2139divUwyO8pc = Duration.m2139divUwyO8pc(j3, 2);
        return (((Duration.m2156getInWholeNanosecondsimpl(jM2139divUwyO8pc) - 1) | 1) > Long.MAX_VALUE ? 1 : (((Duration.m2156getInWholeNanosecondsimpl(jM2139divUwyO8pc) - 1) | 1) == Long.MAX_VALUE ? 0 : -1)) == 0 ? (long) (j2 + Duration.m2179toDoubleimpl(j3, DurationUnit.NANOSECONDS)) : m2264saturatingAddpTJri5U(m2264saturatingAddpTJri5U(j2, jM2139divUwyO8pc), Duration.m2171minusLRDsOJo(j3, jM2139divUwyO8pc));
    }

    public static final long saturatingDiff(long j2, long j3) {
        return ((1 | (j3 - 1)) > Long.MAX_VALUE ? 1 : ((1 | (j3 - 1)) == Long.MAX_VALUE ? 0 : -1)) == 0 ? Duration.m2188unaryMinusUwyO8pc(DurationKt.toDuration(j3, DurationUnit.DAYS)) : saturatingFiniteDiff(j2, j3);
    }

    private static final long saturatingFiniteDiff(long j2, long j3) {
        long j4 = j2 - j3;
        if (((j4 ^ j2) & (~(j4 ^ j3))) >= 0) {
            Duration.Companion companion = Duration.INSTANCE;
            return DurationKt.toDuration(j4, DurationUnit.NANOSECONDS);
        }
        long j5 = 1000000;
        long j6 = (j2 / j5) - (j3 / j5);
        long j7 = (j2 % j5) - (j3 % j5);
        Duration.Companion companion2 = Duration.INSTANCE;
        return Duration.m2172plusLRDsOJo(DurationKt.toDuration(j6, DurationUnit.MILLISECONDS), DurationKt.toDuration(j7, DurationUnit.NANOSECONDS));
    }

    public static final long saturatingOriginsDiff(long j2, long j3) {
        if (((j3 - 1) | 1) == Long.MAX_VALUE) {
            return j2 == j3 ? Duration.INSTANCE.m2238getZEROUwyO8pc() : Duration.m2188unaryMinusUwyO8pc(DurationKt.toDuration(j3, DurationUnit.DAYS));
        }
        return (1 | (j2 - 1)) == Long.MAX_VALUE ? DurationKt.toDuration(j2, DurationUnit.DAYS) : saturatingFiniteDiff(j2, j3);
    }
}
