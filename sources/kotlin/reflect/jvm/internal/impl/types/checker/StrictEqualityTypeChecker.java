package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractStrictEqualityTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class StrictEqualityTypeChecker {

    @NotNull
    public static final StrictEqualityTypeChecker INSTANCE = new StrictEqualityTypeChecker();

    private StrictEqualityTypeChecker() {
    }

    public final boolean strictEqualTypes(@NotNull UnwrappedType a3, @NotNull UnwrappedType b3) {
        Intrinsics.checkNotNullParameter(a3, "a");
        Intrinsics.checkNotNullParameter(b3, "b");
        return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(SimpleClassicTypeSystemContext.INSTANCE, a3, b3);
    }
}
