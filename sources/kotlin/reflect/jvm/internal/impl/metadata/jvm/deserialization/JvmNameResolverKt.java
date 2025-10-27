package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class JvmNameResolverKt {
    @NotNull
    public static final List<JvmProtoBuf.StringTableTypes.Record> toExpandedRecordsList(@NotNull List<JvmProtoBuf.StringTableTypes.Record> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        arrayList.ensureCapacity(list.size());
        for (JvmProtoBuf.StringTableTypes.Record record : list) {
            int range = record.getRange();
            for (int i2 = 0; i2 < range; i2++) {
                arrayList.add(record);
            }
        }
        arrayList.trimToSize();
        return arrayList;
    }
}
