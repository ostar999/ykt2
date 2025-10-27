package kotlinx.serialization.json.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.descriptors.SerialDescriptor;
import org.jetbrains.annotations.NotNull;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public /* synthetic */ class JsonElementMarker$origin$1 extends FunctionReferenceImpl implements Function2<SerialDescriptor, Integer, Boolean> {
    public JsonElementMarker$origin$1(Object obj) {
        super(2, obj, JsonElementMarker.class, "readIfAbsent", "readIfAbsent(Lkotlinx/serialization/descriptors/SerialDescriptor;I)Z", 0);
    }

    @NotNull
    public final Boolean invoke(@NotNull SerialDescriptor p02, int i2) {
        Intrinsics.checkNotNullParameter(p02, "p0");
        return Boolean.valueOf(((JsonElementMarker) this.receiver).readIfAbsent(p02, i2));
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Boolean invoke(SerialDescriptor serialDescriptor, Integer num) {
        return invoke(serialDescriptor, num.intValue());
    }
}
