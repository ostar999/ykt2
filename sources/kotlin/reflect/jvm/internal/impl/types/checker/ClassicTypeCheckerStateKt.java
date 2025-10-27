package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.TypeCheckerState;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypePreparator;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class ClassicTypeCheckerStateKt {
    @NotNull
    public static final TypeCheckerState createClassicTypeCheckerState(boolean z2, boolean z3, @NotNull ClassicTypeSystemContext typeSystemContext, @NotNull KotlinTypePreparator kotlinTypePreparator, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new TypeCheckerState(z2, z3, true, typeSystemContext, kotlinTypePreparator, kotlinTypeRefiner);
    }

    public static /* synthetic */ TypeCheckerState createClassicTypeCheckerState$default(boolean z2, boolean z3, ClassicTypeSystemContext classicTypeSystemContext, KotlinTypePreparator kotlinTypePreparator, KotlinTypeRefiner kotlinTypeRefiner, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z3 = true;
        }
        if ((i2 & 4) != 0) {
            classicTypeSystemContext = SimpleClassicTypeSystemContext.INSTANCE;
        }
        if ((i2 & 8) != 0) {
            kotlinTypePreparator = KotlinTypePreparator.Default.INSTANCE;
        }
        if ((i2 & 16) != 0) {
            kotlinTypeRefiner = KotlinTypeRefiner.Default.INSTANCE;
        }
        return createClassicTypeCheckerState(z2, z3, classicTypeSystemContext, kotlinTypePreparator, kotlinTypeRefiner);
    }
}
