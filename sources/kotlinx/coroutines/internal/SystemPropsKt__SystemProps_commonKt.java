package kotlinx.coroutines.internal;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.StrPool;
import kotlin.Metadata;
import kotlin.text.StringsKt__StringNumberConversionsKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001H\u0000\u001a,\u0010\u0000\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005H\u0000\u001a,\u0010\u0000\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\b2\b\b\u0002\u0010\u0006\u001a\u00020\b2\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0000¨\u0006\t"}, d2 = {"systemProp", "", "propertyName", "", "defaultValue", "", "minValue", "maxValue", "", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48, xs = "kotlinx/coroutines/internal/SystemPropsKt")
/* loaded from: classes8.dex */
public final /* synthetic */ class SystemPropsKt__SystemProps_commonKt {
    public static final boolean systemProp(@NotNull String str, boolean z2) {
        String strSystemProp = SystemPropsKt.systemProp(str);
        return strSystemProp != null ? Boolean.parseBoolean(strSystemProp) : z2;
    }

    public static /* synthetic */ int systemProp$default(String str, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 4) != 0) {
            i3 = 1;
        }
        if ((i5 & 8) != 0) {
            i4 = Integer.MAX_VALUE;
        }
        return SystemPropsKt.systemProp(str, i2, i3, i4);
    }

    public static final int systemProp(@NotNull String str, int i2, int i3, int i4) {
        return (int) SystemPropsKt.systemProp(str, i2, i3, i4);
    }

    public static /* synthetic */ long systemProp$default(String str, long j2, long j3, long j4, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            j3 = 1;
        }
        long j5 = j3;
        if ((i2 & 8) != 0) {
            j4 = Long.MAX_VALUE;
        }
        return SystemPropsKt.systemProp(str, j2, j5, j4);
    }

    public static final long systemProp(@NotNull String str, long j2, long j3, long j4) {
        String strSystemProp = SystemPropsKt.systemProp(str);
        if (strSystemProp == null) {
            return j2;
        }
        Long longOrNull = StringsKt__StringNumberConversionsKt.toLongOrNull(strSystemProp);
        if (longOrNull == null) {
            throw new IllegalStateException(("System property '" + str + "' has unrecognized value '" + strSystemProp + CharPool.SINGLE_QUOTE).toString());
        }
        long jLongValue = longOrNull.longValue();
        boolean z2 = false;
        if (j3 <= jLongValue && jLongValue <= j4) {
            z2 = true;
        }
        if (z2) {
            return jLongValue;
        }
        throw new IllegalStateException(("System property '" + str + "' should be in range " + j3 + StrPool.DOUBLE_DOT + j4 + ", but is '" + jLongValue + CharPool.SINGLE_QUOTE).toString());
    }
}
