package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.TimeMark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.8")
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bg\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\bH¦\u0002J\b\u0010\t\u001a\u00020\u0004H&J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0000H¦\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\f\u0010\rJ\u001b\u0010\n\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH\u0096\u0002ø\u0001\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH¦\u0002ø\u0001\u0001¢\u0006\u0004\b\u0012\u0010\u0010\u0082\u0002\b\n\u0002\b!\n\u0002\b\u0019¨\u0006\u0013"}, d2 = {"Lkotlin/time/ComparableTimeMark;", "Lkotlin/time/TimeMark;", "", "compareTo", "", "other", "equals", "", "", "hashCode", "minus", "Lkotlin/time/Duration;", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "duration", "minus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "plus", "plus-LRDsOJo", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
@ExperimentalTime
/* loaded from: classes8.dex */
public interface ComparableTimeMark extends TimeMark, Comparable<ComparableTimeMark> {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static int compareTo(@NotNull ComparableTimeMark comparableTimeMark, @NotNull ComparableTimeMark other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return Duration.m2135compareToLRDsOJo(comparableTimeMark.mo2127minusUwyO8pc(other), Duration.INSTANCE.m2238getZEROUwyO8pc());
        }

        public static boolean hasNotPassedNow(@NotNull ComparableTimeMark comparableTimeMark) {
            return TimeMark.DefaultImpls.hasNotPassedNow(comparableTimeMark);
        }

        public static boolean hasPassedNow(@NotNull ComparableTimeMark comparableTimeMark) {
            return TimeMark.DefaultImpls.hasPassedNow(comparableTimeMark);
        }

        @NotNull
        /* renamed from: minus-LRDsOJo, reason: not valid java name */
        public static ComparableTimeMark m2131minusLRDsOJo(@NotNull ComparableTimeMark comparableTimeMark, long j2) {
            return comparableTimeMark.mo2128plusLRDsOJo(Duration.m2188unaryMinusUwyO8pc(j2));
        }
    }

    int compareTo(@NotNull ComparableTimeMark other);

    boolean equals(@Nullable Object other);

    int hashCode();

    @Override // kotlin.time.TimeMark
    @NotNull
    /* renamed from: minus-LRDsOJo */
    ComparableTimeMark mo2126minusLRDsOJo(long duration);

    /* renamed from: minus-UwyO8pc */
    long mo2127minusUwyO8pc(@NotNull ComparableTimeMark other);

    @Override // kotlin.time.TimeMark
    @NotNull
    /* renamed from: plus-LRDsOJo */
    ComparableTimeMark mo2128plusLRDsOJo(long duration);
}
