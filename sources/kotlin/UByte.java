package kotlin;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.internal.InlineOnly;
import kotlin.internal.IntrinsicConstEvaluation;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt___URangesKt;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.5")
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 v2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001vB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010\u000fJ\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0012J\u001b\u0010&\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u000fJ\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0012J\u001b\u00102\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b8\u0010\u000bJ\u001b\u00107\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0004\b9\u0010\u0012J\u001b\u00107\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\bø\u0001\u0000¢\u0006\u0004\b:\u0010\u001fJ\u001b\u00107\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\b;\u0010<J\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u000fJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0012J\u001b\u0010?\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bI\u0010GJ\u001b\u0010J\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bK\u0010\u000fJ\u001b\u0010J\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u0012J\u001b\u0010J\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bM\u0010\u001fJ\u001b\u0010J\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u0018J\u001b\u0010O\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bP\u0010\u000fJ\u001b\u0010O\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\u0087\nø\u0001\u0000¢\u0006\u0004\bQ\u0010\u0012J\u001b\u0010O\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\u0087\nø\u0001\u0000¢\u0006\u0004\bR\u0010\u001fJ\u001b\u0010O\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bS\u0010\u0018J\u0010\u0010T\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bU\u0010\u0005J\u0010\u0010V\u001a\u00020WH\u0087\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020[H\u0087\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020\rH\u0087\b¢\u0006\u0004\b_\u0010-J\u0010\u0010`\u001a\u00020aH\u0087\b¢\u0006\u0004\bb\u0010cJ\u0010\u0010d\u001a\u00020eH\u0087\b¢\u0006\u0004\bf\u0010gJ\u000f\u0010h\u001a\u00020iH\u0016¢\u0006\u0004\bj\u0010kJ\u0016\u0010l\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bm\u0010\u0005J\u0016\u0010n\u001a\u00020\u0010H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bo\u0010-J\u0016\u0010p\u001a\u00020\u0013H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bq\u0010cJ\u0016\u0010r\u001a\u00020\u0016H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bs\u0010gJ\u001b\u0010t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bu\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006w"}, d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-w2LRezQ", TtmlNode.TAG_DIV, "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(BLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(B)I", "inc", "inc-w2LRezQ", "inv", "inv-w2LRezQ", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(BS)S", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-7apg3OU", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
@JvmInline
@WasExperimental(markerClass = {ExperimentalUnsignedTypes.class})
/* loaded from: classes8.dex */
public final class UByte implements Comparable<UByte> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final byte MAX_VALUE = -1;
    public static final byte MIN_VALUE = 0;
    public static final int SIZE_BITS = 8;
    public static final int SIZE_BYTES = 1;
    private final byte data;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    private /* synthetic */ UByte(byte b3) {
        this.data = b3;
    }

    @InlineOnly
    /* renamed from: and-7apg3OU, reason: not valid java name */
    private static final byte m794and7apg3OU(byte b3, byte b4) {
        return m801constructorimpl((byte) (b3 & b4));
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UByte m795boximpl(byte b3) {
        return new UByte(b3);
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private int m796compareTo7apg3OU(byte b3) {
        return Intrinsics.compare(getData() & 255, b3 & 255);
    }

    @InlineOnly
    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m798compareToVKZWuLQ(byte b3, long j2) {
        return Long.compare(ULong.m957constructorimpl(b3 & 255) ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE);
    }

    @InlineOnly
    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m799compareToWZ4Q5Ns(byte b3, int i2) {
        return Integer.compare(UInt.m878constructorimpl(b3 & 255) ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE);
    }

    @InlineOnly
    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static final int m800compareToxj2QHRw(byte b3, short s2) {
        return Intrinsics.compare(b3 & 255, s2 & 65535);
    }

    @PublishedApi
    @IntrinsicConstEvaluation
    /* renamed from: constructor-impl, reason: not valid java name */
    public static byte m801constructorimpl(byte b3) {
        return b3;
    }

    @InlineOnly
    /* renamed from: dec-w2LRezQ, reason: not valid java name */
    private static final byte m802decw2LRezQ(byte b3) {
        return m801constructorimpl((byte) (b3 - 1));
    }

    @InlineOnly
    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m803div7apg3OU(byte b3, byte b4) {
        return b.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m804divVKZWuLQ(byte b3, long j2) {
        return f.a(ULong.m957constructorimpl(b3 & 255), j2);
    }

    @InlineOnly
    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m805divWZ4Q5Ns(byte b3, int i2) {
        return b.a(UInt.m878constructorimpl(b3 & 255), i2);
    }

    @InlineOnly
    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m806divxj2QHRw(byte b3, short s2) {
        return b.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(s2 & 65535));
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m807equalsimpl(byte b3, Object obj) {
        return (obj instanceof UByte) && b3 == ((UByte) obj).getData();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m808equalsimpl0(byte b3, byte b4) {
        return b3 == b4;
    }

    @InlineOnly
    /* renamed from: floorDiv-7apg3OU, reason: not valid java name */
    private static final int m809floorDiv7apg3OU(byte b3, byte b4) {
        return b.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: floorDiv-VKZWuLQ, reason: not valid java name */
    private static final long m810floorDivVKZWuLQ(byte b3, long j2) {
        return f.a(ULong.m957constructorimpl(b3 & 255), j2);
    }

    @InlineOnly
    /* renamed from: floorDiv-WZ4Q5Ns, reason: not valid java name */
    private static final int m811floorDivWZ4Q5Ns(byte b3, int i2) {
        return b.a(UInt.m878constructorimpl(b3 & 255), i2);
    }

    @InlineOnly
    /* renamed from: floorDiv-xj2QHRw, reason: not valid java name */
    private static final int m812floorDivxj2QHRw(byte b3, short s2) {
        return b.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(s2 & 65535));
    }

    @PublishedApi
    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m813hashCodeimpl(byte b3) {
        return b3;
    }

    @InlineOnly
    /* renamed from: inc-w2LRezQ, reason: not valid java name */
    private static final byte m814incw2LRezQ(byte b3) {
        return m801constructorimpl((byte) (b3 + 1));
    }

    @InlineOnly
    /* renamed from: inv-w2LRezQ, reason: not valid java name */
    private static final byte m815invw2LRezQ(byte b3) {
        return m801constructorimpl((byte) (~b3));
    }

    @InlineOnly
    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m816minus7apg3OU(byte b3, byte b4) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) - UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m817minusVKZWuLQ(byte b3, long j2) {
        return ULong.m957constructorimpl(ULong.m957constructorimpl(b3 & 255) - j2);
    }

    @InlineOnly
    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m818minusWZ4Q5Ns(byte b3, int i2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) - i2);
    }

    @InlineOnly
    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m819minusxj2QHRw(byte b3, short s2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) - UInt.m878constructorimpl(s2 & 65535));
    }

    @InlineOnly
    /* renamed from: mod-7apg3OU, reason: not valid java name */
    private static final byte m820mod7apg3OU(byte b3, byte b4) {
        return m801constructorimpl((byte) a.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(b4 & 255)));
    }

    @InlineOnly
    /* renamed from: mod-VKZWuLQ, reason: not valid java name */
    private static final long m821modVKZWuLQ(byte b3, long j2) {
        return d.a(ULong.m957constructorimpl(b3 & 255), j2);
    }

    @InlineOnly
    /* renamed from: mod-WZ4Q5Ns, reason: not valid java name */
    private static final int m822modWZ4Q5Ns(byte b3, int i2) {
        return a.a(UInt.m878constructorimpl(b3 & 255), i2);
    }

    @InlineOnly
    /* renamed from: mod-xj2QHRw, reason: not valid java name */
    private static final short m823modxj2QHRw(byte b3, short s2) {
        return UShort.m1064constructorimpl((short) a.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(s2 & 65535)));
    }

    @InlineOnly
    /* renamed from: or-7apg3OU, reason: not valid java name */
    private static final byte m824or7apg3OU(byte b3, byte b4) {
        return m801constructorimpl((byte) (b3 | b4));
    }

    @InlineOnly
    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m825plus7apg3OU(byte b3, byte b4) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) + UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m826plusVKZWuLQ(byte b3, long j2) {
        return ULong.m957constructorimpl(ULong.m957constructorimpl(b3 & 255) + j2);
    }

    @InlineOnly
    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m827plusWZ4Q5Ns(byte b3, int i2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) + i2);
    }

    @InlineOnly
    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m828plusxj2QHRw(byte b3, short s2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) + UInt.m878constructorimpl(s2 & 65535));
    }

    @InlineOnly
    /* renamed from: rangeTo-7apg3OU, reason: not valid java name */
    private static final UIntRange m829rangeTo7apg3OU(byte b3, byte b4) {
        return new UIntRange(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(b4 & 255), null);
    }

    @SinceKotlin(version = "1.7")
    @ExperimentalStdlibApi
    @InlineOnly
    /* renamed from: rangeUntil-7apg3OU, reason: not valid java name */
    private static final UIntRange m830rangeUntil7apg3OU(byte b3, byte b4) {
        return URangesKt___URangesKt.m2075untilJ1ME1BU(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m831rem7apg3OU(byte b3, byte b4) {
        return a.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m832remVKZWuLQ(byte b3, long j2) {
        return d.a(ULong.m957constructorimpl(b3 & 255), j2);
    }

    @InlineOnly
    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m833remWZ4Q5Ns(byte b3, int i2) {
        return a.a(UInt.m878constructorimpl(b3 & 255), i2);
    }

    @InlineOnly
    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m834remxj2QHRw(byte b3, short s2) {
        return a.a(UInt.m878constructorimpl(b3 & 255), UInt.m878constructorimpl(s2 & 65535));
    }

    @InlineOnly
    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m835times7apg3OU(byte b3, byte b4) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) * UInt.m878constructorimpl(b4 & 255));
    }

    @InlineOnly
    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m836timesVKZWuLQ(byte b3, long j2) {
        return ULong.m957constructorimpl(ULong.m957constructorimpl(b3 & 255) * j2);
    }

    @InlineOnly
    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m837timesWZ4Q5Ns(byte b3, int i2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) * i2);
    }

    @InlineOnly
    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m838timesxj2QHRw(byte b3, short s2) {
        return UInt.m878constructorimpl(UInt.m878constructorimpl(b3 & 255) * UInt.m878constructorimpl(s2 & 65535));
    }

    @InlineOnly
    /* renamed from: toByte-impl, reason: not valid java name */
    private static final byte m839toByteimpl(byte b3) {
        return b3;
    }

    @InlineOnly
    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m840toDoubleimpl(byte b3) {
        return b3 & 255;
    }

    @InlineOnly
    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m841toFloatimpl(byte b3) {
        return b3 & 255;
    }

    @InlineOnly
    /* renamed from: toInt-impl, reason: not valid java name */
    private static final int m842toIntimpl(byte b3) {
        return b3 & 255;
    }

    @InlineOnly
    /* renamed from: toLong-impl, reason: not valid java name */
    private static final long m843toLongimpl(byte b3) {
        return b3 & 255;
    }

    @InlineOnly
    /* renamed from: toShort-impl, reason: not valid java name */
    private static final short m844toShortimpl(byte b3) {
        return (short) (b3 & 255);
    }

    @NotNull
    /* renamed from: toString-impl, reason: not valid java name */
    public static String m845toStringimpl(byte b3) {
        return String.valueOf(b3 & 255);
    }

    @InlineOnly
    /* renamed from: toUByte-w2LRezQ, reason: not valid java name */
    private static final byte m846toUBytew2LRezQ(byte b3) {
        return b3;
    }

    @InlineOnly
    /* renamed from: toUInt-pVg5ArA, reason: not valid java name */
    private static final int m847toUIntpVg5ArA(byte b3) {
        return UInt.m878constructorimpl(b3 & 255);
    }

    @InlineOnly
    /* renamed from: toULong-s-VKNKU, reason: not valid java name */
    private static final long m848toULongsVKNKU(byte b3) {
        return ULong.m957constructorimpl(b3 & 255);
    }

    @InlineOnly
    /* renamed from: toUShort-Mh2AYeg, reason: not valid java name */
    private static final short m849toUShortMh2AYeg(byte b3) {
        return UShort.m1064constructorimpl((short) (b3 & 255));
    }

    @InlineOnly
    /* renamed from: xor-7apg3OU, reason: not valid java name */
    private static final byte m850xor7apg3OU(byte b3, byte b4) {
        return m801constructorimpl((byte) (b3 ^ b4));
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UByte uByte) {
        return Intrinsics.compare(getData() & 255, uByte.getData() & 255);
    }

    public boolean equals(Object obj) {
        return m807equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m813hashCodeimpl(this.data);
    }

    @NotNull
    public String toString() {
        return m845toStringimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ byte getData() {
        return this.data;
    }

    @InlineOnly
    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static int m797compareTo7apg3OU(byte b3, byte b4) {
        return Intrinsics.compare(b3 & 255, b4 & 255);
    }
}
