package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public interface BiMap<K, V> extends Map<K, V> {
    @CanIgnoreReturnValue
    @NullableDecl
    V forcePut(@NullableDecl K k2, @NullableDecl V v2);

    BiMap<V, K> inverse();

    @CanIgnoreReturnValue
    @NullableDecl
    V put(@NullableDecl K k2, @NullableDecl V v2);

    void putAll(Map<? extends K, ? extends V> map);

    @Override // java.util.Map
    Set<V> values();
}
