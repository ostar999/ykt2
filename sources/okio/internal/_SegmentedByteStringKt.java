package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio._UtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a-\u0010\u0006\u001a\u00020\u0007*\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a\u0017\u0010\u000e\u001a\u00020\u000f*\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0080\b\u001a\r\u0010\u0012\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\r\u0010\u0013\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0017\u001a\u00020\u000f*\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0017\u001a\u00020\u000f*\u00020\b2\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a\u001d\u0010\u001a\u001a\u00020\u0019*\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u001d\u001a\u00020\u000b*\u00020\bH\u0080\b\u001a%\u0010\u001e\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a]\u0010!\u001a\u00020\u0007*\u00020\b2K\u0010\"\u001aG\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00070#H\u0080\bø\u0001\u0000\u001aj\u0010!\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00012K\u0010\"\u001aG\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00070#H\u0082\b\u001a\u0014\u0010'\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0001H\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006("}, d2 = {"binarySearch", "", "", "value", "fromIndex", "toIndex", "commonCopyInto", "", "Lokio/SegmentedByteString;", "offset", TypedValues.AttributesType.S_TARGET, "", "targetOffset", "byteCount", "commonEquals", "", "other", "", "commonGetSize", "commonHashCode", "commonInternalGet", "", "pos", "commonRangeEquals", "otherOffset", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes9.dex */
public final class _SegmentedByteStringKt {
    public static final int binarySearch(@NotNull int[] iArr, int i2, int i3, int i4) {
        Intrinsics.checkNotNullParameter(iArr, "<this>");
        int i5 = i4 - 1;
        while (i3 <= i5) {
            int i6 = (i3 + i5) >>> 1;
            int i7 = iArr[i6];
            if (i7 < i2) {
                i3 = i6 + 1;
            } else {
                if (i7 <= i2) {
                    return i6;
                }
                i5 = i6 - 1;
            }
        }
        return (-i3) - 1;
    }

    public static final void commonCopyInto(@NotNull SegmentedByteString segmentedByteString, int i2, @NotNull byte[] target, int i3, int i4) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        long j2 = i4;
        _UtilKt.checkOffsetAndCount(segmentedByteString.size(), i2, j2);
        _UtilKt.checkOffsetAndCount(target.length, i3, j2);
        int i5 = i4 + i2;
        int iSegment = segment(segmentedByteString, i2);
        while (i2 < i5) {
            int i6 = iSegment == 0 ? 0 : segmentedByteString.getDirectory()[iSegment - 1];
            int i7 = segmentedByteString.getDirectory()[iSegment] - i6;
            int i8 = segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + iSegment];
            int iMin = Math.min(i5, i7 + i6) - i2;
            int i9 = i8 + (i2 - i6);
            ArraysKt___ArraysJvmKt.copyInto(segmentedByteString.getSegments()[iSegment], target, i3, i9, i9 + iMin);
            i3 += iMin;
            i2 += iMin;
            iSegment++;
        }
    }

    public static final boolean commonEquals(@NotNull SegmentedByteString segmentedByteString, @Nullable Object obj) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        if (obj == segmentedByteString) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() == segmentedByteString.size() && segmentedByteString.rangeEquals(0, byteString, 0, segmentedByteString.size())) {
                return true;
            }
        }
        return false;
    }

    public static final int commonGetSize(@NotNull SegmentedByteString segmentedByteString) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        return segmentedByteString.getDirectory()[segmentedByteString.getSegments().length - 1];
    }

    public static final int commonHashCode(@NotNull SegmentedByteString segmentedByteString) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        int hashCode = segmentedByteString.getHashCode();
        if (hashCode != 0) {
            return hashCode;
        }
        int length = segmentedByteString.getSegments().length;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < length) {
            int i5 = segmentedByteString.getDirectory()[length + i2];
            int i6 = segmentedByteString.getDirectory()[i2];
            byte[] bArr = segmentedByteString.getSegments()[i2];
            int i7 = (i6 - i4) + i5;
            while (i5 < i7) {
                i3 = (i3 * 31) + bArr[i5];
                i5++;
            }
            i2++;
            i4 = i6;
        }
        segmentedByteString.setHashCode$okio(i3);
        return i3;
    }

    public static final byte commonInternalGet(@NotNull SegmentedByteString segmentedByteString, int i2) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        _UtilKt.checkOffsetAndCount(segmentedByteString.getDirectory()[segmentedByteString.getSegments().length - 1], i2, 1L);
        int iSegment = segment(segmentedByteString, i2);
        return segmentedByteString.getSegments()[iSegment][(i2 - (iSegment == 0 ? 0 : segmentedByteString.getDirectory()[iSegment - 1])) + segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + iSegment]];
    }

    public static final boolean commonRangeEquals(@NotNull SegmentedByteString segmentedByteString, int i2, @NotNull ByteString other, int i3, int i4) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (i2 < 0 || i2 > segmentedByteString.size() - i4) {
            return false;
        }
        int i5 = i4 + i2;
        int iSegment = segment(segmentedByteString, i2);
        while (i2 < i5) {
            int i6 = iSegment == 0 ? 0 : segmentedByteString.getDirectory()[iSegment - 1];
            int i7 = segmentedByteString.getDirectory()[iSegment] - i6;
            int i8 = segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + iSegment];
            int iMin = Math.min(i5, i7 + i6) - i2;
            if (!other.rangeEquals(i3, segmentedByteString.getSegments()[iSegment], i8 + (i2 - i6), iMin)) {
                return false;
            }
            i3 += iMin;
            i2 += iMin;
            iSegment++;
        }
        return true;
    }

    @NotNull
    public static final ByteString commonSubstring(@NotNull SegmentedByteString segmentedByteString, int i2, int i3) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        int iResolveDefaultParameter = _UtilKt.resolveDefaultParameter(segmentedByteString, i3);
        if (!(i2 >= 0)) {
            throw new IllegalArgumentException(("beginIndex=" + i2 + " < 0").toString());
        }
        if (!(iResolveDefaultParameter <= segmentedByteString.size())) {
            throw new IllegalArgumentException(("endIndex=" + iResolveDefaultParameter + " > length(" + segmentedByteString.size() + ')').toString());
        }
        int i4 = iResolveDefaultParameter - i2;
        if (!(i4 >= 0)) {
            throw new IllegalArgumentException(("endIndex=" + iResolveDefaultParameter + " < beginIndex=" + i2).toString());
        }
        if (i2 == 0 && iResolveDefaultParameter == segmentedByteString.size()) {
            return segmentedByteString;
        }
        if (i2 == iResolveDefaultParameter) {
            return ByteString.EMPTY;
        }
        int iSegment = segment(segmentedByteString, i2);
        int iSegment2 = segment(segmentedByteString, iResolveDefaultParameter - 1);
        byte[][] bArr = (byte[][]) ArraysKt___ArraysJvmKt.copyOfRange(segmentedByteString.getSegments(), iSegment, iSegment2 + 1);
        int[] iArr = new int[bArr.length * 2];
        if (iSegment <= iSegment2) {
            int i5 = 0;
            int i6 = iSegment;
            while (true) {
                iArr[i5] = Math.min(segmentedByteString.getDirectory()[i6] - i2, i4);
                int i7 = i5 + 1;
                iArr[i5 + bArr.length] = segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + i6];
                if (i6 == iSegment2) {
                    break;
                }
                i6++;
                i5 = i7;
            }
        }
        int i8 = iSegment != 0 ? segmentedByteString.getDirectory()[iSegment - 1] : 0;
        int length = bArr.length;
        iArr[length] = iArr[length] + (i2 - i8);
        return new SegmentedByteString(bArr, iArr);
    }

    @NotNull
    public static final byte[] commonToByteArray(@NotNull SegmentedByteString segmentedByteString) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        byte[] bArr = new byte[segmentedByteString.size()];
        int length = segmentedByteString.getSegments().length;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < length) {
            int i5 = segmentedByteString.getDirectory()[length + i2];
            int i6 = segmentedByteString.getDirectory()[i2];
            int i7 = i6 - i3;
            ArraysKt___ArraysJvmKt.copyInto(segmentedByteString.getSegments()[i2], bArr, i4, i5, i5 + i7);
            i4 += i7;
            i2++;
            i3 = i6;
        }
        return bArr;
    }

    public static final void commonWrite(@NotNull SegmentedByteString segmentedByteString, @NotNull Buffer buffer, int i2, int i3) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        int i4 = i2 + i3;
        int iSegment = segment(segmentedByteString, i2);
        while (i2 < i4) {
            int i5 = iSegment == 0 ? 0 : segmentedByteString.getDirectory()[iSegment - 1];
            int i6 = segmentedByteString.getDirectory()[iSegment] - i5;
            int i7 = segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + iSegment];
            int iMin = Math.min(i4, i6 + i5) - i2;
            int i8 = i7 + (i2 - i5);
            Segment segment = new Segment(segmentedByteString.getSegments()[iSegment], i8, i8 + iMin, true, false);
            Segment segment2 = buffer.head;
            if (segment2 == null) {
                segment.prev = segment;
                segment.next = segment;
                buffer.head = segment;
            } else {
                Intrinsics.checkNotNull(segment2);
                Segment segment3 = segment2.prev;
                Intrinsics.checkNotNull(segment3);
                segment3.push(segment);
            }
            i2 += iMin;
            iSegment++;
        }
        buffer.setSize$okio(buffer.size() + i3);
    }

    public static final void forEachSegment(@NotNull SegmentedByteString segmentedByteString, @NotNull Function3<? super byte[], ? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int length = segmentedByteString.getSegments().length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = segmentedByteString.getDirectory()[length + i2];
            int i5 = segmentedByteString.getDirectory()[i2];
            action.invoke(segmentedByteString.getSegments()[i2], Integer.valueOf(i4), Integer.valueOf(i5 - i3));
            i2++;
            i3 = i5;
        }
    }

    public static final int segment(@NotNull SegmentedByteString segmentedByteString, int i2) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        int iBinarySearch = binarySearch(segmentedByteString.getDirectory(), i2 + 1, 0, segmentedByteString.getSegments().length);
        return iBinarySearch >= 0 ? iBinarySearch : ~iBinarySearch;
    }

    private static final void forEachSegment(SegmentedByteString segmentedByteString, int i2, int i3, Function3<? super byte[], ? super Integer, ? super Integer, Unit> function3) {
        int iSegment = segment(segmentedByteString, i2);
        while (i2 < i3) {
            int i4 = iSegment == 0 ? 0 : segmentedByteString.getDirectory()[iSegment - 1];
            int i5 = segmentedByteString.getDirectory()[iSegment] - i4;
            int i6 = segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + iSegment];
            int iMin = Math.min(i3, i5 + i4) - i2;
            function3.invoke(segmentedByteString.getSegments()[iSegment], Integer.valueOf(i6 + (i2 - i4)), Integer.valueOf(iMin));
            i2 += iMin;
            iSegment++;
        }
    }

    public static final boolean commonRangeEquals(@NotNull SegmentedByteString segmentedByteString, int i2, @NotNull byte[] other, int i3, int i4) {
        Intrinsics.checkNotNullParameter(segmentedByteString, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (i2 < 0 || i2 > segmentedByteString.size() - i4 || i3 < 0 || i3 > other.length - i4) {
            return false;
        }
        int i5 = i4 + i2;
        int iSegment = segment(segmentedByteString, i2);
        while (i2 < i5) {
            int i6 = iSegment == 0 ? 0 : segmentedByteString.getDirectory()[iSegment - 1];
            int i7 = segmentedByteString.getDirectory()[iSegment] - i6;
            int i8 = segmentedByteString.getDirectory()[segmentedByteString.getSegments().length + iSegment];
            int iMin = Math.min(i5, i7 + i6) - i2;
            if (!_UtilKt.arrayRangeEquals(segmentedByteString.getSegments()[iSegment], i8 + (i2 - i6), other, i3, iMin)) {
                return false;
            }
            i3 += iMin;
            i2 += iMin;
            iSegment++;
        }
        return true;
    }
}
