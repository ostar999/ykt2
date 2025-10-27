package kotlin.reflect.jvm.internal.impl.incremental.components;

import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface LocationInfo {
    @NotNull
    String getFilePath();

    @NotNull
    Position getPosition();
}
