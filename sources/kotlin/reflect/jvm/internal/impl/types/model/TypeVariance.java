package kotlin.reflect.jvm.internal.impl.types.model;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public enum TypeVariance {
    IN("in"),
    OUT("out"),
    INV("");


    @NotNull
    private final String presentation;

    TypeVariance(String str) {
        this.presentation = str;
    }

    @Override // java.lang.Enum
    @NotNull
    public String toString() {
        return this.presentation;
    }
}
