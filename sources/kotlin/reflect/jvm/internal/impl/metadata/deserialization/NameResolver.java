package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface NameResolver {
    @NotNull
    String getQualifiedClassName(int i2);

    @NotNull
    String getString(int i2);

    boolean isLocalClassName(int i2);
}
