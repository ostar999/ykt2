package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.collections.MapsKt__MapsKt;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* loaded from: classes8.dex */
public interface NullabilityAnnotationStates<T> {

    @NotNull
    public static final Companion Companion = Companion.$$INSTANCE;

    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        @NotNull
        private static final NullabilityAnnotationStates EMPTY = new NullabilityAnnotationStatesImpl(MapsKt__MapsKt.emptyMap());

        private Companion() {
        }

        @NotNull
        public final NullabilityAnnotationStates getEMPTY() {
            return EMPTY;
        }
    }

    @Nullable
    T get(@NotNull FqName fqName);
}
