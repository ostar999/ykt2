package kotlin.time;

import androidx.exifinterface.media.ExifInterface;
import cn.hutool.core.text.CharPool;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmInline;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import z.a;

@SinceKotlin(version = "1.6")
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0087@\u0018\u0000 ¤\u00012\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002¤\u0001B\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J%\u0010D\u001a\u00020\u00002\u0006\u0010E\u001a\u00020\u00032\u0006\u0010F\u001a\u00020\u0003H\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bG\u0010HJ\u001b\u0010I\u001a\u00020\t2\u0006\u0010J\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\bK\u0010LJ\u001e\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u000fH\u0086\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bO\u0010PJ\u001e\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\tH\u0086\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bO\u0010QJ\u001b\u0010M\u001a\u00020\u000f2\u0006\u0010J\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bR\u0010SJ\u001a\u0010T\u001a\u00020U2\b\u0010J\u001a\u0004\u0018\u00010VHÖ\u0003¢\u0006\u0004\bW\u0010XJ\u0010\u0010Y\u001a\u00020\tHÖ\u0001¢\u0006\u0004\bZ\u0010\rJ\r\u0010[\u001a\u00020U¢\u0006\u0004\b\\\u0010]J\u000f\u0010^\u001a\u00020UH\u0002¢\u0006\u0004\b_\u0010]J\u000f\u0010`\u001a\u00020UH\u0002¢\u0006\u0004\ba\u0010]J\r\u0010b\u001a\u00020U¢\u0006\u0004\bc\u0010]J\r\u0010d\u001a\u00020U¢\u0006\u0004\be\u0010]J\r\u0010f\u001a\u00020U¢\u0006\u0004\bg\u0010]J\u001b\u0010h\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bi\u0010jJ\u001b\u0010k\u001a\u00020\u00002\u0006\u0010J\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bl\u0010jJ\u001e\u0010m\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u000fH\u0086\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bn\u0010PJ\u001e\u0010m\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\tH\u0086\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bn\u0010QJ\u009d\u0001\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p2u\u0010q\u001aq\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(u\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(v\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0rH\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bz\u0010{J\u0088\u0001\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p2`\u0010q\u001a\\\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(v\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0|H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bz\u0010}Js\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p2K\u0010q\u001aG\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(w\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0~H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0004\bz\u0010\u007fJ`\u0010o\u001a\u0002Hp\"\u0004\b\u0000\u0010p27\u0010q\u001a3\u0012\u0013\u0012\u00110\u0003¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(x\u0012\u0013\u0012\u00110\t¢\u0006\f\bs\u0012\b\bt\u0012\u0004\b\b(y\u0012\u0004\u0012\u0002Hp0\u0080\u0001H\u0086\bø\u0001\u0002\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0005\bz\u0010\u0081\u0001J\u0019\u0010\u0082\u0001\u001a\u00020\u000f2\u0007\u0010\u0083\u0001\u001a\u00020=¢\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J\u0019\u0010\u0086\u0001\u001a\u00020\t2\u0007\u0010\u0083\u0001\u001a\u00020=¢\u0006\u0006\b\u0087\u0001\u0010\u0088\u0001J\u0011\u0010\u0089\u0001\u001a\u00030\u008a\u0001¢\u0006\u0006\b\u008b\u0001\u0010\u008c\u0001J\u0019\u0010\u008d\u0001\u001a\u00020\u00032\u0007\u0010\u0083\u0001\u001a\u00020=¢\u0006\u0006\b\u008e\u0001\u0010\u008f\u0001J\u0011\u0010\u0090\u0001\u001a\u00020\u0003H\u0007¢\u0006\u0005\b\u0091\u0001\u0010\u0005J\u0011\u0010\u0092\u0001\u001a\u00020\u0003H\u0007¢\u0006\u0005\b\u0093\u0001\u0010\u0005J\u0013\u0010\u0094\u0001\u001a\u00030\u008a\u0001H\u0016¢\u0006\u0006\b\u0095\u0001\u0010\u008c\u0001J%\u0010\u0094\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u0083\u0001\u001a\u00020=2\t\b\u0002\u0010\u0096\u0001\u001a\u00020\t¢\u0006\u0006\b\u0095\u0001\u0010\u0097\u0001J\u0018\u0010\u0098\u0001\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0001ø\u0001\u0000¢\u0006\u0005\b\u0099\u0001\u0010\u0005JK\u0010\u009a\u0001\u001a\u00030\u009b\u0001*\b0\u009c\u0001j\u0003`\u009d\u00012\u0007\u0010\u009e\u0001\u001a\u00020\t2\u0007\u0010\u009f\u0001\u001a\u00020\t2\u0007\u0010 \u0001\u001a\u00020\t2\b\u0010\u0083\u0001\u001a\u00030\u008a\u00012\u0007\u0010¡\u0001\u001a\u00020UH\u0002¢\u0006\u0006\b¢\u0001\u0010£\u0001R\u0017\u0010\u0006\u001a\u00020\u00008Fø\u0001\u0000ø\u0001\u0001¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0015\u0010\u0012R\u001a\u0010\u0016\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0017\u0010\u000b\u001a\u0004\b\u0018\u0010\u0012R\u001a\u0010\u0019\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001a\u0010\u000b\u001a\u0004\b\u001b\u0010\u0012R\u001a\u0010\u001c\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010\u001f\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\u0012R\u001a\u0010\"\u001a\u00020\u000f8FX\u0087\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\u0012R\u0011\u0010%\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b&\u0010\u0005R\u0011\u0010'\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b(\u0010\u0005R\u0011\u0010)\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b*\u0010\u0005R\u0011\u0010+\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b,\u0010\u0005R\u0011\u0010-\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b.\u0010\u0005R\u0011\u0010/\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b0\u0010\u0005R\u0011\u00101\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b2\u0010\u0005R\u001a\u00103\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b4\u0010\u000b\u001a\u0004\b5\u0010\rR\u001a\u00106\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b7\u0010\u000b\u001a\u0004\b8\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00109\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b:\u0010\u000b\u001a\u0004\b;\u0010\rR\u0014\u0010<\u001a\u00020=8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0015\u0010@\u001a\u00020\t8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\bA\u0010\rR\u0014\u0010B\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bC\u0010\u0005\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b\u009920\u0001¨\u0006¥\u0001"}, d2 = {"Lkotlin/time/Duration;", "", "rawValue", "", "constructor-impl", "(J)J", "absoluteValue", "getAbsoluteValue-UwyO8pc", "hoursComponent", "", "getHoursComponent$annotations", "()V", "getHoursComponent-impl", "(J)I", "inDays", "", "getInDays$annotations", "getInDays-impl", "(J)D", "inHours", "getInHours$annotations", "getInHours-impl", "inMicroseconds", "getInMicroseconds$annotations", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds$annotations", "getInMilliseconds-impl", "inMinutes", "getInMinutes$annotations", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds$annotations", "getInNanoseconds-impl", "inSeconds", "getInSeconds$annotations", "getInSeconds-impl", "inWholeDays", "getInWholeDays-impl", "inWholeHours", "getInWholeHours-impl", "inWholeMicroseconds", "getInWholeMicroseconds-impl", "inWholeMilliseconds", "getInWholeMilliseconds-impl", "inWholeMinutes", "getInWholeMinutes-impl", "inWholeNanoseconds", "getInWholeNanoseconds-impl", "inWholeSeconds", "getInWholeSeconds-impl", "minutesComponent", "getMinutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "getNanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "getSecondsComponent$annotations", "getSecondsComponent-impl", "storageUnit", "Lkotlin/time/DurationUnit;", "getStorageUnit-impl", "(J)Lkotlin/time/DurationUnit;", "unitDiscriminator", "getUnitDiscriminator-impl", "value", "getValue-impl", "addValuesMixedRanges", "thisMillis", "otherNanos", "addValuesMixedRanges-UwyO8pc", "(JJJ)J", "compareTo", "other", "compareTo-LRDsOJo", "(JJ)I", TtmlNode.TAG_DIV, "scale", "div-UwyO8pc", "(JD)J", "(JI)J", "div-LRDsOJo", "(JJ)D", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "isFinite", "isFinite-impl", "(J)Z", "isInMillis", "isInMillis-impl", "isInNanos", "isInNanos-impl", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "(JJ)J", "plus", "plus-LRDsOJo", "times", "times-UwyO8pc", "toComponents", ExifInterface.GPS_DIRECTION_TRUE, "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(JLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(JLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(JLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "toDouble-impl", "(JLkotlin/time/DurationUnit;)D", "toInt", "toInt-impl", "(JLkotlin/time/DurationUnit;)I", "toIsoString", "", "toIsoString-impl", "(J)Ljava/lang/String;", "toLong", "toLong-impl", "(JLkotlin/time/DurationUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(JLkotlin/time/DurationUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-UwyO8pc", "appendFractional", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "whole", "fractional", "fractionalSize", "isoZeroes", "appendFractional-impl", "(JLjava/lang/StringBuilder;IIILjava/lang/String;Z)V", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalTime.class})
@SourceDebugExtension({"SMAP\nDuration.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Duration.kt\nkotlin/time/Duration\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,1484:1\n38#1:1485\n38#1:1486\n38#1:1487\n38#1:1488\n38#1:1489\n672#1,2:1490\n689#1,2:1499\n163#2,6:1492\n1#3:1498\n*S KotlinDebug\n*F\n+ 1 Duration.kt\nkotlin/time/Duration\n*L\n39#1:1485\n40#1:1486\n458#1:1487\n478#1:1488\n651#1:1489\n968#1:1490,2\n1059#1:1499,2\n1010#1:1492,6\n*E\n"})
/* loaded from: classes8.dex */
public final class Duration implements Comparable<Duration> {
    private final long rawValue;

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private static final long ZERO = m2136constructorimpl(0);
    private static final long INFINITE = DurationKt.durationOfMillis(DurationKt.MAX_MILLIS);
    private static final long NEG_INFINITE = DurationKt.durationOfMillis(-4611686018427387903L);

    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020\r2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-H\u0007J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b/\u0010\u0011J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b/\u0010\u0014J\u001d\u0010\f\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b/\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b0\u0010\u0011J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b0\u0010\u0014J\u001d\u0010\u0018\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b0\u0010\u0017J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0011J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0014J\u001d\u0010\u001b\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0017J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b2\u0010\u0011J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b2\u0010\u0014J\u001d\u0010\u001e\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b2\u0010\u0017J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b3\u0010\u0011J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b3\u0010\u0014J\u001d\u0010!\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b3\u0010\u0017J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b4\u0010\u0011J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b4\u0010\u0014J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b4\u0010\u0017J\u001b\u00105\u001a\u00020\u00042\u0006\u0010+\u001a\u000206ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b7\u00108J\u001b\u00109\u001a\u00020\u00042\u0006\u0010+\u001a\u000206ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b:\u00108J\u001b\u0010;\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u000206ø\u0001\u0001ø\u0001\u0000¢\u0006\u0002\b<J\u001b\u0010=\u001a\u0004\u0018\u00010\u00042\u0006\u0010+\u001a\u000206ø\u0001\u0001ø\u0001\u0000¢\u0006\u0002\b>J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\rH\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b?\u0010\u0011J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0012H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b?\u0010\u0014J\u001d\u0010'\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\u0015H\u0007ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b?\u0010\u0017R\u0019\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\b\u001a\u00020\u0004X\u0080\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006R\u0019\u0010\n\u001a\u00020\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u000b\u0010\u0006R%\u0010\f\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R%\u0010\f\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u000e\u0010\u0013\u001a\u0004\b\u0010\u0010\u0014R%\u0010\f\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u000e\u0010\u0016\u001a\u0004\b\u0010\u0010\u0017R%\u0010\u0018\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u0019\u0010\u000f\u001a\u0004\b\u001a\u0010\u0011R%\u0010\u0018\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u0019\u0010\u0013\u001a\u0004\b\u001a\u0010\u0014R%\u0010\u0018\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u0019\u0010\u0016\u001a\u0004\b\u001a\u0010\u0017R%\u0010\u001b\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001c\u0010\u000f\u001a\u0004\b\u001d\u0010\u0011R%\u0010\u001b\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001c\u0010\u0013\u001a\u0004\b\u001d\u0010\u0014R%\u0010\u001b\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001c\u0010\u0016\u001a\u0004\b\u001d\u0010\u0017R%\u0010\u001e\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001f\u0010\u000f\u001a\u0004\b \u0010\u0011R%\u0010\u001e\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001f\u0010\u0013\u001a\u0004\b \u0010\u0014R%\u0010\u001e\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\u001f\u0010\u0016\u001a\u0004\b \u0010\u0017R%\u0010!\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\"\u0010\u000f\u001a\u0004\b#\u0010\u0011R%\u0010!\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\"\u0010\u0013\u001a\u0004\b#\u0010\u0014R%\u0010!\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b\"\u0010\u0016\u001a\u0004\b#\u0010\u0017R%\u0010$\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b%\u0010\u000f\u001a\u0004\b&\u0010\u0011R%\u0010$\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b%\u0010\u0013\u001a\u0004\b&\u0010\u0014R%\u0010$\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b%\u0010\u0016\u001a\u0004\b&\u0010\u0017R%\u0010'\u001a\u00020\u0004*\u00020\r8Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b(\u0010\u000f\u001a\u0004\b)\u0010\u0011R%\u0010'\u001a\u00020\u0004*\u00020\u00128Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b(\u0010\u0013\u001a\u0004\b)\u0010\u0014R%\u0010'\u001a\u00020\u0004*\u00020\u00158Æ\u0002X\u0087\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\f\u0012\u0004\b(\u0010\u0016\u001a\u0004\b)\u0010\u0017\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006@"}, d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE-UwyO8pc", "()J", "J", "NEG_INFINITE", "getNEG_INFINITE-UwyO8pc$kotlin_stdlib", "ZERO", "getZERO-UwyO8pc", "days", "", "getDays-UwyO8pc$annotations", "(D)V", "getDays-UwyO8pc", "(D)J", "", "(I)V", "(I)J", "", "(J)V", "(J)J", "hours", "getHours-UwyO8pc$annotations", "getHours-UwyO8pc", "microseconds", "getMicroseconds-UwyO8pc$annotations", "getMicroseconds-UwyO8pc", "milliseconds", "getMilliseconds-UwyO8pc$annotations", "getMilliseconds-UwyO8pc", "minutes", "getMinutes-UwyO8pc$annotations", "getMinutes-UwyO8pc", "nanoseconds", "getNanoseconds-UwyO8pc$annotations", "getNanoseconds-UwyO8pc", "seconds", "getSeconds-UwyO8pc$annotations", "getSeconds-UwyO8pc", "convert", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "days-UwyO8pc", "hours-UwyO8pc", "microseconds-UwyO8pc", "milliseconds-UwyO8pc", "minutes-UwyO8pc", "nanoseconds-UwyO8pc", "parse", "", "parse-UwyO8pc", "(Ljava/lang/String;)J", "parseIsoString", "parseIsoString-UwyO8pc", "parseIsoStringOrNull", "parseIsoStringOrNull-FghU774", "parseOrNull", "parseOrNull-FghU774", "seconds-UwyO8pc", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: getDays-UwyO8pc, reason: not valid java name */
        private final long m2192getDaysUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.DAYS);
        }

        @InlineOnly
        /* renamed from: getDays-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2194getDaysUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getDays-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2195getDaysUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getDays-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2196getDaysUwyO8pc$annotations(long j2) {
        }

        /* renamed from: getHours-UwyO8pc, reason: not valid java name */
        private final long m2198getHoursUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.HOURS);
        }

        @InlineOnly
        /* renamed from: getHours-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2200getHoursUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getHours-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2201getHoursUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getHours-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2202getHoursUwyO8pc$annotations(long j2) {
        }

        /* renamed from: getMicroseconds-UwyO8pc, reason: not valid java name */
        private final long m2204getMicrosecondsUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.MICROSECONDS);
        }

        @InlineOnly
        /* renamed from: getMicroseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2206getMicrosecondsUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getMicroseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2207getMicrosecondsUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getMicroseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2208getMicrosecondsUwyO8pc$annotations(long j2) {
        }

        /* renamed from: getMilliseconds-UwyO8pc, reason: not valid java name */
        private final long m2210getMillisecondsUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.MILLISECONDS);
        }

        @InlineOnly
        /* renamed from: getMilliseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2212getMillisecondsUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getMilliseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2213getMillisecondsUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getMilliseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2214getMillisecondsUwyO8pc$annotations(long j2) {
        }

        /* renamed from: getMinutes-UwyO8pc, reason: not valid java name */
        private final long m2216getMinutesUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.MINUTES);
        }

        @InlineOnly
        /* renamed from: getMinutes-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2218getMinutesUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getMinutes-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2219getMinutesUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getMinutes-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2220getMinutesUwyO8pc$annotations(long j2) {
        }

        /* renamed from: getNanoseconds-UwyO8pc, reason: not valid java name */
        private final long m2222getNanosecondsUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.NANOSECONDS);
        }

        @InlineOnly
        /* renamed from: getNanoseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2224getNanosecondsUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getNanoseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2225getNanosecondsUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getNanoseconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2226getNanosecondsUwyO8pc$annotations(long j2) {
        }

        /* renamed from: getSeconds-UwyO8pc, reason: not valid java name */
        private final long m2228getSecondsUwyO8pc(int i2) {
            return DurationKt.toDuration(i2, DurationUnit.SECONDS);
        }

        @InlineOnly
        /* renamed from: getSeconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2230getSecondsUwyO8pc$annotations(double d3) {
        }

        @InlineOnly
        /* renamed from: getSeconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2231getSecondsUwyO8pc$annotations(int i2) {
        }

        @InlineOnly
        /* renamed from: getSeconds-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m2232getSecondsUwyO8pc$annotations(long j2) {
        }

        @ExperimentalTime
        public final double convert(double value, @NotNull DurationUnit sourceUnit, @NotNull DurationUnit targetUnit) {
            Intrinsics.checkNotNullParameter(sourceUnit, "sourceUnit");
            Intrinsics.checkNotNullParameter(targetUnit, "targetUnit");
            return DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(value, sourceUnit, targetUnit);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.days", imports = {"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: days-UwyO8pc, reason: not valid java name */
        public final long m2234daysUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.DAYS);
        }

        /* renamed from: getINFINITE-UwyO8pc, reason: not valid java name */
        public final long m2236getINFINITEUwyO8pc() {
            return Duration.INFINITE;
        }

        /* renamed from: getNEG_INFINITE-UwyO8pc$kotlin_stdlib, reason: not valid java name */
        public final long m2237getNEG_INFINITEUwyO8pc$kotlin_stdlib() {
            return Duration.NEG_INFINITE;
        }

        /* renamed from: getZERO-UwyO8pc, reason: not valid java name */
        public final long m2238getZEROUwyO8pc() {
            return Duration.ZERO;
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: hours-UwyO8pc, reason: not valid java name */
        public final long m2240hoursUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.HOURS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: microseconds-UwyO8pc, reason: not valid java name */
        public final long m2243microsecondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: milliseconds-UwyO8pc, reason: not valid java name */
        public final long m2246millisecondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: minutes-UwyO8pc, reason: not valid java name */
        public final long m2249minutesUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: nanoseconds-UwyO8pc, reason: not valid java name */
        public final long m2252nanosecondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.NANOSECONDS);
        }

        /* renamed from: parse-UwyO8pc, reason: not valid java name */
        public final long m2254parseUwyO8pc(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return DurationKt.parseDuration(value, false);
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Invalid duration string format: '" + value + "'.", e2);
            }
        }

        /* renamed from: parseIsoString-UwyO8pc, reason: not valid java name */
        public final long m2255parseIsoStringUwyO8pc(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return DurationKt.parseDuration(value, true);
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Invalid ISO duration string format: '" + value + "'.", e2);
            }
        }

        @Nullable
        /* renamed from: parseIsoStringOrNull-FghU774, reason: not valid java name */
        public final Duration m2256parseIsoStringOrNullFghU774(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return Duration.m2134boximpl(DurationKt.parseDuration(value, true));
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        @Nullable
        /* renamed from: parseOrNull-FghU774, reason: not valid java name */
        public final Duration m2257parseOrNullFghU774(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            try {
                return Duration.m2134boximpl(DurationKt.parseDuration(value, false));
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Int.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: seconds-UwyO8pc, reason: not valid java name */
        public final long m2259secondsUwyO8pc(int value) {
            return DurationKt.toDuration(value, DurationUnit.SECONDS);
        }

        /* renamed from: getDays-UwyO8pc, reason: not valid java name */
        private final long m2193getDaysUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.DAYS);
        }

        /* renamed from: getHours-UwyO8pc, reason: not valid java name */
        private final long m2199getHoursUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.HOURS);
        }

        /* renamed from: getMicroseconds-UwyO8pc, reason: not valid java name */
        private final long m2205getMicrosecondsUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.MICROSECONDS);
        }

        /* renamed from: getMilliseconds-UwyO8pc, reason: not valid java name */
        private final long m2211getMillisecondsUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.MILLISECONDS);
        }

        /* renamed from: getMinutes-UwyO8pc, reason: not valid java name */
        private final long m2217getMinutesUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.MINUTES);
        }

        /* renamed from: getNanoseconds-UwyO8pc, reason: not valid java name */
        private final long m2223getNanosecondsUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.NANOSECONDS);
        }

        /* renamed from: getSeconds-UwyO8pc, reason: not valid java name */
        private final long m2229getSecondsUwyO8pc(long j2) {
            return DurationKt.toDuration(j2, DurationUnit.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.days", imports = {"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: days-UwyO8pc, reason: not valid java name */
        public final long m2235daysUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.DAYS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: hours-UwyO8pc, reason: not valid java name */
        public final long m2241hoursUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.HOURS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: microseconds-UwyO8pc, reason: not valid java name */
        public final long m2244microsecondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: milliseconds-UwyO8pc, reason: not valid java name */
        public final long m2247millisecondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: minutes-UwyO8pc, reason: not valid java name */
        public final long m2250minutesUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: nanoseconds-UwyO8pc, reason: not valid java name */
        public final long m2253nanosecondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Long.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: seconds-UwyO8pc, reason: not valid java name */
        public final long m2260secondsUwyO8pc(long value) {
            return DurationKt.toDuration(value, DurationUnit.SECONDS);
        }

        /* renamed from: getDays-UwyO8pc, reason: not valid java name */
        private final long m2191getDaysUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.DAYS);
        }

        /* renamed from: getHours-UwyO8pc, reason: not valid java name */
        private final long m2197getHoursUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.HOURS);
        }

        /* renamed from: getMicroseconds-UwyO8pc, reason: not valid java name */
        private final long m2203getMicrosecondsUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.MICROSECONDS);
        }

        /* renamed from: getMilliseconds-UwyO8pc, reason: not valid java name */
        private final long m2209getMillisecondsUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.MILLISECONDS);
        }

        /* renamed from: getMinutes-UwyO8pc, reason: not valid java name */
        private final long m2215getMinutesUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.MINUTES);
        }

        /* renamed from: getNanoseconds-UwyO8pc, reason: not valid java name */
        private final long m2221getNanosecondsUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.NANOSECONDS);
        }

        /* renamed from: getSeconds-UwyO8pc, reason: not valid java name */
        private final long m2227getSecondsUwyO8pc(double d3) {
            return DurationKt.toDuration(d3, DurationUnit.SECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.days' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.days", imports = {"kotlin.time.Duration.Companion.days"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: days-UwyO8pc, reason: not valid java name */
        public final long m2233daysUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.DAYS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.hours' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.hours", imports = {"kotlin.time.Duration.Companion.hours"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: hours-UwyO8pc, reason: not valid java name */
        public final long m2239hoursUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.HOURS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.microseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.microseconds", imports = {"kotlin.time.Duration.Companion.microseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: microseconds-UwyO8pc, reason: not valid java name */
        public final long m2242microsecondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.MICROSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.milliseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.milliseconds", imports = {"kotlin.time.Duration.Companion.milliseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: milliseconds-UwyO8pc, reason: not valid java name */
        public final long m2245millisecondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.MILLISECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.minutes' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.minutes", imports = {"kotlin.time.Duration.Companion.minutes"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: minutes-UwyO8pc, reason: not valid java name */
        public final long m2248minutesUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.MINUTES);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.nanoseconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.nanoseconds", imports = {"kotlin.time.Duration.Companion.nanoseconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: nanoseconds-UwyO8pc, reason: not valid java name */
        public final long m2251nanosecondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.NANOSECONDS);
        }

        @SinceKotlin(version = "1.5")
        @Deprecated(message = "Use 'Double.seconds' extension property from Duration.Companion instead.", replaceWith = @ReplaceWith(expression = "value.seconds", imports = {"kotlin.time.Duration.Companion.seconds"}))
        @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.6")
        @ExperimentalTime
        /* renamed from: seconds-UwyO8pc, reason: not valid java name */
        public final long m2258secondsUwyO8pc(double value) {
            return DurationKt.toDuration(value, DurationUnit.SECONDS);
        }
    }

    private /* synthetic */ Duration(long j2) {
        this.rawValue = j2;
    }

    /* renamed from: addValuesMixedRanges-UwyO8pc, reason: not valid java name */
    private static final long m2132addValuesMixedRangesUwyO8pc(long j2, long j3, long j4) {
        long jNanosToMillis = DurationKt.nanosToMillis(j4);
        long j5 = j3 + jNanosToMillis;
        if (!new LongRange(-4611686018426L, 4611686018426L).contains(j5)) {
            return DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j5, -4611686018427387903L, DurationKt.MAX_MILLIS));
        }
        return DurationKt.durationOfNanos(DurationKt.millisToNanos(j5) + (j4 - DurationKt.millisToNanos(jNanosToMillis)));
    }

    /* renamed from: appendFractional-impl, reason: not valid java name */
    private static final void m2133appendFractionalimpl(long j2, StringBuilder sb, int i2, int i3, int i4, String str, boolean z2) {
        sb.append(i2);
        if (i3 != 0) {
            sb.append('.');
            String strPadStart = StringsKt__StringsKt.padStart(String.valueOf(i3), i4, '0');
            int i5 = -1;
            int length = strPadStart.length() - 1;
            if (length >= 0) {
                while (true) {
                    int i6 = length - 1;
                    if (strPadStart.charAt(length) != '0') {
                        i5 = length;
                        break;
                    } else if (i6 < 0) {
                        break;
                    } else {
                        length = i6;
                    }
                }
            }
            int i7 = i5 + 1;
            if (z2 || i7 >= 3) {
                sb.append((CharSequence) strPadStart, 0, ((i7 + 2) / 3) * 3);
                Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
            } else {
                sb.append((CharSequence) strPadStart, 0, i7);
                Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
            }
        }
        sb.append(str);
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Duration m2134boximpl(long j2) {
        return new Duration(j2);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static long m2136constructorimpl(long j2) {
        if (DurationJvmKt.getDurationAssertionsEnabled()) {
            if (m2167isInNanosimpl(j2)) {
                if (!new LongRange(-4611686018426999999L, DurationKt.MAX_NANOS).contains(m2163getValueimpl(j2))) {
                    throw new AssertionError(m2163getValueimpl(j2) + " ns is out of nanoseconds range");
                }
            } else {
                if (!new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS).contains(m2163getValueimpl(j2))) {
                    throw new AssertionError(m2163getValueimpl(j2) + " ms is out of milliseconds range");
                }
                if (new LongRange(-4611686018426L, 4611686018426L).contains(m2163getValueimpl(j2))) {
                    throw new AssertionError(m2163getValueimpl(j2) + " ms is denormalized");
                }
            }
        }
        return j2;
    }

    /* renamed from: div-LRDsOJo, reason: not valid java name */
    public static final double m2137divLRDsOJo(long j2, long j3) {
        DurationUnit durationUnit = (DurationUnit) ComparisonsKt___ComparisonsJvmKt.maxOf(m2161getStorageUnitimpl(j2), m2161getStorageUnitimpl(j3));
        return m2179toDoubleimpl(j2, durationUnit) / m2179toDoubleimpl(j3, durationUnit);
    }

    /* renamed from: div-UwyO8pc, reason: not valid java name */
    public static final long m2139divUwyO8pc(long j2, int i2) {
        if (i2 == 0) {
            if (m2170isPositiveimpl(j2)) {
                return INFINITE;
            }
            if (m2169isNegativeimpl(j2)) {
                return NEG_INFINITE;
            }
            throw new IllegalArgumentException("Dividing zero duration by zero yields an undefined result.");
        }
        if (m2167isInNanosimpl(j2)) {
            return DurationKt.durationOfNanos(m2163getValueimpl(j2) / i2);
        }
        if (m2168isInfiniteimpl(j2)) {
            return m2174timesUwyO8pc(j2, MathKt__MathJVMKt.getSign(i2));
        }
        long j3 = i2;
        long jM2163getValueimpl = m2163getValueimpl(j2) / j3;
        if (!new LongRange(-4611686018426L, 4611686018426L).contains(jM2163getValueimpl)) {
            return DurationKt.durationOfMillis(jM2163getValueimpl);
        }
        return DurationKt.durationOfNanos(DurationKt.millisToNanos(jM2163getValueimpl) + (DurationKt.millisToNanos(m2163getValueimpl(j2) - (jM2163getValueimpl * j3)) / j3));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m2140equalsimpl(long j2, Object obj) {
        return (obj instanceof Duration) && j2 == ((Duration) obj).getRawValue();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m2141equalsimpl0(long j2, long j3) {
        return j2 == j3;
    }

    /* renamed from: getAbsoluteValue-UwyO8pc, reason: not valid java name */
    public static final long m2142getAbsoluteValueUwyO8pc(long j2) {
        return m2169isNegativeimpl(j2) ? m2188unaryMinusUwyO8pc(j2) : j2;
    }

    @PublishedApi
    public static /* synthetic */ void getHoursComponent$annotations() {
    }

    /* renamed from: getHoursComponent-impl, reason: not valid java name */
    public static final int m2143getHoursComponentimpl(long j2) {
        if (m2168isInfiniteimpl(j2)) {
            return 0;
        }
        return (int) (m2152getInWholeHoursimpl(j2) % 24);
    }

    @Deprecated(message = "Use inWholeDays property instead or convert toDouble(DAYS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.DAYS)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInDays$annotations() {
    }

    /* renamed from: getInDays-impl, reason: not valid java name */
    public static final double m2144getInDaysimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.DAYS);
    }

    @Deprecated(message = "Use inWholeHours property instead or convert toDouble(HOURS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.HOURS)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInHours$annotations() {
    }

    /* renamed from: getInHours-impl, reason: not valid java name */
    public static final double m2145getInHoursimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.HOURS);
    }

    @Deprecated(message = "Use inWholeMicroseconds property instead or convert toDouble(MICROSECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MICROSECONDS)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInMicroseconds$annotations() {
    }

    /* renamed from: getInMicroseconds-impl, reason: not valid java name */
    public static final double m2146getInMicrosecondsimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.MICROSECONDS);
    }

    @Deprecated(message = "Use inWholeMilliseconds property instead or convert toDouble(MILLISECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MILLISECONDS)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInMilliseconds$annotations() {
    }

    /* renamed from: getInMilliseconds-impl, reason: not valid java name */
    public static final double m2147getInMillisecondsimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.MILLISECONDS);
    }

    @Deprecated(message = "Use inWholeMinutes property instead or convert toDouble(MINUTES) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.MINUTES)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInMinutes$annotations() {
    }

    /* renamed from: getInMinutes-impl, reason: not valid java name */
    public static final double m2148getInMinutesimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.MINUTES);
    }

    @Deprecated(message = "Use inWholeNanoseconds property instead or convert toDouble(NANOSECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.NANOSECONDS)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInNanoseconds$annotations() {
    }

    /* renamed from: getInNanoseconds-impl, reason: not valid java name */
    public static final double m2149getInNanosecondsimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.NANOSECONDS);
    }

    @Deprecated(message = "Use inWholeSeconds property instead or convert toDouble(SECONDS) if a double value is required.", replaceWith = @ReplaceWith(expression = "toDouble(DurationUnit.SECONDS)", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    public static /* synthetic */ void getInSeconds$annotations() {
    }

    /* renamed from: getInSeconds-impl, reason: not valid java name */
    public static final double m2150getInSecondsimpl(long j2) {
        return m2179toDoubleimpl(j2, DurationUnit.SECONDS);
    }

    /* renamed from: getInWholeDays-impl, reason: not valid java name */
    public static final long m2151getInWholeDaysimpl(long j2) {
        return m2182toLongimpl(j2, DurationUnit.DAYS);
    }

    /* renamed from: getInWholeHours-impl, reason: not valid java name */
    public static final long m2152getInWholeHoursimpl(long j2) {
        return m2182toLongimpl(j2, DurationUnit.HOURS);
    }

    /* renamed from: getInWholeMicroseconds-impl, reason: not valid java name */
    public static final long m2153getInWholeMicrosecondsimpl(long j2) {
        return m2182toLongimpl(j2, DurationUnit.MICROSECONDS);
    }

    /* renamed from: getInWholeMilliseconds-impl, reason: not valid java name */
    public static final long m2154getInWholeMillisecondsimpl(long j2) {
        return (m2166isInMillisimpl(j2) && m2165isFiniteimpl(j2)) ? m2163getValueimpl(j2) : m2182toLongimpl(j2, DurationUnit.MILLISECONDS);
    }

    /* renamed from: getInWholeMinutes-impl, reason: not valid java name */
    public static final long m2155getInWholeMinutesimpl(long j2) {
        return m2182toLongimpl(j2, DurationUnit.MINUTES);
    }

    /* renamed from: getInWholeNanoseconds-impl, reason: not valid java name */
    public static final long m2156getInWholeNanosecondsimpl(long j2) {
        long jM2163getValueimpl = m2163getValueimpl(j2);
        if (m2167isInNanosimpl(j2)) {
            return jM2163getValueimpl;
        }
        if (jM2163getValueimpl > 9223372036854L) {
            return Long.MAX_VALUE;
        }
        if (jM2163getValueimpl < -9223372036854L) {
            return Long.MIN_VALUE;
        }
        return DurationKt.millisToNanos(jM2163getValueimpl);
    }

    /* renamed from: getInWholeSeconds-impl, reason: not valid java name */
    public static final long m2157getInWholeSecondsimpl(long j2) {
        return m2182toLongimpl(j2, DurationUnit.SECONDS);
    }

    @PublishedApi
    public static /* synthetic */ void getMinutesComponent$annotations() {
    }

    /* renamed from: getMinutesComponent-impl, reason: not valid java name */
    public static final int m2158getMinutesComponentimpl(long j2) {
        if (m2168isInfiniteimpl(j2)) {
            return 0;
        }
        return (int) (m2155getInWholeMinutesimpl(j2) % 60);
    }

    @PublishedApi
    public static /* synthetic */ void getNanosecondsComponent$annotations() {
    }

    /* renamed from: getNanosecondsComponent-impl, reason: not valid java name */
    public static final int m2159getNanosecondsComponentimpl(long j2) {
        if (m2168isInfiniteimpl(j2)) {
            return 0;
        }
        return (int) (m2166isInMillisimpl(j2) ? DurationKt.millisToNanos(m2163getValueimpl(j2) % 1000) : m2163getValueimpl(j2) % 1000000000);
    }

    @PublishedApi
    public static /* synthetic */ void getSecondsComponent$annotations() {
    }

    /* renamed from: getSecondsComponent-impl, reason: not valid java name */
    public static final int m2160getSecondsComponentimpl(long j2) {
        if (m2168isInfiniteimpl(j2)) {
            return 0;
        }
        return (int) (m2157getInWholeSecondsimpl(j2) % 60);
    }

    /* renamed from: getStorageUnit-impl, reason: not valid java name */
    private static final DurationUnit m2161getStorageUnitimpl(long j2) {
        return m2167isInNanosimpl(j2) ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
    }

    /* renamed from: getUnitDiscriminator-impl, reason: not valid java name */
    private static final int m2162getUnitDiscriminatorimpl(long j2) {
        return ((int) j2) & 1;
    }

    /* renamed from: getValue-impl, reason: not valid java name */
    private static final long m2163getValueimpl(long j2) {
        return j2 >> 1;
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m2164hashCodeimpl(long j2) {
        return a.a(j2);
    }

    /* renamed from: isFinite-impl, reason: not valid java name */
    public static final boolean m2165isFiniteimpl(long j2) {
        return !m2168isInfiniteimpl(j2);
    }

    /* renamed from: isInMillis-impl, reason: not valid java name */
    private static final boolean m2166isInMillisimpl(long j2) {
        return (((int) j2) & 1) == 1;
    }

    /* renamed from: isInNanos-impl, reason: not valid java name */
    private static final boolean m2167isInNanosimpl(long j2) {
        return (((int) j2) & 1) == 0;
    }

    /* renamed from: isInfinite-impl, reason: not valid java name */
    public static final boolean m2168isInfiniteimpl(long j2) {
        return j2 == INFINITE || j2 == NEG_INFINITE;
    }

    /* renamed from: isNegative-impl, reason: not valid java name */
    public static final boolean m2169isNegativeimpl(long j2) {
        return j2 < 0;
    }

    /* renamed from: isPositive-impl, reason: not valid java name */
    public static final boolean m2170isPositiveimpl(long j2) {
        return j2 > 0;
    }

    /* renamed from: minus-LRDsOJo, reason: not valid java name */
    public static final long m2171minusLRDsOJo(long j2, long j3) {
        return m2172plusLRDsOJo(j2, m2188unaryMinusUwyO8pc(j3));
    }

    /* renamed from: plus-LRDsOJo, reason: not valid java name */
    public static final long m2172plusLRDsOJo(long j2, long j3) {
        if (m2168isInfiniteimpl(j2)) {
            if (m2165isFiniteimpl(j3) || (j3 ^ j2) >= 0) {
                return j2;
            }
            throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
        }
        if (m2168isInfiniteimpl(j3)) {
            return j3;
        }
        if ((((int) j2) & 1) != (((int) j3) & 1)) {
            return m2166isInMillisimpl(j2) ? m2132addValuesMixedRangesUwyO8pc(j2, m2163getValueimpl(j2), m2163getValueimpl(j3)) : m2132addValuesMixedRangesUwyO8pc(j2, m2163getValueimpl(j3), m2163getValueimpl(j2));
        }
        long jM2163getValueimpl = m2163getValueimpl(j2) + m2163getValueimpl(j3);
        return m2167isInNanosimpl(j2) ? DurationKt.durationOfNanosNormalized(jM2163getValueimpl) : DurationKt.durationOfMillisNormalized(jM2163getValueimpl);
    }

    /* renamed from: times-UwyO8pc, reason: not valid java name */
    public static final long m2174timesUwyO8pc(long j2, int i2) {
        if (m2168isInfiniteimpl(j2)) {
            if (i2 != 0) {
                return i2 > 0 ? j2 : m2188unaryMinusUwyO8pc(j2);
            }
            throw new IllegalArgumentException("Multiplying infinite duration by zero yields an undefined result.");
        }
        if (i2 == 0) {
            return ZERO;
        }
        long jM2163getValueimpl = m2163getValueimpl(j2);
        long j3 = i2;
        long j4 = jM2163getValueimpl * j3;
        if (!m2167isInNanosimpl(j2)) {
            return j4 / j3 == jM2163getValueimpl ? DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j4, (ClosedRange<Long>) new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS))) : MathKt__MathJVMKt.getSign(jM2163getValueimpl) * MathKt__MathJVMKt.getSign(i2) > 0 ? INFINITE : NEG_INFINITE;
        }
        if (new LongRange(-2147483647L, 2147483647L).contains(jM2163getValueimpl)) {
            return DurationKt.durationOfNanos(j4);
        }
        if (j4 / j3 == jM2163getValueimpl) {
            return DurationKt.durationOfNanosNormalized(j4);
        }
        long jNanosToMillis = DurationKt.nanosToMillis(jM2163getValueimpl);
        long j5 = jNanosToMillis * j3;
        long jNanosToMillis2 = DurationKt.nanosToMillis((jM2163getValueimpl - DurationKt.millisToNanos(jNanosToMillis)) * j3) + j5;
        return (j5 / j3 != jNanosToMillis || (jNanosToMillis2 ^ j5) < 0) ? MathKt__MathJVMKt.getSign(jM2163getValueimpl) * MathKt__MathJVMKt.getSign(i2) > 0 ? INFINITE : NEG_INFINITE : DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(jNanosToMillis2, (ClosedRange<Long>) new LongRange(-4611686018427387903L, DurationKt.MAX_MILLIS)));
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2178toComponentsimpl(long j2, @NotNull Function5<? super Long, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2151getInWholeDaysimpl(j2)), Integer.valueOf(m2143getHoursComponentimpl(j2)), Integer.valueOf(m2158getMinutesComponentimpl(j2)), Integer.valueOf(m2160getSecondsComponentimpl(j2)), Integer.valueOf(m2159getNanosecondsComponentimpl(j2)));
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    public static final double m2179toDoubleimpl(long j2, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j2 == INFINITE) {
            return Double.POSITIVE_INFINITY;
        }
        if (j2 == NEG_INFINITE) {
            return Double.NEGATIVE_INFINITY;
        }
        return DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(m2163getValueimpl(j2), m2161getStorageUnitimpl(j2), unit);
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    public static final int m2180toIntimpl(long j2, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        return (int) RangesKt___RangesKt.coerceIn(m2182toLongimpl(j2, unit), -2147483648L, 2147483647L);
    }

    @NotNull
    /* renamed from: toIsoString-impl, reason: not valid java name */
    public static final String m2181toIsoStringimpl(long j2) {
        StringBuilder sb = new StringBuilder();
        if (m2169isNegativeimpl(j2)) {
            sb.append(CharPool.DASHED);
        }
        sb.append("PT");
        long jM2142getAbsoluteValueUwyO8pc = m2142getAbsoluteValueUwyO8pc(j2);
        long jM2152getInWholeHoursimpl = m2152getInWholeHoursimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2158getMinutesComponentimpl = m2158getMinutesComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2160getSecondsComponentimpl = m2160getSecondsComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2159getNanosecondsComponentimpl = m2159getNanosecondsComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        if (m2168isInfiniteimpl(j2)) {
            jM2152getInWholeHoursimpl = 9999999999999L;
        }
        boolean z2 = true;
        boolean z3 = jM2152getInWholeHoursimpl != 0;
        boolean z4 = (iM2160getSecondsComponentimpl == 0 && iM2159getNanosecondsComponentimpl == 0) ? false : true;
        if (iM2158getMinutesComponentimpl == 0 && (!z4 || !z3)) {
            z2 = false;
        }
        if (z3) {
            sb.append(jM2152getInWholeHoursimpl);
            sb.append('H');
        }
        if (z2) {
            sb.append(iM2158getMinutesComponentimpl);
            sb.append('M');
        }
        if (z4 || (!z3 && !z2)) {
            m2133appendFractionalimpl(j2, sb, iM2160getSecondsComponentimpl, iM2159getNanosecondsComponentimpl, 9, ExifInterface.LATITUDE_SOUTH, true);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    public static final long m2182toLongimpl(long j2, @NotNull DurationUnit unit) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (j2 == INFINITE) {
            return Long.MAX_VALUE;
        }
        if (j2 == NEG_INFINITE) {
            return Long.MIN_VALUE;
        }
        return DurationUnitKt__DurationUnitJvmKt.convertDurationUnit(m2163getValueimpl(j2), m2161getStorageUnitimpl(j2), unit);
    }

    @Deprecated(message = "Use inWholeMilliseconds property instead.", replaceWith = @ReplaceWith(expression = "this.inWholeMilliseconds", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    /* renamed from: toLongMilliseconds-impl, reason: not valid java name */
    public static final long m2183toLongMillisecondsimpl(long j2) {
        return m2154getInWholeMillisecondsimpl(j2);
    }

    @Deprecated(message = "Use inWholeNanoseconds property instead.", replaceWith = @ReplaceWith(expression = "this.inWholeNanoseconds", imports = {}))
    @DeprecatedSinceKotlin(errorSince = "1.8", warningSince = "1.5")
    @ExperimentalTime
    /* renamed from: toLongNanoseconds-impl, reason: not valid java name */
    public static final long m2184toLongNanosecondsimpl(long j2) {
        return m2156getInWholeNanosecondsimpl(j2);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m2185toStringimpl(long j2) {
        if (j2 == 0) {
            return "0s";
        }
        if (j2 == INFINITE) {
            return "Infinity";
        }
        if (j2 == NEG_INFINITE) {
            return "-Infinity";
        }
        boolean zM2169isNegativeimpl = m2169isNegativeimpl(j2);
        StringBuilder sb = new StringBuilder();
        if (zM2169isNegativeimpl) {
            sb.append(CharPool.DASHED);
        }
        long jM2142getAbsoluteValueUwyO8pc = m2142getAbsoluteValueUwyO8pc(j2);
        long jM2151getInWholeDaysimpl = m2151getInWholeDaysimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2143getHoursComponentimpl = m2143getHoursComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2158getMinutesComponentimpl = m2158getMinutesComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2160getSecondsComponentimpl = m2160getSecondsComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        int iM2159getNanosecondsComponentimpl = m2159getNanosecondsComponentimpl(jM2142getAbsoluteValueUwyO8pc);
        int i2 = 0;
        boolean z2 = jM2151getInWholeDaysimpl != 0;
        boolean z3 = iM2143getHoursComponentimpl != 0;
        boolean z4 = iM2158getMinutesComponentimpl != 0;
        boolean z5 = (iM2160getSecondsComponentimpl == 0 && iM2159getNanosecondsComponentimpl == 0) ? false : true;
        if (z2) {
            sb.append(jM2151getInWholeDaysimpl);
            sb.append('d');
            i2 = 1;
        }
        if (z3 || (z2 && (z4 || z5))) {
            int i3 = i2 + 1;
            if (i2 > 0) {
                sb.append(' ');
            }
            sb.append(iM2143getHoursComponentimpl);
            sb.append('h');
            i2 = i3;
        }
        if (z4 || (z5 && (z3 || z2))) {
            int i4 = i2 + 1;
            if (i2 > 0) {
                sb.append(' ');
            }
            sb.append(iM2158getMinutesComponentimpl);
            sb.append('m');
            i2 = i4;
        }
        if (z5) {
            int i5 = i2 + 1;
            if (i2 > 0) {
                sb.append(' ');
            }
            if (iM2160getSecondsComponentimpl != 0 || z2 || z3 || z4) {
                m2133appendFractionalimpl(j2, sb, iM2160getSecondsComponentimpl, iM2159getNanosecondsComponentimpl, 9, "s", false);
            } else if (iM2159getNanosecondsComponentimpl >= 1000000) {
                m2133appendFractionalimpl(j2, sb, iM2159getNanosecondsComponentimpl / 1000000, iM2159getNanosecondsComponentimpl % 1000000, 6, "ms", false);
            } else if (iM2159getNanosecondsComponentimpl >= 1000) {
                m2133appendFractionalimpl(j2, sb, iM2159getNanosecondsComponentimpl / 1000, iM2159getNanosecondsComponentimpl % 1000, 3, "us", false);
            } else {
                sb.append(iM2159getNanosecondsComponentimpl);
                sb.append(NotificationStyle.NOTIFICATION_STYLE);
            }
            i2 = i5;
        }
        if (zM2169isNegativeimpl && i2 > 1) {
            sb.insert(1, '(').append(')');
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /* renamed from: toString-impl$default, reason: not valid java name */
    public static /* synthetic */ String m2187toStringimpl$default(long j2, DurationUnit durationUnit, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return m2186toStringimpl(j2, durationUnit, i2);
    }

    /* renamed from: unaryMinus-UwyO8pc, reason: not valid java name */
    public static final long m2188unaryMinusUwyO8pc(long j2) {
        return DurationKt.durationOf(-m2163getValueimpl(j2), ((int) j2) & 1);
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Duration duration) {
        return m2189compareToLRDsOJo(duration.getRawValue());
    }

    /* renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public int m2189compareToLRDsOJo(long j2) {
        return m2135compareToLRDsOJo(this.rawValue, j2);
    }

    public boolean equals(Object obj) {
        return m2140equalsimpl(this.rawValue, obj);
    }

    public int hashCode() {
        return m2164hashCodeimpl(this.rawValue);
    }

    @NotNull
    public String toString() {
        return m2185toStringimpl(this.rawValue);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ long getRawValue() {
        return this.rawValue;
    }

    /* renamed from: compareTo-LRDsOJo, reason: not valid java name */
    public static int m2135compareToLRDsOJo(long j2, long j3) {
        long j4 = j2 ^ j3;
        if (j4 < 0 || (((int) j4) & 1) == 0) {
            return Intrinsics.compare(j2, j3);
        }
        int i2 = (((int) j2) & 1) - (((int) j3) & 1);
        return m2169isNegativeimpl(j2) ? -i2 : i2;
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2177toComponentsimpl(long j2, @NotNull Function4<? super Long, ? super Integer, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2152getInWholeHoursimpl(j2)), Integer.valueOf(m2158getMinutesComponentimpl(j2)), Integer.valueOf(m2160getSecondsComponentimpl(j2)), Integer.valueOf(m2159getNanosecondsComponentimpl(j2)));
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2176toComponentsimpl(long j2, @NotNull Function3<? super Long, ? super Integer, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2155getInWholeMinutesimpl(j2)), Integer.valueOf(m2160getSecondsComponentimpl(j2)), Integer.valueOf(m2159getNanosecondsComponentimpl(j2)));
    }

    /* renamed from: toComponents-impl, reason: not valid java name */
    public static final <T> T m2175toComponentsimpl(long j2, @NotNull Function2<? super Long, ? super Integer, ? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        return action.invoke(Long.valueOf(m2157getInWholeSecondsimpl(j2)), Integer.valueOf(m2159getNanosecondsComponentimpl(j2)));
    }

    /* renamed from: div-UwyO8pc, reason: not valid java name */
    public static final long m2138divUwyO8pc(long j2, double d3) {
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(d3);
        if ((((double) iRoundToInt) == d3) && iRoundToInt != 0) {
            return m2139divUwyO8pc(j2, iRoundToInt);
        }
        DurationUnit durationUnitM2161getStorageUnitimpl = m2161getStorageUnitimpl(j2);
        return DurationKt.toDuration(m2179toDoubleimpl(j2, durationUnitM2161getStorageUnitimpl) / d3, durationUnitM2161getStorageUnitimpl);
    }

    /* renamed from: times-UwyO8pc, reason: not valid java name */
    public static final long m2173timesUwyO8pc(long j2, double d3) {
        int iRoundToInt = MathKt__MathJVMKt.roundToInt(d3);
        if (((double) iRoundToInt) == d3) {
            return m2174timesUwyO8pc(j2, iRoundToInt);
        }
        DurationUnit durationUnitM2161getStorageUnitimpl = m2161getStorageUnitimpl(j2);
        return DurationKt.toDuration(m2179toDoubleimpl(j2, durationUnitM2161getStorageUnitimpl) * d3, durationUnitM2161getStorageUnitimpl);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static final String m2186toStringimpl(long j2, @NotNull DurationUnit unit, int i2) {
        Intrinsics.checkNotNullParameter(unit, "unit");
        if (i2 >= 0) {
            double dM2179toDoubleimpl = m2179toDoubleimpl(j2, unit);
            if (Double.isInfinite(dM2179toDoubleimpl)) {
                return String.valueOf(dM2179toDoubleimpl);
            }
            return DurationJvmKt.formatToExactDecimals(dM2179toDoubleimpl, RangesKt___RangesKt.coerceAtMost(i2, 12)) + DurationUnitKt__DurationUnitKt.shortName(unit);
        }
        throw new IllegalArgumentException(("decimals must be not negative, but was " + i2).toString());
    }
}
