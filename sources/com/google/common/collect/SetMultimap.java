package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes4.dex */
public interface SetMultimap<K, V> extends Multimap<K, V> {
    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    Map<K, Collection<V>> asMap();

    @Override // com.google.common.collect.Multimap
    Set<Map.Entry<K, V>> entries();

    @Override // com.google.common.collect.Multimap, com.google.common.collect.ListMultimap
    boolean equals(@NullableDecl Object obj);

    @Override // com.google.common.collect.Multimap
    Set<V> get(@NullableDecl K k2);

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    Set<V> removeAll(@NullableDecl Object obj);

    @Override // com.google.common.collect.Multimap
    @CanIgnoreReturnValue
    Set<V> replaceValues(K k2, Iterable<? extends V> iterable);
}
