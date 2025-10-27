package kotlin.time;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.ComparableTimeMark;
import kotlin.time.TimeSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.3")
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH$R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lkotlin/time/AbstractDoubleTimeSource;", "Lkotlin/time/TimeSource$WithComparableMarks;", "unit", "Lkotlin/time/DurationUnit;", "(Lkotlin/time/DurationUnit;)V", "getUnit", "()Lkotlin/time/DurationUnit;", "markNow", "Lkotlin/time/ComparableTimeMark;", "read", "", "DoubleTimeMark", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
@Deprecated(message = "Using AbstractDoubleTimeSource is no longer recommended, use AbstractLongTimeSource instead.")
@ExperimentalTime
/* loaded from: classes8.dex */
public abstract class AbstractDoubleTimeSource implements TimeSource.WithComparableMarks {

    @NotNull
    private final DurationUnit unit;

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0015\u0010\n\u001a\u00020\u0007H\u0016ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0001H\u0096\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0007H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0016\u0010\u0006\u001a\u00020\u0007X\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u001c"}, d2 = {"Lkotlin/time/AbstractDoubleTimeSource$DoubleTimeMark;", "Lkotlin/time/ComparableTimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractDoubleTimeSource;", "offset", "Lkotlin/time/Duration;", "(DLkotlin/time/AbstractDoubleTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "elapsedNow", "elapsedNow-UwyO8pc", "()J", "equals", "", "other", "", "hashCode", "", "minus", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class DoubleTimeMark implements ComparableTimeMark {
        private final long offset;
        private final double startedAt;

        @NotNull
        private final AbstractDoubleTimeSource timeSource;

        private DoubleTimeMark(double d3, AbstractDoubleTimeSource timeSource, long j2) {
            Intrinsics.checkNotNullParameter(timeSource, "timeSource");
            this.startedAt = d3;
            this.timeSource = timeSource;
            this.offset = j2;
        }

        public /* synthetic */ DoubleTimeMark(double d3, AbstractDoubleTimeSource abstractDoubleTimeSource, long j2, DefaultConstructorMarker defaultConstructorMarker) {
            this(d3, abstractDoubleTimeSource, j2);
        }

        @Override // kotlin.time.TimeMark
        /* renamed from: elapsedNow-UwyO8pc, reason: not valid java name */
        public long mo2125elapsedNowUwyO8pc() {
            return Duration.m2171minusLRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
        }

        @Override // kotlin.time.ComparableTimeMark
        public boolean equals(@Nullable Object other) {
            return (other instanceof DoubleTimeMark) && Intrinsics.areEqual(this.timeSource, ((DoubleTimeMark) other).timeSource) && Duration.m2141equalsimpl0(mo2127minusUwyO8pc((ComparableTimeMark) other), Duration.INSTANCE.m2238getZEROUwyO8pc());
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
            return Duration.m2164hashCodeimpl(Duration.m2172plusLRDsOJo(DurationKt.toDuration(this.startedAt, this.timeSource.getUnit()), this.offset));
        }

        @Override // kotlin.time.TimeMark
        @NotNull
        /* renamed from: minus-LRDsOJo, reason: not valid java name */
        public ComparableTimeMark mo2126minusLRDsOJo(long j2) {
            return ComparableTimeMark.DefaultImpls.m2131minusLRDsOJo(this, j2);
        }

        @Override // kotlin.time.ComparableTimeMark
        /* renamed from: minus-UwyO8pc, reason: not valid java name */
        public long mo2127minusUwyO8pc(@NotNull ComparableTimeMark other) {
            Intrinsics.checkNotNullParameter(other, "other");
            if (other instanceof DoubleTimeMark) {
                DoubleTimeMark doubleTimeMark = (DoubleTimeMark) other;
                if (Intrinsics.areEqual(this.timeSource, doubleTimeMark.timeSource)) {
                    if (Duration.m2141equalsimpl0(this.offset, doubleTimeMark.offset) && Duration.m2168isInfiniteimpl(this.offset)) {
                        return Duration.INSTANCE.m2238getZEROUwyO8pc();
                    }
                    long jM2171minusLRDsOJo = Duration.m2171minusLRDsOJo(this.offset, doubleTimeMark.offset);
                    long duration = DurationKt.toDuration(this.startedAt - doubleTimeMark.startedAt, this.timeSource.getUnit());
                    return Duration.m2141equalsimpl0(duration, Duration.m2188unaryMinusUwyO8pc(jM2171minusLRDsOJo)) ? Duration.INSTANCE.m2238getZEROUwyO8pc() : Duration.m2172plusLRDsOJo(duration, jM2171minusLRDsOJo);
                }
            }
            throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + this + " and " + other);
        }

        @NotNull
        public String toString() {
            return "DoubleTimeMark(" + this.startedAt + DurationUnitKt__DurationUnitKt.shortName(this.timeSource.getUnit()) + " + " + ((Object) Duration.m2185toStringimpl(this.offset)) + ", " + this.timeSource + ')';
        }

        @Override // java.lang.Comparable
        public int compareTo(@NotNull ComparableTimeMark comparableTimeMark) {
            return ComparableTimeMark.DefaultImpls.compareTo(this, comparableTimeMark);
        }

        @Override // kotlin.time.TimeMark
        @NotNull
        /* renamed from: plus-LRDsOJo, reason: not valid java name */
        public ComparableTimeMark mo2128plusLRDsOJo(long duration) {
            return new DoubleTimeMark(this.startedAt, this.timeSource, Duration.m2172plusLRDsOJo(this.offset, duration), null);
        }
    }

    public AbstractDoubleTimeSource(@NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        this.unit = unit;
    }

    @NotNull
    public final DurationUnit getUnit() {
        return this.unit;
    }

    public abstract double read();

    @Override // kotlin.time.TimeSource
    @NotNull
    public ComparableTimeMark markNow() {
        return new DoubleTimeMark(read(), this, Duration.INSTANCE.m2238getZEROUwyO8pc(), null);
    }
}
