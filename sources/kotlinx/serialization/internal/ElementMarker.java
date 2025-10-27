package kotlinx.serialization.internal;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.descriptors.SerialDescriptor;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b\u0001\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0006H\u0002J\b\u0010\u0011\u001a\u00020\u0006H\u0002J\u0006\u0010\u0012\u001a\u00020\u0006J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lkotlinx/serialization/internal/ElementMarker;", "", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "readIfAbsent", "Lkotlin/Function2;", "", "", "(Lkotlinx/serialization/descriptors/SerialDescriptor;Lkotlin/jvm/functions/Function2;)V", "highMarksArray", "", "lowerMarks", "", "mark", "", "index", "markHigh", "nextUnmarkedHighIndex", "nextUnmarkedIndex", "prepareHighMarksArray", "elementsCount", "Companion", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
@PublishedApi
/* loaded from: classes8.dex */
public final class ElementMarker {

    @NotNull
    private static final Companion Companion = new Companion(null);

    @Deprecated
    @NotNull
    private static final long[] EMPTY_HIGH_MARKS = new long[0];

    @NotNull
    private final SerialDescriptor descriptor;

    @NotNull
    private final long[] highMarksArray;
    private long lowerMarks;

    @NotNull
    private final Function2<SerialDescriptor, Integer, Boolean> readIfAbsent;

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0016\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lkotlinx/serialization/internal/ElementMarker$Companion;", "", "()V", "EMPTY_HIGH_MARKS", "", "kotlinx-serialization-core"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ElementMarker(@NotNull SerialDescriptor descriptor, @NotNull Function2<? super SerialDescriptor, ? super Integer, Boolean> readIfAbsent) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(readIfAbsent, "readIfAbsent");
        this.descriptor = descriptor;
        this.readIfAbsent = readIfAbsent;
        int elementsCount = descriptor.getElementsCount();
        if (elementsCount <= 64) {
            this.lowerMarks = elementsCount != 64 ? (-1) << elementsCount : 0L;
            this.highMarksArray = EMPTY_HIGH_MARKS;
        } else {
            this.lowerMarks = 0L;
            this.highMarksArray = prepareHighMarksArray(elementsCount);
        }
    }

    private final void markHigh(int index) {
        int i2 = (index >>> 6) - 1;
        long[] jArr = this.highMarksArray;
        jArr[i2] = jArr[i2] | (1 << (index & 63));
    }

    private final int nextUnmarkedHighIndex() {
        int length = this.highMarksArray.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int i4 = i3 * 64;
            long j2 = this.highMarksArray[i2];
            while (j2 != -1) {
                int iNumberOfTrailingZeros = Long.numberOfTrailingZeros(~j2);
                j2 |= 1 << iNumberOfTrailingZeros;
                int i5 = iNumberOfTrailingZeros + i4;
                if (this.readIfAbsent.invoke(this.descriptor, Integer.valueOf(i5)).booleanValue()) {
                    this.highMarksArray[i2] = j2;
                    return i5;
                }
            }
            this.highMarksArray[i2] = j2;
            i2 = i3;
        }
        return -1;
    }

    private final long[] prepareHighMarksArray(int elementsCount) {
        long[] jArr = new long[(elementsCount - 1) >>> 6];
        if ((elementsCount & 63) != 0) {
            jArr[ArraysKt___ArraysKt.getLastIndex(jArr)] = (-1) << elementsCount;
        }
        return jArr;
    }

    public final void mark(int index) {
        if (index < 64) {
            this.lowerMarks |= 1 << index;
        } else {
            markHigh(index);
        }
    }

    public final int nextUnmarkedIndex() {
        int iNumberOfTrailingZeros;
        int elementsCount = this.descriptor.getElementsCount();
        do {
            long j2 = this.lowerMarks;
            if (j2 == -1) {
                if (elementsCount > 64) {
                    return nextUnmarkedHighIndex();
                }
                return -1;
            }
            iNumberOfTrailingZeros = Long.numberOfTrailingZeros(~j2);
            this.lowerMarks |= 1 << iNumberOfTrailingZeros;
        } while (!this.readIfAbsent.invoke(this.descriptor, Integer.valueOf(iNumberOfTrailingZeros)).booleanValue());
        return iNumberOfTrailingZeros;
    }
}
