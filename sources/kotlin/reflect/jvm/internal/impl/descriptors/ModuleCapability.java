package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public final class ModuleCapability<T> {

    @NotNull
    private final String name;

    public ModuleCapability(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
    }

    @NotNull
    public String toString() {
        return this.name;
    }
}
