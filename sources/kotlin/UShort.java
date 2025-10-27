package kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt___URangesKt;
import okhttp3.internal.ws.WebSocketProtocol;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.5")
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 v2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001vB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010\u0010J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0013J\u001b\u0010&\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u0010J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0013J\u001b\u00102\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001b\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b:\u0010\u0013J\u001b\u00107\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\b;\u0010\u001fJ\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b<\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0010J\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0013J\u001b\u0010?\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bI\u0010GJ\u001b\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bK\u0010\u0010J\u001b\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u0013J\u001b\u0010J\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bM\u0010\u001fJ\u001b\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u0018J\u001b\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bP\u0010\u0010J\u001b\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bQ\u0010\u0013J\u001b\u0010O\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bR\u0010\u001fJ\u001b\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bS\u0010\u0018J\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b¢\u0006\u0004\bZ\u0010[J\u0010\u0010\\\u001a\u00020]H\u0087\b¢\u0006\u0004\b^\u0010_J\u0010\u0010`\u001a\u00020\rH\u0087\b¢\u0006\u0004\ba\u0010-J\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bg\u0010\u0005J\u000f\u0010h\u001a\u00020iH\u0016¢\u0006\u0004\bj\u0010kJ\u0016\u0010l\u001a\u00020\u000eH\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bm\u0010WJ\u0016\u0010n\u001a\u00020\u0011H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bo\u0010-J\u0016\u0010p\u001a\u00020\u0014H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bq\u0010eJ\u0016\u0010r\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bs\u0010\u0005J\u001b\u0010t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bu\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006w"}, d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", TtmlNode.TAG_DIV, "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-xj2QHRw", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
/* loaded from: classes8.dex */
public final class UShort implements Comparable<UShort> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", ExifInterface.LATITUDE_SOUTH, "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    private /* synthetic */ UShort(short s2) {
        this.data = s2;
    }

    @InlineOnly
    /* renamed from: and-xj2QHRw, reason: not valid java name */
    private static final short m1057andxj2QHRw(short s2, short s3) {
        return m1064constructorimpl((short) (s2 & s3));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShort m1058boximpl(short s2) {
        return new UShort(s2);
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m1059compareTo7apg3OU(short s2, byte b3) {
        return Intrinsics.compare(s2 & 65535, b3 & 255);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m1060compareToVKZWuLQ(short s2, long j2) {
        return Long.compare(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX) ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE);
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m1061compareToWZ4Q5Ns(short s2, int i2) {
        return Integer.compare(UInt.m878constructorimpl(s2 & 65535) ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE);
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private int m1062compareToxj2QHRw(short s2) {
        return Intrinsics.compare(getData() & 65535, s2 & 65535);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    /* renamed from: constructor-impl, reason: not valid java name */
    public static short m1064constructorimpl(short s2) {
        return s2;
    }

    @InlineOnly
    /* renamed from: dec-Mh2AYeg, reason: not valid java name */
    private static final short m1065decMh2AYeg(short s2) {
        return m1064constructorimpl((short) (s2 - 1));
    }

    @InlineOnly
    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m1066div7apg3OU(short s2, byte b3) {
        return b.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(b3 & 255));
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m1067divVKZWuLQ(short s2, long j2) {
        return f.a(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX), j2);
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m1068divWZ4Q5Ns(short s2, int i2) {
        return b.a(UInt.m878constructorimpl(s2 & 65535), i2);
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m1069divxj2QHRw(short s2, short s3) {
        return b.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(s3 & 65535));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m1070equalsimpl(short s2, Object obj) {
        return (obj instanceof UShort) && s2 == ((UShort) obj).getData();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1071equalsimpl0(short s2, short s3) {
        return s2 == s3;
    }

    @InlineOnly
    /* renamed from: floorDiv-7apg3OU, reason: not valid java name */
    private static final int m1072floorDiv7apg3OU(short s2, byte b3) {
        return b.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(b3 & 255));
    }

    @InlineOnly
    /* renamed from: floorDiv-VKZWuLQ, reason: not valid java name */
    private static final long m1073floorDivVKZWuLQ(short s2, long j2) {
        return f.a(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX), j2);
    }

    @InlineOnly
    /* renamed from: floorDiv-WZ4Q5Ns, reason: not valid java name */
    private static final int m1074floorDivWZ4Q5Ns(short s2, int i2) {
        return b.a(UInt.m878constructorimpl(s2 & 65535), i2);
    }

    @InlineOnly
    /* renamed from: floorDiv-xj2QHRw, reason: not valid java name */
    private static final int m1075floorDivxj2QHRw(short s2, short s3) {
        return b.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(s3 & 65535));
    }

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m1076hashCodeimpl(short s2) {
        return s2;
    }

    @InlineOnly
    /* renamed from: inc-Mh2AYeg, reason: not valid java name */
    private static final short m1077incMh2AYeg(short s2) {
        return m1064constructorimpl((short) (s2 + 1));
    }

    @InlineOnly
    /* renamed from: inv-Mh2AYeg, reason: not valid java name */
    private static final short m1078invMh2AYeg(short s2) {
        return m1064constructorimpl((short) (~s2));
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m1079minus7apg3OU(short s2, byte b3) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) - UInt.m878constructorimpl(b3 & 255));
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m1080minusVKZWuLQ(short s2, long j2) {
        return ULong.m957constructorimpl(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX) - j2);
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m1081minusWZ4Q5Ns(short s2, int i2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) - i2);
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m1082minusxj2QHRw(short s2, short s3) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) - UInt.m878constructorimpl(s3 & 65535));
    }

    @InlineOnly
    /* renamed from: mod-7apg3OU, reason: not valid java name */
    private static final byte m1083mod7apg3OU(short s2, byte b3) {
        return UByte.m801constructorimpl((byte) a.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(b3 & 255)));
    }

    @InlineOnly
    /* renamed from: mod-VKZWuLQ, reason: not valid java name */
    private static final long m1084modVKZWuLQ(short s2, long j2) {
        return d.a(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX), j2);
    }

    @InlineOnly
    /* renamed from: mod-WZ4Q5Ns, reason: not valid java name */
    private static final int m1085modWZ4Q5Ns(short s2, int i2) {
        return a.a(UInt.m878constructorimpl(s2 & 65535), i2);
    }

    @InlineOnly
    /* renamed from: mod-xj2QHRw, reason: not valid java name */
    private static final short m1086modxj2QHRw(short s2, short s3) {
        return m1064constructorimpl((short) a.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(s3 & 65535)));
    }

    @InlineOnly
    /* renamed from: or-xj2QHRw, reason: not valid java name */
    private static final short m1087orxj2QHRw(short s2, short s3) {
        return m1064constructorimpl((short) (s2 | s3));
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m1088plus7apg3OU(short s2, byte b3) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) + UInt.m878constructorimpl(b3 & 255));
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m1089plusVKZWuLQ(short s2, long j2) {
        return ULong.m957constructorimpl(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX) + j2);
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m1090plusWZ4Q5Ns(short s2, int i2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) + i2);
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m1091plusxj2QHRw(short s2, short s3) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) + UInt.m878constructorimpl(s3 & 65535));
    }

    @InlineOnly
    /* renamed from: rangeTo-xj2QHRw, reason: not valid java name */
    private static final UIntRange m1092rangeToxj2QHRw(short s2, short s3) {
        return new UIntRange(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(s3 & 65535), null);
    }

    @SinceKotlin(version = "1.7")
    @ExperimentalStdlibApi
    @InlineOnly
    /* renamed from: rangeUntil-xj2QHRw, reason: not valid java name */
    private static final UIntRange m1093rangeUntilxj2QHRw(short s2, short s3) {
        return URangesKt___URangesKt.m2075untilJ1ME1BU(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(s3 & 65535));
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m1094rem7apg3OU(short s2, byte b3) {
        return a.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(b3 & 255));
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m1095remVKZWuLQ(short s2, long j2) {
        return d.a(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX), j2);
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m1096remWZ4Q5Ns(short s2, int i2) {
        return a.a(UInt.m878constructorimpl(s2 & 65535), i2);
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m1097remxj2QHRw(short s2, short s3) {
        return a.a(UInt.m878constructorimpl(s2 & 65535), UInt.m878constructorimpl(s3 & 65535));
    }

    @InlineOnly
    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m1098times7apg3OU(short s2, byte b3) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) * UInt.m878constructorimpl(b3 & 255));
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m1099timesVKZWuLQ(short s2, long j2) {
        return ULong.m957constructorimpl(ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX) * j2);
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m1100timesWZ4Q5Ns(short s2, int i2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) * i2);
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m1101timesxj2QHRw(short s2, short s3) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(s2 & 65535) * UInt.m878constructorimpl(s3 & 65535));
    }

    @InlineOnly
    /* renamed from: toByte-impl, reason: not valid java name */
    private static final byte m1102toByteimpl(short s2) {
        return (byte) s2;
    }

    @InlineOnly
    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m1103toDoubleimpl(short s2) {
        return s2 & 65535;
    }

    @InlineOnly
    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m1104toFloatimpl(short s2) {
        return s2 & 65535;
    }

    @InlineOnly
    /* renamed from: toInt-impl, reason: not valid java name */
    private static final int m1105toIntimpl(short s2) {
        return s2 & 65535;
    }

    @InlineOnly
    /* renamed from: toLong-impl, reason: not valid java name */
    private static final long m1106toLongimpl(short s2) {
        return s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    @InlineOnly
    /* renamed from: toShort-impl, reason: not valid java name */
    private static final short m1107toShortimpl(short s2) {
        return s2;
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m1108toStringimpl(short s2) {
        return String.valueOf(s2 & 65535);
    }

    @InlineOnly
    /* renamed from: toUByte-w2LRezQ, reason: not valid java name */
    private static final byte m1109toUBytew2LRezQ(short s2) {
        return UByte.m801constructorimpl((byte) s2);
    }

    @InlineOnly
    /* renamed from: toUInt-pVg5ArA, reason: not valid java name */
    private static final int m1110toUIntpVg5ArA(short s2) {
        return UInt.m878constructorimpl(s2 & 65535);
    }

    @InlineOnly
    /* renamed from: toULong-s-VKNKU, reason: not valid java name */
    private static final long m1111toULongsVKNKU(short s2) {
        return ULong.m957constructorimpl(s2 & WebSocketProtocol.PAYLOAD_SHORT_MAX);
    }

    @InlineOnly
    /* renamed from: toUShort-Mh2AYeg, reason: not valid java name */
    private static final short m1112toUShortMh2AYeg(short s2) {
        return s2;
    }

    @InlineOnly
    /* renamed from: xor-xj2QHRw, reason: not valid java name */
    private static final short m1113xorxj2QHRw(short s2, short s3) {
        return m1064constructorimpl((short) (s2 ^ s3));
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return Intrinsics.compare(getData() & 65535, uShort.getData() & 65535);
    }

    public boolean equals(Object obj) {
        return m1070equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m1076hashCodeimpl(this.data);
    }

    @NotNull
    public String toString() {
        return m1108toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ short getData() {
        return this.data;
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static int m1063compareToxj2QHRw(short s2, short s3) {
        return Intrinsics.compare(s2 & 65535, s3 & 65535);
    }
}
