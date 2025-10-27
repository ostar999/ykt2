package kotlin.reflect.jvm.internal.impl.storage;

import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes8.dex */
public interface CacheWithNotNullValues<K, V> {
    @NotNull
    V computeIfAbsent(K k2, @NotNull Function0<? extends V> function0);
}
