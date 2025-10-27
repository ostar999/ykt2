package kotlinx.serialization.json.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringNumberConversionsKt;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\b\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"MAX_CHARS_IN_POOL", "", "kotlinx-serialization-json"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ArrayPoolsKt {
    private static final int MAX_CHARS_IN_POOL;

    static {
        Object objM783constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            String property = System.getProperty("kotlinx.serialization.json.pool.size");
            Intrinsics.checkNotNullExpressionValue(property, "getProperty(\"kotlinx.ser…lization.json.pool.size\")");
            objM783constructorimpl = Result.m783constructorimpl(StringsKt__StringNumberConversionsKt.toIntOrNull(property));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = null;
        }
        Integer num = (Integer) objM783constructorimpl;
        MAX_CHARS_IN_POOL = num != null ? num.intValue() : 2097152;
    }
}
