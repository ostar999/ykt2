package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.ComparableTimeMark;
import kotlin.time.Duration;
import kotlin.time.TimeSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH$R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/TimeSource$WithComparableMarks;", "unit", "Lkotlin/time/DurationUnit;", "(Lkotlin/time/DurationUnit;)V", "getUnit", "()Lkotlin/time/DurationUnit;", "markNow", "Lkotlin/time/ComparableTimeMark;", "read", "", "LongTimeMark", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
@ExperimentalTime
/* loaded from: classes8.dex */
public abstract class AbstractLongTimeSource implements TimeSource.WithComparableMarks {

    @NotNull
    private final DurationUnit unit;

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0015\u0010\n\u001a\u00020\u0007H\u0000ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\r\u001a\u00020\u0007H\u0016ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\fJ\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0001H\u0096\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017J\u001b\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u0007H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0016\u0010\u0006\u001a\u00020\u0007X\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u001e"}, d2 = {"Lkotlin/time/AbstractLongTimeSource$LongTimeMark;", "Lkotlin/time/ComparableTimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractLongTimeSource;", "offset", "Lkotlin/time/Duration;", "(JLkotlin/time/AbstractLongTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "effectiveDuration", "effectiveDuration-UwyO8pc$kotlin_stdlib", "()J", "elapsedNow", "elapsedNow-UwyO8pc", "equals", "", "other", "", "hashCode", "", "minus", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nTimeSources.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeSources.kt\nkotlin/time/AbstractLongTimeSource$LongTimeMark\n+ 2 Duration.kt\nkotlin/time/Duration\n*L\n1#1,180:1\n720#2,2:181\n*S KotlinDebug\n*F\n+ 1 TimeSources.kt\nkotlin/time/AbstractLongTimeSource$LongTimeMark\n*L\n66#1:181,2\n*E\n"})
    public static final class LongTimeMark implements ComparableTimeMark {
        private final long offset;
        private final long startedAt;

        @NotNull
        private final AbstractLongTimeSource timeSource;

        private LongTimeMark(long j2, AbstractLongTimeSource timeSource, long j3) {
            Intrinsics.checkNotNullParameter(timeSource, "timeSource");
            this.startedAt = j2;
            this.timeSource = timeSource;
            this.offset = j3;
        }

        public /* synthetic */ LongTimeMark(long j2, AbstractLongTimeSource abstractLongTimeSource, long j3, DefaultConstructorMarker defaultConstructorMarker) {
            this(j2, abstractLongTimeSource, j3);
        }

        /* renamed from: effectiveDuration-UwyO8pc$kotlin_stdlib, reason: not valid java name */
        public final long m2129effectiveDurationUwyO8pc$kotlin_stdlib() {
            if (Duration.m2168isInfiniteimpl(this.offset)) {
                return this.offset;
            }
            DurationUnit unit = this.timeSource.getUnit();
            DurationUnit durationUnit = DurationUnit.MILLISECONDS;
            if (unit.compareTo(durationUnit) >= 0) {
                return Duration.m2172plusLRDsOJo(DurationKt.toDuration(this.startedAt, unit), this.offset);
            }
            long jConvertDurationUnit = DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(1L, durationUnit, unit);
            long j2 = this.startedAt;
            long j3 = j2 / jConvertDurationUnit;
            long j4 = j2 % jConvertDurationUnit;
            long j5 = this.offset;
            long jM2157getInWholeSecondsimpl = Duration.m2157getInWholeSecondsimpl(j5);
            int iM2159getNanosecondsComponentimpl = Duration.m2159getNanosecondsComponentimpl(j5);
            int i2 = iM2159getNanosecondsComponentimpl / 1000000;
            long duration = DurationKt.toDuration(j4, unit);
            Duration.Companion companion = Duration.INSTANCE;
            return Duration.m2172plusLRDsOJo(Duration.m2172plusLRDsOJo(Duration.m2172plusLRDsOJo(duration, DurationKt.toDuration(iM2159getNanosecondsComponentimpl % 1000000, DurationUnit.NANOSECONDS)), DurationKt.toDuration(j3 + i2, durationUnit)), DurationKt.toDuration(jM2157getInWholeSecondsimpl, DurationUnit.SECONDS));
        }

        @Override // kotlin.time.TimeMark
        /* renamed from: elapsedNow-UwyO8pc */
        public long mo2125elapsedNowUwyO8pc() {
            return Duration.m2168isInfiniteimpl(this.offset) ? Duration.m2188unaryMinusUwyO8pc(this.offset) : Duration.m2171minusLRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
        }

        @Override // kotlin.time.ComparableTimeMark
        public boolean equals(@Nullable Object other) {
            return (other instanceof LongTimeMark) && Intrinsics.areEqual(this.timeSource, ((LongTimeMark) other).timeSource) && Duration.m2141equalsimpl0(mo2127minusUwyO8pc((ComparableTimeMark) other), Duration.INSTANCE.m2238getZEROUwyO8pc());
        }

        @Override // kotlin.time.TimeMark
        public boolean hasNotPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasNotPassedNow(this);
        }

        @Override // kotlin.time.TimeMark
        public boolean hasPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasPassedNow(this);
        }

        @Override // kotlin.time.ComparableTimeMark
        public int hashCode() {
            return Duration.m2164hashCodeimpl(m2129effectiveDurationUwyO8pc$kotlin_stdlib());
        }

        @Override // kotlin.time.TimeMark
        @NotNull
        /* renamed from: minus-LRDsOJo */
        public ComparableTimeMark mo2126minusLRDsOJo(long j2) {
            return ComparableTimeMark.DefaultImpls.m2131minusLRDsOJo(this, j2);
        }

        @Override // kotlin.time.ComparableTimeMark
        /* renamed from: minus-UwyO8pc */
        public long mo2127minusUwyO8pc(@NotNull ComparableTimeMark other) {
            Intrinsics.checkNotNullParameter(other, "other");
            if (other instanceof LongTimeMark) {
                LongTimeMark longTimeMark = (LongTimeMark) other;
                if (Intrinsics.areEqual(this.timeSource, longTimeMark.timeSource)) {
                    if (Duration.m2141equalsimpl0(this.offset, longTimeMark.offset) && Duration.m2168isInfiniteimpl(this.offset)) {
                        return Duration.INSTANCE.m2238getZEROUwyO8pc();
                    }
                    long jM2171minusLRDsOJo = Duration.m2171minusLRDsOJo(this.offset, longTimeMark.offset);
                    long duration = DurationKt.toDuration(this.startedAt - longTimeMark.startedAt, this.timeSource.getUnit());
                    return Duration.m2141equalsimpl0(duration, Duration.m2188unaryMinusUwyO8pc(jM2171minusLRDsOJo)) ? Duration.INSTANCE.m2238getZEROUwyO8pc() : Duration.m2172plusLRDsOJo(duration, jM2171minusLRDsOJo);
                }
            }
            throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + this + " and " + other);
        }

        @NotNull
        public String toString() {
            return "LongTimeMark(" + this.startedAt + DurationUnitKt__DurationUnitKt.shortName(this.timeSource.getUnit()) + " + " + ((Object) Duration.m2185toStringimpl(this.offset)) + " (=" + ((Object) Duration.m2185toStringimpl(m2129effectiveDurationUwyO8pc$kotlin_stdlib())) + "), " + this.timeSource + ')';
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull ComparableTimeMark comparableTimeMark) {
            return ComparableTimeMark.DefaultImpls.compareTo(this, comparableTimeMark);
        }

        @Override // kotlin.time.TimeMark
        @NotNull
        /* renamed from: plus-LRDsOJo */
        public ComparableTimeMark mo2128plusLRDsOJo(long duration) {
            return new LongTimeMark(this.startedAt, this.timeSource, Duration.m2172plusLRDsOJo(this.offset, duration), null);
        }
    }

    public AbstractLongTimeSource(@NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        this.unit = unit;
    }

    @NotNull
    public final DurationUnit getUnit() {
        return this.unit;
    }

    public abstract long read();

    @Override // kotlin.time.TimeSource
    @NotNull
    public ComparableTimeMark markNow() {
        return new LongTimeMark(read(), this, Duration.INSTANCE.m2238getZEROUwyO8pc(), null);
    }
}
