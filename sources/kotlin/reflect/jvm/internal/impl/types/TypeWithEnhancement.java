package kotlin.reflect.jvm.internal.impl.types;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface TypeWithEnhancement {
    @NotNull
    KotlinType getEnhancement();

    @NotNull
    UnwrappedType getOrigin();
}
