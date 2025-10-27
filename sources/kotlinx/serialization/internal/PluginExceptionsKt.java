package kotlinx.serialization.internal;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.InternalSerializationApi;
import kotlinx.serialization.MissingFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a \u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u000b"}, d2 = {"throwArrayMissingFieldException", "", "seenArray", "", "goldenMaskArray", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "throwMissingFieldException", "seen", "", "goldenMask", "kotlinx-serialization-core"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class PluginExceptionsKt {
    @InternalSerializationApi
    public static final void throwArrayMissingFieldException(@NotNull int[] seenArray, @NotNull int[] goldenMaskArray, @NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(seenArray, "seenArray");
        Intrinsics.checkNotNullParameter(goldenMaskArray, "goldenMaskArray");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ArrayList arrayList = new ArrayList();
        int length = goldenMaskArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = goldenMaskArray[i2] & (~seenArray[i2]);
            if (i3 != 0) {
                for (int i4 = 0; i4 < 32; i4++) {
                    if ((i3 & 1) != 0) {
                        arrayList.add(descriptor.getElementName((i2 * 32) + i4));
                    }
                    i3 >>>= 1;
                }
            }
        }
        throw new MissingFieldException(arrayList, descriptor.getSerialName());
    }

    @InternalSerializationApi
    public static final void throwMissingFieldException(int i2, int i3, @NotNull SerialDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ArrayList arrayList = new ArrayList();
        int i4 = (~i2) & i3;
        for (int i5 = 0; i5 < 32; i5++) {
            if ((i4 & 1) != 0) {
                arrayList.add(descriptor.getElementName(i5));
            }
            i4 >>>= 1;
        }
        throw new MissingFieldException(arrayList, descriptor.getSerialName());
    }
}
