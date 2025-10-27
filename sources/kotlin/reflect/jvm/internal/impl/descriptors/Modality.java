package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public enum Modality {
    FINAL,
    SEALED,
    OPEN,
    ABSTRACT;


    @NotNull
    public static final Companion Companion = new Companion(null);

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Modality convertFromFlags(boolean z2, boolean z3, boolean z4) {
            return z2 ? Modality.SEALED : z3 ? Modality.ABSTRACT : z4 ? Modality.OPEN : Modality.FINAL;
        }
    }
}
