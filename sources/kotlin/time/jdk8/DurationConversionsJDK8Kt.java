package kotlin.time.jdk8;

import java.time.Duration;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlin.time.ExperimentalTime;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u0015\u0010\u0005\u001a\u00020\u0002*\u00020\u0001H\u0087\bø\u0001\u0001¢\u0006\u0002\u0010\u0006\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b\u0019¨\u0006\u0007"}, d2 = {"toJavaDuration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "toJavaDuration-LRDsOJo", "(J)Ljava/time/Duration;", "toKotlinDuration", "(Ljava/time/Duration;)J", "kotlin-stdlib-jdk8"}, k = 2, mv = {1, 8, 0}, pn = "kotlin.time", xi = 48)
@JvmName(name = "DurationConversionsJDK8Kt")
@SourceDebugExtension({"SMAP\nDurationConversions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DurationConversions.kt\nkotlin/time/jdk8/DurationConversionsJDK8Kt\n+ 2 Duration.kt\nkotlin/time/Duration\n*L\n1#1,33:1\n720#2,2:34\n*S KotlinDebug\n*F\n+ 1 DurationConversions.kt\nkotlin/time/jdk8/DurationConversionsJDK8Kt\n*L\n33#1:34,2\n*E\n"})
/* loaded from: classes8.dex */
public final class DurationConversionsJDK8Kt {
    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    @InlineOnly
    /* renamed from: toJavaDuration-LRDsOJo, reason: not valid java name */
    private static final Duration m2297toJavaDurationLRDsOJo(long j2) {
        Duration durationOfSeconds = Duration.ofSeconds(kotlin.time.Duration.m2157getInWholeSecondsimpl(j2), kotlin.time.Duration.m2159getNanosecondsComponentimpl(j2));
        Intrinsics.checkNotNullExpressionValue(durationOfSeconds, "toJavaDuration-LRDsOJo");
        return durationOfSeconds;
    }

    @SinceKotlin(version = "1.6")
    @WasExperimental(markerClass = {ExperimentalTime.class})
    @InlineOnly
    private static final long toKotlinDuration(Duration duration) {
        Intrinsics.checkNotNullParameter(duration, "<this>");
        return kotlin.time.Duration.m2172plusLRDsOJo(DurationKt.toDuration(duration.getSeconds(), DurationUnit.SECONDS), DurationKt.toDuration(duration.getNano(), DurationUnit.NANOSECONDS));
    }
}
