package kotlin.reflect.jvm.internal.impl.types;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface CustomTypeParameter {
    boolean isTypeParameter();

    @NotNull
    KotlinType substitutionResult(@NotNull KotlinType kotlinType);
}
